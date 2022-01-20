package com.kekwetors.dao.impl.h2;

import com.kekwetors.dao.H2DaoFactory;
import com.kekwetors.dao.ReceiptDao;
import com.kekwetors.dao.model.Receipt;
import com.kekwetors.dao.model.ReceiptProduct;
import com.kekwetors.dao.model.ReceiptStatus;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

@Slf4j
public class H2ReceiptDao implements ReceiptDao {

    private static final H2EmployeeDao employeeDao = new H2EmployeeDao();
    private static final H2ProductDao productDao = new H2ProductDao();

    private static final String GET_ALL_PRODUCTS_RELATED_TO_RECEIPT = "SELECT * FROM receipt_product WHERE receipt_id = ?;";
    private static final String INSERT_PRODUCT_RELATED_TO_RECEIPT = "INSERT INTO receipt_product (receipt_id, product_id, quantity) VALUES (?, ?, ?);";

    private static final String GET_ALL_RECEIPTS = "SELECT * FROM receipt;";
    private static final String GET_RECEIPT_BY_ID = "SELECT * FROM receipt WHERE id = ?;";
    private static final String INSERT_RECEIPT = "INSERT INTO receipt (cashier_id, status) VALUES (?, ?);";
    private static final String UPDATE_RECEIPT = "UPDATE receipt SET cashier_id = ?, status = ? WHERE id = ?;";
    private static final String DELETE_RECEIPT = "DELETE FROM receipt WHERE id = ?;";

    @Override
    public List<ReceiptProduct> getProductsRelatedToReceipt(int receiptId) {
        List<ReceiptProduct> receiptProducts = new ArrayList<>();

        try (Connection connection = H2DaoFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_PRODUCTS_RELATED_TO_RECEIPT)) {
            preparedStatement.setInt(1, receiptId);
            ResultSet receiptProductsSet = preparedStatement.executeQuery();
            while (receiptProductsSet.next()) {
                ReceiptProduct receiptProduct = new ReceiptProduct(
                        productDao.getProductById(receiptProductsSet.getInt("product_id")),
                        receiptProductsSet.getDouble("quantity")
                );
                receiptProducts.add(receiptProduct);
            }
        } catch (SQLException e) {
            log.error("SQLException while getting all receipts of product", e);
            throw new RuntimeException();
        }

        return receiptProducts;
    }

    @Override
    public List<Receipt> getAllReceipts() {
        List<Receipt> receipts = new ArrayList<>();

        try (Connection connection = H2DaoFactory.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(GET_ALL_RECEIPTS);
            while (resultSet.next()) {
                int receiptId = resultSet.getInt("id");

                Receipt receipt = new Receipt(receiptId,
                        employeeDao.getEmployeeById(resultSet.getInt("cashier_id")),
                        getProductsRelatedToReceipt(receiptId),
                        ReceiptStatus.fromString(resultSet.getString("status")));
                receipts.add(receipt);
            }
        } catch (SQLException e) {
            log.error("SQLException while getting all receipts", e);
            throw new RuntimeException();
        }
        return receipts;
    }

    @Override
    public Receipt getReceiptById(int id) {
        Receipt receipt = null;

        try (Connection connection = H2DaoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_RECEIPT_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {

                receipt = new Receipt(id,
                        employeeDao.getEmployeeById(resultSet.getInt("cashier_id")),
                        getProductsRelatedToReceipt(id),
                        ReceiptStatus.fromString(resultSet.getString("status")));
            }
        } catch (SQLException e) {
            log.error(format("SQLException while getting receipt with id {%s}", id), e);
            throw new RuntimeException();
        }
        return receipt;
    }

    @Override
    @SneakyThrows
    public Receipt insertReceipt(Receipt receipt) {
        try (Connection connection = H2DaoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_RECEIPT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, receipt.getEmployee().getId());
            statement.setString(2, receipt.getStatus().toString());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                log.error("Failed to insert " + receipt);
                throw new RuntimeException();
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                receipt.setId(generatedKeys.getInt(1));
            } else {
                log.error("Failed to obtain id of inserted " + receipt);
                throw new RuntimeException();
            }

            log.info("Inserted " + receipt);
        } catch (SQLException e) {
            log.error("SQLException while inserting " + receipt, e);
            throw new RuntimeException();
        }
        return receipt;
    }

    @Override
    public ReceiptProduct insertProductIntoReceipt(ReceiptProduct receiptProduct, int receiptId) {
        try (Connection connection = H2DaoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_PRODUCT_RELATED_TO_RECEIPT)) {
            statement.setInt(1, receiptId);
            statement.setInt(2, receiptProduct.getProduct().getId());
            statement.setDouble(3, receiptProduct.getQuantity());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                log.error("Failed to insert " + receiptProduct);
                throw new RuntimeException();
            }

            log.info("Inserted " + receiptProduct);
        } catch (SQLException e) {
            log.error("SQLException while inserting " + receiptProduct, e);
            throw new RuntimeException();
        }
        return receiptProduct;
    }


    @Override
    @SneakyThrows
    public void updateReceipt(Receipt receipt) {
        try (Connection connection = H2DaoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_RECEIPT)) {
            statement.setInt(3, receipt.getId());
            statement.setInt(1, receipt.getEmployee().getId());
            statement.setString(2, receipt.getStatus().toString());
            statement.execute();

            log.info(String.format("Updated receipt with id %s to %s", receipt.getId(), receipt));
        } catch (SQLException e) {
            log.error("SQLException while updating receipt " + receipt, e);
            throw new RuntimeException();
        }
    }

    @Override
    @SneakyThrows
    public void deleteReceipt(int id) {
        try (Connection connection = H2DaoFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_RECEIPT)) {
            statement.setInt(1, id);
            statement.execute();

            log.info("Deleted receipt with id " + id);
        } catch (SQLException e) {
            log.error("SQLException while deleting receipt with id" + id, e);
            throw new RuntimeException();
        }
    }
}
