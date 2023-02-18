package fr.unice.polytech.si3.qgl.les_genies.game.game_element;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fr.unice.polytech.si3.qgl.les_genies.game.deserializer.VisibleEntityDeserializer;
import fr.unice.polytech.si3.qgl.les_genies.game.ship.Ship;
import fr.unice.polytech.si3.qgl.les_genies.game.visible_entities.VisibleEntities;
import fr.unice.polytech.si3.qgl.les_genies.game.visible_entities.Wind;
import java.util.Arrays;
import java.util.List;

/**
 * Json object create at the call of initialisation of next round
 */
public class NextRound {

    private Ship ship;
    private List<VisibleEntities> visibleEntities;
    private Wind wind;

    @JsonProperty("visibleEntities")
    @JsonDeserialize(using = VisibleEntityDeserializer.class)
    public void setVisibleEntities(List<VisibleEntities> visibleEntities) { this.visibleEntities = visibleEntities; }
    @JsonSetter("ship")
    public void setShip(Ship ship) {
        this.ship = ship;
    }
    @JsonSetter("wind")
    public void setWind(Wind wind) {
        this.wind = wind;
    }


    public Ship getShip() {
        return ship;
    }

    public Wind getWind(){
        return wind;
    }

    public List<VisibleEntities> getVisibleEntities() {
        return visibleEntities;
    }

    @Override
    public String toString() {
        return "NextRound{" +
                "ship=" + ship +
                ", visibleEntities= " + Arrays.toString(visibleEntities.toArray()) +
                ", wind= " + wind.toString() +
                '}';
    }
}
