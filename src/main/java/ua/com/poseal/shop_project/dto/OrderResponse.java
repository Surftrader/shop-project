package ua.com.poseal.shop_project.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
      Long id,
      LocalDateTime orderDate,
      String status,
      BigDecimal totalAmount,
      String customerUsername,
      List<OrderItemResponse> items
) { }
