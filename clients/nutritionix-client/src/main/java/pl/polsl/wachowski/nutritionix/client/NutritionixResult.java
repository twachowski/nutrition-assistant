package pl.polsl.wachowski.nutritionix.client;

import lombok.ToString;

import java.util.Objects;
import java.util.Optional;

@ToString
public class NutritionixResult<T> {

    private final T response;
    private final Exception exception;

    private NutritionixResult(final T response, final Exception exception) {
        this.response = response;
        this.exception = exception;
    }

    public static <T> NutritionixResult<T> success(final T response) {
        return new NutritionixResult<>(Objects.requireNonNull(response, "Response must not be null in success result"),
                                       null);
    }

    public static <T> NutritionixResult<T> failure(final Exception exception) {
        return new NutritionixResult<>(null,
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
