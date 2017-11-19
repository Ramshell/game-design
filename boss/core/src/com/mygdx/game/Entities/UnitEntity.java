package com.mygdx.game.Entities;

import box2dLight.PointLight;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Components.*;
import com.mygdx.game.Components.WorldObjects.*;
import com.mygdx.game.Mappers.ResourceMapper;
import com.mygdx.game.OOP.WorldMapObject;
import com.mygdx.game.Play;

public class UnitEntity extends Entity{
    public UnitEntity(PlayerComponent player, WorldObjectComponent wo,
                      float posX, float posY, int initialState,
                      AnimationComponent anim, HealthComponent healthComponent,
                      int id, Play play, float visibility){
        VelocityComponent velocityComponent = new VelocityComponent();
        velocityComponent.maxSpeed = 60;
        StateComponent state = new StateComponent();
        state.set(initialState);
        TextureComponent t = new TextureComponent();
        LightComponent lightComponent =  new LightComponent();
        lightComponent.light = new PointLight(play.rayHandler, 64, new Color(1,1,1,1), visibility,
                posX * ResourceMapper.tileWidth + ResourceMapper.tileWidth / 2,
                posY * ResourceMapper.tileHeight + ResourceMapper.tileHeight / 2);
        lightComponent.visibility = visibility;

        t.region = anim.animations.get(initialState).getKeyFrame(0);
        add(wo).add(t).add(anim).add(state).add(new WorldPositionComponent(posX * ResourceMapper.tileWidth,posY * ResourceMapper.tileHeight))
        .add(velocityComponent).add(player).add(healthComponent).add(new DynamicWOComponent(wo.bounds, posX * ResourceMapper.tileWidth, posY * ResourceMapper.tileHeight))
        .add(lightComponent);
    }
}
