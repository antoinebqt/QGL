package fr.unice.polytech.si3.qgl.les_genies.game.pathfinding;

public class ProcessBox {
    private boolean process;

    public ProcessBox(boolean isProcess) {
        process = isProcess;
    }

    public boolean isProcess() {
        return process;
    }

    public void setProcess(boolean isProcess) {
        this.process = isProcess;
    }
}
