package com.eliascanalesnieto.demo.dto;

import lombok.Data;

@Data
public class Product {

    private int id;
    private String name;
    private Double price;
    private boolean availability;

}
