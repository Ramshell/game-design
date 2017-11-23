package com.mygdx.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.Mappers.AssetsMapper;

public class NLogo extends ScreenAdapter {
    private final Game game;
    private SpriteBatch batch;
    private Stage stage;
    private Skin skin;
    float r = 0/255f;
    float g = 0/255f;
    float b = 0/255f;

    private Image logo;

    private float duration = 6;

    public NLogo(final Game game, final SpriteBatch batch){
        this.batch = batch;
        this.game = game;
        stage = new Stage(new FitViewport(1366, 768));
        skin = AssetsMapper.hudSkin;
        logo = new Image(AssetsMapper.nLogo);
        logo.setScale(1f);
        logo.setPosition(1366 / 2 - logo.getWidth() * logo.getScaleX() / 2, 768 / 2 - logo.getHeight()  * logo.getScaleY() / 2);
        stage.addAction(Actions.sequence(
                Actions.alpha(0),
                Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        AssetsMapper.logoMusic.play();
                        stage.addActor(logo);
                    }
                }),
                Actions.fadeIn(duration / 4f),
                Actions.delay(duration / 2),
                Actions.fadeOut(duration / 4f),
                Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        game.setScreen(new Play(new Engine(), game));
                        AssetsMapper.logoMusic.stop();
                    }
                })));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(r, g, b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        duration -= delta;
        stage.act(delta);
        stage.draw();
    }
}
