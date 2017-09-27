package com.mygdx.game.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Components.*;
import com.mygdx.game.Components.HUD.HUDComponent;
import com.mygdx.game.Components.WorldObjects.ResourceComponent;
import com.mygdx.game.Components.WorldObjects.WorldPositionComponent;
import com.mygdx.game.Components.WorldObjects.WorldObjectComponent;
import com.mygdx.game.Mappers.Mappers;

import java.util.Comparator;

public class MapRendererSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;
    private ImmutableArray<Entity> worldBuildings;
    private ImmutableArray<Entity> worldUnits;
    private ImmutableArray<Entity> resources;
    private ComponentMapper<MapComponent> cm = ComponentMapper.getFor(MapComponent.class);
    private ComponentMapper<CellsComponent> cellsMapper = ComponentMapper.getFor(CellsComponent.class);
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<PlayerComponent> playerMapper = ComponentMapper.getFor(PlayerComponent.class);
    private Engine engine;
    private int[] before_units = {0, 1};
    private int[] after_units = {2,3,4,5};
    private Array<Entity> renderQueue;
    private Comparator<Entity> comparator;

    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(MapComponent.class, HUDComponent.class).get());
        worldBuildings = engine.getEntitiesFor(Family.all(CellsComponent.class, RenderableComponent.class).get());
        worldUnits = engine.getEntitiesFor(Family.all(TextureComponent.class, WorldObjectComponent.class,
                AnimationComponent.class, WorldPositionComponent.class).get());

        this.engine = engine;
        renderQueue = new Array<Entity>();

        comparator = new Comparator<Entity>() {
            @Override
            public int compare(Entity entityA, Entity entityB) {
                return (int)Math.signum(Mappers.worldPosition.get(entityB).position.y -
                        Mappers.worldPosition.get(entityA).position.y);
            }
        };
    }

    public void update(float deltaTime) {
        worldBuildings = engine.getEntitiesFor(Family.all(CellsComponent.class, RenderableComponent.class).get());
        worldUnits = engine.getEntitiesFor(Family.all(TextureComponent.class, WorldObjectComponent.class, AnimationComponent.class)
                .exclude(ResourceComponent.class).get());
        resources = engine.getEntitiesFor(Family.all(TextureComponent.class, WorldObjectComponent.class, AnimationComponent.class,
                ResourceComponent.class).get());
        renderQueue.addAll(worldUnits.toArray());
        renderQueue.sort(comparator);
        for(Entity e : entities){
            MapComponent mapComponent = cm.get(e);
            updateMap();
            mapComponent.renderer.setView(mapComponent.camera);
            mapComponent.renderer.render(before_units);
            mapComponent.renderer.getBatch().begin();
            for(Entity resource: resources){
                TextureComponent regionComponent = Mappers.texture.get(resource);
                WorldPositionComponent pos = Mappers.worldPosition.get(resource);
                mapComponent.renderer.getBatch().draw(
                        regionComponent.region,
                        pos.position.x,
                        pos.position.y);
            }
            for(Entity worldUnit : renderQueue) {
                TextureComponent regionComponent = Mappers.texture.get(worldUnit);
                WorldPositionComponent pos = Mappers.worldPosition.get(worldUnit);
                mapComponent.renderer.getBatch().draw(
                        regionComponent.region,
                        pos.position.x + regionComponent.offsetX,
                        pos.position.y + regionComponent.offsetY);
            }
            mapComponent.renderer.getBatch().end();
            mapComponent.renderer.render(after_units);
        }
        renderQueue.clear();
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

