package com.mygdx.game.Systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.game.Components.HUD.HUDComponent;
import com.mygdx.game.Components.Matches.DefeatComponent;
import com.mygdx.game.Components.Matches.GoalComponent;
import com.mygdx.game.Components.PlayerComponent;
import com.mygdx.game.Mappers.Mappers;
import com.mygdx.game.OOP.Conditions.Condition;

public class DefeatSystem extends IntervalSystem {
    Game game;
    HUDComponent hudComponent;

    public DefeatSystem(Game game) {
        super(1);
        this.game = game;
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        hudComponent = Mappers.hud.get(engine.getEntitiesFor(Family.all(HUDComponent.class).get()).first());
    }

    @Override
    protected void updateInterval() {
        for (Entity e : getEngine().getEntitiesFor(Family.all(DefeatComponent.class).get())) {
            if(Mappers.defeatComponentMapper.get(e).condition.satisfied()){
                for(EntitySystem system : getEngine().getSystems()){
                    system.setProcessing(false);
                }
                showDefeatDialog(Mappers.defeatComponentMapper.get(e).condition);
                getEngine().getSystem(RenderHudSystem.class).setProcessing(true);
                getEngine().getSystem(MapRendererSystem.class).setProcessing(true);
                break;
            }
        }
    }

    private void showDefeatDialog(final Condition condition) {
        final EntitySystem that = this;
        new Dialog("You lose", hudComponent.skin) {

            {
                text(new Label(condition.getDescription(), hudComponent.skin));
                button("ok");
            }

            @Override
            protected void result(final Object object) {
                System.out.println("Deberia volver a la pantalla de campanias");
                for(EntitySystem system : getEngine().getSystems()) if(!system.equals(that)){
                    system.setProcessing(true);
                }
            }

        }.show(hudComponent.stage);
    }
}
