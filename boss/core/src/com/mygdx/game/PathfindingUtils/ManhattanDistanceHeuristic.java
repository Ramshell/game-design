package com.mygdx.game.PathfindingUtils;

import com.badlogic.gdx.ai.pfa.Heuristic;

public class ManhattanDistanceHeuristic implements Heuristic<TiledNode> {
    @Override
    public float estimate(TiledNode node, TiledNode endNode) {
        return Math.abs(endNode.getX() - node.getX()) + Math.abs(endNode.getY() - node.getY());
    }
}
