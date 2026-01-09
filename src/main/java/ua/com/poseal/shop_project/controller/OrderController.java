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
import ua.com.poseal.shop_project.mapper.OrderMapper;
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
    private final OrderMapper orderMapper;

    @PostMapping
    @Operation(summary = "Place a new order")
    public ResponseEntity<OrderResponse> placeOrder(
            @Valid @RequestBody OrderRequest request,
            Principal principal) {
        Order order = orderService.createOrder(request, principal.getName());
        return new ResponseEntity<>(orderMapper.toResponse(order), HttpStatus.CREATED);
    }

    @GetMapping("/my")
    @Operation(summary = "Get my order history")
    public ResponseEntity<Page<OrderResponse>> getMyOrders(
            @ParameterObject Pageable pageable,
            Principal principal) {
        Page<Order> orders = orderService.getUserOrders(principal.getName(), pageable);
        Page<OrderResponse> responsePage = orders.map(orderMapper::toResponse);
        if (orders.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(responsePage);
    }
}
