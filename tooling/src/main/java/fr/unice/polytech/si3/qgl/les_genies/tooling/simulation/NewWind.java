package fr.unice.polytech.si3.qgl.les_genies.tooling.simulation;

public class NewWind {

    @Override
    public String toString(){

        double minOr = -Math.PI, maxOr = Math.PI;
        double minStr = 0, maxStr = 150;

        return "{" +
                "\"orientation\": " + (minOr + (1 * (maxOr - minOr))) +
                ",\"strength\": " + (minStr + (1 * (maxStr - minStr))) +
                "}";
    }
}
