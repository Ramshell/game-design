package com.mygdx.game.Mappers;

import com.badlogic.ashley.core.ComponentMapper;
import com.mygdx.game.Components.*;
import com.mygdx.game.Components.HUD.HUDComponent;

public class Mappers {
    public static final ComponentMapper<PositionComponent> position = ComponentMapper.getFor(PositionComponent.class);
    public static final ComponentMapper<VelocityComponent> velocity = ComponentMapper.getFor(VelocityComponent.class);
    public static final ComponentMapper<CameraComponent> camera = ComponentMapper.getFor(CameraComponent.class);
    public static final ComponentMapper<MapComponent> map = ComponentMapper.getFor(MapComponent.class);
    public static final ComponentMapper<TextureComponent> texture = ComponentMapper.getFor(TextureComponent.class);
    public static final ComponentMapper<WorldObjectComponent> world = ComponentMapper.getFor(WorldObjectComponent.class);
    public static final ComponentMapper<HUDComponent> hud = ComponentMapper.getFor(HUDComponent.class);
    public static final ComponentMapper<PlayerComponent> player = ComponentMapper.getFor(PlayerComponent.class);
}
