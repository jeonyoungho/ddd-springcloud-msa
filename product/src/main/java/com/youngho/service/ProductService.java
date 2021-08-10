package com.youngho.service;

import com.youngho.domain.Product;
import com.youngho.domain.ProductRepository;
import com.youngho.web.dto.request.ProductAddRequestDto;
import com.youngho.web.dto.request.ProductUpdateRequestDto;
import com.youngho.web.dto.response.ProductListResponseDto;
import com.youngho.web.dto.response.ProductResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public Long addProduct(ProductAddRequestDto requestDto) {
        return productRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional(readOnly = true)
    public ProductResponseDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Could not found product with id " + id));

        return new ProductResponseDto(product);
    }

    @Transactional(readOnly = true)
    public Page<ProductListResponseDto> getProductsByPagination(Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber()-1, pageable.getPageSize());
        return productRepository.findAll(pageable)
                .map(ProductListResponseDto::new);
    }

    @Transactional(readOnly = true)
    public List<ProductListResponseDto> getProductsByIds(List<Long> ids) {
        return productRepository.findByIdIn(ids)
                .stream()
                .map(ProductListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long updateProduct(Long id, ProductUpdateRequestDto requestDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Could not found product with id " + id));

        product.update(requestDto.getName(), requestDto.getPrice(), requestDto.getDescription());

        return id;
    }

    @Transactional
    public Long deleteProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Could not found product with id " + id));

        productRepository.delete(product);
        return id;
    }


}
