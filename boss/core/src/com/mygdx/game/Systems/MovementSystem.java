package com.mygdx.game.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Components.PositionComponent;
import com.mygdx.game.Components.VelocityComponent;
import com.mygdx.game.Mappers.ResourceMapper;

public class MovementSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;

    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);

    public MovementSystem() {}

    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(PositionComponent.class, VelocityComponent.class).get());
    }

    public void update(float deltaTime) {
        for (Entity entity : entities) {
            PositionComponent position = pm.get(entity);
            VelocityComponent velocity = vm.get(entity);
            Vector2 v = position.pos.cpy()
                    .add(velocity.accel.cpy().scl(1/2 * deltaTime * deltaTime)
                    .add(velocity.pos.cpy().scl(deltaTime)));
            if(outsideWorld(screenToIso(v))) continue;
            position.pos = v;
            velocity.increment(deltaTime);
        }
    }

    public static Vector2 screenToIso(float screenX, float screenY){
        return new Vector2(- screenY + screenX * 0.5f, screenY + screenX * 0.5f);
    }

    public static Vector2 screenToIso(Vector2 v){
        return new Vector2(- v.y + v.x * 0.5f,v.y + v.x * 0.5f );
    }

    public static Vector2 isoToScreen(float isoScreenX, float isoScreenY){
        return new Vector2(isoScreenX + isoScreenY,(- isoScreenX + isoScreenY) * 0.5f );
    }

    public static Vector2 isoToScreen(Vector2 v){
        return new Vector2(v.x + v.y,(- v.x + v.y) * 0.5f );
    }

    public static boolean outsideWorld(Vector2 check){
        return check.y < 0 || check.y > 128 * 32 || check.x < 0 || check.x > 128 * 32;
    }

    public static boolean outsideWorld(Vector2 check, float xoffset, float yoffset){

        return outsideWorld(check.x - xoffset, check.y) || outsideWorld(check.x + xoffset, check.y) ||
                outsideWorld(check.x, check.y - yoffset) || outsideWorld(check.x, check.y + yoffset);
    }

    public static boolean outsideWorld(float x, float y){
        return y < 0 || y > 128 * 32 || x < 0 || x > 128 * 32;
    }
}
