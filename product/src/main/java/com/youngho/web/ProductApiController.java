package com.youngho.web;

import com.youngho.service.ProductService;
import com.youngho.web.dto.request.ProductAddRequestDto;
import com.youngho.web.dto.request.ProductUpdateRequestDto;
import com.youngho.web.dto.response.ProductListResponseDto;
import com.youngho.web.dto.response.ProductResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "${product.api}", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductApiController {

    private final ProductService productService;

    @PostMapping("/products")
    public ResponseEntity<Long> addProduct(@RequestBody ProductAddRequestDto requestDto) {
        return new ResponseEntity<>(productService.addProduct(requestDto), HttpStatus.CREATED);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable Long id) {
        return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
    }

    @GetMapping("/products")
    public ResponseEntity<Page<ProductListResponseDto>> getProductsByPagination(Pageable pageable) {
        return new ResponseEntity<>(productService.getProductsByPagination(pageable), HttpStatus.OK);
    }

    @GetMapping("/products/ids")
    public ResponseEntity<List<ProductListResponseDto>> getProductsByIds(@RequestParam("productIds") String ids) {
        List<Long> productIds = new ArrayList<>(Arrays.asList(ids.split(",")))
                .stream()
                .map(Long::parseLong)
                .collect(Collectors.toList());

        return new ResponseEntity<>(productService.getProductsByIds(productIds), HttpStatus.OK);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Long> updateProduct(@PathVariable Long id, @RequestBody ProductUpdateRequestDto requestDto) {
        return new ResponseEntity<>(productService.updateProduct(id, requestDto), HttpStatus.OK);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Long> deleteProductById(@PathVariable Long id) {
        return new ResponseEntity<>(productService.deleteProductById(id), HttpStatus.OK);
    }
}
