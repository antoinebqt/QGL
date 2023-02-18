package fr.unice.polytech.si3.qgl.les_genies.game.ship;

public record Deck(int width, int length) {

    /**
     * can also be used for getting the limit value for the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * can also be used for getting the limit value for the length
     */
    public int getLength() {
        return length;
    }

    @Override
    public String toString() {
        return "{" +
                "\"width\": " + getWidth() +
                ",\"length\": " + getLength() +
                "}";
    }
}
