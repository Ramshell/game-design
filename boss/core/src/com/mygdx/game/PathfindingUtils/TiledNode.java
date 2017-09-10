package com.mygdx.game.PathfindingUtils;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultConnection;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;
import com.mygdx.game.Mappers.ResourceMapper;

public class TiledNode {

    public static int TILE_FLOOR = 1;
    public static int TILE_WALL = 2;
    public int type;

    private int x;
    private int y;
    private int height;
    private Array<Connection<TiledNode>> connections;
    private IntMap<Integer> alreadyConnected;

    public TiledNode(int x, int y, int height){
        this(x, y, height, TILE_FLOOR);
    }

    public TiledNode(int x, int y, int height, int type){
        this.x = x;
        this.y = y;
        this.height = height;
        connections = new Array<Connection<TiledNode>>(4);
        alreadyConnected = new IntMap<Integer>();
        this.type = type;
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
            for(int i = 0 ; i < connections.size; ++i) if(connections.get(i).getToNode().equals(toNode)){
                connections.removeIndex(i);
                break;
            }
            alreadyConnected.remove(toNode.getIndex());
        }
    }
}
