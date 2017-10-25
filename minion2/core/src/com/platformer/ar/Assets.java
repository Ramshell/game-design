package com.platformer.ar;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Assets{
    public static Animation<TextureRegion> robotIdleAnim, robotRunRightAnim, robotJumpRightAnim,
        robotShootRightAnim, robotShootJumpRightAnim, robotShootRunRightAnim, robotMeleeRightAnim,
        robotJumpMeleeRightAnim, bullet, muzzle, slide;
    public static TextureAtlas robotAtlas;
    public static TextureRegion background, cactusFarAwayBackground, cactusBackground;


    public static void load(){
        robotAtlas = new TextureAtlas("animations/animations.atlas");
        robotIdleAnim = new Animation<TextureRegion>(0.2f, robotAtlas.findRegions("Idle/idle"), Animation.PlayMode.LOOP);
        robotRunRightAnim = new Animation<TextureRegion>(0.1f, robotAtlas.findRegions("Run/Run"), Animation.PlayMode.LOOP);
        robotJumpRightAnim = new Animation<TextureRegion>(0.15f, robotAtlas.findRegions("Jump/Jump"), Animation.PlayMode.LOOP_PINGPONG);
        robotShootRightAnim = new Animation<TextureRegion>(0.2f, robotAtlas.findRegions("Shoot/Shoot"), Animation.PlayMode.NORMAL);
        robotShootJumpRightAnim = new Animation<TextureRegion>(0.2f, robotAtlas.findRegions("JumpShoot/JumpShoot"), Animation.PlayMode.NORMAL);
        robotShootRunRightAnim = new Animation<TextureRegion>(0.2f, robotAtlas.findRegions("RunShoot/RunShoot"), Animation.PlayMode.NORMAL);
        robotMeleeRightAnim = new Animation<TextureRegion>(0.15f, robotAtlas.findRegions("Melee/Melee"), Animation.PlayMode.NORMAL);
        robotJumpMeleeRightAnim = new Animation<TextureRegion>(0.15f, robotAtlas.findRegions("JumpMelee/JumpMelee"), Animation.PlayMode.NORMAL);
        bullet = new Animation<TextureRegion>(0.2f, robotAtlas.findRegions("Objects/Bullet"), Animation.PlayMode.LOOP);
        muzzle = new Animation<TextureRegion>(0.2f, robotAtlas.findRegions("Objects/Muzzle"), Animation.PlayMode.NORMAL);
        slide = new Animation<TextureRegion>(0.1f, robotAtlas.findRegions("Slide/Slide"), Animation.PlayMode.NORMAL);
        background = new TextureRegion(new Texture("map/background/background_transparent.png"));
        cactusFarAwayBackground = new TextureRegion(new Texture("map/background/cactus_background2_transparent.png"));
        cactusBackground = new TextureRegion(new Texture("map/background/cactus_background_transparent.png"));
    }
}
