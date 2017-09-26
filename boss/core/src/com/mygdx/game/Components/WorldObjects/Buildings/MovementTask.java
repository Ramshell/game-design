package com.mygdx.game.Components.WorldObjects.Buildings;

import com.badlogic.ashley.core.Entity;
import com.mygdx.game.Components.WorldObjects.TargetComponent;
import com.mygdx.game.Mappers.Mappers;

public class MovementTask implements Task<Entity>{
    @Override
    public boolean finished(Entity e) {
        TargetComponent target = Mappers.target.get(e);
        return target != null && target.nextNode == target.path.getCount() &&
                Mappers.worldPosition.get(e).position
                        .epsilonEquals(target.target.x, target.target.y, 0.3f);
    }
}
