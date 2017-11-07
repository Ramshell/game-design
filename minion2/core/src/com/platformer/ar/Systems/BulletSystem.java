package com.platformer.ar.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.platformer.ar.Assets;
import com.platformer.ar.Components.*;
import com.platformer.ar.Components.World.BulletComponent;
import com.platformer.ar.Components.World.SolidComponent;
import com.platformer.ar.Components.World.VelocityComponent;
import com.platformer.ar.GameScreen;

public class BulletSystem extends EntitySystem{
    ComponentMapper<BulletComponent> bcm = ComponentMapper.getFor(BulletComponent.class);
    ComponentMapper<StateComponent> scm = ComponentMapper.getFor(StateComponent.class);
    ComponentMapper<VelocityComponent> vcm = ComponentMapper.getFor(VelocityComponent.class);
    ComponentMapper<AnimationComponent> acm = ComponentMapper.getFor(AnimationComponent.class);
    Vector2 aux = new Vector2();


    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
    }

    @Override
    public void update(float deltaTime) {
        for(Entity e : getEngine().getEntitiesFor(
                Family.all(BulletComponent.class,
                        StateComponent.class,
                        VelocityComponent.class).get())){
            if(scm.get(e).get().equals("MUZZLE")){
                if(acm.get(e).animations.get(scm.get(e).get()).isAnimationFinished(scm.get(e).time))
                    getEngine().removeEntity(e);
                continue;
            }
            if(vcm.get(e).pos.x == 0){
                scm.get(e).set("MUZZLE");
                scm.get(e).time = 0;
                continue;
            }
            for(Entity enemy : getEngine().getEntitiesFor(Family.all(EnemyComponent.class).get())){
                SolidComponent solidComponentBullet = e.getComponent(SolidComponent.class);
                SolidComponent solidComponentEnemy = enemy.getComponent(SolidComponent.class);
                if(solidComponentBullet.rectangle.overlaps(solidComponentEnemy.rectangle)){
                    enemy.getComponent(EnemyComponent.class).shoots -= 1;
                    if(enemy.getComponent(EnemyComponent.class).shoots == 0){
                        double chance = Math.random();
                        getEngine().addEntity(new Entity().add(new ParticleComponent(Assets.getExplosion(solidComponentEnemy.rectangle.getCenter(aux).x, solidComponentEnemy.rectangle.getCenter(aux).y))));
                        if(chance < 0.8){
                            Assets.dropSound.stop();
                            Assets.dropSound.play();
                            getEngine().addEntity(Math.random() < 0.5 ?
                                    GameScreen.buildHealthPotion(solidComponentEnemy.rectangle.getCenter(aux).x, solidComponentEnemy.rectangle.getCenter(aux).y) :
                                    GameScreen.buildCoin(solidComponentEnemy.rectangle.getCenter(aux).x, solidComponentEnemy.rectangle.getCenter(aux).y));
                        }
                        Assets.deathSound.stop();
                        long id = Assets.deathSound.play(0.3f);
                        getEngine().removeEntity(enemy);
                    }
                    vcm.get(e).pos.setZero();
                    scm.get(e).set("MUZZLE");
                    scm.get(e).time = 0;
                }
            }
        }
    }
}
