package com.mygdx.game.Components.HUD;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Builders.WallBuilder;
import com.mygdx.game.Components.PlayerComponent;
import com.mygdx.game.Components.WorldObjectComponent;
import com.mygdx.game.Mappers.Mappers;
import com.mygdx.game.Play;
import com.mygdx.game.Systems.MovementSystem;
import com.sun.javafx.scene.control.skin.ButtonSkin;


public class HUDComponent implements Component {
    public Stage stage;
    private Viewport viewport;
    public PlayerComponent player;

    public Skin skin = new Skin(Gdx.files.internal("HUD/skins/gdx-skins-master/freezing/skin/freezing-ui.json"));

    public Label selectedObjectLabel;
    public Label resourcesLabel;
    public TextButton createWall = new TextButton("Crear Wall",skin);

    public HUDComponent(final PlayerComponent player,final Play p){
        this.player = player;
        selectedObjectLabel = new Label(player.selectedObject.objectName,skin);
        resourcesLabel = new Label("    " + Integer.toString(player.resources),skin);
        OrthographicCamera camera = new OrthographicCamera(512, 384);
        viewport = new FitViewport(camera.viewportWidth, camera.viewportHeight, camera);
        stage = new Stage(viewport);
        Table rootTable = new Table();
        rootTable.setFillParent(true);
        Table topTable = new Table(skin);
        Table midTable = new Table();
        Table midTable2 = new Table();
        Table bottomTable = new Table();
        bottomTable.bottom();
        rootTable.add(topTable).fillX().expandX();
        rootTable.row();
        rootTable.add(midTable).fill().expand();
        rootTable.row();
        rootTable.add(midTable2).fill().expand();
        rootTable.row();
        rootTable.add(bottomTable).fillX().expandX();
        Label rts = new Label("rts",skin);
        Label resources = new Label("resources:",skin);
        HorizontalGroup resourcesBar = new HorizontalGroup();
        resourcesBar.expand().addActor(resources);
        resourcesBar.addActor(resourcesLabel);
        topTable.add(rts).expandX();
        topTable.add(resourcesBar).expandX();
        HorizontalGroup topMostBottomBar = new HorizontalGroup().expand();
        topMostBottomBar.left().addActor(selectedObjectLabel);
        bottomTable.add(topMostBottomBar).expandX().left();
        bottomTable.row();
        createWall.addListener(new ClickListener(){
            public void clicked (InputEvent event, float x, float y) {
                player.state = PlayerComponent.PlayerState.Building;
                player.tryingBuilding = p.wallBuilder.getWall(player,0,0);
            }
        });
        bottomTable.add(createWall);
        TextureRegionDrawable t = new TextureRegionDrawable();
        t.setRegion(new TextureRegion(new Texture("HUD/resourceBar.png")));
        TextureRegionDrawable t2 = new TextureRegionDrawable();
        t2.setRegion(new TextureRegion(new Texture("HUD/ordersBar.png")));
        topTable.setBackground(t);
        bottomTable.setBackground(t2);
        stage.addActor(rootTable);
    }
}
