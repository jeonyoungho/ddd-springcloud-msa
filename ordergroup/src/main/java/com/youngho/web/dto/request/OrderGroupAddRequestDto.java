package com.youngho.web.dto.request;

import com.youngho.domain.product.Product;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderGroupAddRequestDto {

    private List<Integer> productIds;
    private String description;

    @Builder
    public OrderGroupAddRequestDto(String description, List<Integer> productIds) {
        this.description = description;
        if(productIds != null)
            this.productIds = productIds;
    }

}
