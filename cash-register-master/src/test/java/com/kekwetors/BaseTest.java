package com.kekwetors;

import com.kekwetors.dao.H2DaoFactory;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.BeforeClass;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static com.kekwetors.dao.DaoFactory.getDbProperties;
import static com.kekwetors.dao.DaoFactory.loadDriver;

@Slf4j
public class BaseTest {
  private static void doMigrations() {
    final String MIGRATIONS_PATH = "src/main/resources/db.migrations/";
    final String INIT_TABLE_SCRIPT = "init_tables.sql";
    File initScriptFile = new File("./" + MIGRATIONS_PATH + INIT_TABLE_SCRIPT);

    try (Connection connection = H2DaoFactory.getConnection();
         Statement statement = connection.createStatement()){
      log.info("Opened connection to " + getDbProperties().getUrl());

      statement.execute(Files.readString(initScriptFile.getAbsoluteFile().toPath()));
      log.info("Successfully initialised tables. Applied migration " + INIT_TABLE_SCRIPT);

    } catch (SQLException e) {
      log.error("Can't initialise h2 db on url " + getDbProperties().getUrl(), e);
      throw new RuntimeException();
    } catch (FileNotFoundException | NoSuchFileException e) {
      log.error(String.format("Can't find files by path [%s]", MIGRATIONS_PATH), e);
      throw new RuntimeException();
    } catch (IOException e) {
      log.error("IOException while initializing db", e);
    }
  }

  @BeforeClass
  public static void startDB() {
    loadDriver();
    doMigrations();
  }

  @After
  @SneakyThrows
  public void cleanupTestData() {
    try (Connection conn = H2DaoFactory.getConnection();
         Statement stmt = conn.createStatement()) {
      stmt.execute("SET REFERENTIAL_INTEGRITY FALSE");

      stmt.execute("TRUNCATE TABLE receipt_product;");
      stmt.execute("TRUNCATE TABLE receipt;");
      stmt.execute("TRUNCATE TABLE employee;");
      stmt.execute("TRUNCATE TABLE product;");

      stmt.execute("SET REFERENTIAL_INTEGRITY TRUE");
    }
  }
}
