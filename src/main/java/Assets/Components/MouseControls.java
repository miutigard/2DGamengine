package Assets.Components;

import Assets.GamerunTools.GameObject;
import Assets.GamerunTools.MouseListener;
import Gamengine.Gamerun.Window;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;

public class MouseControls extends Component{
    GameObject currentlyHeldObject = null;

    public void pickupObject(GameObject go) {
        this.currentlyHeldObject = go;
        Window.getScene().addGameObjectToScene(go);
    }

    public void place() {
        this.currentlyHeldObject = null;
    }

    @Override
    public void update(float dt) {
        if (currentlyHeldObject != null) {
            currentlyHeldObject.transform.position.x = MouseListener.getOrthoX();
            currentlyHeldObject.transform.position.y = MouseListener.getOrthoY();
            currentlyHeldObject.transform.position.x = (int)(currentlyHeldObject.transform.position.x / GridLines.getGrid_width()) * GridLines.getGrid_height();
            currentlyHeldObject.transform.position.y = (int)(currentlyHeldObject.transform.position.y / GridLines.getGrid_height()) * GridLines.getGrid_height();

            if (MouseListener.mouseButtonDown(GLFW_MOUSE_BUTTON_LEFT)) {
                place();
            }
        }
    }
}
