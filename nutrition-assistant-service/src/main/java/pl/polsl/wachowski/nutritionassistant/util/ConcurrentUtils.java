package pl.polsl.wachowski.nutritionassistant.util;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public final class ConcurrentUtils {

    private ConcurrentUtils() {
    }

    public static <T> T extractFuture(final CompletableFuture<T> completableFuture) {
        try {
            return completableFuture.get();
        } catch (final InterruptedException | ExecutionException e) {
            throw new RuntimeException("Failed to extract CompletableFuture: " + completableFuture, e);
        }
    }

}
