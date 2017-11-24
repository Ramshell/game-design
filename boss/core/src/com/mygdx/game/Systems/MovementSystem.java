package com.mygdx.game.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Components.CameraComponent;
import com.mygdx.game.Components.PositionComponent;
import com.mygdx.game.Components.VelocityComponent;
import com.mygdx.game.Mappers.Mappers;
import com.mygdx.game.Mappers.ResourceMapper;

public class MovementSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;
    private Entity cc;

    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);

    public MovementSystem() {}

    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        entities = engine.getEntitiesFor(Family.all(PositionComponent.class, VelocityComponent.class).get());
        cc = engine.getEntitiesFor(Family.all(PositionComponent.class, CameraComponent.class).get()).first();
    }

    public void update(float deltaTime) {
        for (Entity entity : getEngine().getEntitiesFor(Family.all(PositionComponent.class, VelocityComponent.class)
                .exclude(CameraComponent.class).get())) {
            PositionComponent position = pm.get(entity);
            VelocityComponent velocity = vm.get(entity);
            Vector2 v = position.pos.cpy()
                    .add(velocity.accel.cpy().scl(1/2 * deltaTime * deltaTime)
                    .add(velocity.pos.cpy().scl(deltaTime)));
            if(outsideWorld(v)) continue;
            position.pos = v;
            velocity.increment(deltaTime);
        }
        for (Entity entity : getEngine().getEntitiesFor(Family.all(PositionComponent.class, VelocityComponent.class)
                .one(CameraComponent.class).get())) {
            PositionComponent position = pm.get(entity);
            VelocityComponent velocity = vm.get(entity);
            Vector2 v = position.pos.cpy()
                    .add(velocity.accel.cpy().scl(1/2 * deltaTime * deltaTime)
                            .add(velocity.pos.cpy().scl(deltaTime)));
            if(outsideWorldCamera(v, entity)) continue;
            position.pos = v;
            velocity.increment(deltaTime);
        }
    }

    private boolean outsideWorldCamera(Vector2 v, Entity entity) {
        CameraComponent cameraComponent = Mappers.camera.get(entity);
        return v.y < cameraComponent.getCamera().viewportHeight / 2 || v.y > ResourceMapper.tileHeight * ResourceMapper.height - cameraComponent.getCamera().viewportHeight / 2 ||
                v.x < cameraComponent.getCamera().viewportWidth / 2 || v.x > ResourceMapper.tileWidth * ResourceMapper.width - cameraComponent.getCamera().viewportWidth / 2;
    }

    public static Vector2 screenToIso(float screenX, float screenY){
        return new Vector2(- screenY + screenX * 0.5f, screenY + screenX * 0.5f);
    }

    public static Vector2 isoToScreen(float isoScreenX, float isoScreenY){
        return new Vector2(isoScreenX + isoScreenY,(- isoScreenX + isoScreenY) * 0.5f );
    }

    /**
     * @param check is a vector unprojected to the camera position
     * **/
    public static boolean outsideWorld(Vector2 check){
        return  outsideWorld(check.x, check.y);
    }

    public static boolean outsideWorld(Vector2 check, float width, float height){
        return  outsideWorld(check.x, check.y)              ||
                outsideWorld(check.x + width, check.y)   ||
                outsideWorld(check.x, check.y + height)  ||
                outsideWorld(check.x + width, check.y + height);
    }

    public static boolean outsideWorld(float x, float y){
        return  y < 0 || y > ResourceMapper.tileHeight * ResourceMapper.height ||
                x < 0 || x > ResourceMapper.tileWidth * ResourceMapper.width;
    }

}
