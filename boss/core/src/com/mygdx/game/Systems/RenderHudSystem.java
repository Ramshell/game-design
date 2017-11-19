package com.mygdx.game.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.OrderedSet;
import com.mygdx.game.Components.HUD.HUDComponent;
import com.mygdx.game.Components.MapComponent;
import com.mygdx.game.Components.Matches.GoalComponent;
import com.mygdx.game.Components.WorldObjects.ActionComponent;
import com.mygdx.game.Components.WorldObjects.Buildings.TasksComponent;
import com.mygdx.game.Mappers.Mappers;
import com.mygdx.game.OOP.Actions.BuildAction;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class RenderHudSystem extends EntitySystem implements EntityListener{
    private ImmutableArray<Entity> entities;
    MapComponent mapComponent;
    HUDComponent hud;
    Map<GoalComponent, Label> missions = new HashMap<GoalComponent, Label>();
    BuildAction buildAction = null;

    public void addedToEngine(Engine engine) {
        mapComponent = Mappers.map.get(engine.getEntitiesFor(Family.all(MapComponent.class).get()).first());
        hud = Mappers.hud.get(engine.getEntitiesFor(Family.all(HUDComponent.class).get()).first());
        engine.addEntityListener(
                Family.all(GoalComponent.class).get(),
                this);
        for(Entity e : engine.getEntitiesFor(Family.all(GoalComponent.class).get())){
            entityAdded(e);
        }
    }


    public void update(float delta) {
        mapComponent.renderer.getBatch().setProjectionMatrix(hud.stage.getCamera().combined);
        updateHudComponent(hud);
        hud.stage.act(delta);
        hud.stage.draw();
    }

    private void updateHudComponent(HUDComponent hud) {
        updateSelectedName(hud);
        updateBuildBar(hud);
        updateResources(hud);
        updateButtons(hud);
        updateMissions(hud);
    }

    private void updateBuildBar(HUDComponent hud) {
        if(hud.player.selectedObject.getSelectedObjects().size == 1 &&
                Mappers.tasksComponentMapper.get(hud.player.selectedObject.getSelectedObjects().first()) != null){
            TasksComponent tasksComponent = Mappers.tasksComponentMapper.get(hud.player.selectedObject.getSelectedObjects().first());
            if(tasksComponent.tasks.size > 0){
                Pair<BuildAction, Entity> task = tasksComponent.tasks.first();
                hud.buildQueue.setVisible(true);
                hud.creatingBuildQueue.setText("Creating " + task.getKey().getName());
                if(buildAction != task.getKey()) {
                    buildAction = task.getKey();
                    hud.buildQueue.setRange(0, 100);
                    hud.buildQueue.setStepSize(1);
                    hud.buildQueue.setValue(0);
                    hud.buildQueue.setAnimateDuration(0.1f);
                }else{
                    hud.buildQueue.setValue(tasksComponent.currentTaskProgress * 100 / task.getKey().getMaxBuildSpeed());
                }

            }else{
                hud.creatingBuildQueue.setText("");
                hud.buildQueue.setValue(0);
                hud.buildQueue.setAnimateDuration(0f);
                hud.buildQueue.setVisible(false);
            }
        }else{
            hud.creatingBuildQueue.setText("");
            hud.buildQueue.setValue(0);
            hud.buildQueue.setAnimateDuration(0f);
            hud.buildQueue.setVisible(false);
        }
    }

    private void updateMissions(HUDComponent hud) {
        for(Map.Entry<GoalComponent, Label> p : missions.entrySet()){
            p.getValue().setText(p.getKey().condition.getDescription());
        }
    }

    private void updateSelectedName(HUDComponent hud){
        if(hud.player.selectedObject.gotEnemy()){
            hud.selectedObjectLabel.setColor(new com.badlogic.gdx.graphics.Color(1,0,0,1));
        }else{
            hud.selectedObjectLabel.setColor(new com.badlogic.gdx.graphics.Color(0,1,0,1));
        }
        hud.selectedObjectLabel.setText(hud.player.selectedObject.getName());
    }

    private void updateResources(HUDComponent hud) {
        hud.resourcesLabel.setText("   " + Integer.toString(hud.player.resources));
    }

    private void updateButtons(HUDComponent hud) {
        Array<ActionComponent> iter = hud.player.selectedObject.getActions().orderedItems();
        int curr = 0;
        for(int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(curr >= iter.size || hud.player.selectedObject.gotEnemy()) {
                    hud.actionButtons.get(i).get(j).setVisible(false);
                    if(hud.actionButtons.get(i).get(j).getListeners().size >= 2)
                        hud.actionButtons.get(i).get(j).getListeners().removeIndex(1);
                    continue;
                }
                ActionComponent action = iter.get(curr++);
                if(!hud.actionButtons.get(i).get(j).getListeners().contains(action.listener, true)){
                    if(hud.actionButtons.get(i).get(j).getListeners().size >= 2)
                        hud.actionButtons.get(i).get(j).getListeners().removeIndex(1);
                    hud.actionButtons.get(i).get(j).addListener(action.listener);
                    hud.actionButtons.get(i).get(j).setVisible(true);
                    hud.actionButtons.get(i).get(j).setStyle(action.button.getStyle());
                }
            }
        }
    }

    @Override
    public void entityAdded(Entity entity) {
        if(Mappers.goalComponentMapper.get(entity) != null){
            Label label = new Label(Mappers.goalComponentMapper.get(entity).condition.getDescription(), hud.skin);
            hud.missionTable.row();
            hud.missionTable.add(label).top().left().padLeft(10).padTop(5);
            missions.put(Mappers.goalComponentMapper.get(entity), label);
        }
    }

    @Override
    public void entityRemoved(Entity entity) {
        missions.get(Mappers.goalComponentMapper.get(entity)).setColor(com.badlogic.gdx.graphics.Color.GREEN);
//        missions.remove(Mappers.goalComponentMapper.get(entity));
    }
}
