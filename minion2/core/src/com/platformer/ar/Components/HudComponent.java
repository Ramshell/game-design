package com.platformer.ar.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.platformer.ar.Assets;
import com.platformer.ar.Components.World.PlayerComponent;

public class HudComponent implements Component {
    public Stage stage;
    public SpriteBatch batch;
    public Array<Image> hearts = new Array<Image>(5);
    public PlayerComponent playerComponent;

    public HudComponent(OrthographicCamera camera, SpriteBatch batch, PlayerComponent playerComponent){
        FitViewport viewport = new FitViewport(camera.viewportWidth, camera.viewportHeight);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
        this.batch = batch;
        this.playerComponent = playerComponent;
        Table mainTable = new Table();
        mainTable.top().left();
        mainTable.setFillParent(true);
        for(int i = 0; i < playerComponent.health; ++i){
            Image img = new Image(Assets.heart);
            hearts.add(img);
            mainTable.add(img).right();
        }
        stage.addActor(mainTable);
    }
}
