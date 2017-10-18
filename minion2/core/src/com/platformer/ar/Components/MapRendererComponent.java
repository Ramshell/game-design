package com.platformer.ar.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class MapRendererComponent implements Component{
    public OrthogonalTiledMapRenderer renderer;

    public MapRendererComponent(OrthogonalTiledMapRenderer r){
        renderer = r;
    }
}
