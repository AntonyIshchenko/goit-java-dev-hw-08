package client;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Client {
    private long id;
    private String name;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
