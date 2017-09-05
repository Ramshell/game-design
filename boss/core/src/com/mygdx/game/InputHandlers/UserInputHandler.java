package com.mygdx.game.InputHandlers;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.math.*;
import com.mygdx.game.Components.*;
import com.mygdx.game.Components.HUD.HUDComponent;
import com.mygdx.game.Components.WorldObjects.WorldObjectComponent;
import com.mygdx.game.Entities.PlayerEntity;
import com.mygdx.game.Entities.RTSCamera;
import com.mygdx.game.Mappers.AssetsMapper;
import com.mygdx.game.Mappers.Mappers;
import com.mygdx.game.Mappers.ResourceMapper;
import com.mygdx.game.Systems.MovementSystem;
import com.mygdx.game.Systems.SelectionRenderSystem;

public class UserInputHandler extends InputAdapter {

    private RTSCamera camera;
    private Engine engine;
    private PlayerEntity player;
    private MapComponent map;
    private ComponentMapper<WorldObjectComponent> wm = ComponentMapper.getFor(WorldObjectComponent.class);
    private ComponentMapper<PlayerComponent> pm = ComponentMapper.getFor(PlayerComponent.class);
    private ComponentMapper<HUDComponent> hm = ComponentMapper.getFor(HUDComponent.class);
    private final float velocity = 200;
    private final float accel = 1000;
    private final Pixmap nm = AssetsMapper.nm;
    private final Pixmap rm = AssetsMapper.rm;
    private final Pixmap bm = AssetsMapper.bm;
    private final Pixmap lm = AssetsMapper.lm;
    private final Pixmap tm = AssetsMapper.tm;

    private SelectionComponent selection = new SelectionComponent();
    private MapLayer objectLayer;


    public UserInputHandler(RTSCamera camera, PlayerEntity player, MapComponent map, Engine engine){
        this.player = player;
        this.camera = camera;
        this.map = map;
        this.engine = engine;
        objectLayer = map.map.getLayers().get("selection_layer");
        engine.addSystem(new SelectionRenderSystem(selection, Mappers.camera.get(camera).getCamera()));
    }

    @Override
    public boolean keyDown(int keycode) {
        VelocityComponent velocityComponent = Mappers.velocity.get(camera);
        switch (keycode) {
            case Input.Keys.LEFT:
                Gdx.graphics.setCursor(Gdx.graphics.newCursor(lm, 0, 0));
                addVelocity(velocityComponent, new Vector2(-velocity, 0), new Vector2(-accel,0));
                break;
            case Input.Keys.RIGHT:
                Gdx.graphics.setCursor(Gdx.graphics.newCursor(rm, 0, 0));
                addVelocity(velocityComponent, new Vector2(velocity, 0), new Vector2(accel,0));
                break;
            case Input.Keys.UP:
                Gdx.graphics.setCursor(Gdx.graphics.newCursor(tm, 0, 0));
                addVelocity(velocityComponent, new Vector2(0, velocity), new Vector2(0, accel));
                break;
            case Input.Keys.DOWN:
                Gdx.graphics.setCursor(Gdx.graphics.newCursor(bm, 0, 0));
                addVelocity(velocityComponent, new Vector2(0, -velocity), new Vector2(0, -accel));
                break;
            case Input.Keys.ESCAPE:
                pm.get(player).selectedObject.deselect();
                break;
        }
        return true;
    }

    private void addVelocity(VelocityComponent v, Vector2 vector, Vector2 acceleration) {
        v.accel = acceleration;
        v.pos.add(vector);
    }

    @Override
    public boolean keyUp(int keycode) {
        VelocityComponent velocityComponent = Mappers.velocity.get(camera);
        switch (keycode) {
            case Input.Keys.LEFT:
                Gdx.graphics.setCursor(Gdx.graphics.newCursor(nm, 0, 0));
                velocityComponent.pos.x = 0;
                velocityComponent.accel.x = 0;
                break;
            case Input.Keys.RIGHT:
                Gdx.graphics.setCursor(Gdx.graphics.newCursor(nm, 0, 0));
                velocityComponent.pos.x = 0;
                velocityComponent.accel.x = 0;
                break;
            case Input.Keys.UP:
                Gdx.graphics.setCursor(Gdx.graphics.newCursor(nm, 0, 0));
                velocityComponent.pos.y = 0;
                velocityComponent.accel.y = 0;
                break;
            case Input.Keys.DOWN:
                Gdx.graphics.setCursor(Gdx.graphics.newCursor(nm, 0, 0));
                velocityComponent.pos.y = 0;
                velocityComponent.accel.y = 0;
                break;
        }
        return true;
    }

    @Override
    public boolean mouseMoved (int screenX, int screenY) {
        VelocityComponent velocityComponent = Mappers.velocity.get(camera);
        OrthographicCamera OCamera = Mappers.camera.get(camera).getCamera();
        if(mouseInsideCamera(OCamera, screenX, screenY)) {
            Gdx.graphics.setCursor(Gdx.graphics.newCursor(nm, 0, 0));
            velocityComponent.accel.scl(0);
            velocityComponent.pos.scl(0);
            return true;
        }
        if (screenX < 20 && velocityComponent.pos.x == 0) {
            addVelocity(velocityComponent, new Vector2(-velocity, 0), new Vector2(-accel, 0));
            Gdx.graphics.setCursor(Gdx.graphics.newCursor(lm, 0, 0));
        }
        if (screenX > OCamera.viewportWidth - 20 && velocityComponent.pos.x == 0) {
            addVelocity(velocityComponent, new Vector2(velocity, 0), new Vector2(accel, 0));
            Gdx.graphics.setCursor(Gdx.graphics.newCursor(rm, 0, 0));
        }
        if (screenY < 45 && velocityComponent.pos.y == 0) {
            addVelocity(velocityComponent, new Vector2(0, velocity), new Vector2(0, accel));
            Gdx.graphics.setCursor(Gdx.graphics.newCursor(tm, 0, 0));
        }
        if (screenY > OCamera.viewportHeight - 70 && velocityComponent.pos.y == 0) {
            addVelocity(velocityComponent, new Vector2(0, -velocity), new Vector2(0, -accel));
            Gdx.graphics.setCursor(Gdx.graphics.newCursor(bm, 0, 0));
        }
        return true;
    }

    @Override
    public boolean scrolled (int amount) {
        OrthographicCamera OCamera = Mappers.camera.get(camera).getCamera();
        OCamera.zoom = Math.max(1, Math.min(OCamera.zoom + 0.01f * amount, 2));
        return false;
    }

    public boolean touchDragged (int screenX, int screenY, int pointer) {
        if(selection.selection != null) {
            Vector3 v = Mappers.camera.get(camera).getCamera().unproject(new Vector3(screenX, screenY, 0));
            Vector2 v2 = MovementSystem.screenToIso(v.x, v.y);
            selection.selection.width = v.x - selection.selection.x;
            selection.selection.height = v.y - selection.selection.y;
            return true;
        }
        return false;
    }

    public boolean touchDown(int screenX, int screenY, int pointer, int button){
        if(button == Input.Buttons.LEFT) {
            Vector3 v = Mappers.camera.get(camera).getCamera().unproject(new Vector3(screenX, screenY, 0));
            Vector2 v2 = MovementSystem.screenToIso(v.x, v.y);
            selection.selection = new Rectangle(v.x, v.y, 0, 0);
            return true;
        }
        return false;
    }


    public boolean touchUp (int screenX, int screenY, int pointer, int button) {
        ImmutableArray<Entity> entities = engine.getEntitiesFor(Family.all(WorldObjectComponent.class).get());
        Vector3 v = Mappers.camera.get(camera).getCamera().unproject(new Vector3(screenX, screenY, 0));
        Vector2 v2 = MovementSystem.screenToIso(v.x, v.y);
        if(button == Input.Buttons.LEFT){
            Polygon myRectangle = rectToIsoPolygon(normalize(selection.selection));
            pm.get(player).selectedObject.deselect();
            for(Entity e : entities){
                WorldObjectComponent wo = wm.get(e);
                if(wo.bounds.getRectangle().contains(v2.x, v2.y) ||
                        Intersector.overlapConvexPolygons(
                                myRectangle,
                                rectToPolygon(wo.bounds.getRectangle()))){
                    wo.currentlySelected = true;
                    pm.get(player).selectedObject.add(wo);
                }
            }
            selection.selection = null;
            return true;
        }
        return false;
    }

    private boolean mouseInsideCamera(OrthographicCamera OCamera, int screenX, int screenY){
        return screenX >= 15 && screenX <= OCamera.viewportWidth - 15 &&
                screenY >= 15 && screenY <= OCamera.viewportHeight - 15;
    }

    private Rectangle normalize(Rectangle r){
        if(r.width < 0){
            r.x += r.width;
            r.width = -1 * r.width;
        }
        if(r.height < 0){
            r.y += r.height;
            r.height = -1 * r.height;
        }
        return r;
    }

    public static Polygon rectToScreenPolygon(Rectangle rect){
        float[] vertices = new float[8];
        vertices[0] = 0; vertices[1] = 0;
        Vector2 v = MovementSystem.isoToScreen(rect.width, 0);
        vertices[2] = v.x; vertices[3] = v.y;
        v = MovementSystem.isoToScreen(rect.width, rect.height);
        vertices[4] = v.x; vertices[5] = v.y;
        v = MovementSystem.isoToScreen(0, rect.height);
        vertices[6] = v.x; vertices[7] = v.y;
        Polygon pol = new Polygon(vertices);
        v = MovementSystem.isoToScreen(rect.x, rect.y);
        pol.setPosition(v.x, v.y);
        return pol;
    }

    public static Polygon rectToIsoPolygon(Rectangle rect){
        float[] vertices = new float[8];
        vertices[0] = 0; vertices[1] = 0;
        Vector2 v = MovementSystem.screenToIso(rect.width, 0);
        vertices[2] = v.x; vertices[3] = v.y;
        v = MovementSystem.screenToIso(rect.width, rect.height);
        vertices[4] = v.x; vertices[5] = v.y;
        v = MovementSystem.screenToIso(0, rect.height);
        vertices[6] = v.x; vertices[7] = v.y;
        Polygon pol = new Polygon(vertices);
        v = MovementSystem.screenToIso(rect.x, rect.y);
        pol.setPosition(v.x, v.y);
        return pol;
    }

    private Polygon rectToPolygon(Rectangle r){
        Polygon rPoly = new Polygon(new float[] { 0, 0, r.width, 0, r.width,
                r.height, 0, r.height });
        rPoly.setPosition(r.x, r.y);
        return rPoly;
    }
}
