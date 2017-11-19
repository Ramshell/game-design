package com.mygdx.game.Systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.mygdx.game.Components.HUD.HUDComponent;
import com.mygdx.game.Components.Matches.GoalComponent;
import com.mygdx.game.Mappers.Mappers;
import com.mygdx.game.Play;

public class GoalSystem extends IntervalSystem{
    private Game matach;
    public static boolean allSatisfy = false;
    public boolean paused = false;
    private HUDComponent hudComponent;

    public GoalSystem(Game match) {
        super(1);
        this.matach = match;
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        hudComponent = Mappers.hud.get(engine.getEntitiesFor(Family.all(HUDComponent.class).get()).first());
    }

    @Override
    protected void updateInterval() {
        allSatisfy = true;
        for (Entity e : getEngine().getEntitiesFor(Family.all(GoalComponent.class).get())) {
            boolean b = Mappers.goalComponentMapper.get(e).condition.satisfied();
            allSatisfy &= b;
            if(b) getEngine().removeEntity(e);
        }
        if(allSatisfy){
            for(EntitySystem system : getEngine().getSystems()){
                system.setProcessing(false);
            }
            paused = true;
            showVictoryDialog();
            getEngine().getSystem(RenderHudSystem.class).setProcessing(true);
            getEngine().getSystem(MapRendererSystem.class).setProcessing(true);
        }
    }

    private void showVictoryDialog() {
        final EntitySystem self = this;
        new Dialog("You won", hudComponent.skin) {

            {
                text("Victory!");
                button("ok");
            }

            @Override
            protected void result(final Object object) {
                System.out.println("Deberia volver a la pantalla de campanias");
                for(EntitySystem system : getEngine().getSystems()) if(!system.equals(self)){
                    system.setProcessing(true);
                }
            }

        }.show(hudComponent.stage);
    }
}
