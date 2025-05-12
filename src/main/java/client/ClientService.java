package client;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ClientService {
    private ClientDAO dao;

    public ClientService(Connection connection) {
        try {
            this.dao = new ClientDAO(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
            return dao.create(name.trim()).getId();
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

        Client updatedClient = dao.update(id, name.trim());
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
