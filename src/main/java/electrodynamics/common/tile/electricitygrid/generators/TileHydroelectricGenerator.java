package electrodynamics.common.tile.electricitygrid.generators;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerHydroelectricGenerator;
import electrodynamics.common.item.subtype.SubtypeItemUpgrade;
import electrodynamics.common.settings.Constants;
import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyType;
import electrodynamics.prefab.sound.SoundBarrierMethods;
import electrodynamics.prefab.sound.utils.ITickableSound;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.tile.components.type.ComponentInventory.InventoryBuilder;
import electrodynamics.prefab.utilities.ElectricityUtils;
import electrodynamics.prefab.utilities.object.CachedTileOutput;
import electrodynamics.prefab.utilities.object.TransferPack;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import electrodynamics.registers.ElectrodynamicsSounds;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.fluid.Fluids;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class TileHydroelectricGenerator extends GenericGeneratorTile implements ITickableSound {
	protected CachedTileOutput output;
	public Property<Boolean> isGenerating = property(new Property<>(PropertyType.Boolean, "isGenerating", false));
	public Property<Boolean> directionFlag = property(new Property<>(PropertyType.Boolean, "directionFlag", false));
	public Property<Double> multiplier = property(new Property<>(PropertyType.Double, "multiplier", 1.0));
	private Property<Boolean> hasRedstoneSignal = property(new Property<>(PropertyType.Boolean, "redstonesignal", false));
	public double savedTickRotation;
	public double rotationSpeed;

	private boolean isSoundPlaying = false;

	public TileHydroelectricGenerator() {
		super(ElectrodynamicsBlockTypes.TILE_HYDROELECTRICGENERATOR.get(), 2.25, SubtypeItemUpgrade.stator);
		addComponent(new ComponentTickable(this).tickServer(this::tickServer).tickCommon(this::tickCommon).tickClient(this::tickClient));
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentElectrodynamic(this, true, false).setOutputDirections(Direction.NORTH));
		addComponent(new ComponentInventory(this, InventoryBuilder.newInv().upgrades(1)).validUpgrades(ContainerHydroelectricGenerator.VALID_UPGRADES).valid(machineValidator()));
		addComponent(new ComponentContainerProvider(SubtypeMachine.hydroelectricgenerator, this).createMenu((id, player) -> new ContainerHydroelectricGenerator(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		Direction facing = getFacing();
		return super.getRenderBoundingBox().expandTowards(facing.getStepX(), facing.getStepY(), facing.getStepZ());
	}

	protected void tickServer(ComponentTickable tickable) {
		if (hasRedstoneSignal.get()) {
			isGenerating.set(false);
			return;
		}
		Direction facing = getFacing();
		if (output == null) {
			output = new CachedTileOutput(level, worldPosition.relative(facing.getOpposite()));
		}
		if (tickable.getTicks() % 20 == 0) {
			BlockPos shift = worldPosition.relative(facing);
			BlockState onShift = level.getBlockState(shift);
			isGenerating.set(onShift.getFluidState().getType() == Fluids.FLOWING_WATER);
			if (isGenerating.get() && onShift.getBlock() instanceof FlowingFluidBlock) {
				int amount = level.getBlockState(shift).getValue(FlowingFluidBlock.LEVEL);
				shift = worldPosition.relative(facing).relative(facing.getClockWise());
				onShift = level.getBlockState(shift);
				if (onShift.getBlock() instanceof FlowingFluidBlock && amount > onShift.getValue(FlowingFluidBlock.LEVEL)) {
					directionFlag.set(true);
				} else {
					shift = worldPosition.relative(facing).relative(facing.getClockWise().getOpposite());
					onShift = level.getBlockState(shift);
					if (onShift.getBlock() instanceof FlowingFluidBlock && amount >= onShift.getValue(FlowingFluidBlock.LEVEL)) {
						directionFlag.set(false);
					} else {
						isGenerating.set(false);
					}
				}
			}
			output.update(worldPosition.relative(facing.getOpposite()));
		}
		if (isGenerating.get() && output.valid()) {
			ElectricityUtils.receivePower(output.getSafe(), facing, getProduced(), false);
		}
	}

	protected void tickCommon(ComponentTickable tickable) {
		savedTickRotation += (directionFlag.get() ? 1 : -1) * rotationSpeed;
		rotationSpeed = MathHelper.clamp(rotationSpeed + 0.05 * (isGenerating.get() ? 1 : -1), 0.0, 1.0);
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
		return isGenerating.get();
	}

	@Override
	public void setMultiplier(double val) {
		multiplier.set(val);
	}

	@Override
	public double getMultiplier() {
		return multiplier.get();
	}

	@Override
	public TransferPack getProduced() {
		return TransferPack.ampsVoltage(Constants.HYDROELECTRICGENERATOR_AMPERAGE * (isGenerating.get() ? multiplier.get() : 0), this.<ComponentElectrodynamic>getComponent(IComponentType.Electrodynamic).getVoltage());

	}

	@Override
	public int getComparatorSignal() {
		return isGenerating.get() ? 15 : 0;
	}

	@Override
	public void onNeightborChanged(BlockPos neighbor, boolean blockStateTrigger) {
		if (level.isClientSide) {
			return;
		}
		hasRedstoneSignal.set(level.hasNeighborSignal(getBlockPos()));
	}
}