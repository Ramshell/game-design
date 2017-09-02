package com.mygdx.game.Systems;


import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Components.HUD.HUDComponent;
import com.mygdx.game.Components.MapComponent;
import com.mygdx.game.Components.PositionComponent;
import com.mygdx.game.Components.RenderableComponent;
import com.mygdx.game.Components.TextureComponent;
import com.mygdx.game.Mappers.Mappers;

public class RenderSystem extends EntitySystem{
    private Batch batch;
    private Camera camera;
    private ImmutableArray<Entity> entities;
    private ComponentMapper<TextureComponent> tm = ComponentMapper.getFor(TextureComponent.class);
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);

    public RenderSystem(Camera camera, Batch batch){
        this.batch = batch;
        this.camera = camera;
    }

    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(TextureComponent.class, PositionComponent.class, RenderableComponent.class).get());

    }

    public void update(float deltaTime) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for(Entity e : entities){
            TextureComponent textureComponent = tm.get(e);
            PositionComponent positionComponent = pm.get(e);
            batch.draw(textureComponent.region, positionComponent.pos.x, positionComponent.pos.y);
        }
        batch.end();
    }

}
