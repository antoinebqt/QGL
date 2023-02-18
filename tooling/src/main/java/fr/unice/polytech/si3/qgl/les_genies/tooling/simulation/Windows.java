package fr.unice.polytech.si3.qgl.les_genies.tooling.simulation;

import fr.unice.polytech.si3.qgl.les_genies.game.checkpoint.Checkpoint;
import fr.unice.polytech.si3.qgl.les_genies.game.checkpoint.RegattaGoal;
import fr.unice.polytech.si3.qgl.les_genies.game.game_element.Game;
import fr.unice.polytech.si3.qgl.les_genies.game.shapes.Circle;
import fr.unice.polytech.si3.qgl.les_genies.game.map.BoxMap;
import fr.unice.polytech.si3.qgl.les_genies.game.map.MapGrid;
import fr.unice.polytech.si3.qgl.les_genies.game.shapes.Shape;
import fr.unice.polytech.si3.qgl.les_genies.game.tools.Constants;
import fr.unice.polytech.si3.qgl.les_genies.game.tools.Position;
import fr.unice.polytech.si3.qgl.les_genies.game.visible_entities.Reef;
import fr.unice.polytech.si3.qgl.les_genies.game.visible_entities.Stream;
import fr.unice.polytech.si3.qgl.les_genies.game.visible_entities.VisibleEntities;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Windows extends JPanel {
    private final ArrayList<Position> pos;
    private final Checkpoint[] checkpoints;
    private final RegattaGoal goal;
    private final List<VisibleEntities> seaEntities;
    private final MapGrid map;
    private final Map m2;


    private int rapport = 16;
    private int dist = 0;
    private int distR = 0;

    Windows(ArrayList<Position> pos, Checkpoint[] checkpoints, Game game, Map map){
        this.pos = pos;
        this.checkpoints = checkpoints;
        this.goal = game.getGoal();
        this.seaEntities = game.getVisibleEntities();
        this.map = game.getMap();
        this.m2 = map;
        for(VisibleEntities visibleEntities : m2.getVisibleEntities())game.addDistance(visibleEntities.getPosition());
    }

    public void paint(Graphics g) {
        super.paint(g);

        BufferedImage image;
        try {
            image = ImageIO.read(new File(System.getProperty("user.dir") + "/tooling/src/main/ImageVisualisation/ocean.jpg"));
            g.drawImage(image,0,0,1500,1000,this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Checkpoint check : goal.getListOpti()){
            g.setColor(Color.orange);
            drawShape(g,check.getPosition(), check.getShape());
        }

        for(VisibleEntities visibleEntities : m2.getVisibleEntities()){
            Position position = null;
            Shape shape = null;
            if(Objects.equals(visibleEntities.getType(), Constants.REEF)){
                Reef r = (Reef) visibleEntities;
                position = r.getPosition();
                shape = r.getShape();
            }else if(Objects.equals(visibleEntities.getType(), Constants.STREAM)){
                Stream s = (Stream) visibleEntities;
                position = s.getPosition();
                shape = s.getShape();
            }

            g.setColor(Color.lightGray);
            drawShape(g, position, shape);
        }

        for(VisibleEntities visibleEntities : seaEntities){
            Position position = null;
            Shape shape = null;
            if(Objects.equals(visibleEntities.getType(), Constants.REEF)){
                Reef r = (Reef) visibleEntities;
                position = r.getPosition();
                shape = r.getShape();
                g.setColor(Color.darkGray);
            }else if(Objects.equals(visibleEntities.getType(), Constants.STREAM)){
                Stream s = (Stream) visibleEntities;
                position = s.getPosition();
                shape = s.getShape();
                g.setColor(Color.PINK);
            }

            if(!visibleEntities.getType().equals(Constants.SHIP))drawShape(g, position, shape);
        }

        //draw basic checkpoint
        for (Checkpoint check : checkpoints){
            g.setColor(Color.red);
            drawShape(g,check.getPosition(), check.getShape());
        }

        int s = MapGrid.getBoxSize();
        for(BoxMap[] boxLine : map.getMap()) {
            for(BoxMap box : boxLine){
                Position p = box.getCenter();
                if(!box.getStatus())g.setColor(Color.red);
                else if(box.isStream())g.setColor(Color.blue);
                else g.setColor(Color.white);
                g.drawRect((int)(p.getX()-s/2)/rapport+dist,(int)(p.getY()-s/2)/rapport+distR,s/rapport,s/rapport);
            }
        }

        //draw boat positions
        for (Position p : pos){
            g.setColor(Color.BLUE);
            drawShape(g,p, new Circle(40));
        }

        //draw line between 2 boat positions
        int len = pos.size()-1;
        for (int i = 0; i < len; i++){
            g.setColor(Color.CYAN);
            g.drawLine((int)pos.get(i).getX()/rapport+dist,(int)pos.get(i).getY()/rapport+distR,(int)pos.get(i+1).getX()/rapport+dist,(int)pos.get(i+1).getY()/rapport+distR);
        }
    }

    private void drawShape(Graphics g, Position p, Shape s){
        if(s != null){
            if(s.getType().equals(Constants.CIRCLE)) drawCircle(g,p,(int)s.getRadius()*2/rapport);
            else drawRectangleAndPolygon(g,p,s);
        }else {
            System.out.println("shape is null at position : "+ p);
        }
    }

    private void drawCircle(Graphics g, Position p, int size){
        g.fillOval((int)p.getX()/rapport+dist-size/2,(int)p.getY()/rapport+distR-size/2,size,size);
    }

    private void drawRectangleAndPolygon(Graphics g, Position p, Shape s){
        if(p!=null){
            PolygoneCalculator poly = new PolygoneCalculator(p,s);
            for (int i = 0; i< poly.getSize(); i++){
                poly.getXs()[i]=poly.getXs()[i]/rapport+dist;
                poly.getYs()[i]=poly.getYs()[i]/rapport+distR;
            }
            g.fillPolygon(poly.getXs(), poly.getYs(), poly.getSize());
        }
    }
}
