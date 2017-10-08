package com.mygdx.game.Components.WorldObjects;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.OOP.Actions.Action;

public class ActionComponent implements Component{
    public Action<Entity> actions;
    public ImageButton button;
    public EventListener listener;
    public String key;

    @Override
    public boolean equals(Object o){
        return  o != null &&
                o instanceof ActionComponent &&
                key.equals(((ActionComponent)o).key);
    }
}
