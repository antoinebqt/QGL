package fr.unice.polytech.si3.qgl.les_genies.game.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.les_genies.game.shapes.Shape;
import fr.unice.polytech.si3.qgl.les_genies.game.tools.Constants;
import fr.unice.polytech.si3.qgl.les_genies.game.tools.Position;
import fr.unice.polytech.si3.qgl.les_genies.game.visible_entities.OtherShip;
import fr.unice.polytech.si3.qgl.les_genies.game.visible_entities.Reef;
import fr.unice.polytech.si3.qgl.les_genies.game.visible_entities.Stream;
import fr.unice.polytech.si3.qgl.les_genies.game.visible_entities.VisibleEntities;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VisibleEntityDeserializer extends JsonDeserializer<List<VisibleEntities>> {

    @Override
    public List<VisibleEntities> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(jsonParser);
        List<VisibleEntities> visibleEnt = new ArrayList<>();

        for(JsonNode n : node){
            VisibleEntities visibleEntities = getVisibleEntityFromJSON(n);
            visibleEnt.add(visibleEntities);
        }
        return visibleEnt;
    }

    VisibleEntities getVisibleEntityFromJSON(JsonNode node){
        String shape = "shape";
        String position = "position";
        return switch (node.get("type").asText()) {
            case Constants.STREAM -> new Stream(deserPos(node.get(position)), deserShape(node.get(shape)), node.get("strength").asInt());
            case Constants.REEF -> new Reef(deserPos(node.get(position)), deserShape(node.get(shape)));
            case Constants.SHIP -> new OtherShip(deserPos(node.get(position)), deserShape(node.get(shape)), node.get("life").asInt());
            default -> throw new IllegalStateException("Unexpected bite value: " + node.get("type").asText());
        };
    }

    /**
     * deserializer of Position while we deserialize an visibleEntity
     * @param jsonPos Position node
     * @return a Position
     */
    private Position deserPos(JsonNode jsonPos){
        return new Position(jsonPos.get("x").asDouble(),jsonPos.get("y").asDouble(),jsonPos.get("orientation").asDouble());
    }

    /**
     * deserializer of shape while we deserialize an visibleEntity
     * @param jsonShape shape node
     * @return a shape
     */
    private Shape deserShape(JsonNode jsonShape){
        ShapeDeserializer shapeDeserializer = new ShapeDeserializer();
        return  shapeDeserializer.getShapeFromJSON(jsonShape);
    }
}
