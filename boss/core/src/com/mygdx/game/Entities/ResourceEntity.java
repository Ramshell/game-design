package com.mygdx.game.Entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.mygdx.game.Components.AnimationComponent;
import com.mygdx.game.Components.StateComponent;
import com.mygdx.game.Components.TextureComponent;
import com.mygdx.game.Components.WorldObjects.ResourceComponent;
import com.mygdx.game.Components.WorldObjects.WorldObjectComponent;
import com.mygdx.game.Components.WorldObjects.WorldPositionComponent;
import com.mygdx.game.Mappers.ResourceMapper;

public class ResourceEntity extends Entity{

    public ResourceEntity(String name,
                          float posX, float posY, float width,
                          float height, int initialState,
                          AnimationComponent anim, int currResources){
        ResourceComponent resourceComponent = new ResourceComponent();
        resourceComponent.currentResources = currResources;
        WorldObjectComponent wo = new WorldObjectComponent(name);
        wo.bounds = new RectangleMapObject(posX * ResourceMapper.tileWidth, posY * ResourceMapper.tileHeight,
                width, height);
        StateComponent state = new StateComponent();
        state.set(initialState);
        TextureComponent t = new TextureComponent();
        t.region = anim.animations.get(initialState).getKeyFrame(0);
        add(wo).add(t).add(anim).add(state).add(new WorldPositionComponent(posX * ResourceMapper.tileWidth,posY * ResourceMapper.tileHeight));
    }
}
