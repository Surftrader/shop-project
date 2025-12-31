package ua.com.poseal.shop_project.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductCreateRequest(
        @NotBlank(message = "Product name cannot be empty")
        @Size(min = 2, max = 100, message = "The name must be between 2 and 100 characters.")
        String name,

        @Size(max = 1000, message = "The description must not exceed 1000 characters.")
        String description,

        @NotNull(message = "Price is mandatory")
        @DecimalMin(value = "0.01", message = "The price must be greater than zero.")
        BigDecimal price,

        @Min(value = 0, message = "The quantity in stock cannot be negative.")
        Integer stockQuantity,

        @NotNull(message = "Category ID is required")
        Long categoryId
) { }
