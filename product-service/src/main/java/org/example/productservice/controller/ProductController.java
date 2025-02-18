package org.example.productservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.productservice.model.dto.ProductCreateDTO;
import org.example.productservice.model.dto.ProductDTO;
import org.example.productservice.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getDetail(@PathVariable("id") Long productId) {
        ProductDTO productDTO = productService.findById(productId);
        return ResponseEntity.ok(productDTO);
    }

    @PostMapping()
    public ResponseEntity<ProductDTO> create(@RequestBody @Valid ProductCreateDTO productCreateDTO) {
        ProductDTO productDTO = productService.create(productCreateDTO);
        return ResponseEntity.ok(productDTO);
    }
}
