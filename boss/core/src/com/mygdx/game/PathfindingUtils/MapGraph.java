package com.mygdx.game.PathfindingUtils;


import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.utils.Array;

public class MapGraph implements IndexedGraph<TiledNode>{
    private Array<TiledNode> graph;
    private Integer width;
    private Integer height;

    public MapGraph(int width, int height){
        this.width = width;
        this.height = height;
        graph = new Array<TiledNode>();
        for(int i = 0; i < width; i++)
            for(int j = 0; j < height; j++) graph.add(new TiledNode(i, j, height));
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
        return fromNode.getConnections();
    }

   public TiledNode getNode(int index){
        return graph.get(index);
   }

   public TiledNode getNode(int x, int y){
       return graph.get(x * height + y);
   }

   public void addConnection(TiledNode fromNode, TiledNode toNode){
       fromNode.addConnection(toNode);
   }
}
