package electrodynamics.common.tile;

import electrodynamics.common.block.VoxelShapes;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerHydroelectricGenerator;
import electrodynamics.common.item.subtype.SubtypeItemUpgrade;
import electrodynamics.common.settings.Constants;
import electrodynamics.common.tile.generic.GenericGeneratorTile;
import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyType;
import electrodynamics.prefab.sound.SoundBarrierMethods;
import electrodynamics.prefab.sound.utils.ITickableSoundTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.ElectricityUtils;
import electrodynamics.prefab.utilities.object.CachedTileOutput;
import electrodynamics.prefab.utilities.object.TransferPack;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import electrodynamics.registers.ElectrodynamicsSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TileHydroelectricGenerator extends GenericGeneratorTile implements ITickableSoundTile {
	protected CachedTileOutput output;
	public Property<Boolean> isGenerating = property(new Property<Boolean>(PropertyType.Boolean, "isGenerating", false));
	public Property<Boolean> directionFlag = property(new Property<Boolean>(PropertyType.Boolean, "directionFlag", false));
	public Property<Double> multiplier = property(new Property<Double>(PropertyType.Double, "multiplier", 1.0));
	public double savedTickRotation;
	public double rotationSpeed;
	
	private boolean isSoundPlaying = false;

	public TileHydroelectricGenerator(BlockPos worldPosition, BlockState blockState) {
		super(ElectrodynamicsBlockTypes.TILE_HYDROELECTRICGENERATOR.get(), worldPosition, blockState, 2.25, SubtypeItemUpgrade.stator);
		addComponent(new ComponentDirection());
		addComponent(new ComponentTickable().tickServer(this::tickServer).tickCommon(this::tickCommon).tickClient(this::tickClient));
		addComponent(new ComponentPacketHandler());
		addComponent(new ComponentElectrodynamic(this).relativeOutput(Direction.NORTH));
		addComponent(new ComponentInventory(this).size(1).slotFaces(0, Direction.values()).validUpgrades(ContainerHydroelectricGenerator.VALID_UPGRADES).valid(machineValidator()));
		addComponent(new ComponentContainerProvider(SubtypeMachine.hydroelectricgenerator).createMenu((id, player) -> new ContainerHydroelectricGenerator(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
	}

	@Override
	public AABB getRenderBoundingBox() {
		ComponentDirection direction = getComponent(ComponentType.Direction);
		Direction facing = direction.getDirection();
		return super.getRenderBoundingBox().expandTowards(facing.getStepX(), facing.getStepY(), facing.getStepZ());
	}

	protected void tickServer(ComponentTickable tickable) {
		ComponentDirection direction = getComponent(ComponentType.Direction);
		Direction facing = direction.getDirection();
		if (output == null) {
			output = new CachedTileOutput(level, worldPosition.relative(facing.getOpposite()));
		}
		if (tickable.getTicks() % 20 == 0) {
			BlockPos shift = worldPosition.relative(facing);
			BlockState onShift = level.getBlockState(shift);
			isGenerating.set(onShift.getFluidState().getType() == Fluids.FLOWING_WATER);
			if (isGenerating.get() && onShift.getBlock() instanceof LiquidBlock) {
				int amount = level.getBlockState(shift).getValue(LiquidBlock.LEVEL);
				shift = worldPosition.relative(facing).relative(facing.getClockWise());
				onShift = level.getBlockState(shift);
				if (onShift.getBlock() instanceof LiquidBlock && amount > onShift.getValue(LiquidBlock.LEVEL)) {
					directionFlag.set(true);
				} else {
					shift = worldPosition.relative(facing).relative(facing.getClockWise().getOpposite());
					onShift = level.getBlockState(shift);
					if (onShift.getBlock() instanceof LiquidBlock && amount >= onShift.getValue(LiquidBlock.LEVEL)) {
						directionFlag.set(false);
					} else {
						isGenerating.set(false);
					}
				}
			}
			output.update(worldPosition.relative(facing.getOpposite()));
		}
		if (isGenerating.get() == Boolean.TRUE && output.valid()) {
			ElectricityUtils.receivePower(output.getSafe(), facing, getProduced(), false);
		}
	}

	protected void tickCommon(ComponentTickable tickable) {
		savedTickRotation += (directionFlag.get() ? 1 : -1) * rotationSpeed;
		rotationSpeed = Mth.clamp(rotationSpeed + 0.05 * (isGenerating.get() == Boolean.TRUE ? 1 : -1), 0.0, 1.0);
	}

	protected void tickClient(ComponentTickable tickable) {
		if (isGenerating.get() && level.random.nextDouble() < 0.3) {
			Direction direction = this.<ComponentDirection>getComponent(ComponentType.Direction).getDirection();
			double d4 = level.random.nextDouble();
			double d5 = direction.getAxis() == Direction.Axis.X ? direction.getStepX() * (direction.getStepX() == -1 ? 0.2D : 1.2D) : d4;
			double d6 = level.random.nextDouble();
			double d7 = direction.getAxis() == Direction.Axis.Z ? direction.getStepZ() * (direction.getStepZ() == -1 ? 0.2D : 1.2D) : d4;
			level.addParticle(ParticleTypes.BUBBLE_COLUMN_UP, worldPosition.getX() + d5, worldPosition.getY() + d6, worldPosition.getZ() + d7, 0.0D, 0.0D, 0.0D);
		}
		if (shouldPlaySound() && !isSoundPlaying) {
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
		return TransferPack.ampsVoltage(Constants.HYDROELECTRICGENERATOR_AMPERAGE * (isGenerating.get() ? multiplier.get() : 0), this.<ComponentElectrodynamic>getComponent(ComponentType.Electrodynamic).getVoltage());
	}

	static {
		VoxelShape shape = Shapes.empty();
		shape = Shapes.join(shape, Shapes.box(0.06, 0.25, 0.250625, 0.9975, 0.375, 0.750625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.06, 0, 0.09125, 0.9975, 0.25, 0.90375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.56, 0.375, 0.438125, 0.9975, 0.5, 0.563125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.685, 0.375, 0.375625, 0.935, 0.5625, 0.625625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.8725, 0.317394375, 0.33602125, 0.935, 0.379894375, 0.39852125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.685, 0.317394375, 0.33602125, 0.7475, 0.379894375, 0.39852125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.685, 0.317394375, 0.60272875, 0.7475, 0.379894375, 0.66522875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.8725, 0.317394375, 0.60272875, 0.935, 0.379894375, 0.66522875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.06, 0.375, 0.250625, 0.4975, 0.75, 0.750625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(-0.0025, 0.25, 0.250625, 0.06, 0.75, 0.750625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.4975, 0.375, 0.375625, 0.56, 0.5625, 0.625625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.4975, 0.375, 0.313125, 0.56, 0.4375, 0.375625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.4975, 0.375, 0.625625, 0.56, 0.4375, 0.688125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(1.294375, 0.125, 0.125625, 1.3725, 0.75, 0.188125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(1.294375, 0.75, 0.125625, 1.3725, 0.8125, 0.875625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(1.294375, 0.0625, 0.125625, 1.3725, 0.125, 0.875625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(1.294375, 0.125, 0.813125, 1.3725, 0.75, 0.875625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.9975, 0.125, 0.813125, 1.075625, 0.75, 0.875625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.9975, 0.0625, 0.125625, 1.075625, 0.125, 0.875625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.9975, 0.75, 0.125625, 1.075625, 0.8125, 0.875625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.9975, 0.125, 0.125625, 1.075625, 0.75, 0.188125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(1.06, -0.125, 0.438125, 1.31, 1, 0.563125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(1.06, 0.375, -0.061875, 1.31, 0.5, 1.063125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.9975, 0.375, 0.438125, 1.3725, 0.5, 0.563125), BooleanOp.OR);

		VoxelShapes.registerShape(SubtypeMachine.hydroelectricgenerator, shape, Direction.EAST);
	}
}
