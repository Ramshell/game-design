package com.mygdx.game.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.game.Components.CameraComponent;
import com.mygdx.game.Components.MatchComponent;
import com.mygdx.game.Components.PositionComponent;
import com.mygdx.game.Mappers.Mappers;


public class SetUpSystem extends EntitySystem {
    MatchComponent matchComponent;

    public void addedToEngine(Engine engine) {
        matchComponent = Mappers.matchComponentMapper.get(getEngine().getEntitiesFor(Family.all(MatchComponent.class).get()).first());
    }

    public void update(float deltaTime) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        matchComponent.world.step(1 / 60f, 8, 3);

    }
}
