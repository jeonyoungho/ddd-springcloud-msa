package com.youngho.service;

import com.youngho.domain.lineitem.LineItem;
import com.youngho.domain.lineitem.LineItemRepository;
import com.youngho.domain.ordergroup.OrderGroup;
import com.youngho.domain.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class LineItemService {

    private final LineItemRepository lineItemRepository;

    @Transactional
    public void addLineItems(OrderGroup orderGroup, List<Product> products) {
        List<LineItem> lineItems = new ArrayList<>();
        int size = products.size();
        for(int i=0;i<size;i++) {
            lineItems.add(LineItem.builder()
                    .product(products.get(i))
                    .orderGroup(orderGroup)
                    .build());
        }

        lineItemRepository.saveAll(lineItems);
    }

}
