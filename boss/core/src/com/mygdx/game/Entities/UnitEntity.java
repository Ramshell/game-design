package com.mygdx.game.Entities;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.mygdx.game.Components.AnimationComponent;
import com.mygdx.game.Components.PlayerComponent;
import com.mygdx.game.Components.StateComponent;
import com.mygdx.game.Components.TextureComponent;
import com.mygdx.game.Components.WorldObjects.WorldObjectComponent;

public class UnitEntity extends Entity{
    public UnitEntity(PlayerComponent player, String name,
                      float posX, float posY, float width,
                      float height, float textureWidth, float textureHeight, int initialState,
                      AnimationComponent anim){
        WorldObjectComponent wo = new WorldObjectComponent(name);
        wo.bounds = new RectangleMapObject(posX - width / 2, posY + height / 2, width, height);
        wo.cost = 10;
        wo.hitPoints = 30;
        wo.maxHitPoints = 30;
        wo.sellValue = 10;
        StateComponent state = new StateComponent();
        state.set(initialState);
        TextureComponent t = new TextureComponent();
        t.region = anim.animations.get(initialState).getKeyFrame(0);
        add(wo).add(t).add(anim).add(state);
    }
}
