package com.eliascanalesnieto.demo.service;

import com.eliascanalesnieto.demo.dto.ProductSimilar;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ProductService {

    CompletableFuture<List<ProductSimilar>> getProductSimilars(final String id);

}
