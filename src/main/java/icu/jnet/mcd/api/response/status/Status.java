package icu.jnet.mcd.api.response.status;

import java.util.ArrayList;
import java.util.List;

public class Status {

    private final int code = -1;
    private final String message, type;
    private final List<Error> errors;

    public Status(String message, String type, List<Error> errors) {
        this.message = message;
        this.type = type;
        this.errors = errors;
    }

    public Status() {
        this.message = "Unknown";
        this.type = "";
        this.errors = new ArrayList<>();
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }

    public List<Error> getErrors() {
        return errors != null ? errors : new ArrayList<>();
    }

    public static class Error {

        private int code;
        private String type, message, property, path, service;

        public int getErrorCode() {
            return code;
        }

        public String getErrorType() {
            return type;
        }

        public String getErrorMessage() {
            return message;
        }

        public String getProperty() {
            return property;
        }

        public String getPath() {
            return path;
        }

        public String getService() {
            return service;
        }
    }
}
