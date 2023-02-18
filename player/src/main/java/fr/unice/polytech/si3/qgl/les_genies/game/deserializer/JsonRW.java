package fr.unice.polytech.si3.qgl.les_genies.game.deserializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonRW {

    private JsonRW(){}

    public static final ObjectMapper objectMapper = getDefaultObjectMapper();

    public static ObjectMapper getDefaultObjectMapper() {
        ObjectMapper om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        om.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        return om;
    }

    /*
        READER
     */
    public static JsonNode parse(String str) throws JsonProcessingException {
        return objectMapper.readTree(str);
    }

    public static <A> A fromJson(JsonNode jsonNode, Class<A> clazz) throws JsonProcessingException {
        return objectMapper.treeToValue(jsonNode, clazz);
    }

    public static <A> A parseFromJson(String str, Class<A> clazz) throws JsonProcessingException {
        return fromJson(parse(str), clazz);
    }
}
