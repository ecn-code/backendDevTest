package com.eliascanalesnieto.demo.controller;

import com.eliascanalesnieto.demo.dto.ProductSimilar;
import com.eliascanalesnieto.demo.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping(path="/{productId}/similar", produces = "application/json")
    public List<ProductSimilar> getProductSimilars(@PathVariable final String productId) throws ExecutionException, InterruptedException {

        final List<ProductSimilar> productSimilars = productService
                .getProductSimilars(productId)
                .get();

        return productSimilars;
    }

}
