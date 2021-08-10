package com.youngho.web.dto.response;

import com.youngho.domain.Product;
import lombok.Getter;


@Getter
public class ProductListResponseDto {
    private Long id;
    private String name;
    private int price;
    private String description;

    public ProductListResponseDto(Product entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.price = entity.getPrice();
        this.description = entity.getDescription();
    }
}
