package fr.unice.polytech.si3.qgl.les_genies.game.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.les_genies.Cockpit;
import fr.unice.polytech.si3.qgl.les_genies.game.shapes.*;
import fr.unice.polytech.si3.qgl.les_genies.game.tools.Constants;

import java.io.IOException;


public class ShapeDeserializer extends JsonDeserializer<Shape> {
    ObjectMapper mapper = new ObjectMapper();

    @Override
    public Shape deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = mapper.readTree(jsonParser);

        return getShapeFromJSON(node);
    }

    Shape getShapeFromJSON(JsonNode node){
        switch (node.get("type").asText()){
            case Constants.CIRCLE:
                    return new Circle(node.get("radius").asDouble());
            case Constants.RECTANGLE:
                try {
                    return new Rectangle(node.get("width").asDouble(), node.get("height").asDouble(), node.get("orientation").asDouble());
                } catch (Exception e) {
                    try {
                        return new Rectangle(node.get("width").asDouble(), node.get("height").asDouble());
                    } catch (Exception e2) {
                        Cockpit.addLog(e.getMessage());
                        Cockpit.addLog(e2.getMessage());
                        break;
                    }
                }
            case Constants.POLYGONE:
                    return new Polygone(deserializeToList(node.get("vertices")));
            default:
                throw new IllegalStateException("Unexpected value: " + node.get("type").asText());
        }
        return null;
    }

    private Point[] deserializeToList(JsonNode vertices) {
        int s = vertices.size();
        Point[] list = new Point[s];
        int i = 0;
        for (JsonNode n : vertices) {
            list[i] = new Point(n.get("x").asDouble(),n.get("y").asDouble());
            i++;
        }
        return list;
    }
}
