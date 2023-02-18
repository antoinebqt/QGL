package fr.unice.polytech.si3.qgl.les_genies.game.visibleEntities;

import fr.unice.polytech.si3.qgl.les_genies.game.visible_entities.Wind;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

class WindTest {
    @Test
    void createWind(){
        Wind wind = new Wind(0,0);
        assertNotEquals("", wind.toString());
    }
}
