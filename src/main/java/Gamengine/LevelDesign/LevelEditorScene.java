package Gamengine.LevelDesign;

import Gamengine.Character.PlayerCharacter;
import Gamengine.Components.*;
import Gamengine.Gamerun.*;
import Gamengine.Renderer.DebugDraw;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import imgui.ImGui;
import imgui.ImVec2;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import static org.lwjgl.glfw.GLFW.*;

public class LevelEditorScene extends Scene {

    private Spritesheet sprites, sprites2;
    private GameObject obj1;

    GameObject levelEditorTmp = new GameObject("Level Editor", new Transform(new Vector2f()), 0);

    public LevelEditorScene() {

    }

    @Override
    public void init() {
        levelEditorTmp.addComponent(new MouseControls());
        levelEditorTmp.addComponent(new GridLines());

        loadResources();
        this.camera = new Camera(new Vector2f());
        sprites = AssetPool.getSpritesheet("assets/images/croppedpokemontileset.png");
        sprites2 = AssetPool.getSpritesheet("assets/images/spritesheet2.png");

        if(levelLoaded) {
            if (gameObjects.size() <= 0) return;
            this.currentGameObject = gameObjects.get(0);
            return;
        }

        GameObject playerchar = new GameObject("Playerchar",
                new Transform(new Vector2f(100, 100), new Vector2f(128, 128)), 3);
        SpriteRenderer playercharSpriteRenderer = new SpriteRenderer();
        playercharSpriteRenderer.setSprite(sprites2.getSprite(0));
        playerchar.addComponent(playercharSpriteRenderer);
        this.addGameObjectToScene(playerchar);
        this.currentGameObject = playerchar;

    }

    private void loadResources() {
        AssetPool.getShader("assets/shaders/default.glsl");

        AssetPool.addSpritesheet("assets/images/croppedpokemontileset.png",
                new Spritesheet(AssetPool.getTexture("assets/images/croppedpokemontileset.png"),
                        16, 16, 128, 0));

        AssetPool.addSpritesheet("assets/images/spritesheet2.png",
                new Spritesheet(AssetPool.getTexture("assets/images/spritesheet2.png"),
                        64, 64, 151, 0));
    }

    @Override
    public void update(float dt) {
        //System.out.println("FPS: " + (1.0F / dt));
        levelEditorTmp.update(dt);

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
        ImGui.begin("Pokemon tiles");

        ImVec2 windowPos = new ImVec2();
        ImGui.getWindowPos(windowPos);
        ImVec2 windowSize = new ImVec2();
        ImGui.getWindowSize(windowSize);
        ImVec2 itemSpacing = new ImVec2();
        ImGui.getStyle().getItemSpacing(itemSpacing);

        float windowX2 = windowPos.x + windowSize.x;
        for (int i = 0; i < sprites.size(); i++) {
            Sprite sprite = sprites.getSprite(i);
            float spriteWidth = sprite.getWidth() * 2 ;
            float spriteHeight = sprite.getHeight() * 2;
            int id = sprite.getTexId();
            Vector2f[] texCoords = sprite.getTexCoords();

            ImGui.pushID(i);
            if (ImGui.imageButton(id, spriteWidth, spriteHeight, texCoords[2].x, texCoords[0].y, texCoords[0].x, texCoords[2].y)) {
                GameObject object = Prefabs.generateSpriteObject(sprite, 64, 64);
                levelEditorTmp.getComponent(MouseControls.class).pickupObject(object);
            }
            ImGui.popID();

            ImVec2 lastButtonPos = new ImVec2();
            ImGui.getItemRectMax(lastButtonPos);
            float lastButtonX2 = lastButtonPos.x;
            float nextButtonX2 = lastButtonX2 + itemSpacing.x + spriteWidth;

            if (i + 1 < sprites.size() && nextButtonX2 < windowX2) {
                ImGui.sameLine();
            }
        }
        ImGui.end();
    }

}
