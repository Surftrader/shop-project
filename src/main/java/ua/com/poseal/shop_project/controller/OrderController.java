package ua.com.poseal.shop_project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.poseal.shop_project.dto.OrderItemResponse;
import ua.com.poseal.shop_project.dto.OrderRequest;
import ua.com.poseal.shop_project.dto.OrderResponse;
import ua.com.poseal.shop_project.model.Order;
import ua.com.poseal.shop_project.service.OrderService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Tag(name = "Orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @Operation(summary = "Place a new order")
    public ResponseEntity<OrderResponse> placeOrder(
            @Valid @RequestBody OrderRequest request,
            Principal principal) {
        Order order = orderService.createOrder(request, principal.getName());
        return new ResponseEntity<>(convertToResponse(order), HttpStatus.CREATED);
    }

    @GetMapping("/my")
    @Operation(summary = "Get my order history")
    public ResponseEntity<Page<OrderResponse>> getMyOrders(
            @ParameterObject Pageable pageable,
            Principal principal) {
        Page<Order> orders = orderService.getUserOrders(principal.getName(), pageable);
        Page<OrderResponse> responsePage = orders.map(this::convertToResponse);
        if (orders.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(responsePage);
    }

    private OrderResponse convertToResponse(Order order) {
        List<OrderItemResponse> itemDtos = order.getItems().stream()
                .map(item -> new OrderItemResponse(
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getPriceAtPurchase(),
                        item.getQuantity()
                )).toList();

        return new OrderResponse(
                order.getId(),
                order.getOrderDate(),
                order.getStatus().name(),
                order.getTotalAmount(),
                order.getUser().getUsername(),
                itemDtos
        );
    }
}
