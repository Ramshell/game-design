package com.mygdx.game.Components.WorldObjects;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.PathfindingUtils.MapGraph;

public class SpawnComponent implements Component{
    public int spawn_side = 0, x, y, width, height, currX = 0, currY = 0;

    public SpawnComponent(int x, int y, int width, int height){
        this.x = x; this.y = y; this.width = width + 1; this.height = height + 1;
    }

    public void setPos(int x, int y){
        this.x = x; this.y = y;
        currX = x - 1; currY = y - 1;
    }


    public Vector2 nextSpawnTile(MapGraph mapGraph){
        spawn_side = 0; currX = x - 1; currY = y - 1;
        while(spawn_side != 4 && mapGraph.colideO1(currX, currY)  ||
                mapGraph.getNode(currX, currY).entities.size > 0  ||
                currY < 0 || currX < 0 || currX >= mapGraph.width || currY >= mapGraph.height){
            tick();
        }
        return spawn_side == 4 ? null : new Vector2(currX, currY);
    }

    private void tick(){
        update();
        if(spawn_side == 0)
            this.currX += 1;
        else if(spawn_side == 1)
            this.currY += 1;
        else if(spawn_side == 2)
            this.currX -= 1;
        else if(spawn_side == 3)
            this.currY -= 1;
    }

    private void update() {
        if(this.currX > x + width - 1){
            spawn_side = 1;
            this.currX -= 1;
            this.currY += 1;
        }else if(this.currY > y + height - 1){
            spawn_side = 2;
            this.currY -= 1;
            this.currX -= 1;
        }else if(this.currX < x - 1){
            this.spawn_side = 3;
            this.currX += 1;
            this.currY -= 1;
        }else if(this.currY < y - 1) spawn_side = 4;
    }
}
