package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.utilities.CachedTileOutput;
import electrodynamics.common.network.FluidUtilities;
import electrodynamics.common.settings.Constants;
import electrodynamics.common.tile.generic.GenericTileTickable;
import electrodynamics.common.tile.generic.component.ComponentType;
import electrodynamics.common.tile.generic.component.type.ComponentDirection;
import electrodynamics.common.tile.generic.component.type.ComponentElectrodynamic;
import electrodynamics.common.tile.generic.component.type.ComponentFluidHandler;
import electrodynamics.common.tile.generic.component.type.ComponentTickable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;

public class TileElectricPump extends GenericTileTickable {
    private boolean hasWater;

    public TileElectricPump() {
	super(DeferredRegisters.TILE_ELECTRICPUMP.get());
	addComponent(new ComponentElectrodynamic().setMaxJoules(Constants.ELECTRICPUMP_USAGE_PER_TICK * 20)
		.addInputDirection(Direction.UP));
	addComponent(new ComponentDirection());
	addComponent(new ComponentTickable().setTickServer(this::tickServer));
	addComponent(
		new ComponentFluidHandler().addFluidTank(Fluids.WATER, 0).addRelativeInputDirection(Direction.EAST));
    }

    protected CachedTileOutput output;

    public void tickServer() {
	Direction direction = this.<ComponentDirection>getComponent(ComponentType.Direction).getDirection().rotateY();
	if (output == null) {
	    output = new CachedTileOutput(world, new BlockPos(pos).offset(direction));
	}
	if (world.getWorldInfo().getGameTime() % 20 == 0) {
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
