package com.kekwetors.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class ReceiptProductDto {
  Integer id;
  Double quantity;

  public ReceiptProductDto(@JsonProperty("id") Integer id,
                           @JsonProperty("quantity") Double quantity) {
    this.id = id;
    this.quantity = quantity;
  }
}
