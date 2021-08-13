package com.youngho.service;

import com.youngho.domain.lineitem.LineItem;
import com.youngho.domain.lineitem.LineItemRepository;
import com.youngho.domain.ordergroup.OrderGroup;
import com.youngho.domain.ordergroup.OrderGroupRepository;
import com.youngho.domain.product.Product;
import com.youngho.web.dto.request.OrderChangeRequestDto;
import com.youngho.web.dto.request.OrderGroupAddRequestDto;
import com.youngho.web.dto.response.OrderGroupListResponseDto;
import com.youngho.web.dto.response.OrderGroupResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderGroupService {
    private final String productApiUrl = "http://localhost:8081/api/v1";
    private final OrderGroupRepository orderGroupRepository;
    private final LineItemService lineItemService;
    private final RestTemplate restTemplate;

    @Transactional
    public Long orderProduct(OrderGroupAddRequestDto requestDto) {
        List<Product> products = getProductsWithRest(requestDto.getProductIds());

        OrderGroup orderGroup = orderGroupRepository.save(OrderGroup.builder()
                .description(requestDto.getDescription())
                .build());

        lineItemService.addLineItems(orderGroup, products);

        return orderGroup.getId();
    }

    @Transactional(readOnly = true)
    public List<OrderGroupListResponseDto> getOrders() {
        return orderGroupRepository.findAllWithProducts()
                .stream()
                .map(OrderGroupListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public OrderGroupResponseDto getOrderById(Long id) {
        return new OrderGroupResponseDto(orderGroupRepository.findByIdWithProducts(id));
    }

    @Transactional
    public Long changeOrder(Long id, OrderChangeRequestDto requestDto) {
        OrderGroup orderGroup = orderGroupRepository.findByIdWithProducts(id);
        List<Product> newProducts = getProductsWithRest(requestDto.getProductIds());
        Set<LineItem> newItems = new LinkedHashSet<>();
        for(Product product:newProducts) {
            newItems.add(LineItem.builder()
                    .product(product)
                    .orderGroup(orderGroup)
                    .build()
            );
        }

        lineItemService.deleteLineItems(orderGroup.getLineItems());
        orderGroup.updateLineItems(newItems);

        return id;
    }

    @Transactional
    public Long deleteOrderById(Long id) {
        OrderGroup entity = orderGroupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Could not found order with id " + id));

        orderGroupRepository.delete(entity);
        return id;
    }

    private List<Product> getProductsWithRest(List<Integer> productIds) {
        String ids = String.join(",", productIds
                .stream()
                .map(String::valueOf)
                .collect(Collectors.toList()));
        String url = productApiUrl + "/products/ids?productIds=" + ids;

        return Arrays.asList(restTemplate.getForObject(url, Product[].class));
    }

}
