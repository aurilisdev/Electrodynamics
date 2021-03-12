package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.utilities.CachedTileOutput;
import electrodynamics.common.network.FluidUtilities;
import electrodynamics.common.settings.Constants;
import electrodynamics.common.tile.generic.GenericTileTicking;
import electrodynamics.common.tile.generic.component.ComponentType;
import electrodynamics.common.tile.generic.component.type.ComponentDirection;
import electrodynamics.common.tile.generic.component.type.ComponentElectrodynamic;
import electrodynamics.common.tile.generic.component.type.ComponentFluidHandler;
import electrodynamics.common.tile.generic.component.type.ComponentTickable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.Direction;
import net.minecraftforge.fluids.FluidStack;

public class TileElectricPump extends GenericTileTicking {
    private boolean hasWater;

    public TileElectricPump() {
	super(DeferredRegisters.TILE_ELECTRICPUMP.get());
	addComponent(new ComponentElectrodynamic(this).setMaxJoules(Constants.ELECTRICPUMP_USAGE_PER_TICK * 20)
		.addInputDirection(Direction.UP));
	addComponent(new ComponentDirection());
	addComponent(new ComponentTickable().addTickServer(this::tickServer));
	addComponent(
		new ComponentFluidHandler().addFluidTank(Fluids.WATER, 0).addRelativeInputDirection(Direction.EAST));
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
}
