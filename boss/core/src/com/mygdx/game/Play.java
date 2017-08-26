package com.mygdx.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Components.CameraComponent;
import com.mygdx.game.Components.HUD.HUDComponent;
import com.mygdx.game.Components.MapComponent;
import com.mygdx.game.Entities.RTSCamera;
import com.mygdx.game.Entities.RendererEntity;
import com.mygdx.game.InputHandlers.CameraInputHandler;
import com.mygdx.game.Mappers.AssetsMapper;
import com.mygdx.game.Mappers.Mappers;
import com.mygdx.game.Systems.CameraSystem;
import com.mygdx.game.Systems.MapRendererSystem;
import com.mygdx.game.Systems.MovementSystem;
import com.mygdx.game.Systems.SetUpSystem;

public class Play implements Screen {

    private TiledMap map;
    private IsometricTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private Stage stage;
    private CameraComponent cameraComponent;
    private Engine engine;

    public Play(Engine engine){
        this.engine = engine;
    }

    @Override
    public void show() {
        TmxMapLoader loader = new TmxMapLoader();
        map = loader.load("example_map.tmx");
        Gdx.graphics.setCursor(Gdx.graphics.newCursor(AssetsMapper.nm, 0, 0));
        renderer = new IsometricTiledMapRenderer(map);
        RTSCamera rtsCamera = new RTSCamera();
        RendererEntity rendererEntity =
                new RendererEntity(
                        new MapComponent(map, Mappers.camera.get(rtsCamera).getCamera()),
                        new HUDComponent());
        engine.addEntity(rendererEntity);
        engine.addEntity(rtsCamera);
        engine.addSystem(new SetUpSystem());
        engine.addSystem(new MapRendererSystem());
        engine.addSystem(new CameraSystem());
        engine.addSystem(new MovementSystem());
        camera = Mappers.camera.get(rtsCamera).getCamera();
        HUDComponent p = Mappers.hud.get(rendererEntity);
        stage = p.stage;
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(p.stage);
        multiplexer.addProcessor(new CameraInputHandler(rtsCamera));
        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    public void render(float delta) {
        engine.update(delta);

//        float[] a = new float[8];
//        a[0] = 0; a[1] = 0; a[2] = 10; a[3] = 10; a[4] = 0; a[5] = 10; a[6] = 10; a[7] = 0;
//        float[] b = new float[8];
//        b[0] = 11; b[1] = 11; b[2] = 12; b[3] = 12; b[4] = 11; b[5] = 12; b[6] = 12; b[7] = 11;
//        Polygon p = new Polygon(a);Polygon p1=  new Polygon(b);
//        System.out.println(Intersector.overlapConvexPolygons(p,p1));
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
