package fr.unice.polytech.si3.qgl.les_genies.game.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.les_genies.game.entities.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EntityDeserializer extends JsonDeserializer<List<Entity>> {

    @Override
    public List<Entity> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(jsonParser);
        List<Entity> entities = new ArrayList<>();

        for(JsonNode n : node){
            Entity entity = getEntityFromJSON(n);
            entities.add(entity);
        }
        return entities;
    }


    Entity getEntityFromJSON(JsonNode node){
        switch (node.get("type").asText()) {
            case "oar":
                    return new Oar(node.get("x").asInt(), node.get("y").asInt());
            case "rudder":
                    return new Rudder(node.get("x").asInt(), node.get("y").asInt());
            case "sail":
                boolean opened = false;
                JsonNode nodeOpened = node.get("openned");
                if (nodeOpened != null)
                    opened = node.get("openned").asBoolean();
                return new Sail(
                        node.get("x").asInt(),
                        node.get("y").asInt(),
                        opened
                );
            case "watch":
                    return new Watch(node.get("x").asInt(), node.get("y").asInt());
            default:
                throw new IllegalStateException("Unexpected value: " + node.get("type").asText());
        }
    }
}

