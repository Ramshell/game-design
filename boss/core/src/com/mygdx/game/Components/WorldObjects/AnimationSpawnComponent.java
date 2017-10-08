package com.mygdx.game.Components.WorldObjects;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationSpawnComponent implements Component {
    public Animation<TextureRegion> anim;
    public float x, y, time = 0, offsetX, offsetY;

    public AnimationSpawnComponent(float x, float y, Animation<TextureRegion> anim, float offsetX, float offsetY){
        this.anim = anim;
        this.x = x;
        this.y = y;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }
}