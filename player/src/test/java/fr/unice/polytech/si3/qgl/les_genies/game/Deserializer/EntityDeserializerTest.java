package fr.unice.polytech.si3.qgl.les_genies.game.Deserializer;

import fr.unice.polytech.si3.qgl.les_genies.Cockpit;
import fr.unice.polytech.si3.qgl.les_genies.game.entities.Entity;
import fr.unice.polytech.si3.qgl.les_genies.game.game_element.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EntityDeserializerTest {

    private Game game;
    private String INIT_GAME;

    @BeforeEach
    void setUp(){
        Path pathGame = Paths.get(System.getProperty("user.dir") + "/src/test/JsonTestsFiles/CockpitGameInitDeserializer.json");
        try {
            INIT_GAME = Files.readString(pathGame);
        }catch (IOException e){
            e.printStackTrace();
        }

        Cockpit c = new Cockpit();
        c.initGame(INIT_GAME);
        this.game = c.getGame();
    }

    @Test
    void listEntitiesTest(){
        int oarCount = 0;
        int sailCount = 0;
        int rudderCount = 0;
        int elseCount = 0;

        for(Entity e : game.getShip().getEntities()){
            switch (e.getType()) {
                case "oar" -> oarCount++;
                case "sail" -> sailCount++;
                case "rudder" -> rudderCount++;
                default -> elseCount++;
            }
        }

        assertEquals(2,sailCount);
        assertEquals(10,oarCount);
        assertEquals(1,rudderCount);
        assertEquals(0,elseCount);

    }


}
