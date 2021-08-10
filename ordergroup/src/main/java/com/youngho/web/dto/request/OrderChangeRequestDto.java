package com.youngho.web.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderChangeRequestDto {
    private List<Integer> productIds;
    private String description;

    @Builder
    public OrderChangeRequestDto(String description, List<Integer> productIds) {
        this.description = description;
        if(productIds != null)
            this.productIds = productIds;
    }
}
