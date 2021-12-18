package electrodynamics.common.tile;

import java.util.List;

import electrodynamics.DeferredRegisters;
import electrodynamics.SoundRegister;
import electrodynamics.api.sound.SoundAPI;
import electrodynamics.common.inventory.container.ContainerCombustionChamber;
import electrodynamics.common.network.ElectricityUtilities;
import electrodynamics.common.settings.Constants;
import electrodynamics.common.tags.ElectrodynamicsTags;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerMulti;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.object.CachedTileOutput;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class TileCombustionChamber extends GenericTile {
	public static final int TICKS_PER_MILLIBUCKET = 200;
	public static final int TANK_CAPACITY = 100;
	public boolean running = false;
	private int burnTime;
	public int clientAmount = 0;
	private CachedTileOutput output;

	public TileCombustionChamber(BlockPos worldPosition, BlockState blockState) {
		super(DeferredRegisters.TILE_COMBUSTIONCHAMBER.get(), worldPosition, blockState);
		addComponent(new ComponentDirection());
		addComponent(new ComponentTickable().tickServer(this::tickServer).tickClient(this::tickClient));
		addComponent(new ComponentPacketHandler().customPacketReader(this::readNBT).customPacketWriter(this::writeNBT).guiPacketReader(this::readNBT)
				.guiPacketWriter(this::writeNBT));
		addComponent(new ComponentElectrodynamic(this).relativeOutput(Direction.EAST));
		addComponent(new ComponentFluidHandlerMulti(this).setManualFluidTags(1, true, TANK_CAPACITY, ElectrodynamicsTags.Fluids.ETHANOL)
				.relativeInput(Direction.WEST));
		addComponent(new ComponentContainerProvider("container.combustionchamber")
				.createMenu((id, player) -> new ContainerCombustionChamber(id, player, null, getCoordsArray())));
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
		ComponentFluidHandlerMulti tank = getComponent(ComponentType.FluidHandler);
		ComponentElectrodynamic electro = getComponent(ComponentType.Electrodynamic);
		if (burnTime <= 0) {
			boolean shouldSend = !running;
			running = false;
			// can you think of a better name
			List<Fluid> ethanols = ElectrodynamicsTags.Fluids.ETHANOL.getValues();
			for (Fluid fluid : ethanols) {
				FluidStack stack = tank.getTankFromFluid(fluid, true).getFluid();
				if (stack.getAmount() > 0) {
					stack.setAmount(stack.getAmount() - 1);
					running = true;
					burnTime = TICKS_PER_MILLIBUCKET;
					shouldSend = true;
					break;
				}
			}
			if (shouldSend) {
				this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendGuiPacketToTracking();
			}
		} else {
			running = true;
			burnTime--;
		}
		if (running && burnTime > 0 && output.valid()) {
			ElectricityUtilities.receivePower(output.getSafe(), facing.getClockWise().getOpposite(),
					TransferPack.joulesVoltage(Constants.COMBUSTIONCHAMBER_JOULES_PER_TICK, electro.getVoltage()), false);
		}
	}

	protected void tickClient(ComponentTickable tickable) {
		if (running && level.random.nextDouble() < 0.15) {
			level.addParticle(ParticleTypes.SMOKE, worldPosition.getX() + level.random.nextDouble(), worldPosition.getY() + level.random.nextDouble(),
					worldPosition.getZ() + level.random.nextDouble(), 0.0D, 0.0D, 0.0D);
		}
		if (running && tickable.getTicks() % 100 == 0) {
			SoundAPI.playSound(SoundRegister.SOUND_COMBUSTIONCHAMBER.get(), SoundSource.BLOCKS, 1, 1, worldPosition);
		}
	}

	protected void writeNBT(CompoundTag nbt) {
		nbt.putBoolean("running", running);
		List<Fluid> ethanols = ElectrodynamicsTags.Fluids.ETHANOL.getValues();
		ComponentFluidHandlerMulti tank = getComponent(ComponentType.FluidHandler);
		for (Fluid fluid : ethanols) {
			FluidStack stack = tank.getTankFromFluid(fluid, true).getFluid();
			if (stack.getAmount() > 0) {
				clientAmount = stack.getAmount();
				break;
			}
		}
		nbt.putInt("clientAmount", clientAmount);
	}

	protected void readNBT(CompoundTag nbt) {
		running = nbt.getBoolean("running");
		clientAmount = nbt.getInt("clientAmount");
	}
}
