package Gamengine.LevelDesign;

import Gamengine.Character.NPC;
import Gamengine.Item.Item;

public class Level {

    int levelX, levelY;

    public Level() {

    }

    public Level(int levelX, int levelY) {
        this.levelX = levelX;
        this.levelY = levelY;
    }

    public int getLevelX() {
        return levelX;
    }

    public void setLevelX(int levelX) {
        this.levelX = levelX;
    }

    public int getLevelY() {
        return levelY;
    }

    public void setLevelY(int levelY) {
        this.levelY = levelY;
    }

    //Methods

    //Where playercharacter spawns on the level.

    public void spawnPoint(int x, int y) {

    }

    //Where the level ends when the playercharacter walks on the grid.
    public void endPoint(int x, int y) {

    }

    //Where an eventual NPC will be placed on a level.
    public void placeNPC(NPC npc, int x, int y) {

    }

    //Where an Item can be placed and picked up by an character.
    public void placeItem(Item item, int x, int y) {

    }

    //Creates the grid-based level with a size of levelX * levelY
    public void createLevel() {

    }

    //Sets the graphic on one grid
    public void setGraphicOnGrid(String graphic, int x, int y) {

    }

    //Sets the graphic on the whole level
    public void setGraphicOnWholeLevel(String graphic) {

    }

}
