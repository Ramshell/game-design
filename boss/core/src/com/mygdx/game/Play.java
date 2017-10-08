package com.mygdx.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.Builders.EoLBuilder;
import com.mygdx.game.Builders.HarlandSoldierBuilder;
import com.mygdx.game.Builders.HarlandWorkerBuilder;
import com.mygdx.game.Builders.MainBuildingBuilder;
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
    private Game game;
    private EoLBuilder eolBuilder;

    public Play(Engine engine, Game game) { this.engine = engine; this.game=game;}

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
        eolBuilder = new EoLBuilder(this, mapGraph);
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
        GoalComponent goalComponent = new GoalComponent();
        goalComponent.condition = new TutorialVictoryCondition(engine, playerComponent, 1);
        engine.addEntity(new Entity().add(goalComponent));
        GoalComponent goalComponent2 = new GoalComponent();
        goalComponent2.condition = new CollectResourcesVictoryCondition(engine, playerComponent, 1200);
        engine.addEntity(new Entity().add(goalComponent2));
        GoalComponent goalComponent3 = new GoalComponent();
        goalComponent3.condition = new DestroyEnemiesVictoryCondition(engine, playerComponentEnemy);
        engine.addEntity(new Entity().add(goalComponent3));
        GoalComponent goalComponent4 = new GoalComponent();
        goalComponent4.condition = new DeadLineVictoryCondition(engine, playerComponentEnemy, 600);
        engine.addEntity(new Entity().add(goalComponent4));
        DefeatComponent defeatComponent = new DefeatComponent();
        defeatComponent.condition = new NormalDefeatCondition(engine, playerComponent);
        engine.addEntity(new Entity().add(defeatComponent));
        DefeatComponent defeatComponent2 = new DefeatComponent();
        defeatComponent2.condition = new DeadLineDefeatCondition(engine, playerComponent, 600);
        engine.addEntity(new Entity().add(defeatComponent2));

        engine.addEntity(workerBuilder.getWorker(playerComponent,0, 0));

        Entity worker = workerBuilder.getWorker(playerComponentEnemy,16, 0);
        engine.addEntity(worker);
        final TryingBuildingComponent tryingBuildingComponent = new TryingBuildingComponent();
        tryingBuildingComponent.building = mainBuildingBuilder.getWall(playerComponentEnemy,16,0);
        worker.add(tryingBuildingComponent);
        new CreateBuildingAction(16 * ResourceMapper.tileWidth, 0, engine, playerComponentEnemy, mapGraph).act(worker);

        for(int i = 16; i < 21; i += 2){
            Entity soldierEnemy = soldierBuilder.getSoldier(playerComponentEnemy, 16, i);
            engine.addEntity(soldierEnemy);
            new PatrolAction(2 * ResourceMapper.tileWidth, i * ResourceMapper.tileHeight).act(soldierEnemy);
        }


//        for(int i = 32; i < 39; ++i){
//            Entity soldierEnemy = soldierBuilder.getSoldier(playerComponentEnemy, 32, i);
//            engine.addEntity(soldierEnemy);
//            new PatrolAction(12 * ResourceMapper.tileWidth, i * ResourceMapper.tileHeight).act(soldierEnemy);
//        }

//        for(int i = 32; i < 39; ++i){
//            Entity soldierEnemy = soldierBuilder.getSoldier(playerComponentEnemy, i, 32);
//            engine.addEntity(soldierEnemy);
//            new PatrolAction(i * ResourceMapper.tileWidth,12  * ResourceMapper.tileHeight).act(soldierEnemy);
//        }

        engine.addEntity(eolBuilder.getEoL(3,4,2000));
        engine.addEntity(eolBuilder.getEoL(4,6,2000));
        engine.addEntity(eolBuilder.getEoL(5,8,2000));

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
        engine.addSystem(new PatrolSystem());
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
        engine.addSystem(new WOSoundSystem());
        engine.addSystem(new DamageSpawnSystem());
        engine.addSystem(new AnimationSpawnSystem());
        engine.addSystem(new GoalSystem(game));
        engine.addSystem(new DefeatSystem(game));
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
        dispose();
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
    }
}
