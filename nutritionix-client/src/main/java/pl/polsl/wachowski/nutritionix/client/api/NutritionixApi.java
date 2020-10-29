package pl.polsl.wachowski.nutritionix.client.api;

public final class NutritionixApi {

    private NutritionixApi() {
    }

    public static final String API_PREFIX = "https://trackapi.nutritionix.com/v2";

    public static final String APP_ID_HEADER = "x-app-id";
    public static final String APP_KEY_HEADER = "x-app-key";

    public static final String QUERY = "query";

    public static final String EXERCISE_SEARCH_API = "/natural/exercise";
    public static final String FOOD_SEARCH_API = "/search/instant";
    public static final String FOOD_API = API_PREFIX + "/natural/nutrients";

}
