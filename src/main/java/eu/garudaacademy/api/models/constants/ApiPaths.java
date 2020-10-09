package eu.garudaacademy.api.models.constants;

public class ApiPaths {
    public static final String AUTHENTICATION_BASE = "/authentication";
    public static final String AUTHENTICATION_AUTHENTICATE = "/authenticate";

    public static final String VIDEOS_BASE = "/videos";
    public static final String VIDEOS_GET_BY_CATEGORY = "/get-by-category/{categoryId}";

    public static final String CATEGORIES_BASE = "/categories";
    public static final String CATEGORIES_GET_WITH_PURCHASES = "/get-with-purchases/{userId}";

    public static final String PURCHASES_BASE = "/purchases";

    public static final String USERS_BASE = "/users";

    public static final String GET_ALL = "/get-all";
    public static final String GET_BY_ID = "/get/{id}";
    public static final String CREATE = "/create";
    public static final String UPDATE = "/update/{id}";
    public static final String DELETE = "/delete/{id}";



}
