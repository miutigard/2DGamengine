package Gamengine.Components;

import Gamengine.Gamerun.Constants;
import Gamengine.Gamerun.Window;
import Gamengine.Renderer.DebugDraw;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class GridLines extends Component{

    @Override
    public void update(float dt) {
        Vector2f cameraPos = Window.getScene().camera().position;
        Vector2f projectionSize = Window.getScene().camera().getProjectionSize();

        int firstX = ((int)(cameraPos.x / Constants.GRID_WIDTH) - 1) * Constants.GRID_HEIGHT;
        int firstY = ((int)(cameraPos.y / Constants.GRID_WIDTH) - 1) * Constants.GRID_HEIGHT;

        int numVerticalLines = (int)(projectionSize.x / Constants.GRID_WIDTH) + 2;
        int numHorizontalLines = (int)(projectionSize.y / Constants.GRID_HEIGHT) + 2;

        int height = (int)projectionSize.y + Constants.GRID_HEIGHT * 2;
        int width = (int)projectionSize.x + Constants.GRID_WIDTH * 2;

        int maxLines = Math.max(numVerticalLines, numHorizontalLines);
        Vector3f color = new Vector3f(0.2f, 0.2f, 0.2f);
        for (int i = 0; i < maxLines; i++) {
            int x = firstX + (Constants.GRID_WIDTH * i);
            int y = firstY + (Constants.GRID_HEIGHT *i);

            if(i < numVerticalLines) {
                DebugDraw.addLine2D(new Vector2f(x, firstY), new Vector2f(x, y + height), color);
            }

            if(i < numHorizontalLines) {
                DebugDraw.addLine2D(new Vector2f(firstX, y), new Vector2f(firstX + width, y), color);
            }
        }
    }
}
