package client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO {
    private final PreparedStatement createSt;
    private final PreparedStatement getSt;
    private final PreparedStatement updateSt;
    private final PreparedStatement deleteSt;
    private final PreparedStatement getAllSt;

    public ClientDAO(Connection connection) throws SQLException {
        this.createSt = connection.prepareStatement(
                "INSERT INTO client (name) VALUES (?)", Statement.RETURN_GENERATED_KEYS
        );
        this.getSt = connection.prepareStatement("SELECT id, name FROM client WHERE id=?");
        this.updateSt = connection.prepareStatement("UPDATE client SET name=? WHERE id=?");
        this.deleteSt = connection.prepareStatement("DELETE client WHERE id=?");
        this.getAllSt = connection.prepareStatement("SELECT id, name FROM client");
    }

    public long create(String name) throws SQLException {
        createSt.setString(1, name);

        int affectedRows = createSt.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("Creating client failed, no rows affected.");
        }

        try (ResultSet gk = createSt.getGeneratedKeys()) {
            if (gk.next()) {
                return gk.getLong("id");
            } else {
                throw new SQLException("Creating client failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException(e);
        }
    }

    public Client get(long id) throws SQLException {
        getSt.setLong(1, id);
        try (ResultSet rs = getSt.executeQuery()) {
            if (rs.next()) {
                long clientId = rs.getLong("id");
                String clientName = rs.getString("name");
                return new Client(clientId, clientName);
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Getting client failed");
        }
    }

    public Client update(long id, String name) throws SQLException {
        updateSt.setString(1, name);
        updateSt.setLong(2, id);

        int affectedRows = updateSt.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("Creating client failed, no rows affected.");
        }
        return get(id);
    }

    public Client deleteById(long id) throws SQLException {
        Client client = get(id);
        if (client != null) {
            deleteSt.setLong(1, id);
            int affectedRows = deleteSt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting client failed, no rows affected.");
            }
        }
        return client;
    }

    public List<Client> getAll() throws SQLException {
        List<Client> result = new ArrayList<>();
        try (ResultSet rs = getAllSt.executeQuery()) {
            while (rs.next()) {
                long clientId = rs.getLong("id");
                String clientName = rs.getString("name");
                result.add(new Client(clientId, clientName));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Getting all clients failed");
        }
        return result;
    }
}
