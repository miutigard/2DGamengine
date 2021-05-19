package Gamengine.LevelDesign;

import Gamengine.Gamerun.KeyListener;
import Gamengine.Gamerun.Window;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;

public class LevelScene extends Scene {

    public LevelScene() {
        System.out.println("Inside level scene");

    }

    @Override
    public void update(float dt) {
        if (KeyListener.isKeyPressed(GLFW_KEY_SPACE)) {
            Window.changeScene(0);
        }
    }
}
