package com.kekwetors.dao;

import com.kekwetors.dao.model.Product;

import java.util.List;

public interface ProductDao {
  List<Product> getAllProducts();
  Product getProductById(int id);
  Product getProductByName(String name);
  Product insertProduct(Product product);
  void updateProduct(Product product);
  void deleteProduct(int id);
}
