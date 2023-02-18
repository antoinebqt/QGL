package fr.unice.polytech.si3.qgl.les_genies.game.logic;

import fr.unice.polytech.si3.qgl.les_genies.Cockpit;
import fr.unice.polytech.si3.qgl.les_genies.game.ship.Ship;
import fr.unice.polytech.si3.qgl.les_genies.game.game_element.Game;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogicRotationTest {

    String jsonStart = """
                {
                  "goal": {
                    "mode": "REGATTA",
                    "checkpoints": [
                      {
                      "position": {
               """;
    String jsonMid = """
                                   "orientation": 0
                                },
                                "shape": {
                                  "type": "circle",
                                  "radius": 50
                                }
                              }
                            ]
                          },
                          "ship": {
                            "type": "ship",
                            "life": 100,
                            "position": {
               """;
    String jsonEnd = """
                            },
                            "name": "Les copaings d'abord!",
                            "deck": {
                               "width": 3,
                               "length": 6
                            },
                            "entities": [
                               {
                                   "x": 1,
                                   "y": 0,
                                   "type": "oar"
                               },
                               {
                                   "x": 1,
                                   "y": 2,
                                   "type": "oar"
                               },
                               {
                                   "x": 3,
                                   "y": 0,
                                   "type": "oar"
                               },
                               {
                                   "x": 3,
                                   "y": 2,
                                   "type": "oar"
                               },
                               {
                                   "x": 4,
                                   "y": 0,
                                   "type": "oar"
                               },
                               {
                                   "x": 4,
                                   "y": 2,
                                   "type": "oar"
                               }
                           ],
                            "shape": {
                               "type": "rectangle",
                               "width": 3,
                               "height": 6,
                               "orientation": 0
                            }
                          },
                           "sailors": [
                               {
                                   "x": 2,
                                   "y": 1,
                                   "id": 0,
                                   "name": "Edward Teach"
                               }
                           ],
                           "shipCount": 1
                           }
                """;


    void oarCalculationAndTest(String json, double a, double b, double c){
        Cockpit cockpit = new Cockpit();
        cockpit.initGame(json);

        Game game = cockpit.getGame();
        Ship ship = game.getShip();
        LogicRotation logicRotation = new LogicRotation(ship);

        logicRotation.computeRotationData(game.getGoal().getCurrentCheckpoint());
        OarConfig result = logicRotation.getOarConfig();
        double angleRudder = logicRotation.getAngleRudder();

        assertEquals(a,result.getOarLeftSide());
        assertEquals(b,result.getOarRightSide());
        assertEquals(c, angleRudder);
    }

    void oarCalculationWrongTest(String json, double a, double b, double c){
        Cockpit cockpit = new Cockpit();
        cockpit.initGame(json);

        Game game = cockpit.getGame();
        Ship ship = game.getShip();
        LogicRotation logicRotation = new LogicRotation(ship);

        logicRotation.computeRotationData(game.getGoal().getCurrentCheckpoint());
        OarConfig result = logicRotation.getOarConfig();
        double angleRudder = logicRotation.getAngleRudder();

        assertEquals(a,result.getOarLeftSide());
        assertEquals(b,result.getOarRightSide());
        assertNotEquals(c,angleRudder);
    }
    @Test
    void oarCalculationTestWithCheckpointForward() {
        oarCalculationAndTest(createJson(10,0,0,0,0),0,0,0);
    }

    @Test
    void oarCalculationTestWithCheckpointBarelyForwardUp() {
        oarCalculationWrongTest(createJson(10,3,0,0,0),0,0,0);
    }

    @Test
    void oarCalculationTestWithCheckpointForwardUp() {
        oarCalculationWrongTest(createJson(10,12,0,0,0),0,2,0);
    }

    @Test
    void oarCalculationTestWithCheckpointForwardInDiagUp() {
        oarCalculationAndTest(createJson(10,10,0,0,0),0,0,Math.PI/4);
    }

    @Test
    void oarCalculationTestWithCheckpointBarelyPerpendicularUp() {
        oarCalculationWrongTest(createJson(1,10,0,0,0),0,3,0);
    }

    @Test
    void oarCalculationTestWithCheckpointBarelyForwardDown() {
        oarCalculationWrongTest(createJson(10,-3,0,0,0),0,0,0);
    }

    @Test
    void oarCalculationTestWithCheckpointForwardDown() {
        oarCalculationWrongTest(createJson(10,-12,0,0,0),2,0,0);
    }

    @Test
    void oarCalculationTestWithCheckpointForwardInDiagDown() {
        oarCalculationAndTest(createJson(10,-10,0,0,0),0,0,-Math.PI / 4);
    }

    @Test
    void oarCalculationTestWithCheckpointBarelyPerpendicularDown() {
        oarCalculationWrongTest(createJson(1,-10,0,0,0),3,0,0);

    }

    @Test
    void oarCalculationTestWithCheckpointBackwardUp() {
        oarCalculationAndTest(createJson(-10,2,0,0,0),0,3,Math.PI / 4);
    }

    @Test
    void oarCalculationTestWithCheckpointBackwardDown() {
        oarCalculationAndTest(createJson(-10,-2,0,0,0),3,0,-Math.PI / 4);
    }

    @Test
    void radToDegWith0Rad(){
        LogicRotation logicRotation = new LogicRotation(new Ship());
        assertEquals(0, logicRotation.radToDeg(0));
    }

    @Test
    void radToDegWithPiOn2Rad(){
        LogicRotation logicRotation = new LogicRotation(new Ship());
        assertEquals(90, logicRotation.radToDeg(Math.PI/2));
    }

    @Test
    void radToDegWithMinusPiOn4Rad(){
        LogicRotation logicRotation = new LogicRotation(new Ship());
        assertEquals(-45, logicRotation.radToDeg(-Math.PI/4));
    }

    @Test
    void possibleAngleWith0Oar() {
        LogicRotation logicRotation = new LogicRotation(new Ship());
        assertEquals(1, logicRotation.possibleAngles(0).length);
    }

    @Test
    void possibleAngleWith2Oars() {
        LogicRotation logicRotation = new LogicRotation(new Ship());
        assertEquals(3, logicRotation.possibleAngles(2).length);
    }

    @Test
    void possibleAngleWith4Oars() {
        LogicRotation logicRotation = new LogicRotation(new Ship());
        assertEquals(5, logicRotation.possibleAngles(4).length);
    }

    @Test
    void possibleAngleWith632Oars() {
        LogicRotation logicRotation = new LogicRotation(new Ship());
        assertEquals(633, logicRotation.possibleAngles(632).length);
    }

    private String createJson(int xCheckpoint, int yCheckpoint, int xShip, int yShip, double orientationShip){
        String checkpointJson = """
                "x":\040
                """ + xCheckpoint + "," + """
                "y":\040
                """ + yCheckpoint + ",";

        String shipJson = """
                "x":\040
                """ + xShip + "," + """
                "y":\040
                """ + yShip + "," + """
                "orientation":\040
                """ + orientationShip;

        return jsonStart + checkpointJson + jsonMid + shipJson + jsonEnd;
    }

    @Test
    void angleToCheckpointNullTest(){
        Cockpit cockpit = new Cockpit();
        cockpit.initGame(createJson(100,0,0,0,0));

        LogicRotation rotation = new LogicRotation(cockpit.getGame().getShip());
        assertEquals(0,rotation.angleToCheckpoint(cockpit.getGame().getGoal().getCurrentCheckpoint()));
    }

    @Test
    void angleToCheckpointPiTest(){
        Cockpit cockpit = new Cockpit();
        cockpit.initGame(createJson(-100,0,0,0,0));

        LogicRotation rotation = new LogicRotation(cockpit.getGame().getShip());
        assertEquals(Math.PI,rotation.angleToCheckpoint(cockpit.getGame().getGoal().getCurrentCheckpoint()));
    }

    @Test
    void angleToCheckpointPiDivideBy4Test(){
        Cockpit cockpit = new Cockpit();
        cockpit.initGame(createJson(100,100,0,0,0));

        LogicRotation rotation = new LogicRotation(cockpit.getGame().getShip());
        assertEquals(Math.PI/4,rotation.angleToCheckpoint(cockpit.getGame().getGoal().getCurrentCheckpoint()));
    }

    @Test
    void correctBiggerAngleTest(){
        Cockpit cockpit = new Cockpit();
        cockpit.initGame(createJson(100,100,0,0,0));

        LogicRotation rotation = new LogicRotation(cockpit.getGame().getShip());

        double a = 3*Math.PI/2;

        double newAngle = rotation.correctAngle(a);

        assertEquals(-Math.PI/2,newAngle);
    }

    @Test
    void correctSmallerAngleTest(){
        Cockpit cockpit = new Cockpit();
        cockpit.initGame(createJson(100,100,0,0,0));

        LogicRotation rotation = new LogicRotation(cockpit.getGame().getShip());

        double a = -3*Math.PI/2;

        double newAngle = rotation.correctAngle(a);

        assertEquals(Math.PI/2,newAngle);
    }

    @Test
    void dontCorrectAngleTest(){
        Cockpit cockpit = new Cockpit();
        cockpit.initGame(createJson(100,100,0,0,0));

        LogicRotation rotation = new LogicRotation(cockpit.getGame().getShip());

        double a = Math.PI/2;
        double newAngle = rotation.correctAngle(a);

        assertEquals(a,newAngle);
    }

    @Test
    void angleAjustementTest(){
        Cockpit cockpit = new Cockpit();
        cockpit.initGame(createJson(100,100,0,0,0));

        LogicRotation rotation = new LogicRotation(cockpit.getGame().getShip());

        double angle = rotation.angleAjustement(Math.PI, Math.PI/2);
        assertEquals(Math.PI/4,angle);

        angle = rotation.angleAjustement(-Math.PI, -Math.PI/2);
        assertEquals(-Math.PI/4,angle);

        angle = rotation.angleAjustement(Math.PI/2, Math.PI/2);
        assertEquals(0,angle);

        angle = rotation.angleAjustement(Math.PI*2/6, Math.PI/6);
        assertEquals(Math.PI/6,angle);

        angle = rotation.angleAjustement(-Math.PI*2/6, -Math.PI/6);
        assertEquals(-Math.PI/6,angle);
    }

    @Test
    void getTest() {
        Cockpit cockpit = new Cockpit();
        cockpit.initGame(createJson(100, 100, 0, 0, 0));

        LogicRotation rotation = new LogicRotation(cockpit.getGame().getShip());
        assertEquals(0,rotation.getAngle());
    }

    @Test
    void bestAngleTest() {
        Cockpit cockpit = new Cockpit();
        cockpit.initGame(createJson(100, 100, 0, 0, 0));

        LogicRotation rotation = new LogicRotation(cockpit.getGame().getShip());
        double[] angles = {-3,-2,-1,0,1,2,3};
        assertEquals(-3,rotation.bestAngle(-3.14, angles));
    }
}
