package itt.lnc.news_aggregation.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import itt.lnc.news_aggregation.exception.JsonParsingException;

import java.io.IOException;
import java.util.List;

import static itt.lnc.news_aggregation.constants.ExceptionMessages.INVALID_JSON_FORMAT;

public class JsonParser {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T parse(String json, TypeReference<T> classType) {
        try {
            return objectMapper.readValue(json, classType);
        } catch (JsonProcessingException e) {
            throw new JsonParsingException(String.format(INVALID_JSON_FORMAT, e.getMessage()));
        }
    }

    public static <T> List<T> parseList(JsonNode arrayNode, Class<T> classType) {
        try {
            return objectMapper.readerForListOf(classType).readValue(arrayNode);
        } catch (IOException e) {
            throw new JsonParsingException(String.format(INVALID_JSON_FORMAT, e.getMessage()));
        }
    }

    public static JsonNode toJsonNode(String json) {
        try {
            return objectMapper.readTree(json);
        } catch (JsonProcessingException e) {
            throw new JsonParsingException(String.format(INVALID_JSON_FORMAT, e.getMessage()));
        }
    }
}
