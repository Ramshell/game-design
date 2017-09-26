package com.mygdx.game.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Components.WorldObjects.Buildings.BuildingHolder;
import com.mygdx.game.Entities.BuildingEntity;

public class ToBuildComponent extends BuildingHolder{
    public int x, y;

    @Override
    public Vector3 getCoords(OrthographicCamera camera) {
        return new Vector3(x,y, 0);
    }
}
