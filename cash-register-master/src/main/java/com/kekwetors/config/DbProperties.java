package com.kekwetors.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.qatools.properties.PropertyLoader;
import ru.yandex.qatools.properties.annotations.Property;
import ru.yandex.qatools.properties.annotations.Resource;

import static java.util.Objects.isNull;

@Data
@Slf4j
@Resource.Classpath("application.properties")
public class DbProperties {

  @Property("db.driver")
  String driver;

  @Property("db.username")
  String username;

  @Property("db.password")
  String password;

  @Property("db.url")
  String url;

  @Property("db.migrations")
  String migrations;

  private DbProperties() {}

  public static DbProperties getInstance() {
    return DbPropertiesHolder.getInstance();
  }

  private static class DbPropertiesHolder {

    private static DbProperties dbProperties;

    public static DbProperties getInstance() {
      if (isNull(dbProperties)) {
        dbProperties = new DbProperties();
        PropertyLoader.populate(dbProperties);
        log.info("Successfully loaded properties [{}]", dbProperties);
      }
      return dbProperties;
    }
  }
}
