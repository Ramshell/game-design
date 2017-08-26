package com.mygdx.game.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.game.Components.CameraComponent;
import com.mygdx.game.Components.PositionComponent;


public class SetUpSystem extends EntitySystem {

    public void addedToEngine(Engine engine) { }

    public void update(float deltaTime) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
}
