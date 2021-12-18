package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.SoundRegister;
import electrodynamics.api.sound.SoundAPI;
import electrodynamics.common.network.FluidUtilities;
import electrodynamics.common.settings.Constants;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerMulti;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.object.CachedTileOutput;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;

public class TileElectricPump extends GenericTile {
	private boolean isGenerating;

	public TileElectricPump(BlockPos worldPosition, BlockState blockState) {
		super(DeferredRegisters.TILE_ELECTRICPUMP.get(), worldPosition, blockState);
		addComponent(new ComponentElectrodynamic(this).maxJoules(Constants.ELECTRICPUMP_USAGE_PER_TICK * 20).input(Direction.UP));
		addComponent(new ComponentDirection());
		addComponent(new ComponentTickable().tickServer(this::tickServer).tickClient(this::tickClient));
		addComponent(new ComponentPacketHandler().customPacketWriter(this::writeNBT).customPacketReader(this::readNBT));
		addComponent(new ComponentFluidHandlerMulti(this).setManualFluids(1, false, 0, Fluids.WATER).relativeInput(Direction.EAST));
	}

	protected CachedTileOutput output;

	protected void tickServer(ComponentTickable tickable) {
		Direction direction = this.<ComponentDirection>getComponent(ComponentType.Direction).getDirection().getClockWise();
		if (output == null) {
			output = new CachedTileOutput(level, worldPosition.relative(direction));
		}
		ComponentElectrodynamic electro = getComponent(ComponentType.Electrodynamic);

		if (tickable.getTicks() % 20 == 0) {
			output.update();
			FluidState state = level.getFluidState(worldPosition.relative(Direction.DOWN));
			if (isGenerating != (state.isSource() && state.getType() == Fluids.WATER)) {
				isGenerating = electro.getJoulesStored() > Constants.ELECTRICPUMP_USAGE_PER_TICK && state.isSource()
						&& state.getType() == Fluids.WATER;
				this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendCustomPacket();
			}
		}
		if (isGenerating && output.valid()) {
			electro.joules(electro.getJoulesStored() - Constants.ELECTRICPUMP_USAGE_PER_TICK);
			FluidUtilities.receiveFluid(output.getSafe(), direction.getOpposite(), new FluidStack(Fluids.WATER, 200), false);
		}
	}

	public void writeNBT(CompoundTag nbt) {
		nbt.putBoolean("isGenerating", isGenerating);

	}

	public void readNBT(CompoundTag nbt) {
		isGenerating = nbt.getBoolean("isGenerating");
	}

	protected void tickClient(ComponentTickable tickable) {
		if (isGenerating) {
			if (level.random.nextDouble() < 0.15) {
				level.addParticle(ParticleTypes.SMOKE, worldPosition.getX() + level.random.nextDouble(),
						worldPosition.getY() + level.random.nextDouble() * 0.2 + 0.8, worldPosition.getZ() + level.random.nextDouble(), 0.0D, 0.0D,
						0.0D);
			}
			level.addParticle(ParticleTypes.BUBBLE, worldPosition.getX() + level.random.nextDouble(),
					worldPosition.getY() - level.random.nextDouble() * 0.2 - .1, worldPosition.getZ() + level.random.nextDouble(), 0.0D, 0.0D, 0.0D);
			if (tickable.getTicks() % 200 == 0) {
				SoundAPI.playSound(SoundRegister.SOUND_ELECTRICPUMP.get(), SoundSource.BLOCKS, 1, 1, worldPosition);
			}
		}
	}
}
