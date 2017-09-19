package com.mygdx.game.Builders;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Components.AnimationComponent;
import com.mygdx.game.Components.PlayerComponent;
import com.mygdx.game.Components.WorldObjects.ActionComponent;
import com.mygdx.game.Components.WorldObjects.GatheringPowerComponent;
import com.mygdx.game.Entities.UnitEntity;
import com.mygdx.game.Mappers.AssetsMapper;
import com.mygdx.game.OOP.Actions.*;
import com.mygdx.game.PathfindingUtils.MapGraph;
import com.mygdx.game.Play;

public class HarlandWorkerBuilder{
    public static int IDLE = 1;
    public static int MOVE_RIGHT_BOTTOM = 2;
    public static int MOVE_LEFT_BOTTOM = 3;
    public static int MOVE_RIGHT_TOP = 4;
    public static int MOVE_LEFT_TOP = 5;
    public static int WATER_GATHERING = 6;
    private Play play;
    private MapGraph mapGraph;

    public HarlandWorkerBuilder(Play p, MapGraph mapGraph){
        this.play = p;
        this.mapGraph = mapGraph;
    }

    /**
     * @param posX
     * @param posY both are tile positions
     * **/
    public Entity getWorker(final PlayerComponent player, int posX, int posY){
        Array<ActionComponent> actions = new Array<ActionComponent>();
        ActionComponent move = new ActionComponent();
        ActionComponent craft = new ActionComponent();
        ActionComponent shovel = new ActionComponent();
        move.button = AssetsMapper.moveButton;;
        move.listener = new ClickListener(){
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

        craft.button = AssetsMapper.craftButton;
        craft.listener = new ClickListener(){
            public void clicked (InputEvent event, float x, float y) {
                player.state = PlayerComponent.PlayerState.Building;
                player.tryingBuilding = play.wallBuilder.getWall(player,0,0);
                player.action = new ActionBuilder() {
                    @Override
                    public Action<Entity> getAction(float x, float y) {
                        Vector3 v = play.camera.unproject(new Vector3(x, y, 0));
                        return new CreateBuildingAction(v.x, v.y, play.engine, player, mapGraph);
                    }
                };
            }
        };

        shovel.button = AssetsMapper.shovelButton;
        shovel.listener = new ClickListener(){
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
        actions.add(move);actions.add(craft);actions.add(shovel);
        AnimationComponent anim = new AnimationComponent();
        anim.animations.put(IDLE, AssetsMapper.harlandWorkerIdleAnim);
        anim.animations.put(MOVE_RIGHT_BOTTOM, AssetsMapper.harlandWorkerMoveRightBottomAnim);
        anim.animations.put(MOVE_LEFT_BOTTOM, AssetsMapper.harlandWorkerMoveLeftBottomAnim);
        anim.animations.put(MOVE_RIGHT_TOP, AssetsMapper.harlandWorkerMoveRightTopAnim);
        anim.animations.put(MOVE_LEFT_TOP, AssetsMapper.harlandWorkerMoveLeftTopAnim);
        anim.animations.put(WATER_GATHERING, AssetsMapper.waterGatheringAnim);
        GatheringPowerComponent gPower = new GatheringPowerComponent();
        gPower.capacity = 50;
        gPower.resourcesPerTick = 5;
        return new UnitEntity(player, "Harland Worker", posX, posY,
                32, 32, IDLE, anim, actions).add(gPower);
    }
}
