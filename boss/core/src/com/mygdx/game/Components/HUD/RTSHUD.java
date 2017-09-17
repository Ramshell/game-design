package com.mygdx.game.Components.HUD;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Components.PlayerComponent;
import com.mygdx.game.Mappers.AssetsMapper;
import com.mygdx.game.OOP.MiniMap;
import com.mygdx.game.Play;

public class RTSHUD extends Stage{
    public MiniMap miniMap;
    private Viewport viewport;
    public PlayerComponent player;

    public Skin skin = AssetsMapper.hudSkin;

    public Label selectedObjectLabel;
    public Label resourcesLabel;
    public TextButton createWall = new TextButton("create wall",skin, "round");
    public Array<Array<ImageButton>> actionButtons = new Array<Array<ImageButton>>();

    public RTSHUD(final PlayerComponent player,final Play p){
        for(int i = 0; i < 3; i++) {
            actionButtons.add(new Array<ImageButton>());
            for (int j = 0; j < 3; j++)
                actionButtons.get(i).add(new ImageButton(skin));
        }
        this.player = player;
        selectedObjectLabel = new Label(player.selectedObject.getName(),skin);
        resourcesLabel = new Label(Integer.toString(player.resources),skin);
        OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport = new FitViewport(camera.viewportWidth, camera.viewportHeight, camera);
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
        Label rts = new Label("Essence of Life",skin, "title");
        Label resources = new Label("EoL",skin, "subtitle");
        HorizontalGroup resourcesBar = new HorizontalGroup();
        resourcesBar.expand().addActor(resources);
        resourcesBar.addActor(resourcesLabel);
        topTable.add(rts).left();
        topTable.add(resourcesBar).padLeft(15).expandX();
        Table minimapTable = new Table(skin).left();
        Table statsTable = new Table(skin).center();
        Table actionsTable = new Table(skin).right();

        actionsTable.add(new ImageButton(skin)).right().expandX();actionsTable.add(new ImageButton(skin)).expandX();actionsTable.add(new ImageButton(skin)).expandX();
        actionsTable.row();
        actionsTable.add(new ImageButton(skin)).expandX();actionsTable.add(new ImageButton(skin)).expandX();actionsTable.add(new ImageButton(skin)).expandX();
        actionsTable.row();
        actionsTable.add(new ImageButton(skin)).expandX();actionsTable.add(new ImageButton(skin)).expandX();actionsTable.add(new ImageButton(skin)).expandX();
        HorizontalGroup topMostBottomBar = new HorizontalGroup();
        topMostBottomBar.addActor(selectedObjectLabel);
        topMostBottomBar.addActor(new ImageButton(skin).center());
        topMostBottomBar.addActor(new ImageButton(skin).left());
        statsTable.add(topMostBottomBar);

//        topMostBottomBar.left().addActor(selectedObjectLabel);
//        bottomTable.add(topMostBottomBar).expandX().left();
//        bottomTable.row();
//        createWall.addListener(new ClickListener(){
//            public void clicked (InputEvent event, float x, float y) {
//                player.state = PlayerComponent.PlayerState.Building;
//                player.tryingBuilding = p.wallBuilder.getWall(player,0,0);
//            }
//        });
//        bottomTable.add(createWall);
        bottomTable.add(minimapTable).expandX();
        bottomTable.add(statsTable).expandX();
        bottomTable.add(actionsTable).expandX();
        TextureRegionDrawable t = new TextureRegionDrawable();
        t.setRegion(new TextureRegion(new Texture("HUD/resourceBar.png")));
        TextureRegionDrawable t2 = new TextureRegionDrawable();
        t2.setRegion(new TextureRegion(new Texture("HUD/transparentBar.png")));
        topTable.setBackground(t);
        bottomTable.setBackground(t2);
        addActor(rootTable);
        setViewport(viewport);

    }

    public void act (float deltaTime){
        selectedObjectLabel.setText(player.selectedObject.getName());
        resourcesLabel.setText(Integer.toString(player.resources));
        super.act(deltaTime);
    }

}
