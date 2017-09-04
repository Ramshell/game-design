package com.mygdx.game.InputHandlers;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Components.CellComponent;
import com.mygdx.game.Components.CellsComponent;
import com.mygdx.game.Components.PlayerComponent;
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
        return player.state == PlayerComponent.PlayerState.Building;
    }

    public boolean touchDown(int screenX, int screenY, int pointer, int button){
        return player.state == PlayerComponent.PlayerState.Building && button == Input.Buttons.LEFT;
    }

    public boolean touchUp (int screenX, int screenY, int pointer, int button) {
        if(player.state == PlayerComponent.PlayerState.Building) {
            if (button == Input.Buttons.LEFT) {
                for (CellComponent c : cellsMapper.get(player.tryingBuilding).cells)
                    if (!BuildingMakingSystem.isBlocked(c)) {
                        player.state = PlayerComponent.PlayerState.Normal;
                        Mappers.world.get(player.tryingBuilding).bounds.getRectangle().setPosition(c.position.x * 32, c.position.y * 32);
                        engine.addEntity(player.tryingBuilding);
                        player.tryingBuilding = null;
                    }
            }
            return true;
        }
        return false;
    }
}
