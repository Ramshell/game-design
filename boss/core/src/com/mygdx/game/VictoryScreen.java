package com.mygdx.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Screen;

public class VictoryScreen implements Screen{
    public Engine engine;

    public VictoryScreen(Engine engine){
        this.engine = engine;
    }

    @Override
    public void show() {
        for(EntitySystem system : engine.getSystems()){
            system.setProcessing(false);
        }
    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
