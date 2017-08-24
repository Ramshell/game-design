package com.mygdx.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.mygdx.game.Components.CameraComponent;
import com.mygdx.game.Entities.RTSCamera;
import com.mygdx.game.InputHandlers.CameraInputHandler;
import com.mygdx.game.Mappers.Mappers;
import com.mygdx.game.Systems.CameraSystem;
import com.mygdx.game.Systems.MovementSystem;

public class Play implements Screen {

    private TiledMap map;
    private IsometricTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private CameraComponent cameraComponent;
    private RTSCamera rtsCamera;
    private Engine engine;

    public Play(Engine engine){
        this.engine = engine;
    }

    @Override
    public void show() {
//        Cursor customCursor = Gdx.graphics.newCursor(new Pixmap(Gdx.files.internal("cursor/real.png")), 0, 0);
//        Gdx.graphics.setCursor(customCursor);
        TmxMapLoader loader = new TmxMapLoader();
        map = loader.load("example_map.tmx");
        renderer = new IsometricTiledMapRenderer(map);
        rtsCamera = new RTSCamera();
        engine.addEntity(rtsCamera);
        engine.addSystem(new CameraSystem());
        engine.addSystem(new MovementSystem());
        camera = Mappers.camera.get(rtsCamera).getCamera();
        Gdx.input.setInputProcessor(new CameraInputHandler(rtsCamera));

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.setView(camera);
        renderer.render();
        engine.update(delta);
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
