package com.mygdx.game.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Components.*;
import com.mygdx.game.Components.HUD.HUDComponent;
import com.mygdx.game.Components.WorldObjects.WorldPositionComponent;
import com.mygdx.game.Components.WorldObjects.WorldObjectComponent;
import com.mygdx.game.Mappers.Mappers;

public class MapRendererSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;
    private ImmutableArray<Entity> worldBuildings;
    private ImmutableArray<Entity> worldUnits;
    private ComponentMapper<MapComponent> cm = ComponentMapper.getFor(MapComponent.class);
    private ComponentMapper<CellsComponent> cellsMapper = ComponentMapper.getFor(CellsComponent.class);
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<PlayerComponent> playerMapper = ComponentMapper.getFor(PlayerComponent.class);
    private Engine engine;
    private int[] before_units = {0, 1};
    private int[] after_units = {2,3,4,5};


    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(MapComponent.class, HUDComponent.class).get());
        worldBuildings = engine.getEntitiesFor(Family.all(CellsComponent.class, RenderableComponent.class).get());
        worldUnits = engine.getEntitiesFor(Family.all(TextureComponent.class, WorldObjectComponent.class,
                AnimationComponent.class, PositionComponent.class).get());

        this.engine = engine;
    }

    public void update(float deltaTime) {
        worldBuildings = engine.getEntitiesFor(Family.all(CellsComponent.class, RenderableComponent.class).get());
        worldUnits = engine.getEntitiesFor(Family.all(TextureComponent.class, WorldObjectComponent.class, AnimationComponent.class).get());
        for(Entity e : entities){
            MapComponent mapComponent = cm.get(e);
            updateMap();
            mapComponent.renderer.setView(mapComponent.camera);
            mapComponent.renderer.render(before_units);
            mapComponent.renderer.getBatch().begin();
            for(Entity worldUnit : worldUnits) {
                TextureComponent regionComponent = Mappers.texture.get(worldUnit);
                WorldObjectComponent unit = Mappers.world.get(worldUnit);
                WorldPositionComponent pos = Mappers.worldPosition.get(worldUnit);
                mapComponent.renderer.getBatch().draw(
                        regionComponent.region,
                        pos.position.x,
                        pos.position.y);
            }
            mapComponent.renderer.getBatch().end();
            mapComponent.renderer.render(after_units);
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

