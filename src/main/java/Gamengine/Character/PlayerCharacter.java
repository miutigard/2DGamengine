package Gamengine.Character;

import Gamengine.Components.Sprite;
import Gamengine.Components.SpriteRenderer;
import Gamengine.Gamerun.AssetPool;
import Gamengine.Gamerun.GameObject;
import Gamengine.Gamerun.Transform;
import Gamengine.Gamerun.Window;
import org.joml.Vector2f;

public class PlayerCharacter {

        private static final PlayerCharacter instance = new PlayerCharacter();
        private static final Sprite playercharSprite = new Sprite();

            public static void createPlayerChar(String name, Transform transform, int zIndex) {
            GameObject playerchar = new GameObject(name, transform, zIndex);

            SpriteRenderer playercharSpriteRenderer = new SpriteRenderer();
            playercharSpriteRenderer.setSprite(playercharSprite);

            playerchar.addComponent(playercharSpriteRenderer);
            Window.getScene().addGameObjectToScene(playerchar);
            Window.getScene().setCurrentGameObject(playerchar);
        }

    public static void setPlayercharSprite(String Texture) {
        playercharSprite.setTexture(AssetPool.getTexture(Texture));
    }
}