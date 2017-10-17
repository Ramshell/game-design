package com.platformer.ar;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Platformer extends Game {
	SpriteBatch batch;
    private ScreenDispatcher screenDispatcher;
    float r = 0/255f;
    float g = 24f/255f;
    float b = 72f/255f;

	@Override
	public void create () {
	    Assets.load();
		batch = new SpriteBatch();
        screenDispatcher = new ScreenDispatcher();
        Screen gameScreen = new GameScreen(batch, screenDispatcher);
        screenDispatcher.addScreen(gameScreen);
        setScreen(gameScreen);
    }

	@Override
	public void render () {
        Gdx.gl.glClearColor(r, g, b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Screen nextScreen = screenDispatcher.getNextScreen();
        if(nextScreen != getScreen()){
            setScreen(nextScreen);
        }

        super.render();
    }
}
