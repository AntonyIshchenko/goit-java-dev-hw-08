package storage;

import org.flywaydb.core.Flyway;
import utils.ConfigManager;

public class DatabaseInitService {

    private DatabaseInitService() {
    }

    public static void initialize() {
        Flyway flyway = Flyway.
                configure().
                dataSource(
                        ConfigManager.getProperty("db.url"),
                        ConfigManager.getProperty("db.username"),
                        ConfigManager.getProperty("db.password")
                ).
                load();

        flyway.migrate();
    }
}
