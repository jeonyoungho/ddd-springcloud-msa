package com.youngho.domain.ordergroup;

import com.youngho.domain.lineitem.LineItem;
import com.youngho.domain.product.Product;
import com.youngho.domain.product.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.LinkedHashSet;
import java.util.Set;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class OrderGroupRepositoryTest {

    @Autowired
    OrderGroupRepository orderGroupRepository;

    @Autowired
    ProductRepository productRepository;

    @Test
    public void OrderGroup_등록_테스트() {

        Product product = productRepository.save(Product.builder()
                .name("macbook pro")
                .description("best macbook")
                .price(100)
                .build());

        Set<LineItem> lineItems = new LinkedHashSet<>();
        lineItems.add(LineItem.builder()
                .product(product).build());

        OrderGroup orderGroup = OrderGroup.builder()
                .description("order_description1")
                .lineItems(lineItems)
                .build();

        orderGroupRepository.save(orderGroup);

    }
}
