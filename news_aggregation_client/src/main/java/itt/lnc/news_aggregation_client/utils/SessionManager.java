package itt.lnc.news_aggregation_client.utils;

import lombok.Data;

public class SessionManager {
    private static String accessToken;
    private static String name;
    private static String email;
    private static String role;

    public static String getAccessToken() {
        return accessToken;
    }

    public static void setAccessToken(String accessToken) {
        SessionManager.accessToken = accessToken;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        SessionManager.name = name;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        SessionManager.email = email;
    }

    public static String getRole() {
        return role;
    }

    public static void setRole(String role) {
        SessionManager.role = role;
    }

    public static void clear() {
        accessToken = null;
        role = null;
    }
}
