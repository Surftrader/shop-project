package ua.com.poseal.shop_project.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record OrderItemRequest(
        @NotNull
        Long productId,
        @Min(value = 1, message = "The number must be at least 1")
        Integer quantity
) { }
