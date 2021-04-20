package Gamengine.Character;

public class PlayerCharacter extends Characters{

    String moveUpKey, moveDownKey, moveRightKey, moveLeftKey, attackKey, interactKey;

    public PlayerCharacter() { }

    public PlayerCharacter(String name, int hitPoints, int attack, int defence) {
        super(name, hitPoints, attack, defence);
    }

    public String getMoveUpKey() {
        return moveUpKey;
    }

    public void setMoveUpKey(String moveUpKey) {
        this.moveUpKey = moveUpKey;
    }

    public String getMoveDownKey() {
        return moveDownKey;
    }

    public void setMoveDownKey(String moveDownKey) {
        this.moveDownKey = moveDownKey;
    }

    public String getMoveRightKey() {
        return moveRightKey;
    }

    public void setMoveRightKey(String moveRightKey) {
        this.moveRightKey = moveRightKey;
    }

    public String getMoveLeftKey() {
        return moveLeftKey;
    }

    public void setMoveLeftKey(String moveLeftKey) {
        this.moveLeftKey = moveLeftKey;
    }

    public String getAttackKey() {
        return attackKey;
    }

    public void setAttackKey(String attackKey) {
        this.attackKey = attackKey;
    }

    public String getInteractKey() {
        return interactKey;
    }

    public void setInteractKey(String interactKey) {
        this.interactKey = interactKey;
    }

    //Methods

    //Move player character methods
    public void moveUp() {

    }

    public void moveDown() {

    }

    public void moveRight() {

    }

    public void moveLeft() {

    }

    //Attack with player character
    public void attack() {

    }

    @Override
    public String toString() {
        return "Name: " + getName() + ", HitPoints: " + getHitPoints() + ", Attack: " + getAttack() + ", Defence: " + getDefence();
    }
}
