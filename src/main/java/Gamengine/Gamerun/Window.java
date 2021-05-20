package Gamengine.Gamerun;

import Assets.GamerunTools.KeyListener;
import Assets.GamerunTools.MouseListener;
import Gamengine.LevelDesign.Scene;
import Assets.Renderer.Drawer;
import Assets.imGUI.imGuiGlfw;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    int width, height;
    private static int windowWidth, windowHeight;
    String title;
    private long glfwWindow;
    private imGuiGlfw imGuiGlfw;

    public float r, g, b, a;

    private static Window window = null;

    private static Scene currentScene;

    private Window(int width, int height) {
        this.width = width;
        this.height = height;
        this.title = "Gamengine";
        r = 1;
        b = 1;
        g = 1;
        a = 1;
    }

    public static void setCurrentScene(Scene scene) {
        currentScene = scene;
    }

    public static void loadCurrentScene() {
        currentScene.load();
        currentScene.init();
        currentScene.start();
    }

    public static Window get() {
        if (Window.window == null) {
            Window.window = new Window(windowWidth, windowHeight);
        }

        return Window.window;
    }

    public static Scene getScene() {
        return get().currentScene;
    }

    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        init();
        loop();

        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
        if ( glfwWindow == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
        glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);
        glfwSetWindowSizeCallback(glfwWindow, (w, newWidth, newHeight) -> {
            Window.setWidth(newWidth);
            Window.setHeight(newHeight);
        });

        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            glfwGetWindowSize(glfwWindow, pWidth, pHeight);

            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            glfwSetWindowPos(
                    glfwWindow,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        }

        glfwMakeContextCurrent(glfwWindow);

        glfwSwapInterval(1);

        glfwShowWindow(glfwWindow);

        GL.createCapabilities();

        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);
        this.imGuiGlfw = new imGuiGlfw(glfwWindow);
        imGuiGlfw.initImGui();

        loadCurrentScene();
    }

    public void loop() {
        float beginTime = (float)glfwGetTime();
        float endTime = (float)glfwGetTime();
        float dt = -1.0f;

        while ( !glfwWindowShouldClose(glfwWindow) ) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            glfwPollEvents();

            Drawer.beginFrame();

            glClearColor(r, g, b, a);
            glClear(GL_COLOR_BUFFER_BIT);

            if (currentScene != null) {
                if (dt >= 0) {
                    Drawer.draw();
                    currentScene.update(dt);
                }

                this.imGuiGlfw.update(dt, currentScene);
                glfwSwapBuffers(glfwWindow);
            }
            endTime = (float)glfwGetTime();
            dt = endTime - beginTime;
            beginTime = (float)glfwGetTime();
        }
        currentScene.save();
    }

    public static int getWidth() {
        return get().width;
    }

    public static int getHeight() {
        return get().height;
    }

    public static void setWidth(int newWidth) {
        get().width = newWidth;
    }

    public static void setHeight(int newHeight) {
        get().height = newHeight;
    }

    public static void setWindowWidth(int windowWidth) {
        Window.windowWidth = windowWidth;
    }

    public static void setWindowHeight(int windowHeight) {
        Window.windowHeight = windowHeight;
    }
}
