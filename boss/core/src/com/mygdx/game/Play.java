package com.mygdx.game;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mygdx.game.Builders.*;
import com.mygdx.game.Components.*;
import com.mygdx.game.Components.HUD.HUDComponent;
import com.mygdx.game.Components.Matches.DefeatComponent;
import com.mygdx.game.Components.Matches.GoalComponent;
import com.mygdx.game.Components.WorldObjects.Buildings.TryingBuildingComponent;
import com.mygdx.game.Components.WorldObjects.PatrolComponent;
import com.mygdx.game.Entities.*;
import com.mygdx.game.InputHandlers.ActionsInputHandler;
import com.mygdx.game.InputHandlers.UserInputHandler;
import com.mygdx.game.Mappers.AssetsMapper;
import com.mygdx.game.Mappers.Mappers;
import com.mygdx.game.Mappers.ResourceMapper;
import com.mygdx.game.OOP.Actions.Action;
import com.mygdx.game.OOP.Actions.ActionBuilder;
import com.mygdx.game.OOP.Actions.CreateBuildingAction;
import com.mygdx.game.OOP.Actions.PatrolAction;
import com.mygdx.game.OOP.Conditions.*;
import com.mygdx.game.PathfindingUtils.*;
import com.mygdx.game.Systems.*;
import com.mygdx.game.Systems.Combat.*;
import com.mygdx.game.Systems.Fog.FogSystem;

public class Play implements Screen {

    public TiledMap map;
    public OrthogonalTiledMapRenderer renderer;
    public OrthographicCamera camera;
    private Stage stage;
    private CameraComponent cameraComponent;
    public Engine engine;
    public MainBuildingBuilder mainBuildingBuilder;
    public HarlandWorkerBuilder workerBuilder;
    public HarlandSoldierBuilder soldierBuilder;
    public HarlandAmablesFlattererBuilder amablesFlattererBuilder;
    public MapGraph mapGraph;
    private ParticleEffect effect;
    private Game game;
    private EoLBuilder eolBuilder;
    public World world;
    public Box2DDebugRenderer box2DRenderer;
    public RayHandler rayHandler;
    public HUDComponent hudComponent;
    public InputMultiplexer multiplexer;
    public Entity firstWorker;

    public Play(Engine engine, Game game) {
        this.engine = engine;
        this.game = game;
        world = new World(new Vector2(0, -9.81f), true);
        RayHandler.setGammaCorrection(true);
        RayHandler.useDiffuseLight(true);
        box2DRenderer = new Box2DDebugRenderer();
        rayHandler = new RayHandler(world);
        rayHandler.setAmbientLight(0f, 0f, 0f, 0.5f);
        rayHandler.setBlurNum(3);
        MatchComponent matchComponent = new MatchComponent();
        matchComponent.world = world;
        matchComponent.rayHandler = rayHandler;
        matchComponent.box2DRenderer = box2DRenderer;
        matchComponent.match = this;
        engine.addEntity(new Entity().add(matchComponent));

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
        amablesFlattererBuilder = new HarlandAmablesFlattererBuilder(this, mapGraph);
        eolBuilder = new EoLBuilder(this, mapGraph);
        RTSCamera rtsCamera = new RTSCamera();
        PlayerComponent playerComponent = new PlayerComponent();
        playerComponent.resources = 1200;
        PlayerComponent playerComponentEnemy = new PlayerComponent();
        playerComponentEnemy.resources = 1200;
        RendererEntity rendererEntity =
                new RendererEntity(
                        new MapComponent(map, Mappers.camera.get(rtsCamera).getCamera()),
                        new HUDComponent(playerComponent, this));
        hudComponent = Mappers.hud.get(rendererEntity);
        MapComponent mapComponent = Mappers.map.get(rendererEntity);
        PlayerEntity player = new PlayerEntity(hudComponent, playerComponent);
        PlayerEntity enemy = new PlayerEntity(hudComponent, playerComponentEnemy);

        camera = Mappers.camera.get(rtsCamera).getCamera();
        matchComponent.camera = camera;

        GoalComponent goalComponent = new GoalComponent();
        GoalComponent goalComponent2 = new GoalComponent();
        GoalComponent goalComponent3 = new GoalComponent();
        GoalComponent goalComponent4 = new GoalComponent();
        goalComponent.condition = new TutorialVictoryCondition(engine, playerComponent, 1);
        goalComponent.displayFromBeginning = true;
        goalComponent2.condition = new CollectResourcesVictoryCondition(engine, playerComponent, 1500);
        goalComponent.nextConditions.add(goalComponent2);
        engine.addEntity(new Entity().add(goalComponent));
        engine.addEntity(new Entity().add(goalComponent2));
        goalComponent3.condition = new DestroyEnemiesVictoryCondition(engine, playerComponentEnemy);
        engine.addEntity(new Entity().add(goalComponent3));
        goalComponent4.condition = new DeadLineVictoryCondition(engine, playerComponentEnemy, 600);
        goalComponent2.nextConditions.add(goalComponent3);
        goalComponent2.nextConditions.add(goalComponent4);
        engine.addEntity(new Entity().add(goalComponent4));
        DefeatComponent defeatComponent = new DefeatComponent();
        defeatComponent.condition = new NormalDefeatCondition(engine, playerComponent);
        engine.addEntity(new Entity().add(defeatComponent));
        DefeatComponent defeatComponent2 = new DefeatComponent();
        defeatComponent2.condition = new DeadLineDefeatCondition(engine, playerComponent, 600);
        engine.addEntity(new Entity().add(defeatComponent2));

        firstWorker = workerBuilder.getWorker(playerComponent,14, 4);
        engine.addEntity(firstWorker);

        Entity worker = workerBuilder.getWorker(playerComponentEnemy,30, 0);
        engine.addEntity(worker);
        final TryingBuildingComponent tryingBuildingComponent = new TryingBuildingComponent();
        tryingBuildingComponent.building = mainBuildingBuilder.getWall(playerComponentEnemy,30,0);
        worker.add(tryingBuildingComponent);
        new CreateBuildingAction(30 * ResourceMapper.tileWidth, 0, engine, playerComponentEnemy, mapGraph).act(worker);

        for(int i = 20; i < 26; i += 2){
            Entity soldierEnemy = soldierBuilder.getSoldier(playerComponentEnemy, 16, i);
            engine.addEntity(soldierEnemy);
            new PatrolAction(2 * ResourceMapper.tileWidth, i * ResourceMapper.tileHeight).act(soldierEnemy);
        }


        for(int i = 40; i < 40; ++i){
            Entity soldierEnemy = soldierBuilder.getSoldier(playerComponentEnemy, 32, i);
            engine.addEntity(soldierEnemy);
            new PatrolAction(12 * ResourceMapper.tileWidth, i * ResourceMapper.tileHeight).act(soldierEnemy);
        }

        for(int i = 45; i < 50; ++i){
            Entity soldierEnemy = soldierBuilder.getSoldier(playerComponentEnemy, i, 32);
            engine.addEntity(soldierEnemy);
            new PatrolAction(i * ResourceMapper.tileWidth,12  * ResourceMapper.tileHeight).act(soldierEnemy);
        }

        engine.addEntity(eolBuilder.getEoL(3,4,20000));
        engine.addEntity(eolBuilder.getEoL(4,6,20000));
        engine.addEntity(eolBuilder.getEoL(5,8,20000));

        engine.addEntity(player);
        engine.addEntity(rendererEntity);
        engine.addEntity(rtsCamera);
        engine.addEntity(mapGraphEntity);
        engine.addSystem(new SetUpSystem());
        engine.addSystem(new MapRendererSystem());
        engine.addSystem(new CameraSystem());
        engine.addSystem(new MovementSystem());
        engine.addSystem(new UnitsVelocitySystem());
        engine.addSystem(new PatrolSystem());
        engine.addSystem(new AttackProgressionSystem());
        engine.addSystem(new CombatSystem());
        engine.addSystem(new DamageSystem());
        engine.addSystem(new CollisionSystem());
        engine.addSystem(new UnitsMovementSystem());
        engine.addSystem(new UnitStateSystem());
        engine.addSystem(new AnimationSystem());
        engine.addSystem(new StateSystem());
        engine.addSystem(new MapGraphUpdaterSystem());
        engine.addSystem(new ToBuildMakingSystem());
        engine.addSystem(new BuildingMakingSystem());
        engine.addSystem(new TasksSystem());
        engine.addSystem(new ClickFeedbackSystem(mapComponent.camera));
        engine.addSystem(new ResourcesSystem());
        engine.addSystem(new GatheringStarterSystem());
        engine.addSystem(new HealthRenderSystem());
        engine.addSystem(new ResourcesPercentageRenderSystem());
        engine.addSystem(new WOSoundSystem());
        engine.addSystem(new DamageSpawnSystem());
        engine.addSystem(new AnimationSpawnSystem());
        engine.addSystem(new GoalSystem(game));
        engine.addSystem(new DefeatSystem(game));
        engine.addSystem(new FogSystem(playerComponent));
        stage = hudComponent.stage;
        multiplexer = new InputMultiplexer();
        UserInputHandler userInputHandler = new UserInputHandler(rtsCamera, player,mapComponent, engine, mapGraph);

        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(new GestureDetector(userInputHandler));
        multiplexer.addProcessor(new ActionsInputHandler(playerComponent, engine, mapGraph));
        multiplexer.addProcessor(userInputHandler);

        engine.addSystem(new SelectionRenderSystem(userInputHandler.selection, mapComponent.camera));
        engine.addSystem(new MatchTimeSystem());
        engine.addSystem(new RenderHudSystem());
    }


    @Override
    public void show() {
        for(EntitySystem es : engine.getSystems()){
            es.setProcessing(true);
        }
        Gdx.input.setInputProcessor(multiplexer);
        AssetsMapper.playMusic();
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
        for(EntitySystem es : engine.getSystems()){
            es.setProcessing(false);
        }
        Gdx.input.setInputProcessor(null);
        AssetsMapper.pauseMusic();
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        rayHandler.dispose();
        Gdx.input.setInputProcessor(null);
    }
}
