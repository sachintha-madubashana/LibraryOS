package io.github.sachintha_madubashana.libraryos.service;

public class ResultService<T> {
    private final ResultState state; // Using the Enum here!
    private final String message;
    private final String description;
    private final T data;

    private ResultService(ResultState state, String message, String description, T data) {
        this.state = state;
        this.message = message;
        this.description = description;
        this.data = data;
    }

    public static <T> ResultService<T> success(String message, T data) {
        return new ResultService<>(ResultState.SUCCESS, message, null, data);
    }

    public static <T> ResultService<T> success(String message, String description, T data) {
        return new ResultService<>(ResultState.SUCCESS, message, description, data);
    }

    public static <T> ResultService<T> error(String message) {
        return new ResultService<>(ResultState.ERROR, message, null, null);
    }

    public static <T> ResultService<T> error(String message, String description) {
        return new ResultService<>(ResultState.ERROR, message, description, null);
    }


    public boolean isSuccess() {
        return this.state == ResultState.SUCCESS;
    }

    public ResultState getState() {
        return state;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }

    public T getData() {
        return data;
    }

    public enum ResultState {
        SUCCESS,
        ERROR
    }
}
