package com.kekwetors.converter;

import com.kekwetors.dao.model.Product;
import com.kekwetors.web.dto.ProductDto;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ProductConverter implements Converter<ProductDto, Product>{

  @Override
  public Product toModel(ProductDto dto) {
    return new Product(dto.getId(),
            dto.getName(),
            dto.getAvailableAmount(),
            dto.getPrice());
  }

  @Override
  public ProductDto toDto(Product model) {
    return new ProductDto(model.getId(),
            model.getName(),
            model.getAvailableAmount(),
            model.getPrice());
  }

}
