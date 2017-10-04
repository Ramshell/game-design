package com.mygdx.game.Systems.Combat;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.mygdx.game.Components.Combat.DamageSpawnComponent;
import com.mygdx.game.Components.HUD.HUDComponent;
import com.mygdx.game.Components.MapComponent;
import com.mygdx.game.Mappers.AssetsMapper;
import com.mygdx.game.Mappers.Mappers;
import com.mygdx.game.Mappers.ResourceMapper;
import javafx.scene.paint.Color;

public class DamageSpawnSystem extends EntitySystem{
    OrthogonalTiledMapRenderer renderer;
    OrthographicCamera camera;
    BitmapFont bitmapFont;
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
        DamageSpawnComponent damageSpawnComponent;
        renderer.getBatch().setProjectionMatrix(camera.combined);
        renderer.getBatch().begin();
        AssetsMapper.damageSkin.getFont("subtitle").setColor(new com.badlogic.gdx.graphics.Color(1,0,0,1));
        for(Entity e : getEngine().getEntitiesFor(Family.all(DamageSpawnComponent.class).get())){
            damageSpawnComponent = Mappers.damageSpawnComponentMapper.get(e);
            AssetsMapper.damageSkin.getFont("subtitle").draw(renderer.getBatch(),
                    String.valueOf(damageSpawnComponent.damage),
                    damageSpawnComponent.x,
                    damageSpawnComponent.y);

            damageSpawnComponent.current += deltaTime;
            damageSpawnComponent.y += damageSpawnComponent.speed * deltaTime;
            if(damageSpawnComponent.current >= damageSpawnComponent.lifeSpan){
                getEngine().removeEntity(e);
            }
        }
        renderer.getBatch().end();
    }
}
