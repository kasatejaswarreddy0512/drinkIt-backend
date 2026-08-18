 package com.ktsr.drinkIt.service.impl;

import com.ktsr.drinkIt.DTO.OrderDto;
import com.ktsr.drinkIt.DTO.OrderEventDto;
import com.ktsr.drinkIt.DTO.PaymentDto;
import com.ktsr.drinkIt.entity.*;
import com.ktsr.drinkIt.enums.*;
import com.ktsr.drinkIt.helper.OrderResponseDto;
import com.ktsr.drinkIt.messaging.NotificationEventProducer;
import com.ktsr.drinkIt.messaging.OrderEventProducer;
import com.ktsr.drinkIt.repository.*;
import com.ktsr.drinkIt.service.OrderService;
import com.ktsr.drinkIt.service.PaymentService;
import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private static final Double TAX_RATE = 0.18;
    private static final Double FREE_DELIVERY_LIMIT = 500.0;
    private static final Double DELIVERY_CHARGE = 50.0;

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final CouponRepository couponRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderItemRepository orderItemRepository;
    private final PaymentRepository paymentRepository;
    private final ProductVariantRepository productVariantRepository;
    private final RefundRepository refundRepository;
    private final NotificationEventProducer notificationEventProducer;
    private final PaymentService paymentService;
    private final OrderEventProducer orderEventProducer;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderResponseDto createOrder(OrderDto dto, PaymentMethod paymentMethod) throws StripeException, RazorpayException {

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Address address = addressRepository.findById(dto.getAddressId())
                .orElseThrow(() -> new EntityNotFoundException("Address not found"));

        // Make sure address belongs to user
        if (!address.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException(
                    "Address does not belong to the user");
        }

        Coupon coupon = null;

        if (coupon != null) {

            LocalDate today = LocalDate.now();

            if (!coupon.getActive()) {
                throw new IllegalArgumentException("Coupon is inactive.");
            }

            if (today.isBefore(coupon.getStartDate())
                    || today.isAfter(coupon.getEndDate())) {
                throw new IllegalArgumentException("Coupon has expired.");
            }
        }

        Cart cart = cartRepository.findByUserId(dto.getUserId());

        if (cart == null) {
            throw new EntityNotFoundException("Cart not found");
        }

        List<CartItem> cartItems =
                cartItemRepository.findByCartId(cart.getId());

        if (cartItems == null || cartItems.isEmpty()) {
            throw new IllegalArgumentException("Cart is empty.");
        }

        for (CartItem item : cartItems) {

            ProductVariant variant = item.getProductVariant();

            if (variant == null) {
                throw new EntityNotFoundException("Product variant not found");
            }

            if (!Boolean.TRUE.equals(variant.getActive())) {
                throw new IllegalArgumentException("Product variant is inactive: " + variant.getSku());
            }

            if (variant.getStock() < item.getQuantity()) {
                throw new IllegalArgumentException("Insufficient stock for product variant: " + variant.getSku() + ". Available: " + variant.getStock() + ", Requested: " + item.getQuantity());
            }
        }
        Double totalAmount = cartItems.stream()
                .map(CartItem::getSubtotal)
                .reduce(0.0, Double::sum);

        totalAmount = round(totalAmount);
        Double discount = calculateDiscount(coupon, totalAmount);
        Double deliveryCharge = totalAmount >= FREE_DELIVERY_LIMIT ? 0.0 : DELIVERY_CHARGE;

        Double tax = round(totalAmount * TAX_RATE);
        Double finalAmount = totalAmount - discount + deliveryCharge + tax;
        finalAmount = round(finalAmount);


        if (finalAmount < 0) {
            finalAmount = 0.0;
        }

        Order order = Order.builder()
                .orderNumber(generateOrderNumber())
                .user(user)
                .address(address)
                .coupon(coupon)
                .totalAmount(totalAmount)
                .discount(discount)
                .deliveryCharge(deliveryCharge)
                .tax(tax)
                .finalAmount(finalAmount)
                .status(OrderStatus.PLACED)
                .paymentStatus(PaymentStatus.PENDING)
                .orderDate(LocalDateTime.now())
                .build();

        Order savedOrder = orderRepository.save(order);

        for (CartItem item : cartItems) {

            ProductVariant variant = item.getProductVariant();

            OrderItem orderItem = OrderItem.builder()
                    .order(savedOrder)
                    .productVariant(variant)
                    .quantity(item.getQuantity())
                    .price(item.getPrice())
                    .subtotal(item.getSubtotal())
                    .build();

            OrderItem savedItem = orderItemRepository.save(orderItem);

            variant.setStock(variant.getStock() - item.getQuantity());
            variant.setUpdatedAt(LocalDateTime.now());

            productVariantRepository.save(variant);
        }

        cartItemRepository.deleteByCartId(cart.getId());

        cart.setTotalAmount(0.0);

        cartRepository.save(cart);

        PaymentDto paymentDto = PaymentDto.builder()
                .orderId(savedOrder.getId())
                .paymentMethod(paymentMethod)
                .build();

       Payment payment= paymentService.createPayment(paymentDto);

        OrderEventDto event = OrderEventDto.builder()
                .orderId(payment.getOrder().getId())
                .userId(payment.getOrder().getUser().getId())
                .orderNumber(payment.getOrder().getOrderNumber())
                .paymentStatus(PaymentStatus.SUCCESS)
                .build();

        orderEventProducer.sendOrderCreatedEvent(event);

        return OrderResponseDto.builder()
                .order(savedOrder)
                .payment(payment)
                .build();
    }


    @Override
    @Transactional
    public Order updateOrder(Long id, OrderDto dto) {

        Order order = getOrderById(id);

        if (order.getStatus() == OrderStatus.SHIPPED
                || order.getStatus() == OrderStatus.OUT_FOR_DELIVERY
                || order.getStatus() == OrderStatus.DELIVERED
                || order.getStatus() == OrderStatus.CANCELLED) {
            throw new IllegalStateException("Order cannot be updated once it has been shipped, delivered or cancelled.");
        }

        if (dto.getAddressId() != null && !dto.getAddressId().equals(order.getAddress().getId())) {

            Address address = addressRepository.findById(dto.getAddressId())
                    .orElseThrow(() -> new EntityNotFoundException("Address not found"));


            if (!address.getUser().getId().equals(order.getUser().getId())) {
                throw new IllegalArgumentException("Address does not belong to the user");
            }

            order.setAddress(address);
        }

        if (dto.getDeliveryDate() != null) {
            order.setDeliveryDate(dto.getDeliveryDate());
        }

        return orderRepository.save(order);
    }


    @Override
    @Transactional(readOnly = true)
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Order getOrderByOrderNumber(String orderNumber) {
        return orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new EntityNotFoundException("Order not found: " + orderNumber));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getOrdersByUser(Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("User not found");
        }
        return orderRepository.findByUserId(userId);
    }


    @Override
    @Transactional(readOnly = true)
    public List<Order> getOrdersByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status);
    }


    @Override
    @Transactional(readOnly = true)
    public List<Order> getOrdersByPaymentStatus(PaymentStatus paymentStatus) {
        return orderRepository.findByPaymentStatus(paymentStatus);
    }


    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return orderRepository.existsById(id);
    }


    @Override
    @Transactional
    public Order updateOrderStatus(Long id, OrderStatus status) {
        Order order = getOrderById(id);

        if (status == null) {
            throw new IllegalArgumentException("Order status cannot be null");
        }

        if (order.getStatus() == OrderStatus.DELIVERED) {
            throw new IllegalStateException("Delivered order cannot be updated.");
        }

        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new IllegalStateException("Cancelled order cannot be updated.");
        }

        order.setStatus(status);

        return orderRepository.save(order);
    }


    @Override
    @Transactional
    public Order updatePaymentStatus(
            Long id,
            PaymentStatus paymentStatus) {

        Order order = getOrderById(id);


        if (paymentStatus == null) {
            throw new IllegalArgumentException("Payment status cannot be null");
        }

        if (order.getPaymentStatus() == PaymentStatus.SUCCESS) {
            throw new IllegalStateException("Payment is already completed.");
        }

        if (order.getPaymentStatus() == PaymentStatus.REFUNDED) {
            throw new IllegalStateException("Payment has already been refunded.");
        }

        order.setPaymentStatus(paymentStatus);

        return orderRepository.save(order);
    }


    @Override
    @Transactional
    public void cancelOrder(Long id) {

        Order order = getOrderById(id);

        if (order.getStatus() == OrderStatus.DELIVERED) {
            throw new IllegalStateException("Delivered order cannot be cancelled.");
        }

        if (order.getStatus() == OrderStatus.SHIPPED || order.getStatus() == OrderStatus.OUT_FOR_DELIVERY) {
            throw new IllegalStateException("Shipped order cannot be cancelled.");
        }

        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new IllegalStateException("Order is already cancelled.");
        }

        order.setStatus(OrderStatus.CANCELLED);

        List<OrderItem> orderItems = orderItemRepository.findByOrderId(order.getId());

        for (OrderItem item : orderItems) {
            ProductVariant variant = item.getProductVariant();
            variant.setStock(variant.getStock() + item.getQuantity());
            productVariantRepository.save(variant);
        }

        if (order.getPaymentStatus() == PaymentStatus.SUCCESS) {

            Payment payment = paymentRepository.findByOrderId(order.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Payment not found"));

            Refund refund = Refund.builder()
                    .payment(payment)
                    .amount(payment.getAmount())
                    .reason("Order Cancelled")
                    .status(RefundStatus.PENDING)
                    .refundDate(LocalDateTime.now())
                    .build();

            refundRepository.save(refund);

            order.setPaymentStatus(PaymentStatus.REFUNDED);
        }

        orderRepository.save(order);
        notificationEventProducer.sendOrderCancelledNotification(order.getUser().getId(), order.getOrderNumber());
    }


    @Override
    @Transactional
    public void deleteOrder(Long id) {

        Order order = getOrderById(id);

        if (order.getStatus() != OrderStatus.CANCELLED) {
            throw new IllegalStateException("Only cancelled orders can be deleted.");
        }

        orderRepository.delete(order);
    }

    @Override
    @Transactional
    public Order processOrder(OrderEventDto event) {

        Order order = getOrderById(event.getOrderId());
        order.setStatus(OrderStatus.CONFIRMED);
        order.setPaymentStatus(event.getPaymentStatus());
        return orderRepository.save(order);
    }

    private Double calculateDiscount(Coupon coupon, Double totalAmount) {

        if (coupon == null) {
            return 0.0;
        }

        if (coupon.getMinimumAmount() != null && totalAmount < coupon.getMinimumAmount()) {
            throw new IllegalArgumentException("Minimum purchase amount not reached.");
        }

        Double discount;

        if (coupon.getDiscountType() == DiscountType.PERCENTAGE) {
            discount = totalAmount * coupon.getDiscountValue() / 100.0;

        } else {
            discount = coupon.getDiscountValue();
        }

        if (discount > totalAmount) {
            discount = totalAmount;
        }

        return round(discount);
    }


    private Double round(Double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    private String generateOrderNumber() {
        return "ORD-" + System.currentTimeMillis() + "-" + UUID.randomUUID()
                .toString().substring(0, 8).toUpperCase();
    }
}

