package Gamengine.Gamerun;

public class GameRun {

    String pauseKey;

    public GameRun() { 

    }

    public static void startGame() {
        Window window = Window.get();
        window.run();
    }
}
