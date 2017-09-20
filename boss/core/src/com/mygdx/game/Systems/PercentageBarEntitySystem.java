package com.mygdx.game.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.Components.MapComponent;
import com.mygdx.game.Components.PercentageComponent;
import com.mygdx.game.Components.WorldObjects.HealthComponent;
import com.mygdx.game.Components.WorldObjects.WorldObjectComponent;
import com.mygdx.game.Components.WorldObjects.WorldPositionComponent;
import com.mygdx.game.Mappers.Mappers;

public abstract class PercentageBarEntitySystem<T extends PercentageComponent, E extends WorldObjectComponent> extends EntitySystem{

    private ImmutableArray<Entity> entities;
    protected Family family;
    protected ComponentMapper<T> percentageMapper;
    Engine engine;
    protected ShapeRenderer shapeRenderer;
    protected Camera camera;

    public PercentageBarEntitySystem(Family f, ComponentMapper<T> percentageMapper){
        family = f;
        this.percentageMapper = percentageMapper;
    }

    public void addedToEngine(Engine engine){
        this.engine = engine;
        camera = Mappers.map.get(engine.getEntitiesFor(Family.all(MapComponent.class).get()).first()).camera;
        shapeRenderer = new ShapeRenderer();
    }

    public void update(float deltaTime){
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.setProjectionMatrix(camera.combined);
        entities = engine.getEntitiesFor(family);
        for(Entity e: entities){
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            WorldPositionComponent worldPositionComponent = Mappers.worldPosition.get(e);
            WorldObjectComponent wo = Mappers.world.get(e);
            T percentageComponent = percentageMapper.get(e);
            float healthPercentage = percentageComponent.hitPoints / percentageComponent.maxHitPoints;
            shapeRenderer.rect(worldPositionComponent.position.x,
                    worldPositionComponent.position.y - 6,
                    wo.bounds.getRectangle().width * healthPercentage, 6,
                    topGradient(healthPercentage),
                    topGradient(healthPercentage),
                    bottomGradient(healthPercentage),
                    bottomGradient(healthPercentage));
            shapeRenderer.end();
        }
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    protected abstract Color topGradient(float percentage);

    protected abstract Color bottomGradient(float percentage);
}
