package com.mygdx.game.Systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Mappers.ResourceMapper;

public class ClickFeedbackSystem extends EntitySystem{

    ShapeRenderer shapeRenderer;
    Camera camera;

    public ClickFeedbackSystem(Camera camera) {
        shapeRenderer = new ShapeRenderer();
        this.camera = camera;
    }

    public void update(float deltaTime){
        if(Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            Vector3 v = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            shapeRenderer.setProjectionMatrix(camera.combined);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            Gdx.gl.glLineWidth(2);
            shapeRenderer.setColor(new Color(
                    1,
                    0,
                    1,
                    0.25f));
            shapeRenderer.rect(
                    ((int) v.x / ResourceMapper.tileWidth) * ResourceMapper.tileWidth,
                    ((int) v.y / ResourceMapper.tileHeight) * ResourceMapper.tileHeight,
                    ResourceMapper.tileWidth, ResourceMapper.tileHeight);
            shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
        }
    }
}
