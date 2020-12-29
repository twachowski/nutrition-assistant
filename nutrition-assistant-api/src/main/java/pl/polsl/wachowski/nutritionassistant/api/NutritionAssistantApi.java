package pl.polsl.wachowski.nutritionassistant.api;

public final class NutritionAssistantApi {

    private NutritionAssistantApi() {
    }

    public static final String APPLICATION_PREFIX = "/nutrition-assistant";
    public static final String API_VERSION = "/api/v1.0";
    public static final String API_PREFIX = APPLICATION_PREFIX + API_VERSION;

    public static final String QUERY = "query";
    public static final String PROVIDER = "provider";
    public static final String FOOD_ID = "foodId";
    public static final String DATE = "date";
    public static final String ENTRY_POSITION = "entryPosition";

    //path params
    public static final String FOOD_ID_PATH_PARAM = "{" + FOOD_ID + "}";
    public static final String DATE_PATH_PARAM = "{" + DATE + "}";
    public static final String ENTRY_POSITION_PATH_PARAM = "{" + ENTRY_POSITION + "}";

    //users
    public static final String USERS_API_SUFFIX = "/users";
    public static final String LOGIN_API_SUFFIX = "/login";
    public static final String USERS_LOGIN_API_SUFFIX = USERS_API_SUFFIX + LOGIN_API_SUFFIX;
    public static final String USERS_API = API_PREFIX + USERS_API_SUFFIX;
    public static final String USERS_LOGIN_API = API_PREFIX + USERS_LOGIN_API_SUFFIX;

    //profile
    public static final String PROFILE_API_SUFFIX = "/profile";
    public static final String BIOMETRICS_API_SUFFIX = "/biometrics";
    public static final String HIGHLIGHTED_TARGETS_API_SUFFIX = "/highlightedTargets";
    public static final String PROFILE_BIOMETRICS_API_SUFFIX = PROFILE_API_SUFFIX + BIOMETRICS_API_SUFFIX;
    public static final String PROFILE_HIGHLIGHTED_TARGETS_API_SUFFIX = PROFILE_API_SUFFIX + HIGHLIGHTED_TARGETS_API_SUFFIX;
    public static final String PROFILE_BIOMETRICS_API = API_PREFIX + PROFILE_BIOMETRICS_API_SUFFIX;
    public static final String HIGHLIGHTED_TARGETS_API = API_PREFIX + PROFILE_HIGHLIGHTED_TARGETS_API_SUFFIX;

    //foods
    public static final String FOODS_API_SUFFIX = "/foods";
    public static final String FOOD_ID_API_SUFFIX = "/" + FOOD_ID_PATH_PARAM;
    public static final String FOODS_API = API_PREFIX + FOODS_API_SUFFIX;
    public static final String FOOD_ID_API = FOODS_API + FOOD_ID_API_SUFFIX;

    //exercises
    public static final String EXERCISES_API_SUFFIX = "/exercises";
    public static final String EXERCISES_SEARCH_API_SUFFIX = EXERCISES_API_SUFFIX + "/search";
    public static final String EXERCISES_SEARCH_API = API_PREFIX + EXERCISES_SEARCH_API_SUFFIX;

    //diary
    public static final String DIARY_API_SUFFIX = "/diary";
    public static final String ENTRIES_API_SUFFIX = "/entries";
    public static final String DIARY_DAY_API_SUFFIX = DIARY_API_SUFFIX + "/" + DATE_PATH_PARAM;
    public static final String ENTRIES_POSITION_API_SUFFIX = ENTRIES_API_SUFFIX + "/" + ENTRY_POSITION_PATH_PARAM;
    public static final String DIARY_DAY_ENTRIES_API_SUFFIX = DIARY_DAY_API_SUFFIX + ENTRIES_POSITION_API_SUFFIX;
    public static final String DIARY_DAY_API = API_PREFIX + DIARY_DAY_API_SUFFIX;
    public static final String DIARY_DAY_ENTRIES_API = DIARY_DAY_API + ENTRIES_API_SUFFIX;
    public static final String DIARY_DAY_POSITION_API = API_PREFIX + DIARY_DAY_ENTRIES_API_SUFFIX;

}
