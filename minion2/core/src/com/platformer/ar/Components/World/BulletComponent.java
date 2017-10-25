package com.platformer.ar.Components.World;

import com.badlogic.ashley.core.Component;

public class BulletComponent implements Component{
    public float damage;

    public BulletComponent(float damage) {
        this.damage = damage;
    }
}
