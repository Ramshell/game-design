package com.platformer.ar.Components.World;

import com.badlogic.ashley.core.Component;

public class PlayerComponent implements Component{
    float damage = 10;
    public float maxSpeed = 200f;
    public float jumpSpeed = 300f;
    float velocity = 0;
}
