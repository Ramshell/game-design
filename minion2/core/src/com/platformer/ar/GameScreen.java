package com.platformer.ar;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.platformer.ar.Components.*;
import com.platformer.ar.Components.World.PlayerComponent;
import com.platformer.ar.Components.World.PositionComponent;
import com.platformer.ar.Components.World.VelocityComponent;
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

        RenderingSystem renderingSystem = new RenderingSystem(batch);
        engine.addSystem(new AnimationSystem());
        engine.addSystem(renderingSystem);
        engine.addSystem(new PlayerSystem());
        engine.addSystem(new StateSystem());
        engine.addSystem(new MovementSystem());
        engine.addSystem(new GravitySystem());

        Entity e = buildMainCharacter();
        engine.addEntity(e);

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
        e.add(new PositionComponent(0,0));
        e.add(new VelocityComponent());

        AnimationComponent a = new AnimationComponent();
        a.animations.put("IDLE", Assets.robotIdleAnim);
        a.animations.put("RUNNING_RIGHT", Assets.robotRunRightAnim);
        a.animations.put("RUNNING_LEFT", Assets.robotRunLeftAnim);
        e.add(a);
        StateComponent state = new StateComponent();
        state.set("IDLE");
        e.add(state);
        TextureComponent tc = new TextureComponent();
        e.add(tc);

        TransformComponent tfc = new TransformComponent();
        tfc.position.set(10f, 10f, 1f);
        tfc.rotation = 0f;
        tfc.scale.set(0.3f, 0.3f);
        e.add(tfc);

        return e;
    }
}
