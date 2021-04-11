package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.SoundRegister;
import electrodynamics.api.sound.SoundAPI;
import electrodynamics.common.network.FluidUtilities;
import electrodynamics.common.settings.Constants;
import electrodynamics.prefab.tile.GenericTileTicking;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandler;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.object.CachedTileOutput;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fluids.FluidStack;

public class TileElectricPump extends GenericTileTicking {
    private boolean isGenerating;

    public TileElectricPump() {
	super(DeferredRegisters.TILE_ELECTRICPUMP.get());
	addComponent(new ComponentElectrodynamic(this).maxJoules(Constants.ELECTRICPUMP_USAGE_PER_TICK * 20).input(Direction.UP));
	addComponent(new ComponentDirection());
	addComponent(new ComponentTickable().tickServer(this::tickServer).tickClient(this::tickClient));
	addComponent(new ComponentPacketHandler().customPacketWriter(this::writeNBT).customPacketReader(this::readNBT));
	addComponent(new ComponentFluidHandler(this).fluidTank(Fluids.WATER, 0).relativeInput(Direction.EAST));
    }

    protected CachedTileOutput output;

    protected void tickServer(ComponentTickable tickable) {
	Direction direction = this.<ComponentDirection>getComponent(ComponentType.Direction).getDirection().rotateY();
	if (output == null) {
	    output = new CachedTileOutput(world, pos.offset(direction));
	}
	ComponentElectrodynamic electro = getComponent(ComponentType.Electrodynamic);

	if (tickable.getTicks() % 20 == 0) {
	    output.update();
	    FluidState state = world.getFluidState(pos.offset(Direction.DOWN));
	    if (isGenerating != (state.isSource() && state.getFluid() == Fluids.WATER)) {
		isGenerating = electro.getJoulesStored() > Constants.ELECTRICPUMP_USAGE_PER_TICK && state.isSource()
			&& state.getFluid() == Fluids.WATER;
		this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendCustomPacket();
	    }
	}
	if (isGenerating && output.valid()) {
	    electro.joules(electro.getJoulesStored() - Constants.ELECTRICPUMP_USAGE_PER_TICK);
	    FluidUtilities.receiveFluid(output.getSafe(), direction.getOpposite(), new FluidStack(Fluids.WATER, 200), false);
	}
    }

    public void writeNBT(CompoundNBT nbt) {
	nbt.putBoolean("isGenerating", isGenerating);

    }

    public void readNBT(CompoundNBT nbt) {
	isGenerating = nbt.getBoolean("isGenerating");
    }

    protected void tickClient(ComponentTickable tickable) {
	if (isGenerating) {
	    if (world.rand.nextDouble() < 0.15) {
		world.addParticle(ParticleTypes.SMOKE, pos.getX() + world.rand.nextDouble(), pos.getY() + world.rand.nextDouble() * 0.2 + 0.8,
			pos.getZ() + world.rand.nextDouble(), 0.0D, 0.0D, 0.0D);
	    }
	    world.addParticle(ParticleTypes.BUBBLE, pos.getX() + world.rand.nextDouble(), pos.getY() - world.rand.nextDouble() * 0.2 - .1,
		    pos.getZ() + world.rand.nextDouble(), 0.0D, 0.0D, 0.0D);
	    if (tickable.getTicks() % 200 == 0) {
		SoundAPI.playSound(SoundRegister.SOUND_ELECTRICPUMP.get(), SoundCategory.BLOCKS, 1, 1, pos);
	    }
	}
    }
}
