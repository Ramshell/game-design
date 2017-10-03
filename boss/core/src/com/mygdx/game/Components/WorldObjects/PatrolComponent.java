package com.mygdx.game.Components.WorldObjects;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class PatrolComponent implements Component{
    public Vector2 fst, snd;

    public PatrolComponent(Vector2 fst, Vector2 snd){
        this.fst = fst;
        this.snd = snd;
    }

    public PatrolComponent(int fstX, int fstY, int sndX, int sndY){
        this.fst = new Vector2(fstX, fstY);
        this.snd = new Vector2(sndX, sndY);
    }
}
