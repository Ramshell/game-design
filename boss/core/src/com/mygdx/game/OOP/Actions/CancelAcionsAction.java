package com.mygdx.game.OOP.Actions;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mygdx.game.Components.*;
import com.mygdx.game.Components.Combat.AttackProgressionComponent;
import com.mygdx.game.Components.WorldObjects.Buildings.TryingBuildingComponent;
import com.mygdx.game.Mappers.Mappers;

public class CancelAcionsAction extends Action<Entity>{

    private final Engine engine;

    public CancelAcionsAction(Engine engine){
        this.engine = engine;
    }
    
    @Override
    public void act(Entity e) {
        if(Mappers.tryingBuildingComponentMapper.get(e) != null) {
            TryingBuildingComponent tryingBuildingComponent = Mappers.tryingBuildingComponentMapper.get(e);
            ComponentMapper<CellsComponent> cellsMapper = ComponentMapper.getFor(CellsComponent.class);
            TiledMapTileLayer toDelete = (TiledMapTileLayer)Mappers.map.get(engine.getEntitiesFor(Family.all(MapComponent.class).get()).get(0)).map.getLayers().get("trying_building");
            for (CellComponent c : cellsMapper.get(tryingBuildingComponent.building).cells) {
                toDelete.setCell((int) c.position.x, (int) c.position.y, null);
            }
            e.remove(TryingBuildingComponent.class);
        }
        if(Mappers.toBuildComponentMapper.get(e) != null) {
            ToBuildComponent toBuildComponent = Mappers.toBuildComponentMapper.get(e);
            ComponentMapper<CellsComponent> cellsMapper = ComponentMapper.getFor(CellsComponent.class);
            TiledMapTileLayer toDelete = (TiledMapTileLayer)Mappers.map.get(engine.getEntitiesFor(Family.all(MapComponent.class).get()).get(0)).map.getLayers().get("trying_building");
            for (CellComponent c : cellsMapper.get(toBuildComponent.building).cells) {
                toDelete.setCell((int) c.position.x, (int) c.position.y, null);
            }
            e.remove(ToBuildComponent.class);
        }
        if(Mappers.startGatheringComponentComponentMapper.get(e) != null)
            e.remove(StartGatheringComponent.class);

        if(Mappers.attackProgressionComponentMapper.get(e) != null)
            e.remove(AttackProgressionComponent.class);
    }
}
