package com.platformer.ar.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.platformer.ar.*;
import com.platformer.ar.Components.*;
import com.platformer.ar.Components.World.*;


public class PlayerSystem extends EntitySystem{
    ComponentMapper<PlayerComponent> rcm = ComponentMapper.getFor(PlayerComponent.class);
    ComponentMapper<StateComponent> scm = ComponentMapper.getFor(StateComponent.class);
    ComponentMapper<SolidComponent> solidCM = ComponentMapper.getFor(SolidComponent.class);
    ComponentMapper<VelocityComponent> vcm = ComponentMapper.getFor(VelocityComponent.class);
    ComponentMapper<TransformComponent> transformM = ComponentMapper.getFor(TransformComponent.class);
    ComponentMapper<AnimationComponent> am = ComponentMapper.getFor(AnimationComponent.class);
    CameraComponent cameraComponent;
    ScreenDispatcher screenDispatcher;
    Interpolation interploation = Interpolation.elastic;
    Rectangle end;
    SpriteBatch batch;
    long startTime;
    int heartsLost = 0;

    public PlayerSystem(SpriteBatch batch, ScreenDispatcher screenDispatcher, Rectangle end){
        this.screenDispatcher = screenDispatcher;
        this.end = end;
        this.batch = batch;
    }



    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        cameraComponent = ComponentMapper.getFor(CameraComponent.class).get(getEngine().getEntitiesFor(Family.all(CameraComponent.class).get()).first());
        startTime = System.currentTimeMillis();
    }

    @Override
    public void update(float deltaTime) {
        Entity entity = getEngine().getEntitiesFor(Family.all(PlayerComponent.class, VelocityComponent.class).get()).first();
        if(entity == null) return;
        VelocityComponent vc = vcm.get(entity);
        PlayerComponent rc = rcm.get(entity);
        String state = scm.get(entity).get();
        TransformComponent tc = transformM.get(entity);
        if( rc.health <= 0 ||
            tc.position.x < cameraComponent.boundLeftX - cameraComponent.camera.viewportWidth / 2 ||
            tc.position.x > cameraComponent.boundRightX + cameraComponent.camera.viewportWidth / 2 ||
            tc.position.y < cameraComponent.boundBottomY - cameraComponent.camera.viewportHeight / 2 ||
            tc.position.y > cameraComponent.boundTopY + cameraComponent.camera.viewportHeight / 2 ) {
            System.out.println("entre");
            screenDispatcher.win(new GameOverScreen(batch, screenDispatcher));
            return;
        }
        if(solidCM.get(entity).rectangle.overlaps(end)){
            screenDispatcher.win(new VictoryScreen(batch, screenDispatcher, startTime, rc.coins, heartsLost));
        }
        if(vc.pos.y == 0) rc.jump = 0;
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            vc.pos.set(-rc.maxSpeed, vc.pos.y);
            scm.get(entity).set(idleState(vc, scm.get(entity), am.get(entity)));
            vc.pos.x *= isHitting(state) ? 0.5f : 1;
            transformM.get(entity).scale.x *= transformM.get(entity).scale.x < 0 ? 1 : -1;
        }else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            vc.pos.set(rc.maxSpeed, vc.pos.y);
            scm.get(entity).set(idleState(vc, scm.get(entity), am.get(entity)));
            vc.pos.x *= isHitting(state) ? 0.5f : 1;
            transformM.get(entity).scale.x *= transformM.get(entity).scale.x < 0 ? -1 : 1;
        }else{
            scm.get(entity).set(idleState(vc, scm.get(entity), am.get(entity)));
            vc.pos.x = 0f;

        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && rc.jump < rc.maxJumps){
            scm.get(entity).set("JUMP");
            vc.pos.set(vc.pos.x, rc.jumpSpeed);
            rc.jump += 1;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.Z)){
            scm.get(entity).set("SHOOT");
            scm.get(entity).time = 0;
            getEngine().addEntity(buildBullet(transformM.get(entity).scale.x, entity));
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.C)){
            scm.get(entity).set("MELEE");
            scm.get(entity).time = 0;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_LEFT)){
            scm.get(entity).set("SLIDE");
            scm.get(entity).time = 0;
        }
        SolidComponent solidComponent = solidCM.get(entity);
        if(state.equals("SLIDE")) {
            solidComponent.rectangle.height = transformM.get(entity).scale.y * entity.getComponent(TextureComponent.class).region.getRegionWidth() / 2;
            vc.pos.x *= 1.5;
        }else{
            solidComponent.rectangle.height = transformM.get(entity).scale.y * entity.getComponent(TextureComponent.class).region.getRegionWidth();
        }
        if(rc.vulnerability || rc.hittedElapsed >= rc.invulnerabilityTime) {
            rc.vulnerability = true;
            for (Entity e : getEngine().getEntitiesFor(Family.all(SolidComponent.class).get())) {
                if (solidCM.get(e).rectangle.overlaps(solidComponent.rectangle)) {
                    if(e.getComponent(EnemyComponent.class) != null) {
                        rc.vulnerability = false;
                        rc.hittedElapsed = 0;
                        for (int i = 0; i < e.getComponent(EnemyComponent.class).damage; ++i) {
                            getEngine().getSystem(RenderHudSystem.class).damage();
                        }
                        rc.health -= e.getComponent(EnemyComponent.class).damage;
                        heartsLost += e.getComponent(EnemyComponent.class).damage;
                        for (Sound s : Assets.hittedSounds) s.stop();
                        Assets.hittedSounds.random().play();
                        interploation = Interpolation.elastic;
                        break;
                    }else if(e.getComponent(HealthComponent.class) != null && rc.health < rc.maxHealth){
                        getEngine().getSystem(RenderHudSystem.class).heal();
                        rc.health += 1;
                        Assets.potionSound.stop();
                        Assets.potionSound.play();
                        getEngine().removeEntity(e);
                    }else if(e.getComponent(CoinComponent.class) != null){
                        rc.coins += 1;
                        Assets.coinSound.stop();
                        Assets.coinSound.play();
                        getEngine().removeEntity(e);
                    }
                }

            }
        }
        else{
            rc.hittedElapsed += deltaTime;
            tc.fadeOut = interploation.apply(rc.hittedElapsed);
        }
    }

    private boolean isHitting(String state) {
        return  state.length() >= 5 &&
                        (state.substring(state.length()-5).equals("MELEE") ||
                        state.substring(state.length()-5).equals("SHOOT"));
    }

    private String idleState(VelocityComponent vc, StateComponent sc, AnimationComponent ac) {
        if(vc.pos.y != 0){
            if(ac.animations.get(sc.get()).isAnimationFinished(sc.time)){
                sc.set("JUMP");
            }
            String[] res = sc.get().split("_");
            return sc.get().equals("IDLE") || sc.get().equals("SLIDE") || sc.get().substring(0, 4).equals("JUMP") ? sc.get() : "JUMP_" + res[res.length-1];
        }else if(vc.pos.x != 0){
            if(ac.animations.get(sc.get()).isAnimationFinished(sc.time)){
                sc.set("RUNNING");
            }
            String[] res = sc.get().split("_");
            return sc.get().equals("IDLE") || sc.get().equals("JUMP") || sc.get().equals("SLIDE") || sc.get().length() >= 7 && sc.get().substring(0, 7).equals("RUNNING") ? sc.get() : "RUNNING_" + res[res.length-1];
        }else{
            if(ac.animations.get(sc.get()).isAnimationFinished(sc.time)){
                sc.set("IDLE");
            }
            String[] res = sc.get().split("_");
            return  sc.get().length() >= 5 &&
                    (sc.get().substring(sc.get().length()-5).equals("SHOOT") ||
                     sc.get().substring(sc.get().length()-5).equals("MELEE")) ? res[res.length-1] : "IDLE";
        }
    }

    private Entity buildBullet(float scale, Entity shooter){
        TransformComponent tc = shooter.getComponent(TransformComponent.class);
        SolidComponent sc = shooter.getComponent(SolidComponent.class);
        PlayerComponent pc = shooter.getComponent(PlayerComponent.class);
        Entity e = new Entity();
        e.add(new OnTopComponent());
        TransformComponent transformComponent = new TransformComponent();
        transformComponent.position.set(tc.position.cpy().add(sc.rectangle.getWidth() * (tc.scale.x < 0 ? -1 : 1), 0, 0));
        transformComponent.scale.set(scale, scale);
        e.add(transformComponent);
        VelocityComponent vc = new VelocityComponent();
        vc.pos.x = pc.bulletSpeed * (tc.scale.x < 0 ? -1 : 1);
        e.add(new BulletComponent(pc.damage));
        e.add(vc);
        AnimationComponent a = new AnimationComponent();
        a.animations.put("BULLET", Assets.bullet);
        a.animations.put("MUZZLE", Assets.muzzle);
        e.add(a);
        StateComponent stateComponent = new StateComponent();
        stateComponent.set("BULLET");
        e.add(stateComponent);
        TextureComponent textureComponent = new TextureComponent();
        textureComponent.region = a.animations.get(stateComponent.get()).getKeyFrame(0f);
        e.add(textureComponent);
        SolidComponent solidComponent = new SolidComponent(new Rectangle(
                transformComponent.position.x,
                transformComponent.position.y,
                textureComponent.region.getRegionWidth() * scale / 2,
                textureComponent.region.getRegionHeight() * scale > 0 ? textureComponent.region.getRegionHeight() * scale : - textureComponent.region.getRegionHeight() * scale));
        e.add(solidComponent);
        for(Sound s : Assets.shootSounds) s.stop();
        Assets.shootSounds.random().play();
        return e;
    }
}
