package ua.com.poseal.shop_project.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ua.com.poseal.shop_project.dto.OrderItemResponse;
import ua.com.poseal.shop_project.dto.OrderResponse;
import ua.com.poseal.shop_project.model.Order;
import ua.com.poseal.shop_project.model.OrderItem;

@Mapper(componentModel = "string")
public interface OrderMapper {

    @Mapping(source = "user.username", target = "customerUsername")
    OrderResponse toResponse(Order order);

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    OrderItemResponse toItemResponse(OrderItem orderItem);
}
