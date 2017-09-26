package com.mygdx.game.Systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.OrderedSet;
import com.mygdx.game.Components.HUD.HUDComponent;
import com.mygdx.game.Components.MapComponent;
import com.mygdx.game.Components.WorldObjects.ActionComponent;
import com.mygdx.game.Mappers.Mappers;
import javafx.scene.paint.Color;

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
            hud.stage.act(delta);
            hud.stage.draw();
        }
    }

    private void updateHudComponent(HUDComponent hud) {
        updateSelectedName(hud);
        updateResources(hud);
        updateButtons(hud);
    }

    private void updateSelectedName(HUDComponent hud){
        if(hud.player.selectedObject.gotEnemy()){
            hud.selectedObjectLabel.setColor(new com.badlogic.gdx.graphics.Color(1,0,0,1));
        }else{
            hud.selectedObjectLabel.setColor(new com.badlogic.gdx.graphics.Color(0,1,0,1));
        }
        hud.selectedObjectLabel.setText(hud.player.selectedObject.getName());
    }

    private void updateResources(HUDComponent hud) {
        hud.resourcesLabel.setText("   " + Integer.toString(hud.player.resources));
    }

    private void updateButtons(HUDComponent hud) {
        Array<ActionComponent> iter = hud.player.selectedObject.getActions().orderedItems();
        int curr = 0;
        for(int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(curr >= iter.size || hud.player.selectedObject.gotEnemy()) {
                    hud.actionButtons.get(i).get(j).setVisible(false);
                    if(hud.actionButtons.get(i).get(j).getListeners().size >= 2)
                        hud.actionButtons.get(i).get(j).getListeners().removeIndex(1);
                    continue;
                }
                ActionComponent action = iter.get(curr++);
                if(!hud.actionButtons.get(i).get(j).getListeners().contains(action.listener, true)){
                    if(hud.actionButtons.get(i).get(j).getListeners().size >= 2)
                        hud.actionButtons.get(i).get(j).getListeners().removeIndex(1);
                    hud.actionButtons.get(i).get(j).addListener(action.listener);
                    hud.actionButtons.get(i).get(j).setVisible(true);
                    hud.actionButtons.get(i).get(j).setStyle(action.button.getStyle());
                }
            }
        }
    }
}
