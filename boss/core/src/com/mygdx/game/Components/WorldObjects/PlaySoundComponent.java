package com.mygdx.game.Components.WorldObjects;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.audio.Sound;

public class PlaySoundComponent implements Component{
    public Sound sound;
    public String mappingSound;

    public PlaySoundComponent(){}
    public PlaySoundComponent(String key){
        mappingSound = key;
    }
}
