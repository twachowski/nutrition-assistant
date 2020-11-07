package pl.polsl.wachowski.okhttpclient.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

import java.io.IOException;
import java.util.Optional;

import static pl.polsl.wachowski.okhttpclient.common.HttpMediaTypes.JSON_MEDIA_TYPE;

public abstract class CommonOkhttpClient {

    protected final OkHttpClient okHttpClient;
    private final ObjectMapper objectMapper;

    protected CommonOkhttpClient(final OkHttpClient okHttpClient, final ObjectMapper objectMapper) {
        this.okHttpClient = okHttpClient;
        this.objectMapper = objectMapper;
    }

    protected RequestBody createRequestBody(final Object object) throws JsonProcessingException {
        final String json = objectMapper.writeValueAsString(object);
        return RequestBody.create(json, JSON_MEDIA_TYPE);
    }

    protected <T> T sendRequest(final Request request, final Class<T> clazz) throws IOException {
        final Call call = okHttpClient.newCall(request);
        try (final Response response = call.execute()) {
            if (response.isSuccessful()) {
                final String responseBody = getResponseBody(response)
                        .orElseThrow(() -> new IllegalStateException("Response body is null, status=" + response.code()));
                return objectMapper.readValue(responseBody, clazz);
            }
            final String responseBody = getResponseBody(response)
                    .orElse(null);
            throw new RuntimeException(String.format("Response unsuccessful, status=%s, message=%s, body=%s",
                                                     response.code(),
                                                     response.message(),
                                                     responseBody));
        }
    }

    private static Optional<String> getResponseBody(final Response response) {
        return Optional.ofNullable(response.body())
                .map(Object::toString);
    }

    protected static HttpUrl.Builder createUrlBuilder(final String url) {
        return Optional.ofNullable(HttpUrl.parse(url))
                .map(HttpUrl::newBuilder)
                .orElseThrow(() -> new IllegalArgumentException("Failed to parse HttpUrl from: " + url));
    }

}
