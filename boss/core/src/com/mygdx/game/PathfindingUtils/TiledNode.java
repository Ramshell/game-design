package com.mygdx.game.PathfindingUtils;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultConnection;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;
import com.mygdx.game.Mappers.ResourceMapper;

public class TiledNode {

    private int x;
    private int y;
    private int height;
    private Array<Connection<TiledNode>> connections;
    private IntMap<Integer> alreadyConnected;

    public TiledNode(int x, int y, int height){
        this.x = x;
        this.y = y;
        this.height = height;
        connections = new Array<Connection<TiledNode>>(4);
        alreadyConnected = new IntMap<Integer>();
    }

    public int getIndex(){
        return x * height + y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Array<Connection<TiledNode>> getConnections(){
        return connections;
    }

    public void addConnection(TiledNode toNode) {
        if (!alreadyConnected.containsKey(toNode.getIndex())){
            connections.add(new DefaultConnection<TiledNode>(this, toNode));
            alreadyConnected.put(toNode.getIndex(), connections.size - 1);
        }
    }

    public void removeConnection(TiledNode toNode){
        if(alreadyConnected.containsKey(toNode.getIndex())){
            connections.removeIndex(alreadyConnected.get(toNode.getIndex()));
            alreadyConnected.remove(toNode.getIndex());
        }
    }
}
