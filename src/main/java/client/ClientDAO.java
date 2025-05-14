package client;

import interfaces.EntityDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO implements EntityDAO<Client> {
    private final Connection connection;

    public ClientDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Client create(Client client) throws SQLException {
        String sql = "INSERT INTO client (name) VALUES (?)";
        String errorMessage = "Unexpected database response when executing DML INSERT";

        try (PreparedStatement st = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, client.getName());

            int affectedRows = st.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException(errorMessage);
            }

            try (ResultSet gk = st.getGeneratedKeys()) {
                if (gk.next()) {
                    client.setId(gk.getLong("id"));
                    return client;
                } else {
                    throw new SQLException(errorMessage);
                }
            }
        }
    }

    @Override
    public Client get(long id) throws SQLException {
        String sql = "SELECT id, name FROM client WHERE id=?";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setLong(1, id);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    long clientId = rs.getLong("id");
                    String clientName = rs.getString("name");
                    return new Client(clientId, clientName);
                } else {
                    return null;
                }
            }
        }
    }

    @Override
    public Client update(Client client) throws SQLException {
        String sql = "UPDATE client SET name=? WHERE id=?";
        String errorMessage = "Unexpected database response when executing DML UPDATE";

        try (PreparedStatement st = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, client.getName());
            st.setLong(2, client.getId());

            int affectedRows = st.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException(errorMessage);
            }

            return client;
        }
    }

    @Override
    public Client deleteById(long id) throws SQLException {
        String sql = "DELETE FROM client WHERE id=?";
        String errorMessage = "Unexpected database response when executing DML DELETE";

        Client client = get(id);
        if (client == null) {
            return null;
        }

        try (PreparedStatement st = connection.prepareStatement(sql)){
            st.setLong(1, id);

            int affectedRows = st.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException(errorMessage);
            }

            return client;
        }
    }

    @Override
    public List<Client> getAll() throws SQLException {
        String sql = "SELECT id, name FROM client";
        List<Client> result = new ArrayList<>();

        try (PreparedStatement st = connection.prepareStatement(sql)){
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    long clientId = rs.getLong("id");
                    String clientName = rs.getString("name");
                    result.add(new Client(clientId, clientName));
                }
            }
            return result;
        }
    }
}
