package com.youngho;

import com.youngho.domain.Product;
import com.youngho.domain.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@RequiredArgsConstructor
@SpringBootApplication
public class ProductApplication implements CommandLineRunner {

    private final ProductRepository productRepository;

    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        for(int i=1;i<=10;i++) {
            productRepository.save(Product.builder().
                    name("product"+i)
                    .price(i*100)
                    .description("product" + i + " is best product!")
                    .build());
        }
    }
}
