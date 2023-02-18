package fr.unice.polytech.si3.qgl.les_genies.game.gameELements;

import fr.unice.polytech.si3.qgl.les_genies.Cockpit;
import fr.unice.polytech.si3.qgl.les_genies.game.game_element.Game;
import fr.unice.polytech.si3.qgl.les_genies.game.game_element.InitGame;
import fr.unice.polytech.si3.qgl.les_genies.game.tools.Position;
import fr.unice.polytech.si3.qgl.les_genies.game.visible_entities.VisibleEntities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.spec.PSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    Game game;
    Cockpit c;

    private String INIT_GAME;

    @BeforeEach
    void setUp(){
        INIT_GAME = getStringFromJson("CockpitGameInit.json");

        c = new Cockpit();
        c.initGame(INIT_GAME);
        this.game = c.getGame();
    }

    String getStringFromJson(String fileName){
        String s = null;
        Path pathGame = Paths.get(System.getProperty("user.dir") + "/src/test/JsonTestsFiles/" + fileName);
        try {
            s = Files.readString(pathGame);
        }catch (IOException e){
            e.printStackTrace();
        }

        return s;
    }

    @Test
    void toStringNullTest(){
        Game g = new Game(new InitGame(), true);

        assertEquals("Game{goal=null, ship=null, sailors=null, shipCount=0, visibleEntities=null, wind=null}",g.toString());
    }

    @Test
    void NextRoundNotNull(){
        String round = getStringFromJson("Round.json");
        String s = c.nextRound(round);
        assertTrue(s.startsWith("[") && s.endsWith("]"));
        assertNotNull(game.getWind());
    }

    @Test
    void GetCheckpointPositionTest(){
        assertNotNull(game.getCurrentCheckpointPosition());
    }

    @Test
    void GetMapTest(){
        assertNotNull(game.getMap());
    }

    @Test
    void CheckDistanceXYAbove0(){
        String init = getStringFromJson("Week10Test.json");

        Cockpit cockpit = new Cockpit();
        cockpit.initGame(init);
        assertTrue(cockpit.getGame().getDistanceX()!=0 && cockpit.getGame().getDistanceY()!=0);
    }

    @Test
    void addDistanceTest(){
        game.getMinCoordinates(new Position(0,0));
        assertEquals(0,game.getDistanceX());
        assertEquals(0,game.getDistanceY());

        game.getMinCoordinates(new Position(1000,50));
        assertEquals(0,game.getDistanceX());
        assertEquals(0,game.getDistanceY());

        game.getMinCoordinates(new Position(-1000,-50));
        assertEquals(-1000,game.getDistanceX());
        assertEquals(-50,game.getDistanceY());
    }

    @Test
    void ChangeDistancePositionTest(){
        game.getMinCoordinates(new Position(-1000,-50));
        assertEquals(-1000,game.getDistanceX());
        assertEquals(-50,game.getDistanceY());

        Position p = new Position(0,0);
        game.addDistance(p);

        assertEquals(2000, p.getX() );
        assertEquals(5050, p.getY() );
    }

    @Test
    void CheckPositionAsChange(){
        int count = 0;

        String init = getStringFromJson("Week10Test.json");

        Cockpit cockpit = new Cockpit();
        cockpit.initGame(init);

        String round = getStringFromJson("Round.json");
        cockpit.nextRound(round);


        for (VisibleEntities v : cockpit.getGame().getVisibleEntities()){
            if(v.getPosition().getX() < 0 || v.getPosition().getY() < 0) count++;
        }

        assertEquals(0, count);
    }
}
