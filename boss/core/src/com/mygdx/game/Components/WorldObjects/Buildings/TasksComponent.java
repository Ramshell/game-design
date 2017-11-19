package com.mygdx.game.Components.WorldObjects.Buildings;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Queue;
import com.mygdx.game.OOP.Actions.BuildAction;
import javafx.util.Pair;

public class TasksComponent implements Component{
    public Queue<Pair<BuildAction,Entity>> tasks = new Queue<Pair<BuildAction,Entity>>();
    public float currentTaskProgress = 0.0f;


    public void createUnit(Pair<BuildAction,Entity> pair){
        tasks.addLast(pair);
    }

    public void processBuildQueue(float deltaTime){
        if(tasks.size > 0){
            Pair<BuildAction, Entity> first = tasks.first();
            currentTaskProgress += deltaTime * first.getKey().getBuildSpeed();
            if(currentTaskProgress > first.getKey().getMaxBuildSpeed()){
                Pair<BuildAction, Entity> pair = tasks.removeFirst();
                pair.getKey().act(pair.getValue());
                currentTaskProgress = 0.0f;
            }
        }
    }
}
