package pl.polsl.wachowski.nutritionassistant.fdc.client;

import lombok.ToString;

import java.util.Objects;
import java.util.Optional;

@ToString
public class FdcResult<T> {

    private final T response;
    private final Exception exception;

    private FdcResult(final T response, final Exception exception) {
        this.response = response;
        this.exception = exception;
    }

    public static <T> FdcResult<T> success(final T response) {
        return new FdcResult<>(Objects.requireNonNull(response, "Response must not be null in success result"),
                               null);
    }

    public static <T> FdcResult<T> failure(final Exception exception) {
        return new FdcResult<>(null,
                               Objects.requireNonNull(exception, "Exception must not be null in failure result"));
    }

    public T response() {
        return Optional.ofNullable(response)
                .orElseThrow(() -> new IllegalStateException("Cannot get response from failure result"));
    }

    public Exception exception() {
        return Optional.ofNullable(exception)
                .orElseThrow(() -> new IllegalStateException("Cannot get exception from success result"));
    }

    public boolean isSuccess() {
        return exception == null;
    }

    public boolean isFailure() {
        return !isSuccess();
    }

}
