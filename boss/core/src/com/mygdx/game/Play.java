package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;

public class Play implements Screen {

    private TiledMap map;
    private IsometricTiledMapRenderer renderer;
    private OrthographicCamera camera;

    @Override
    public void show() {
        Gdx.input.setCursorCatched(true);
        TmxMapLoader loader = new TmxMapLoader();
        map = loader.load("example_map.tmx");
        renderer = new IsometricTiledMapRenderer(map);
        camera = new OrthographicCamera();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.setView(camera);
        renderer.render();
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) camera.position.y += 10;
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) camera.position.y -= 10;
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) camera.position.x -= 10;
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) camera.position.x += 10;
        camera.update();
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportHeight = height;
        camera.viewportWidth = width;
        camera.update();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
    }
}
