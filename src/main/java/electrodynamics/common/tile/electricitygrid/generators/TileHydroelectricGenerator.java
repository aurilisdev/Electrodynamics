package electrodynamics.common.tile.electricitygrid.generators;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerHydroelectricGenerator;
import electrodynamics.common.settings.ElectrodynamicsConfig;
import electrodynamics.prefab.utilities.ElectricityUtils;
import electrodynamics.registers.ElectrodynamicsSounds;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
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

public class TileHydroelectricGenerator extends GenericGeneratorTile implements ITickableSound {

    public SingleProperty<Boolean> isGenerating = property(
	    new SingleProperty<>(getPropertyManager(), PropertyTypes.BOOLEAN, "isGenerating", false));
    public SingleProperty<Boolean> directionFlag = property(
	    new SingleProperty<>(getPropertyManager(), PropertyTypes.BOOLEAN, "directionFlag", false));
    public SingleProperty<Double> multiplier = property(
	    new SingleProperty<>(getPropertyManager(), PropertyTypes.DOUBLE, "multiplier", 1.0));
    public SingleProperty<Float> waterLevel = property(
	    new SingleProperty<>(getPropertyManager(), PropertyTypes.FLOAT, "waterLevel", 1.0F));

    private final SingleProperty<Boolean> hasRedstoneSignal = property(
	    new SingleProperty<>(getPropertyManager(), PropertyTypes.BOOLEAN, "redstonesignal", false));

    public double savedTickRotation;
    public double rotationSpeed;

    private boolean isSoundPlaying;

    public TileHydroelectricGenerator(BlockPos worldPosition, BlockState blockState) {
	super(ElectrodynamicsTiles.TILE_HYDROELECTRICGENERATOR.get(), worldPosition, blockState, 2.25,
		SubtypeItemUpgrade.stator);
	addComponent(new ComponentTickable(this).tickServer(this::tickServer).tickCommon(this::tickCommon)
		.tickClient(this::tickClient));
	addComponent(new ComponentElectrodynamic(this, true, false)
		.setOutputDirections(BlockEntityUtils.MachineDirection.BACK));
	addComponent(new ComponentInventory(this, ComponentInventory.InventoryBuilder.newInv().upgrades(1))
		.validUpgrades(ContainerHydroelectricGenerator.VALID_UPGRADES).valid(machineValidator()));
	addComponent(new ComponentContainerProvider(SubtypeMachine.hydroelectricgenerator.tag(), this)
		.createMenu((id, player) -> new ContainerHydroelectricGenerator(id, player,
			requireComponent(IComponentType.Inventory), getCoordsArray())));
    }

    protected void tickServer(Level level, ComponentTickable tickable) {
	if (hasRedstoneSignal.getValue()) {
	    isGenerating.setValue(false);
	    return;
	}
	Direction facing = getFacing();
	if (tickable.getTicks() % 5 == 0) {
	    BlockPos frontPos = worldPosition.relative(facing);
	    BlockState frontState = level.getBlockState(frontPos);
	    boolean generating = frontState.getFluidState().getType() == Fluids.FLOWING_WATER;
	    if (generating) {
		int frontLevel = frontState.getValue(LiquidBlock.LEVEL);
		Direction clockwise = facing.getClockWise();
		Direction counterClockwise = facing.getCounterClockWise();
		BlockState clockwiseState = level.getBlockState(frontPos.relative(clockwise));
		BlockState counterClockwiseState = level.getBlockState(frontPos.relative(counterClockwise));
		boolean clockwiseWater = clockwiseState.getFluidState().getType() == Fluids.FLOWING_WATER;
		boolean counterClockwiseWater = counterClockwiseState.getFluidState().getType() == Fluids.FLOWING_WATER;
		boolean flowsFromClockwise = clockwiseWater && clockwiseState.getValue(LiquidBlock.LEVEL) < frontLevel;
		boolean flowsFromCounterClockwise = counterClockwiseWater
			&& counterClockwiseState.getValue(LiquidBlock.LEVEL) <= frontLevel;
		if (flowsFromClockwise && !flowsFromCounterClockwise)
		    directionFlag.setValue(true);
		else if (flowsFromCounterClockwise && !flowsFromClockwise)
		    directionFlag.setValue(false);
		else
		    generating = false;
		int levelValue = frontState.getValue(LiquidBlock.LEVEL);
		waterLevel.setValue(1.0F - (levelValue - 1.0F) / 6.0F * 0.5F);
	    } else {
		waterLevel.setValue(0.0F);
	    }
	    isGenerating.setValue(generating);
	}
	if (isGenerating.getValue()) {
	    BlockEntity target = level.getBlockEntity(worldPosition.relative(facing.getOpposite()));
	    if (target != null && !target.isRemoved())
		ElectricityUtils.receivePower(target, facing, getProduced(), false);
	}
    }

    protected void tickCommon(Level level, ComponentTickable tickable) {
	double targetSpeed = isGenerating.getValue()
		? directionFlag.getValue() ? -waterLevel.getValue() : waterLevel.getValue()
		: 0.0;
	rotationSpeed += Mth.clamp(targetSpeed - rotationSpeed, -0.05, 0.05);
	savedTickRotation += rotationSpeed;
    }

    protected void tickClient(Level level, ComponentTickable tickable) {
	if (!shouldPlaySound())
	    return;
	if (level.random.nextDouble() < 0.3) {
	    Direction direction = getFacing();
	    double horizontalOffset = level.random.nextDouble();
	    double particleX = direction.getAxis() == Direction.Axis.X
		    ? direction.getStepX() * (direction.getStepX() == -1 ? 0.2D : 1.2D)
		    : horizontalOffset;
	    double particleY = level.random.nextDouble();
	    double particleZ = direction.getAxis() == Direction.Axis.Z
		    ? direction.getStepZ() * (direction.getStepZ() == -1 ? 0.2D : 1.2D)
		    : horizontalOffset;
	    level.addParticle(ParticleTypes.BUBBLE_COLUMN_UP, worldPosition.getX() + particleX,
		    worldPosition.getY() + particleY, worldPosition.getZ() + particleZ, 0.0D, 0.0D, 0.0D);
	}
	if (!isSoundPlaying) {
	    isSoundPlaying = true;
	    SoundBarrierMethods.playTileSound(ElectrodynamicsSounds.SOUND_HYDROELECTRICGENERATOR.get(), this, true);
	}
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
	double amperage = isGenerating.getValue()
		? multiplier.getValue() * waterLevel.getValue() * Math.abs(rotationSpeed)
			* ElectrodynamicsConfig.INSTANCE.HYDROELECTRICGENERATOR_AMPERAGE.get()
		: 0;
	return TransferPack.ampsVoltage(amperage, electro.getVoltage());
    }

    @Override
    public int getComparatorSignal(Level level) {
	return isGenerating.getValue() ? 15 : 0;
    }

    @Override
    public void onNeighbourChanged(LevelReader reader, BlockPos neighbor, boolean blockStateTrigger) {
	if (reader instanceof Level level && !level.isClientSide)
	    hasRedstoneSignal.setValue(level.hasNeighborSignal(getBlockPos()));
    }
}