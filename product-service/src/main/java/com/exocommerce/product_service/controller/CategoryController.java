package com.exocommerce.product_service.controller;

import com.exocommerce.product_service.dto.CategoryDto;
import com.exocommerce.product_service.entity.Category;
import com.exocommerce.product_service.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import jakarta.validation.Valid;



import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    private CategoryDto toDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    private Category toEntity(CategoryDto dto) {
        return Category.builder()
                .name(dto.getName())
                .build();
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CategoryDto dto) {
        // Check if category with same name (case-insensitive) already exists
        if (categoryRepository.findByNameIgnoreCase(dto.getName()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("A category with the name '" + dto.getName() + "' already exists");
        }
        
        // Create and save the new category
        Category category = toEntity(dto);
        try {
            Category saved = categoryRepository.save(category);
            return ResponseEntity.status(HttpStatus.CREATED).body(toDto(saved));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create category: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(categoryRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Category not found")));
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAll() {
        return ResponseEntity.ok(categoryRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody CategoryDto dto) {
        try {
            Category existing = categoryRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

            if (!existing.getName().equals(dto.getName()) &&
                    categoryRepository.existsByName(dto.getName())) {
                return ResponseEntity.badRequest().body("Category with this name already exists");
            }

            existing.setName(dto.getName());
            Category updated = categoryRepository.save(existing);
            return ResponseEntity.ok(toDto(updated));
        } catch (ResponseStatusException ex) {
            return ResponseEntity.status(ex.getStatusCode()).body(ex.getReason());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Category not found");
        }
        categoryRepository.deleteById(id);
        return ResponseEntity.ok("Category deleted");
    }
}
