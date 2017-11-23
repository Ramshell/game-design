package com.mygdx.game.OOP;

public class MutableString {
    public String s;

    public MutableString(String s){
        this.s = s;
    }

    public String concat(String s){
        this.s = this.s.concat(s);
        return this.s;
    }
}
