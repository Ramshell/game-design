package com.mygdx.game.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.Components.MapComponent;
import com.mygdx.game.Components.SelectionComponent;
import com.mygdx.game.Components.WorldObjects.HealthComponent;
import com.mygdx.game.Components.WorldObjects.WorldObjectComponent;
import com.mygdx.game.Components.WorldObjects.WorldPositionComponent;
import com.mygdx.game.Mappers.Mappers;
import com.mygdx.game.Mappers.ResourceMapper;

public class HealthRenderSystem extends PercentageBarEntitySystem<HealthComponent, WorldObjectComponent>{

    public HealthRenderSystem() {
        super(Family.all(HealthComponent.class, WorldPositionComponent.class,
                WorldObjectComponent.class, SelectionComponent.class).get(), Mappers.healthComponentComponentMapper);
    }

    public Color topGradient(float percentage){
        Color res;
        if(percentage < 0.2) res = new Color(0.8f, 0, 0.1f, 0.9f);
        else if(percentage < 0.5) res = new Color(0.5f, 0.5f, 0, 0.9f);
        else res = new Color(0.2f, 0.98f, 0.01f, 0.9f);
        return res;
    }

    public Color bottomGradient(float percentage){
        Color res;
        if(percentage < 0.2) res = new Color(0.9f, 0.01f, 0.3f, 0.9f);
        else if(percentage < 0.5) res = new Color(0.8f, 0.85f, 0.1f, 0.9f);
        else res = new Color(0.15f, 0.5f, 0, 0.9f);
        return res;
    }
}
