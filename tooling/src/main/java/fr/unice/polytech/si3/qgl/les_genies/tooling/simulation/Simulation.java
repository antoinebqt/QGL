package fr.unice.polytech.si3.qgl.les_genies.tooling.simulation;

import fr.unice.polytech.si3.qgl.les_genies.Cockpit;
import fr.unice.polytech.si3.qgl.les_genies.game.checkpoint.Checkpoint;
import fr.unice.polytech.si3.qgl.les_genies.game.crew.Sailor;
import fr.unice.polytech.si3.qgl.les_genies.game.ship.Ship;
import fr.unice.polytech.si3.qgl.les_genies.game.tools.Position;
import fr.unice.polytech.si3.qgl.les_genies.game.tools.Tool;
import fr.unice.polytech.si3.qgl.les_genies.game.visible_entities.Wind;
import fr.unice.polytech.si3.qgl.les_genies.game.entities.Entity;
import fr.unice.polytech.si3.qgl.les_genies.tooling.Application;
import fr.unice.polytech.si3.qgl.les_genies.tooling.moves.Moves;
import fr.unice.polytech.si3.qgl.les_genies.tooling.moves.MovesList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Objects;

public class Simulation {
    private JFrame frame;
    private final Cockpit cockpit;
    private final Map map;
    private int checkpointNumber = 0;
    private final ArrayList<Position> coords = new ArrayList<>();
    private final int checkpointTotal;
    private final static ArrayList<Integer> deadSailors = new ArrayList<>();
    public final Ship simulationShip;

    public Simulation(Cockpit cockpit, Map map) {
        this.cockpit = cockpit;
        this.map = map;
        checkpointTotal = cockpit.getGame().getGoal().getCheckpoints().length;

        Cockpit c = new Cockpit();
        c.initGame(Application.getStringFromJsonFile("InitGame"+Application.WEEK+".json"),false);

        simulationShip = new Ship();
        simulationShip.setPosition(c.getGame().getShip().getPosition());

        this.map.setShipPosition(simulationShip.getPosition());
    }


    public String generateNextRoundData() {
        String roundJson;

        Ship s = cockpit.getGame().getShip();
        simulationShip.setDeck(s.getDeck());
        simulationShip.setShape(s.getShape());
        simulationShip.setEntities(s.getEntities());
        simulationShip.setLife(s.getLife());
        simulationShip.setName(s.getName());
        simulationShip.setType(s.getType());

        NewWind newWind = new NewWind();

        this.map.setShipPosition(simulationShip.getPosition());

        roundJson = "{"+
                "\"ship\": " + simulationShip + "," +
                "\"visibleEntities\": " + map + "," +
                "\"wind\": " + newWind +
                "}";

        return roundJson;
    }

    public void launch() {
        String moves, newJsonRound;
        int round = 0;

        Position boatPosition = cockpit.getGame().getShip().getPosition();
        coords.add(new Position(boatPosition.getX(),boatPosition.getY(),boatPosition.getOrientation()));
        while(stillCheckpoints() && round < 600){
            //if(round== 240)break;
            newJsonRound = generateNextRoundData();
            moves = cockpit.nextRound(newJsonRound);
            calculNewData(moves);

            boatPosition = cockpit.getGame().getShip().getPosition();
            coords.add(new Position(boatPosition.getX(),boatPosition.getY(),boatPosition.getOrientation()));

            checkIsOnCheckpoint();
            round++;
        }
        show(coords);
        System.out.println("Sailors dead : " + deadSailors.size());
        System.out.println("Rounds : " + round);
    }

    private void show(ArrayList<Position> coords) {
        frame = new JFrame();
        frame.getContentPane().setBackground(Color.blue);
        frame.setSize(1500,1000);
        Windows windows = new Windows(coords, cockpit.getGame().getGoal().getCheckpoints(), cockpit.getGame(), map);
        JScrollPane scrPane = new JScrollPane(windows);
        frame.add(scrPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        JMenuBar menubar = new JMenuBar();
        JMenu  menu = new JMenu("Map");
        JMenuItem e;

        for(int i = 1; i<=10;i++){
            e = new JMenuItem(Integer.toString(i));

            e.addActionListener( this::mapListener );
            menu.add(e);
        }

        // Ajouter le menu au barre de menu
        menubar.add(menu);

        // Ajouter la barre de menu au frame
        frame.setJMenuBar(menubar);
        frame.setLayout(null);
        frame.setVisible(true);
    }


    public void calculNewData(String moves) {
        MovesList movesList = new MovesList(moves);
        int oarRight = 0, oarLeft = 0;
        double rotRudder = 0;
        int nbSail = 0, sailOpen = 0;
        int watch = 0;

        for(Sailor sailor:cockpit.getGame().getSailors()){
            if(sailor.getX()<0 || sailor.getY()<0 || sailor.getX()>cockpit.getGame().getShip().getDeck().getLength()||sailor.getY()>cockpit.getGame().getShip().getDeck().getWidth()){
                deadSailors.add(sailor.getId());
            }
        }
        for(Moves m : movesList.getMoves()){
            if(!deadSailors.contains(m.getSailorId())){
                switch (m.getType()) {
                    case "OAR":
                        if (cockpit.getGame().getSailors()[Integer.parseInt(m.getSailorId())].getCoordinates().getCoordinateY() == 0) {
                            oarLeft++;
                        } else if (cockpit.getGame().getSailors()[Integer.parseInt(m.getSailorId())].getCoordinates().getCoordinateY() == cockpit.getGame().getShip().getDeck().getWidth() - 1) {
                            oarRight++;
                        }
                        break;
                    case "TURN":
                        rotRudder = m.getRotation();
                        break;
                    case "USE_WATCH":
                        watch++;
                        break;
                }
            }
        }
        int countOar = 0;
        for(Entity e : cockpit.getGame().getShip().getEntities()){
            if(Objects.equals(e.getType(), "oar")) countOar++;
            if(Objects.equals(e.getType(), "sail")) {
                nbSail++;
                if(e.isOpenned()) sailOpen++;
            }
        }

        moveBoat(oarRight,oarLeft,countOar, rotRudder, nbSail, sailOpen, watch!=0);
    }

    private void checkIsOnCheckpoint() {
        if(getCurrentCheckpoint().isOnCheckpoint(cockpit.getGame().getShip().getPosition())){
            checkpointNumber++;
        }
    }

    private void moveBoat(int oarRight, int oarLeft, int nbOar, double rotRudder, int nbSail, int sailOpen, boolean watch){
        Wind wind = cockpit.getGame().getWind();
        Tool tool = new Tool(simulationShip.getPosition());
        Position p = tool.distanceOarAndRudderAndSail(oarRight, oarLeft, nbOar, rotRudder, sailOpen, nbSail, wind);
        simulationShip.setPosition(p);
        map.setWatch(watch);
    }

    private boolean stillCheckpoints() {
        return checkpointNumber < checkpointTotal;
    }

    public Checkpoint getCurrentCheckpoint(){
        return cockpit.getGame().getGoal().getCheckpoints()[checkpointNumber];
    }

    public void mapListener( ActionEvent event ) {
        int week = Integer.parseInt(event.getActionCommand());
        frame.setVisible(false);
        frame.dispose();
        System.out.println("Button clicked !" + week);
        Application.WEEK = week;

        Application.launchApp();
    }
}
