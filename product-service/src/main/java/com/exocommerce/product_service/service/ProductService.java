package com.exocommerce.product_service.service;

import com.exocommerce.product_service.dto.ProductCartDto;
import com.exocommerce.product_service.dto.ProductDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    ProductDto createProduct(ProductDto dto, MultipartFile image);

    ProductDto getProductById(Long id);
    List<ProductDto> getAllProducts();
    ProductDto updateProduct(Long id, ProductDto dto, MultipartFile image);
    void deleteProduct(Long id);
    ProductCartDto getCartProductById(Long id);

}
