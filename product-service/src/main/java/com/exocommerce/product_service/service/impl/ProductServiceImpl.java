package com.exocommerce.product_service.service.impl;

import com.exocommerce.product_service.dto.ProductDto;
import com.exocommerce.product_service.entity.Product;
import com.exocommerce.product_service.exception.ResourceNotFoundException;
import com.exocommerce.product_service.repository.ProductRepository;
import com.exocommerce.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
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
                .imageData(p.getImageData() != null ? new String(p.getImageData()) : null)
                .build();
    }

    private Product toEntity(ProductDto dto) {
        return Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .imageUrl(dto.getImageUrl())
                .imageData(dto.getImageData() != null ? dto.getImageData().getBytes() : null)
                .build();
    }

    @Override
    public ProductDto createProduct(ProductDto dto, MultipartFile image) {
        Product product = toEntity(dto);
        if (image != null && !image.isEmpty()) {
            try {
                product.setImageData(image.getBytes());
                product.setImageUrl(image.getOriginalFilename());
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
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll()
                .stream().map(this::toDto).toList();
    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto dto, MultipartFile image) {
        Product p = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        p.setName(dto.getName());
        p.setDescription(dto.getDescription());
        p.setPrice(dto.getPrice());
        p.setStock(dto.getStock());

        if (image != null && !image.isEmpty()) {
            try {
                p.setImageData(image.getBytes());
                p.setImageUrl(image.getOriginalFilename());
            } catch (Exception e) {
                throw new RuntimeException("Failed to process image", e);
            }
        } else if (dto.getImageUrl() != null) {
            p.setImageUrl(dto.getImageUrl());
            // If imageUrl is provided but no new image, keep the existing imageData
            if (dto.getImageData() == null) {
                p.setImageData(null);
            } else {
                p.setImageData(dto.getImageData().getBytes());
            }
        } else {
            // If no image or imageUrl is provided, clear both
            p.setImageData(null);
            p.setImageUrl(null);
        }

        return toDto(productRepository.save(p));
    }


    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id))
            throw new ResourceNotFoundException("Product not found");

        productRepository.deleteById(id);
    }
}
