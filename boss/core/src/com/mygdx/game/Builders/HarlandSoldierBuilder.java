package com.mygdx.game.Builders;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector3;
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
import com.mygdx.game.OOP.Actions.Action;
import com.mygdx.game.OOP.Actions.ActionBuilder;
import com.mygdx.game.OOP.Actions.MoveAction;
import com.mygdx.game.PathfindingUtils.MapGraph;
import com.mygdx.game.Play;

public class HarlandSoldierBuilder extends UnitBuilder{
    public static int COST = 150;

    public HarlandSoldierBuilder(Play play, MapGraph mapGraph) {
        super(play, mapGraph);
    }

    /**
     * @param posX
     * @param posY both are tile positions
     * **/
    public Entity getSoldier(final PlayerComponent player, int posX, int posY){
        WOSoundComponent ws = new WOSoundComponent();
        ws.sounds.add(AssetsMapper.harlandSoldier1);
        ws.sounds.add(AssetsMapper.harlandSoldier2);

        Array<ActionComponent> actions = new Array<ActionComponent>();
        ActionComponent move = new ActionComponent();
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
        actions.add(move);
        AnimationComponent anim = new AnimationComponent();
        anim.animations.put(IDLE, AssetsMapper.harlandSoldierIdleAnim);
        anim.animations.put(MOVE_RIGHT_BOTTOM, AssetsMapper.harlandSoldierMoveRightBottomAnim);
        anim.animations.put(MOVE_LEFT_BOTTOM, AssetsMapper.harlandSoldierMoveLeftBottomAnim);
        anim.animations.put(MOVE_RIGHT_TOP, AssetsMapper.harlandSoldierMoveRightTopAnim);
        anim.animations.put(MOVE_LEFT_TOP, AssetsMapper.harlandSoldierMoveLeftTopAnim);
        anim.animations.put(ATTACKING_RIGHT_BOTTOM, AssetsMapper.harlandSoldierAttackRightBottomAnim);
        anim.offsetsX.put(ATTACKING_RIGHT_BOTTOM, -14f);
        anim.animations.put(ATTACKING_LEFT_BOTTOM, AssetsMapper.harlandSoldierAttackLeftBottomAnim);
        anim.offsetsX.put(ATTACKING_LEFT_BOTTOM, -14f);
        anim.animations.put(ATTACKING_RIGHT_TOP, AssetsMapper.harlandSoldierAttackRightTopAnim);
        anim.offsetsX.put(ATTACKING_RIGHT_TOP, -14f);
        anim.animations.put(ATTACKING_LEFT_TOP, AssetsMapper.harlandSoldierAttackLeftTopAnim);
        anim.offsetsX.put(ATTACKING_LEFT_TOP, -14f);
        WorldObjectComponent wo = new WorldObjectComponent("Harland Soldier");
        wo.bounds = new RectangleMapObject(posX * ResourceMapper.tileWidth, posY * ResourceMapper.tileHeight, 32, 32);
        wo.cost = COST;
        wo.sellValue = 25;
        wo.actions = actions;
        RangedWeaponComponent rangedWeaponComponent = new RangedWeaponComponent();
        rangedWeaponComponent.range = new Circle(0, 0, 64);
        rangedWeaponComponent.attackDuration = 0.6f;
        rangedWeaponComponent.attackSpeed = 1;
        rangedWeaponComponent.damage = 5;
        rangedWeaponComponent.minDamage = 3;
        rangedWeaponComponent.maxDamage = 10;
        return new UnitEntity(player, wo, posX, posY,
                IDLE, anim, new HealthComponent(70), id++)
                .add(rangedWeaponComponent).add(ws);
    }
}
