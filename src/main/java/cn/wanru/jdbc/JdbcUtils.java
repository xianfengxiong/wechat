package cn.wanru.jdbc;


import org.apache.commons.dbutils.DbUtils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author xxf
 * @since 1/23/17
 */
public class JdbcUtils {

  private static final String DRIVERNAME ;
  private static final String URL;
  private static final String USERNAME;
  private static final String PASSWORD;

  static{
    InputStream is = JdbcUtils.class.getResourceAsStream("/jdbc.properties");
    Properties properties = new Properties();
    try {
      properties.load(is);
      DRIVERNAME = properties.getProperty("jdbc.drivername");
      URL = properties.getProperty("jdbc.url");
      USERNAME = properties.getProperty("jdbc.username");
      PASSWORD = properties.getProperty("jdbc.password");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    try {
      Class.forName(DRIVERNAME);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  public static Connection getConnection() throws SQLException {
    return DriverManager.getConnection(URL,USERNAME,PASSWORD);
  }

  public static void closeConnection(Connection connection) {
    DbUtils.closeQuietly(connection);
  }


}
