package com.mygdx.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.Builders.EoLBuilder;
import com.mygdx.game.Builders.HarlandSoldierBuilder;
import com.mygdx.game.Builders.HarlandWorkerBuilder;
import com.mygdx.game.Builders.MainBuildingBuilder;
import com.mygdx.game.Components.*;
import com.mygdx.game.Components.HUD.HUDComponent;
import com.mygdx.game.Entities.*;
import com.mygdx.game.InputHandlers.ActionsInputHandler;
import com.mygdx.game.InputHandlers.UserInputHandler;
import com.mygdx.game.Mappers.AssetsMapper;
import com.mygdx.game.Mappers.Mappers;
import com.mygdx.game.Mappers.ResourceMapper;
import com.mygdx.game.PathfindingUtils.*;
import com.mygdx.game.Systems.*;
import com.mygdx.game.Systems.Combat.AttackProgressionSystem;
import com.mygdx.game.Systems.Combat.CombatSystem;
import com.mygdx.game.Systems.Combat.DamageSystem;

public class Play implements Screen {

    public TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    public OrthographicCamera camera;
    private Stage stage;
    private CameraComponent cameraComponent;
    public Engine engine;
    public MainBuildingBuilder mainBuildingBuilder;
    public HarlandWorkerBuilder workerBuilder;
    public HarlandSoldierBuilder soldierBuilder;
    public MapGraph mapGraph;
    private ParticleEffect effect;

    public Play(Engine engine) { this.engine = engine; }

    @Override
    public void show() {
        TmxMapLoader loader = new TmxMapLoader();
        map = loader.load("ortho_map/harland_desert.tmx");
        ResourceMapper.width = map.getProperties().get("width", Integer.class);
        ResourceMapper.height = map.getProperties().get("height", Integer.class);
        ResourceMapper.tileWidth = map.getProperties().get("tilewidth", Integer.class);
        ResourceMapper.tileHeight = map.getProperties().get("tileheight", Integer.class);
        MapGraphEntity mapGraphEntity = createGraph();
        Gdx.graphics.setCursor(Gdx.graphics.newCursor(AssetsMapper.nm, 0, 0));
        renderer = new OrthogonalTiledMapRenderer(map);
        mainBuildingBuilder = new MainBuildingBuilder(renderer, this);
        workerBuilder = new HarlandWorkerBuilder(this, mapGraph);
        soldierBuilder = new HarlandSoldierBuilder(this, mapGraph);
        RTSCamera rtsCamera = new RTSCamera();
        PlayerComponent playerComponent = new PlayerComponent();
        playerComponent.resources = 900;
        PlayerComponent playerComponentEnemy = new PlayerComponent();
        playerComponentEnemy.resources = 900;
        RendererEntity rendererEntity =
                new RendererEntity(
                        new MapComponent(map, Mappers.camera.get(rtsCamera).getCamera()),
                        new HUDComponent(playerComponent, this));
        HUDComponent p = Mappers.hud.get(rendererEntity);
        MapComponent mapComponent = Mappers.map.get(rendererEntity);
        PlayerEntity player = new PlayerEntity(p, playerComponent);
        PlayerEntity enemy = new PlayerEntity(p, playerComponentEnemy);

        camera = Mappers.camera.get(rtsCamera).getCamera();
//        for(int i = 0; i < 10; ++i){
//            for(int j = 0; j < 10; ++j){
//                engine.addEntity(soldierBuilder.getSoldier(playerComponent,i, j));
//            }
//        }
        engine.addEntity(workerBuilder.getWorker(playerComponent,0, 0));

        engine.addEntity(workerBuilder.getWorker(playerComponentEnemy,3, 0));

        engine.addEntity(soldierBuilder.getSoldier(playerComponent,5, 5));
        engine.addEntity(soldierBuilder.getSoldier(playerComponentEnemy,10, 10));

        engine.addEntity(new EoLBuilder(this, mapGraph).getEoL(3,4,20000));
        engine.addEntity(player);
        engine.addEntity(rendererEntity);
        engine.addEntity(rtsCamera);
        engine.addEntity(mapGraphEntity);
        engine.addSystem(new SetUpSystem());
        engine.addSystem(new MapRendererSystem());
        engine.addSystem(new UnitStateSystem());
        engine.addSystem(new AnimationSystem());
        engine.addSystem(new StateSystem());
        engine.addSystem(new CameraSystem());
        engine.addSystem(new MovementSystem());
        engine.addSystem(new UnitsVelocitySystem());
        engine.addSystem(new AttackProgressionSystem());
        engine.addSystem(new CombatSystem());
        engine.addSystem(new DamageSystem());
        engine.addSystem(new CollisionSystem());
        engine.addSystem(new UnitsMovementSystem());
        engine.addSystem(new MapGraphUpdaterSystem());
        engine.addSystem(new ToBuildMakingSystem());
        engine.addSystem(new BuildingMakingSystem());
        engine.addSystem(new ClickFeedbackSystem(mapComponent.camera));
        engine.addSystem(new ResourcesSystem());
        engine.addSystem(new GatheringStarterSystem());
        engine.addSystem(new HealthRenderSystem());
        engine.addSystem(new ResourcesPercentageRenderSystem());
        stage = p.stage;
        InputMultiplexer multiplexer = new InputMultiplexer();
        UserInputHandler userInputHandler = new UserInputHandler(rtsCamera, player,mapComponent, engine, mapGraph);
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(new ActionsInputHandler(playerComponent, engine, mapGraph));
        multiplexer.addProcessor(userInputHandler);
        Gdx.input.setInputProcessor(multiplexer);
        engine.addSystem(new SelectionRenderSystem(userInputHandler.selection, mapComponent.camera));
        engine.addSystem(new MatchTimeSystem());
        engine.addSystem(new RenderHudSystem());
    }

    private MapGraphEntity createGraph() {
        mapGraph = new MapGraph(1, map);
        return new MapGraphEntity(mapGraph);
    }

    @Override
    public void render(float delta) {
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
