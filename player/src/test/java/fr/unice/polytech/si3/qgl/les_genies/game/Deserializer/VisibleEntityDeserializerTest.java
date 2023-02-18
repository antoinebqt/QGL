package fr.unice.polytech.si3.qgl.les_genies.game.Deserializer;

import fr.unice.polytech.si3.qgl.les_genies.Cockpit;
import fr.unice.polytech.si3.qgl.les_genies.game.deserializer.JsonRW;
import fr.unice.polytech.si3.qgl.les_genies.game.game_element.Game;
import fr.unice.polytech.si3.qgl.les_genies.game.game_element.NextRound;
import fr.unice.polytech.si3.qgl.les_genies.game.visible_entities.OtherShip;
import fr.unice.polytech.si3.qgl.les_genies.game.visible_entities.VisibleEntities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class VisibleEntityDeserializerTest {
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
        game = c.getGame();

        Path pathRound = Paths.get(System.getProperty("user.dir") + "/src/test/JsonTestsFiles/VisibleEntities.json");

        try {
            String round = Files.readString(pathRound);
            NextRound nextRound = JsonRW.parseFromJson(round, NextRound.class);
            game.updateGame(nextRound);
        }  catch (Exception e){
            System.out.println("Ca marche pas");
        }
    }

    @Test
    void parserTest(){
        for(VisibleEntities e : game.getVisibleEntities()){
            assertNotNull(e.getPosition());
            assertNotNull(e.getShape());
        }
        assertTrue(game.getVisibleEntities().get(4) instanceof OtherShip);
    }
}
