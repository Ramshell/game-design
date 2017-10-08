package com.mygdx.game.Systems.Fog;

import box2dLight.RayHandler;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.mygdx.game.Components.MatchComponent;
import com.mygdx.game.Components.PlayerComponent;
import com.mygdx.game.Components.WorldObjects.LightComponent;
import com.mygdx.game.Components.WorldObjects.WorldObjectComponent;
import com.mygdx.game.Components.WorldObjects.WorldPositionComponent;
import com.mygdx.game.Mappers.Mappers;
import com.mygdx.game.Play;

public class FogSystem extends EntitySystem{
    MatchComponent matchComponent;
    PlayerComponent player;

    public FogSystem(PlayerComponent player){
        this.player = player;
    }
    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        matchComponent = Mappers.matchComponentMapper.get(getEngine().getEntitiesFor(Family.all(MatchComponent.class).get()).first());
    }

    @Override
    public void update(float deltaTime) {
        for(Entity e: getEngine().getEntitiesFor(Family.all(
                LightComponent.class,
                WorldObjectComponent.class,
                PlayerComponent.class).get())){
            LightComponent lightComponent = Mappers.lightComponentMapper.get(e);
            if(!Mappers.player.get(e).equals(player)){
                lightComponent.light.setDistance(0);
                continue;
            }
            WorldObjectComponent wo = Mappers.world.get(e);
            lightComponent.light.setPosition(
                    wo.bounds.getRectangle().x + wo.bounds.getRectangle().getWidth() / 2,
                    wo.bounds.getRectangle().y + wo.bounds.getRectangle().getHeight() / 2);
            lightComponent.light.setDistance(lightComponent.visibility);
        }
        matchComponent.box2DRenderer.render(matchComponent.world, matchComponent.camera.combined);
        matchComponent.rayHandler.setCombinedMatrix(matchComponent.camera);
        matchComponent.rayHandler.updateAndRender();
    }
}
