package ua.com.poseal.shop_project.dto;

import java.math.BigDecimal;

public record OrderItemResponse(
        Long productId,
        String productName,
        BigDecimal priceAtPurchase,
        Integer quantity
) { }
