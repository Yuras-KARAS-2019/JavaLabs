package com.kekwetors.web.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class ProductDto {
    Integer id;
    String name;
    Double availableAmount;
    Double price;

    @JsonCreator
    public ProductDto(@JsonProperty("id") Integer id,
                      @JsonProperty("name") String name,
                      @JsonProperty("availableAmount") Double availableAmount,
                      @JsonProperty("price") Double price) {
        this.id = id;
        this.name = name;
        this.availableAmount = availableAmount;
        this.price = price;
    }
}
