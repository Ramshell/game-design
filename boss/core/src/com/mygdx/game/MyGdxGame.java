package com.mygdx.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Mappers.AssetsMapper;

public class MyGdxGame extends Game {

	
	@Override
	public void create () {
		AssetsMapper.load();
		setScreen(new Play(new Engine()));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
        super.dispose();
	}
}
