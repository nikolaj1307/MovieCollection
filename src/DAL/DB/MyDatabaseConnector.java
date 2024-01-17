package DAL.DB;

import GUI.Util.MovieExceptions;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

    public class MyDatabaseConnector {

        private static final String PROP_FILE = "config/config.settings";

        private SQLServerDataSource dataSource;

        public MyDatabaseConnector() throws MovieExceptions {
            Properties databaseProperties = new Properties();
            try {
                databaseProperties.load(new FileInputStream((PROP_FILE)));

                dataSource = new SQLServerDataSource();
                dataSource.setServerName(databaseProperties.getProperty("Server"));
                dataSource.setDatabaseName(databaseProperties.getProperty("Database"));
                dataSource.setUser(databaseProperties.getProperty("User"));
                dataSource.setPassword(databaseProperties.getProperty("Password"));
                dataSource.setPortNumber(1433);
                dataSource.setTrustServerCertificate(true);
            } catch (Exception e) {
                throw new MovieExceptions(e);
            }
        }



        public Connection getConnection() throws SQLException {
            return dataSource.getConnection();
        }

        public static void main(String[] args) throws MovieExceptions {

            MyDatabaseConnector databaseConnector = new MyDatabaseConnector();

            try (Connection connection = databaseConnector.getConnection()) {

                System.out.println("Is it open? " + !connection.isClosed());
            } catch (SQLException e){
                throw new MovieExceptions(e);
            }
            //Connection gets closed here
        }
    }

