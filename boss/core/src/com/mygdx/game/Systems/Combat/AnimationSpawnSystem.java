package com.mygdx.game.Systems.Combat;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.mygdx.game.Components.Combat.DamageSpawnComponent;
import com.mygdx.game.Components.HUD.HUDComponent;
import com.mygdx.game.Components.MapComponent;
import com.mygdx.game.Components.WorldObjects.AnimationSpawnComponent;
import com.mygdx.game.Mappers.AssetsMapper;
import com.mygdx.game.Mappers.Mappers;

public class AnimationSpawnSystem extends EntitySystem {
    OrthogonalTiledMapRenderer renderer;
    OrthographicCamera camera;
    HUDComponent hudComponent;
    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        renderer = Mappers.map.get(engine.getEntitiesFor(Family.all(MapComponent.class).get()).first()).renderer;
        camera = Mappers.map.get(engine.getEntitiesFor(Family.all(MapComponent.class).get()).first()).camera;
        hudComponent = Mappers.hud.get(engine.getEntitiesFor(Family.all(HUDComponent.class).get()).first());
    }

    @Override
    public void update(float deltaTime) {
        AnimationSpawnComponent animationSpawnComponent;
        renderer.getBatch().setProjectionMatrix(camera.combined);
        renderer.getBatch().begin();
        for(Entity e : getEngine().getEntitiesFor(Family.all(AnimationSpawnComponent.class).get())){
            animationSpawnComponent = Mappers.animationSpawnComponentMapper.get(e);
            renderer.getBatch().draw(animationSpawnComponent.anim.getKeyFrame(animationSpawnComponent.time),
                    animationSpawnComponent.x + animationSpawnComponent.offsetX,
                    animationSpawnComponent.y + animationSpawnComponent.offsetY);
            animationSpawnComponent.time += deltaTime;
            if(animationSpawnComponent.anim.isAnimationFinished(animationSpawnComponent.time)){
                getEngine().removeEntity(e);
            }
        }
        renderer.getBatch().end();
    }
}
