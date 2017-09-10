package com.mygdx.game.Entities;

import com.badlogic.ashley.core.Entity;
import com.mygdx.game.Components.HUD.HUDComponent;
import com.mygdx.game.Components.PlayerComponent;

public class PlayerEntity extends Entity{
    public PlayerEntity(HUDComponent hudComponent, PlayerComponent player){
        add(hudComponent).add(player);
    }
}
