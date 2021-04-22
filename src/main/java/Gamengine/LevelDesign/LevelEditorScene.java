package Gamengine.LevelDesign;

import Gamengine.Gamerun.KeyListener;
import Gamengine.Gamerun.Window;

import java.awt.event.KeyEvent;

public class LevelEditorScene extends Scene {

    private boolean changingScene;
    private float timeToChangeScene = 2.0F;

    public LevelEditorScene() {
        System.out.println("Inside level editor scene");
    }

    @Override
    public void update(float dt) {

        System.out.println("" + (1.0f / dt) + " FPS");

        if (!changingScene && KeyListener.isKeyPressed(KeyEvent.VK_SPACE)) {
            changingScene = true;
        }

        if (changingScene && timeToChangeScene > 0) {
            timeToChangeScene -= dt;
            Window.get().r -= dt * 2.5f;
            Window.get().g -= dt * 2.5f;
            Window.get().b -= dt * 2.5f;
        } else if (changingScene) {
            Window.changeScene(1);
        }

    }
}
