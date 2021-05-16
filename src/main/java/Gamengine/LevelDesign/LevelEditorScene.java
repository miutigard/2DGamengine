package Gamengine.LevelDesign;

import Gamengine.Components.Sprite;
import Gamengine.Components.SpriteRenderer;
import Gamengine.Components.Spritesheet;
import Gamengine.Gamerun.*;
import org.joml.Vector2f;
import org.joml.Vector4f;

import static org.lwjgl.glfw.GLFW.*;

public class LevelEditorScene extends Scene {

    public LevelEditorScene() {

    }

    @Override
    public void init() {
        loadResources();

        this.camera = new Camera(new Vector2f(-250, -60));

        Spritesheet sprites = AssetPool.getSpritesheet("assets/images/spritesheet3.png");

        GameObject obj1 = new GameObject("Object 1", new Transform(new Vector2f(100, 100), new Vector2f(256, 256)));
        obj1.addComponent(new SpriteRenderer(sprites.getSprite(140)));
        addGameObjectToScene(obj1);

        /*GameObject obj2 = new GameObject("Object 2", new Transform(new Vector2f(400, 100), new Vector2f(256, 256)));
        obj2.addComponent(new SpriteRenderer(sprites.getSprite(93)));
        addGameObjectToScene(obj2);

        GameObject obj3 = new GameObject("Object 3", new Transform(new Vector2f(100, 400), new Vector2f(256, 256)));
        obj3.addComponent(new SpriteRenderer(sprites.getSprite(64)));
        addGameObjectToScene(obj3);*/
    }

    private void loadResources() {
        AssetPool.getShader("assets/shaders/default.glsl");

        AssetPool.addSpritesheet("assets/images/spritesheet3.png",
                new Spritesheet(AssetPool.getTexture("assets/images/spritesheet2.png"),
                        64, 64, 160, 0));
    }

    @Override
    public void update(float dt) {
        System.out.println("FPS: " + (1.0F / dt));

        if (KeyListener.isKeyPressed(GLFW_KEY_RIGHT)) {
            camera.position.x -= 300f *dt;
        } else if (KeyListener.isKeyPressed(GLFW_KEY_LEFT)) {
            camera.position.x += 300f *dt;
        }

        if (KeyListener.isKeyPressed(GLFW_KEY_UP)) {
            camera.position.y -= 300f *dt;
        } else if (KeyListener.isKeyPressed(GLFW_KEY_DOWN)) {
            camera.position.y += 300f *dt;
        }

        for (GameObject go : this.gameObjects) {
            go.update(dt);
        }

        this.renderer.render();
    }
}
