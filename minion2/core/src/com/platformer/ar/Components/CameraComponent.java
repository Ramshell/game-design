package com.platformer.ar.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class CameraComponent implements Component{
    public OrthographicCamera camera;
    public Entity target;
    public float boundLeftX = 220, boundRightX = 1000, boundTopY = 180, boundBottomY = 160;

    public CameraComponent(OrthographicCamera c, Entity t){
        camera = c;
        camera.position.x = boundLeftX;
        camera.position.y = boundBottomY;
        target = t;
    }
}
