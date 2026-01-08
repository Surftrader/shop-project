package ua.com.poseal.shop_project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.poseal.shop_project.model.Order;
import ua.com.poseal.shop_project.model.User;
import ua.com.poseal.shop_project.model.enums.OrderStatus;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Page<Order> findAllByUserOrderByOrderDateDesc(User user, Pageable pageable);

    List<Order> findAllByStatus(OrderStatus status);
}
