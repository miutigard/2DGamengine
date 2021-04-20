package Gamengine.Character;

import Gamengine.Item.Item;

public abstract class Characters {

    String name, graphics;
    int hitPoints, attack, defence;
    Item weapon;
    Item armor;

    public Characters() {
    }

    public Characters(String name, int hitPoints, int attack, int defence) {
        this.name = name;
        this.hitPoints = hitPoints;
        this.attack = attack;
        this.defence = defence;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //Use filelocation of picture file to set as a string to get the graphic for the character.
    public String getGraphics() {
        return graphics;
    }

    public void setGraphics(String graphics) {
        this.graphics = graphics;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefence() {
        return defence;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }

    public Item getWeapon() {
        return weapon;
    }

    public void setWeapon(Item weapon) {
        this.weapon = weapon;
    }

    public Item getArmor() {
        return armor;
    }

    public void setArmor(Item armor) {
        this.armor = armor;
    }
}
