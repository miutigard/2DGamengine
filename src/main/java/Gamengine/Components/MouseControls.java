package Gamengine.Components;

import Gamengine.Gamerun.Constants;
import Gamengine.Gamerun.GameObject;
import Gamengine.Gamerun.MouseListener;
import Gamengine.Gamerun.Window;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;

public class MouseControls extends Component{
    GameObject holdingObject = null;

    public void pickupObject(GameObject go) {
        this.holdingObject = go;
        Window.getScene().addGameObjectToScene(go);
    }

    public void place() {
        this.holdingObject = null;
    }

    @Override
    public void update(float dt) {
        if (holdingObject != null) {
            holdingObject.transform.position.x = MouseListener.getOrthoX();
            holdingObject.transform.position.y = MouseListener.getOrthoY();
            holdingObject.transform.position.x = (int)(holdingObject.transform.position.x / Constants.GRID_WIDTH) * Constants.GRID_HEIGHT;
            holdingObject.transform.position.y = (int)(holdingObject.transform.position.y / Constants.GRID_HEIGHT) * Constants.GRID_HEIGHT;

            if (MouseListener.mouseButtonDown(GLFW_MOUSE_BUTTON_LEFT)) {
                place();
            }
        }
    }
}
