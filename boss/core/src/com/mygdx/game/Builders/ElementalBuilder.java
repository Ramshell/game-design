package com.mygdx.game.Builders;

import com.mygdx.game.Components.AnimationComponent;
import com.mygdx.game.Components.PlayerComponent;
import com.mygdx.game.Components.StateComponent;
import com.mygdx.game.Entities.UnitEntity;
import com.mygdx.game.Mappers.AssetsMapper;

public class ElementalBuilder {

    public enum ElementalState{
        Normal
    }

    public UnitEntity elemental(PlayerComponent player, float posX, float posY){
        AnimationComponent anim = new AnimationComponent();
        anim.animations.put(ElementalState.Normal.ordinal(), AssetsMapper.skeletonStandAnim);
        return new UnitEntity(player, "elemental", posX, posY,
                8, 8,128, 128,
                ElementalState.Normal.ordinal(), anim);
    }
}
