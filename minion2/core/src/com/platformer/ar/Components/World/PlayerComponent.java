package com.platformer.ar.Components.World;

import com.badlogic.ashley.core.Component;

public class PlayerComponent implements Component{
    public float damage = 10, bulletSpeed = 500;
    public float maxSpeed = 200f;
    public float jumpSpeed = 350f;
    float velocity = 0;
    public float attackSpeed = 0.8f;
    public int health = 3, maxHealth = 3, coins = 0, jump = 0, maxJumps = 2;
    public boolean vulnerability = true;
    public float hittedElapsed = 1f, invulnerabilityTime = 1f;
}
