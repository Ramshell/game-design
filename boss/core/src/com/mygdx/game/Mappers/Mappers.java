package com.mygdx.game.Mappers;

import com.badlogic.ashley.core.ComponentMapper;
import com.mygdx.game.Components.CameraComponent;
import com.mygdx.game.Components.PositionComponent;
import com.mygdx.game.Components.VelocityComponent;

public class Mappers {
    public static final ComponentMapper<PositionComponent> position = ComponentMapper.getFor(PositionComponent.class);
    public static final ComponentMapper<VelocityComponent> velocity = ComponentMapper.getFor(VelocityComponent.class);
    public static final ComponentMapper<CameraComponent> camera = ComponentMapper.getFor(CameraComponent.class);

}
