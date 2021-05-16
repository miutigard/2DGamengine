package Gamengine.LevelDesign;

import Gamengine.Components.Sprite;
import Gamengine.Components.SpriteRenderer;
import Gamengine.Components.Spritesheet;
import Gamengine.Gamerun.*;
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

        obj1 = new GameObject("Object 1", new Transform(new Vector2f(0, 120), new Vector2f(256, 256)), 2);
        obj1.addComponent(new SpriteRenderer(sprites4.getSprite(0)));
        addGameObjectToScene(obj1);

        GameObject obj2 = new GameObject("Object 2", new Transform(new Vector2f(0, 0), new Vector2f(1280, 800)), 0);
        obj2.addComponent(new SpriteRenderer(new Sprite(
                AssetPool.getTexture("assets/images/windowsxp.jpg")
        )));
        addGameObjectToScene(obj2);

        GameObject obj3 = new GameObject("Object 3", new Transform(new Vector2f(600, 500), new Vector2f(229, 224)), 0);
        obj3.addComponent(new SpriteRenderer(new Sprite(
                AssetPool.getTexture("assets/images/finalboss.png")
        )));
        addGameObjectToScene(obj3);

        obj4 = new GameObject("Object 1", new Transform(new Vector2f(900, 120), new Vector2f(256, 256)), 1);
        obj4.addComponent(new SpriteRenderer(sprites.getSprite(140)));
        addGameObjectToScene(obj4);


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

        if (KeyListener.isKeyPressed(GLFW_KEY_D)) {
            obj1.transform.position.x += 1000 *dt;
        } else if (KeyListener.isKeyPressed(GLFW_KEY_A)) {
            obj1.transform.position.x -= 1000 *dt;
        }

        if (KeyListener.isKeyPressed(GLFW_KEY_W)) {
            obj1.transform.position.y += 1000 *dt;
        } else if (KeyListener.isKeyPressed(GLFW_KEY_S)) {
            obj1.transform.position.y -= 1000 *dt;
        }

        if (KeyListener.isKeyPressed(GLFW_KEY_RIGHT)) {
            obj4.transform.position.x += 1000 *dt;
        } else if (KeyListener.isKeyPressed(GLFW_KEY_LEFT)) {
            obj4.transform.position.x -= 1000 *dt;
        }

        if (KeyListener.isKeyPressed(GLFW_KEY_UP)) {
            obj4.transform.position.y += 1000 *dt;
        } else if (KeyListener.isKeyPressed(GLFW_KEY_DOWN)) {
            obj4.transform.position.y -= 1000 *dt;
        }


        for (GameObject go : this.gameObjects) {
            go.update(dt);
        }

        this.renderer.render();
    }
}
