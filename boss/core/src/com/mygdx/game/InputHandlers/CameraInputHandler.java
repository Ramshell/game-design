package com.mygdx.game.InputHandlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Components.CameraComponent;
import com.mygdx.game.Components.VelocityComponent;
import com.mygdx.game.Entities.RTSCamera;
import com.mygdx.game.Mappers.Mappers;

public class CameraInputHandler extends InputAdapter {

    private RTSCamera camera;
    private final float velocity = 200;
    private final float accel = 1000;

    public CameraInputHandler(RTSCamera camera){
        this.camera = camera;
    }

    @Override
    public boolean keyDown(int keycode) {
        VelocityComponent velocityComponent = Mappers.velocity.get(camera);
        switch (keycode) {
            case Input.Keys.LEFT:
                addVelocity(velocityComponent, new Vector2(-velocity, 0), new Vector2(-accel,0));
                break;
            case Input.Keys.RIGHT:
                addVelocity(velocityComponent, new Vector2(velocity, 0), new Vector2(accel,0));
                break;
            case Input.Keys.UP:
                addVelocity(velocityComponent, new Vector2(0, velocity), new Vector2(0, accel));
                break;
            case Input.Keys.DOWN:
                addVelocity(velocityComponent, new Vector2(0, -velocity), new Vector2(0, -accel));
                break;
        }
        return true;
    }

    private void addVelocity(VelocityComponent v, Vector2 vector, Vector2 acceleration) {
        v.accel = acceleration;
        v.pos.add(vector);
    }

    @Override
    public boolean keyUp(int keycode) {
        VelocityComponent velocityComponent = Mappers.velocity.get(camera);
        switch (keycode) {
            case Input.Keys.LEFT:
                velocityComponent.pos.x = 0;
                velocityComponent.accel.x = 0;
                break;
            case Input.Keys.RIGHT:
                velocityComponent.pos.x = 0;
                velocityComponent.accel.x = 0;
                break;
            case Input.Keys.UP:
                velocityComponent.pos.y = 0;
                velocityComponent.accel.y = 0;
                break;
            case Input.Keys.DOWN:
                velocityComponent.pos.y = 0;
                velocityComponent.accel.y = 0;
                break;
        }
        return true;
    }

    @Override
    public boolean mouseMoved (int screenX, int screenY) {
        VelocityComponent velocityComponent = Mappers.velocity.get(camera);
        OrthographicCamera OCamera = Mappers.camera.get(camera).getCamera();
        if(mouseInsideCamera(OCamera, screenX, screenY)) {
            velocityComponent.accel.scl(0);
            velocityComponent.pos.scl(0);
            return true;
        }
        if (screenX < 20 && velocityComponent.pos.x == 0)
            addVelocity(velocityComponent, new Vector2(-velocity, 0), new Vector2(-accel,0));
        if (screenX > OCamera.viewportWidth - 20 && velocityComponent.pos.x == 0)
            addVelocity(velocityComponent, new Vector2(velocity, 0), new Vector2(accel,0));
        if (screenY < 20 && velocityComponent.pos.y == 0)
            addVelocity(velocityComponent, new Vector2(0, velocity), new Vector2(0, accel));
        if (screenY > OCamera.viewportHeight - 20 && velocityComponent.pos.y == 0)
            addVelocity(velocityComponent, new Vector2(0, -velocity), new Vector2(0, -accel));
        return true;
    }

    @Override
    public boolean scrolled (int amount) {
        OrthographicCamera OCamera = Mappers.camera.get(camera).getCamera();
        OCamera.zoom = Math.max(1, Math.min(OCamera.zoom + 0.01f * amount, 2));
        return false;
    }

    private boolean mouseInsideCamera(OrthographicCamera OCamera, int screenX, int screenY){
        return screenX >= 20 && screenX <= OCamera.viewportWidth - 20 &&
                screenY >= 10 && screenY <= OCamera.viewportHeight - 20;
    }
}
