package com.exocommerce.product_service.service.impl;

import com.exocommerce.product_service.dto.ProductDto;
import com.exocommerce.product_service.entity.Product;
import com.exocommerce.product_service.exception.ResourceNotFoundException;
import com.exocommerce.product_service.repository.ProductRepository;
import com.exocommerce.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private ProductDto toDto(Product p) {
        return ProductDto.builder()
                .id(p.getId())
                .name(p.getName())
                .description(p.getDescription())
                .price(p.getPrice())
                .stock(p.getStock())
                .imageUrl(p.getImageUrl())
                .build();
    }

    private Product toEntity(ProductDto dto) {
        return Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .imageUrl(dto.getImageUrl())
                .build();
    }

    @Override
    public ProductDto createProduct(ProductDto dto) {
        Product product = toEntity(dto);
        return toDto(productRepository.save(product));
    }

    @Override
    public ProductDto getProductById(Long id) {
        return productRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll()
                .stream().map(this::toDto).toList();
    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto dto) {
        Product p = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        p.setName(dto.getName());
        p.setDescription(dto.getDescription());
        p.setPrice(dto.getPrice());
        p.setStock(dto.getStock());
        p.setImageUrl(dto.getImageUrl());

        return toDto(productRepository.save(p));
    }

    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id))
            throw new ResourceNotFoundException("Product not found");

        productRepository.deleteById(id);
    }
}
