package com.mygdx.game.Components.Combat;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Mappers.ResourceMapper;

public class DamageSpawnComponent implements Component {
    public float lifeSpan = 0.2f, current = 0, speed = 15, x, y, realX, realY;
    public int damage;
    public Interpolation pow5 = Interpolation.pow5In;
    public Interpolation pow2 = Interpolation.pow2Out;

    public DamageSpawnComponent(float x, float y, int damage){
        this.x = (x / ResourceMapper.tileWidth) * ResourceMapper.tileWidth;
        this.y = (y / ResourceMapper.tileHeight) * ResourceMapper.tileHeight + ResourceMapper.tileHeight * 3;
        this.realX = x;
        this.realY = y;
        this.damage = damage;
    }
}
