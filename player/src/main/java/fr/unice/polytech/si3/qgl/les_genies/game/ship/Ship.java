package fr.unice.polytech.si3.qgl.les_genies.game.ship;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fr.unice.polytech.si3.qgl.les_genies.game.deserializer.EntityDeserializer;
import fr.unice.polytech.si3.qgl.les_genies.game.deserializer.ShapeDeserializer;
import fr.unice.polytech.si3.qgl.les_genies.game.entities.Entity;
import fr.unice.polytech.si3.qgl.les_genies.game.shapes.Shape;
import fr.unice.polytech.si3.qgl.les_genies.game.tools.Position;
import java.util.List;

public class Ship {

    private String type;
    private int life;
    private Position position;
    private String name;
    private Deck deck;
    private List<Entity> entities;
    private Shape shape;

    @JsonSetter("type")
    public void setType(String type) {
        this.type = type;
    }
    @JsonSetter("life")
    public void setLife(int life) {
        this.life = life;
    }
    @JsonSetter("position")
    public void setPosition(Position position) {
        this.position = position;
    }
    @JsonSetter("name")
    public void setName(String name) {
        this.name = name;
    }
    @JsonSetter("deck")
    public void setDeck(Deck deck) {
        this.deck = deck;
    }
    @JsonSetter("entities")
    @JsonDeserialize(using = EntityDeserializer.class)
    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }
    @JsonSetter("shape")
    @JsonDeserialize(using = ShapeDeserializer.class)
    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public String getType() {
        return type;
    }

    public int getLife() {
        return life;
    }

    public Position getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }

    public Deck getDeck() {
        return deck;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void updateShip(Ship ship){
        this.deck = ship.getDeck();
        this.type = ship.getType();
        this.life = ship.getLife();
        this.position = ship.getPosition();
        this.name = ship.getName();
        this.entities = ship.getEntities();
    }

    public Shape getShape() {
        return shape;
    }

    @Override
    public String toString() {

        StringBuilder textEntity = new StringBuilder();

        for (Entity e : entities) {
            if (textEntity.length() != 0) textEntity.append(",");
            textEntity.append(e.toString());
        }

        return "{" +
                "\"type\": \"" + type + "\"" +
                ",\"life\": " + life +
                ",\"position\" : " + position +
                ",\"name\": \"" + name + "\"" +
                ",\"deck\": " + deck +
                ",\"entities\": [" + textEntity + "]" +
                "}";
    }
}
