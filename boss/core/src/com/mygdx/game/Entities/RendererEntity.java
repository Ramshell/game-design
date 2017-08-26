package com.mygdx.game.Entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.game.Components.CameraComponent;
import com.mygdx.game.Components.HUD.HUDComponent;
import com.mygdx.game.Components.MapComponent;
import com.mygdx.game.Components.PositionComponent;
import com.mygdx.game.Components.VelocityComponent;

public class RendererEntity extends Entity{
    public MapComponent mc;
    public HUDComponent hud;

    public RendererEntity(MapComponent mc, HUDComponent h){
        this.mc = mc;
        hud = h;
        add(mc).add(h);
    }
}
