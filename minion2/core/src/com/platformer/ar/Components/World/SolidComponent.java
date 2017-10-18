package com.platformer.ar.Components.World;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;

public class SolidComponent implements Component{
    public Rectangle rectangle;

    public SolidComponent(Rectangle r){
        rectangle = r;
    }
}
