package com.mygdx.game.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
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
        updateMissions();
    }

    private void updateMissions() {
        for(Map.Entry<GoalComponent, Label> pair : missions.entrySet())
            pair.getValue().setText(pair.getKey().condition.getDescription());
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
            GoalComponent goalComponent = Mappers.goalComponentMapper.get(entity);
            Label label = new Label(goalComponent.condition.getDescription(), hud.skin);
            if(goalComponent.displayFromBeginning) {
                label.addAction(goalComponent.actionBeforeShow);
                hud.missionTable.row();
                hud.missionTable.add(label).top().left().padLeft(10).padTop(5);
            }
            missions.put(Mappers.goalComponentMapper.get(entity), label);
        }
    }

    @Override
    public void entityRemoved(Entity entity) {
        final GoalComponent goalComponent = Mappers.goalComponentMapper.get(entity);
        final Label label = missions.get(goalComponent);
        missions.remove(goalComponent);
        label.addAction(Actions.sequence(
                goalComponent.actionBeforeCompletion,
                Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        if(goalComponent.nextConditions.size > 0){
                            missions.put(goalComponent.nextConditions.get(0), label);
                        }else label.setText("");
                    }
                }),
                goalComponent.nextConditions.size > 0 ?
                        goalComponent.nextConditions.get(0).actionBeforeShow :
                        Actions.delay(0)
        ));
        if(goalComponent.nextConditions.size > 1){
            for(int i = 1; i < goalComponent.nextConditions.size; ++i){
                Label label2 = new Label(goalComponent.nextConditions.get(i).condition.getDescription(), hud.skin);
                label2.addAction(goalComponent.nextConditions.get(i).actionBeforeShow);
                hud.missionTable.row();
                hud.missionTable.add(label2).top().left().padLeft(10).padTop(5);
                missions.put(goalComponent.nextConditions.get(i), label2);
            }
        }
    }
}
