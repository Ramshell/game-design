package com.mygdx.game.Components;

import com.badlogic.ashley.core.Component;
import com.mygdx.game.Entities.BuildingEntity;

public class PlayerComponent implements Component {

    public enum PlayerState{
        Normal, Building
    }

    public boolean human;
    public int resources = 0;
    public WorldObjectComponent selectedObject = new WorldObjectComponent();
    public PlayerState state = PlayerState.Normal;
    public BuildingEntity tryingBuilding;
}
