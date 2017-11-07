package com.platformer.ar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class VictoryScreen extends ScreenAdapter {
    private SpriteBatch batch;
    private IScreenDispatcher dispatcher;
    private Stage stage;

    private Skin skin;
    private Image victoryBg;
    private Image victory;
    private Label time;

    private Label coinsLabel;

    private Label heartsLostLabel;



    Vector2 target = new Vector2();
    float elapsed = 0;
    Interpolation easAply = Interpolation.bounceOut;

    public VictoryScreen(final SpriteBatch batch, final IScreenDispatcher dispatcher, long start_time, int coins, int heartsLost) {
        super();
        this.batch = batch;
        this.dispatcher = dispatcher;
        stage = new Stage(new FitViewport(1024, 768));
        skin = new Skin(Gdx.files.internal("flat-earth/skin/flat-earth-ui.json"));
        victoryBg = new Image(Assets.victoryBg);
        victoryBg.setScale(1.2f);
        victory = new Image(Assets.victory);
        victory.setScale(0.4f);
        victory.setPosition(220, 1000);
        time = new Label("Win time: " + String.valueOf((System.currentTimeMillis() -start_time) / 1000) + " secs", skin, "title");
        time.setColor(Color.BLUE);
        time.setPosition(10, 10);

        coinsLabel = new Label("Coins:  " + String.valueOf(coins), skin, "title");
        coinsLabel.setColor(Color.GREEN);
        coinsLabel.setPosition(500, 10);

        heartsLostLabel = new Label("Hearts lost:  " + String.valueOf(heartsLost), skin, "title");
        heartsLostLabel.setColor(Color.RED);
        heartsLostLabel.setPosition(10, 700);




        victoryBg.setPosition(0 , 0);
        target.set(220, 300);


        stage.addActor(victoryBg);
        stage.addActor(victory);
        stage.addActor(time);
        stage.addActor(coinsLabel);
        stage.addActor(heartsLostLabel);
    }

    @Override
    public void show() {
        Assets.desertBackground.stop();
        Assets.winSound.play();
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
        elapsed += delta * 0.3f;
        float progress = Math.min(1f, elapsed);
        float alpha = easAply.apply(progress);
        victory.setPosition(target.x * alpha, target.y * alpha);
        stage.act(delta);
        stage.draw();
    }
}
