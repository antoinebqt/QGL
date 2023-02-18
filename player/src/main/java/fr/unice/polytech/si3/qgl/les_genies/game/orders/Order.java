package fr.unice.polytech.si3.qgl.les_genies.game.orders;


import fr.unice.polytech.si3.qgl.les_genies.game.entities.Entity;
import fr.unice.polytech.si3.qgl.les_genies.game.ship.Ship;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Order {
    Ship ship;
    protected static final List<Entity> usefulEntity = new ArrayList<>();

    public Order(Ship ship) {
        this.ship = ship;
    }

    public static void clearEntities() {
        usefulEntity.clear();
    }

    int getNumberOf(String entity) {
        int counter = 0;
        for (Entity e : ship.getEntities()) {
            if (Objects.equals(e.getType(), entity)) {
                counter++;
            }
        }
        return counter;
    }

    public Entity getFirstEntityByType(String type) {
        for (Entity entity : ship.getEntities()) {
            if (Objects.equals(entity.getType(), type)){
                return entity;
            }
        }
        return null;
    }

    public static List<Entity> getList() {
        return usefulEntity;
    }

    public boolean aSailorIsFree(int sailors){
        return sailors - usefulEntity.size() > 0;
    }
}
