package com.platformer.ar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameOverScreen extends ScreenAdapter {
    private SpriteBatch batch;
    private IScreenDispatcher dispatcher;
    private Stage stage;

    private Skin skin;
    private Image gameOver;

    private TextButton retry;
    Vector2 target = new Vector2();
    float elapsed = 0;
    Interpolation easAply = Interpolation.swingOut;

    public GameOverScreen(final SpriteBatch batch, final ScreenDispatcher dispatcher){
        super();
        this.batch = batch;
        this.dispatcher = dispatcher;
        stage = new Stage(new FitViewport(1024, 768));
        skin = new Skin(Gdx.files.internal("flat-earth/skin/flat-earth-ui.json"));
        gameOver = new Image(Assets.gameOver);
        retry = new TextButton("Retry", skin);


        retry.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dispatcher.screens.remove(dispatcher.screens.get(0));
                Assets.desertBackground.play();
                Assets.gameOverSound.stop();
                dispatcher.addScreen(new GameScreen(batch, dispatcher));
            }
        });

        gameOver.setPosition(512 - gameOver.getWidth() / 2, 384 - gameOver.getHeight()/ 2);
        retry.setSize(200, 100);

        retry.setPosition(512 - retry.getWidth() / 2, 600);
        target.set(512 - retry.getWidth() / 2, 600);
        stage.addActor(gameOver);stage.addActor(retry);
    }

    @Override
    public void show() {
        Assets.desertBackground.stop();
        Assets.gameOverSound.play();
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
        elapsed += delta;
        float progress = Math.min(1f, elapsed);
        float alpha = easAply.apply(progress);
        retry.setPosition(target.x * alpha, target.y * alpha);
        stage.act(delta);
        stage.draw();
    }
}
