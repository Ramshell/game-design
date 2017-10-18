package com.platformer.ar;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.platformer.ar.Components.*;
import com.platformer.ar.Components.World.*;
import com.platformer.ar.Systems.*;

public class GameScreen extends ScreenAdapter{

    private boolean isInitialized = false;
    private float elapsedTime = 0f;
    private int secondsToSplash = 10;
    private World world;
    private PooledEngine engine;

    private SpriteBatch batch;
    private IScreenDispatcher dispatcher;
    private OrthogonalTiledMapRenderer renderer;
    private TiledMap map;


    public GameScreen(SpriteBatch batch, IScreenDispatcher dispatcher){
        super();
        this.batch = batch;
        this.dispatcher = dispatcher;
    }

    private void init(){
        Gdx.app.log("GameScreen", "Initializing");
        isInitialized = true;
        engine = new PooledEngine();
        TmxMapLoader loader = new TmxMapLoader();
        map = loader.load("map/platformer.tmx");

        OrthographicCamera camera = new OrthographicCamera(1024, 768);
        camera.zoom = 0.4f;
        OrthogonalTiledMapRenderer renderer = new OrthogonalTiledMapRenderer(map);

        Entity e = buildMainCharacter();
        engine.addEntity(e);
        engine.addEntity(new Entity().add(new CameraComponent(camera, e)));
        engine.addEntity(new Entity().add(new MapRendererComponent(renderer)));

        RenderingSystem renderingSystem = new RenderingSystem(batch, camera);
        engine.addSystem(new PlayerSystem());
        engine.addSystem(new AnimationSystem());
        engine.addSystem(new StateSystem());
        engine.addSystem(new GravitySystem());
        engine.addSystem(new CollisionSystem());
        engine.addSystem(new MovementSystem());
        engine.addSystem(new MapRendererSystem());
        engine.addSystem(renderingSystem);



        isInitialized = true;
    }


    private void update(float delta){
        engine.update(delta);
    }

    @Override
    public void render(float delta) {
        if(isInitialized) {
            update(delta);
        }else{
            init();
        }
    }

    private Entity buildMainCharacter() {
        Entity e = engine.createEntity();
        e.add(new PlayerComponent());
        e.add(new VelocityComponent());
        e.add(new GravityComponent());
        MapObject start = map.getLayers().get("spawn").getObjects().get("start");

        AnimationComponent a = new AnimationComponent();
        a.animations.put("IDLE", Assets.robotIdleAnim);
        a.animations.put("RUNNING_RIGHT", Assets.robotRunRightAnim);
        a.animations.put("RUNNING_LEFT", Assets.robotRunLeftAnim);
        e.add(a);
        StateComponent state = new StateComponent();
        state.set("IDLE");
        e.add(state);
        TextureComponent tc = new TextureComponent();
        tc.region = a.animations.get(state.get()).getKeyFrame(0f);
        e.add(new SolidComponent(new Rectangle(
                (Float) start.getProperties().get("x"),
                (Float) start.getProperties().get("y"),
                tc.region.getRegionWidth() * 0.1f,
                tc.region.getRegionHeight() * 0.2f)));
        e.add(tc);

        TransformComponent tfc = new TransformComponent();
        tfc.position.set((Float) start.getProperties().get("x"), (Float) start.getProperties().get("y"), 1f);
        tfc.rotation = 0f;
        tfc.scale.set(0.2f, 0.2f);
        e.add(tfc);

        return e;
    }
}
