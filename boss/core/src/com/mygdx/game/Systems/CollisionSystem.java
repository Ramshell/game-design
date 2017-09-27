package com.mygdx.game.Systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.OrderedSet;
import com.mygdx.game.Components.MapGraphComponent;
import com.mygdx.game.Components.VelocityComponent;
import com.mygdx.game.Components.WorldObjects.DynamicWOComponent;
import com.mygdx.game.Components.WorldObjects.WorldObjectComponent;
import com.mygdx.game.Components.WorldObjects.WorldPositionComponent;
import com.mygdx.game.Mappers.Mappers;
import com.mygdx.game.Mappers.ResourceMapper;
import com.mygdx.game.OOP.Actions.MoveAction;
import com.mygdx.game.OOP.Actions.NotSmoothingMoveAction;
import com.mygdx.game.PathfindingUtils.MapGraph;

public class CollisionSystem extends EntitySystem{
    Engine engine;
    private ImmutableArray<Entity> dynamicEntities;
    MapGraph mapGraph;

    public void addedToEngine(Engine e) {
        this.engine = e;
        mapGraph = Mappers.graph.get(e.getEntitiesFor(Family.all(MapGraphComponent.class).get()).first()).mapGraph;
    }

    public void update(float deltaTime){
        dynamicEntities = engine.getEntitiesFor(Family.all(
                DynamicWOComponent.class,
                WorldObjectComponent.class,
                WorldPositionComponent.class,
                VelocityComponent.class).get());
        for(Entity dynamicEntity: dynamicEntities){
            WorldObjectComponent worldObjectComponent = Mappers.world.get(dynamicEntity);
            WorldPositionComponent worldPositionComponent = Mappers.worldPosition.get(dynamicEntity);
            int fromX = (int) worldPositionComponent.position.x / ResourceMapper.tileWidth;
            int fromY = (int) worldPositionComponent.position.y / ResourceMapper.tileHeight;
            int toX   = (int) (fromX + worldObjectComponent.bounds.getRectangle().width / ResourceMapper.tileWidth);
            int toY   = (int) (fromY + worldObjectComponent.bounds.getRectangle().height / ResourceMapper.tileHeight);
            int widthInTiles = (int) worldObjectComponent.bounds.getRectangle().width / ResourceMapper.tileWidth;
            int heightInTiles = (int) worldObjectComponent.bounds.getRectangle().height / ResourceMapper.tileHeight;
            checkCollision(fromX, fromY, toX, toY, widthInTiles, heightInTiles,
                    deltaTime, worldPositionComponent, worldObjectComponent, dynamicEntity);

        }
    }

    private void checkCollision(int fromX, int fromY, int toX, int toY, int widthInTiles, int heightInTiles,
                                float deltaTime, WorldPositionComponent wp, WorldObjectComponent worldObjectComponent, Entity dynamicEntity) {
        VelocityComponent velocity = Mappers.velocity.get(dynamicEntity);
        Rectangle r = new Rectangle(worldObjectComponent.bounds.getRectangle());
        Vector2 v = wp.position.cpy()
                .add(velocity.accel.cpy().scl(1/2 * deltaTime * deltaTime)
                        .add(velocity.pos.cpy().scl(deltaTime)));
        r.setPosition(v);
        for(int i = fromX; i < Math.min(mapGraph.width, toX + widthInTiles); ++i) {
            for (int j = fromY; j < Math.min(mapGraph.height, toY + heightInTiles); ++j) {
                for(Entity e : mapGraph.getNode(i, j).entities){
                    if(!e.equals(dynamicEntity) && r.overlaps(Mappers.world.get(e).bounds.getRectangle()) && Mappers.target.get(dynamicEntity) != null) {
                        velocity.pos.setZero();
                        velocity.accel.setZero();
                        break;
                    }
                }
            }
        }
    }
}
