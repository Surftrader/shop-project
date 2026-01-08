package ua.com.poseal.shop_project.dto;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record OrderRequest(
     @NotEmpty(message = "Order cannot be empty")
     List<OrderItemRequest> items
) { }
