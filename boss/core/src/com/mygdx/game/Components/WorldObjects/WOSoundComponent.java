package com.mygdx.game.Components.WorldObjects;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.Map;

public class WOSoundComponent implements Component{
    public HashMap<String, Array<Sound>> sounds = new HashMap<String, Array<Sound>>();
    public Sound lastSound;
}
