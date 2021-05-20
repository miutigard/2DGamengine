package Gamengine.Components;

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
            holdingObject.transform.position.x = (int)(holdingObject.transform.position.x / GridLines.getGrid_width()) * GridLines.getGrid_height();
            holdingObject.transform.position.y = (int)(holdingObject.transform.position.y / GridLines.getGrid_height()) * GridLines.getGrid_height();

            if (MouseListener.mouseButtonDown(GLFW_MOUSE_BUTTON_LEFT)) {
                place();
            }
        }
    }
}
