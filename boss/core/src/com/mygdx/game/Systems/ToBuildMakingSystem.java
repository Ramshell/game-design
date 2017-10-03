package com.mygdx.game.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Components.*;
import com.mygdx.game.Components.WorldObjects.WorldPositionComponent;
import com.mygdx.game.Mappers.Mappers;
import com.mygdx.game.Mappers.ResourceMapper;
import com.mygdx.game.PathfindingUtils.MapGraph;

public class ToBuildMakingSystem extends EntitySystem{
    Engine engine;
    ImmutableArray<Entity> entities;
    MapGraph mapGraph;
    private Vector2 compVector;
    private ComponentMapper<CellsComponent> cellsMapper = ComponentMapper.getFor(CellsComponent.class);

    @Override
    public void addedToEngine(Engine engine) {
        mapGraph = Mappers.graph.get(engine.getEntitiesFor(Family.all(MapGraphComponent.class).get()).first()).mapGraph;
        this.engine = engine;
        compVector = new Vector2();
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
            compVector.set(toBuildComponent.x, toBuildComponent.y);
            if (positionComponent.position.epsilonEquals(compVector,1) &&
                    !BuildingMakingSystem.isBlocked(cellsMapper.get(toBuildComponent.building), mapGraph) &&
                    player.resources >= Mappers.world.get(toBuildComponent.building).cost) {
                player.resources -= Mappers.world.get(toBuildComponent.building).cost;
                for (CellComponent c : cellsMapper.get(toBuildComponent.building).cells) {
                    toDelete.setCell((int) c.position.x, (int) c.position.y, null);
                    if (c.blocked) {
                        mapGraph.setCollision(c.position.x, c.position.y, true);
                        mapGraph.getNode((int)c.position.x,(int) c.position.y).entities.add(toBuildComponent.building);
                    }
                }
                player.state = PlayerComponent.PlayerState.Normal;
                engine.addEntity(toBuildComponent.building);
                player.tryingBuilding = null;
                e.remove(ToBuildComponent.class);
            }else if(ComponentMapper.getFor(SelectionComponent.class).get(e) == null){
                for (CellComponent c : cellsMapper.get(toBuildComponent.building).cells) {
                    toDelete.setCell((int) c.position.x, (int) c.position.y, null);
                }
            }else{
                for (CellComponent c : cellsMapper.get(toBuildComponent.building).cells) {
                    toDelete.setCell((int) c.position.x, (int) c.position.y, c.cell);
                }
            }
        }
    }
}
