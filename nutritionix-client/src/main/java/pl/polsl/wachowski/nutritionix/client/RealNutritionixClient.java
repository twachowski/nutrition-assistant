package pl.polsl.wachowski.nutritionix.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import okhttp3.*;
import pl.polsl.wachowski.nutritionix.client.api.food.NutritionixFood;
import pl.polsl.wachowski.nutritionix.client.api.food.NutritionixFoodRequest;
import pl.polsl.wachowski.nutritionix.client.api.food.NutritionixFoodResponse;
import pl.polsl.wachowski.nutritionix.client.api.food.search.NutritionixFoodSearchResponse;

import java.io.IOException;
import java.util.Optional;

import static pl.polsl.wachowski.nutritionix.client.api.NutritionixApi.*;

@AllArgsConstructor
public class RealNutritionixClient implements NutritionixClient {

    private static final MediaType JSON_MEDIA_TYPE = MediaType.parse("application/json");
    private static final String SERVING_100G_SUFFIX = " 100g";

    private final OkHttpClient okHttpClient;
    private final ObjectMapper objectMapper;
    private final NutritionixClientConfig config;

    @Override
    public NutritionixResult<NutritionixFoodSearchResponse> searchFoods(final String query) {
        final HttpUrl url = createUrlBuilder(FOOD_SEARCH_API)
                .addQueryParameter(QUERY, query)
                .build();
        final Request request = new Request.Builder()
                .url(url)
                .headers(getHeaders())
                .get()
                .build();
        final Call call = okHttpClient.newCall(request);
        try {
            final NutritionixFoodSearchResponse response = makeCall(call, NutritionixFoodSearchResponse.class);
            return NutritionixResult.success(response);
        } catch (final Exception e) {
            return NutritionixResult.failure(e);
        }
    }

    @Override
    public NutritionixResult<NutritionixFood> getFood(final String id) {
        final HttpUrl url = createUrlBuilder(FOOD_API)
                .build();
        final String query = id + SERVING_100G_SUFFIX;
        final NutritionixFoodRequest foodRequest = new NutritionixFoodRequest(query);
        try {
            final RequestBody requestBody = createRequestBody(foodRequest);
            final Request request = new Request.Builder()
                    .url(url)
                    .headers(getHeaders())
                    .post(requestBody)
                    .build();
            final Call call = okHttpClient.newCall(request);
            final NutritionixFoodResponse response = makeCall(call, NutritionixFoodResponse.class);
            final NutritionixFood food = response.getFoods()
                    .iterator()
                    .next();
            return NutritionixResult.success(food);
        } catch (final Exception e) {
            return NutritionixResult.failure(e);
        }
    }

    private Headers getHeaders() {
        return new Headers.Builder()
                .add(APP_ID_HEADER, config.getAppId())
                .add(APP_KEY_HEADER, config.getAppKey())
                .build();
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
