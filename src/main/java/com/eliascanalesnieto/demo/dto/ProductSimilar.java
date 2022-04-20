package com.eliascanalesnieto.demo.dto;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSimilar implements Serializable {

    private int id;
    private String name;
    private Double price;
    private boolean availability;

}
