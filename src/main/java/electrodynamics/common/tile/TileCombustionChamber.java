package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.SoundRegister;
import electrodynamics.api.electricity.generator.IElectricGenerator;
import electrodynamics.api.sound.SoundAPI;
import electrodynamics.common.inventory.container.tile.ContainerCombustionChamber;
import electrodynamics.common.network.FluidUtilities;
import electrodynamics.common.settings.Constants;
import electrodynamics.common.tags.ElectrodynamicsTags;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerMulti;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.CapabilityUtils;
import electrodynamics.prefab.utilities.ElectricityUtils;
import electrodynamics.prefab.utilities.object.CachedTileOutput;
import electrodynamics.prefab.utilities.object.TransferPack;
import electrodynamics.prefab.utilities.tile.CombustionFuelSource;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.Tags.IOptionalNamedTag;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class TileCombustionChamber extends GenericTile implements IElectricGenerator {
	public static final int TICKS_PER_MILLIBUCKET = 200;
	public static final int TANK_CAPACITY = 100;
	public boolean running;
	public int burnTime;
	private CachedTileOutput output;
	private double multiplier = 1;

	private static IOptionalNamedTag<Fluid>[] FUELS;

	static {
		CombustionFuelSource.addFuelSource(ElectrodynamicsTags.Fluids.HYDROGEN, 1, 1);
		CombustionFuelSource.addFuelSource(ElectrodynamicsTags.Fluids.ETHANOL, 1, 1);
		FUELS = CombustionFuelSource.FUELS.keySet().toArray(new IOptionalNamedTag[0]);
	}

	public TileCombustionChamber(BlockPos worldPosition, BlockState blockState) {
		super(DeferredRegisters.TILE_COMBUSTIONCHAMBER.get(), worldPosition, blockState);
		addComponent(new ComponentDirection());
		addComponent(new ComponentTickable().tickServer(this::tickServer).tickClient(this::tickClient));
		addComponent(new ComponentPacketHandler().customPacketWriter(this::createPacket).guiPacketWriter(this::createPacket).customPacketReader(this::readPacket).guiPacketReader(this::readPacket));
		addComponent(new ComponentElectrodynamic(this).relativeOutput(Direction.EAST));
		addComponent(new ComponentInventory(this).size(1).bucketInputs(1).valid((slot, stack, i) -> CapabilityUtils.hasFluidItemCap(stack)));
		addComponent(new ComponentFluidHandlerMulti(this).setManualFluidTags(1, true, TANK_CAPACITY, FUELS).relativeInput(Direction.WEST));
		addComponent(new ComponentContainerProvider("container.combustionchamber").createMenu((id, player) -> new ContainerCombustionChamber(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
	}

	protected void tickServer(ComponentTickable tickable) {
		ComponentDirection direction = getComponent(ComponentType.Direction);
		Direction facing = direction.getDirection();
		if (output == null) {
			output = new CachedTileOutput(level, worldPosition.relative(facing.getClockWise()));
		}
		if (tickable.getTicks() % 40 == 0) {
			output.update();
		}
		if (tickable.getTicks() % 5 == 0) {
			this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendGuiPacketToTracking();
		}
		ComponentFluidHandlerMulti handler = getComponent(ComponentType.FluidHandler);
		FluidUtilities.drainItem(this);
		FluidTank tank = handler.getInputTanks()[0];
		if (burnTime <= 0) {
			running = false;
			if (tank.getFluidAmount() > 0) {
				CombustionFuelSource source = CombustionFuelSource.getSourceFromFluid(tank.getFluid().getFluid());
				tank.getFluid().shrink(source.getFluidUsage());
				multiplier = source.getPowerMultiplier();
				running = true;
				burnTime = TICKS_PER_MILLIBUCKET;
			}
		} else {
			running = true;
		}
		if (burnTime > 0) {
			--burnTime;
		}
		if (running && burnTime > 0 && output.valid()) {
			ElectricityUtils.receivePower(output.getSafe(), facing.getClockWise().getOpposite(), getProduced(), false);
		}
	}

	protected void tickClient(ComponentTickable tickable) {
		if (running && level.random.nextDouble() < 0.15) {
			level.addParticle(ParticleTypes.SMOKE, worldPosition.getX() + level.random.nextDouble(), worldPosition.getY() + level.random.nextDouble(), worldPosition.getZ() + level.random.nextDouble(), 0.0D, 0.0D, 0.0D);
		}
		if (running && tickable.getTicks() % 100 == 0) {
			SoundAPI.playSound(SoundRegister.SOUND_COMBUSTIONCHAMBER.get(), SoundSource.BLOCKS, 1, 1, worldPosition);
		}
	}

	protected void createPacket(CompoundTag nbt) {
		nbt.putInt("burnTime", burnTime);
		nbt.putBoolean("running", running);
	}

	protected void readPacket(CompoundTag nbt) {
		burnTime = nbt.getInt("burnTime");
		running = nbt.getBoolean("running");
	}

	@Override
	public void saveAdditional(CompoundTag compound) {
		compound.putInt("burnTime", burnTime);
		compound.putBoolean("running", running);
		super.saveAdditional(compound);
	}

	@Override
	public void load(CompoundTag compound) {
		super.load(compound);
		burnTime = compound.getInt("burnTime");
		running = compound.getBoolean("running");
	}

	@Override
	public void setMultiplier(double val) {

	}

	@Override
	public double getMultiplier() {
		return 1;
	}

	@Override
	public TransferPack getProduced() {
		ComponentElectrodynamic electro = getComponent(ComponentType.Electrodynamic);
		return TransferPack.joulesVoltage(Constants.COMBUSTIONCHAMBER_JOULES_PER_TICK * multiplier, electro.getVoltage());
	}
}
