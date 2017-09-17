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
import com.mygdx.game.PathfindingUtils.MapGraph;
import com.mygdx.game.Systems.BuildingMakingSystem;

public class TryingBuildingInputHandler extends InputAdapter {
    PlayerComponent player;
    Engine engine;
    MapGraph mapGraph;
    private ComponentMapper<CellsComponent> cellsMapper = ComponentMapper.getFor(CellsComponent.class);

    public TryingBuildingInputHandler(PlayerComponent player, Engine engine, MapGraph mapGraph){
        this.player = player;
        this.engine = engine;
        this.mapGraph = mapGraph;
    }

    public boolean touchDragged (int screenX, int screenY, int pointer) {
        return concerned();
    }

    public boolean touchDown(int screenX, int screenY, int pointer, int button){
        if(concerned() && button == Input.Buttons.RIGHT){
            toNormal();
            return true;
        }
        return concerned() && button == Input.Buttons.LEFT;
    }

    private void toNormal() {
        player.state = PlayerComponent.PlayerState.Normal;
        if(player.tryingBuilding != null) for (CellComponent c : cellsMapper.get(player.tryingBuilding).cells) {
            TiledMapTileLayer toDelete = (TiledMapTileLayer)Mappers.map.get(engine.getEntitiesFor(Family.all(MapComponent.class).get()).get(0)).map.getLayers().get("trying_building");
            toDelete.setCell((int)c.position.x, (int)c.position.y, null);
        }
        player.tryingBuilding = null;
        player.action = null;
    }

    public boolean touchUp (int screenX, int screenY, int pointer, int button) {
        if (concerned() && button == Input.Buttons.LEFT){
            player.selectedObject.act(player.action.getAction(screenX, screenY));
            toNormal();
            return true;
        }
        return false;
    }

    public boolean keyDown(int keycode) {
        if(concerned()) {
            switch (keycode) {
                case Input.Keys.ESCAPE:
                    toNormal();
                    break;
            }
            return true;
        }
        return false;
    }


    private boolean concerned() {
        return player.action != null;
    }
}
