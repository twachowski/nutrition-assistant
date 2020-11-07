package pl.polsl.wachowski.nutritionix.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import pl.polsl.wachowski.nutritionix.client.api.exercise.search.NutritionixExercise;
import pl.polsl.wachowski.nutritionix.client.api.exercise.search.NutritionixExerciseSearchRequest;
import pl.polsl.wachowski.nutritionix.client.api.exercise.search.NutritionixExerciseSearchResponse;
import pl.polsl.wachowski.nutritionix.client.api.food.NutritionixFood;
import pl.polsl.wachowski.nutritionix.client.api.food.NutritionixFoodRequest;
import pl.polsl.wachowski.nutritionix.client.api.food.NutritionixFoodResponse;
import pl.polsl.wachowski.nutritionix.client.api.food.search.NutritionixFoodSearchResponse;
import pl.polsl.wachowski.okhttpclient.common.CommonOkhttpClient;

import java.util.List;

import static pl.polsl.wachowski.nutritionix.client.api.NutritionixApi.*;

@Slf4j
public class RealNutritionixClient extends CommonOkhttpClient implements NutritionixClient {

    private static final String SERVING_100G_SUFFIX = " 100g";

    private final NutritionixClientConfig config;
    private final Headers headers;

    public RealNutritionixClient(final OkHttpClient okHttpClient,
                                 final ObjectMapper objectMapper,
                                 final NutritionixClientConfig config) {
        super(okHttpClient, objectMapper);
        this.config = config;
        this.headers = new Headers.Builder()
                .add(APP_ID_HEADER, config.getAppId())
                .add(APP_KEY_HEADER, config.getAppKey())
                .build();
    }

    @Override
    public NutritionixResult<NutritionixFoodSearchResponse> searchFoods(final String query) {
        final HttpUrl url = createUrlBuilder(FOOD_SEARCH_API)
                .addQueryParameter(QUERY, query)
                .build();
        final Request request = new Request.Builder()
                .url(url)
                .headers(headers)
                .get()
                .build();
        try {
            final NutritionixFoodSearchResponse response = sendRequest(request, NutritionixFoodSearchResponse.class);
            return NutritionixResult.success(response);
        } catch (final Exception e) {
            log.error("Failed to search foods in Nutritionix, query={}", query, e);
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
                    .headers(headers)
                    .post(requestBody)
                    .build();
            final NutritionixFoodResponse response = sendRequest(request, NutritionixFoodResponse.class);
            final NutritionixFood food = response.getFoods()
                    .iterator()
                    .next();
            return NutritionixResult.success(food);
        } catch (final Exception e) {
            log.error("Failed to get food from Nutritionix, request={}", foodRequest, e);
            return NutritionixResult.failure(e);
        }
    }

    @Override
    public NutritionixResult<List<NutritionixExercise>> searchExercises(final NutritionixExerciseSearchRequest searchRequest) {
        final HttpUrl url = createUrlBuilder(EXERCISE_SEARCH_API)
                .build();
        try {
            final RequestBody requestBody = createRequestBody(searchRequest);
            final Request request = new Request.Builder()
                    .url(url)
                    .headers(headers)
                    .post(requestBody)
                    .build();
            final NutritionixExerciseSearchResponse response = sendRequest(request, NutritionixExerciseSearchResponse.class);
            return NutritionixResult.success(response.getExercises());
        } catch (final Exception e) {
            log.error("Failed to search exercises in Nutritionix, request={}", searchRequest, e);
            return NutritionixResult.failure(e);
        }
    }

}
