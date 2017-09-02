package com.mygdx.game.Components;

import com.badlogic.ashley.core.Component;

public class PlayerComponent implements Component {
    public boolean human;
    public int resources;
    public WorldObjectComponent selectedObject = new WorldObjectComponent();
}
