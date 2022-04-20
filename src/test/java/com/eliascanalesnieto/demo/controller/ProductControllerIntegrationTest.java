package com.eliascanalesnieto.demo.controller;

import com.eliascanalesnieto.demo.dto.ProductSimilar;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.FileUrlResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.Map;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerIntegrationTest {

    private MockRestServiceServer mockServer;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Value("${product-api-host}")
    private String productApiHost;

    @BeforeEach
    public void init(){
        mockServer = MockRestServiceServer.bindTo(restTemplate).ignoreExpectOrder(true).build();
    }

    @Test
    public void getSimilarProducts() throws MalformedURLException, FileNotFoundException {

        mockServer.expect(ExpectedCount.manyTimes(),
                requestTo(URI.create(productApiHost + "/5/similarids")))
            .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("[\"1\", \"2\"]")
                );

        mockServer.expect(requestTo(URI.create(productApiHost + "/1")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(new FileUrlResource(ResourceUtils.getFile(
                                "classpath:product-similar-1.json").toURI().toURL()))
                );

        mockServer.expect(requestTo(URI.create(productApiHost + "/2")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(new FileUrlResource(ResourceUtils.getFile(
                                "classpath:product-similar-2.json").toURI().toURL()))
                );

        ProductSimilar[] response = testRestTemplate.getForObject(
                "/product/{id}/similar", ProductSimilar[].class, Map.of("id", 5));

        Assertions.assertArrayEquals(
                new ProductSimilar[]{
                        new ProductSimilar(1, "name", 12.32d, true),
                        new ProductSimilar(2, "name-2", 599d, false)
                },
                response
        );
    }

}
