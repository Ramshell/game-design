package com.mygdx.game.Systems;

import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.Components.SelectionComponent;
import com.mygdx.game.Components.WorldObjects.HealthComponent;
import com.mygdx.game.Components.WorldObjects.ResourceComponent;
import com.mygdx.game.Components.WorldObjects.WorldObjectComponent;
import com.mygdx.game.Components.WorldObjects.WorldPositionComponent;
import com.mygdx.game.Mappers.Mappers;

public class ResourcesPercentageRenderSystem extends PercentageBarEntitySystem<ResourceComponent, WorldObjectComponent>{
    public ResourcesPercentageRenderSystem() {
        super(Family.all(ResourceComponent.class, WorldPositionComponent.class,
                WorldObjectComponent.class, SelectionComponent.class).get(), Mappers.resourceComponentMapper);
    }

    public Color topGradient(float percentage){
        Color res;
        if(percentage < 0.2) res = new Color(0.5f, 0.55f, 0.8f, 0.9f);
        else if(percentage < 0.5) res = new Color(0.1f, 0.4f, 0.7f, 0.9f);
        else res = new Color(0, 0.3f, 0.5f, 0.9f);
        return res;
    }

    public Color bottomGradient(float percentage){
        Color res;
        if(percentage < 0.2) res = new Color(0.35f, 0.45f, 0.95f, 0.9f);
        else if(percentage < 0.5) res = new Color(0.05f, 0.3f, 0.8f, 0.9f);
        else res = new Color(0, 0, 1, 0.9f);
        return res;
    }

}
