package com.mygdx.game.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.IntMap;

public class AnimationComponent implements Component {
    public IntMap<Animation<TextureRegion>> animations = new IntMap<Animation<TextureRegion>>();
    public IntMap<Float> offsetsX = new IntMap<Float>();
    public IntMap<Float> offsetsY = new IntMap<Float>();
}
