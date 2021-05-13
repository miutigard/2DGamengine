package Gamengine.LevelDesign;

import Gamengine.Components.FontRenderer;
import Gamengine.Components.SpriteRenderer;
import Gamengine.Gamerun.*;
import Gamengine.Renderer.Shader;
import Gamengine.Renderer.Texture;
import org.joml.Vector2f;
import org.lwjgl.BufferUtils;

import java.awt.event.KeyEvent;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class LevelEditorScene extends Scene {

    private Shader defaultShader;
    private Texture testTexture;

    GameObject testObject;
    private boolean firstTime = false;

    private float[] vertexArray = {
        // position                     // color                            // UV Coordinates
        400f,   300f,     0.0f,           1.0f,   0.0f,   0.0f,   1.0f,       1, 1,   //bottom right 0
        300f,     400f,   0.0f,           0.0f,   1.0f,   0.0f,   1.0f,       0, 0,   //top left     1
        400f,   400f,   0.0f,           1.0f,   0.0f,   1.0f,   1.0f,       1, 0,   // top right   2
        300f,     300f,     0.0f,           1.0f,   1.0f,   0.0f,   1.0f,       0, 1   // bottom left 3

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
        System.out.println("Creating test object");
        this.testObject = new GameObject("Test object");
        this.testObject.addComponent(new SpriteRenderer());
        this.testObject.addComponent(new FontRenderer());
        this.addGameObjectToScene(this.testObject);

        this.camera = new Camera(new Vector2f());
        defaultShader = new Shader("assets/shaders/default.glsl");
        defaultShader.compile();
        this.testTexture = new Texture("assets/images/testImage4.jpg");

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
        int uvSize = 2;
        int vertexSizeBytes = (positionsSize + colorSize + uvSize) * Float.BYTES;
        glVertexAttribPointer(0, positionsSize, GL_FLOAT, false, vertexSizeBytes, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeBytes, positionsSize * Float.BYTES);
        glEnableVertexAttribArray(1);

        glVertexAttribPointer(2, uvSize, GL_FLOAT, false, vertexSizeBytes, (positionsSize + colorSize) * Float.BYTES);
        glEnableVertexAttribArray(2);
    }

    @Override
    public void update(float dt) {

        // bind shader program
        defaultShader.use();

        // Upload texture to shader
        defaultShader.uploadTexture("TEX_SAMPLER", 0);
        glActiveTexture(GL_TEXTURE0);
        testTexture.bind();

        defaultShader.uploadMat4f("uProjection", camera.getProjectionMatrix());
        defaultShader.uploadMat4f("uView", camera.getViewMatrix());
        defaultShader.uploadFloat("uTime", Time.getTime());
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

        if (!firstTime) {
            System.out.println("Creating game object2");
            GameObject go = new GameObject("Game test 2");
            go.addComponent(new SpriteRenderer());
            this.addGameObjectToScene(go);
            firstTime = true;
        }

        for (GameObject go : this.gameObjects) {
            go.update(dt);
        }
    }
}
