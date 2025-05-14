package client;

import interfaces.EntityDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ClientService {
    private final EntityDAO<Client> dao;

    public ClientService(EntityDAO<Client> dao) {
        this.dao = dao;
    }

    private void checkName(String name) throws IllegalArgumentException{
        // Step 1: Check empty value
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name must be not empty value!");
        }

        // Step 2: Check length
        int length = name.trim().length();
        if (length < 2 || length > 1000) {
            throw new IllegalArgumentException("Name length must be between 2 and 1000!");
        }
    }

    private void checkId(long id) throws IllegalArgumentException{
        if (id < 1) {
            throw new IllegalArgumentException("Id must be greater than 0!");
        }
    }

    public long create(String name) throws IllegalArgumentException, SQLException {
        checkName(name);

        try {
            Client client= new Client(0, name.trim());
            return dao.create(client).getId();
        } catch (SQLException e) {
            throw new SQLException("Client creating failed!");
        }
    }

    public String getById(long id) throws IllegalArgumentException, SQLException {
        checkId(id);

        try {
            Client client = dao.get(id);
            return client == null ? null : client.getName();
        } catch (SQLException e) {
            throw new SQLException("Client getting failed!");
        }
    }

    public void setName(long id, String name) throws IllegalArgumentException, SQLException {
        checkName(name);
        checkId(id);

        Client client= new Client(id, name.trim());
        Client updatedClient = dao.update(client);
        System.out.println(updatedClient.toString());
    }

    public void deletedById(long id) throws IllegalArgumentException, SQLException{
        checkId(id);

        Client deletedClient = dao.deleteById(id);
        System.out.println(deletedClient == null ? "Client not found" : deletedClient.toString());
    }

    public List<Client> listAll() throws SQLException {
        return dao.getAll();
    }
}
