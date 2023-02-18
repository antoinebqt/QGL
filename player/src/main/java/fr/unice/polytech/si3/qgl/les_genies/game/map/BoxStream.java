package fr.unice.polytech.si3.qgl.les_genies.game.map;

import fr.unice.polytech.si3.qgl.les_genies.game.visible_entities.Stream;

public class BoxStream {
    private final double orientation;
    private final double power;

    public BoxStream(Stream s){
        this.orientation = s.getPosition().getOrientation();
        this.power = s.getStrength();
    }

    public double getOrientation() {
        return orientation;
    }

    public double getPower() {
        return power;
    }
}
