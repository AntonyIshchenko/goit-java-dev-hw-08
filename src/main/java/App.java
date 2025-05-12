import client.ClientService;
import storage.Database;
import storage.DatabaseInitService;

import java.sql.Connection;

public class App {
    public static void main(String[] args) {

        Connection connection = Database.getINSTANCE().getConnection();

        DatabaseInitService.initialize();

        ClientService clientService = new ClientService(connection);

        try {
            System.out.println("Get all clients: " + clientService.listAll().toString());

            long newId = clientService.create("test1");
            System.out.println("Create new client name \"test1\" received id: " + newId);

            System.out.println("Create new client name \"test2\" received id: " + clientService.create("test2"));

            System.out.println("Get client with id=3 : " + clientService.getById(3));

            System.out.print("Set client with id=6 name \"Mega Test\" : ");
            clientService.setName(6, "Mega Test");

            System.out.print("Delete client with id=" + newId + " :");
            clientService.deletedById(newId);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
