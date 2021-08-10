package com.youngho.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.youngho.domain.ProductRepository;
import com.youngho.domain.Product;
import com.youngho.web.dto.request.ProductAddRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProductRepository productRepository;

    @AfterEach
    public void cleanup() throws Exception {
        productRepository.deleteAll();
    }

    @Test
    public void 단일_Product_등록_테스트() throws Exception {
        //given
        String name = "SampleProduct";
        int price = 1000;
        String description = "SampleDescription";

        // when
        ProductAddRequestDto requestDto = ProductAddRequestDto.builder()
                .name(name)
                .price(price)
                .description(description)
                .build();

        //when
        String url = "http://localhost:" + port + "/api/v1/products";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andReturn();

        // then
        Product product = productRepository.findAll().get(0);

        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(201);
        assertThat(product.getName()).isEqualTo(name);
        assertThat(product.getPrice()).isEqualTo(price);
        assertThat(product.getDescription()).isEqualTo(description);
    }

    @Test
    public void id로_Product_조회_테스트() throws Exception {
        String name = "macbook";
        String description = "best product!";
        int price = 100;
        Product product = productRepository.save(Product.builder()
                .name(name)
                .description(description)
                .price(price)
                .build());
        String url = "http://localhost:" + port + "/api/v1/products/" + product.getId();

        mvc.perform(MockMvcRequestBuilders.get(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.price", is(price)))
                .andExpect(jsonPath("$.description", is(description)));
    }


    @Test
    public void 전체_Product_조회_테스트() throws Exception {
        //given
        int total = 10;
        for(int i=0;i<total;i++) {
            productRepository.save(Product.builder()
                    .name("macbook")
                    .description("best product!")
                    .price(100)
                    .build());
        }

        String url = "http://localhost:" + port + "/api/v1/products";

        //when
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(url)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        //  String content = mvcResult.getResponse().getContentAsString();
        //  ProductListResponseDto[] responseDtos = new ObjectMapper().readValue(content, ProductListResponseDto[].class);

        //then
        assertThat(status).isEqualTo(200);
        // assertThat(responseDtos.length).isEqualTo(total);
    }

}
