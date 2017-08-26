package com.mygdx.game.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;

public class MapComponent implements Component{
    public TiledMap map;
    public IsometricTiledMapRenderer renderer;
    public OrthographicCamera camera;


    public MapComponent(TiledMap map, OrthographicCamera c){
        this.map = map;
        renderer = new IsometricTiledMapRenderer(map);
        camera = c;

    }
}
