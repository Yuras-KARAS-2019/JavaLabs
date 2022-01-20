package com.kekwetors.web.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kekwetors.dao.model.ReceiptStatus;
import lombok.Value;

import java.util.List;

@Value
public class ReceiptDto {
    Integer id;
    Integer employeeId;
    List<ReceiptProductDto> receiptProductDtos;
    ReceiptStatus status;

    @JsonCreator
    public ReceiptDto(@JsonProperty("id") Integer id,
                      @JsonProperty("employeeId") Integer employeeId,
                      @JsonProperty("receiptProductDtos") List<ReceiptProductDto> receiptProductDtos,
                      @JsonProperty("status") ReceiptStatus status) {
        this.id = id;
        this.employeeId = employeeId;
        this.receiptProductDtos = receiptProductDtos;
        this.status = status;
    }
}
