package com.youngho.web.dto.request;

import com.youngho.domain.Product;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductAddRequestDto {
    private String name;
    private int price;
    private String description;

    @Builder
    public ProductAddRequestDto(String name, int price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public Product toEntity() {
        return Product.builder()
                .name(name)
                .price(price)
                .description(description)
                .build();
    }
}
