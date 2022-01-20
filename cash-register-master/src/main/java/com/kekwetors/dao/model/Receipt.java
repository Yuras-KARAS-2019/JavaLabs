package com.kekwetors.dao.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Receipt {
    private Integer id;
    private Employee employee;
    private List<ReceiptProduct> receiptProduct;
    private ReceiptStatus status;
}
