package com.mygdx.game.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Rectangle;

public class SelectionComponent implements Component{
    public Rectangle selection;
    public boolean selectedByEnemy = false;

    public SelectionComponent(){}
    public SelectionComponent(boolean selectedByEnemy){
        this.selectedByEnemy = selectedByEnemy;
    }
}
