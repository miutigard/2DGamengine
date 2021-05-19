package Gamengine.LevelDesign;

import Gamengine.Components.Sprite;
import Gamengine.Components.SpriteRenderer;
import Gamengine.Components.Spritesheet;
import Gamengine.Gamerun.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import imgui.ImGui;
import org.joml.Vector2f;
import org.joml.Vector4f;

import static org.lwjgl.glfw.GLFW.*;

public class LevelEditorScene extends Scene {

    private GameObject obj1;
    private Spritesheet sprites;
    SpriteRenderer obj1Sprite;

    public LevelEditorScene() {

    }

    @Override
    public void init() {
        loadResources();

        this.camera = new Camera(new Vector2f());
        if(levelLoaded) {
            return;
        }

        sprites = AssetPool.getSpritesheet("assets/images/spritesheet2.png");

        obj1 = new GameObject("Object 1", new Transform(new Vector2f(600, 100),
                new Vector2f(256, 256)), 2);
        obj1Sprite = new SpriteRenderer();
        obj1Sprite.setColor(new Vector4f(1, 0, 0, 1));
        obj1.addComponent(obj1Sprite);
        this.addGameObjectToScene(obj1);
        this.currentGameObject = obj1;

        GameObject obj2 = new GameObject("Object 2",
                new Transform(new Vector2f(600, 300), new Vector2f(256, 256)), 3);
        SpriteRenderer obj2SpriteRenderer = new SpriteRenderer();
        Sprite obj2Sprite = new Sprite();
        obj2Sprite.setTexture(AssetPool.getTexture("assets/images/blendImage2.png"));
        obj2SpriteRenderer.setSprite(obj2Sprite);
        obj2.addComponent(obj2SpriteRenderer);
        this.addGameObjectToScene(obj2);
    }

    private void loadResources() {
        AssetPool.getShader("assets/shaders/default.glsl");

        AssetPool.addSpritesheet("assets/images/spritesheet2.png",
                new Spritesheet(AssetPool.getTexture("assets/images/spritesheet2.png"),
                        64, 64, 151, 0));
    }

    @Override
    public void update(float dt) {
        //System.out.println("FPS: " + (1.0F / dt));

        for (GameObject go : this.gameObjects) {
            go.update(dt);
        }

        this.renderer.render();
    }

    @Override
    public void imgui() {
        ImGui.begin("Test window");
        ImGui.text("Some random text");
        ImGui.end();
    }

}
