package com.mygdx.game.InputHandlers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.mygdx.game.Entities.RTSCamera;
import com.mygdx.game.Mappers.Mappers;

public class CameraInputHandler extends InputAdapter {

    private RTSCamera camera;

    public CameraInputHandler(RTSCamera camera){
        this.camera = camera;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
                Mappers.velocity.get(camera).x = -200;
                break;
            case Input.Keys.RIGHT:
                Mappers.velocity.get(camera).x = 200;
                break;
            case Input.Keys.UP:
                Mappers.velocity.get(camera).y = 200;
                break;
            case Input.Keys.DOWN:
                Mappers.velocity.get(camera).y = -200;
                break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
                Mappers.velocity.get(camera).x = 0;
                break;
            case Input.Keys.RIGHT:
                Mappers.velocity.get(camera).x = 0;
                break;
            case Input.Keys.UP:
                Mappers.velocity.get(camera).y = 0;
                break;
            case Input.Keys.DOWN:
                Mappers.velocity.get(camera).y = 0;
                break;
        }
        return true;
    }
}
