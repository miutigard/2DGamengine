package Gamengine.LevelDesign;

import Gamengine.Components.Component;
import Gamengine.Components.ComponentDeserializer;
import Gamengine.Gamerun.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.joml.Vector2f;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.lwjgl.glfw.GLFW.*;

public class LevelScene extends Scene {

    public LevelScene() {

    }

    @Override
    public void init() {

        // creating the camera
        this.camera = new Camera(new Vector2f());
    }

    @Override
    public void update(float dt) {
        if (KeyListener.isKeyPressed(GLFW_KEY_2)) {
            save();
            Window.changeScene(0);
        }
    }
}
