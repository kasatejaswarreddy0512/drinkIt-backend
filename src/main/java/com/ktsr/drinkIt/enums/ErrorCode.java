package com.ktsr.drinkIt.enums;

import lombok.Getter;

@Getter
public enum ErrorCode {

    SUCCESS("0", "Your request was successful."),
    PARTIAL_SUCCESS("2", "Your request was partially successful."),
    COUNT_SUCCESS("1", "Your request was successful."),

    INVALID_INPUT("ERR001", "Invalid input provided."),
    USER_NOT_FOUND("ERR002", "User not found."),
    USER_ALREADY_EXISTS("ERR009", "User already exists."),
    USERNAME_ALREADY_EXISTS("ERR009", "User name already exists."),
    INTERNAL_SERVER_ERROR("ERR500", "An internal server error occurred."),
    FORBIDDEN("ERR501", "Access is forbidden."),
    UNAUTHORIZED("ERR502", "Unauthorized access."),

    ADDRESS_NOT_FOUND("ERR503", "Address not found."),
    CATEGORY_NOT_FOUND("ERR504", "Category not found."),
    BRAND_NOT_FOUND("ERR505", "Brand not found."),
    PRODUCT_NOT_FOUND("ERR506", "Product not found."),
    PRODUCT_VARIANT_NOT_FOUND("ERR507", "Product variant not found."),
    PRODUCT_IMAGE_NOT_FOUND("ERR508", "Product image not found."),
    CART_ITEM_NOT_FOUND("ERR509", "Cart item not found."),
    COUPON_NOT_FOUND("ERR510", "Coupon not found."),
    ORDER_NOT_FOUND("ERR511", "Order not found."),
    PAYMENT_NOT_FOUND("ERR512", "Payment not found."),
    ORDER_ITEM_NOT_FOUND("ERR513", "Order item not found."),
    NOTIFICATION_NOT_FOUND("ERR514", "Notification not found."),
    INVOICE_NOT_FOUND("ERR515", "Invoice not found."),
    REFUND_NOT_FOUND("ERR516", "Refund not found."),
    ;



    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
