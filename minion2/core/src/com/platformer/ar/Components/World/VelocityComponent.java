package com.platformer.ar.Components.World;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class VelocityComponent implements Component{
    public Vector2 pos = new Vector2(0.0f, 0.0f);
    public Vector2 accel = new Vector2(0.0f,0.0f);
    public float maxSpeed = 30000f;
    public float accelF = 20f;


    public VelocityComponent(){}
    public VelocityComponent(float posx, float posy){
        maxSpeed = 10f;
        pos = new Vector2(posx, posy);
        accel = new Vector2(0.0f,0.0f);
    }

    public void increment(float deltaTime){
        pos.add(accel.cpy().scl(deltaTime));
        clamp();
    }

    public void clamp(){
        pos.x = Math.min(pos.x, maxSpeed);
        pos.y = Math.min(pos.y, maxSpeed);
        pos.x = Math.max(pos.x, -maxSpeed);
        pos.y = Math.max(pos.y, -maxSpeed);
    }
}
