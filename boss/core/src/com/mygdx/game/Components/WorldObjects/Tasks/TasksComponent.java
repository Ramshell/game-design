package com.mygdx.game.Components.WorldObjects.Tasks;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Queue;

public class TasksComponent implements Component{
    public Queue<Task<Entity>> tasks = new Queue<Task<Entity>>();

    public boolean finished(){
        return tasks.size == 0;
    }
}
