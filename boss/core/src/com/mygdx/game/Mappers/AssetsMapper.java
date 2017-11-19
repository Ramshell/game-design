package com.mygdx.game.Mappers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class AssetsMapper {
    public static Pixmap nm;
    public static Pixmap rm;
    public static Pixmap bm;
    public static Pixmap lm;
    public static Pixmap tm;

    public static Texture harlandWorker;
    public static Animation<TextureRegion> harlandWorkerIdleAnim;
    public static Animation<TextureRegion> harlandWorkerMoveRightBottomAnim;
    public static Animation<TextureRegion> harlandWorkerMoveLeftBottomAnim;
    public static Animation<TextureRegion> harlandWorkerMoveRightTopAnim;
    public static Animation<TextureRegion> harlandWorkerMoveLeftTopAnim;
    public static Animation<TextureRegion> harlandWorkerDeathAnim;

    public static Texture harlandSoldier;
    public static Animation<TextureRegion> harlandSoldierIdleAnim;
    public static Animation<TextureRegion> harlandSoldierMoveRightBottomAnim;
    public static Animation<TextureRegion> harlandSoldierMoveLeftBottomAnim;
    public static Animation<TextureRegion> harlandSoldierMoveRightTopAnim;
    public static Animation<TextureRegion> harlandSoldierMoveLeftTopAnim;
    public static Animation<TextureRegion> harlandSoldierAttackRightBottomAnim;
    public static Animation<TextureRegion> harlandSoldierAttackLeftBottomAnim;
    public static Animation<TextureRegion> harlandSoldierAttackRightTopAnim;
    public static Animation<TextureRegion> harlandSoldierAttackLeftTopAnim;
    public static Animation<TextureRegion> harlandSoldierDeathAnim;


    public static Texture harlandAmablesFlatterer;
    public static Animation<TextureRegion> harlandAmablesFlattererIdleAnim;
    public static Animation<TextureRegion> harlandAmablesFlattererMoveRightBottomAnim;
    public static Animation<TextureRegion> harlandAmablesFlattererMoveLeftBottomAnim;
    public static Animation<TextureRegion> harlandAmablesFlattererMoveRightTopAnim;
    public static Animation<TextureRegion> harlandAmablesFlattererMoveLeftTopAnim;
    public static Animation<TextureRegion> harlandAmablesFlattererAttackRightBottomAnim;
    public static Animation<TextureRegion> harlandAmablesFlattererAttackLeftBottomAnim;
    public static Animation<TextureRegion> harlandAmablesFlattererAttackRightTopAnim;
    public static Animation<TextureRegion> harlandAmablesFlattererAttackLeftTopAnim;
    public static Animation<TextureRegion> harlandAmablesFlattererDeathAnim;


    public static Texture eol;
    public static Texture waterGathering;
    public static Animation<TextureRegion> eolIdleAnim;
    public static Skin hudSkin;
    public static Texture unitDamage;
    public static Animation<TextureRegion> unitDamageAnim;
    public static Texture moveAction;
    public static Texture moveActionDown;
    public static Texture craftAction;
    public static Texture craftActionDown;
    public static Texture attackAction;
    public static Texture attackActionDown;
    public static Texture patrolAction;
    public static Texture patrolActionDown;
    public static Texture craftHarlandWorkerAction;
    public static Texture craftHarlandWorkerActionDown;
    public static Texture craftHarlandSoldierAction;
    public static Texture craftHarlandSoldierActionDown;
    public static Texture shovelAction;
    public static Texture shovelActionDown;
    public static ImageButton moveButton, attackButton, patrolButton;
    public static ImageButton craftButton;
    public static ImageButton craftHarlandWorkerButton, craftHarlandSoldierButton;
    public static ImageButton shovelButton;
    public static Animation<TextureRegion> waterGatheringAnim;

    //sound assets
    public static Music harlandDesertBackground;
    public static Sound harlandWorker1;
    public static Sound harlandWorker2;
    public static Sound harlandWorker3;
    public static Sound harlandWorker4;
    public static Sound harlandSoldier1;
    public static Sound harlandSoldier2;
    public static Sound harlandSoldierShoot1, harlandSoldierShoot2, harlandSoldierShoot3;
    public static Sound harlandAmablesFlattererAttack1, harlandAmablesFlattererAttack2, harlandAmablesFlattererAttack3;
    public static Skin damageSkin;
    public static Skin tracerSkin;
    public static Texture craftHarlandAmablesFlattererAction;
    public static Texture craftHarlandAmablesFlattererActionDown;
    public static ImageButton craftHarlandAmablesFlattererButton;


    public static Texture loadTexture (String file) {
        return new Texture(Gdx.files.internal(file));
    }

    public static void load () {
        hudSkin = new Skin(Gdx.files.internal("HUD/skins/gdx-skins-master/shade/skin/uiskin.json"));
        damageSkin = new Skin(Gdx.files.internal("HUD/skins/gdx-skins-master/pixthulhu/skin/pixthulhu-ui.json"));
        tracerSkin = new Skin(Gdx.files.internal("HUD/skins/gdx-skins-master/flat-earth/skin/flat-earth-ui.json"));
        nm = new Pixmap(Gdx.files.internal("cursor/normal_pointer.png"));
        rm = new Pixmap(Gdx.files.internal("cursor/right_movement.png"));
        bm = new Pixmap(Gdx.files.internal("cursor/bottom_movement.png"));
        lm = new Pixmap(Gdx.files.internal("cursor/left_movement.png"));
        tm = new Pixmap(Gdx.files.internal("cursor/top_movement.png"));
        harlandWorker = loadTexture("characters/harland_worker.png");
        harlandWorkerIdleAnim = new Animation<TextureRegion>(0.8f,
                new TextureRegion(harlandWorker, 14, 0, 36, 64),
                new TextureRegion(harlandWorker, 78, 0, 36, 64));
        harlandWorkerMoveLeftBottomAnim = new Animation<TextureRegion>(0.2f,
                new TextureRegion(harlandWorker, 14, 64, 36, 64),
                new TextureRegion(harlandWorker, 78, 64, 36, 64),
                new TextureRegion(harlandWorker, 142, 64, 36, 64),
                new TextureRegion(harlandWorker, 206, 64, 36, 64),
                new TextureRegion(harlandWorker, 270, 64, 36, 64),
                new TextureRegion(harlandWorker, 334, 64, 36, 64));
        harlandWorkerMoveRightBottomAnim = new Animation<TextureRegion>(0.2f,
                new TextureRegion(harlandWorker, 14, 128, 36, 64),
                new TextureRegion(harlandWorker, 78, 128, 36, 64),
                new TextureRegion(harlandWorker, 142, 128, 36, 64),
                new TextureRegion(harlandWorker, 206, 128, 36, 64),
                new TextureRegion(harlandWorker, 270, 128, 36, 64),
                new TextureRegion(harlandWorker, 334, 128, 36, 64));
        harlandWorkerMoveRightTopAnim = new Animation<TextureRegion>(0.2f,
                new TextureRegion(harlandWorker, 14, 192, 36, 64),
                new TextureRegion(harlandWorker, 78, 192, 36, 64),
                new TextureRegion(harlandWorker, 142, 192, 36, 64),
                new TextureRegion(harlandWorker, 206, 192, 36, 64),
                new TextureRegion(harlandWorker, 270, 192, 36, 64));
        harlandWorkerMoveLeftTopAnim = new Animation<TextureRegion>(0.2f,
                new TextureRegion(harlandWorker, 14, 256, 36, 64),
                new TextureRegion(harlandWorker, 78, 256, 36, 64),
                new TextureRegion(harlandWorker, 142, 256, 36, 64),
                new TextureRegion(harlandWorker, 206, 256, 36, 64),
                new TextureRegion(harlandWorker, 270, 256, 36, 64));
        harlandWorkerDeathAnim = new Animation<TextureRegion>(0.2f,
                new TextureRegion(harlandWorker, 0, 320, 64, 64),
                new TextureRegion(harlandWorker, 64, 320, 64, 64),
                new TextureRegion(harlandWorker, 128, 320, 64, 64),
                new TextureRegion(harlandWorker, 192, 320, 64, 64),
                new TextureRegion(harlandWorker, 256, 320, 64, 64),
                new TextureRegion(harlandWorker, 320, 320, 64, 64),
                new TextureRegion(harlandWorker, 384, 320, 64, 64),
                new TextureRegion(harlandWorker, 448, 320, 64, 64));
        harlandSoldier = loadTexture("characters/harland_soldier.png");
        harlandSoldierIdleAnim = new Animation<TextureRegion>(0.8f,
                new TextureRegion(harlandSoldier, 14, 0, 36, 64),
                new TextureRegion(harlandSoldier, 78, 0, 36, 64));
        harlandSoldierMoveLeftBottomAnim = new Animation<TextureRegion>(0.2f,
                new TextureRegion(harlandSoldier, 0, 64, 64, 64),
                new TextureRegion(harlandSoldier, 64, 64, 64, 64),
                new TextureRegion(harlandSoldier, 128, 64, 64, 64),
                new TextureRegion(harlandSoldier, 192, 64, 64, 64),
                new TextureRegion(harlandSoldier, 256, 64, 64, 64),
                new TextureRegion(harlandSoldier, 320, 64, 64, 64));
        harlandSoldierMoveRightBottomAnim = new Animation<TextureRegion>(0.2f,
                new TextureRegion(harlandSoldier, 0, 128, 64, 64),
                new TextureRegion(harlandSoldier, 64, 128, 64, 64),
                new TextureRegion(harlandSoldier, 128, 128, 64, 64),
                new TextureRegion(harlandSoldier, 192, 128, 64, 64),
                new TextureRegion(harlandSoldier, 256, 128, 64, 64),
                new TextureRegion(harlandSoldier, 320, 128, 64, 64));
        harlandSoldierMoveLeftTopAnim = new Animation<TextureRegion>(0.2f,
                new TextureRegion(harlandSoldier, 0, 192, 64, 64),
                new TextureRegion(harlandSoldier, 64, 192, 64, 64),
                new TextureRegion(harlandSoldier, 128, 192, 64, 64),
                new TextureRegion(harlandSoldier, 192, 192, 64, 64),
                new TextureRegion(harlandSoldier, 256, 192, 64, 64),
                new TextureRegion(harlandSoldier, 320, 192, 64, 64));
        harlandSoldierMoveRightTopAnim = new Animation<TextureRegion>(0.2f,
                new TextureRegion(harlandSoldier, 0, 256, 64, 64),
                new TextureRegion(harlandSoldier, 64, 256, 64, 64),
                new TextureRegion(harlandSoldier, 128, 256, 64, 64),
                new TextureRegion(harlandSoldier, 192, 256, 64, 64),
                new TextureRegion(harlandSoldier, 256, 256, 64, 64),
                new TextureRegion(harlandSoldier, 320, 256, 64, 64));
        harlandSoldierAttackLeftBottomAnim = new Animation<TextureRegion>(0.2f,
                new TextureRegion(harlandSoldier, 0, 320, 64, 64),
                new TextureRegion(harlandSoldier, 64, 320, 64, 64),
                new TextureRegion(harlandSoldier, 128, 320, 64, 64),
                new TextureRegion(harlandSoldier, 192, 320, 64, 64),
                new TextureRegion(harlandSoldier, 256, 320, 64, 64));
        harlandSoldierAttackRightBottomAnim = new Animation<TextureRegion>(0.2f,
                new TextureRegion(harlandSoldier, 0, 384, 64, 64),
                new TextureRegion(harlandSoldier, 64, 384, 64, 64),
                new TextureRegion(harlandSoldier, 128, 384, 64, 64),
                new TextureRegion(harlandSoldier, 192, 384, 64, 64),
                new TextureRegion(harlandSoldier, 256, 384, 64, 64));
        harlandSoldierAttackLeftTopAnim = new Animation<TextureRegion>(0.2f,
                new TextureRegion(harlandSoldier, 0, 448, 64, 64),
                new TextureRegion(harlandSoldier, 64, 448, 64, 64),
                new TextureRegion(harlandSoldier, 128, 448, 64, 64),
                new TextureRegion(harlandSoldier, 192, 448, 64, 64),
                new TextureRegion(harlandSoldier, 256, 448, 64, 64));
        harlandSoldierAttackRightTopAnim = new Animation<TextureRegion>(0.2f,
                new TextureRegion(harlandSoldier, 0, 512, 64, 64),
                new TextureRegion(harlandSoldier, 64, 512, 64, 64),
                new TextureRegion(harlandSoldier, 128, 512, 64, 64),
                new TextureRegion(harlandSoldier, 192, 512, 64, 64),
                new TextureRegion(harlandSoldier, 256, 512, 64, 64));
        harlandSoldierDeathAnim = new Animation<TextureRegion>(0.1f,
                new TextureRegion(harlandSoldier, 0, 576, 64, 64),
                new TextureRegion(harlandSoldier, 64, 576, 64, 64),
                new TextureRegion(harlandSoldier, 128, 576, 64, 64),
                new TextureRegion(harlandSoldier, 192, 576, 64, 64),
                new TextureRegion(harlandSoldier, 256, 576, 64, 64),
                new TextureRegion(harlandSoldier, 320, 576, 64, 64),
                new TextureRegion(harlandSoldier, 320, 576, 64, 64),
                new TextureRegion(harlandSoldier, 320, 576, 64, 64),
                new TextureRegion(harlandSoldier, 320, 576, 64, 64),
                new TextureRegion(harlandSoldier, 320, 576, 64, 64));



        harlandAmablesFlatterer = loadTexture("characters/harland_amables_flatterer.png");
        harlandAmablesFlattererIdleAnim = new Animation<TextureRegion>(0.8f,
                new TextureRegion(harlandAmablesFlatterer, 0, 0, 64, 64),
                new TextureRegion(harlandAmablesFlatterer, 64, 0, 64, 64),
                new TextureRegion(harlandAmablesFlatterer, 128, 0, 64, 64),
                new TextureRegion(harlandAmablesFlatterer, 192, 0, 64, 64),
                new TextureRegion(harlandAmablesFlatterer, 256, 0, 64, 64));
        harlandAmablesFlattererMoveLeftBottomAnim = new Animation<TextureRegion>(0.2f,
                new TextureRegion(harlandAmablesFlatterer, 0, 128, 64, 64),
                new TextureRegion(harlandAmablesFlatterer, 64, 128, 64, 64),
                new TextureRegion(harlandAmablesFlatterer, 128, 128, 64, 64),
                new TextureRegion(harlandAmablesFlatterer, 192, 128, 64, 64),
                new TextureRegion(harlandAmablesFlatterer, 256, 128, 64, 64),
                new TextureRegion(harlandAmablesFlatterer, 320, 128, 64, 64));
        harlandAmablesFlattererMoveRightBottomAnim = new Animation<TextureRegion>(0.2f,
                new TextureRegion(harlandAmablesFlatterer, 0, 192, 64, 64),
                new TextureRegion(harlandAmablesFlatterer, 64, 192, 64, 64),
                new TextureRegion(harlandAmablesFlatterer, 128, 192, 64, 64),
                new TextureRegion(harlandAmablesFlatterer, 192, 192, 64, 64),
                new TextureRegion(harlandAmablesFlatterer, 256, 192, 64, 64),
                new TextureRegion(harlandAmablesFlatterer, 320, 192, 64, 64));
        harlandAmablesFlattererMoveLeftTopAnim = new Animation<TextureRegion>(0.2f,
                new TextureRegion(harlandAmablesFlatterer, 0, 256, 64, 64),
                new TextureRegion(harlandAmablesFlatterer, 64, 256, 64, 64),
                new TextureRegion(harlandAmablesFlatterer, 128, 256, 64, 64),
                new TextureRegion(harlandAmablesFlatterer, 192, 256, 64, 64),
                new TextureRegion(harlandAmablesFlatterer, 256, 256, 64, 64));
        harlandAmablesFlattererMoveRightTopAnim = new Animation<TextureRegion>(0.2f,
                new TextureRegion(harlandAmablesFlatterer, 0, 320, 64, 64),
                new TextureRegion(harlandAmablesFlatterer, 64, 320, 64, 64),
                new TextureRegion(harlandAmablesFlatterer, 128, 320, 64, 64),
                new TextureRegion(harlandAmablesFlatterer, 192, 320, 64, 64),
                new TextureRegion(harlandAmablesFlatterer, 256, 320, 64, 64));
        harlandAmablesFlattererAttackLeftBottomAnim = new Animation<TextureRegion>(0.2f,
                new TextureRegion(harlandAmablesFlatterer, 0, 64, 64, 64),
                new TextureRegion(harlandAmablesFlatterer, 64, 64, 64, 64),
                new TextureRegion(harlandAmablesFlatterer, 128, 64, 64, 64),
                new TextureRegion(harlandAmablesFlatterer, 192, 64, 64, 64),
                new TextureRegion(harlandAmablesFlatterer, 256, 64, 64, 64));
        harlandAmablesFlattererAttackRightBottomAnim = new Animation<TextureRegion>(0.2f,
                new TextureRegion(harlandAmablesFlatterer, 0, 64, 64, 64),
                new TextureRegion(harlandAmablesFlatterer, 64, 64, 64, 64),
                new TextureRegion(harlandAmablesFlatterer, 128, 64, 64, 64),
                new TextureRegion(harlandAmablesFlatterer, 192, 64, 64, 64),
                new TextureRegion(harlandAmablesFlatterer, 256, 64, 64, 64));
        harlandAmablesFlattererAttackLeftTopAnim = new Animation<TextureRegion>(0.2f,
                new TextureRegion(harlandAmablesFlatterer, 0, 64, 64, 64),
                new TextureRegion(harlandAmablesFlatterer, 64, 64, 64, 64),
                new TextureRegion(harlandAmablesFlatterer, 128, 64, 64, 64),
                new TextureRegion(harlandAmablesFlatterer, 192, 64, 64, 64),
                new TextureRegion(harlandAmablesFlatterer, 256, 64, 64, 64));
        harlandAmablesFlattererAttackRightTopAnim = new Animation<TextureRegion>(0.2f,
                new TextureRegion(harlandAmablesFlatterer, 0, 64, 64, 64),
                new TextureRegion(harlandAmablesFlatterer, 64, 64, 64, 64),
                new TextureRegion(harlandAmablesFlatterer, 128, 64, 64, 64),
                new TextureRegion(harlandAmablesFlatterer, 192, 64, 64, 64),
                new TextureRegion(harlandAmablesFlatterer, 256, 64, 64, 64));
        harlandAmablesFlattererDeathAnim = new Animation<TextureRegion>(0.1f,
                new TextureRegion(harlandAmablesFlatterer, 0, 384, 64, 64),
                new TextureRegion(harlandAmablesFlatterer, 64, 384, 64, 64),
                new TextureRegion(harlandAmablesFlatterer, 128, 384, 64, 64),
                new TextureRegion(harlandAmablesFlatterer, 192, 384, 64, 64),
                new TextureRegion(harlandAmablesFlatterer, 256, 384, 64, 64),
                new TextureRegion(harlandAmablesFlatterer, 320, 384, 64, 64),
                new TextureRegion(harlandAmablesFlatterer, 384, 384, 64, 64),
                new TextureRegion(harlandAmablesFlatterer, 448, 384, 64, 64),
                new TextureRegion(harlandAmablesFlatterer, 448, 384, 64, 64),
                new TextureRegion(harlandAmablesFlatterer, 448, 384, 64, 64));



        unitDamage = loadTexture("characters/blood.png");
        unitDamageAnim = new Animation<TextureRegion>(0.1f,
                new TextureRegion(unitDamage, 0, 0, 64, 64),
                new TextureRegion(unitDamage, 64, 0, 64, 64),
                new TextureRegion(unitDamage, 128, 0, 64, 64),
                new TextureRegion(unitDamage, 192, 0, 64, 64),
                new TextureRegion(unitDamage, 256, 0, 64, 64));

        eol = loadTexture("characters/charco.png");
        eolIdleAnim = new Animation<TextureRegion>(1f,
                new TextureRegion(eol, 0, 0, 64, 64),
                new TextureRegion(eol, 64, 0, 64, 64),
                new TextureRegion(eol, 128, 0, 64, 64));
        waterGathering = loadTexture("characters/water_gathering.png");
        waterGatheringAnim = new Animation<TextureRegion>(0.3f,
                new TextureRegion(waterGathering, 0, 0, 64, 64),
                new TextureRegion(waterGathering, 64, 0, 64, 64),
                new TextureRegion(waterGathering, 128, 0, 64, 64),
                new TextureRegion(waterGathering, 192, 0, 64, 64),
                new TextureRegion(waterGathering, 256, 0, 64, 64),
                new TextureRegion(waterGathering, 320, 0, 64, 64));
        moveAction = loadTexture("HUD/move_action32.png");
        moveActionDown = loadTexture("HUD/move_action32_down.png");
        ImageButton.ImageButtonStyle moveStyle = new ImageButton.ImageButtonStyle();
        moveStyle.imageUp = new TextureRegionDrawable(new TextureRegion(moveAction));
        moveStyle.imageDown = new TextureRegionDrawable(new TextureRegion(moveActionDown));
        moveButton = new ImageButton(moveStyle);
        moveButton.setSize(32,32);

        attackAction = loadTexture("HUD/attack_action_32.png");
        attackActionDown = loadTexture("HUD/attack_action_32_down.png");
        ImageButton.ImageButtonStyle attackStyle = new ImageButton.ImageButtonStyle();
        attackStyle.imageUp = new TextureRegionDrawable(new TextureRegion(attackAction));
        attackStyle.imageDown = new TextureRegionDrawable(new TextureRegion(attackActionDown));
        attackButton = new ImageButton(attackStyle);
        attackButton.setSize(32,32);

        patrolAction = loadTexture("HUD/patrol_action_32.png");
        patrolActionDown = loadTexture("HUD/patrol_action_32_down.png");
        ImageButton.ImageButtonStyle patrolStyle = new ImageButton.ImageButtonStyle();
        patrolStyle.imageUp = new TextureRegionDrawable(new TextureRegion(patrolAction));
        patrolStyle.imageDown = new TextureRegionDrawable(new TextureRegion(patrolActionDown));
        patrolButton = new ImageButton(patrolStyle);
        patrolButton.setSize(32,32);


        craftAction = loadTexture("HUD/craft_action32.png");
        craftActionDown = loadTexture("HUD/craft_action32_down.png");
        ImageButton.ImageButtonStyle craftStyle = new ImageButton.ImageButtonStyle();
        craftStyle.imageUp = new TextureRegionDrawable(new TextureRegion(craftAction));
        craftStyle.imageDown = new TextureRegionDrawable(new TextureRegion(craftActionDown));
        craftButton = new ImageButton(craftStyle);
        craftButton.setSize(32, 32);
        craftHarlandWorkerAction = loadTexture("HUD/createHarlandWorkerAction.png");
        craftHarlandWorkerActionDown = loadTexture("HUD/createHarlandWorkerAction_down.png");
        ImageButton.ImageButtonStyle craftHarlandWorkerStyle = new ImageButton.ImageButtonStyle();
        craftHarlandWorkerStyle.imageUp = new TextureRegionDrawable(new TextureRegion(craftHarlandWorkerAction));
        craftHarlandWorkerStyle.imageDown = new TextureRegionDrawable(new TextureRegion(craftHarlandWorkerActionDown));
        craftHarlandWorkerButton = new ImageButton(craftHarlandWorkerStyle);
        craftHarlandSoldierAction = loadTexture("HUD/createHarlandSoldierAction.png");
        craftHarlandSoldierActionDown = loadTexture("HUD/createHarlandSoldierAction_down.png");
        ImageButton.ImageButtonStyle craftHarlandSoldierStyle = new ImageButton.ImageButtonStyle();
        craftHarlandSoldierStyle.imageUp = new TextureRegionDrawable(new TextureRegion(craftHarlandSoldierAction));
        craftHarlandSoldierStyle.imageDown = new TextureRegionDrawable(new TextureRegion(craftHarlandSoldierActionDown));
        craftHarlandSoldierButton = new ImageButton(craftHarlandSoldierStyle);

        craftHarlandAmablesFlattererAction = loadTexture("HUD/createHarlandAmablesFlattererAction.png");
        craftHarlandAmablesFlattererActionDown = loadTexture("HUD/createHarlandAmablesFlattererAction_down.png");
        ImageButton.ImageButtonStyle craftHarlandAmablesFlattererStyle = new ImageButton.ImageButtonStyle();
        craftHarlandAmablesFlattererStyle.imageUp = new TextureRegionDrawable(new TextureRegion(craftHarlandAmablesFlattererAction));
        craftHarlandAmablesFlattererStyle.imageDown = new TextureRegionDrawable(new TextureRegion(craftHarlandAmablesFlattererActionDown));
        craftHarlandAmablesFlattererButton = new ImageButton(craftHarlandAmablesFlattererStyle);

        shovelAction = loadTexture("HUD/shovel_action.png");
        shovelActionDown = loadTexture("HUD/shovel_action_down.png");
        ImageButton.ImageButtonStyle shovelStyle = new ImageButton.ImageButtonStyle();
        shovelStyle.imageUp = new TextureRegionDrawable(new TextureRegion(shovelAction));
        shovelStyle.imageDown = new TextureRegionDrawable(new TextureRegion(shovelActionDown));
        shovelButton = new ImageButton(shovelStyle);
        shovelButton.setSize(32, 32);

        harlandDesertBackground = Gdx.audio.newMusic(Gdx.files.internal("soundtrack/background.mp3"));
        harlandDesertBackground.setLooping(true);
        harlandDesertBackground.setVolume(0.2f);
        harlandWorker1 = Gdx.audio.newSound(Gdx.files.internal("soundtrack/voices/characters/harland_worker_1_16b.wav"));
        harlandWorker2 = Gdx.audio.newSound(Gdx.files.internal("soundtrack/voices/characters/harland_worker_2_16b.wav"));
        harlandWorker3 = Gdx.audio.newSound(Gdx.files.internal("soundtrack/voices/characters/harland_worker_3_16b.wav"));
        harlandWorker4 = Gdx.audio.newSound(Gdx.files.internal("soundtrack/voices/characters/harland_worker_4_16b.wav"));
        harlandSoldier1 = Gdx.audio.newSound(Gdx.files.internal("soundtrack/voices/characters/harland_soldier_1_16b.wav"));
        harlandSoldier2 = Gdx.audio.newSound(Gdx.files.internal("soundtrack/voices/characters/harland_soldier_2_16b.wav"));
        harlandSoldierShoot1 = Gdx.audio.newSound(Gdx.files.internal("soundtrack/sounds/soldier/soldierAttack_01.wav"));
        harlandSoldierShoot2 = Gdx.audio.newSound(Gdx.files.internal("soundtrack/sounds/soldier/soldierAttack_02.wav"));
        harlandSoldierShoot3 = Gdx.audio.newSound(Gdx.files.internal("soundtrack/sounds/soldier/soldierAttack_03.wav"));

        harlandAmablesFlattererAttack1 = Gdx.audio.newSound(Gdx.files.internal("soundtrack/sounds/amables_flatter/attack_flatterer_1.wav"));
        harlandAmablesFlattererAttack2 = Gdx.audio.newSound(Gdx.files.internal("soundtrack/sounds/amables_flatter/attack_flatterer_2.wav"));
        harlandAmablesFlattererAttack3 = Gdx.audio.newSound(Gdx.files.internal("soundtrack/sounds/amables_flatter/attack_flatterer_3.wav"));
    }

    public static void playMusic(){
        harlandDesertBackground.play();
    }

    public static void pauseMusic(){
        harlandDesertBackground.pause();
    }
}
