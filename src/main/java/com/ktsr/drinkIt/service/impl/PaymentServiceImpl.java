package com.ktsr.drinkIt.service.impl;

import com.ktsr.drinkIt.DTO.NotificationDto;
import com.ktsr.drinkIt.DTO.OrderEventDto;
import com.ktsr.drinkIt.DTO.PaymentDto;
import com.ktsr.drinkIt.DTO.PaymentEventDto;
import com.ktsr.drinkIt.entity.Invoice;
import com.ktsr.drinkIt.entity.Order;
import com.ktsr.drinkIt.entity.Payment;
import com.ktsr.drinkIt.entity.User;
import com.ktsr.drinkIt.enums.NotificationType;
import com.ktsr.drinkIt.enums.OrderStatus;
import com.ktsr.drinkIt.enums.PaymentMethod;
import com.ktsr.drinkIt.enums.PaymentStatus;
import com.ktsr.drinkIt.messaging.NotificationEventProducer;
import com.ktsr.drinkIt.messaging.OrderEventProducer;
import com.ktsr.drinkIt.messaging.PaymentEventProducer;
import com.ktsr.drinkIt.repository.InvoiceRepository;
import com.ktsr.drinkIt.repository.OrderRepository;
import com.ktsr.drinkIt.repository.PaymentRepository;
import com.ktsr.drinkIt.service.PaymentService;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    private final OrderEventProducer orderEventProducer;
    private final NotificationEventProducer notificationEventProducer;
    private final InvoiceRepository invoiceRepository;
    private final PaymentEventProducer paymentEventProducer;

    @Value("${stripe.api.key}")
    private String stripeSecretKey;

    @Value("${razorpay.api.key}")
    private String razorpayApiKey;

    @Value("${razorpay.api.secret}")
    private String razorpayApiSecret;

    @Override
    public Payment createPayment(PaymentDto dto) throws RazorpayException, StripeException {

        Order order = orderRepository.findById(dto.getOrderId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        Optional<Payment> existingPayment = paymentRepository.findByOrderId(order.getId());

        if (existingPayment.isPresent()) {
            throw new IllegalArgumentException("Payment already exists for this order.");
        }

        Payment payment = Payment.builder()
                .order(order)
                .paymentMethod(dto.getPaymentMethod())
                .amount(order.getFinalAmount())
                .status(PaymentStatus.PENDING)
                .paymentDate(LocalDateTime.now())
                .build();

        Payment savedPayment = paymentRepository.save(payment);

        switch (dto.getPaymentMethod()) {
            case COD:
                savedPayment.setTransactionId("COD-" + System.currentTimeMillis());
                savedPayment.setGatewayResponse("Cash on Delivery selected.");
                savedPayment.setStatus(PaymentStatus.SUCCESS);
                paymentRepository.save(savedPayment);
                break;
            case STRIPE:
                // Call Stripe payment processing logic here
                String stripPaymentUrl=createStripePaymentLink(order.getUser(),
                                        savedPayment.getAmount(), savedPayment.getId());
                savedPayment.setGatewayResponse(stripPaymentUrl);
                savedPayment.setStatus(PaymentStatus.PENDING);
                paymentRepository.save(savedPayment);
                break;
            case RAZORPAY:
                // Call Razorpay payment processing logic here
                PaymentLink razorpayPayment =createRazorPayPaymentLink(order.getUser(),
                                        savedPayment.getAmount(), savedPayment.getId());
                String razorpayUrl = razorpayPayment.get("short_url").toString();
                String razorpayPaymentLinkId = razorpayPayment.get("id").toString();
                savedPayment.setPaymentLinkId(razorpayPaymentLinkId);
                savedPayment.setGatewayResponse(razorpayUrl);
                savedPayment.setStatus(PaymentStatus.PENDING);
                paymentRepository.save(savedPayment);
                break;
            default:
                throw new IllegalArgumentException("Unsupported payment method: " + dto.getPaymentMethod());
        }

        return savedPayment;
    }

    @Override
    public Payment getPaymentOrderById(Long id) {
        return paymentRepository.findById(id).orElse(null);
    }

    @Override
    public Payment getPaymentOrderByPaymentId(String paymentLinkId) {
        return paymentRepository.findByPaymentLinkId(paymentLinkId).orElse(null);
    }


    @Override
    public String createStripePaymentLink(User user, Double amount, Long id) throws StripeException {
        Stripe.apiKey=stripeSecretKey;

        SessionCreateParams params=SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:3000/payment-success/"+id)
                .setCancelUrl("http://localhost:3000/payment-cancel")
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("INR")
                                .setUnitAmount((long) (amount*100))
                                .setProductData(SessionCreateParams.
                                        LineItem.
                                        PriceData.
                                        ProductData.
                                        builder().setName("DrinkIt Order").build()
                                ).build()
                        ).build()
                ).build();

        Session session=Session.create(params);

        return session.getUrl();
    }

    @Override
    public PaymentLink createRazorPayPaymentLink(User user, Double amount, Long id) throws RazorpayException {

        Long amountPay = Math.round(amount * 100);

        RazorpayClient razorpay = new RazorpayClient(razorpayApiKey, razorpayApiSecret);

        JSONObject request= new JSONObject();
        request.put("amount",amountPay);
        request.put("currency","INR");

        JSONObject customer= new JSONObject();
        customer.put("name" , user.getFullName());
        customer.put("email",user.getEmail());
        request.put("customer",customer);

        JSONObject notify=  new JSONObject();
        notify.put("email", true);
        notify.put("sms", true);

        request.put("notify",notify);

        request.put("callback_url","http://localhost:8080/api/payments/razorpay/callback");
        request.put("callback_method","get");
        try {
            return razorpay.paymentLink.create(request);
        } catch (RazorpayException e) {
            throw new RuntimeException("Failed to create Razorpay payment link", e);
        }
    }

    @Override
    @Transactional
    public Boolean processedPayment(Payment payment,
                                    String paymentId,
                                    String paymentLinkId) throws RazorpayException {

        if (payment == null) {
            throw new EntityNotFoundException("Payment not found");
        }

        if (payment.getStatus() == PaymentStatus.SUCCESS) {
            return true;
        }

        if (payment.getPaymentMethod() != PaymentMethod.RAZORPAY) {
            throw new IllegalArgumentException("Only Razorpay payments are supported here.");
        }

        RazorpayClient razorpayClient =
                new RazorpayClient(razorpayApiKey, razorpayApiSecret);

        com.razorpay.Payment razorPayment =
                razorpayClient.payments.fetch(paymentId);

        String razorpayStatus = razorPayment.get("status");

        Integer amountInPaise = razorPayment.get("amount");
        Double amount = amountInPaise / 100.0;

        if (Math.abs(amount - payment.getAmount()) > 0.01) {
            throw new IllegalArgumentException("Payment amount mismatch.");
        }

        if (!"captured".equalsIgnoreCase(razorpayStatus)) {
            return false;
        }

        payment.setTransactionId(paymentId);
        payment.setPaymentLinkId(paymentLinkId);
        payment.setStatus(PaymentStatus.SUCCESS);
        payment.setPaymentDate(LocalDateTime.now());

        paymentRepository.save(payment);

        PaymentEventDto paymentEvent = PaymentEventDto.builder()
                .paymentId(payment.getId())
                .orderId(payment.getOrder().getId())
                .userId(payment.getOrder().getUser().getId())
                .amount(payment.getAmount())
                .transactionId(paymentId)
                .paymentMethod(payment.getPaymentMethod())
                .paymentStatus(PaymentStatus.SUCCESS)
                .build();

        paymentEventProducer.sendPaymentSuccessEvent(paymentEvent);

        return true;
    }

    @Override
    @Transactional
    public void processPaymentEvent(PaymentEventDto event) {

        Payment payment = paymentRepository.findById(event.getPaymentId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Payment not found"));

        // Update payment only if it is not already successful
        if (payment.getStatus() != PaymentStatus.SUCCESS) {

            payment.setTransactionId(event.getTransactionId());
            payment.setStatus(event.getPaymentStatus());
            payment.setPaymentDate(LocalDateTime.now());

            paymentRepository.save(payment);
        }

        Order order = payment.getOrder();

        // Continue only for successful payments
        if (event.getPaymentStatus() == PaymentStatus.SUCCESS) {

            // Update Order
            if (order.getPaymentStatus() != PaymentStatus.SUCCESS) {

                order.setPaymentStatus(PaymentStatus.SUCCESS);

                if (order.getStatus() != OrderStatus.CONFIRMED) {
                    order.setStatus(OrderStatus.CONFIRMED);
                }

                orderRepository.save(order);
            }

            // Generate Invoice if it doesn't exist
            if (!invoiceRepository.existsByOrderId(order.getId())) {

                Invoice invoice = Invoice.builder()
                        .order(order)
                        .invoiceNumber("INV-" + order.getOrderNumber())
                        .invoiceDate(LocalDateTime.now())
                        .pdfUrl(null)
                        .build();

                invoiceRepository.save(invoice);
            }

            // Send Notification
            NotificationDto notification = NotificationDto.builder()
                    .userId(order.getUser().getId())
                    .title("Payment Successful")
                    .message("Payment received successfully for Order "
                            + order.getOrderNumber())
                    .type(NotificationType.PAYMENT)
                    .build();

            notificationEventProducer.sendNotification(notification);
        }
    }
}
