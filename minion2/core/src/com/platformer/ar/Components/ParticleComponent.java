package com.platformer.ar.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;

public class ParticleComponent implements Component{
    public ParticleEffect particleEffect;

    public ParticleComponent(ParticleEffect particleEffect){
        this.particleEffect = particleEffect;
    }
}
