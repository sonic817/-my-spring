package demo.service;

import org.json.simple.JSONObject;

public class DBException extends RuntimeException {

    private JSONObject value;

    public JSONObject getValue() {
        return value;
    }

    public DBException(JSONObject value, String message) {
        super(message);
        this.value = value;
    }
}
