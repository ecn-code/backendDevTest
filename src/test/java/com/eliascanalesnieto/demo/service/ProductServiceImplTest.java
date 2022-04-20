package com.eliascanalesnieto.demo.service;

import com.eliascanalesnieto.demo.dto.Product;
import com.eliascanalesnieto.demo.dto.ProductSimilar;
import com.eliascanalesnieto.demo.mapper.ProductSimilarMapper;
import com.eliascanalesnieto.demo.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductSimilarMapper productSimilarMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    public void getProductSimilarsNull() throws ExecutionException, InterruptedException {
        Mockito.when(productRepository.getProductSimilarIds("1"))
                .thenReturn(CompletableFuture.supplyAsync(() -> new String[1]));

        assertTrue(productService.getProductSimilars("1").get().isEmpty());
    }

    @Test
    public void getProductSimilarsEmpty() throws ExecutionException, InterruptedException {
        Mockito.when(productRepository.getProductSimilarIds("1"))
                .thenReturn(CompletableFuture.supplyAsync(() -> new String[1]));

        assertTrue(productService.getProductSimilars("1").get().isEmpty());
    }

    @Test
    public void getProductSimilars() throws ExecutionException, InterruptedException {
        Mockito.when(productRepository.getProductSimilarIds("1"))
                .thenReturn(CompletableFuture.supplyAsync(() -> new String[]{"1", "2", "3"}));

        Mockito.when(productRepository.getProduct("1"))
                .thenReturn(CompletableFuture.supplyAsync(() -> new Product()));
        Mockito.when(productRepository.getProduct("2"))
                .thenReturn(CompletableFuture.supplyAsync(() -> new Product()));
        Mockito.when(productRepository.getProduct("3"))
                .thenReturn(CompletableFuture.supplyAsync(() -> new Product()));

        Mockito.when(productSimilarMapper.map(any(Product.class)))
                .thenReturn(new ProductSimilar());

        assertEquals(
                List.of(new ProductSimilar(), new ProductSimilar(), new ProductSimilar()),
                productService.getProductSimilars("1").get()
        );
    }

}
