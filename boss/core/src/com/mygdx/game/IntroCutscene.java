package com.mygdx.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.Components.CameraComponent;
import com.mygdx.game.Components.HUD.HUDComponent;
import com.mygdx.game.Components.PositionComponent;
import com.mygdx.game.Components.VelocityComponent;
import com.mygdx.game.Components.WorldObjects.WorldPositionComponent;
import com.mygdx.game.Mappers.AssetsMapper;
import com.mygdx.game.Mappers.Mappers;
import com.mygdx.game.OOP.Actions.MoveAction;
import com.mygdx.game.OOP.CutsceneDialog;
import com.mygdx.game.Systems.*;
import com.mygdx.game.Systems.Fog.FogSystem;
import javafx.util.Pair;

import java.util.Map;

public class IntroCutscene extends ScreenAdapter {
    private final Game game;
    private SpriteBatch batch;
    private Stage stage;
    private final Label label;
    private Skin skin;
    private final Play play;
    float r = 0/255f;
    float g = 0/255f;
    float b = 0/255f;
    Array<Sound> steps = new Array<Sound>();
    private Sound step1, step2, step3, step4, step5, step6, step7, step8, step9,
    hurry, i_must_hurry, i_should, for_the_holy, eol, this_is_a_g_spot;


    public IntroCutscene(final Game game, final SpriteBatch batch){
        this.game = game;
        this.batch = batch;
        play = new Play(new Engine(), game);
        stage = new Stage(new FitViewport(1366, 768, play.camera), play.renderer.getBatch());
        skin = AssetsMapper.hudSkin;
        loadSounds();
        label = new Label("", skin);
        label.setWrap(true);
        label.setWidth(150);
        label.addAction(Actions.sequence(
            Actions.parallel(
                CutsceneDialog.forDialog("Hurry...", label, 0.01f, 0, 0.5f),
                playSoundAction(hurry)
            ),
            Actions.parallel(
                CutsceneDialog.forDialog("I must..... Hurry", label, 0.01f, 0, 0.75f),
                playSoundAction(i_must_hurry)
            ),
            Actions.parallel(
                CutsceneDialog.forDialog("I should find a good spot in the nearby to setup a basecamp", label, 0.01f, 0, 2f),
                playSoundAction(i_should)
            )
        ));
    }

    private Action playSoundAction(final Sound sound) {
        return Actions.sequence(
                Actions.delay(0.6f),
                Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        sound.play();
                    }
                })
        );
    }

    private void loadSounds() {
        step1 = Gdx.audio.newSound(Gdx.files.internal("soundtrack/cutscenes/1/snow_step_wet-01.wav"));
        step2 = Gdx.audio.newSound(Gdx.files.internal("soundtrack/cutscenes/1/snow_step_wet-02.wav"));
        step3 = Gdx.audio.newSound(Gdx.files.internal("soundtrack/cutscenes/1/snow_step_wet-03.wav"));
        step4 = Gdx.audio.newSound(Gdx.files.internal("soundtrack/cutscenes/1/snow_step_wet-04.wav"));
        step5 = Gdx.audio.newSound(Gdx.files.internal("soundtrack/cutscenes/1/snow_step_wet-05.wav"));
        step6 = Gdx.audio.newSound(Gdx.files.internal("soundtrack/cutscenes/1/snow_step_wet-06.wav"));
        step7 = Gdx.audio.newSound(Gdx.files.internal("soundtrack/cutscenes/1/snow_step_wet-07.wav"));
        step8 = Gdx.audio.newSound(Gdx.files.internal("soundtrack/cutscenes/1/snow_step_wet-08.wav"));
        step9 = Gdx.audio.newSound(Gdx.files.internal("soundtrack/cutscenes/1/snow_step_wet-09.wav"));
        steps.add(step1);
        steps.add(step2);
        steps.add(step3);
        steps.add(step4);
        steps.add(step5);
        steps.add(step6);
        steps.add(step7);
        steps.add(step8);
        steps.add(step9);
        hurry = Gdx.audio.newSound(Gdx.files.internal("soundtrack/cutscenes/1/hurry.wav"));
        i_must_hurry = Gdx.audio.newSound(Gdx.files.internal("soundtrack/cutscenes/1/i_must_hurry.wav"));
        i_should = Gdx.audio.newSound(Gdx.files.internal("soundtrack/cutscenes/1/i_should.wav"));
        for_the_holy = Gdx.audio.newSound(Gdx.files.internal("soundtrack/cutscenes/1/for_the_holy.wav"));
        eol = Gdx.audio.newSound(Gdx.files.internal("soundtrack/cutscenes/1/eol.wav"));
        this_is_a_g_spot = Gdx.audio.newSound(Gdx.files.internal("soundtrack/cutscenes/1/this_is_a_g_spot.wav"));
    }

    private void setSystems(boolean b){
        play.engine.getSystem(SetUpSystem.class).setProcessing(b);
        play.engine.getSystem(MapRendererSystem.class).setProcessing(b);
        play.engine.getSystem(CameraSystem.class).setProcessing(b);
        play.engine.getSystem(MovementSystem.class).setProcessing(b);
        play.engine.getSystem(UnitsVelocitySystem.class).setProcessing(b);
        play.engine.getSystem(CollisionSystem.class).setProcessing(b);
        play.engine.getSystem(UnitStateSystem.class).setProcessing(b);
        play.engine.getSystem(UnitsMovementSystem.class).setProcessing(b);
        play.engine.getSystem(AnimationSystem.class).setProcessing(b);
        play.engine.getSystem(StateSystem.class).setProcessing(b);
        play.engine.getSystem(MapGraphUpdaterSystem.class).setProcessing(b);
        play.engine.getSystem(WOSoundSystem.class).setProcessing(b);
        play.engine.getSystem(FogSystem.class).setProcessing(b);
    }

    @Override
    public void show() {
        for(EntitySystem es : play.engine.getSystems()){
            es.setProcessing(false);
        }
        setSystems(true);
        Mappers.position.get(play.engine.getEntitiesFor(Family.all(PositionComponent.class, CameraComponent.class).get()).first())
                .pos.set(play.firstWorker.getComponent(WorldPositionComponent.class).position);
        Action moveAction = Actions.run(new Runnable() {
            @Override
            public void run() {
                new MoveAction(play.worker_2.x * 32, play.worker_2.y * 32, play.mapGraph).act(play.firstWorker);
            }
        });
        stage.addAction(Actions.sequence(
                Actions.parallel(
                        moveAction,
                        stepsAction(0.01f, 0.32f, 20),
                        Actions.run(new Runnable() {
                            @Override
                            public void run() {
                                stage.addActor(label);
                            }
                        })

                ),
                Actions.delay(7),
                Actions.parallel(
                        Actions.run(new Runnable() {
                            @Override
                            public void run() {
                                new MoveAction(play.worker_3.x * 32, play.worker_3.y * 32, play.mapGraph).act(play.firstWorker);
                            }
                        }),
                        stepsAction(0.01f, 0.32f, 16),
                        Actions.run(new Runnable() {
                            @Override
                            public void run() {
                                label.addAction(Actions.sequence(
                                        Actions.parallel(
                                                CutsceneDialog.forDialog("Oh for the holy Esssence!!", label, 0.01f, 0, 1.5f),
                                                playSoundAction(for_the_holy)
                                        ),
                                        Actions.parallel(
                                                CutsceneDialog.forDialog("EoL!!", label,0.01f, 0, 1f),
                                                playSoundAction(eol)
                                        ),
                                        Actions.parallel(
                                                CutsceneDialog.forDialog("This is a good one to setup it! Heads up!", label),
                                                playSoundAction(this_is_a_g_spot)
                                        )
                                ));
                            }
                        })
                ),
                Actions.delay(5),
                Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        Mappers.hud.get(play.engine.getEntitiesFor(Family.all(HUDComponent.class).get()).first()).stage.addAction(
                                Actions.sequence(Actions.alpha(0), Actions.fadeIn(2))
                        );
                    }
                }),
                Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        game.setScreen(play);
                    }
                })
        ));
    }

    private Action stepsAction(float firstDelay, float betweenSteps, int count) {
        return Actions.sequence(
                Actions.delay(firstDelay),
                Actions.repeat(count, Actions.sequence(
                        Actions.run(new Runnable() {
                            @Override
                            public void run() {
                                steps.random().play();
                            }
                        }),
                        Actions.delay(betweenSteps)
                ))
        );
    }

    @Override
    public void render(float delta) {
        float time = delta;
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            time = delta * 3;
        }
        Mappers.position.get(play.engine.getEntitiesFor(Family.all(PositionComponent.class, CameraComponent.class).get()).first())
                .pos.set(play.firstWorker.getComponent(WorldPositionComponent.class).position);
        Rectangle r = Mappers.world.get(play.firstWorker).bounds.getRectangle();
        label.setPosition(r.x, r.y + r.height * 3);
        stage.act(time);
        play.engine.update(time);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        play.camera.viewportHeight = height;
        play.camera.viewportWidth = width;
        play.camera.update();
    }

    @Override
    public void hide() {
        for(EntitySystem es : play.engine.getSystems()){
            es.setProcessing(false);
        }
        Gdx.input.setInputProcessor(null);
        AssetsMapper.pauseMusic();
    }

    @Override
    public void dispose() {
        play.map.dispose();
        play.renderer.dispose();
        play.rayHandler.dispose();
        Gdx.input.setInputProcessor(null);
    }
}
