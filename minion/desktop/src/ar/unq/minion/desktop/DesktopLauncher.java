package ar.unq.minion.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ar.unq.minion.MinionGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Minion 1";
		config.width = 480;
		config.height = 800;
		new LwjglApplication(new MinionGame(), config);
	}
}
