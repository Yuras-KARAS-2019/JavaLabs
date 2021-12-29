package com.kekwetors.dao;

import com.kekwetors.dao.impl.h2.H2EmployeeDao;
import com.kekwetors.dao.impl.h2.H2ProductDao;
import com.kekwetors.dao.impl.h2.H2ReceiptDao;
import jdk.jshell.spi.ExecutionControl.NotImplementedException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.sql.*;

@Slf4j
public class H2DaoFactory extends DaoFactory{

  @Override
  @SneakyThrows
  public EmployeeDao getEmployeeDao() {
    return new H2EmployeeDao();
  }

  @Override
  @SneakyThrows
  public ProductDao getProductDao() {
    return new H2ProductDao();
  }

  @Override
  @SneakyThrows
  public ReceiptDao getReceiptDao() {
    return new H2ReceiptDao();
  }

  public static Connection getConnection() {
    Connection connection = null;
    try {
      connection =  DriverManager.getConnection(dbProperties.getUrl(), dbProperties.getUsername(), dbProperties.getPassword());
    } catch (SQLException e) {
      log.error("Can not establish database connection", e);
    }
    return connection;
  }

  @Override
  public void launchDb(String realPath) {
    final String MIGRATIONS_PATH = realPath + dbProperties.getMigrations();

    final String INIT_TABLE_SCRIPT = "init_tables.sql";
    final String INIT_ENTITIES_SCRIPT = "init_entities.sql";

    try (Connection connection = getConnection();
         Statement statement = connection.createStatement()){
      log.info("Opened connection to " + dbProperties.getUrl());

      statement.execute(Files.readString(Path.of(MIGRATIONS_PATH + INIT_TABLE_SCRIPT)));
      log.info("Successfully initialized tables. Applied migration " + INIT_TABLE_SCRIPT);

      statement.execute(Files.readString(Path.of(MIGRATIONS_PATH + INIT_ENTITIES_SCRIPT)));
      log.info("Successfully initialized entities, Applied migration " + INIT_ENTITIES_SCRIPT);
    } catch (SQLException e) {
      log.error("Can't initialize h2 db on url " + dbProperties.getUrl(), e);
      throw new RuntimeException();
    } catch (FileNotFoundException | NoSuchFileException e) {
      log.error(String.format("Can't find files by path [%s]", dbProperties.getMigrations()), e);
      throw new RuntimeException();
    } catch (IOException e) {
      log.error("IOException while initializing db", e);
    }
  }
}
