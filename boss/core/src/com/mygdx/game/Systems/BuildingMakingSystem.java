package com.mygdx.game.Systems;


import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Components.*;
import com.mygdx.game.Components.HUD.HUDComponent;
import com.mygdx.game.Components.WorldObjects.Buildings.BuildingHolder;
import com.mygdx.game.Components.WorldObjects.Buildings.TryingBuildingComponent;
import com.mygdx.game.Components.WorldObjects.WorldObjectComponent;
import com.mygdx.game.Mappers.Mappers;
import com.mygdx.game.Mappers.ResourceMapper;
import com.mygdx.game.PathfindingUtils.MapGraph;

public class BuildingMakingSystem extends EntitySystem{
    MapComponent mapComponent;
    private MapGraph mapGraph;
    private ImmutableArray<Entity> entities;
    private ImmutableArray<Entity> worldBuildings;
    private Engine engine;
    private PlayerComponent player;
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<MapComponent> cm = ComponentMapper.getFor(MapComponent.class);
    private ComponentMapper<CellsComponent> cellsMapper = ComponentMapper.getFor(CellsComponent.class);
    private ShapeRenderer shapeRenderer;

    public BuildingMakingSystem(){
        shapeRenderer = new ShapeRenderer();
    }

    public void addedToEngine(Engine e) {
        engine = e;
        mapComponent = cm.get(engine.getEntitiesFor(Family.all(MapComponent.class, HUDComponent.class).get()).first());
        worldBuildings = engine.getEntitiesFor(Family.all(WorldObjectComponent.class, CellsComponent.class, RenderableComponent.class).get());
        this.mapGraph = Mappers.graph.get(engine.getEntitiesFor(Family.one(MapGraphComponent.class).get())
                .first()).mapGraph;
        this.player = Mappers.player.get(engine.getEntitiesFor(Family.all(PlayerComponent.class).get()).first());
    }


    public void update(float deltaTime) {
        entities = engine.getEntitiesFor(Family.all(TryingBuildingComponent.class).get());
        worldBuildings = engine.getEntitiesFor(Family.all(ToBuildComponent.class).get());
        processEntities(entities, Mappers.tryingBuildingComponentMapper);
    }

    public static boolean isBlocked(CellComponent c, MapGraph mapGraph) {
        return c.blocked && (mapGraph.colideO1((int)c.position.x, (int)c.position.y) ||
                mapGraph.colideO1Building((int)c.position.x, (int)c.position.y));
    }

    public static boolean isBlocked(CellsComponent cells, MapGraph mapGraph) {
        boolean res = false;
        for(CellComponent c : cells.cells){
            res = res || isBlocked(c, mapGraph);
        }
        return res;
    }

    private void processEntities(ImmutableArray<Entity> entities, ComponentMapper<? extends  BuildingHolder> mapper){
        for(Entity e: entities){
            BuildingHolder buildingHolder = mapper.get(e);
            CellsComponent cellsComponent = cellsMapper.get(buildingHolder.building);
            WorldObjectComponent wo = Mappers.world.get(buildingHolder.building);
            TiledMapTileLayer layer = (TiledMapTileLayer)mapComponent.map.getLayers().get("trying_building");
            Vector3 v = buildingHolder.getCoords(mapComponent.camera);
            if(MovementSystem.outsideWorld(new Vector2(
                            ((int)v.x / ResourceMapper.tileWidth) *
                                    ResourceMapper.tileWidth,
                            ((int)v.y / ResourceMapper.tileHeight) *
                                    ResourceMapper.tileHeight),
                    wo.bounds.getRectangle().width,
                    wo.bounds.getRectangle().height)) return;
            wo.bounds.getRectangle().setPosition(((int)v.x / ResourceMapper.tileWidth) * ResourceMapper.tileWidth, ((int)v.y / ResourceMapper.tileHeight) * ResourceMapper.tileHeight);
            Mappers.spawn.get(buildingHolder.building).setPos(
                    (int)v.x / ResourceMapper.tileWidth,
                    (int)v.y / ResourceMapper.tileHeight);
            Mappers.worldPosition.get(buildingHolder.building).position.set(
                    ((int)v.x / ResourceMapper.tileWidth) * ResourceMapper.tileWidth,
                    ((int)v.y / ResourceMapper.tileHeight) * ResourceMapper.tileHeight);
            for(CellComponent c : cellsComponent.cells) {
                layer.setCell((int) c.position.x, (int) c.position.y, null);
                c.position.x = v.x / ResourceMapper.tileWidth + c.xOffset;
                c.position.y = v.y / ResourceMapper.tileHeight + c.yOffset;
                SelectionComponent s = ComponentMapper.getFor(SelectionComponent.class).get(e);
                if(s == null){
                    layer.setCell((int) c.position.x, (int) c.position.y, null);
                    continue;
                }
                if(isBlocked(c, mapGraph)){
                    Gdx.gl.glEnable(GL20.GL_BLEND);
                    Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
                    shapeRenderer.setProjectionMatrix(mapComponent.camera.combined);
                    shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                    shapeRenderer.setColor(new Color(
                            1,
                            0,
                            0,
                            ResourceMapper.opacySelectionColor));
                    shapeRenderer.rect((int)c.position.x * ResourceMapper.tileWidth,
                            (int)c.position.y * ResourceMapper.tileHeight,
                            ResourceMapper.tileWidth, ResourceMapper.tileHeight);
                    shapeRenderer.end();
                    Gdx.gl.glDisable(GL20.GL_BLEND);
                }
                layer.setCell((int) c.position.x, (int) c.position.y, c.cell);
            }
        }
    }

}
