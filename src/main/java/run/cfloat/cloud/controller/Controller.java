package run.cfloat.cloud.controller;

import java.util.HashMap;
import java.util.Map;

public class Controller {
    public Map<String, Object> toSuccess(Object data) {
        final var result = new HashMap<String, Object>();
        result.put("code", 0);
        result.put("message", "success");
        result.put("data", data);
        return result;
    }

    public Map<String, Object> toSuccess() {
        final var result = new HashMap<String, Object>();
        result.put("code", 0);
        result.put("message", "success");
        result.put("data", null);
        return result;
    }

    public Map<String, Object> toError(String message) {
        final var result = new HashMap<String, Object>();
        result.put("code", 1);
        result.put("message", message);
        return result;
    }
}
