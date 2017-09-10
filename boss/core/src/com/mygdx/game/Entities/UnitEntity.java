package com.mygdx.game.Entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.mygdx.game.Components.*;
import com.mygdx.game.Components.WorldObjects.WorldPositionComponent;
import com.mygdx.game.Components.WorldObjects.WorldObjectComponent;
import com.mygdx.game.OOP.WorldMapObject;

public class UnitEntity extends Entity{
    public UnitEntity(PlayerComponent player, String name,
                      float posX, float posY, float width,
                      float height, float textureWidth, float textureHeight, int initialState,
                      AnimationComponent anim){
        WorldObjectComponent wo = new WorldObjectComponent(name);
        wo.bounds = new RectangleMapObject(posX, posY, width, height);
        wo.cost = 10;
        wo.hitPoints = 30;
        wo.maxHitPoints = 30;
        wo.sellValue = 10;
        VelocityComponent velocityComponent = new VelocityComponent();
        velocityComponent.maxSpeed = 40;
        StateComponent state = new StateComponent();
        state.set(initialState);
        TextureComponent t = new TextureComponent();
        t.region = anim.animations.get(initialState).getKeyFrame(0);
        add(wo).add(t).add(anim).add(state).add(new WorldPositionComponent(posX,posY))
        .add(velocityComponent).add(player);
    }
}
