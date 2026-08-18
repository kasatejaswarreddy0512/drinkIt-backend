package com.ktsr.drinkIt.service.impl;

import com.ktsr.drinkIt.DTO.OrderItemDto;
import com.ktsr.drinkIt.entity.Order;
import com.ktsr.drinkIt.entity.OrderItem;
import com.ktsr.drinkIt.entity.ProductVariant;
import com.ktsr.drinkIt.repository.OrderItemRepository;
import com.ktsr.drinkIt.repository.OrderRepository;
import com.ktsr.drinkIt.repository.ProductVariantRepository;
import com.ktsr.drinkIt.service.OrderItemService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final ProductVariantRepository productVariantRepository;

    @Override
    public OrderItem createOrderItem(OrderItemDto dto) {
        Order order = orderRepository.findById(dto.getOrderId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        ProductVariant variant = productVariantRepository.findById(dto.getProductVariantId())
                .orElseThrow(() -> new EntityNotFoundException("Product Variant not found"));

        Double subtotal = variant.getPrice() * dto.getQuantity();

        OrderItem orderItem = OrderItem.builder()
                .order(order)
                .productVariant(variant)
                .quantity(dto.getQuantity())
                .price(variant.getPrice())
                .subtotal(subtotal)
                .build();

        return orderItemRepository.save(orderItem);
    }

    @Override
    public OrderItem updateOrderItem(Long id, OrderItemDto dto) {
        OrderItem existing = getOrderItemById(id);

        Order order = orderRepository.findById(dto.getOrderId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        ProductVariant variant = productVariantRepository.findById(dto.getProductVariantId())
                .orElseThrow(() -> new EntityNotFoundException("Product Variant not found"));

        Double subtotal = variant.getPrice() * dto.getQuantity();

        existing.setOrder(order);
        existing.setProductVariant(variant);
        existing.setQuantity(dto.getQuantity());
        existing.setPrice(variant.getPrice());
        existing.setSubtotal(subtotal);

        return orderItemRepository.save(existing);
    }

    @Override
    public OrderItem getOrderItemById(Long id) {
        return orderItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order Item not found"));
    }

    @Override
    public List<OrderItem> getOrderItemsByOrder(Long orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }

    @Override
    public List<OrderItem> getOrderItemsByProductVariant(Long productVariantId) {
        return orderItemRepository.findByProductVariantId(productVariantId);
    }

    @Override
    public boolean existsById(Long id) {
        return orderItemRepository.existsById(id);
    }

    @Override
    public void deleteOrderItem(Long id) {
        OrderItem orderItem = getOrderItemById(id);
        orderItemRepository.delete(orderItem);
    }
}
