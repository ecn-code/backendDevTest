package com.eliascanalesnieto.demo.mapper;

import com.eliascanalesnieto.demo.dto.Product;
import com.eliascanalesnieto.demo.dto.ProductSimilar;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductSimilarMapper {

    ProductSimilar map(final Product product);

}
