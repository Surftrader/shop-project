package ua.com.poseal.shop_project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.poseal.shop_project.dto.ProductCreateRequest;
import ua.com.poseal.shop_project.model.Product;
import ua.com.poseal.shop_project.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@Tag(name = "Products", description = "Operations with store products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @Operation(summary = "Add new products")
    public ResponseEntity<Product> addProduct(@Valid @RequestBody ProductCreateRequest request) {
        return new ResponseEntity<>(productService.addProduct(request), HttpStatus.CREATED);
    }

    @GetMapping("/category/{categoryId}")
    @Operation(summary = "Find products by category ID")
    public List<Product> getByCategory(@PathVariable Long categoryId) {
        return productService.getProductsByCategory(categoryId);
    }

    @GetMapping("/search")
    @Operation(summary = "Search products by name")
    public List<Product> search(@RequestParam String name) {
        return productService.searchProducts(name);
    }

}
