package com.kekwetors.converter;

public interface Converter<D, M> {

  M toModel(D dto);

  D toDto(M model);

}
