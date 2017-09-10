package com.mygdx.game.InputHandlers;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Components.CellComponent;
import com.mygdx.game.Components.CellsComponent;
import com.mygdx.game.Components.MapComponent;
import com.mygdx.game.Components.PlayerComponent;
import com.mygdx.game.Entities.RendererEntity;
import com.mygdx.game.Mappers.Mappers;
import com.mygdx.game.Mappers.ResourceMapper;
import com.mygdx.game.Systems.BuildingMakingSystem;
import com.mygdx.game.Systems.MovementSystem;

//((InputMultiplexer)Gdx.input.getInputProcessor()).addProcessor();
public class TryingBuildingInputHandler extends InputAdapter {
    PlayerComponent player;
    Engine engine;
    private ComponentMapper<CellsComponent> cellsMapper = ComponentMapper.getFor(CellsComponent.class);

    public TryingBuildingInputHandler(PlayerComponent player, Engine engine){
        this.player = player;
        this.engine = engine;
    }

    public boolean touchDragged (int screenX, int screenY, int pointer) {
        return concerned();
    }

    public boolean touchDown(int screenX, int screenY, int pointer, int button){
        return concerned() && button == Input.Buttons.LEFT;
    }

    public boolean touchUp (int screenX, int screenY, int pointer, int button) {
        if(concerned()) {
            if (button == Input.Buttons.LEFT) {
                for (CellComponent c : cellsMapper.get(player.tryingBuilding).cells)
                    if (!BuildingMakingSystem.isBlocked(c)) {
                        player.state = PlayerComponent.PlayerState.Normal;
                        Rectangle r = Mappers.world.get(player.tryingBuilding).bounds.getRectangle();
                        r.setPosition(((int)c.position.x) * ResourceMapper.tileWidh - 16, ((int)c.position.y) * ResourceMapper.tileHeight + 16);
                        TiledMapTileLayer toDelete = (TiledMapTileLayer)Mappers.map.get(engine.getEntitiesFor(Family.all(MapComponent.class).get()).get(0)).map.getLayers().get("trying_building");
                        toDelete.setCell((int)c.position.x, (int)c.position.y, null);
                }
                engine.addEntity(player.tryingBuilding);
                player.tryingBuilding = null;
            }
            return true;
        }
        return false;
    }

    public boolean keyDown(int keycode) {
        if(concerned() && player.tryingBuilding != null) {
            switch (keycode) {
                case Input.Keys.ESCAPE:
                    player.state = PlayerComponent.PlayerState.Normal;
                    for (CellComponent c : cellsMapper.get(player.tryingBuilding).cells) {
                        TiledMapTileLayer toDelete = (TiledMapTileLayer)Mappers.map.get(engine.getEntitiesFor(Family.all(MapComponent.class).get()).get(0)).map.getLayers().get("trying_building");
                        toDelete.setCell((int)c.position.x, (int)c.position.y, null);
                    }
                    player.tryingBuilding = null;
                    break;
            }
            return true;
        }
        return false;
    }


    private boolean concerned() {
        return player.state == PlayerComponent.PlayerState.Building;
    }
}
