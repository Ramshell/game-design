package com.mygdx.game.Builders;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Components.AnimationComponent;
import com.mygdx.game.Components.PlayerComponent;
import com.mygdx.game.Components.WorldObjects.*;
import com.mygdx.game.Components.WorldObjects.Buildings.TryingBuildingComponent;
import com.mygdx.game.Entities.UnitEntity;
import com.mygdx.game.Mappers.AssetsMapper;
import com.mygdx.game.Mappers.ResourceMapper;
import com.mygdx.game.OOP.Actions.*;
import com.mygdx.game.OOP.RTSSound;
import com.mygdx.game.PathfindingUtils.MapGraph;
import com.mygdx.game.Play;

public class HarlandWorkerBuilder extends UnitBuilder{

    public static float BUILD_SPEED = 8;
    public static final float MAX_BUILD_SPEED = 100;
    public static int COST = 90;

    public HarlandWorkerBuilder(Play p, MapGraph mapGraph){
        super(p, mapGraph);
    }

    /**
     * @param posX
     * @param posY both are tile positions
     * **/
    public Entity getWorker(final PlayerComponent player, int posX, int posY){
        WOSoundComponent ws = new WOSoundComponent();
        ws.sounds.put("action", new Array<RTSSound>());
        ws.sounds.put("death", new Array<RTSSound>());
        ws.sounds.put("spawn", new Array<RTSSound>());
        Array<RTSSound> sounds = ws.sounds.get("action");
        sounds.add(new RTSSound(AssetsMapper.harlandWorker1, 3f));
        sounds.add(new RTSSound(AssetsMapper.harlandWorker2, 3f));
        sounds.add(new RTSSound(AssetsMapper.harlandWorker3, 4f));
        sounds.add(new RTSSound(AssetsMapper.harlandWorker4, 5f));
        sounds = ws.sounds.get("death");
        sounds.add(new RTSSound(AssetsMapper.harlandWorkerDeath, 1.8f, RTSSound.STOP_AND_PLAY));
        sounds = ws.sounds.get("spawn");
        sounds.add(new RTSSound(AssetsMapper.harlandWorkerSpawn, 2.2f, RTSSound.STOP_AND_PLAY));

        Array<ActionComponent> actions = new Array<ActionComponent>();
        ActionComponent move = new ActionComponent();
        ActionComponent craft = new ActionComponent();
        ActionComponent shovel = new ActionComponent();
        move.button = AssetsMapper.moveButton;;
        move.listener = new ClickListener(){
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                play.hudComponent.hintTitle.setText("Move");
                play.hudComponent.hintContent.setText("Left click on this button, and then left click over the map to move");
            }
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor){
                play.hudComponent.hintTitle.setText("");
                play.hudComponent.hintContent.setText("");
            }
            public void clicked (InputEvent event, float x, float y) {
                player.action = new ActionBuilder() {
                    @Override
                    public Action<Entity> getAction(float x, float y) {
                        Vector3 v = play.camera.unproject(new Vector3(x, y, 0));
                        return new MoveAction(v.x, v.y, mapGraph);
                    }
                };
            }
        };
        move.key = "move";

        craft.button = AssetsMapper.craftButton;
        craft.listener = new ClickListener(){
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                play.hudComponent.hintTitle.setText("Craft Harlanding Center");
                play.hudComponent.hintCost.setText(String.valueOf(MainBuildingBuilder.COST));
                if(player.resources < MainBuildingBuilder.COST){
                    play.hudComponent.hintCost.setColor(play.hudComponent.redColor);
                }else {
                    play.hudComponent.hintCost.setColor(play.hudComponent.greenColor);
                }
                play.hudComponent.hintContent.setText("Left click on this button, and then left click to choose where you want to build");
            }
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor){
                play.hudComponent.hintTitle.setText("");
                play.hudComponent.hintCost.setText("");
                play.hudComponent.hintContent.setText("");
            }
            public void clicked (InputEvent event, float x, float y) {
                player.state = PlayerComponent.PlayerState.Building;
                player.tryingBuilding = play.mainBuildingBuilder.getWall(player,0,0);
                final TryingBuildingComponent tryingBuildingComponent = new TryingBuildingComponent();
                tryingBuildingComponent.building = play.mainBuildingBuilder.getWall(player,0,0);
                player.selectedObject.getSelectedObjects().first().add(tryingBuildingComponent);
                player.action = new ActionBuilder() {
                    @Override
                    public Action<Entity> getAction(float x, float y) {
                        Vector3 v = play.camera.unproject(new Vector3(x, y, 0));
                        return new CreateBuildingAction(v.x, v.y, play.engine, player, mapGraph);
                    }
                };
            }
        };
        craft.key = "craft";

        shovel.button = AssetsMapper.shovelButton;
        shovel.listener = new ClickListener(){
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                play.hudComponent.hintTitle.setText("Shovel");
                play.hudComponent.hintContent.setText("Click this button, and then click a resource to start gathering");
            }
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor){
                play.hudComponent.hintTitle.setText("");
                play.hudComponent.hintContent.setText("");
            }
            public void clicked (InputEvent event, float x, float y) {
                player.action = new ActionBuilder() {
                    @Override
                    public Action<Entity> getAction(float x, float y) {
                        Vector3 v = play.camera.unproject(new Vector3(x, y, 0));
                        return new ResourceGatheringAction(v.x, v.y, mapGraph, play.engine);
                    }
                };
            }
        };
        shovel.key = "shovel";

        actions.add(move);actions.add(craft);actions.add(shovel);
        AnimationComponent anim = new AnimationComponent();
        anim.animations.put(IDLE, AssetsMapper.harlandWorkerIdleAnim);
        anim.animations.put(MOVE_RIGHT_BOTTOM, AssetsMapper.harlandWorkerMoveRightBottomAnim);
        anim.animations.put(MOVE_LEFT_BOTTOM, AssetsMapper.harlandWorkerMoveLeftBottomAnim);
        anim.animations.put(MOVE_RIGHT_TOP, AssetsMapper.harlandWorkerMoveRightTopAnim);
        anim.animations.put(MOVE_LEFT_TOP, AssetsMapper.harlandWorkerMoveLeftTopAnim);
        anim.animations.put(WATER_GATHERING, AssetsMapper.waterGatheringAnim);
        anim.offsetsX.put(WATER_GATHERING, -26f);
        anim.offsetsY.put(WATER_GATHERING, -6f);
        anim.animations.put(DEAD, AssetsMapper.harlandWorkerDeathAnim);
        anim.offsetsX.put(DEAD, -14f);
        anim.offsetsY.put(DEAD, 0f);
        GatheringPowerComponent gPower = new GatheringPowerComponent();
        gPower.capacity = 50;
        gPower.resourcesPerTick = 2;
        WorldObjectComponent wo = new WorldObjectComponent("Harland Worker");
        wo.bounds = new RectangleMapObject(posX * ResourceMapper.tileWidth, posY * ResourceMapper.tileHeight, 32, 32);
        wo.cost = COST;
        wo.sellValue = 10;
        wo.actions = actions;
        wo.visibility = 384f;
        return new UnitEntity(player, wo, posX, posY,
                IDLE, anim, new HealthComponent(45), id++, play, 512, 60).add(gPower).add(ws);
    }
}
