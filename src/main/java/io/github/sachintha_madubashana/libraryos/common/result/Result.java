package io.github.sachintha_madubashana.libraryos.common.result;

public final class Result<T> {
    private final ResultState state;
    private final String message;
    private final String description;
    private final T data;

    private Result(ResultState state, String message, String description, T data) {
        this.state = state;
        this.message = message;
        this.description = description;
        this.data = data;
    }

    public static Result<Void> success(String message) {
        return new Result<>(ResultState.SUCCESS, message, null, null);
    }

    public static Result<Void> success(String message, String description) {
        return new Result<>(ResultState.SUCCESS, message, description, null);
    }

    public static <T> Result<T> success(String message, T data) {
        return new Result<>(ResultState.SUCCESS, message, null, data);
    }

    public static <T> Result<T> success(String message, String description, T data) {
        return new Result<>(ResultState.SUCCESS, message, description, data);
    }

    public static <T> Result<T> error(String message) {
        return new Result<>(ResultState.ERROR, message, null, null);
    }

    public static <T> Result<T> error(String message, String description) {
        return new Result<>(ResultState.ERROR, message, description, null);
    }

    public boolean isSuccess() {
        return this.state == ResultState.SUCCESS;
    }

    public boolean isError() {
        return state == ResultState.ERROR;
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
}
