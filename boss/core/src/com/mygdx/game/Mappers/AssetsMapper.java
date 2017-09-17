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
    public static Animation<TextureRegion> harlandWorkerAnim;
    public static Skin hudSkin;
    public static Texture moveAction;
    public static Texture moveActionDown;
    public static Texture craftAction;
    public static Texture craftActionDown;
    public static Texture craftHarlandWorkerAction;
    public static Texture craftHarlandWorkerActionDown;
    public static ImageButton moveButton;
    public static ImageButton craftButton;
    public static ImageButton craftHarlandWorkerButton;

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
        harlandWorker = loadTexture("characters/harlandWorker.png");
        harlandWorkerAnim = new Animation<TextureRegion>(0.8f,
                new TextureRegion(harlandWorker, 16, 0, 32, 64),
                new TextureRegion(harlandWorker, 80, 0, 32, 64));
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
    }
}
