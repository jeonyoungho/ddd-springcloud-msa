package com.youngho.domain;

import com.youngho.domain.Product;
import com.youngho.domain.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @AfterEach
    public void cleanup() {
        productRepository.deleteAll();
    }

    @Test
    public void Product_등록_테스트() throws Exception {
        //given
        String name = "SampleProduct";
        int price = 1000;
        String description = "SampleDescription";

        // when
        Long id = productRepository.save(Product.builder()
                .name(name)
                .price(price)
                .description(description)
                .build()).getId();

        // then
        Product product = productRepository.findById(id).get();

        assertThat(product.getId()).isEqualTo(id);
        assertThat(product.getName()).isEqualTo(name);
        assertThat(product.getPrice()).isEqualTo(price);
        assertThat(product.getDescription()).isEqualTo(description);
    }

    @ParameterizedTest
    @CsvSource({"5, 3", "2, 2", "3, 2"})
    public void Product_페이징_조회_테스트(int page, int size) throws Exception {
        //given
        int total = 10;
        for(int i=1;i<=total;i++) {
            productRepository.save(Product.builder()
                    .name("macbook"+i)
                    .description("best product!")
                    .price(100)
                    .build());
        }

        // when
        Page<Product> products = productRepository.findAll(PageRequest.of(page, size));

        // then
        int expectedTotalPages = (int) Math.ceil((double) total/(double) size);
        assertThat(products.getTotalPages()).isEqualTo(expectedTotalPages);
        assertThat(products.getNumber()).isEqualTo(page);

        if(expectedTotalPages >= page)
            assertThat(products.getNumberOfElements()).isEqualTo(size);
    }

    @Test
    public void id리스트로_Product조회_테스트() {
        //given
        int total = 10;
        List<Long> ids = new ArrayList<>();
        for(int i=1;i<=total;i++) {
            productRepository.save(Product.builder()
                    .name("macbook"+i)
                    .description("best product!")
                    .price(100)
                    .build());
        }

        for(int i=1;i<=3;i++) {
            ids.add(Long.valueOf(i));
        }

        // when
        List<Product> products = productRepository.findByIdIn(ids);

        // then
        assertThat(products.get(0).getId()).isEqualTo(1L);
        assertThat(products.get(1).getId()).isEqualTo(2L);
        assertThat(products.get(2).getId()).isEqualTo(3L);

    }

}
