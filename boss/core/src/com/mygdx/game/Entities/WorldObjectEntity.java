package com.mygdx.game.Entities;

import com.badlogic.ashley.core.Entity;
import com.mygdx.game.Components.PlayerComponent;
import com.mygdx.game.Components.WorldObjects.WorldObjectComponent;

public class WorldObjectEntity extends Entity{
    public WorldObjectEntity(PlayerComponent player, WorldObjectComponent wo){
        add(wo).add(player);
    }
}
