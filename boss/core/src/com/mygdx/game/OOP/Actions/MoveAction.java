package com.mygdx.game.OOP.Actions;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.pfa.PathSmoother;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.mygdx.game.Components.WorldObjects.Tasks.MovementTask;
import com.mygdx.game.Components.WorldObjects.Tasks.TasksComponent;
import com.mygdx.game.Components.WorldObjects.WorldPositionComponent;
import com.mygdx.game.Components.WorldObjects.TargetComponent;
import com.mygdx.game.Mappers.Mappers;
import com.mygdx.game.Mappers.ResourceMapper;
import com.mygdx.game.PathfindingUtils.*;


public class MoveAction extends Action<Entity>{
    protected int x;
    protected int y;
    protected MapGraph mapGraph;
    IndexedAStarPathFinder<TiledNode> pathFinder;
    TiledSmoothableGraphPath<TiledNode> path;
    PathSmoother pathSmoother;
    ManhattanDistanceHeuristic heuristic;


    /**
     * x and y should be isoScreen coordinates;
     * **/
    public MoveAction(float x, float y, MapGraph mapGraph) {
        this.x = (int) (x / ResourceMapper.tileWidth);
        this.y = (int) (y / ResourceMapper.tileHeight);
        this.mapGraph = mapGraph;
        pathFinder = new IndexedAStarPathFinder<TiledNode>(mapGraph);
        pathSmoother = new PathSmoother(new TiledRayCastCollisionDetection(mapGraph));
        heuristic = new ManhattanDistanceHeuristic();
    }

    @Override
    public void act(Entity e) {
        path = new TiledSmoothableGraphPath<TiledNode>();
        WorldPositionComponent pos = Mappers.worldPosition.get(e);
        TargetComponent target = Mappers.target.get(e);
        if(pos == null) return;
        if(target == null){
            target = new TargetComponent(path);
        }
        pathFinder.searchNodePath(
                mapGraph.getNodeFromScreen(pos.position.x, pos.position.y),
                mapGraph.getNode(x, y),
                heuristic,path);
        pathSmoother.smoothPath(path);
        target.changePath(path);
        e.add(target);
    }
}
