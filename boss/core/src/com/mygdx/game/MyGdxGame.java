package com.mygdx.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mygdx.game.Mappers.AssetsMapper;

public class MyGdxGame extends Game{

	@Override
	public void create () {
		AssetsMapper.load();
        setScreen(new NLogo(this, new SpriteBatch()));
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
