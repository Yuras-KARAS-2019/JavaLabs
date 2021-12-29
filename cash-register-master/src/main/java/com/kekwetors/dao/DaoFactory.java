package com.kekwetors.dao;

import com.kekwetors.config.DbProperties;
import jdk.jshell.spi.ExecutionControl.NotImplementedException;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class DaoFactory {

  public static final int H2 = 1;

  public abstract EmployeeDao getEmployeeDao();
  public abstract ProductDao getProductDao();
  public abstract ReceiptDao getReceiptDao();

  @Getter
  protected static final DbProperties dbProperties = DbProperties.getInstance();

  public static void loadDriver() {
    try {
      Class.forName(dbProperties.getDriver());
      log.info("Successfully loaded [{}]", dbProperties.getDriver());
    } catch (ClassNotFoundException e) {
      log.info("Wasn't able to load [{}]", dbProperties.getDriver());
      throw new RuntimeException();
    }
  }

  public abstract void launchDb(String realPath);

  @SneakyThrows
  public static DaoFactory getDaoFactory(int factoryId) {
    switch (factoryId) {
      case H2:
        return new H2DaoFactory();
      default:
        throw new NotImplementedException("DaoFactory isn't implemented for " + factoryId);
    }
  }
}
