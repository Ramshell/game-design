package com.mygdx.game.Components.WorldObjects;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;

public class WOSoundComponent implements Component{
    public Array<Sound> sounds = new Array<Sound>();
    public Sound lastSound;
}
