package pl.polsl.wachowski.fdc.client.api;

public final class FdcApi {

    private FdcApi() {
    }

    public static final String API_PREFIX = "https://api.nal.usda.gov/fdc/v1";

    public static final String API_KEY = "api_key";
    public static final String FOOD_ID = "{foodId}";

    public static final String FOODS_API_SUFFIX = "/foods";
    public static final String FOOD_API_SUFFIX = "/food";
    public static final String FOODS_SEARCH_API_SUFFIX = FOODS_API_SUFFIX + "/search";
    public static final String FOOD_ID_API_SUFFIX = FOOD_API_SUFFIX + "/" + FOOD_ID;

    public static final String SEARCH_API = API_PREFIX + FOODS_SEARCH_API_SUFFIX;
    public static final String FOOD_API = API_PREFIX + FOOD_ID_API_SUFFIX;

}
