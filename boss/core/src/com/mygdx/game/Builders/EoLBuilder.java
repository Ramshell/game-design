package com.mygdx.game.Builders;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.mygdx.game.Components.AnimationComponent;
import com.mygdx.game.Components.StateComponent;
import com.mygdx.game.Components.TextureComponent;
import com.mygdx.game.Components.WorldObjects.ResourceComponent;
import com.mygdx.game.Components.WorldObjects.WorldObjectComponent;
import com.mygdx.game.Components.WorldObjects.WorldPositionComponent;
import com.mygdx.game.Mappers.AssetsMapper;
import com.mygdx.game.Mappers.ResourceMapper;
import com.mygdx.game.PathfindingUtils.MapGraph;
import com.mygdx.game.Play;

public class EoLBuilder {
    public static int IDLE = 1;
    private Play play;
    private MapGraph mapGraph;

    public EoLBuilder(Play p, MapGraph mapGraph){
        this.play = p;
        this.mapGraph = mapGraph;
    }

    /**
     * @param posX
     * @param posY both are tile positions
     * **/
    public Entity getEoL(int posX, int posY, int maxResources){

        WorldObjectComponent wo = new WorldObjectComponent("EoL");
        wo.bounds = new RectangleMapObject(posX * ResourceMapper.tileWidth, posY * ResourceMapper.tileHeight, 64, 32);
        WorldPositionComponent wp = new WorldPositionComponent(posX * ResourceMapper.tileWidth, posY * ResourceMapper.tileHeight);
        ResourceComponent ro = new ResourceComponent(maxResources);

        AnimationComponent anim = new AnimationComponent();
        anim.animations.put(IDLE, AssetsMapper.eolIdleAnim);
        StateComponent state = new StateComponent();
        state.set(IDLE);
        TextureComponent t = new TextureComponent();
        t.region = anim.animations.get(IDLE).getKeyFrame(0);
        for(int i = posX; i < posX + 2 ; ++i)
            for(int j = posY; j < posY + 1; ++j)
                mapGraph.setCollisionBuilding(i, j, true);
        return new Entity().add(wo).add(ro).add(wp).add(t).add(state).add(anim);
    }
}
