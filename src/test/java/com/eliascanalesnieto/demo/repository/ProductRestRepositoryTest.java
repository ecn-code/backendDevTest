package com.eliascanalesnieto.demo.repository;

import com.eliascanalesnieto.demo.dto.Product;
import com.eliascanalesnieto.demo.mapper.ProductSimilarMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.concurrent.ExecutionException;

@ExtendWith(MockitoExtension.class)
public class ProductRestRepositoryTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ProductRestRepository productRestRepository;

    @Test
    public void getSimilarIdsTest() throws ExecutionException, InterruptedException {
        Mockito.when(restTemplate.getForObject("/{id}/similarids", String[].class, Map.of("id", "5")))
                .thenReturn(new String[]{"1", "2", "3"});

        Assertions.assertArrayEquals(
                new String[]{"1", "2", "3"},
                productRestRepository.getProductSimilarIds("5").get()
        );
    }

}
