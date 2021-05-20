package Gamengine.Gamerun;

import Assets.Components.GridLines;

public class GameRun {

    private static final GameRun instance = new GameRun();

    private GameRun() {

    }

    public static void startGame() {

    }

    public static GameRun getInstance() {
        return instance;
    }
}
