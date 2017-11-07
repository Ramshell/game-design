package com.platformer.ar.Components;

import com.badlogic.ashley.core.Component;

public class EnemyComponent implements Component{
    public int damage, shoots = 3;
    public boolean attacking = false;
    public float delay = 3;
    public final float maxDealey = 2;
}
