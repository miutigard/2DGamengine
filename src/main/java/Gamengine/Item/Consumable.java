package Gamengine.Item;

public class Consumable extends Item{

    int hitPointsStatIncrease, attackStatIncrease, defenceStatIncrease;

    public Consumable() {

    }

    public Consumable(String name) {
        super(name);
    }

    public int getHitPointsStatIncrease() {
        return hitPointsStatIncrease;
    }

    public void setHitPointsStatIncrease(int hitPointsStatIncrease) {
        this.hitPointsStatIncrease = hitPointsStatIncrease;
    }

    public int getAttackStatIncrease() {
        return attackStatIncrease;
    }

    public void setAttackStatIncrease(int attackStatIncrease) {
        this.attackStatIncrease = attackStatIncrease;
    }

    public int getDefenceStatIncrease() {
        return defenceStatIncrease;
    }

    public void setDefenceStatIncrease(int defenceStatIncrease) {
        this.defenceStatIncrease = defenceStatIncrease;
    }

    //Methods

    //Interact with item (Increase stats)
    public void interactItem() {

    }
}
