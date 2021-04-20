package Gamengine.Item;

public class Armor extends Item{

    int hitPoints, defence;

    public Armor() {

    }

    public Armor(String name, int hitPoints, int defence) {
        super(name);
        this.hitPoints = hitPoints;
        this.defence = defence;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public int getDefence() {
        return defence;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }

    //Methods

    //Interact with item (Equips armor)
    public void interactItem() {

    }
}
