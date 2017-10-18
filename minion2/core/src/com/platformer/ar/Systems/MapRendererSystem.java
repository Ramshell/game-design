package com.platformer.ar.Systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.platformer.ar.Components.CameraComponent;
import com.platformer.ar.Components.MapRendererComponent;
import com.platformer.ar.Components.TransformComponent;
import com.platformer.ar.Components.World.SolidComponent;

public class MapRendererSystem extends IteratingSystem{
    ComponentMapper<MapRendererComponent> rendererComponentMapper = ComponentMapper.getFor(MapRendererComponent.class);
    CameraComponent camera;

    public MapRendererSystem() {
        super(Family.all(MapRendererComponent.class).get());
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        camera = ComponentMapper.getFor(CameraComponent.class).get(getEngine().getEntitiesFor(Family.all(CameraComponent.class).get()).first());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        camera.camera.position.set(ComponentMapper.getFor(TransformComponent.class).get(camera.target).position);
        camera.camera.update();
        rendererComponentMapper.get(entity).renderer.setView(camera.camera);
        rendererComponentMapper.get(entity).renderer.render();
//        for (Entity e : getEngine().getEntitiesFor(Family.all(SolidComponent.class).get())){
//            ShapeRenderer shapeRenderer = new ShapeRenderer();
//            shapeRenderer.setProjectionMatrix(camera.camera.combined);
//            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//            shapeRenderer.rect(e.getComponent(SolidComponent.class).rectangle.x,
//                    e.getComponent(SolidComponent.class).rectangle.y,
//                    e.getComponent(SolidComponent.class).rectangle.getWidth(),
//                    e.getComponent(SolidComponent.class).rectangle.getHeight());
//            shapeRenderer.end();
//        }
    }
}
