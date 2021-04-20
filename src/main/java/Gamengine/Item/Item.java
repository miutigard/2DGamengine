package Gamengine.Item;

public abstract class Item {

    String name, graphics;

    public Item() {

    }

    public Item(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGraphics() {
        return graphics;
    }

    public void setGraphics(String graphics) {
        this.graphics = graphics;
    }

    //Methods

    //Interact with item
    public void interactItem() {

    }
}
