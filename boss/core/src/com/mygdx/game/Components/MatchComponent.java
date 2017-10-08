package com.mygdx.game.Components;

import box2dLight.RayHandler;
import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Play;

public class MatchComponent implements Component{
    public World world;
    public Play match;
    public Box2DDebugRenderer box2DRenderer;
    public RayHandler rayHandler;
    public OrthographicCamera camera;
}
