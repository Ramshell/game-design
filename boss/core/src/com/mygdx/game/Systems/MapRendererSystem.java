package com.mygdx.game.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.mygdx.game.Components.*;
import com.mygdx.game.Components.HUD.HUDComponent;
import com.mygdx.game.Components.WorldObjects.WorldObjectComponent;
import com.mygdx.game.Mappers.Mappers;

public class MapRendererSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;
    private ImmutableArray<Entity> worldBuildings;
    private ComponentMapper<MapComponent> cm = ComponentMapper.getFor(MapComponent.class);
    private ComponentMapper<CellsComponent> cellsMapper = ComponentMapper.getFor(CellsComponent.class);
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<PlayerComponent> playerMapper = ComponentMapper.getFor(PlayerComponent.class);
    private Engine engine;


    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(MapComponent.class, HUDComponent.class).get());
        worldBuildings = engine.getEntitiesFor(Family.all(CellsComponent.class, RenderableComponent.class).get());
        this.engine = engine;
    }

    public void update(float deltaTime) {
        worldBuildings = engine.getEntitiesFor(Family.all(CellsComponent.class, RenderableComponent.class).get());
        for(Entity e : entities){
            MapComponent mapComponent = cm.get(e);
            mapComponent.renderer.setView(mapComponent.camera);
            mapComponent.renderer.getBatch().begin();
            updateMap();
            mapComponent.renderer.getBatch().end();
            mapComponent.renderer.render();
        }
    }


    private void updateMap() {
        for(Entity ent : worldBuildings){
            CellsComponent cellsComponent = cellsMapper.get(ent);
            WorldObjectComponent wo = Mappers.world.get(ent);
            for(CellComponent p : cellsComponent.cells){
                p.layer.setCell((int)p.position.x, (int)p.position.y, p.cell);
            }
        }
    }
}

