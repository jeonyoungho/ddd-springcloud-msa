package com.youngho.web.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductUpdateRequestDto {
    private String name;
    private int price;
    private String description;

    @Builder
    public ProductUpdateRequestDto(String name, int price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

}
