package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.sound.DistanceSound;
import electrodynamics.api.tile.GenericTileTicking;
import electrodynamics.api.tile.components.ComponentType;
import electrodynamics.api.tile.components.type.ComponentDirection;
import electrodynamics.api.tile.components.type.ComponentElectrodynamic;
import electrodynamics.api.tile.components.type.ComponentFluidHandler;
import electrodynamics.api.tile.components.type.ComponentPacketHandler;
import electrodynamics.api.tile.components.type.ComponentTickable;
import electrodynamics.api.utilities.object.CachedTileOutput;
import electrodynamics.common.network.FluidUtilities;
import electrodynamics.common.settings.Constants;
import net.minecraft.client.Minecraft;
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
	    FluidState state = world.getBlockState(pos.offset(Direction.DOWN)).getFluidState();
	    if (isGenerating != (state.isSource() && state.getFluid() == Fluids.WATER)) {
		isGenerating = electro.getJoulesStored() > Constants.ELECTRICPUMP_USAGE_PER_TICK && state.isSource()
			&& state.getFluid() == Fluids.WATER;
		this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendCustomPacket();
	    }
	}
	if (isGenerating) {
	    electro.joules(electro.getJoulesStored() - Constants.ELECTRICPUMP_USAGE_PER_TICK);
	    FluidUtilities.receiveFluid(output.get(), direction.getOpposite(), new FluidStack(Fluids.WATER, 50), false);
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
		Minecraft.getInstance().getSoundHandler()
			.play(new DistanceSound(DeferredRegisters.SOUND_ELECTRICPUMP.get(), SoundCategory.BLOCKS, 1, 1, pos));
	    }
	}
    }
}
