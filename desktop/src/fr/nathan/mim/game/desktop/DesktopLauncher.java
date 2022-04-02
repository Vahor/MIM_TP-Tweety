package fr.nathan.mim.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import fr.nathan.mim.game.Client;

public class DesktopLauncher {

    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.title = "Jeu de MIM";

        config.width  = 480;
        config.height = 640;
        config.resizable = false;

        new LwjglApplication(new Client(), config);
    }
}
