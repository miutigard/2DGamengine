package Gamengine.LevelDesign;

import Gamengine.Components.Sprite;
import Gamengine.Components.SpriteRenderer;
import Gamengine.Components.Spritesheet;
import Gamengine.Gamerun.*;
import imgui.ImGui;
import org.joml.Vector2f;
import org.joml.Vector4f;

import static org.lwjgl.glfw.GLFW.*;

public class LevelEditorScene extends Scene {

    private GameObject obj1, obj4;
    private Spritesheet sprites;

    public LevelEditorScene() {

    }

    @Override
    public void init() {
        loadResources();

        this.camera = new Camera(new Vector2f());

        sprites = AssetPool.getSpritesheet("assets/images/spritesheet2.png");
        Spritesheet sprites4 = AssetPool.getSpritesheet("assets/images/spritesheet4.png");

        obj1 = new GameObject("Object 1", new Transform(new Vector2f(600, 120), new Vector2f(256, 256)), 2);
        obj1.addComponent(new SpriteRenderer(sprites.getSprite(149)));
        addGameObjectToScene(obj1);
        this.currentGameObject = obj1;
    }

    private void loadResources() {
        AssetPool.getShader("assets/shaders/default.glsl");

        AssetPool.addSpritesheet("assets/images/spritesheet2.png",
                new Spritesheet(AssetPool.getTexture("assets/images/spritesheet2.png"),
                        64, 64, 151, 0));

        AssetPool.addSpritesheet("assets/images/spritesheet4.png",
                new Spritesheet(AssetPool.getTexture("assets/images/spritesheet4.png"),
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
