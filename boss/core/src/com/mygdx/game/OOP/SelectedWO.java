package com.mygdx.game.OOP;


import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.OrderedSet;
import com.mygdx.game.Components.WorldObjects.WorldObjectComponent;

public class SelectedWO {
    private OrderedSet<WorldObjectComponent> selectedObjects;
    private String name;

    public SelectedWO(){
        selectedObjects = new OrderedSet<WorldObjectComponent>();
    }

    private Boolean selected(){
        return selectedObjects.size > 0;
    }

    public String getName(){
        if(!selected()) return "Nothing Selected";
        else return name;
    }


    public void add(WorldObjectComponent wo){
        Boolean notContained = !selectedObjects.contains(wo);
        if(!selected()) name = wo.objectName;
        else if(selectedObjects.size == 1 && name.equals(wo.objectName) &&
                notContained) name = name + "s";
        else if(!name.substring(0, name.length() - 1).equals(wo.objectName) &&
                notContained){
            name = "Group";
        }
        selectedObjects.add(wo);
    }
    public void deselect(){
        for(WorldObjectComponent wo : selectedObjects) wo.currentlySelected = false;
        name = "";
        selectedObjects.clear();
    }
}
