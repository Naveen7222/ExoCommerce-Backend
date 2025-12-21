package com.exocommerce.product_service.service.impl;

import com.exocommerce.product_service.dto.ProductCartDto;
import com.exocommerce.product_service.dto.ProductDto;
import com.exocommerce.product_service.entity.Category;
import com.exocommerce.product_service.entity.Product;
import com.exocommerce.product_service.exception.ResourceNotFoundException;
import com.exocommerce.product_service.repository.CategoryRepository;
import com.exocommerce.product_service.repository.ProductRepository;
import com.exocommerce.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    /* ===================== MAPPERS ===================== */

    private ProductDto toDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .imageBase64(
                        product.getImageData() != null
                                ? Base64.getEncoder().encodeToString(product.getImageData())
                                : null
                )
                .categoryId(
                        product.getCategory() != null
                                ? product.getCategory().getId()
                                : null
                )
                .categoryName(
                        product.getCategory() != null
                                ? product.getCategory().getName()
                                : null
                )
                .build();
    }

    private Product toEntity(ProductDto dto) {
        Product product = Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .build();

        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() ->
                            new RuntimeException("Category not found with id: " + dto.getCategoryId()));
            product.setCategory(category);
        }

        return product;
    }

    /* ===================== CRUD ===================== */

    @Override
    public ProductDto createProduct(ProductDto dto, MultipartFile image) {
        Product product = toEntity(dto);

        if (image != null && !image.isEmpty()) {
            try {
                product.setImageData(image.getBytes());
            } catch (Exception e) {
                throw new RuntimeException("Failed to process image", e);
            }
        }

        return toDto(productRepository.save(product));
    }

    @Override
    public ProductDto getProductById(Long id) {
        return productRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found"));
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto dto, MultipartFile image) {
        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found"));

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());

        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() ->
                            new RuntimeException("Category not found with id: " + dto.getCategoryId()));
            product.setCategory(category);
        }

        if (image != null && !image.isEmpty()) {
            try {
                product.setImageData(image.getBytes());
            } catch (Exception e) {
                throw new RuntimeException("Failed to process image", e);
            }
        }
        // else → keep existing imageData

        return toDto(productRepository.save(product));
    }

    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found");
        }
        productRepository.deleteById(id);
    }
    @Override
    public ProductCartDto getCartProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        return ProductCartDto.builder()
                .name(product.getName())
                .price(product.getPrice())
                .imageBase64(product.getImageData() != null
                        ? Base64.getEncoder().encodeToString(product.getImageData())
                        : null)
                .build();
    }

}
