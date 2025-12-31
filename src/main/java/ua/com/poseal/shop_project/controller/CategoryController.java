package ua.com.poseal.shop_project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.poseal.shop_project.dto.CategoryRequest;
import ua.com.poseal.shop_project.model.Category;
import ua.com.poseal.shop_project.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@Tag(name = "Category", description = "Product category management")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @Operation(summary = "Create new category")
    public ResponseEntity<Category> create(@Valid @RequestBody CategoryRequest request) {
        Category category = Category.builder()
                .name(request.name())
                .description(request.description())
                .build();
        return new ResponseEntity<>(categoryService.createCategory(category), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all categories")
    public List<Category> getAll(){
        return categoryService.getAllCategories();
    }
}
