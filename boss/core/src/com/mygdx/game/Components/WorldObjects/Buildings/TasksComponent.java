package com.mygdx.game.Components.WorldObjects.Buildings;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Queue;
import com.mygdx.game.Entities.UnitEntity;
import com.mygdx.game.OOP.Actions.Action;
import javafx.util.Pair;

public class TasksComponent implements Component{
    public Queue<Pair<Action<Entity>,Entity>> tasks = new Queue<Pair<Action<Entity>,Entity>>();
    public float currentTaskProgress = 0.0f, maxBuildProgress, buildSpeed;

    public TasksComponent(float maxBuildProgress, float buildSpeed){
        this.maxBuildProgress = maxBuildProgress;
        this.buildSpeed = buildSpeed;
    }

    public void createUnit(Pair<Action<Entity>,Entity> pair){
        tasks.addLast(pair);
    }

    public void processBuildQueue(float deltaTime){
        if(tasks.size > 0){
            currentTaskProgress += deltaTime * buildSpeed;
            System.out.print(currentTaskProgress * 100 / maxBuildProgress);
            System.out.println(" %");
            if(currentTaskProgress > maxBuildProgress){
                Pair<Action<Entity>, Entity> pair = tasks.removeFirst();
                pair.getKey().act(pair.getValue());
                currentTaskProgress = 0.0f;
            }
        }
    }
}
