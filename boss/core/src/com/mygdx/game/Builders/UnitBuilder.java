package com.mygdx.game.Builders;

import com.mygdx.game.PathfindingUtils.MapGraph;
import com.mygdx.game.Play;

public abstract class UnitBuilder {
    protected Play play;
    protected MapGraph mapGraph;
    protected int id;

    public UnitBuilder(Play play, MapGraph mapGraph){
        this.play = play;
        this.mapGraph = mapGraph;
        id = 0;
    }

    public static int IDLE = 1;
    public static int MOVE_RIGHT_BOTTOM = 2;
    public static int MOVE_LEFT_BOTTOM = 3;
    public static int MOVE_RIGHT_TOP = 4;
    public static int MOVE_LEFT_TOP = 5;
    public static int WATER_GATHERING = 6;
    public static int ATTACKING_RIGHT_BOTTOM = 7;
    public static int ATTACKING_LEFT_BOTTOM = 8;
    public static int ATTACKING_RIGHT_TOP = 9;
    public static int ATTACKING_LEFT_TOP = 10;
    public static int DEAD = 11;
}
