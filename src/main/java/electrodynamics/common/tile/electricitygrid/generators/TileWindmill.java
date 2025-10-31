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
import voltaic.prefab.tile.components.type.ComponentPacketHandler;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.prefab.utilities.object.CachedTileOutput;
import voltaic.prefab.utilities.object.TransferPack;

public class TileWindmill extends GenericGeneratorTile implements IMultiblockParentTile, ITickableSound {

    protected CachedTileOutput output;
    private SingleProperty<Boolean> isGenerating = property(
	    new SingleProperty<>(PropertyTypes.BOOLEAN, "isGenerating", false));
    public SingleProperty<Boolean> directionFlag = property(
	    new SingleProperty<>(PropertyTypes.BOOLEAN, "directionFlag", false));
    public SingleProperty<Double> generating = property(new SingleProperty<>(PropertyTypes.DOUBLE, "generating", 0.0));
    private SingleProperty<Double> multiplier = property(new SingleProperty<>(PropertyTypes.DOUBLE, "multiplier", 1.0));
    private SingleProperty<Boolean> hasRedstoneSignal = property(
	    new SingleProperty<>(PropertyTypes.BOOLEAN, "redstonesignal", false));
    public double savedTickRotation;
    public double rotationSpeed;

    private boolean isSoundPlaying = false;

    public TileWindmill(BlockPos worldPosition, BlockState blockState) {
	super(ElectrodynamicsTiles.TILE_WINDMILL.get(), worldPosition, blockState, 2.25, SubtypeItemUpgrade.stator);
	addComponent(new ComponentPacketHandler(this));
	addComponent(new ComponentTickable(this).tickServer(this::tickServer).tickCommon(this::tickCommon)
		.tickClient(this::tickClient));
	addComponent(new ComponentElectrodynamic(this, true, false)
		.setOutputDirections(BlockEntityUtils.MachineDirection.BOTTOM));
	addComponent(new ComponentInventory(this, ComponentInventory.InventoryBuilder.newInv().upgrades(1))
		.validUpgrades(ContainerWindmill.VALID_UPGRADES).valid(machineValidator()));
	addComponent(new ComponentContainerProvider(SubtypeMachine.windmill.tag(), this)
		.createMenu((id, player) -> new ContainerWindmill(id, player, getComponent(IComponentType.Inventory),
			getCoordsArray())));

    }

    protected void tickServer(ComponentTickable tickable) {
	if (hasRedstoneSignal.getValue()) {
	    generating.setValue(0.0);
	    return;
	}
	Direction facing = getFacing();
	if (tickable.getTicks() % 40 == 0) {
	    isGenerating.setValue(level.isEmptyBlock(worldPosition.relative(facing).relative(Direction.UP)));
	    float height = Math.max(0, level.getHeight());
	    double f = Math.log10((Math.max(0, getBlockPos().getY()) + height / 10.0) * 10.0 / height);
	    generating.setValue(ElectrodynamicsConfig.INSTANCE.WINDMILL_MAX_AMPERAGE.get() * Mth.clamp(f, 0, 1));
	}
	if (output == null) {
	    output = new CachedTileOutput(level, worldPosition.relative(Direction.DOWN));
	}
	output.update(worldPosition.relative(Direction.DOWN));
	if (isGenerating.getValue() && output.valid()) {
	    ElectricityUtils.receivePower(output.getSafe(), Direction.UP, getProduced(), false);
	}
    }

    protected void tickCommon(ComponentTickable tickable) {
	savedTickRotation += (directionFlag.getValue() ? 1 : -1) * rotationSpeed;
	rotationSpeed = Mth.clamp(rotationSpeed + 0.05 * (isGenerating.getValue() ? 1 : -1), 0.0, 1.0);
    }

    protected void tickClient(ComponentTickable tickable) {
	if (shouldPlaySound() && !isSoundPlaying) {
	    isSoundPlaying = true;
	    SoundBarrierMethods.playTileSound(ElectrodynamicsSounds.SOUND_HUM.get(), this, true);
	}
    }

    @Override
    public void onNeightborChanged(BlockPos neighbor, boolean blockStateTrigger) {
	if (level.isClientSide) {
	    return;
	}
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
	return TransferPack.ampsVoltage(generating.getValue() * multiplier.getValue(),
		this.<ComponentElectrodynamic>getComponent(IComponentType.Electrodynamic).getVoltage());
    }

    @Override
    public int getComparatorSignal() {
	return isGenerating.getValue() ? 15 : 0;
    }

    @Override
    public void onSubnodeDestroyed(TileMultiSubnode subnode) {
	level.destroyBlock(worldPosition, true);
    }

    @Override
    public int getSubdnodeComparatorSignal(TileMultiSubnode subnode) {
	return getComparatorSignal();
    }

    @Override
    public ItemInteractionResult onSubnodeUseWithItem(ItemStack used, Player player, InteractionHand hand,
	    BlockHitResult hit, TileMultiSubnode subnode) {
	return useWithItem(used, player, hand, hit);
    }

    @Override
    public InteractionResult onSubnodeUseWithoutItem(Player player, BlockHitResult hit, TileMultiSubnode subnode) {
	return useWithoutItem(player, hit);
    }

    @Override
    public Direction getFacingDirection() {
	return getFacing();
    }

}
