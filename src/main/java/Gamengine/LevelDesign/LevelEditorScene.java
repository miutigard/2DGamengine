package Gamengine.LevelDesign;

import Gamengine.Gamerun.Camera;
import Gamengine.Gamerun.KeyListener;
import Gamengine.Gamerun.Window;
import Gamengine.Renderer.Shader;
import org.joml.Vector2f;
import org.lwjgl.BufferUtils;

import java.awt.event.KeyEvent;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class LevelEditorScene extends Scene {

    private String vertexShaderSrc = "#version 330 core\n" +
            "layout (location=0) in vec3 aPos;\n" +
            "layout (location=1) in vec4 aColor;\n" +
            "\n" +
            "out vec4 fColor;\n" +
            "\n" +
            "void main()\n" +
            "{\n" +
            "    fColor = aColor;\n" +
            "    gl_Position = vec4(aPos, 1.0);\n" +
            "}";
    private String fragmentShaderSrc = "#version 330 core\n" +
            "\n" +
            "in vec4 fColor;\n" +
            "\n" +
            "out vec4 color;\n" +
            "\n" +
            "void main()\n" +
            "{\n" +
            "    color = fColor;\n" +
            "}";

    private int vertexID, fragmentID, shaderProgram;

    private Shader defaultShader;

    private float[] vertexArray = {
        // position                 // color
        100.5f, 0.5f, 0.0f,          1.0f, 0.0f, 0.0f, 1.0f, //bottom right
        0.5f, 100.5f, 0.0f,          0.0f, 1.0f, 0.0f, 1.0f, //top left
        100.5f, 100.5f, 0.0f,           0.0f, 0.0f, 1.0f, 1.0f, // top right
        0.5f, 0.5f, 0.0f,         1.0f, 1.0f, 0.0f, 1.0f, // bottom left

    };

    // counter clockwise order
    private int[] elementArray = {
        2, 1, 0, // top right triangle
        0, 1, 3 // bottom left triangle
    };

    private int vaoID, vboID, eboID;

    public LevelEditorScene() {

    }

    @Override
    public void init() {
        this.camera = new Camera(new Vector2f());
        defaultShader = new Shader("assets/shaders/default.glsl");
        defaultShader.compile();

        // generate VAO, VBO, and EBO and send to GPU
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        // create a float bugger of verticies
        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.length);
        vertexBuffer.put(vertexArray).flip();

        // create VBO upload the vertex buffer
        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

        // create the indices and upload
        IntBuffer elementBuffer = BufferUtils.createIntBuffer(elementArray.length);
        elementBuffer.put(elementArray).flip();

        eboID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);

        // add the vertex attriubute pointers
        int positionsSize = 3;
        int colorSize = 4;
        int floatSizeBytes = 4;
        int vertexSizeBytes = (positionsSize + colorSize) * floatSizeBytes;
        glVertexAttribPointer(0, positionsSize, GL_FLOAT, false, vertexSizeBytes, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeBytes, positionsSize * floatSizeBytes);
        glEnableVertexAttribArray(1);

    }

    @Override
    public void update(float dt) {
        camera.position.x -= dt *50.0f;
        camera.position.y -= dt *50.0f;

        // bind shader program
        defaultShader.use();
        defaultShader.UploadMat4f("uProjection", camera.getProjectionMatrix());
        defaultShader.UploadMat4f("uView", camera.getViewMatrix());
        // bind the vao
        glBindVertexArray(vaoID);
        //enable vertex att pointers
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES, elementArray.length, GL_UNSIGNED_INT, 0);

        //unbind all
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);

        glBindVertexArray(0);

        defaultShader.detach();
    }
}
