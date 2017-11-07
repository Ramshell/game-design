package com.platformer.ar.Systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.platformer.ar.Assets;
import com.platformer.ar.Components.HudComponent;

public class RenderHudSystem extends EntitySystem {

    HudComponent hud;

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        hud = engine.getEntitiesFor(Family.all(HudComponent.class).get()).first().getComponent(HudComponent.class);
    }

    @Override
    public void update(float deltaTime) {
        hud.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.act(deltaTime);
        hud.stage.draw();
    }

    public void damage(){
        hud.hearts.get(hud.playerComponent.health - 1).setDrawable(new TextureRegionDrawable(Assets.heartDown));
    }

    public void heal(){
        hud.hearts.get(hud.playerComponent.health).setDrawable(new TextureRegionDrawable(Assets.heart));
    }
}
