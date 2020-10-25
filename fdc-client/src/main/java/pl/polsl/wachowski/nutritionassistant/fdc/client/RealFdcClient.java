package pl.polsl.wachowski.nutritionassistant.fdc.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import okhttp3.*;
import pl.polsl.wachowski.nutritionassistant.fdc.client.api.food.FdcFood;
import pl.polsl.wachowski.nutritionassistant.fdc.client.api.food.search.FdcFoodSearchRequest;
import pl.polsl.wachowski.nutritionassistant.fdc.client.api.food.search.FdcFoodSearchResponse;

import java.io.IOException;
import java.util.Optional;

import static pl.polsl.wachowski.nutritionassistant.fdc.client.api.FdcApi.*;

@AllArgsConstructor
public class RealFdcClient implements FdcClient {

    private static final MediaType JSON_MEDIA_TYPE = MediaType.parse("application/json");

    private final OkHttpClient okHttpClient;
    private final ObjectMapper objectMapper;
    private final FdcClientConfig config;

    @Override
    public FdcResult<FdcFoodSearchResponse> searchFoods(final String query) {
        final HttpUrl httpUrl = createUrlBuilder(SEARCH_API)
                .addQueryParameter(API_KEY, config.getApiKey())
                .build();
        final FdcFoodSearchRequest searchRequest = new FdcFoodSearchRequest(query);
        try {
            final RequestBody requestBody = createRequestBody(searchRequest);
            final Request request = new Request.Builder()
                    .url(httpUrl)
                    .post(requestBody)
                    .build();
            final Call call = okHttpClient.newCall(request);
            final FdcFoodSearchResponse response = makeCall(call, FdcFoodSearchResponse.class);
            return FdcResult.success(response);
        } catch (final Exception e) {
            return FdcResult.failure(e);
        }
    }

    @Override
    public FdcResult<FdcFood> getFood(final long id) {
        final String url = FOOD_API.replace(FOOD_ID, String.valueOf(id));
        final HttpUrl httpUrl = createUrlBuilder(url)
                .addQueryParameter(API_KEY, config.getApiKey())
                .build();
        final Request request = new Request.Builder()
                .url(httpUrl)
                .get()
                .build();
        final Call call = okHttpClient.newCall(request);
        try {
            final FdcFood food = makeCall(call, FdcFood.class);
            return FdcResult.success(food);
        } catch (final Exception e) {
            return FdcResult.failure(e);
        }
    }

    private RequestBody createRequestBody(final Object object) throws JsonProcessingException {
        final String json = objectMapper.writeValueAsString(object);
        return RequestBody.create(json, JSON_MEDIA_TYPE);
    }

    private <T> T makeCall(final Call call, final Class<T> clazz) throws IOException {
        try (final Response response = call.execute()) {
            if (response.isSuccessful()) {
                final String responseBody = Optional.ofNullable(response.body())
                        .map(Object::toString)
                        .orElseThrow(() -> new IllegalStateException("Response body is null, status=" + response.code()));
                return objectMapper.readValue(responseBody, clazz);
            }
            throw new RuntimeException("Response unsuccessful, status=" + response.code());
        }
    }

    private static HttpUrl.Builder createUrlBuilder(final String url) {
        return Optional.ofNullable(HttpUrl.parse(url))
                .map(HttpUrl::newBuilder)
                .orElseThrow(() -> new IllegalArgumentException("Failed to parse HttpUrl from: " + url));
    }

}
