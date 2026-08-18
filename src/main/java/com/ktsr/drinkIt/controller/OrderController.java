package com.ktsr.drinkIt.controller;

import com.ktsr.drinkIt.DTO.OrderDto;
import com.ktsr.drinkIt.DTO.PaymentDto;
import com.ktsr.drinkIt.entity.Order;
import com.ktsr.drinkIt.entity.Payment;
import com.ktsr.drinkIt.enums.ErrorCode;
import com.ktsr.drinkIt.enums.OrderStatus;
import com.ktsr.drinkIt.enums.PaymentMethod;
import com.ktsr.drinkIt.enums.PaymentStatus;
import com.ktsr.drinkIt.helper.APIResponse;
import com.ktsr.drinkIt.helper.OrderResponseDto;
import com.ktsr.drinkIt.helper.ResponseWrapper;
import com.ktsr.drinkIt.service.OrderService;
import com.ktsr.drinkIt.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<ResponseWrapper> createOrder(@Valid @RequestBody OrderDto order,
                                                       @RequestParam PaymentMethod paymentMethod) {
        try {
            OrderResponseDto createdOrder = orderService.createOrder(order,paymentMethod);
            return APIResponse.get(ErrorCode.SUCCESS,createdOrder, HttpStatus.CREATED );
        }catch (Exception e) {
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper> updateOrder(@PathVariable Long id, @Valid @RequestBody OrderDto order) {
        try {
            boolean exists = orderService.existsById(id);
            if(!exists) {
                return APIResponse.get(ErrorCode.ORDER_NOT_FOUND,"Order not found", HttpStatus.NOT_FOUND);
            }
            Order updatedOrder = orderService.updateOrder(id, order);
            return APIResponse.get(ErrorCode.SUCCESS,updatedOrder, HttpStatus.OK );
        }catch (Exception e) {
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper> getOrderById(@PathVariable Long id) {
        try {
            boolean exists = orderService.existsById(id);
            if(!exists) {
                return APIResponse.get(ErrorCode.ORDER_NOT_FOUND,"Order not found", HttpStatus.NOT_FOUND);
            }
            Order order = orderService.getOrderById(id);
            return APIResponse.get(ErrorCode.SUCCESS,order, HttpStatus.OK);
        }
        catch (Exception e) {
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/number/{orderNumber}")
    public ResponseEntity<ResponseWrapper> getOrderByNumber(@PathVariable String orderNumber) {
        try {
            Order order = orderService.getOrderByOrderNumber(orderNumber);
            return APIResponse.get(ErrorCode.SUCCESS,order, HttpStatus.OK);
        }
        catch (Exception e) {
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper> getAllOrders() {
        try {
            List<Order> orders = orderService.getAllOrders();
            return APIResponse.get(ErrorCode.SUCCESS,orders, HttpStatus.OK);
        }
        catch (Exception e) {
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseWrapper> getOrdersByUserId(@PathVariable Long userId) {
        try {
            List<Order> orders = orderService.getOrdersByUser(userId);
            return APIResponse.get(ErrorCode.SUCCESS,orders, HttpStatus.OK);
        }
        catch (Exception e) {
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ResponseWrapper> getOrdersByStatus(@PathVariable String status) {
        try {
            List<Order> orders = orderService.getOrdersByStatus(Enum.valueOf(com.ktsr.drinkIt.enums.OrderStatus.class, status));
            return APIResponse.get(ErrorCode.SUCCESS,orders, HttpStatus.OK);
        }
        catch (Exception e) {
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/payment-status/{paymentStatus}")
    public ResponseEntity<ResponseWrapper> getOrdersByPaymentStatus(@PathVariable String paymentStatus) {
        try {
            List<Order> orders = orderService.getOrdersByPaymentStatus(Enum.valueOf(com.ktsr.drinkIt.enums.PaymentStatus.class, paymentStatus));
            return APIResponse.get(ErrorCode.SUCCESS, orders, HttpStatus.OK);
        } catch (Exception e) {
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR, "Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ResponseWrapper> updateOrderStatus(@PathVariable Long id, @RequestParam OrderStatus status) {
        try {
            boolean exists = orderService.existsById(id);
            if (!exists) {
                return APIResponse.get(ErrorCode.ORDER_NOT_FOUND, "Order not found", HttpStatus.NOT_FOUND);
            }
            Order updatedOrder = orderService.updateOrderStatus(id, status);
            return APIResponse.get(ErrorCode.SUCCESS, updatedOrder, HttpStatus.OK);
        } catch (Exception e) {
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR, "Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/{id}/payment-status")
    public ResponseEntity<ResponseWrapper> updatePaymentStatus(@PathVariable Long id, @RequestParam PaymentStatus paymentStatus) {
        try {
            boolean exists = orderService.existsById(id);
            if (!exists) {
                return APIResponse.get(ErrorCode.ORDER_NOT_FOUND, "Order not found", HttpStatus.NOT_FOUND);
            }
            Order updatedOrder = orderService.updatePaymentStatus(id, paymentStatus);
            return APIResponse.get(ErrorCode.SUCCESS, updatedOrder, HttpStatus.OK);
        } catch (Exception e) {
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR, "Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<ResponseWrapper> cancelOrder(@PathVariable Long id) {
        try {
            boolean exists = orderService.existsById(id);
            if (!exists) {
                return APIResponse.get(ErrorCode.ORDER_NOT_FOUND, "Order not found", HttpStatus.NOT_FOUND);
            }
            orderService.cancelOrder(id);
            return APIResponse.get(ErrorCode.SUCCESS, "Order cancelled successfully", HttpStatus.OK);
        } catch (Exception e) {
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR, "Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper> deleteOrder(@PathVariable Long id) {
        try {
            boolean exists = orderService.existsById(id);
            if (!exists) {
                return APIResponse.get(ErrorCode.ORDER_NOT_FOUND, "Order not found", HttpStatus.NOT_FOUND);
            }
            orderService.deleteOrder(id);
            return APIResponse.get(ErrorCode.SUCCESS, "Order deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR, "Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}