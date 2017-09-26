package com.mygdx.game.Components.WorldObjects.Buildings;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

public class TryingBuildingComponent extends BuildingHolder{
    @Override
    public Vector3 getCoords(OrthographicCamera camera) {
        return camera.unproject(new Vector3(Gdx.input.getX(),Gdx.input.getY(), 0));
    }
}
