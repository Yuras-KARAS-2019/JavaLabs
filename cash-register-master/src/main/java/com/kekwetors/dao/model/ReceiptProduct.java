package com.kekwetors.dao.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReceiptProduct {
    private Product product;
    private Double quantity;
}
