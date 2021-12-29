package com.kekwetors.dao.model;

import lombok.AllArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
public enum ReceiptStatus {
  OPENED("opened"),
  CLOSED("closed"),
  CANCELED("canceled");

  private final String name;

  public static ReceiptStatus fromString(String status) {
    return Arrays.stream(ReceiptStatus.values())
            .filter(x -> x.name.equalsIgnoreCase(status))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Can't get status from string " + status));
  }
}
