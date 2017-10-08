package com.mygdx.game.Systems;

import box2dLight.PointLight;
import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Components.*;
import com.mygdx.game.Components.WorldObjects.LightComponent;
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
    private MatchComponent matchComponent;

    @Override
    public void addedToEngine(Engine engine) {
        mapGraph = Mappers.graph.get(engine.getEntitiesFor(Family.all(MapGraphComponent.class).get()).first()).mapGraph;
        this.engine = engine;
        compVector = new Vector2();
        matchComponent = Mappers.matchComponentMapper.get(getEngine().getEntitiesFor(Family.all(MatchComponent.class).get()).first());

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
            for (CellComponent c : cellsMapper.get(toBuildComponent.building).cells) {
                toDelete.setCell((int) c.position.x, (int) c.position.y, null);
            }
            if (positionComponent.position.epsilonEquals(compVector,1) &&
                    !BuildingMakingSystem.isBlocked(cellsMapper.get(toBuildComponent.building), mapGraph) &&
                    player.resources >= Mappers.world.get(toBuildComponent.building).cost) {
                player.resources -= Mappers.world.get(toBuildComponent.building).cost;
                for (CellComponent c : cellsMapper.get(toBuildComponent.building).cells) {
                    if (c.blocked) {
                        mapGraph.setCollision(c.position.x, c.position.y, true);
                        mapGraph.getNode((int)c.position.x,(int) c.position.y).entities.add(toBuildComponent.building);
                    }
                }
                player.state = PlayerComponent.PlayerState.Normal;
                LightComponent lightComponent =  new LightComponent();
                lightComponent.visibility = Mappers.world.get(toBuildComponent.building).visibility;
                lightComponent.light = new PointLight(matchComponent.match.rayHandler, 128, new Color(1,1,1,1), lightComponent.visibility,
                        Mappers.world.get(toBuildComponent.building).bounds.getRectangle().x + Mappers.world.get(toBuildComponent.building).bounds.getRectangle().getWidth() / 2,
                        Mappers.world.get(toBuildComponent.building).bounds.getRectangle().y + Mappers.world.get(toBuildComponent.building).bounds.getRectangle().getHeight() / 2);
                toBuildComponent.building.add(lightComponent);
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
