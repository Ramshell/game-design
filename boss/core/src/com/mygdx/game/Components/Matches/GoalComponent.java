package com.mygdx.game.Components.Matches;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Mappers.AssetsMapper;
import com.mygdx.game.OOP.Conditions.Condition;

public class GoalComponent implements Component {
    public Condition condition;
    public Array<GoalComponent> nextConditions = new Array<GoalComponent>();
    public boolean displayFromBeginning = false;
    public Action
            actionBeforeCompletion = Actions.parallel(
                    Actions.run(new Runnable() {
                        @Override
                        public void run() {
                            AssetsMapper.missionComplete.play();
                        }
                    }),
                    Actions.sequence(Actions.color(Color.GREEN, 1, Interpolation.pow2In), Actions.fadeOut(2, Interpolation.pow2Out))
            ),
            actionBeforeShow = Actions.sequence(
                    Actions.parallel(Actions.color(Color.WHITE),
                            Actions.alpha(0)),
                    Actions.fadeIn(2, Interpolation.pow2In)
            );
}
