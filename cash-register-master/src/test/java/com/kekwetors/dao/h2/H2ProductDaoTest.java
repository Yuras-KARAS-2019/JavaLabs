package com.kekwetors.dao.h2;

import com.kekwetors.dao.model.Product;
import com.kekwetors.data_providers.ProductEntityProvider;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

@Slf4j
public class H2ProductDaoTest extends AbstractDaoTest {

  @Test
  public void getAllProductsTest() {
    for(int i=0; i<3; i++) {
      insertProduct();
    }
    Assert.assertEquals("Inserted products amount isn't equal to received products amount", productDao.getAllProducts().size(), 3);
  }

  @Test
  public void getExistingProductByIdTest() {
    Product product = insertProduct();
    Product receivedProduct = productDao.getProductById(product.getId());
    Assert.assertEquals(receivedProduct, product);
  }

  @Test
  public void getNonExistingProductByIdTest() {
    int id = -1;
    Product receivedProduct = productDao.getProductById(id);
    Assert.assertNull("Getting product by non existing id should return null",receivedProduct);
  }

  @Test
  public void getExistingProductByNameTest() {
    Product product = insertProduct();
    Product receivedProduct = productDao.getProductByName(product.getName());
    Assert.assertEquals(receivedProduct, product);
  }

  @Test
  public void insertProductTest() {
    Product product = ProductEntityProvider.prepareProduct();
    Product insertedProduct = productDao.insertProduct(product);

    Product receivedProduct = productDao.getProductById(insertedProduct.getId());
    Assert.assertEquals("Received product isn't equal to inserted one", receivedProduct, insertedProduct);
  }

  @Test
  public void updateProductTest() {
    Product product = insertProduct();

    Double newPrice = Math.random() * 100;
    product.setPrice(newPrice);
    productDao.updateProduct(product);

    Product receivedProduct = productDao.getProductById(product.getId());
    Assert.assertEquals(receivedProduct.getPrice(), newPrice);
  }

  @Test
  public void deleteProductTest() {
    Product product = insertProduct();
    productDao.deleteProduct(product.getId());

    Product receivedProduct = productDao.getProductById(product.getId());
    Assert.assertNull(receivedProduct);
  }
}
