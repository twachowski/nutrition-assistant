package pl.polsl.wachowski.fdc.client.api;

public final class FdcApi {

    private FdcApi() {
    }

    public static final String API_PREFIX = "https://api.nal.usda.gov/fdc/v1";

    public static final String API_KEY = "api_key";
    public static final String FOOD_ID = "{foodId}";

    public static final String SEARCH_API = API_PREFIX + "/search";
    public static final String FOOD_API = API_PREFIX + "/" + FOOD_ID;

}
