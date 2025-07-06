package itt.lnc.news_aggregation_client.constants;

public class Urls {
    public static final String BASE_URL = "http://localhost:8080";
    public static final String AUTH_URL = BASE_URL + "/api/auth";
    public static final String LOGIN_URL = AUTH_URL + "/login";
    public static final String REGISTER_URL = AUTH_URL + "/register";
    public static final String CURRENT_USER_URL = BASE_URL + "/api/users/me";
    public static final String EXTERNAL_SERVER_URL = BASE_URL + "/api/external-servers";
    public static final String EXTERNAL_SERVER_BY_ID_URL = EXTERNAL_SERVER_URL + "/%s";
    public static final String CATEGORIES_URL = BASE_URL + "/api/categories";
    public static final String HIDE_CATEGORY_URL = CATEGORIES_URL + "/%s/hide";
    public static final String UNHIDE_CATEGORY_URL = CATEGORIES_URL + "/%s/unhide";
    public static final String BLOCKED_KEYWORDS_URL = BASE_URL + "/api/blocked-keywords";
    public static final String BLOCKED_KEYWORD_BY_ID_URL = BLOCKED_KEYWORDS_URL + "/%s";
    public static final String REPORTED_ARTICLES_URL = BASE_URL + "/api/reported-articles";
    public static final String HIDE_REPORTED_ARTICLE_URL = REPORTED_ARTICLES_URL + "/%s/hide";
    public static final String ARTICLES_URL = BASE_URL + "/api/articles";
    public static final String ARTICLE_BY_ID_URL = ARTICLES_URL + "/%s";
    public static final String READ_ARTICLE_URL = ARTICLES_URL + "/%s/read";
    public static final String REACTIONS_URL = BASE_URL + "/api/reactions/%s";
    public static final String LIKE_ARTICLE_URL = REACTIONS_URL + "/like";
    public static final String DISLIKE_ARTICLE_URL = REACTIONS_URL + "/dislike";
    public static final String REPORT_ARTICLE_URL = REACTIONS_URL + "/report";
    public static final String SAVE_ARTICLE_URL = ARTICLE_BY_ID_URL + "/save";
    public static final String SAVED_ARTICLES_URL = ARTICLES_URL + "/saved";
    public static final String NOTIFICATIONS_URL = BASE_URL + "/api/notifications";
    public static final String NOTIFICATION_CONFIRMATION_URL = BASE_URL + "/api/notification-configurations";
    public static final String NOTIFICATION_CONFIGURATION_CATEGORY = NOTIFICATION_CONFIRMATION_URL + "/%s/toggle";
    public static final String NOTIFICATION_CONFIGURATION_KEYWORD = NOTIFICATION_CONFIRMATION_URL + "/%s/keywords";
}
