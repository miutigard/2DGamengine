package Gamengine.Components;

import Gamengine.Gamerun.Window;
import Gamengine.Renderer.DebugDraw;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class GridLines extends Component{
    private static int grid_width;
    private static int grid_height;

    @Override
    public void update(float dt) {
        Vector2f cameraPos = Window.getScene().camera().position;
        Vector2f projectionSize = Window.getScene().camera().getProjectionSize();

        int firstX = ((int)(cameraPos.x / grid_width) - 1) * grid_height;
        int firstY = ((int)(cameraPos.y / grid_width) - 1) * grid_height;

        int numVerticalLines = (int)(projectionSize.x / grid_width) + 2;
        int numHorizontalLines = (int)(projectionSize.y / grid_height) + 2;

        int height = (int)projectionSize.y + grid_height * 2;
        int width = (int)projectionSize.x + grid_width * 2;

        int maxLines = Math.max(numVerticalLines, numHorizontalLines);
        Vector3f color = new Vector3f(0.2f, 0.2f, 0.2f);
        for (int i = 0; i < maxLines; i++) {
            int x = firstX + (grid_width * i);
            int y = firstY + (grid_height *i);

            if(i < numVerticalLines) {
                DebugDraw.addLine2D(new Vector2f(x, firstY), new Vector2f(x, y + height), color);
            }

            if(i < numHorizontalLines) {
                DebugDraw.addLine2D(new Vector2f(firstX, y), new Vector2f(firstX + width, y), color);
            }
        }
    }

    public static void setGrid_width(int grid_width) {
        GridLines.grid_width = grid_width;
    }

    public static void setGrid_height(int grid_height) {
        GridLines.grid_height = grid_height;
    }

    public static int getGrid_width() {
        return grid_width;
    }

    public static int getGrid_height() {
        return grid_height;
    }
}
