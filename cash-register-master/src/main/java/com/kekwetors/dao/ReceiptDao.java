package com.kekwetors.dao;

import com.kekwetors.dao.model.Receipt;
import com.kekwetors.dao.model.ReceiptProduct;

import java.util.List;

public interface ReceiptDao {
    List<Receipt> getAllReceipts();

    Receipt getReceiptById(int id);

    Receipt insertReceipt(Receipt receipt);

    void updateReceipt(Receipt receipt);

    void deleteReceipt(int id);

    List<ReceiptProduct> getProductsRelatedToReceipt(int receiptId);

    ReceiptProduct insertProductIntoReceipt(ReceiptProduct receiptProduct, int receiptId);
}
