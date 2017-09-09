package com.mygdx.game.OOP;


import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.OrderedSet;
import com.mygdx.game.Components.WorldObjects.WorldObjectComponent;
import com.mygdx.game.Mappers.Mappers;
import com.mygdx.game.OOP.Actions.Action;

public class SelectedWO {
    private OrderedSet<Entity> selectedObjects;
    private String name;

    public SelectedWO(){
        selectedObjects = new OrderedSet<Entity>();
    }

    private Boolean selected(){
        return selectedObjects.size > 0;
    }

    public String getName(){
        if(!selected()) return "Nothing Selected";
        else return name;
    }


    public void add(Entity entity){
        WorldObjectComponent wo = Mappers.world.get(entity);
        if(wo == null ||
            (selectedObjects.size >= 1 &&
             selectedObjects.first().getClass() != entity.getClass())) return;
        Boolean notContained = !selectedObjects.contains(entity);
        if(!selected()) name = wo.objectName;
        else if(selectedObjects.size == 1 && name.equals(wo.objectName) &&
                notContained) name = name + "s";
        else if(!name.substring(0, name.length() - 1).equals(wo.objectName) &&
                notContained){
            name = "Group";
        }
        selectedObjects.add(entity);
        wo.currentlySelected = true;
    }
    public void deselect(){
        for(Entity e : selectedObjects){
            Mappers.world.get(e).currentlySelected = false;
        }
        name = "";
        selectedObjects.clear();
    }

    public void act(Action<Entity> action){
        for(Entity e : selectedObjects){
            action.act(e);
        }
    }
}
