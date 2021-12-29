package com.kekwetors.dao.h2;

import com.kekwetors.BaseTest;
import com.kekwetors.dao.DaoFactory;
import com.kekwetors.dao.ProductDao;
import com.kekwetors.dao.model.Product;
import lombok.extern.slf4j.Slf4j;

import static com.kekwetors.data_providers.ProductEntityProvider.prepareProduct;

@Slf4j
public abstract class AbstractDaoTest extends BaseTest{

  protected DaoFactory factory = DaoFactory.getDaoFactory(DaoFactory.H2);
  protected ProductDao productDao = factory.getProductDao();

  protected Product insertProduct() {
    Product product = prepareProduct();
    return productDao.insertProduct(product);
  }
}

