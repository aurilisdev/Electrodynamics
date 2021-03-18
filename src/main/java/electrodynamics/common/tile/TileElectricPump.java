package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.tile.GenericTileTicking;
import electrodynamics.api.tile.components.ComponentType;
import electrodynamics.api.tile.components.type.ComponentDirection;
import electrodynamics.api.tile.components.type.ComponentElectrodynamic;
import electrodynamics.api.tile.components.type.ComponentFluidHandler;
import electrodynamics.api.tile.components.type.ComponentProcessor;
import electrodynamics.api.tile.components.type.ComponentTickable;
import electrodynamics.api.utilities.object.CachedTileOutput;
import electrodynamics.common.network.FluidUtilities;
import electrodynamics.common.settings.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fluids.FluidStack;

public class TileElectricPump extends GenericTileTicking {
    private boolean hasWater;

    public TileElectricPump() {
	super(DeferredRegisters.TILE_ELECTRICPUMP.get());
	addComponent(new ComponentElectrodynamic(this).setMaxJoules(Constants.ELECTRICPUMP_USAGE_PER_TICK * 20).addInputDirection(Direction.UP));
	addComponent(new ComponentDirection());
	addComponent(new ComponentTickable().addTickServer(this::tickServer).addTickClient(this::tickClient));
	addComponent(new ComponentFluidHandler(this).addFluidTank(Fluids.WATER, 0).addRelativeInputDirection(Direction.EAST));
    }

    protected CachedTileOutput output;

    protected void tickServer(ComponentTickable tickable) {
	Direction direction = this.<ComponentDirection>getComponent(ComponentType.Direction).getDirection().rotateY();
	if (output == null) {
	    output = new CachedTileOutput(world, pos.offset(direction));
	}
	if (tickable.getTicks() % 20 == 0) {
	    FluidState state = world.getBlockState(pos.offset(Direction.DOWN)).getFluidState();
	    hasWater = state.isSource() && state.getFluid() == Fluids.WATER;
	}
	ComponentElectrodynamic electro = getComponent(ComponentType.Electrodynamic);
	if (hasWater && electro.getJoulesStored() > Constants.ELECTRICPUMP_USAGE_PER_TICK) {
	    electro.setJoules(electro.getJoulesStored() - Constants.ELECTRICPUMP_USAGE_PER_TICK);
	    FluidUtilities.receiveFluid(output.get(), direction.getOpposite(), new FluidStack(Fluids.WATER, 50), false);
	}
    }

    protected void tickClient(ComponentTickable tickable) {
	ComponentProcessor processor = getComponent(ComponentType.Processor);
	if (processor.operatingTicks > 0 && world.rand.nextDouble() < 0.15) {
	    world.addParticle(ParticleTypes.SMOKE, pos.getX() + world.rand.nextDouble(), pos.getY() + world.rand.nextDouble() * 0.2 + 0.8,
		    pos.getZ() + world.rand.nextDouble(), 0.0D, 0.0D, 0.0D);
	}
	if (processor.operatingTicks > 0 && world.rand.nextDouble() < 0.15) {
	    world.addParticle(ParticleTypes.BUBBLE, pos.getX() + world.rand.nextDouble(), pos.getY() - world.rand.nextDouble() * 0.2 - .1,
		    pos.getZ() + world.rand.nextDouble(), 0.0D, 0.0D, 0.0D);
	}
	if (processor.operatingTicks > 0 && tickable.getTicks() % 200 == 0) {
	    Minecraft.getInstance().getSoundHandler()
		    .play(new SimpleSound(DeferredRegisters.SOUND_ELECTRICPUMP.get(), SoundCategory.BLOCKS, 1, 1, pos));
	}
    }
}
