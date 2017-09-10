package com.mygdx.game.Builders;

import com.mygdx.game.Components.AnimationComponent;
import com.mygdx.game.Components.PlayerComponent;
import com.mygdx.game.Entities.UnitEntity;
import com.mygdx.game.Mappers.AssetsMapper;

public class HarlandWorkerBuilder{
    public static int IDLE = 1;


    /**
     * @param posX
     * @param posY both are tile positions
     * **/
    public UnitEntity getWorker(PlayerComponent player, int posX, int posY){
        AnimationComponent anim = new AnimationComponent();
        anim.animations.put(IDLE, AssetsMapper.harlandWorkerAnim);
        return new UnitEntity(player, "Harland Worker", posX, posY,
                64, 64,64, 64,
                IDLE, anim);
    }
}
