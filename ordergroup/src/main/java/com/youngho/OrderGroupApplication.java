package com.youngho;

import com.youngho.domain.lineitem.LineItem;
import com.youngho.domain.ordergroup.OrderGroup;
import com.youngho.domain.ordergroup.OrderGroupRepository;
import com.youngho.domain.product.Product;
import com.youngho.domain.product.ProductRepository;
import com.youngho.service.OrderGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class OrderGroupApplication implements CommandLineRunner {

    @Autowired
    OrderGroupService orderGroupService;

    @Autowired
    ProductRepository productRepository;

    public static void main(String[] args) {
        SpringApplication.run(OrderGroupApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        List<Product> products = new ArrayList<>();
//        for(int i=0;i<10;i++) {
//            products.add(productRepository.save(Product.builder()
//                    .name("macbook pro"+i)
//                    .description("best macbook!")
//                    .price(100)
//                    .build()));
//        }
//        orderGroupService.orderGroup(products);
    }
}
