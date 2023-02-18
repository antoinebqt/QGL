package fr.unice.polytech.si3.qgl.les_genies.game.crew;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.si3.qgl.les_genies.Cockpit;
import fr.unice.polytech.si3.qgl.les_genies.game.tools.Coordinates;
import fr.unice.polytech.si3.qgl.les_genies.game.ship.Deck;
import fr.unice.polytech.si3.qgl.les_genies.game.entities.Entity;
import fr.unice.polytech.si3.qgl.les_genies.game.tools.Constants;

public class Sailor {
    private final int id;
    private final String name;
    private final Coordinates coordinates;

    /**
     * tell us if the sailor is busy as doing stuff ( moving, sailing, oaring ...)
     */
    @JsonIgnore
    private boolean busy;
    /**
     * permit knowing if the sailor is already on an entity
     */
    @JsonIgnore
    private boolean onEntity;

    @JsonCreator
    public Sailor(@JsonProperty("id") int id,
                  @JsonProperty("name") String name,
                  @JsonProperty("x")int x, @JsonProperty("y")int y){
        this.id=id;
        this.name=name;
        this.coordinates= new Coordinates(x,y);
        this.busy=false;
        this.onEntity=false;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates(){
        return this.coordinates;
    }

    public int getX() {
        return this.coordinates.getCoordinateX();
    }

    public int getY() {
        return this.coordinates.getCoordinateY();
    }

    public boolean isBusy(){
        return busy;
    }

    /**
     * sailor start working
     * that implies that he can't do anything else
     */
    public void work(){this.busy =true;}

    /**
     * free a sailor
     */
    public void free(){this.busy =false;}

    /**
     * move the sailor if the coordinates of it are valid
     * ( the actual coordinate + the added one cannot be greater than the limite of the deck)
     * ( limitation of the move up to 5 square )
     * @param y shift through the abscisse
     * @param x  shift through the ordinate
     */
    public int[] move(int x, int y, Deck deck){
        int nbCase=Math.abs(y) + Math.abs(x);

        boolean absOK = (getY()+y)>=0 && (getY()+y) <= deck.getWidth();
        boolean ordOK = (getX()+x)>=0 && (getX()+x) <= deck.getLength();

        if( absOK && ordOK){
            if(nbCase <=5){
                this.coordinates.translation(x,y);
                return new int[]{x,y};
            }
            else{
                int[] tab = remakeSailorShift(x,y);
                int newX = tab[0];
                int newY = tab[1];
                this.coordinates.translation(newX,newY);
                return new int[]{newX,newY};
            }
        }
        else
            Cockpit.addLog(Constants.MESSAGE_ERR_ON_MOVE);
        return new int[]{};
    }

    /**
     * Check if the sailor is physically on an entity, so he can eventually work
     * @param entity an entity
     * @return true or false
     */
    public boolean checkIfOnEntity(Entity entity){
        return getX() == entity.getX() && getY() == entity.getY();
    }

    /**
     * to know if the sailor is on a useful entity, so he doesn't have to move
     * @return true or false
     */
    public boolean isOnEntity() {
        return onEntity;
    }

    public void setOnEntity(boolean onEntity) {
        this.onEntity = onEntity;
    }

    /**
     * methode to correct the shift of an sailor if he move to far of what he actualy can
     * ( wich is 5 step )
     * @param x x
     * @param y y
     * @return an array of [ new x, new y ]
     */
    public int[] remakeSailorShift(int x, int y){
        int nbCase = Math.abs(x)+Math.abs(y);
        int nbOut = Math.abs(nbCase - 5);
        int nvX = x;
        int nvY = y;
        while(nbOut != 0){
            if(nvX != 0){
                nvX = (nvX > 0) ? nvX -1 : nvX +1;
            }
            else{
                nvY = (nvY > 0) ? nvY-1 : nvY+1;
            }
            nbOut--;
        }
        return new int[]{nvX,nvY};
    }

    @Override
    public String toString() {
        return "Sailor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", busy=" + busy +
                ", onEntity=" + onEntity +
                '}';
    }
}
