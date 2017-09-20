package com.mygdx.game.InputHandlers;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Components.CellComponent;
import com.mygdx.game.Components.CellsComponent;
import com.mygdx.game.Components.MapComponent;
import com.mygdx.game.Components.PlayerComponent;
import com.mygdx.game.Mappers.Mappers;
import com.mygdx.game.Mappers.ResourceMapper;
import com.mygdx.game.OOP.Actions.CancelAcionsAction;
import com.mygdx.game.PathfindingUtils.MapGraph;
import com.mygdx.game.Systems.BuildingMakingSystem;

public class ActionsInputHandler extends InputAdapter {
    PlayerComponent player;
    Engine engine;
    MapGraph mapGraph;
    private ComponentMapper<CellsComponent> cellsMapper = ComponentMapper.getFor(CellsComponent.class);

    public ActionsInputHandler(PlayerComponent player, Engine engine, MapGraph mapGraph){
        this.player = player;
        this.engine = engine;
        this.mapGraph = mapGraph;
    }

    public boolean touchDragged (int screenX, int screenY, int pointer) {
        return concerned();
    }

    public boolean touchDown(int screenX, int screenY, int pointer, int button){
        if(concerned() && button == Input.Buttons.RIGHT){
            cancelActions(player, engine);
            toNormal(player);
            return true;
        }
        return concerned() && button == Input.Buttons.LEFT;
    }

    public static void toNormal(PlayerComponent player) {
        player.state = PlayerComponent.PlayerState.Normal;
        player.tryingBuilding = null;
        player.action = null;
    }

    public boolean touchUp (int screenX, int screenY, int pointer, int button) {
        if (concerned() && button == Input.Buttons.LEFT){
            player.selectedObject.act(player.action.getAction(screenX, screenY));
            toNormal(player);
            return true;
        }
        return false;
    }

    public boolean keyDown(int keycode) {
        if(concerned()) {
            switch (keycode) {
                case Input.Keys.ESCAPE:
                    toNormal(player);
                    player.selectedObject.deselect();
                    break;
            }
            return true;
        }
        return false;
    }


    private boolean concerned() {
        return player.action != null;
    }

    public static void cancelActions(PlayerComponent playerComponent, Engine engine) {
        playerComponent.selectedObject.act(new CancelAcionsAction(engine));
    }
}
