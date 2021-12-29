package com.kekwetors.dao.impl.h2;

import com.kekwetors.dao.H2DaoFactory;
import com.kekwetors.dao.ProductDao;
import com.kekwetors.dao.model.Product;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

@Slf4j
public class H2ProductDao implements ProductDao {

  private static final String GET_ALL_PRODUCTS = "SELECT * FROM product;";
  private static final String GET_PRODUCT_BY_ID = "SELECT * FROM product WHERE id = ?;";
  private static final String GET_PRODUCT_BY_NAME = "SELECT * FROM product WHERE name = ?;";
  private static final String INSERT_PRODUCT = "INSERT INTO product (name, amount, price) VALUES (?, ?, ?);";
  private static final String UPDATE_PRODUCT = "UPDATE product SET name = ?, amount = ?, price = ? WHERE id = ?;";
  private static final String DELETE_PRODUCT = "DELETE FROM product WHERE id = ?;";

  @Override
  public List<Product> getAllProducts() {
    List<Product> products = new ArrayList<>();

    try (Connection connection = H2DaoFactory.getConnection();
         Statement statement = connection.createStatement()) {
      ResultSet resultSet =  statement.executeQuery(GET_ALL_PRODUCTS);
      while (resultSet.next()) {
        Product product = new Product(resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getDouble("amount"),
                resultSet.getDouble("price"));
        products.add(product);
      }
    } catch (SQLException e) {
      log.error("SQLException while getting all products",e);
      throw new RuntimeException();
    }
    return products;
  }

  @Override
  public Product getProductById(int id) {
    Product product = null;

    try (Connection connection = H2DaoFactory.getConnection();
         PreparedStatement statement = connection.prepareStatement(GET_PRODUCT_BY_ID)) {
      statement.setInt(1, id);

      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next()) {
        product = new Product(resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getDouble("amount"),
                resultSet.getDouble("price"));
      }
    } catch (SQLException e) {
      log.error(format("SQLException while getting product with id {%s}", id),e);
      throw new RuntimeException();
    }
    return product;
  }

  @Override
  public Product getProductByName(String name) {
    Product product = null;

    try (Connection connection = H2DaoFactory.getConnection();
         PreparedStatement statement = connection.prepareStatement(GET_PRODUCT_BY_NAME)) {
      statement.setString(1, name);

      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next()) {
        product = new Product(resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getDouble("amount"),
                resultSet.getDouble("price"));
      }
    } catch (SQLException e) {
      log.error(format("SQLException while getting product with name {%s}", name),e);
      throw new RuntimeException();
    }
    return product;
  }

  @Override
  public Product insertProduct(Product product) {

    try (Connection connection = H2DaoFactory.getConnection();
         PreparedStatement statement = connection.prepareStatement(INSERT_PRODUCT, Statement.RETURN_GENERATED_KEYS)) {
      statement.setString(1, product.getName());
      statement.setDouble(2, product.getAvailableAmount());
      statement.setDouble(3, product.getPrice());

      int affectedRows = statement.executeUpdate();
      if (affectedRows == 0) {
        log.error("Failed to insert " + product);
        throw new RuntimeException();
      }

      ResultSet generatedKeys = statement.getGeneratedKeys();
      if (generatedKeys.next()) {
        product.setId(generatedKeys.getInt(1));
      } else {
        log.error("Failed to obtain id of inserted " + product);
        throw new RuntimeException();
      }

      log.info("Inserted " + product);
    } catch (SQLException e) {
      log.error("SQLException while inserting " + product,e);
      throw new RuntimeException();
    }

    return product;
  }

  @Override
  public void updateProduct(Product product) {
    try (Connection connection = H2DaoFactory.getConnection();
         PreparedStatement statement = connection.prepareStatement(UPDATE_PRODUCT)) {
      statement.setInt(4, product.getId());
      statement.setString(1, product.getName());
      statement.setDouble(2, product.getAvailableAmount());
      statement.setDouble(3, product.getPrice());
      statement.execute();

      log.info(String.format("Updated product with id %s to %s", product.getId(), product));
    } catch (SQLException e) {
      log.error("SQLException while updating product " + product,e);
      throw new RuntimeException();
    }
  }

  @Override
  public void deleteProduct(int id) {
    try (Connection connection = H2DaoFactory.getConnection();
         PreparedStatement statement = connection.prepareStatement(DELETE_PRODUCT)) {
      statement.setInt(1, id);
      statement.execute();

      log.info("Deleted product with id " + id);
    } catch (SQLException e) {
      log.error("SQLException while deleting product with id" + id,e);
      throw new RuntimeException();
    }
  }
}
