package com.eliascanalesnieto.demo.repository;

import com.eliascanalesnieto.demo.dto.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
@Repository
public class ProductRestRepository implements ProductRepository {

    public static final String GET_PRODUCT = "/{id}";
    public static final String GET_SIMILAR_IDS = "/{id}/similarids";

    private final RestTemplate restTemplate;

    @Override
    public CompletableFuture<String[]> getProductSimilarIds(final String id) {
        return CompletableFuture.supplyAsync(() ->
                getSimilarIdsRequest(id)
        ).handle((ids, throwable) -> ids != null ? ids : null);
    }

    @Override
    public CompletableFuture<Product> getProduct(final String id) {
        return CompletableFuture.supplyAsync(() ->
                getProductRequest(id)
        ).handle((product, throwable) -> product != null ? product : null);
    }

    private Product getProductRequest(final String id) {
        return restTemplate.getForObject(GET_PRODUCT, Product.class, Map.of("id", id));
    }

    private String[] getSimilarIdsRequest(final String id) {
        return restTemplate.getForObject(GET_SIMILAR_IDS, String[].class, Map.of("id", id));
    }

}
