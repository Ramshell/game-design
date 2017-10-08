package com.mygdx.game.Components.WorldObjects;

import box2dLight.PointLight;
import box2dLight.PositionalLight;
import com.badlogic.ashley.core.Component;

public class LightComponent implements Component {
    public PointLight light;
    public float visibility;
}
