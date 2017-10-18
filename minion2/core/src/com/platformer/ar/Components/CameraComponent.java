package com.platformer.ar.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class CameraComponent implements Component{
    public OrthographicCamera camera;
    public Entity target;

    public CameraComponent(OrthographicCamera c, Entity t){
        camera = c;
        target = t;
    }
}
