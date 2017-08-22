package com.mygdx.game.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class CameraComponent implements Component{
    private OrthographicCamera camera;

    public CameraComponent(OrthographicCamera camera){
        this.camera = camera;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
}
