package com.mygdx.game.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.mygdx.game.Components.WorldObjects.WorldObjectComponent;
import com.mygdx.game.Entities.BuildingEntity;
import com.mygdx.game.OOP.Actions.Action;
import com.mygdx.game.OOP.Actions.ActionBuilder;
import com.mygdx.game.OOP.SelectedWO;

public class PlayerComponent implements Component {

    public enum PlayerState{
        Normal, Building
    }

    public boolean human;
    public int resources = 0, worldObjects = 0;
    public SelectedWO selectedObject = new SelectedWO();
    public PlayerState state = PlayerState.Normal;
    public BuildingEntity tryingBuilding;
    public ActionBuilder action;
}
