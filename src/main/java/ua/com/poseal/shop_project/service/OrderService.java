package ua.com.poseal.shop_project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.poseal.shop_project.dto.OrderItemRequest;
import ua.com.poseal.shop_project.dto.OrderRequest;
import ua.com.poseal.shop_project.exception.BusinessLogicException;
import ua.com.poseal.shop_project.exception.ResourceNotFoundException;
import ua.com.poseal.shop_project.model.Order;
import ua.com.poseal.shop_project.model.OrderItem;
import ua.com.poseal.shop_project.model.Product;
import ua.com.poseal.shop_project.model.User;
import ua.com.poseal.shop_project.model.enums.OrderStatus;
import ua.com.poseal.shop_project.repository.OrderRepository;
import ua.com.poseal.shop_project.repository.ProductRepository;
import ua.com.poseal.shop_project.repository.UserRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Transactional
    public Order createOrder(OrderRequest request, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (OrderItemRequest itemDto : request.items()) {
            Product product = productRepository.findById(itemDto.productId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Product not found: " + itemDto.productId()));
            if (product.getStockQuantity() < itemDto.quantity()) {
                throw new BusinessLogicException("Not enough goods in stock: " + product.getName());
            }

            product.setStockQuantity(product.getStockQuantity() - itemDto.quantity());

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(itemDto.quantity());
            orderItem.setPriceAtPurchase(product.getPrice());

            orderItems.add(orderItem);

            BigDecimal itemTotal = product.getPrice().multiply(BigDecimal.valueOf(itemDto.quantity()));
            totalAmount = totalAmount.add(itemTotal);
        }

        order.setItems(orderItems);
        order.setTotalAmount(totalAmount);

        return orderRepository.save(order);
    }

    public Page<Order> getUserOrders(String username, Pageable pageable) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return orderRepository.findAllByUserOrderByOrderDateDesc(user, pageable);
    }
}
