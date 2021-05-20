package Gamengine.Renderer;

import Gamengine.Gamerun.AssetPool;
import Gamengine.Gamerun.JMath;
import Gamengine.Gamerun.Window;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class DebugDraw {
    private static final int MAX_LINES = 500;

    private static final List<Line2D> lines = new ArrayList<>();

    private static final float[] vertexArray = new float[MAX_LINES * 6 * 2];
    private static final Shader shader = AssetPool.getShader("assets/shaders/debugLine2D.glsl");

    private static int vaoID;
    private static int vboID;

    private static boolean started = false;

    public static void start() {
        // generate vao

        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        // create vbo buffer memory
        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, vertexArray.length * Float.BYTES, GL_DYNAMIC_DRAW);

        //Enable vertex array atri
        glVertexAttribPointer(0,3,GL_FLOAT, false, 6* Float.BYTES, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1,3,GL_FLOAT, false, 6* Float.BYTES, 3 * Float.BYTES);
        glEnableVertexAttribArray(1);

        glLineWidth(2.0f);

    }

    public static void beginFrame() {
        if (!started) {
            start();
            started = true;
        }

        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).beginFrame() < 0) {
                lines.remove(i);
                i--;
            }
        }
    }

    public static void draw() {
        if (lines.size() <= 0) return;

        int index = 0;
        for (Line2D line : lines) {
            for (int i = 0; i < 2; i++) {
                Vector2f position = i == 0 ? line.getStart() : line.getEnd();
                Vector3f color = line.getColor();

                vertexArray[index] = position.x;
                vertexArray[index+1] = position.y;
                vertexArray[index+2] = -10.0f;


                vertexArray[index+3] = color.x;
                vertexArray[index+4] = color.y;
                vertexArray[index+5] = color.z;
                index +=6;
            }
        }
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferSubData(GL_ARRAY_BUFFER, 0, Arrays.copyOfRange(vertexArray, 0, lines.size() * 6 * 2));

        shader.use();
        shader.uploadMat4f("uProjection", Window.getScene().camera().getProjectionMatrix());
        shader.uploadMat4f("uView", Window.getScene().camera().getViewMatrix());

        glBindVertexArray(vaoID);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawArrays(GL_LINES, 0, lines.size());

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);
        shader.detach();
    }

    //ADD LINE2D
    public static void addLine2D(Vector2f start, Vector2f end) {
        addLine2D(start, end, new Vector3f(0, 1, 0), 3);
    }

    public static void addLine2D(Vector2f start, Vector2f end, Vector3f color) {
        addLine2D(start, end, color, 3);
    }

    public static void addLine2D(Vector2f start, Vector2f end, Vector3f color, int lifetime) {
        if(lines.size() >= MAX_LINES) return;
        DebugDraw.lines.add(new Line2D(start, end, color, lifetime));
    }

    //ADD BOX2D
    public static void addBox2D(Vector2f center, Vector2f dimensions, float rotation) {
        addBox2D(center, dimensions, rotation, new Vector3f(0, 1, 0), 1);
    }

    public static void addBox2D(Vector2f center, Vector2f dimensions,float rotation, Vector3f color) {
        addBox2D(center, dimensions, rotation, color, 1);
    }

    public static void addBox2D(Vector2f center, Vector2f dimensions,float rotation, Vector3f color, int lifetime) {
        Vector2f min = new Vector2f(center).sub(new Vector2f(dimensions).mul(0.5f));
        Vector2f max = new Vector2f(center).add(new Vector2f(dimensions).mul(0.5f));

        Vector2f[] verticies = {
            new Vector2f(min.x, min.y), new Vector2f(min.x, max.y),
            new Vector2f(max.x, max.y), new Vector2f(max.x, min.y)
        };

        if (rotation != 0.0f) {
            for (Vector2f vert : verticies) {
                JMath.rotate(vert, rotation, center);
            }
        }

        addLine2D(verticies[0], verticies[1], color, lifetime);
        addLine2D(verticies[0], verticies[3], color, lifetime);
        addLine2D(verticies[1], verticies[2], color, lifetime);
        addLine2D(verticies[2], verticies[3], color, lifetime);

    }

    // Add Circle

    public static void addCircle(Vector2f center, float radius) {
        addCircle(center, radius, new Vector3f(0, 1, 0), 3);
    }

    public static void addCircle(Vector2f center, float radius, Vector3f color) {
        addCircle(center, radius, color,3);
    }

    public static void addCircle(Vector2f center, float radius, Vector3f color, int lifetime) {
        Vector2f[] points = new Vector2f[20];
        int increments = 360 / points.length;
        int currentAngle = 0;

        for (int i = 0; i < points.length; i++) {
            Vector2f tmp = new Vector2f(radius, 0);
            JMath.rotate(tmp, currentAngle, new Vector2f());
            points[i] = new Vector2f(tmp).add(center);

            if ( i > 0) {
                addLine2D(points[i - 1], points[i], color, lifetime);
            }
            currentAngle += increments;
        }

        addLine2D(points[points.length - 1], points[0], color, lifetime);
    }

}
