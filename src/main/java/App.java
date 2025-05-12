import Client.ClientService;
import storage.Database;
import storage.DatabaseInitService;

import java.sql.Connection;

public class App {
    public static void main(String[] args) {

        Connection connection = Database.getINSTANCE().getConnection();

        DatabaseInitService.initialize();

        ClientService clientService = new ClientService(connection);

        System.out.println("App successfully initialized");
    }
}
