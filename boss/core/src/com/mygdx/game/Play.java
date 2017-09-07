package com.mygdx.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.*;
import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.Builders.ElementalBuilder;
import com.mygdx.game.Builders.WallBuilder;
import com.mygdx.game.Components.*;
import com.mygdx.game.Components.HUD.HUDComponent;
import com.mygdx.game.Entities.BuildingEntity;
import com.mygdx.game.Entities.PlayerEntity;
import com.mygdx.game.Entities.RTSCamera;
import com.mygdx.game.Entities.RendererEntity;
import com.mygdx.game.InputHandlers.TryingBuildingInputHandler;
import com.mygdx.game.InputHandlers.UserInputHandler;
import com.mygdx.game.Mappers.AssetsMapper;
import com.mygdx.game.Mappers.Mappers;
import com.mygdx.game.PathfindingUtils.ManhattanDistanceHeuristic;
import com.mygdx.game.PathfindingUtils.MapGraph;
import com.mygdx.game.PathfindingUtils.TiledNode;
import com.mygdx.game.Systems.*;

import java.util.Iterator;

public class Play implements Screen {

    private TiledMap map;
    private IsometricTiledMapRenderer renderer;
    public OrthographicCamera camera;
    private Stage stage;
    private CameraComponent cameraComponent;
    private Engine engine;
    public WallBuilder wallBuilder;

    public Play(Engine engine) { this.engine = engine; }

    @Override
    public void show() {
        TmxMapLoader loader = new TmxMapLoader();
        map = loader.load("example_map.tmx");
        createGraph();

        Gdx.graphics.setCursor(Gdx.graphics.newCursor(AssetsMapper.nm, 0, 0));
        renderer = new IsometricTiledMapRenderer(map);
        wallBuilder = new WallBuilder(renderer);
        RTSCamera rtsCamera = new RTSCamera();
        PlayerComponent playerComponent = new PlayerComponent();
        RendererEntity rendererEntity =
                new RendererEntity(
                        new MapComponent(map, Mappers.camera.get(rtsCamera).getCamera()),
                        new HUDComponent(playerComponent, this));
        HUDComponent p = Mappers.hud.get(rendererEntity);
        MapComponent mapComponent = Mappers.map.get(rendererEntity);
        PlayerEntity player = new PlayerEntity(p, playerComponent);
        camera = Mappers.camera.get(rtsCamera).getCamera();
        BuildingEntity buildingEntity = wallBuilder.getWall(playerComponent,10, 10);
        engine.addEntity(new ElementalBuilder().elemental(playerComponent,32, 32));
        engine.addEntity(buildingEntity);
        engine.addEntity(player);
        engine.addEntity(rendererEntity);
        engine.addEntity(rtsCamera);
        engine.addSystem(new SetUpSystem());
        engine.addSystem(new MapRendererSystem());
        engine.addSystem(new AnimationSystem());
        engine.addSystem(new StateSystem());
        engine.addSystem(new CameraSystem());
        engine.addSystem(new MovementSystem());
        engine.addSystem(new RenderHudSystem());
        engine.addSystem(new BuildingMakingSystem(playerComponent));
        stage = p.stage;
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(new TryingBuildingInputHandler(playerComponent, engine));
        multiplexer.addProcessor(p.stage);
        multiplexer.addProcessor(new UserInputHandler(rtsCamera, player,mapComponent, engine));
        Gdx.input.setInputProcessor(multiplexer);
    }

    private void createGraph() {
        Integer mapWidth = map.getProperties().get("width", Integer.class);
        Integer mapHeight = map.getProperties().get("height", Integer.class);
//        MapLayer oLayer = map.getLayers().get("collision_layer");
        MapGraph mapGraph = new MapGraph(mapWidth, mapHeight);
        for(int i = 0; i < mapWidth; i++){
            for(int j = 0; j < mapHeight; j++){
                TiledNode from = mapGraph.getNode(i, j);
                if(nodeWithinBounds(mapWidth, mapHeight, i + 1, j))
                    from.addConnection(mapGraph.getNode(i + 1, j)); //right
                if(nodeWithinBounds(mapWidth, mapHeight, i - 1, j))
                    from.addConnection(mapGraph.getNode(i - 1, j)); //left
                if(nodeWithinBounds(mapWidth, mapHeight, i, j + 1))
                    from.addConnection(mapGraph.getNode(i, j + 1)); //right
                if(nodeWithinBounds(mapWidth, mapHeight, i, j - 1))
                    from.addConnection(mapGraph.getNode(i, j - 1)); //down
            }
        }
        IndexedAStarPathFinder<TiledNode> pathFinder = new IndexedAStarPathFinder<TiledNode>(mapGraph);
        DefaultGraphPath<TiledNode> path = new DefaultGraphPath<TiledNode>();
        pathFinder.searchNodePath(mapGraph.getNode(0), mapGraph.getNode(mapWidth*mapHeight - 1),new ManhattanDistanceHeuristic(),path);
        System.out.println(path.getCount());
    }

    private Boolean nodeWithinBounds(Integer mapWidth, Integer mapHeight, Integer x, Integer y) {
        return !(y < 0 || y >= mapHeight || x >= mapWidth || x < 0);
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
