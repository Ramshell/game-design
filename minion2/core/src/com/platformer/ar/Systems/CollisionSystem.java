package com.platformer.ar.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector3;
import com.platformer.ar.Components.MapRendererComponent;
import com.platformer.ar.Components.TextureComponent;
import com.platformer.ar.Components.TransformComponent;
import com.platformer.ar.Components.World.SolidComponent;
import com.platformer.ar.Components.World.VelocityComponent;

public class CollisionSystem extends EntitySystem{

    ComponentMapper<VelocityComponent> vcm = ComponentMapper.getFor(VelocityComponent.class);
    ComponentMapper<TransformComponent> tcm = ComponentMapper.getFor(TransformComponent.class);
    ComponentMapper<SolidComponent> scm = ComponentMapper.getFor(SolidComponent.class);
    ComponentMapper<MapRendererComponent> mrcm = ComponentMapper.getFor(MapRendererComponent.class);
    ComponentMapper<TextureComponent> textureM = ComponentMapper.getFor(TextureComponent.class);

    MapRendererComponent mrc;

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        mrc = mrcm.get(getEngine().getEntitiesFor(Family.all(MapRendererComponent.class).get()).first());

    }

    @Override
    public void update(float deltaTime) {
        for(Entity e : getEngine().getEntitiesFor(Family.all(
                VelocityComponent.class,
                TransformComponent.class,
                SolidComponent.class,
                TextureComponent.class).get())){
            VelocityComponent vc = vcm.get(e);
            TransformComponent tc = tcm.get(e);
            SolidComponent sc = scm.get(e);
            TextureComponent tex = textureM.get(e);

            float width = tex.region.getRegionWidth() * Math.abs(tc.scale.x) / 2;
            float height = tex.region.getRegionHeight() * Math.abs(tc.scale.y);

            float originX = width/2f;
            float originY = height/2f;

            Vector3 v = tc.position.cpy()
                    .add(vc.accel.x * 1/2 * deltaTime * deltaTime, vc.accel.y * 1/2 * deltaTime * deltaTime, 0)
                    .add(vc.pos.x * deltaTime, vc.pos.y * deltaTime, 0);
            sc.rectangle.x = v.x - originX;
            for(RectangleMapObject rmo : mrc.renderer.getMap().getLayers().get("collision").getObjects().getByType(RectangleMapObject.class)){
                if(sc.rectangle.overlaps(rmo.getRectangle())){
                    vc.pos.x = 0;
                    vc.accel.x = 0;
                    break;
                }
            }
            sc.rectangle.setPosition(tc.position.x - originX, tc.position.y - originY);
            sc.rectangle.y = v.y - originY;
            for(RectangleMapObject rmo : mrc.renderer.getMap().getLayers().get("collision").getObjects().getByType(RectangleMapObject.class)){
                if(sc.rectangle.overlaps(rmo.getRectangle())){
                    vc.pos.y = 0;
                    vc.accel.y = 0;
                    break;
                }
            }
            sc.rectangle.setPosition(tc.position.x - originX, tc.position.y - originY);
        }
    }
}
