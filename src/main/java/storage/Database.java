package storage;

import lombok.Generated;
import lombok.Getter;
import utils.ConfigManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Database {
    @Getter
    private static final Database INSTANCE = new Database();
    @Getter
    private Connection connection;

    private Database() {
       try {
            this.connection = DriverManager.getConnection(
                    ConfigManager.getProperty("db.url"),
                    ConfigManager.getProperty("db.username"),
                    ConfigManager.getProperty("db.password")
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    public static Database getInstance() {
//        return INSTANCE;
//    }
}
