package com.kekwetors.converter;

import com.kekwetors.dao.ProductDao;
import com.kekwetors.dao.impl.h2.H2ProductDao;
import com.kekwetors.dao.model.ReceiptProduct;
import com.kekwetors.web.dto.ReceiptProductDto;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ReceiptProductConverter implements Converter<ReceiptProductDto, ReceiptProduct> {

  ProductDao productDao = new H2ProductDao();

  @Override
  public ReceiptProduct toModel(ReceiptProductDto dto) {
    return new ReceiptProduct(productDao.getProductById(dto.getId()), dto.getQuantity());
  }

  @Override
  public ReceiptProductDto toDto(ReceiptProduct model) {
    return new ReceiptProductDto(model.getProduct().getId(), model.getQuantity());
  }

}
