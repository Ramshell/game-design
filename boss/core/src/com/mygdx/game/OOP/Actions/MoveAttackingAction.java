package com.mygdx.game.OOP.Actions;

import com.badlogic.ashley.core.Entity;
import com.mygdx.game.Components.Combat.WannaAttackComponent;
import com.mygdx.game.Mappers.Mappers;
import com.mygdx.game.PathfindingUtils.MapGraph;

public class MoveAttackingAction extends MoveAction{
    /**
     * x and y should be isoScreen coordinates;
     *
     * @param x
     * @param y
     * @param mapGraph
     **/
    public MoveAttackingAction(float x, float y, MapGraph mapGraph) {
        super(x, y, mapGraph);
    }

    @Override
    public void act(Entity e) {
        Entity attacked = AttackAction.getTarget(x, y, mapGraph, Mappers.player.get(e));
        if( attacked != null &&
                !Mappers.player.get(attacked).equals(Mappers.player.get(e))){
            new AttackAction(attacked).act(e);
        }else {
            super.act(e);
            if (e.getComponent(WannaAttackComponent.class) == null) {
                e.add(new WannaAttackComponent());
            }
        }
    }
}
