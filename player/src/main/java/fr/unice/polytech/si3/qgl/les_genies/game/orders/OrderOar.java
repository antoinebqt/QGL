package fr.unice.polytech.si3.qgl.les_genies.game.orders;

import fr.unice.polytech.si3.qgl.les_genies.game.checkpoint.RegattaGoal;
import fr.unice.polytech.si3.qgl.les_genies.game.entities.Entity;
import fr.unice.polytech.si3.qgl.les_genies.game.logic.LogicDistance;
import fr.unice.polytech.si3.qgl.les_genies.game.logic.LogicRotation;
import fr.unice.polytech.si3.qgl.les_genies.game.logic.OarConfig;
import fr.unice.polytech.si3.qgl.les_genies.game.ship.Ship;

import java.util.Objects;

public class OrderOar extends Order{

    OarConfig oarConfig;
    int left = 0;
    int right = 0;

    public OrderOar(Ship ship, LogicRotation logicRotation, RegattaGoal goal, int sailors) {
        super(ship);
        oarConfig = logicRotation.getOarConfig();
        boostSpeed(oarConfig, goal, sailors);
        selectOarToUse(oarConfig, sailors);
    }

    public void selectOarToUse(OarConfig oarConfig, int sailors){
        for (Entity entity : ship.getEntities()) {
            if (Objects.equals(entity.getType(), "oar")){
                if (entity.getY() == 0 && oarConfig.getOarLeftSide() != 0 && aSailorIsFree(sailors)){
                    oarConfig.setOarLeftSide(oarConfig.getOarLeftSide()-1);
                    usefulEntity.add(entity);
                } else if (entity.getY() != 0 && oarConfig.getOarRightSide() != 0  && aSailorIsFree(sailors)){
                    oarConfig.setOarRightSide(oarConfig.getOarRightSide()-1);
                    usefulEntity.add(entity);
                }
            }
        }
    }

    public void boostSpeed(OarConfig oarConfig, RegattaGoal goal, int sailors) {
        LogicDistance distance = new LogicDistance(0,0);
        distance.setDistanceBetween(ship.getPosition(),goal.getCurrentCheckpoint().getPosition());

        int numberOfOars = getNumberOf("oar");
        left = oarConfig.getOarLeftSide();
        right = oarConfig.getOarRightSide();

        do {
            int sailorFree = sailors - left - right - usefulEntity.size();
            if (sailorFree < 2) break;
            if (left != numberOfOars/2 && right != numberOfOars/2){
                oarConfig.addOarEachSide();
                left = oarConfig.getOarLeftSide();
                right = oarConfig.getOarRightSide();

                if(numberOfOars > 0 && distance.getNorm() <= 165.0 * (oarConfig.getOarLeftSide() + oarConfig.getOarRightSide()) / numberOfOars) {
                    //slow down if the boat go to far
                    oarConfig.removeOarEachSide();
                    left = numberOfOars/2;
                    right = numberOfOars/2;
                }
            }
        } while (left != numberOfOars/2 && right != numberOfOars/2);
    }

    public int getLeftOar(){
        return left;
    }

    public int getRightOar(){
        return right;
    }
}
