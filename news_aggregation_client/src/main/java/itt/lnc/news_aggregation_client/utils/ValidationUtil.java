package itt.lnc.news_aggregation_client.utils;

public class ValidationUtil {
    public static boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    public static boolean isNotBlank(String input) {
        return input != null && !input.trim().isEmpty();
    }
}
