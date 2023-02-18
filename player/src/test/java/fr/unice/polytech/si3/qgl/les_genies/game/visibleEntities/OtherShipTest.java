package fr.unice.polytech.si3.qgl.les_genies.game.visibleEntities;

import fr.unice.polytech.si3.qgl.les_genies.game.shapes.Shape;
import fr.unice.polytech.si3.qgl.les_genies.game.tools.Constants;
import fr.unice.polytech.si3.qgl.les_genies.game.tools.Position;
import fr.unice.polytech.si3.qgl.les_genies.game.visible_entities.OtherShip;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class OtherShipTest {
    @Test
    void createOtherShip(){
        Position p = new Position(0,0,0);
        Shape s = new Shape();
        OtherShip otherShip = new OtherShip(p,s,100);
        assertEquals(p,otherShip.getPosition());
        assertEquals(s, otherShip.getShape());
        assertEquals(100, otherShip.getLife());
        assertEquals(Constants.SHIP, otherShip.getType());
        assertNotEquals("", otherShip.toString());
    }
}
