package com.mygdx.game.Entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.game.Components.CameraComponent;
import com.mygdx.game.Components.PositionComponent;
import com.mygdx.game.Components.VelocityComponent;

public class RTSCamera extends Entity{
    public RTSCamera(){
        this(0,0);
    }

    public RTSCamera(float x, float y){
        PositionComponent pos = new PositionComponent();
        pos.pos.x = x; pos.pos.y = y;
        VelocityComponent vel = new VelocityComponent();
        OrthographicCamera camera = new OrthographicCamera(pos.pos.x, pos.pos.y);
        add(vel).add(pos).add(new CameraComponent(camera));
    }
}
