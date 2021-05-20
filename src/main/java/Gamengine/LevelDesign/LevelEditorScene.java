package Gamengine.LevelDesign;

import Assets.GamerunTools.*;
import Gamengine.Character.PlayerCharacter;
import Assets.Components.*;
import imgui.ImGui;
import imgui.ImVec2;
import org.joml.Vector2f;

import static org.lwjgl.glfw.GLFW.*;

public class LevelEditorScene extends Scene {

    private Spritesheet spritesheet;

    GameObject grid = new GameObject("Grid", new Transform(new Vector2f()), 0);

    public LevelEditorScene() {

    }

    @Override
    public void init() {


        // loading the spritesheet and shader resources
        loadResources();
        spritesheet = Assets.getSpritesheet("assets/images/croppedpokemontileset.png");

        // creating the camera
        this.camera = new Camera(new Vector2f());

        if (levelLoaded) {
            if (gameObjects.size() <= 0) return;
            this.currentGameObject = gameObjects.get(1);    //since the playerchar is the second gameobject created in the init() it will be 1 in the list of gameobjects
            return;
        }
        // adding a grid to the level editor
        grid.addComponent(new MouseControls());
        grid.addComponent(new GridLines());
        gameObjects.add(grid);

        // adding a playable character
        PlayerCharacter.setPlayercharSprite("assets/images/testImage.png");
        PlayerCharacter.createPlayerChar("Player character", new Transform(new Vector2f(100, 100), new Vector2f(128, 128)), 3);
    }

    private void loadResources() {
        Assets.getShader("assets/shaders/default.glsl");

        Assets.addSpritesheet("assets/images/croppedpokemontileset.png",
                new Spritesheet(Assets.getTexture("assets/images/croppedpokemontileset.png"),
                        16, 16, 128, 0));

        Assets.addSpritesheet("assets/images/testImage.png",
                new Spritesheet(Assets.getTexture("assets/images/testImage.png"),
                        32, 32, 1, 0));
    }

    @Override
    public void update(float dt) {
        //System.out.println("FPS: " + (1.0F / dt));

        //Move the currentGameObject which is the playercharacter
        if (KeyListener.isKeyPressed(GLFW_KEY_D)) {
            currentGameObject.transform.position.x += 300f * dt;
        } else if (KeyListener.isKeyPressed(GLFW_KEY_A)) {
            currentGameObject.transform.position.x -= 300f * dt;
        }
        if (KeyListener.isKeyPressed(GLFW_KEY_W)) {
            currentGameObject.transform.position.y += 300f * dt;
        } else if (KeyListener.isKeyPressed(GLFW_KEY_S)) {
            currentGameObject.transform.position.y -= 300f * dt;
        }

        for (GameObject go : this.gameObjects) {
            go.update(dt);
        }

        this.renderer.render();
    }

    @Override
    public void imgui() {
        // Creates a widget for the spritesheet in sprites ("assets/images/croppedpokemontiles.png)
        // To change to own spritesheet change
        ImGui.begin("Pokemon tiles");

        // Code from ImGui library
        ImVec2 windowPos = new ImVec2();
        ImGui.getWindowPos(windowPos);
        ImVec2 windowSize = new ImVec2();
        ImGui.getWindowSize(windowSize);
        ImVec2 itemSpacing = new ImVec2();
        ImGui.getStyle().getItemSpacing(itemSpacing);

        float windowX2 = windowPos.x + windowSize.x;
        for (int i = 0; i < spritesheet.size(); i++) {          //sprites is the spritesheet you want to use
            Sprite sprite = spritesheet.getSprite(i);
            float spriteWidth = sprite.getWidth() * 2;     //the width of the icons in the widget
            float spriteHeight = sprite.getHeight() * 2;    //the height of the icons in the widget
            int id = sprite.getTexId();
            Vector2f[] texCoords = sprite.getTexCoords();

            ImGui.pushID(i);
            if (ImGui.imageButton(id, spriteWidth, spriteHeight, texCoords[2].x, texCoords[0].y, texCoords[0].x, texCoords[2].y)) {
                GameObject object = Prefabs.generateSpriteObject(sprite, 64, 64);           //64x64 is the size of the object when picked up or placed into the game world (can be changed to what you want)
                gameObjects.get(0).getComponent(MouseControls.class).pickupObject(object);              //gameObjects.get(0) will be the grid gameObject since it was the first one created in the init()
            }
            ImGui.popID();

            ImVec2 lastButtonPos = new ImVec2();
            ImGui.getItemRectMax(lastButtonPos);
            float lastButtonX2 = lastButtonPos.x;
            float nextButtonX2 = lastButtonX2 + itemSpacing.x + spriteWidth;

            if (i + 1 < spritesheet.size() && nextButtonX2 < windowX2) {
                ImGui.sameLine();
            }
        }
        ImGui.end();
    }
}
