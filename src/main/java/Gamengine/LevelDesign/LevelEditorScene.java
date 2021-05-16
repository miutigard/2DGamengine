package Gamengine.LevelDesign;

import Gamengine.Components.Sprite;
import Gamengine.Components.SpriteRenderer;
import Gamengine.Components.Spritesheet;
import Gamengine.Gamerun.*;
import org.joml.Vector2f;
import org.joml.Vector4f;

import static org.lwjgl.glfw.GLFW.*;

public class LevelEditorScene extends Scene {

    private GameObject obj1, obj2;
    private Spritesheet sprites, sprites2;

    public LevelEditorScene() {

    }

    @Override
    public void init() {
        loadResources();

        this.camera = new Camera(new Vector2f(-250, -60));

        sprites = AssetPool.getSpritesheet("assets/images/spritesheet3.png");
        sprites2 = AssetPool.getSpritesheet("assets/images/spritesheet.png");

        obj1 = new GameObject("Object 1", new Transform(new Vector2f(100, 100), new Vector2f(256, 256)));
        obj1.addComponent(new SpriteRenderer(sprites.getSprite(0)));
        addGameObjectToScene(obj1);

        obj2 = new GameObject("Object 2", new Transform(new Vector2f(400, 100), new Vector2f(256, 256)));
        obj2.addComponent(new SpriteRenderer(sprites2.getSprite(0)));
        addGameObjectToScene(obj2);

    }

    private void loadResources() {
        AssetPool.getShader("assets/shaders/default.glsl");

        AssetPool.addSpritesheet("assets/images/spritesheet3.png",
                new Spritesheet(AssetPool.getTexture("assets/images/spritesheet3.png"),
                        64, 64, 151, 0));

        AssetPool.addSpritesheet("assets/images/spritesheet.png",
                new Spritesheet(AssetPool.getTexture("assets/images/spritesheet.png"),
                        16, 16, 26, 0));
    }

    private int spriteIndex = 0;
    private float spriteFlipTime = 0.2f;
    private float spriteFlipTimeLeft = 0.0f;

    @Override
    public void update(float dt) {
        //System.out.println("FPS: " + (1.0F / dt));

        if (KeyListener.isKeyPressed(GLFW_KEY_RIGHT)) {
            obj2.transform.position.x += 100 *dt;
        } else if (KeyListener.isKeyPressed(GLFW_KEY_LEFT)) {
            obj2.transform.position.x -= 100 *dt;
        }

        if (KeyListener.isKeyPressed(GLFW_KEY_UP)) {
            obj2.transform.position.y += 100 *dt;
        } else if (KeyListener.isKeyPressed(GLFW_KEY_DOWN)) {
            obj2.transform.position.y -= 100 *dt;
        }

        spriteFlipTimeLeft -= dt;
        if (spriteFlipTimeLeft <= 0) {
            spriteFlipTimeLeft = spriteFlipTime;
            spriteIndex++;
            if (spriteIndex > 4) {
                spriteIndex = 0;
            }
            obj1.getComponent(SpriteRenderer.class).setSprite(sprites.getSprite(spriteIndex));
            obj2.getComponent(SpriteRenderer.class).setSprite(sprites2.getSprite(spriteIndex));
        }

        for (GameObject go : this.gameObjects) {
            go.update(dt);
        }

        this.renderer.render();
    }
}
