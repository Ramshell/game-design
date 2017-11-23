package com.mygdx.game.OOP;

public class MutableInt {

    public int i;

    public MutableInt(int i){
        this.i = i;
    }

    public int add(){
        return ++this.i;
    }
}
