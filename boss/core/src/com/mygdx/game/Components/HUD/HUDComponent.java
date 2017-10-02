package com.mygdx.game.Components.HUD;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Components.PlayerComponent;
import com.mygdx.game.Mappers.AssetsMapper;
import com.mygdx.game.OOP.Actions.Action;
import com.mygdx.game.OOP.Actions.ActionBuilder;
import com.mygdx.game.OOP.Actions.MoveAction;
import com.mygdx.game.OOP.MiniMap;
import com.mygdx.game.Play;


public class HUDComponent implements Component {
    public TextButton missionButton;
    public Label matchTime;
    public MiniMap miniMap;
    public Stage stage;
    private Viewport viewport;
    public PlayerComponent player;

    public Skin skin = AssetsMapper.hudSkin;

    public Label selectedObjectLabel;
    public Label resourcesLabel;
    public Array<Array<ImageButton>> actionButtons = new Array<Array<ImageButton>>();
    public Array<Array<ImageButton>> selectedObjects = new Array<Array<ImageButton>>();

    public Table actionsTable;

    public HUDComponent(final PlayerComponent player,final Play p){
        for(int i = 0; i < 3; i++) {
            actionButtons.add(new Array<ImageButton>());
            for (int j = 0; j < 3; j++) {
                ImageButton imgButton = new ImageButton(skin);
                imgButton.setVisible(false);
                actionButtons.get(i).add(imgButton);
            }
        }

        for(int i = 0; i < 3; i++) {
            selectedObjects.add(new Array<ImageButton>());
            for (int j = 0; j < 3; j++) {
                ImageButton img = new ImageButton(skin);
                img.setVisible(false);
                selectedObjects.get(i).add(img);
            }
        }

        this.player = player;
        selectedObjectLabel = new Label(player.selectedObject.getName(),skin);
        resourcesLabel = new Label(Integer.toString(player.resources),skin);
        OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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
        matchTime = new Label("00:00",skin, "title");
        Label resources = new Label("EoL",skin, "subtitle");
        HorizontalGroup resourcesBar = new HorizontalGroup();
        resourcesBar.expand().addActor(resources);
        resourcesBar.addActor(resourcesLabel);
        topTable.add(matchTime).expandX().left();
        topTable.add(resourcesBar).expandX();
        topTable.row();
        missionButton = new TextButton("Mission", skin);
        Table minimapTable = new Table(skin).left();
        minimapTable.add(missionButton);
//        minimapTable.add(AssetsMapper.moveButton);
        Table statsTable = new Table(skin).center();
        actionsTable = new Table(skin).right();
        for(int i = 0; i < 3; i++)
            actionsTable.add(actionButtons.get(0).get(i)).center().expandX();
        actionsTable.row();
        for(int i = 0; i < 3; i++)
            actionsTable.add(actionButtons.get(1).get(i)).center().expandX();
        actionsTable.row();
        for(int i = 0; i < 3; i++)
            actionsTable.add(actionButtons.get(2).get(i)).center().expandX();
        HorizontalGroup topMostBottomBar = new HorizontalGroup();
        topMostBottomBar.addActor(selectedObjectLabel);
        statsTable.add(topMostBottomBar).top();
        statsTable.row();
        for(int i = 0; i < 3; i++)
            statsTable.add(selectedObjects.get(0).get(i)).center().expandX();
        statsTable.row();
        for(int i = 0; i < 3; i++)
            statsTable.add(selectedObjects.get(1).get(i)).center().expandX();
        statsTable.row();
        for(int i = 0; i < 3; i++)
            statsTable.add(selectedObjects.get(2).get(i)).center().expandX();
        bottomTable.add(minimapTable).expandX();
        bottomTable.add(statsTable).expandX();
        bottomTable.add(actionsTable).expandX();
        TextureRegionDrawable t = new TextureRegionDrawable();
        t.setRegion(new TextureRegion(new Texture("HUD/resourceBar.png")));
        TextureRegionDrawable t2 = new TextureRegionDrawable();
        t2.setRegion(new TextureRegion(new Texture("HUD/ordersBar.png")));
        topTable.setBackground(t);
        bottomTable.setBackground(t2);
        stage.addActor(rootTable);
    }
}
