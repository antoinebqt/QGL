package fr.unice.polytech.si3.qgl.les_genies.game.visibleEntities;

import fr.unice.polytech.si3.qgl.les_genies.game.shapes.Shape;
import fr.unice.polytech.si3.qgl.les_genies.game.tools.Constants;
import fr.unice.polytech.si3.qgl.les_genies.game.tools.Position;
import fr.unice.polytech.si3.qgl.les_genies.game.visible_entities.Reef;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ReefTest {
    @Test
    void createReef(){
        Position p = new Position(0,0,0);
        Shape s = new Shape();
        Reef reef = new Reef(p,s);
        assertEquals(p,reef.getPosition());
        assertEquals(s, reef.getShape());
        assertEquals(Constants.REEF, reef.getType());
        assertNotEquals("", reef.toString());
    }
}
