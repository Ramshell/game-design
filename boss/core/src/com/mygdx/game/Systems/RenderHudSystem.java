package com.mygdx.game.Systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.Components.HUD.HUDComponent;
import com.mygdx.game.Components.MapComponent;
import com.mygdx.game.Mappers.Mappers;

public class RenderHudSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;


    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(MapComponent.class, HUDComponent.class).get());
    }


    public void update(float delta) {
        for(Entity e : entities){
            MapComponent mapComponent = Mappers.map.get(e);
            HUDComponent hud = Mappers.hud.get(e);
            mapComponent.renderer.getBatch().setProjectionMatrix(hud.stage.getCamera().combined);
            updateHudComponent(hud);
            hud.stage.draw();
        }
    }

    private void updateHudComponent(HUDComponent hud) {
        drawSelectedName(hud);
        drawResources(hud);
        drawButtons(hud);
    }

    private void drawSelectedName(HUDComponent hud){
        hud.selectedObjectLabel.setText(hud.player.selectedObject.getName());
    }

    private void drawResources(HUDComponent hud) {
        hud.resourcesLabel.setText("    " + Integer.toString(hud.player.resources));
    }

    private void drawButtons(HUDComponent hud) {

    }
}
