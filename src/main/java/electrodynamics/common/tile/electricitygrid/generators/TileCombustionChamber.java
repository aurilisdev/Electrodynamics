package electrodynamics.common.tile.electricitygrid.generators;

import electrodynamics.api.electricity.generator.IElectricGenerator;
import electrodynamics.common.block.VoxelShapes;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerCombustionChamber;
import electrodynamics.common.network.utils.FluidUtilities;
import electrodynamics.common.reloadlistener.CombustionFuelRegister;
import electrodynamics.common.settings.Constants;
import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyType;
import electrodynamics.prefab.sound.SoundBarrierMethods;
import electrodynamics.prefab.sound.utils.ITickableSound;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerMulti;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentInventory.InventoryBuilder;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.tile.types.GenericMaterialTile;
import electrodynamics.prefab.utilities.CapabilityUtils;
import electrodynamics.prefab.utilities.ElectricityUtils;
import electrodynamics.prefab.utilities.object.CachedTileOutput;
import electrodynamics.prefab.utilities.object.CombustionFuelSource;
import electrodynamics.prefab.utilities.object.TransferPack;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import electrodynamics.registers.ElectrodynamicsSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class TileCombustionChamber extends GenericMaterialTile implements IElectricGenerator, ITickableSound {

	public static final int TICKS_PER_MILLIBUCKET = 200;
	public static final int TANK_CAPACITY = 1000;
	public Property<Boolean> running = property(new Property<>(PropertyType.Boolean, "running", false));
	public Property<Integer> burnTime = property(new Property<>(PropertyType.Integer, "burnTime", 0));
	private double fuelMultiplier = 1;
	private CachedTileOutput output;
	// for future upgrades
	private Property<Double> multiplier = property(new Property<>(PropertyType.Double, "multiplier", 1.0));
	private Property<Boolean> hasRedstoneSignal = property(new Property<>(PropertyType.Boolean, "redstonesignal", false));

	private boolean isSoundPlaying = false;

	public TileCombustionChamber(BlockPos worldPosition, BlockState blockState) {
		super(ElectrodynamicsBlockTypes.TILE_COMBUSTIONCHAMBER.get(), worldPosition, blockState);
		addComponent(new ComponentDirection(this));
		addComponent(new ComponentTickable(this).tickServer(this::tickServer).tickClient(this::tickClient));
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentElectrodynamic(this).relativeOutput(Direction.EAST));
		addComponent(new ComponentInventory(this, InventoryBuilder.newInv().bucketInputs(1)).valid((slot, stack, i) -> CapabilityUtils.hasFluidItemCap(stack)));
		addComponent(new ComponentFluidHandlerMulti(this).setInputTanks(1, TANK_CAPACITY).setInputDirections(Direction.WEST).setInputFluidTags(CombustionFuelRegister.INSTANCE.getFluidTags()));
		addComponent(new ComponentContainerProvider(SubtypeMachine.combustionchamber, this).createMenu((id, player) -> new ContainerCombustionChamber(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
	}

	protected void tickServer(ComponentTickable tickable) {
		if (hasRedstoneSignal.get()) {
			running.set(false);
			return;
		}
		ComponentDirection direction = getComponent(ComponentType.Direction);
		Direction facing = direction.getDirection();
		if (output == null) {
			output = new CachedTileOutput(level, worldPosition.relative(facing.getClockWise()));
		}
		if (tickable.getTicks() % 40 == 0) {
			output.update(worldPosition.relative(facing.getClockWise()));
		}
		ComponentFluidHandlerMulti handler = getComponent(ComponentType.FluidHandler);
		FluidUtilities.drainItem(this, handler.getInputTanks());
		FluidTank tank = handler.getInputTanks()[0];
		if (burnTime.get() <= 0) {
			running.set(false);
			if (tank.getFluidAmount() > 0) {
				CombustionFuelSource source = CombustionFuelRegister.INSTANCE.getFuelFromFluid(tank.getFluid());
				if (!source.isEmpty()) {
					tank.drain(new FluidStack(tank.getFluid().getFluid(), source.getFuelUsage()), FluidAction.EXECUTE);
					fuelMultiplier = source.getPowerMultiplier();
					running.set(true);
					burnTime.set(TICKS_PER_MILLIBUCKET);
				}

			}
		} else {
			running.set(true);
		}
		if (burnTime.get() > 0) {
			burnTime.set(burnTime.get() - 1);
		}
		if (running.get() && burnTime.get() > 0 && output.valid()) {
			ElectricityUtils.receivePower(output.getSafe(), facing.getClockWise().getOpposite(), getProduced(), false);
		}
	}

	protected void tickClient(ComponentTickable tickable) {
		if (!running.get()) {
			return;
		}

		if (level.random.nextDouble() < 0.15) {
			level.addParticle(ParticleTypes.SMOKE, worldPosition.getX() + level.random.nextDouble(), worldPosition.getY() + level.random.nextDouble(), worldPosition.getZ() + level.random.nextDouble(), 0.0D, 0.0D, 0.0D);
		}

		if (!isSoundPlaying) {
			isSoundPlaying = true;
			SoundBarrierMethods.playTileSound(ElectrodynamicsSounds.SOUND_COMBUSTIONCHAMBER.get(), this, true);
		}
	}

	@Override
	public void setNotPlaying() {
		isSoundPlaying = false;
	}

	@Override
	public boolean shouldPlaySound() {
		return running.get();
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
		ComponentElectrodynamic electro = getComponent(ComponentType.Electrodynamic);
		return TransferPack.joulesVoltage(Constants.COMBUSTIONCHAMBER_JOULES_PER_TICK * fuelMultiplier * multiplier.get(), electro.getVoltage());
	}

	@Override
	public int getComparatorSignal() {
		return running.get() ? 15 : 0;
	}

	@Override
	public void onNeightborChanged(BlockPos neighbor) {
		if (level.isClientSide) {
			return;
		}
		hasRedstoneSignal.set(level.hasNeighborSignal(getBlockPos()));
	}

	static {

		VoxelShape shape = Block.box(1, 0, 0, 15, 1, 16);

		shape = Shapes.or(shape, Block.box(2, 1, 0, 3, 12, 1));
		shape = Shapes.or(shape, Block.box(13, 1, 0, 14, 12, 1));
		shape = Shapes.or(shape, Block.box(3, 12, 0, 13, 13, 1));
		shape = Shapes.or(shape, Block.box(4, 3, 0, 12, 11, 1));

		shape = Shapes.or(shape, Block.box(2, 12, 1, 3, 13, 15));
		shape = Shapes.or(shape, Block.box(3, 1, 1, 13, 12, 15));
		shape = Shapes.or(shape, Block.box(13, 12, 1, 14, 13, 15));

		shape = Shapes.or(shape, Block.box(2, 1, 15, 3, 12, 16));
		shape = Shapes.or(shape, Block.box(13, 1, 15, 14, 12, 16));
		shape = Shapes.or(shape, Block.box(3, 12, 15, 13, 13, 16));
		shape = Shapes.or(shape, Block.box(4, 3, 15, 12, 11, 16));

		shape = Shapes.or(shape, Block.box(2, 3, 1, 3, 4, 2));
		shape = Shapes.or(shape, Block.box(1, 4, 1, 2, 10, 2));
		shape = Shapes.or(shape, Block.box(2, 10, 1, 3, 11, 2));
		shape = Shapes.or(shape, Block.box(2, 3, 7, 3, 4, 9));
		shape = Shapes.or(shape, Block.box(1, 4, 7, 2, 10, 9));
		shape = Shapes.or(shape, Block.box(2, 10, 7, 3, 11, 9));
		shape = Shapes.or(shape, Block.box(2, 3, 14, 3, 4, 15));
		shape = Shapes.or(shape, Block.box(1, 4, 14, 2, 10, 15));
		shape = Shapes.or(shape, Block.box(2, 10, 14, 3, 11, 15));

		shape = Shapes.or(shape, Block.box(13, 3, 1, 14, 4, 2));
		shape = Shapes.or(shape, Block.box(14, 4, 1, 15, 10, 2));
		shape = Shapes.or(shape, Block.box(13, 10, 1, 14, 11, 2));
		shape = Shapes.or(shape, Block.box(13, 3, 7, 14, 4, 9));
		shape = Shapes.or(shape, Block.box(14, 4, 7, 15, 10, 9));
		shape = Shapes.or(shape, Block.box(13, 10, 7, 14, 11, 9));
		shape = Shapes.or(shape, Block.box(13, 3, 14, 14, 4, 15));
		shape = Shapes.or(shape, Block.box(14, 4, 14, 15, 10, 15));
		shape = Shapes.or(shape, Block.box(13, 10, 14, 14, 11, 15));

		shape = Shapes.or(shape, Block.box(4, 12, 4, 5, 13, 5));
		shape = Shapes.or(shape, Block.box(4, 12, 11, 5, 13, 12));

		shape = Shapes.or(shape, Block.box(7, 12, 2, 9, 14, 3));
		shape = Shapes.or(shape, Block.box(7, 14, 3, 9, 15, 4));
		shape = Shapes.or(shape, Block.box(5, 12, 4, 11, 15, 12));
		shape = Shapes.or(shape, Block.box(7, 12, 13, 9, 14, 14));
		shape = Shapes.or(shape, Block.box(7, 14, 12, 9, 15, 13));

		shape = Shapes.or(shape, Block.box(11, 12, 4, 12, 13, 5));
		shape = Shapes.or(shape, Block.box(11, 12, 11, 12, 13, 12));

		VoxelShapes.registerShape(SubtypeMachine.combustionchamber, shape, Direction.WEST);

	}

}
