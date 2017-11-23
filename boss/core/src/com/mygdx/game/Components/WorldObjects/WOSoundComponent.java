package com.mygdx.game.Components.WorldObjects;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.OOP.RTSSound;

import java.util.HashMap;
import java.util.Map;

public class WOSoundComponent implements Component{
    public HashMap<String, Array<RTSSound>> sounds = new HashMap<String, Array<RTSSound>>();
    public RTSSound lastSound;
    public float startTime = 0;
    public boolean residual = false;
}
