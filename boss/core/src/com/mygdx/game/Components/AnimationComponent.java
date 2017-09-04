package com.mygdx.game.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationComponent implements Component {
    public Animation<TextureRegion> animation;
    public float stateTime = 0.0f;

    public AnimationComponent(Animation<TextureRegion> anim){
        animation = anim;
    }
}