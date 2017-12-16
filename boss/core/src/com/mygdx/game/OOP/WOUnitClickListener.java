package com.mygdx.game.OOP;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Components.PlayerComponent;
import com.mygdx.game.OOP.Actions.Action;
import com.mygdx.game.OOP.Actions.ActionBuilder;
import com.mygdx.game.OOP.Actions.MoveAction;
import com.mygdx.game.Play;

public class WOUnitClickListener extends ClickListener{
    private final PlayerComponent player;
    String name, hint, cost;
    final Play play;
    Runnable runnable;

    public WOUnitClickListener(String name, String hint, String cost, Play play, PlayerComponent playerComponent){
       this.name = name;
       this.hint = hint;
       this.cost = cost;
       this.play = play;
       this.player = playerComponent;
    }

    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
        play.hudComponent.hintTitle.setText(name);
        play.hudComponent.hintContent.setText(hint);
        play.hudComponent.hintCost.setText(cost);
    }
    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor){
        play.hudComponent.hintTitle.setText("");
        play.hudComponent.hintContent.setText("");
        play.hudComponent.hintCost.setText("");
    }
    public void clicked (InputEvent event, float x, float y) {
        player.action = new ActionBuilder() {
            @Override
            public Action<Entity> getAction(float x, float y) {
                Vector3 v = play.camera.unproject(new Vector3(x, y, 0));
                return new MoveAction(v.x, v.y, play.mapGraph);
            }
        };
    }
}
