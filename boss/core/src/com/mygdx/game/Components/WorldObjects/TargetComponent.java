package com.mygdx.game.Components.WorldObjects;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Mappers.ResourceMapper;
import com.mygdx.game.PathfindingUtils.TiledNode;
import com.mygdx.game.PathfindingUtils.TiledSmoothableGraphPath;

public class TargetComponent implements Component{
    public Vector2 target;
    private TiledSmoothableGraphPath<TiledNode> path;
    private int nextNode;

    public TargetComponent(TiledSmoothableGraphPath<TiledNode> path){
        init(path);
    }

    private void init(TiledSmoothableGraphPath<TiledNode> path) {
        nextNode = 1;
        this.path = path;
        nextTarget();
    }


    public void nextTarget(){
        if(path.getCount() > nextNode)
            target = new Vector2(
                    path.get(nextNode).getX() * ResourceMapper.tileWidth,
                    path.get(nextNode++).getY() * ResourceMapper.tileHeight);
        else
            target = null;
    }

    public void changePath(TiledSmoothableGraphPath<TiledNode> path){
        init(path);
    }
}
