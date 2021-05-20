package Gamengine.Character;

import Assets.Components.Sprite;
import Assets.Components.SpriteRenderer;
import Assets.GamerunTools.Assets;
import Assets.GamerunTools.GameObject;
import Assets.GamerunTools.Transform;
import Gamengine.Gamerun.Window;

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
        playercharSprite.setTexture(Assets.getTexture(Texture));
    }
}