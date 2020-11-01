package pl.polsl.wachowski.nutritionassistant.fdc.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import pl.polsl.wachowski.nutritionassistant.fdc.client.api.food.FdcFood;
import pl.polsl.wachowski.nutritionassistant.fdc.client.api.food.search.FdcFoodSearchRequest;
import pl.polsl.wachowski.nutritionassistant.fdc.client.api.food.search.FdcFoodSearchResponse;
import pl.polsl.wachowski.okhttpclient.common.CommonOkhttpClient;

import static pl.polsl.wachowski.nutritionassistant.fdc.client.api.FdcApi.*;

public class RealFdcClient extends CommonOkhttpClient implements FdcClient {

    private final FdcClientConfig config;

    public RealFdcClient(final OkHttpClient okHttpClient,
                         final ObjectMapper objectMapper,
                         final FdcClientConfig config) {
        super(okHttpClient, objectMapper);
        this.config = config;
    }

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
            final FdcFoodSearchResponse response = sendRequest(request, FdcFoodSearchResponse.class);
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
        try {
            final FdcFood food = sendRequest(request, FdcFood.class);
            return FdcResult.success(food);
        } catch (final Exception e) {
            return FdcResult.failure(e);
        }
    }

}
