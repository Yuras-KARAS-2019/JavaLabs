package com.kekwetors.converter;

import com.kekwetors.dao.EmployeeDao;
import com.kekwetors.dao.impl.h2.H2EmployeeDao;
import com.kekwetors.dao.model.Receipt;
import com.kekwetors.web.dto.ReceiptDto;
import lombok.NoArgsConstructor;

import java.util.stream.Collectors;

@NoArgsConstructor
public class ReceiptConverter implements Converter<ReceiptDto, Receipt> {

    EmployeeDao employeeDao = new H2EmployeeDao();
    ReceiptProductConverter receiptProductConverter = new ReceiptProductConverter();

    @Override
    public Receipt toModel(ReceiptDto dto) {
        return new Receipt(
                dto.getId(),
                employeeDao.getEmployeeById(dto.getEmployeeId()),
                dto.getReceiptProductDtos() == null ? null : dto.getReceiptProductDtos().stream().map(receiptProductConverter::toModel).collect(Collectors.toList()),
                dto.getStatus()
        );
    }

    @Override
    public ReceiptDto toDto(Receipt model) {
        return new ReceiptDto(
                model.getId(),
                model.getEmployee().getId(),
                model.getReceiptProduct() == null ? null : model.getReceiptProduct().stream().map(receiptProductConverter::toDto).collect(Collectors.toList()),
                model.getStatus()
        );
    }

}
