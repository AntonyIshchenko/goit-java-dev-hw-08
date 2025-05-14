import client.Client;
import client.ClientDAO;
import client.ClientService;
import interfaces.EntityDAO;
import storage.Database;
import storage.DatabaseInitService;

import java.sql.Connection;
import java.sql.SQLException;

public class App {
    public static void main(String[] args) {
        Connection connection = Database.getINSTANCE().getConnection();

        DatabaseInitService.initialize();

        EntityDAO<Client> clientDAO = new ClientDAO(connection);

        ClientService clientService = new ClientService(clientDAO);

        // For demonstration
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

        } catch (SQLException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}
