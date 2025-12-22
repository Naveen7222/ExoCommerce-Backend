package com.exocommerce.product_service.controller;

import com.exocommerce.product_service.dto.ProductCartDto;
import com.exocommerce.product_service.dto.ProductDto;
import com.exocommerce.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /* ================= CREATE PRODUCT ================= */

    // 1️⃣ JSON ONLY (NO IMAGE)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDto> createJson(
            @RequestBody ProductDto dto) {

        return ResponseEntity.ok(
                productService.createProduct(dto, null)
        );
    }

    // 2️⃣ MULTIPART (WITH OR WITHOUT IMAGE)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductDto> createMultipart(
            @RequestPart("dto") ProductDto dto,
            @RequestPart(value = "image", required = false) MultipartFile image) {

        return ResponseEntity.ok(
                productService.createProduct(dto, image)
        );
    }

    /* ================= READ ================= */

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAll() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    /* ================= UPDATE ================= */

    // JSON update (no image)
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDto> updateJson(
            @PathVariable Long id,
            @RequestBody ProductDto dto) {

        return ResponseEntity.ok(
                productService.updateProduct(id, dto, null)
        );
    }

    // Multipart update (with image)
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductDto> updateMultipart(
            @PathVariable Long id,
            @RequestPart("dto") ProductDto dto,
            @RequestPart(value = "image", required = false) MultipartFile image) {

        return ResponseEntity.ok(
                productService.updateProduct(id, dto, image)
        );
    }

    /* ================= DELETE ================= */

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted");
    }

    @GetMapping("/cart-details/{id}")
    public ResponseEntity<ProductCartDto> getCartProduct(@PathVariable Long id) {
        ProductCartDto dto = productService.getCartProductById(id);
        return ResponseEntity.ok(dto);
    }

}
