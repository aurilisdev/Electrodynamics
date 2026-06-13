package electrodynamics.common.tile.electricitygrid.generators;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerHydroelectricGenerator;
import electrodynamics.common.settings.ElectroConstants;
import electrodynamics.prefab.utilities.ElectricityUtils;
import electrodynamics.registers.ElectrodynamicsSounds;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
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

public class TileHydroelectricGenerator extends GenericGeneratorTile implements ITickableSound {
	protected CachedTileOutput output;
	public SingleProperty<Boolean> isGenerating = property(new SingleProperty<>(PropertyTypes.BOOLEAN, "isGenerating", false));
	public SingleProperty<Boolean> directionFlag = property(new SingleProperty<>(PropertyTypes.BOOLEAN, "directionFlag", false));
	public SingleProperty<Double> multiplier = property(new SingleProperty<>(PropertyTypes.DOUBLE, "multiplier", 1.0));
	public SingleProperty<Float> waterLevel = property(new SingleProperty<>(PropertyTypes.FLOAT, "waterLevel", 1.0f));
	private SingleProperty<Boolean> hasRedstoneSignal = property(new SingleProperty<>(PropertyTypes.BOOLEAN, "redstonesignal", false));
	public double savedTickRotation;
	public double rotationSpeed;

	private boolean isSoundPlaying = false;

	public TileHydroelectricGenerator(BlockPos worldPosition, BlockState blockState) {
		super(ElectrodynamicsTiles.TILE_HYDROELECTRICGENERATOR.get(), worldPosition, blockState, 2.25, SubtypeItemUpgrade.stator);
		addComponent(new ComponentTickable(this).tickServer(this::tickServer).tickCommon(this::tickCommon).tickClient(this::tickClient));
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentElectrodynamic(this, true, false).setOutputDirections(BlockEntityUtils.MachineDirection.BACK));
		addComponent(new ComponentInventory(this, ComponentInventory.InventoryBuilder.newInv().upgrades(1)).validUpgrades(ContainerHydroelectricGenerator.VALID_UPGRADES).valid(machineValidator()));
		addComponent(new ComponentContainerProvider(SubtypeMachine.hydroelectricgenerator.tag(), this).createMenu((id, player) -> new ContainerHydroelectricGenerator(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));
	}

	protected void tickServer(ComponentTickable tickable) {
		if (hasRedstoneSignal.getValue()) {
			isGenerating.setValue(false);
			return;
		}
		Direction facing = getFacing();
		if (output == null) {
			output = new CachedTileOutput(level, worldPosition.relative(facing.getOpposite()));
		}
		if (tickable.getTicks() % 5 == 0) {
			BlockPos frontPos = worldPosition.relative(facing);
			BlockState frontState = level.getBlockState(frontPos);

			boolean generating = frontState.getFluidState().getType() == Fluids.FLOWING_WATER;

			if (generating) {
				int frontLevel = frontState.getValue(LiquidBlock.LEVEL);

				Direction clockwise = facing.getClockWise();
				Direction counterClockwise = facing.getCounterClockWise();

				BlockState clockwiseState = level.getBlockState(frontPos.relative(clockwise));
				BlockState counterState = level.getBlockState(frontPos.relative(counterClockwise));

				boolean clockwiseWater = clockwiseState.getFluidState().getType() == Fluids.FLOWING_WATER;
				boolean counterWater = counterState.getFluidState().getType() == Fluids.FLOWING_WATER;
				
				boolean flowsFromClockwise = clockwiseWater && clockwiseState.getValue(LiquidBlock.LEVEL) < frontLevel;

				boolean flowsFromCounterClockwise = counterWater
						&& counterState.getValue(LiquidBlock.LEVEL) <= frontLevel;

				if (flowsFromClockwise && !flowsFromCounterClockwise) {
					directionFlag.setValue(true);
				} else if (flowsFromCounterClockwise && !flowsFromClockwise) {
					directionFlag.setValue(false);
				} else {
					// Either no clear side flow, or water comes from both sides and cancels out
					// here.
					generating = false;
				}
				int levelValue = frontState.getValue(LiquidBlock.LEVEL);

				// LEVEL 1 -> 1.0, LEVEL 7 -> 0.5
				waterLevel.setValue(1.0F - ((levelValue - 1.0F) / 6.0F) * 0.5F);
			} else {
				waterLevel.setValue(0.0F);
			}

			isGenerating.setValue(generating);
			output.update(worldPosition.relative(facing.getOpposite()));
		}
		if (isGenerating.getValue() && output.valid()) {
			ElectricityUtils.receivePower(output.getSafe(), facing, getProduced(), false);
		}
	}
	protected void tickCommon(ComponentTickable tickable) {
		double targetSpeed = isGenerating.getValue()
				? (directionFlag.getValue() ? -waterLevel.getValue() : waterLevel.getValue())
				: 0.0;

		rotationSpeed += Mth.clamp(targetSpeed - rotationSpeed, -0.05, 0.05);

		savedTickRotation += rotationSpeed;
	}

	protected void tickClient(ComponentTickable tickable) {
		if (!shouldPlaySound()) {
			return;
		}
		if (level.random.nextDouble() < 0.3) {
			Direction direction = getFacing();
			double d4 = level.random.nextDouble();
			double d5 = direction.getAxis() == Direction.Axis.X ? direction.getStepX() * (direction.getStepX() == -1 ? 0.2D : 1.2D) : d4;
			double d6 = level.random.nextDouble();
			double d7 = direction.getAxis() == Direction.Axis.Z ? direction.getStepZ() * (direction.getStepZ() == -1 ? 0.2D : 1.2D) : d4;
			level.addParticle(ParticleTypes.BUBBLE_COLUMN_UP, worldPosition.getX() + d5, worldPosition.getY() + d6, worldPosition.getZ() + d7, 0.0D, 0.0D, 0.0D);
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
		return TransferPack.ampsVoltage(ElectroConstants.HYDROELECTRICGENERATOR_AMPERAGE * (isGenerating.getValue() ? multiplier.getValue() * waterLevel.getValue() * Math.abs(rotationSpeed): 0), this.<ComponentElectrodynamic>getComponent(IComponentType.Electrodynamic).getVoltage());

	}

	@Override
	public int getComparatorSignal() {
		return isGenerating.getValue() ? 15 : 0;
	}

	@Override
	public void onNeightborChanged(BlockPos neighbor, boolean blockStateTrigger) {
		if (level.isClientSide) {
			return;
		}
		hasRedstoneSignal.setValue(level.hasNeighborSignal(getBlockPos()));
	}
	
	@Override
	public AABB getRenderBoundingBox() {
		Direction facing = getFacing();
		return super.getRenderBoundingBox().expandTowards(facing.getStepX(), facing.getStepY(), facing.getStepZ());
	}	

}
