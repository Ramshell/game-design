package com.mygdx.game.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.mygdx.game.Components.HUD.HUDComponent;
import com.mygdx.game.Components.MapComponent;
import com.mygdx.game.Mappers.Mappers;

public class MapRendererSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;
    private ComponentMapper<MapComponent> cm = ComponentMapper.getFor(MapComponent.class);


    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(MapComponent.class, HUDComponent.class).get());

    }

    public void update(float deltaTime) {
        for(Entity e : entities){
            MapComponent entity = cm.get(e);
            HUDComponent hud = Mappers.hud.get(e);
            entity.renderer.setView(entity.camera);
            entity.renderer.render();
            entity.renderer.getBatch().setProjectionMatrix(hud.stage.getCamera().combined);
            hud.stage.draw();
        }
    }
}

