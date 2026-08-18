package com.ktsr.drinkIt.service;

import com.ktsr.drinkIt.DTO.OrderItemDto;
import com.ktsr.drinkIt.entity.OrderItem;

import java.util.List;

public interface OrderItemService {

    OrderItem createOrderItem(OrderItemDto dto);

    OrderItem updateOrderItem(Long id, OrderItemDto dto);

    OrderItem getOrderItemById(Long id);

    List<OrderItem> getOrderItemsByOrder(Long orderId);

    List<OrderItem> getOrderItemsByProductVariant(Long productVariantId);

    boolean existsById(Long id);

    void deleteOrderItem(Long id);
}
