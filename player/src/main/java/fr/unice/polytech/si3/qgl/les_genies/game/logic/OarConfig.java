package fr.unice.polytech.si3.qgl.les_genies.game.logic;

public class OarConfig {

    private int oarLeftSide;
    private int oarRightSide;

    public OarConfig(int left, int right){
        oarLeftSide = left;
        oarRightSide = right;
    }

    public int getOarLeftSide() {
        return oarLeftSide;
    }

    public int getOarRightSide() {
        return oarRightSide;
    }

    public void setOarLeftSide(int oarLeftSide) {
        this.oarLeftSide = oarLeftSide;
    }

    public void setOarRightSide(int oarRightSide) {
        this.oarRightSide = oarRightSide;
    }

    public void setOarEachSide(int left, int right){
        oarLeftSide = left;
        oarRightSide = right;
    }

    public void addOarEachSide() {
        oarLeftSide++;
        oarRightSide++;
    }

    public void removeOarEachSide() {
        if(oarRightSide!=0 && oarLeftSide!=0){
            oarLeftSide--;
            oarRightSide--;
        }
    }
}
