package com.mygdx.game.OOP;


import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.OrderedSet;
import com.mygdx.game.Components.SelectionComponent;
import com.mygdx.game.Components.WorldObjects.ActionComponent;
import com.mygdx.game.Components.WorldObjects.DynamicWOComponent;
import com.mygdx.game.Components.WorldObjects.PlaySoundComponent;
import com.mygdx.game.Components.WorldObjects.WorldObjectComponent;
import com.mygdx.game.Mappers.Mappers;
import com.mygdx.game.OOP.Actions.Action;

public class SelectedWO {
    private OrderedSet<Entity> selectedObjects;
    private OrderedSet<Entity> toRemove = new OrderedSet<Entity>();
    private String name;
    private OrderedSet<ActionComponent> actions = new OrderedSet<ActionComponent>();

    private boolean onlyDyn = false;

    public SelectedWO(){
        selectedObjects = new OrderedSet<Entity>();
    }

    public OrderedSet<Entity> getSelectedObjects(){
        return selectedObjects;
    }

    private Boolean selected(){
        return selectedObjects.size > 0;
    }

    public String getName(){
        if(!selected()) return "Nothing Selected";
        else return name;
    }

    public OrderedSet<ActionComponent> getActions(){
        return actions;
    }


    public void add(Entity entity){
        WorldObjectComponent wo = Mappers.world.get(entity);
        DynamicWOComponent dyn = Mappers.dynamicWOComponentMapper.get(entity);
        if(dyn != null && !onlyDyn){
            onlyDyn = true;
            toRemove.clear();
            for(Entity e: selectedObjects)
                if(Mappers.dynamicWOComponentMapper.get(e) == null) {
                    toRemove.add(e);
                    e.remove(SelectionComponent.class);
            }
            for(Entity e: toRemove) selectedObjects.remove(e);
        }
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
        OrderedSet<ActionComponent> actions = new OrderedSet<ActionComponent>();
        for(ActionComponent a : wo.actions){
            if(selectedObjects.size == 0 || containsAction(a.key))
                actions.add(a);
        }
        this.actions.clear();
        this.actions = actions;
        selectedObjects.add(entity);
        entity.add(new SelectionComponent());
        playSound(entity);
    }

    private void playSound(Entity entity) {
        if( Mappers.woSoundComponentMapper.get(entity)  != null) {
            if(Mappers.woSoundComponentMapper.get(entity).lastSound != null){
                Mappers.woSoundComponentMapper.get(entity).lastSound.stop();
            }
            entity.add(new PlaySoundComponent());
        }
    }

    public void addEnemy(Entity e) {
        deselect();
        add(e);
        e.getComponent(SelectionComponent.class).selectedByEnemy = true;
        name = name + " - Enemy";
    }

    public void deselect(){
        onlyDyn = false;
        for(Entity e : selectedObjects){
            e.remove(SelectionComponent.class);
        }
        name = "";
        actions.clear();
        selectedObjects.clear();
    }

    public void act(Action<Entity> action){
        if(gotEnemy()) return;
        for(Entity e : selectedObjects){
            playSound(e);
            action.act(e);
        }
    }

    public boolean gotEnemy(){
        return  name!= null        &&
                name.length() >= 5 &&
                name.substring(name.length() - 5, name.length()).equals("Enemy");
    }

    public boolean containsAction(String action){
        for(ActionComponent a : actions){
            if(action.equals(a.key)) return true;
        }
        return false;
    }
}
