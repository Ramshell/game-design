package com.mygdx.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.Components.CameraComponent;
import com.mygdx.game.Components.HUD.HUDComponent;
import com.mygdx.game.Components.PositionComponent;
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
    private String workerScript;
    private String currString = "";
    private int curr;
    private Skin skin;
    private final Play play;
    float r = 0/255f;
    float g = 0/255f;
    float b = 0/255f;


    public IntroCutscene(final Game game, final SpriteBatch batch){
        this.game = game;
        this.batch = batch;
        play = new Play(new Engine(), game);
        stage = new Stage(new FitViewport(1366, 768, play.camera), play.renderer.getBatch());
        skin = AssetsMapper.hudSkin;
        label = new Label("", skin);
        label.setWrap(true);
        label.setWidth(150);
        workerScript = "There must be a good place to setup the basecamp for the company in the nearby, I must hurry.";
        curr = 0;
        label.addAction(CutsceneDialog.forDialog(workerScript, label));
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
        Action moveAction = Actions.run(new Runnable() {
            @Override
            public void run() {
                new MoveAction(14 * 32, 7 * 32, play.mapGraph).act(play.firstWorker);
            }
        });
        stage.addAction(Actions.sequence(
                Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        setSystems(true);
                    }
                }),
                moveAction,
                Actions.delay(3),
                Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        Rectangle r = Mappers.world.get(play.firstWorker).bounds.getRectangle();
                        label.setPosition(r.x, r.y + r.height * 3);
                        stage.addActor(label);
                    }
                }),
                Actions.delay(10),
                Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        new MoveAction(8 * 32, 7 * 32, play.mapGraph).act(play.firstWorker);
                    }
                }),
                Actions.delay(5),
                Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        Rectangle r = Mappers.world.get(play.firstWorker).bounds.getRectangle();
                        label.setPosition(r.x, r.y + r.height * 3);
                        label.setText("");
                        label.addAction(Actions.sequence(
                            CutsceneDialog.forDialog("Oh for the holy Esssence!!", label),
                            CutsceneDialog.forDialog("EoL!!", label),
                            CutsceneDialog.forDialog("This is a good place to set up the basecamp! Hands up!", label)
                        ));
                    }
                }),
                Actions.delay(19),
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

    @Override
    public void render(float delta) {
        float time = delta;
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            time = delta * 3;
        }
        Mappers.position.get(play.engine.getEntitiesFor(Family.all(PositionComponent.class, CameraComponent.class).get()).first())
        .pos.set(play.firstWorker.getComponent(WorldPositionComponent.class).position);
        play.engine.update(time);
        stage.act(time);
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
