package com.mygdx.game.Builders;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Components.AnimationComponent;
import com.mygdx.game.Components.Combat.RangedWeaponComponent;
import com.mygdx.game.Components.PlayerComponent;
import com.mygdx.game.Components.WorldObjects.ActionComponent;
import com.mygdx.game.Components.WorldObjects.HealthComponent;
import com.mygdx.game.Components.WorldObjects.WOSoundComponent;
import com.mygdx.game.Components.WorldObjects.WorldObjectComponent;
import com.mygdx.game.Entities.UnitEntity;
import com.mygdx.game.Mappers.AssetsMapper;
import com.mygdx.game.Mappers.ResourceMapper;
import com.mygdx.game.OOP.Actions.*;
import com.mygdx.game.PathfindingUtils.MapGraph;
import com.mygdx.game.Play;

public class HarlandAmablesFlattererBuilder extends UnitBuilder{
    public static final float BUILD_SPEED = 6;
    public static final float MAX_BUILD_SPEED = 100;
    public static int COST = 180;

    public HarlandAmablesFlattererBuilder(Play play, MapGraph mapGraph) {
        super(play, mapGraph);
    }

    /**
     * @param posX
     * @param posY both are tile positions
     * **/
    public Entity getAmablesFlatterer(final PlayerComponent player, int posX, int posY){
        WOSoundComponent ws = new WOSoundComponent();
        ws.sounds.put("action", new Array<Sound>());
        ws.sounds.put("attack", new Array<Sound>());
        Array<Sound> sounds = ws.sounds.get("action");
        sounds.add(AssetsMapper.harlandSoldier1);
        sounds.add(AssetsMapper.harlandSoldier2);
        sounds = ws.sounds.get("attack");
        sounds.add(AssetsMapper.harlandAmablesFlattererAttack1);
        sounds.add(AssetsMapper.harlandAmablesFlattererAttack2);
        sounds.add(AssetsMapper.harlandAmablesFlattererAttack3);

        Array<ActionComponent> actions = new Array<ActionComponent>();
        ActionComponent move = new ActionComponent();
        ActionComponent attack = new ActionComponent();
        ActionComponent patrol = new ActionComponent();
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

        attack.button = AssetsMapper.attackButton;
        attack.listener = new ClickListener(){
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                play.hudComponent.hintTitle.setText("Attack");
                play.hudComponent.hintContent.setText("Left click on this button, and then left click over an enemy to attack it");
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
                        return new AttackAction(v.x, v.y, mapGraph, player);
                    }
                };
            }
        };
        attack.key = "attack";

        patrol.button = AssetsMapper.patrolButton;
        patrol.listener = new ClickListener(){
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                play.hudComponent.hintTitle.setText("Patrol");
                play.hudComponent.hintContent.setText("Left click on this button, and then left click over the map to to patrol from your current position to the selected one");
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
                        return new PatrolAction(v.x, v.y);
                    }
                };
            }
        };
        patrol.key = "patrol";

        actions.add(move);actions.add(attack);actions.add(patrol);
        AnimationComponent anim = new AnimationComponent();
        anim.animations.put(IDLE, AssetsMapper.harlandAmablesFlattererIdleAnim);
        anim.offsetsX.put(IDLE, -14f);
        anim.animations.put(MOVE_RIGHT_BOTTOM, AssetsMapper.harlandAmablesFlattererMoveRightBottomAnim);
        anim.offsetsX.put(MOVE_RIGHT_BOTTOM, -14f);
        anim.animations.put(MOVE_LEFT_BOTTOM, AssetsMapper.harlandAmablesFlattererMoveLeftBottomAnim);
        anim.offsetsX.put(MOVE_LEFT_BOTTOM, -14f);
        anim.animations.put(MOVE_RIGHT_TOP, AssetsMapper.harlandAmablesFlattererMoveRightTopAnim);
        anim.offsetsX.put(MOVE_RIGHT_TOP, -14f);
        anim.animations.put(MOVE_LEFT_TOP, AssetsMapper.harlandAmablesFlattererMoveLeftTopAnim);
        anim.offsetsX.put(MOVE_LEFT_TOP, -14f);
        anim.animations.put(ATTACKING_RIGHT_BOTTOM, AssetsMapper.harlandAmablesFlattererAttackRightBottomAnim);
        anim.offsetsX.put(ATTACKING_RIGHT_BOTTOM, -14f);
        anim.animations.put(ATTACKING_LEFT_BOTTOM, AssetsMapper.harlandAmablesFlattererAttackLeftBottomAnim);
        anim.offsetsX.put(ATTACKING_LEFT_BOTTOM, -14f);
        anim.animations.put(ATTACKING_RIGHT_TOP, AssetsMapper.harlandAmablesFlattererAttackRightTopAnim);
        anim.offsetsX.put(ATTACKING_RIGHT_TOP, -14f);
        anim.animations.put(ATTACKING_LEFT_TOP, AssetsMapper.harlandAmablesFlattererAttackLeftTopAnim);
        anim.offsetsX.put(ATTACKING_LEFT_TOP, -14f);
        anim.animations.put(DEAD, AssetsMapper.harlandAmablesFlattererDeathAnim);
        anim.offsetsX.put(DEAD, -14f);
        anim.offsetsY.put(DEAD, 0f);
        WorldObjectComponent wo = new WorldObjectComponent("Harland Amable's Flatterer");
        wo.bounds = new RectangleMapObject(posX * ResourceMapper.tileWidth, posY * ResourceMapper.tileHeight, 32, 32);
        wo.cost = COST;
        wo.sellValue = 25;
        wo.actions = actions;
        wo.visibility = 416f;
        RangedWeaponComponent rangedWeaponComponent = new RangedWeaponComponent();
        rangedWeaponComponent.range = new Circle(0, 0, 32);
        rangedWeaponComponent.visionRange = new Circle(0, 0, 128);
        rangedWeaponComponent.attackDuration = 0.5f;
        rangedWeaponComponent.attackSpeed = 0.6f;
        rangedWeaponComponent.minDamage = 1;
        rangedWeaponComponent.maxDamage = 6;
        return new UnitEntity(player, wo, posX, posY,
                IDLE, anim, new HealthComponent(100), id++, play, 416f, 90)
                .add(rangedWeaponComponent).add(ws);
    }
}

