package com.mygdx.game.Components.WorldObjects.Buildings;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Entities.BuildingEntity;

public abstract class BuildingHolder implements Component{
    public BuildingEntity building;

    public abstract Vector3 getCoords(OrthographicCamera camera);
}
