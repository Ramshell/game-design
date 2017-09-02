package com.mygdx.game.Systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Components.SelectionComponent;
import com.mygdx.game.Mappers.ResourceMapper;

public class SelectionRenderSystem extends EntitySystem{
    private Engine engine;
    private Camera camera;
    private SelectionComponent selection;
    private ShapeRenderer shapeRenderer;

    public SelectionRenderSystem(SelectionComponent selection, Camera camera){
        this.camera = camera;
        shapeRenderer = new ShapeRenderer();
        this.selection = selection;
    }

    public void addedToEngine(Engine engine) {
        this.engine = engine;
    }

    public void update(float delta){
        if(selection.selection == null) return;
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(new Color(
                ResourceMapper.rSelectionColor,
                ResourceMapper.gSelectionColor,
                ResourceMapper.bSelectionColor,
                ResourceMapper.opacySelectionColor));
        shapeRenderer.rect(selection.selection.x, selection.selection.y,
                selection.selection.width, selection.selection.height);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }


}
