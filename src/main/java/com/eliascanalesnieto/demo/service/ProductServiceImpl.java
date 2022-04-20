package com.eliascanalesnieto.demo.service;

import com.eliascanalesnieto.demo.dto.ProductSimilar;
import com.eliascanalesnieto.demo.mapper.ProductSimilarMapper;
import com.eliascanalesnieto.demo.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductSimilarMapper productSimilarMapper;

    @Override
    @Async
    public CompletableFuture<List<ProductSimilar>> getProductSimilars(final String id) {
        return productRepository.getProductSimilarIds(id)
                .thenApplyAsync(result ->
                        Arrays.asList(ArrayUtils.nullToEmpty(result)).stream()
                        .filter(productSimilarId -> StringUtils.isNotBlank(productSimilarId))
                        .map(productRepository::getProduct)
                        .map(CompletableFuture::join)
                        .filter(product -> Objects.nonNull(product))
                        .map(productSimilarMapper::map)
                        .collect(Collectors.toList())
                );
    }

}
