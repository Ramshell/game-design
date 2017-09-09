package com.mygdx.game.Systems;


import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Components.*;
import com.mygdx.game.Components.HUD.HUDComponent;
import com.mygdx.game.Components.WorldObjects.WorldObjectComponent;
import com.mygdx.game.Mappers.ResourceMapper;

public class BuildingMakingSystem extends EntitySystem{
    private ImmutableArray<Entity> entities;
    private ImmutableArray<Entity> worldBuildings;
    private PlayerComponent player;
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<MapComponent> cm = ComponentMapper.getFor(MapComponent.class);
    private ComponentMapper<CellsComponent> cellsMapper = ComponentMapper.getFor(CellsComponent.class);


    public BuildingMakingSystem(PlayerComponent player){
        this.player = player;
    }

    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(MapComponent.class, HUDComponent.class).get());
        worldBuildings = engine.getEntitiesFor(Family.all(WorldObjectComponent.class, CellsComponent.class, RenderableComponent.class).get());
    }


    public void update(float deltaTime) {
        if(player.state.equals(PlayerComponent.PlayerState.Building)){
            CellsComponent cellsComponent = cellsMapper.get(player.tryingBuilding);
            for(Entity e : entities){
                MapComponent mapComponent = cm.get(e);
                TiledMapTileLayer layer = (TiledMapTileLayer)mapComponent.map.getLayers().get("trying_building");
                TiledMapTileLayer wLayer = (TiledMapTileLayer)mapComponent.map.getLayers().get("wrong_layer");
                for(CellComponent c : cellsComponent.cells) {
                    Vector3 v = mapComponent.camera.unproject(new Vector3(Gdx.input.getX(),Gdx.input.getY(), 0));
                    Vector2 v2 = MovementSystem.screenToIso(v.x - ResourceMapper.tileWidth / 2, v.y- ResourceMapper.tileHeight / 2);
                    if(MovementSystem.outsideWorld(v2)) continue;
                    TiledMapTileLayer.Cell wrongCell = new TiledMapTileLayer.Cell();
                    wrongCell.setTile(mapComponent.map.getTileSets().getTileSet("wrong").getTile(241));
                    layer.setCell((int) c.position.x, (int) c.position.y, null);
                    wLayer.setCell((int)c.position.x, (int)c.position.y, null);
                    c.position.x = v2.x / ResourceMapper.tileWidth;
                    c.position.y = v2.y / ResourceMapper.tileHeight;
                    if(isBlocked(c))
                        wLayer.setCell((int)c.position.x, (int)c.position.y, wrongCell);
                    layer.setCell((int) c.position.x, (int) c.position.y, c.cell);
                }
            }
        }
    }

    public static boolean isBlocked(CellComponent c) {
        return c.layer.getCell((int)c.position.x, (int)c.position.y) != null && c.layer.getCell((int)c.position.x, (int)c.position.y).getTile().getProperties().get("blocked") != null;
    }

    public static boolean isBlocked(CellsComponent cells) {
        boolean res = false;
        for(CellComponent c : cells.cells){
            res = res || isBlocked(c);
        }
        return res;
    }

}
