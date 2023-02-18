package fr.unice.polytech.si3.qgl.les_genies.game.game_element;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.si3.qgl.les_genies.game.checkpoint.RegattaGoal;
import fr.unice.polytech.si3.qgl.les_genies.game.ship.Ship;
import fr.unice.polytech.si3.qgl.les_genies.game.crew.Sailor;
import java.util.Arrays;


public class InitGame {
    @JsonProperty("goal")
    private RegattaGoal goal;
    @JsonProperty("ship")
    private Ship ship;
    @JsonProperty("sailors")
    private Sailor[] sailors;
    @JsonProperty("shipCount")
    private int shipCount;

    public RegattaGoal getGoal() {
        return goal;
    }

    public Ship getShip() {
        return ship;
    }

    public Sailor[] getSailors() {
        return sailors;
    }

    public int getShipCount() {
        return shipCount;
    }

    @Override
    public String toString() {
        return "InitGame{" +
                "goal=" + goal +
                ", ship=" + ship +
                ", sailors=" + Arrays.toString(sailors) +
                ", shipCount=" + shipCount +
                '}';
    }
}