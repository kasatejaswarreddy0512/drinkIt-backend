package com.ktsr.drinkIt.service;

import com.ktsr.drinkIt.DTO.OrderDto;
import com.ktsr.drinkIt.DTO.OrderEventDto;
import com.ktsr.drinkIt.entity.Order;
import com.ktsr.drinkIt.enums.OrderStatus;
import com.ktsr.drinkIt.enums.PaymentMethod;
import com.ktsr.drinkIt.enums.PaymentStatus;
import com.ktsr.drinkIt.helper.OrderResponseDto;
import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;

import java.util.List;

public interface OrderService {

    OrderResponseDto createOrder(OrderDto dto, PaymentMethod paymentMethod) throws StripeException, RazorpayException;

    Order updateOrder(Long id, OrderDto dto);

    Order getOrderById(Long id);

    Order getOrderByOrderNumber(String orderNumber);

    List<Order> getAllOrders();

    List<Order> getOrdersByUser(Long userId);

    List<Order> getOrdersByStatus(OrderStatus status);

    List<Order> getOrdersByPaymentStatus(PaymentStatus paymentStatus);

    Order updateOrderStatus(Long id, OrderStatus status);

    Order updatePaymentStatus(Long id, PaymentStatus paymentStatus);

    boolean existsById(Long id);

    void cancelOrder(Long id);

    void deleteOrder(Long id);

    Order processOrder(OrderEventDto event);
}
