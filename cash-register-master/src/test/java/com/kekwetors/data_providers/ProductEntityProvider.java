package com.kekwetors.data_providers;

import com.kekwetors.dao.model.Product;

public class ProductEntityProvider {

  public static Product prepareProduct() {
    long currentTime = System.currentTimeMillis();

    Integer id = null;
    String name = String.format("name-%s", currentTime);
    Double availableAmount = Math.random() * 100;
    Double price = Math.random() * 50;

    return new Product(id, name, availableAmount, price);
  }
}
