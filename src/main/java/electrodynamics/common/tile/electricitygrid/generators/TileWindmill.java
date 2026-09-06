package electrodynamics.common.tile.electricitygrid.generators;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerWindmill;
import electrodynamics.common.settings.ElectrodynamicsConfig;
import electrodynamics.prefab.utilities.ElectricityUtils;
import electrodynamics.registers.ElectrodynamicsSounds;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import voltaic.api.multiblock.subnodebased.TileMultiSubnode;
import voltaic.api.multiblock.subnodebased.parent.IMultiblockParentBlock;
import voltaic.api.multiblock.subnodebased.parent.IMultiblockParentTile;
import voltaic.common.item.subtype.SubtypeItemUpgrade;
import voltaic.prefab.properties.types.PropertyTypes;
import voltaic.prefab.properties.variant.SingleProperty;
import voltaic.prefab.sound.ITickableSound;
import voltaic.prefab.sound.SoundBarrierMethods;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentContainerProvider;
import voltaic.prefab.tile.components.type.ComponentElectrodynamic;
import voltaic.prefab.tile.components.type.ComponentInventory;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.prefab.utilities.object.TransferPack;

public class TileWindmill extends GenericGeneratorTile implements IMultiblockParentTile, ITickableSound {

    private final SingleProperty<Boolean> isGenerating = property(
	    new SingleProperty<>(getPropertyManager(), PropertyTypes.BOOLEAN, "isGenerating", false));
    public SingleProperty<Boolean> directionFlag = property(
	    new SingleProperty<>(getPropertyManager(), PropertyTypes.BOOLEAN, "directionFlag", false));
    public SingleProperty<Double> generating = property(
	    new SingleProperty<>(getPropertyManager(), PropertyTypes.DOUBLE, "generating", 0.0));

    private final SingleProperty<Double> multiplier = property(
	    new SingleProperty<>(getPropertyManager(), PropertyTypes.DOUBLE, "multiplier", 1.0));
    private final SingleProperty<Boolean> hasRedstoneSignal = property(
	    new SingleProperty<>(getPropertyManager(), PropertyTypes.BOOLEAN, "redstonesignal", false));

    public double savedTickRotation;
    public double rotationSpeed;

    private boolean isSoundPlaying;

    public TileWindmill(BlockPos worldPosition, BlockState blockState) {
	super(ElectrodynamicsTiles.TILE_WINDMILL.get(), worldPosition, blockState, 2.25, SubtypeItemUpgrade.stator);
	addComponent(new ComponentTickable(this).tickServer(this::tickServer).tickCommon(this::tickCommon)
		.tickClient(this::tickClient));
	addComponent(new ComponentElectrodynamic(this, true, false)
		.setOutputDirections(BlockEntityUtils.MachineDirection.BOTTOM));
	addComponent(new ComponentInventory(this, ComponentInventory.InventoryBuilder.newInv().upgrades(1))
		.validUpgrades(ContainerWindmill.VALID_UPGRADES).valid(machineValidator()));
	addComponent(new ComponentContainerProvider(SubtypeMachine.windmill.tag(), this)
		.createMenu((id, player) -> new ContainerWindmill(id, player,
			requireComponent(IComponentType.Inventory), getCoordsArray())));
    }

    protected void tickServer(Level level, ComponentTickable tickable) {
	if (hasRedstoneSignal.getValue()) {
	    generating.setValue(0.0);
	    return;
	}
	Direction facing = getFacing();
	if (tickable.getTicks() % 40 == 0) {
	    isGenerating.setValue(level.isEmptyBlock(worldPosition.relative(facing).above()));
	    float height = Math.max(0, level.getHeight());
	    double heightMultiplier = Math.log10((Math.max(0, worldPosition.getY()) + height / 10.0) * 10.0 / height);
	    generating.setValue(
		    ElectrodynamicsConfig.INSTANCE.WINDMILL_MAX_AMPERAGE.get() * Mth.clamp(heightMultiplier, 0, 1));
	}
	if (!isGenerating.getValue())
	    return;
	BlockEntity target = level.getBlockEntity(worldPosition.below());
	if (target != null && !target.isRemoved())
	    ElectricityUtils.receivePower(target, Direction.UP, getProduced(), false);
    }

    protected void tickCommon(Level level, ComponentTickable tickable) {
	savedTickRotation += (directionFlag.getValue() ? 1 : -1) * rotationSpeed;
	rotationSpeed = Mth.clamp(rotationSpeed + 0.05 * (isGenerating.getValue() ? 1 : -1), 0.0, 1.0);
    }

    protected void tickClient(Level level, ComponentTickable tickable) {
	if (shouldPlaySound() && !isSoundPlaying) {
	    isSoundPlaying = true;
	    SoundBarrierMethods.playTileSound(ElectrodynamicsSounds.SOUND_HUM.get(), this, true);
	}
    }

    @Override
    public void onNeighbourChanged(LevelReader reader, BlockPos neighbor, boolean blockStateTrigger) {
	if (reader instanceof Level level && !level.isClientSide)
	    hasRedstoneSignal.setValue(level.hasNeighborSignal(getBlockPos()));
    }

    @Override
    public void setNotPlaying() {
	isSoundPlaying = false;
    }

    @Override
    public boolean shouldPlaySound() {
	return isGenerating.getValue();
    }

    @Override
    public IMultiblockParentBlock.SubnodeWrapper getSubNodes() {
	return SubtypeMachine.Subnodes.WINDMILL;
    }

    @Override
    public void setMultiplier(double val) {
	multiplier.setValue(val);
    }

    @Override
    public double getMultiplier() {
	return multiplier.getValue();
    }

    @Override
    public TransferPack getProduced() {
	ComponentElectrodynamic electro = requireComponent(IComponentType.Electrodynamic);
	return TransferPack.ampsVoltage(generating.getValue() * multiplier.getValue(), electro.getVoltage());
    }

    @Override
    public int getComparatorSignal(Level level) {
	return isGenerating.getValue() ? 15 : 0;
    }

    @Override
    public void onSubnodeDestroyed(Level level, TileMultiSubnode subnode) {
	level.destroyBlock(worldPosition, true);
    }

    @Override
    public int getSubdnodeComparatorSignal(Level level, TileMultiSubnode subnode) {
	return getComparatorSignal(level);
    }

    @Override
    public ItemInteractionResult onSubnodeUseWithItem(Level level, ItemStack used, Player player, InteractionHand hand,
	    BlockHitResult hit, TileMultiSubnode subnode) {
	return useWithItem(level, used, player, hand, hit);
    }

    @Override
    public InteractionResult onSubnodeUseWithoutItem(Level level, Player player, BlockHitResult hit,
	    TileMultiSubnode subnode) {
	return useWithoutItem(level, player, hit);
    }

    @Override
    public Direction getFacingDirection() {
	return getFacing();
    }
}