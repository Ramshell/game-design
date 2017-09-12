package com.mygdx.game.Mappers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

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

    public static Texture loadTexture (String file) {
        return new Texture(Gdx.files.internal(file));
    }

    public static void load () {
        nm = new Pixmap(Gdx.files.internal("cursor/normal_pointer.png"));
        rm = new Pixmap(Gdx.files.internal("cursor/right_movement.png"));
        bm = new Pixmap(Gdx.files.internal("cursor/bottom_movement.png"));
        lm = new Pixmap(Gdx.files.internal("cursor/left_movement.png"));
        tm = new Pixmap(Gdx.files.internal("cursor/top_movement.png"));
        harlandWorker = loadTexture("characters/harlandWorker.png");
        harlandWorkerAnim = new Animation<TextureRegion>(0.8f,
                new TextureRegion(harlandWorker, 16, 0, 32, 64),
                new TextureRegion(harlandWorker, 80, 0, 32, 64));


    }
}
