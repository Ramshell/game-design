package com.mygdx.game.PathfindingUtils;


import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.Array;

import java.util.BitSet;

public class MapGraph implements IndexedGraph<TiledNode>{
    private Array<TiledNode> graph;
    private Integer width;
    private Integer height;
    private Integer mapWidth;
    private Integer mapHeight;
    private Integer tileWidth;
    private Integer tileHeight;
    private Integer multiplier;
    private TiledMap map;
    Array<Boolean> colide;
    Array<Boolean> colideBuilding;

    public MapGraph(int multiplier, TiledMap map){
        this.mapWidth = map.getProperties().get("width", Integer.class);
        this.mapHeight = map.getProperties().get("height", Integer.class);
        this.tileWidth = map.getProperties().get("tilewidth", Integer.class);
        this.tileHeight = map.getProperties().get("tileheight", Integer.class);
        this.width = mapWidth * multiplier;
        this.height = mapHeight * multiplier;
        colide = new Array<Boolean>(width * height);
        colideBuilding = new Array<Boolean>(width * height);
        for(int i = 0; i < width * height; i++) {
            colide.add(false);
            colideBuilding.add(false);
        }
        this.map = map;
        this.multiplier = multiplier;
        graph = new Array<TiledNode>();
        for(int i = 0; i < width; i++)
            for(int j = 0; j < height; j++) graph.add(new TiledNode(i, j, height));
        init();
    }

    private void init() {
        for(int i = 0; i < width; i++)
            for(int j = 0; j < height; j++)
                colide.set(getNode(i, j).getIndex(), colide(i, j));
        for(int i = 0; i < width; i++)
            for(int j = 0; j < height; j++)
                colideBuilding.set(getNode(i, j).getIndex(), false);
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                TiledNode from = getNode(i, j);
                if(nodeWithinBounds(i + 1, j) && !colideO1(i + 1, j))
                    from.addConnection(getNode(i + 1, j)); //right
                if(nodeWithinBounds(i - 1, j) && !colideO1(i - 1, j))
                    from.addConnection(getNode(i - 1, j)); //left
                if(nodeWithinBounds(i, j + 1) && !colideO1(i, j + 1))
                    from.addConnection(getNode(i, j + 1)); //right
                if(nodeWithinBounds(i, j - 1) && !colideO1(i, j - 1))
                    from.addConnection(getNode(i, j - 1)); //down
            }
        }
    }

    @Override
    public int getIndex(TiledNode node) {
        return node.getIndex();
    }

    @Override
    public int getNodeCount() {
        return width * height;
    }

    @Override
    public Array<Connection<TiledNode>> getConnections(TiledNode fromNode) {
        Array<Connection<TiledNode>> res = new Array<Connection<TiledNode>>();
        for(Connection<TiledNode> conn:fromNode.getConnections()){
            if(!colideO1(conn.getToNode().getIndex())){
                res.add(conn);
            }
        }
        return res;
    }

    public TiledNode getNode(int index){
        return graph.get(index);
   }

    public TiledNode getNode(int x, int y){
        return graph.get(x * height + y);
   }

    public TiledNode getNodeFromScreen(float x, float y){
        Integer newX = (int) (x / tileWidth);
        Integer newY = (int) (y / tileHeight);
        return getNode(newX, newY);
    }

    public void addConnection(TiledNode fromNode, TiledNode toNode){
       fromNode.addConnection(toNode);
   }

    private Boolean nodeWithinBounds(Integer x, Integer y) {
        return !(y < 0 || y >= height || x >= width || x < 0);
    }

    private Boolean colide(Integer x, Integer y) {
        Boolean colide = false;
        for(TiledMapTileLayer t : map.getLayers().getByType(TiledMapTileLayer.class))
            colide = colide || (
                    t.getCell(x / multiplier, y / multiplier) != null &&
                    t.getCell(x / multiplier, y / multiplier).getTile().getProperties().containsKey("blocked")
            );
        for(RectangleMapObject r : map.getLayers().get("collision_layer").getObjects().getByType(RectangleMapObject.class))
            colide = colide || r.getRectangle().contains(x / multiplier, y / multiplier);
        return colide;
    }

    public Boolean colideO1(Integer index){
        return (index < 0 || index > width * height) ||
                (colide.get(index));
    }

    public Boolean colideO1(Integer x, Integer y){
        return (x < 0 || y < 0 || x >= width || y >=  height) ||
                (colide.get(getNode(x, y).getIndex()));
    }

    public void setCollision(float x, float y, boolean collision) {
        Integer realX = (int)x * multiplier;
        Integer realY = (int)y * multiplier;
        colide.set(getNode(realX, realY).getIndex(), collision);
    }

    public void setCollisionBuilding(float x, float y, boolean collision) {
        Integer realX = (int)x * multiplier;
        Integer realY = (int)y * multiplier;
        colideBuilding.set(getNode(realX, realY).getIndex(), collision);
    }

    public boolean colideO1Building(int x, int y) {
        return (x < 0 || y < 0 || x >= width || y >=  height) ||
                (colideBuilding.get(getNode(x, y).getIndex()));
    }
}
