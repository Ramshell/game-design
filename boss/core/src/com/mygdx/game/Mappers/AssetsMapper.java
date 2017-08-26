package com.mygdx.game.Mappers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;

public class AssetsMapper {
    public static final Pixmap nm = new Pixmap(Gdx.files.internal("cursor/normal_pointer.png"));
    public static final Pixmap rm = new Pixmap(Gdx.files.internal("cursor/right_movement.png"));
    public static final Pixmap bm = new Pixmap(Gdx.files.internal("cursor/bottom_movement.png"));
    public static final Pixmap lm = new Pixmap(Gdx.files.internal("cursor/left_movement.png"));
    public static final Pixmap tm = new Pixmap(Gdx.files.internal("cursor/top_movement.png"));
}
