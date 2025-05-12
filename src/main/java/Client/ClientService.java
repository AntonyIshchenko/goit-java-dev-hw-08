package Client;

import java.sql.Connection;

public class ClientService {
    private final Connection connection;

    public ClientService(Connection connection){
        this.connection = connection;
    }
}
