package ua.com.poseal.shop_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.poseal.shop_project.model.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
