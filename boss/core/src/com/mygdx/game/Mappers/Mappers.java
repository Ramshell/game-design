package com.mygdx.game.Mappers;

import com.badlogic.ashley.core.ComponentMapper;
import com.mygdx.game.Components.*;
import com.mygdx.game.Components.Combat.AttackProgressionComponent;
import com.mygdx.game.Components.Combat.RangedWeaponComponent;
import com.mygdx.game.Components.HUD.HUDComponent;
import com.mygdx.game.Components.Matches.DefeatComponent;
import com.mygdx.game.Components.Matches.GoalComponent;
import com.mygdx.game.Components.WorldObjects.*;
import com.mygdx.game.Components.WorldObjects.Buildings.TryingBuildingComponent;

public class Mappers {
    public static final ComponentMapper<PositionComponent> position = ComponentMapper.getFor(PositionComponent.class);
    public static final ComponentMapper<WorldPositionComponent> worldPosition = ComponentMapper.getFor(WorldPositionComponent.class);
    public static final ComponentMapper<VelocityComponent> velocity = ComponentMapper.getFor(VelocityComponent.class);
    public static final ComponentMapper<CameraComponent> camera = ComponentMapper.getFor(CameraComponent.class);
    public static final ComponentMapper<MapComponent> map = ComponentMapper.getFor(MapComponent.class);
    public static final ComponentMapper<TextureComponent> texture = ComponentMapper.getFor(TextureComponent.class);
    public static final ComponentMapper<WorldObjectComponent> world = ComponentMapper.getFor(WorldObjectComponent.class);
    public static final ComponentMapper<HUDComponent> hud = ComponentMapper.getFor(HUDComponent.class);
    public static final ComponentMapper<PlayerComponent> player = ComponentMapper.getFor(PlayerComponent.class);
    public static final ComponentMapper<AnimationComponent> animation = ComponentMapper.getFor(AnimationComponent.class);
    public static final ComponentMapper<TargetComponent> target = ComponentMapper.getFor(TargetComponent.class);
    public static final ComponentMapper<SpawnComponent> spawn = ComponentMapper.getFor(SpawnComponent.class);
    public static final ComponentMapper<MapGraphComponent> graph = ComponentMapper.getFor(MapGraphComponent.class);
    public static final ComponentMapper<ToBuildComponent> toBuildComponentMapper = ComponentMapper.getFor(ToBuildComponent.class);
    public static final ComponentMapper<StateComponent> stateComponentMapper = ComponentMapper.getFor(StateComponent.class);
    public static final ComponentMapper<ResourceComponent> resourceComponentMapper = ComponentMapper.getFor(ResourceComponent.class);
    public static final ComponentMapper<StartGatheringComponent> startGatheringComponentComponentMapper = ComponentMapper.getFor(StartGatheringComponent.class);
    public static final ComponentMapper<GatheringPowerComponent> gatheringComponentComponentMapper = ComponentMapper.getFor(GatheringPowerComponent.class);
    public static final ComponentMapper<HealthComponent> healthComponentMapper = ComponentMapper.getFor(HealthComponent.class);
    public static final ComponentMapper<DynamicWOComponent> dynamicWOComponentMapper = ComponentMapper.getFor(DynamicWOComponent.class);
    public static final ComponentMapper<TryingBuildingComponent> tryingBuildingComponentMapper = ComponentMapper.getFor(TryingBuildingComponent.class);
    public static final ComponentMapper<IdComponent> idComponentMapper = ComponentMapper.getFor(IdComponent.class);
    public static final ComponentMapper<AttackProgressionComponent> attackProgressionComponentMapper = ComponentMapper.getFor(AttackProgressionComponent.class);
    public static final ComponentMapper<RangedWeaponComponent> rangedWeaponComponentMapper = ComponentMapper.getFor(RangedWeaponComponent.class);
    public static final ComponentMapper<WOSoundComponent> woSoundComponentMapper = ComponentMapper.getFor(WOSoundComponent.class);
    public static final ComponentMapper<PlaySoundComponent> playSoundComponentMapper = ComponentMapper.getFor(PlaySoundComponent.class);
    public static final ComponentMapper<GoalComponent> goalComponentMapper = ComponentMapper.getFor(GoalComponent.class);
    public static final ComponentMapper<CellsComponent> cellsComponentMapper = ComponentMapper.getFor(CellsComponent.class);
    public static final ComponentMapper<DefeatComponent> defeatComponentMapper = ComponentMapper.getFor(DefeatComponent.class);
    public static final ComponentMapper<PatrolComponent> patrolComponentMapper = ComponentMapper.getFor(PatrolComponent.class);

}
