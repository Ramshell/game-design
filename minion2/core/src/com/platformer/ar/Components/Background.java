package com.platformer.ar.Components;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.platformer.ar.Components.World.VelocityComponent;

public class Background extends Entity {
    public TextureRegion background;

    public Background(TextureRegion region, float x, float y, float scale, float deep){
        background = region;
        TextureComponent textureComponent = new TextureComponent();
        textureComponent.region = background;
        add(textureComponent);
        TransformComponent transformComponent = new TransformComponent();
        transformComponent.position.set(x, y, deep);
        transformComponent.scale.set(scale,scale);
        add(transformComponent);
        VelocityComponent velocityComponent = new VelocityComponent();
        add(velocityComponent);
    }
}
