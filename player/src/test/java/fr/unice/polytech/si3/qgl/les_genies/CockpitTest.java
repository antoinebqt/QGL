package fr.unice.polytech.si3.qgl.les_genies;


import fr.unice.polytech.si3.qgl.les_genies.game.game_element.Game;
import fr.unice.polytech.si3.qgl.les_genies.game.ship.Ship;
import fr.unice.polytech.si3.qgl.les_genies.game.tools.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static fr.unice.polytech.si3.qgl.les_genies.Cockpit.log;
import static org.junit.jupiter.api.Assertions.*;

class CockpitTest {

    Cockpit cockpit;

    private String INIT_GAME;
    private String ROUND;

    @BeforeEach
    void setUp(){
        Path pathGame = Paths.get(System.getProperty("user.dir") + "/src/test/JsonTestsFiles/CockpitGameInit.json");
        Path pathRound = Paths.get(System.getProperty("user.dir") + "/src/test/JsonTestsFiles/CockpitRound.json");
        try {
            INIT_GAME = Files.readString(pathGame);
            ROUND = Files.readString(pathRound);
        }catch (IOException e){
            e.printStackTrace();
        }

        this.cockpit = new Cockpit();
    }

    @Test
    void nextRoundTest() {
        assertEquals("[]", cockpit.nextRound("{}"));
    }

    /**
     * test :
     *  1) l'initialisation du jeu via json
     *  2) l'initialisation du round a venir via json
     *  avec test sur les valeurs ayant ete mis a jour
     *  ( test aussi que l'on a bien acces a tous les elements du jeu )
     */
    @Test
    void initGame_ok_plus_nextRound_ok(){

        this.cockpit.initGame(INIT_GAME);
        Game jeu = cockpit.getGame();
        assertNotEquals(null,jeu);
        Ship ship = jeu.getShip();

        assertEquals(Constants.SHIP,ship.getType());
        assertEquals(100,ship.getLife());
        assertEquals(1000,ship.getPosition().getX());
        assertEquals(5000,ship.getPosition().getY());
        assertEquals("Les copaings d'abord!",ship.getName());
        assertEquals(6,ship.getDeck().getLength());
        assertEquals(3,ship.getDeck().getWidth());
        assertEquals("oar",ship.getEntities().get(0).getType());
        assertEquals(Constants.RECTANGLE,ship.getShape().getType());
        assertEquals("Edward Pouce",jeu.getSailors()[1].getName());
        assertEquals("Edward Teach",jeu.getSailors()[0].getName());
        assertEquals(Constants.CIRCLE,jeu.getGoal().getCurrentCheckpoint().getShape().getType());

        // obliger de passer par un cast pour recuperer les methodes propres aux methodes des classes filles de shapes
        assertEquals(50,jeu.getGoal().getCurrentCheckpoint().getShape().getRadius());
    }

    @Test
    void logTest(){
        int s = log.size();
        Cockpit.addLog("je suis un test");

        assertEquals(s+1, log.size());
    }

    @Test
    void GameTestException() {
        int indexLog = cockpit.getLogs().size();
        cockpit.initGame("");
        cockpit.nextRound(ROUND);
        assertEquals("Error :com.fasterxml.jackson.databind.exc.MismatchedInputException: Cannot deserialize value of type `fr.unice.polytech.si3.qgl.les_genies.game.game_element.InitGame` from [Unavailable value] (token `JsonToken.NOT_AVAILABLE`)\n" +
                " at [Source: UNKNOWN; byte offset: #UNKNOWN]", cockpit.getLogs().get(indexLog));
        assertEquals("Game is null | Error :java.lang.NullPointerException: Cannot invoke \"fr.unice.polytech.si3.qgl.les_genies.game.game_element.Game.updateGame(fr.unice.polytech.si3.qgl.les_genies.game.game_element.NextRound)\" because \"this.game\" is null",
                cockpit.getLogs().get(indexLog+1));
    }

    @Test
    void NextRoundTestExceptionJson() {
        int indexLog = cockpit.getLogs().size();
        cockpit.initGame(INIT_GAME);
        cockpit.nextRound("");
        assertEquals("Json issue | Error :com.fasterxml.jackson.databind.exc.MismatchedInputException: Cannot deserialize value of type `fr.unice.polytech.si3.qgl.les_genies.game.game_element.NextRound` from [Unavailable value] (token `JsonToken.NOT_AVAILABLE`)\n" +
                " at [Source: UNKNOWN; byte offset: #UNKNOWN]", cockpit.getLogs().get(indexLog));
    }

    @Test
    void NextRoundTestNoError() {
        int indexLog = cockpit.getLogs().size();
        cockpit.initGame(INIT_GAME);
        cockpit.nextRound(ROUND);
        assertEquals("Angle : -1.5739652175758199 ( -90.18156406748483 degrees) to be aligned checkpoint : 0", cockpit.getLogs().get(indexLog));
    }

    @Test
    void emptyLogsTest() {
        cockpit.getLogs().clear();
        assertEquals(0, cockpit.getLogs().size());
    }
}
