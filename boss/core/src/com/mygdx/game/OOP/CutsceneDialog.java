package com.mygdx.game.OOP;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class CutsceneDialog {
    public static Action forDialog(final String s, final Label label){
        final MutableString currString = new MutableString("");
        final MutableInt curr = new MutableInt(0);
        float speedTransition = 0.01f;
        SequenceAction res = Actions.sequence();
        res.addAction(Actions.alpha(0));
        res.addAction(Actions.fadeIn(1, Interpolation.pow5));
        String[] separatedByComma = s.split(",");
        if(separatedByComma.length == 1){
            res.addAction(Actions.repeat(separatedByComma[separatedByComma.length-1].length(),
                    Actions.sequence(
                            Actions.run(new Runnable() {
                                @Override
                                public void run() {
                                    currString.concat(String.valueOf(s.charAt(curr.i)));
                                    curr.add();
                                    label.setText(currString.s);
                                }
                            }),
                            Actions.delay(speedTransition)
                    )));
        }
        for(int i = 0; i < separatedByComma.length - 1 ; ++i){
            res.addAction(Actions.sequence(Actions.repeat(separatedByComma[i].length(), Actions.sequence(
                    Actions.run(new Runnable() {
                        @Override
                        public void run() {
                            currString.concat(String.valueOf(s.charAt(curr.i)));
                            curr.add();
                            label.setText(currString.s);
                        }
                    }),
                    Actions.delay(speedTransition)
            )),
            Actions.run(new Runnable() {
                @Override
                public void run() {
                    label.setText(label.getText() + ",");
                    currString.concat(",");
                    curr.add();
                }
            }),
            Actions.delay(0.5f)));
        }
        if(separatedByComma.length > 1){
            res.addAction(Actions.repeat(separatedByComma[separatedByComma.length-1].length(),
                    Actions.sequence(
                            Actions.run(new Runnable() {
                                @Override
                                public void run() {
                                    currString.concat(String.valueOf(s.charAt(curr.i)));
                                    curr.add();
                                    label.setText(currString.s);
                                }
                            }),
                            Actions.delay(speedTransition)
                    )));
        }
        res.addAction(Actions.delay(3));
        res.addAction(Actions.fadeOut(1, Interpolation.pow5));
        res.addAction(Actions.run(new Runnable() {
            @Override
            public void run() {
                label.setText("");
            }
        }));
        return res;
    }
}
