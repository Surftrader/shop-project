package ua.com.poseal.shop_project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.poseal.shop_project.dto.RegisterRequest;
import ua.com.poseal.shop_project.exception.BusinessLogicException;
import ua.com.poseal.shop_project.model.Role;
import ua.com.poseal.shop_project.model.User;
import ua.com.poseal.shop_project.repository.RoleRepository;
import ua.com.poseal.shop_project.repository.UserRepository;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void register(RegisterRequest request) {
        if (userRepository.findByUsername(request.username()).isPresent()) {
            throw new BusinessLogicException("User with that name already exists.");
        }
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new BusinessLogicException("Role 'ROLE_USER' not found in the database"));

        User user = User.builder()
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .roles(Set.of(userRole))
                .build();

        userRepository.save(user);
    }
}
