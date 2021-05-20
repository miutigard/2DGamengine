package Gamengine.Gamerun;

import Gamengine.Components.GridLines;
import Gamengine.Components.MouseControls;
import org.joml.Vector2f;

public class GameRun {

    private static final GameRun instance = new GameRun();

    private GameRun() {

    }

    public static void startGame() {
        // Setting the window width and height
        Window.setWindowWidth(1600);
        Window.setWindowHeight(980);

        // Setting the grid height and width
        GridLines.setGrid_height(64);
        GridLines.setGrid_width(64);

        //Getting the window and running it
        Window.get().run();

    }

    public static GameRun getInstance() {
        return instance;
    }
}
