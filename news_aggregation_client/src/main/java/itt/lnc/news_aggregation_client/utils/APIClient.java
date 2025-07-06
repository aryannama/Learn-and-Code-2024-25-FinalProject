package itt.lnc.news_aggregation_client.utils;

import itt.lnc.news_aggregation_client.exception.InvalidRequestException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class APIClient {
    private static final HttpClient httpClient = HttpClient.newHttpClient();

    public static HttpResponse<String> get(String url) {
        return sendRequest(url, "GET", null, null);
    }

    public static HttpResponse<String> get(String url, String token) {
        return sendRequest(url, "GET", null, token);
    }

    public static HttpResponse<String> post(String url, String token) {
        return sendRequest(url, "POST", null, token);
    }

    public static HttpResponse<String> post(String url, String body, String token) {
        return sendRequest(url, "POST", body, token);
    }

    public static HttpResponse<String> put(String url, String body, String token) {
        return sendRequest(url, "PUT", body, token);
    }

    public static HttpResponse<String> put(String url, String token) {
        return sendRequest(url, "PUT", null, token);
    }

    public static HttpResponse<String> patch(String url, String body, String token) {
        return sendRequest(url, "PATCH", body, token);
    }

    public static HttpResponse<String> patch(String url, String token) {
        return sendRequest(url, "PATCH", null, token);
    }


    public static HttpResponse<String> delete(String url, String token) {
        return sendRequest(url, "DELETE", null, token);
    }

    private static HttpResponse<String> sendRequest(String url, String method, String body, String token) {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Accept", "application/json");

        if (token != null && !token.isBlank()) {
            builder.header("Authorization", "Bearer " + token);
        }

        if (body != null) {
            builder.header("Content-Type", "application/json");
            builder.method(method, HttpRequest.BodyPublishers.ofString(body));
        } else {
            builder.method(method, HttpRequest.BodyPublishers.noBody());
        }

        try {
            return httpClient.send(builder.build(), HttpResponse.BodyHandlers.ofString());
        } catch (Exception exception) {
            throw new InvalidRequestException("HTTP " + method + " failed: " + exception.getMessage());
        }
    }

}
