package com.eliascanalesnieto.demo.repository;

import com.eliascanalesnieto.demo.dto.Product;

import java.util.concurrent.CompletableFuture;

public interface ProductRepository {

    CompletableFuture<String[]> getProductSimilarIds(final String id);

    CompletableFuture<Product> getProduct(final String id);

}
