package com.ktsr.drinkIt.controller;

import com.ktsr.drinkIt.DTO.OrderItemDto;
import com.ktsr.drinkIt.entity.OrderItem;
import com.ktsr.drinkIt.enums.ErrorCode;
import com.ktsr.drinkIt.helper.APIResponse;
import com.ktsr.drinkIt.helper.ResponseWrapper;
import com.ktsr.drinkIt.service.OrderItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order-items")
@RequiredArgsConstructor
public class OrderItemController {

    private final OrderItemService orderItemService;

    @PostMapping
    public ResponseEntity<ResponseWrapper> createOrderItem(@Valid @RequestBody OrderItemDto orderItem){
        try {
            OrderItem orderItemResult = orderItemService.createOrderItem(orderItem);
            return APIResponse.get(ErrorCode.SUCCESS, orderItemResult, HttpStatus.CREATED);
        }
        catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper> updateOrderItem(@PathVariable Long id, @Valid @RequestBody OrderItemDto orderItem){
        try {
            boolean exists = orderItemService.existsById(id);
            if(!exists){
                return APIResponse.get(ErrorCode.ORDER_ITEM_NOT_FOUND,"Order Item not found", HttpStatus.NOT_FOUND);
            }
            OrderItem orderItemResult = orderItemService.updateOrderItem(id, orderItem);
            return APIResponse.get(ErrorCode.SUCCESS, orderItemResult, HttpStatus.OK);
        }
        catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper> getOrderItem(@PathVariable Long id){
        try {
            boolean exists = orderItemService.existsById(id);
            if(!exists){
                return APIResponse.get(ErrorCode.ORDER_ITEM_NOT_FOUND,"Order Item not found", HttpStatus.NOT_FOUND);
            }
            OrderItem orderItemResult = orderItemService.getOrderItemById(id);
            return APIResponse.get(ErrorCode.SUCCESS, orderItemResult, HttpStatus.OK);
        }
        catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<ResponseWrapper> getOrderItemByOrderId(@PathVariable Long orderId){
        try {
            List<OrderItem> orderItems = orderItemService.getOrderItemsByOrder(orderId);
            return APIResponse.get(ErrorCode.SUCCESS, orderItems, HttpStatus.OK);
        }
        catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/product-variant/{productVariantId}")
    public ResponseEntity<ResponseWrapper> getOrderItemByProductVariantId(@PathVariable Long productVariantId){
        try {
            List<OrderItem> orderItems = orderItemService.getOrderItemsByProductVariant(productVariantId);
            return APIResponse.get(ErrorCode.SUCCESS, orderItems, HttpStatus.OK);
        }
        catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper> deleteOrderItem(@PathVariable Long id){
        try {
            boolean exists = orderItemService.existsById(id);
            if(!exists){
                return APIResponse.get(ErrorCode.ORDER_ITEM_NOT_FOUND,"Order Item not found", HttpStatus.NOT_FOUND);
            }
            orderItemService.deleteOrderItem(id);
            return APIResponse.get(ErrorCode.SUCCESS,"Order Item deleted successfully", HttpStatus.OK);
        }
        catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
