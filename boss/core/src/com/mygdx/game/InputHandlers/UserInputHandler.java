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
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.math.*;
import com.mygdx.game.Components.*;
import com.mygdx.game.Components.HUD.HUDComponent;
import com.mygdx.game.Components.WorldObjects.WorldObjectComponent;
import com.mygdx.game.Entities.PlayerEntity;
import com.mygdx.game.Entities.RTSCamera;
import com.mygdx.game.Mappers.AssetsMapper;
import com.mygdx.game.Mappers.Mappers;
import com.mygdx.game.OOP.Actions.*;
import com.mygdx.game.PathfindingUtils.MapGraph;
import com.mygdx.game.Systems.MovementSystem;

public class UserInputHandler extends InputAdapter implements GestureDetector.GestureListener{

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

    public SelectionComponent selection = new SelectionComponent();
    private MapLayer objectLayer;
    private MapGraph mapGraph;


    public UserInputHandler(RTSCamera camera, PlayerEntity player, MapComponent map, Engine engine, MapGraph mapGraph){
        this.player = player;
        this.camera = camera;
        this.map = map;
        this.engine = engine;
        this.mapGraph = mapGraph;
        objectLayer = map.map.getLayers().get("selection_layer");
    }

    @Override
    public boolean keyDown(int keycode) {
        VelocityComponent velocityComponent = Mappers.velocity.get(camera);
        switch (keycode) {
            case Input.Keys.LEFT:
                Gdx.graphics.setCursor(Gdx.graphics.newCursor(lm, 0, 0));
                addVelocity(velocityComponent, new Vector2(-velocity, velocityComponent.pos.y), new Vector2(-accel,velocityComponent.accel.y));
                break;
            case Input.Keys.RIGHT:
                Gdx.graphics.setCursor(Gdx.graphics.newCursor(rm, 0, 0));
                addVelocity(velocityComponent, new Vector2(velocity, velocityComponent.pos.y), new Vector2(accel,velocityComponent.accel.y));
                break;
            case Input.Keys.UP:
                Gdx.graphics.setCursor(Gdx.graphics.newCursor(tm, 0, 0));
                addVelocity(velocityComponent, new Vector2(velocityComponent.pos.x, velocity), new Vector2(velocityComponent.accel.x, accel));
                break;
            case Input.Keys.DOWN:
                Gdx.graphics.setCursor(Gdx.graphics.newCursor(bm, 0, 0));
                addVelocity(velocityComponent, new Vector2(velocityComponent.pos.x, -velocity), new Vector2(velocityComponent.accel.x, -accel));
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
            case Input.Keys.FORWARD_DEL:
                Mappers.player.get(player).selectedObject.act(new DeleteAction());
            case Input.Keys.A:
                Mappers.player.get(player).action = new ActionBuilder() {
                    @Override
                    public Action<Entity> getAction(float x, float y) {
                        OrthographicCamera OCamera = Mappers.camera.get(camera).getCamera();
                        Vector3 v = OCamera.unproject(new Vector3(x, y, 0));
                        return new MoveAttackingAction(v.x, v.y, mapGraph);
                    }
                };
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
            addVelocity(velocityComponent, new Vector2(-velocity, velocityComponent.pos.y), new Vector2(-accel, velocityComponent.accel.y));
            Gdx.graphics.setCursor(Gdx.graphics.newCursor(lm, 0, 0));
        }
        if (screenX > OCamera.viewportWidth - 20 && velocityComponent.pos.x == 0) {
            addVelocity(velocityComponent, new Vector2(velocity, velocityComponent.pos.y), new Vector2(accel, velocityComponent.accel.y));
            Gdx.graphics.setCursor(Gdx.graphics.newCursor(rm, 0, 0));
        }
        if (screenY < 20 && velocityComponent.pos.y == 0) {
            addVelocity(velocityComponent, new Vector2(velocityComponent.pos.x, velocity), new Vector2(velocityComponent.accel.x, accel));
            Gdx.graphics.setCursor(Gdx.graphics.newCursor(tm, 0, 0));
        }
        if (screenY > OCamera.viewportHeight - 20 && velocityComponent.pos.y == 0) {
            addVelocity(velocityComponent, new Vector2(velocityComponent.pos.x, -velocity), new Vector2(velocityComponent.accel.x, -accel));
            Gdx.graphics.setCursor(Gdx.graphics.newCursor(bm, 0, 0));
        }
        return true;
    }

    @Override
    public boolean scrolled (int amount) {
        OrthographicCamera OCamera = Mappers.camera.get(camera).getCamera();
        OCamera.zoom = Math.max(0.5f, Math.min(OCamera.zoom + 0.05f * amount, 2));
        return false;
    }

    public boolean touchDragged (int screenX, int screenY, int pointer) {
        if(selection.selection != null && Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            Vector3 v = Mappers.camera.get(camera).getCamera().unproject(new Vector3(screenX, screenY, 0));
            selection.selection.width = v.x - selection.selection.x;
            selection.selection.height = v.y - selection.selection.y;
            return true;
        }
        return false;
    }

    public boolean touchDown(int screenX, int screenY, int pointer, int button){
        Vector3 v = Mappers.camera.get(camera).getCamera().unproject(new Vector3(screenX, screenY, 0));
        if(button == Input.Buttons.LEFT) {
            selection.selection = new Rectangle(v.x, v.y, 0, 0);
            return true;
        }
        if(button == Input.Buttons.RIGHT) {
            if(MovementSystem.outsideWorld(v.x, v.y)) return false;
            ActionsInputHandler.cancelActions(Mappers.player.get(player),engine);
            Entity attacked = AttackAction.getTarget(v.x, v.y, mapGraph, Mappers.player.get(player));
            if( attacked != null &&
                !Mappers.player.get(attacked).equals(Mappers.player.get(player))) Mappers.player.get(player).selectedObject.act(new AttackAction(attacked));
            else {
                ResourceGatheringAction resourceGatheringAction = new ResourceGatheringAction(v.x, v.y, mapGraph, engine);
                if(resourceGatheringAction.resource != null)
                    Mappers.player.get(player).selectedObject.act(resourceGatheringAction);
                else Mappers.player.get(player).selectedObject.act(new MoveAction(v.x, v.y, mapGraph));
            }
            return true;
        }
        return false;
    }


    public boolean touchUp (int screenX, int screenY, int pointer, int button) {
        ImmutableArray<Entity> entities = engine.getEntitiesFor(Family.all(WorldObjectComponent.class).get());
        Vector3 v = Mappers.camera.get(camera).getCamera().unproject(new Vector3(screenX, screenY, 0));
        if(button == Input.Buttons.LEFT){
            pm.get(player).selectedObject.deselect();
            normalize(selection.selection);
            for(Entity e : entities){
                WorldObjectComponent wo = wm.get(e);
                PlayerComponent checkPlayer = pm.get(e);
                if(wo.bounds.getRectangle().contains(v.x, v.y) ||
                        Intersector.overlaps(
                                selection.selection,
                                wo.bounds.getRectangle())){
                    if(checkPlayer != null && !checkPlayer.equals(pm.get(player)) && pm.get(player).selectedObject.getSelectedObjects().size == 0){
                        pm.get(player).selectedObject.addEnemy(e);
                    }else if(!(checkPlayer != null && !checkPlayer.equals(pm.get(player)))){
                        pm.get(player).selectedObject.add(e);
                    }
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

    private void normalize(Rectangle r){
        if(r == null) return;
        if(r.width < 0){
            r.x += r.width;
            r.width = -1 * r.width;
        }
        if(r.height < 0){
            r.y += r.height;
            r.height = -1 * r.height;
        }
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        if(count != 2 || button != Input.Buttons.LEFT || pm.get(player).selectedObject.getSelectedObjects().size != 1 ||
                (Mappers.player.get(pm.get(player).selectedObject.getSelectedObjects().first()) != null &&
                !Mappers.player.get(pm.get(player).selectedObject.getSelectedObjects().first()).equals(Mappers.player.get(player)))) return false;
        Rectangle rectangle = new Rectangle(
                Mappers.camera.get(camera).getCamera().position.x - Mappers.camera.get(camera).getCamera().viewportWidth / 2,
                Mappers.camera.get(camera).getCamera().position.y - Mappers.camera.get(camera).getCamera().viewportHeight / 2,
                Mappers.camera.get(camera).getCamera().viewportWidth, Mappers.camera.get(camera).getCamera().viewportHeight);
        ImmutableArray<Entity> entities = engine.getEntitiesFor(Family.all(WorldObjectComponent.class).get());
        for(Entity e: entities){
            WorldObjectComponent wo = wm.get(e);
            PlayerComponent checkPlayer = pm.get(e);
            if( wo.bounds.getRectangle().overlaps(rectangle) &&
                checkPlayer != null && checkPlayer.equals(pm.get(player)) &&
                Mappers.world.get(pm.get(player).selectedObject.getSelectedObjects().first())
                    .objectName.equals(wo.objectName)){
                pm.get(player).selectedObject.add(e);
            }
        }
        return true;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }
}
