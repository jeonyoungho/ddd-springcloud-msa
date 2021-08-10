package com.youngho.web.dto.response;

import com.youngho.domain.lineitem.LineItem;
import com.youngho.domain.ordergroup.OrderGroup;
import com.youngho.domain.product.Product;
import lombok.Getter;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class OrderGroupListResponseDto {
    private Long id;
    private String description;
    private boolean status;
    private List<Product> products;

    public OrderGroupListResponseDto(OrderGroup entity) {
        this.id = entity.getId();
        this.description = entity.getDescription();
        this.status = entity.getStatus();
        this.products = entity.getLineItems()
                .stream()
                .map(LineItem::getProduct)
                .collect(Collectors.toList());
    }

}
