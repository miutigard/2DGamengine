package Gamengine.Components;

import Gamengine.Gamerun.Component;

public class SpriteRenderer extends Component {

    private boolean firstTime = false;

    @Override
    public void start() {
        System.out.println("Starting bitch");
    }

    @Override
    public void update(float dt) {
        if (!firstTime) {


        System.out.println("Updating bitch");
        firstTime = true;
        }
    }
}
