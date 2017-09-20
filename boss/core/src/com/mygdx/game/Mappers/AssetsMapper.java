package com.mygdx.game.Mappers;

import com.badlogic.gdx.Gdx;
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
    public static Texture skeleton;
    public static Animation<TextureRegion> skeletonStandAnim;

    public static Texture harlandWorker;
    public static Animation<TextureRegion> harlandWorkerIdleAnim;
    public static Animation<TextureRegion> harlandWorkerMoveRightBottomAnim;
    public static Animation<TextureRegion> harlandWorkerMoveLeftBottomAnim;
    public static Animation<TextureRegion> harlandWorkerMoveRightTopAnim;
    public static Animation<TextureRegion> harlandWorkerMoveLeftTopAnim;
    public static Texture eol;
    public static Texture waterGathering;
    public static Animation<TextureRegion> eolIdleAnim;
    public static Skin hudSkin;
    public static Texture moveAction;
    public static Texture moveActionDown;
    public static Texture craftAction;
    public static Texture craftActionDown;
    public static Texture craftHarlandWorkerAction;
    public static Texture craftHarlandWorkerActionDown;
    public static Texture shovelAction;
    public static Texture shovelActionDown;
    public static ImageButton moveButton;
    public static ImageButton craftButton;
    public static ImageButton craftHarlandWorkerButton;
    public static ImageButton shovelButton;
    public static Animation<TextureRegion> waterGatheringAnim;

    public static Texture loadTexture (String file) {
        return new Texture(Gdx.files.internal(file));
    }

    public static void load () {
        hudSkin = new Skin(Gdx.files.internal("HUD/skins/gdx-skins-master/shade/skin/uiskin.json"));
        nm = new Pixmap(Gdx.files.internal("cursor/normal_pointer.png"));
        rm = new Pixmap(Gdx.files.internal("cursor/right_movement.png"));
        bm = new Pixmap(Gdx.files.internal("cursor/bottom_movement.png"));
        lm = new Pixmap(Gdx.files.internal("cursor/left_movement.png"));
        tm = new Pixmap(Gdx.files.internal("cursor/top_movement.png"));
        harlandWorker = loadTexture("characters/harland_worker_shadow.png");
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
                new TextureRegion(harlandWorker, 270, 192, 36, 64),
                new TextureRegion(harlandWorker, 334, 192, 36, 64));
        harlandWorkerMoveLeftTopAnim = new Animation<TextureRegion>(0.2f,
                new TextureRegion(harlandWorker, 14, 256, 36, 64),
                new TextureRegion(harlandWorker, 78, 256, 36, 64),
                new TextureRegion(harlandWorker, 142, 256, 36, 64),
                new TextureRegion(harlandWorker, 206, 256, 36, 64),
                new TextureRegion(harlandWorker, 270, 256, 36, 64),
                new TextureRegion(harlandWorker, 334, 256, 36, 64));
        eol = loadTexture("characters/charco.png");
        eolIdleAnim = new Animation<TextureRegion>(0.3f,
                new TextureRegion(eol, 0, 32, 64, 32),
                new TextureRegion(eol, 64, 32, 64, 32),
                new TextureRegion(eol, 128, 32, 64, 32));
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
        shovelAction = loadTexture("HUD/shovel_action.png");
        shovelActionDown = loadTexture("HUD/shovel_action_down.png");
        ImageButton.ImageButtonStyle shovelStyle = new ImageButton.ImageButtonStyle();
        shovelStyle.imageUp = new TextureRegionDrawable(new TextureRegion(shovelAction));
        shovelStyle.imageDown = new TextureRegionDrawable(new TextureRegion(shovelActionDown));
        shovelButton = new ImageButton(shovelStyle);
        shovelButton.setSize(32, 32);
    }
}
