package com.mygdx.game.Components;

import com.badlogic.ashley.core.Component;

public class IdComponent implements Component{
    public int id;

    public IdComponent(int id){
        this.id = id;
    }
}
