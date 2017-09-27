package com.mygdx.game.Systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.Components.AnimationComponent;
import com.mygdx.game.Components.StateComponent;
import com.mygdx.game.Components.TextureComponent;


public class AnimationSystem extends IteratingSystem {
    private ComponentMapper<TextureComponent> tm;
    private ComponentMapper<AnimationComponent> am;
    private ComponentMapper<StateComponent> sm;

    public AnimationSystem() {
        super(Family.all(TextureComponent.class,
                AnimationComponent.class,
                StateComponent.class).get());

        tm = ComponentMapper.getFor(TextureComponent.class);
        am = ComponentMapper.getFor(AnimationComponent.class);
        sm = ComponentMapper.getFor(StateComponent.class);
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        TextureComponent tex = tm.get(entity);
        AnimationComponent anim = am.get(entity);
        StateComponent state = sm.get(entity);

        Animation<TextureRegion> animation = anim.animations.get(state.get());

        if (animation != null) {
            tex.region = animation.getKeyFrame(state.time, true);
            Float offsetX = anim.offsetsX.get(state.get());
            if(offsetX != null) tex.offsetX = offsetX;
            else tex.offsetX = 0;
            Float offsetY = anim.offsetsY.get(state.get());
            if(offsetY != null) tex.offsetY = offsetY;
            else tex.offsetY = 0;
        }

        state.time += deltaTime;
    }
}
