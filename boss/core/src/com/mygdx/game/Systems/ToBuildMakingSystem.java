package com.mygdx.game.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mygdx.game.Components.*;
import com.mygdx.game.Components.WorldObjects.WorldPositionComponent;
import com.mygdx.game.Mappers.Mappers;
import com.mygdx.game.PathfindingUtils.MapGraph;

public class ToBuildMakingSystem extends EntitySystem{
    Engine engine;
    ImmutableArray<Entity> entities;
    MapGraph mapGraph;
    private ComponentMapper<CellsComponent> cellsMapper = ComponentMapper.getFor(CellsComponent.class);

    @Override
    public void addedToEngine(Engine engine) {
        mapGraph = Mappers.graph.get(engine.getEntitiesFor(Family.all(MapGraphComponent.class).get()).first()).mapGraph;
        this.engine = engine;
    }

    @Override
    public void update(float deltaTime) {
        entities = engine.getEntitiesFor(Family.all(ToBuildComponent.class).get());
        TiledMapTileLayer toDelete = (TiledMapTileLayer)Mappers.map.get(engine.getEntitiesFor(Family.all(MapComponent.class).get()).get(0)).map.getLayers().get("trying_building");
        for(Entity e: entities) {
            ToBuildComponent toBuildComponent = Mappers.toBuildComponentMapper.get(e);
            WorldPositionComponent positionComponent = Mappers.worldPosition.get(e);
            WorldPositionComponent positionComponentBuilding = Mappers.worldPosition.get(toBuildComponent.building);
            PlayerComponent player = Mappers.player.get(e);
            if (positionComponent.position.epsilonEquals(positionComponentBuilding.position,0.3f) &&
                    !BuildingMakingSystem.isBlocked(cellsMapper.get(toBuildComponent.building), mapGraph)) {
                for (CellComponent c : cellsMapper.get(toBuildComponent.building).cells) {
                    toDelete.setCell((int) c.position.x, (int) c.position.y, null);
                    if (c.blocked) mapGraph.setCollision(c.position.x, c.position.y, true);
                }
                player.state = PlayerComponent.PlayerState.Normal;
                engine.addEntity(toBuildComponent.building);
                player.tryingBuilding = null;
                e.remove(ToBuildComponent.class);
            }
        }
    }
}
