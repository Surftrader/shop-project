package ua.com.poseal.shop_project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.poseal.shop_project.dto.ProductCreateRequest;
import ua.com.poseal.shop_project.exception.BusinessLogicException;
import ua.com.poseal.shop_project.exception.ResourceNotFoundException;
import ua.com.poseal.shop_project.model.Category;
import ua.com.poseal.shop_project.model.Product;
import ua.com.poseal.shop_project.model.enums.ProductStatus;
import ua.com.poseal.shop_project.repository.CategoryRepository;
import ua.com.poseal.shop_project.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public Product addProduct(ProductCreateRequest request) {
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Category with ID " + request.categoryId() + " not found")
                );

        if (request.price().compareTo(BigDecimal.valueOf(1000000)) > 0) {
            throw new BusinessLogicException("The price is too high for this type of product.");
        }

        Product product = Product.builder()
                .name(request.name())
                .description(request.description())
                .price(request.price())
                .stockQuantity(request.stockQuantity())
                .status(ProductStatus.AVAILABLE)
                .category(category)
                .build();

        return productRepository.save(product);
    }



    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    public List<Product> searchProducts(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

}
