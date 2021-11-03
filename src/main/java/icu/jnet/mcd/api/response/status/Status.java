package icu.jnet.mcd.api.response.status;

import java.util.List;

public class Status {

    private int code;
    private String message, type;
    private List<Error> errors;

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
        return errors;
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
