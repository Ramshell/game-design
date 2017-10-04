package com.mygdx.game.Components.Combat;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Mappers.ResourceMapper;

public class DamageSpawnComponent implements Component {
    public float lifeSpan = 0.2f, current = 0, speed = 15, x, y;
    public int damage;

    public DamageSpawnComponent(float x, float y, int damage){
        this.x = (x / ResourceMapper.tileWidth) * ResourceMapper.tileWidth;
        this.y = (y / ResourceMapper.tileHeight) * ResourceMapper.tileHeight + ResourceMapper.tileHeight * 3;
        this.damage = damage;
    }
}
