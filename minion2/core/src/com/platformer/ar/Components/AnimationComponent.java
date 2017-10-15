package com.platformer.ar.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ArrayMap;

public class AnimationComponent implements Component{
    public ArrayMap<String, Animation<TextureRegion>> animations = new ArrayMap<String, Animation<TextureRegion>>();
}
