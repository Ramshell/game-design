package com.platformer.ar.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class BackgroundComponent implements Component{
    public Array<Background> background = new Array<Background>(3);
    public Entity target;
    public float nearBy = 100;

    public BackgroundComponent(float scale, TextureRegion somebackground, Entity target, Engine engine, float nearBy, float deep, float offsetY){
        this.nearBy = nearBy;
        Vector3 targetPosition = target.getComponent(TransformComponent.class).position;
        this.target = target;
        background.add(new Background(somebackground, targetPosition.x - somebackground.getRegionWidth() * scale,targetPosition.y + offsetY, scale, deep));
        background.add(new Background(somebackground,  targetPosition.x, targetPosition.y + offsetY, scale, deep));
        background.add(new Background(somebackground, targetPosition.x + somebackground.getRegionWidth() * scale,targetPosition.y + offsetY, scale, deep));
        engine.addEntity(background.get(0));
        engine.addEntity(background.get(1));
        engine.addEntity(background.get(2));
    }
}
