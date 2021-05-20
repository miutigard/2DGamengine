package Assets.GamerunTools;

import Assets.Components.Spritesheet;
import Assets.Renderer.Shader;
import Assets.Renderer.Texture;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Assets {
    private static final Map<String, Shader> shaders = new HashMap<>();
    private static final Map<String, Texture> textures = new HashMap<>();
    private static final Map<String, Spritesheet> spritesheets = new HashMap<>();

    public static Shader getShader(String resourceName) {
        File file = new File(resourceName);
        if (Assets.shaders.containsKey(file.getAbsolutePath())) {
            return Assets.shaders.get(file.getAbsolutePath());
        } else {
            Shader shader = new Shader(resourceName);
            shader.compile();
            Assets.shaders.put(file.getAbsolutePath(), shader);
            return shader;
        }
    }

    public static Texture getTexture(String resourceName) {
        File file = new File(resourceName);
        if (Assets.textures.containsKey(file.getAbsolutePath())) {
            return Assets.textures.get(file.getAbsolutePath());
        } else {
            Texture texture = new Texture();
            texture.init(resourceName);
            Assets.textures.put(file.getAbsolutePath(), texture);
            return texture;
        }
    }

    public static void addSpritesheet(String resourceName, Spritesheet spritesheet) {
        File file = new File(resourceName);
        if (!Assets.spritesheets.containsKey(file.getAbsolutePath())) {
            Assets.spritesheets.put(file.getAbsolutePath(), spritesheet);
        }
    }

    public static Spritesheet getSpritesheet(String resourceName) {
        File file = new File(resourceName);
        return Assets.spritesheets.getOrDefault(file.getAbsolutePath(), null);
    }
}
