package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.utilities.CachedTileOutput;
import electrodynamics.api.utilities.TransferPack;
import electrodynamics.common.network.ElectricityUtilities;
import electrodynamics.common.settings.Constants;
import electrodynamics.common.tile.generic.GenericTileTicking;
import electrodynamics.common.tile.generic.component.ComponentType;
import electrodynamics.common.tile.generic.component.type.ComponentDirection;
import electrodynamics.common.tile.generic.component.type.ComponentElectrodynamic;
import electrodynamics.common.tile.generic.component.type.ComponentTickable;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.Direction;

public class TileHydroelectricGenerator extends GenericTileTicking {
    protected CachedTileOutput output;
    protected boolean hasWater = false;

    public TileHydroelectricGenerator() {
	super(DeferredRegisters.TILE_THERMOELECTRICGENERATOR.get());
	addComponent(new ComponentDirection());
	addComponent(new ComponentTickable().addTickServer(this::tickServer));
	addComponent(new ComponentElectrodynamic(this).addRelativeOutputDirection(Direction.UP));
    }

    protected void tickServer(ComponentTickable tickable) {
	ComponentDirection direction = getComponent(ComponentType.Direction);
	Direction facing = direction.getDirection();
	if (output == null) {
	    output = new CachedTileOutput(world, pos.offset(facing));
	}
	ComponentElectrodynamic electro = getComponent(ComponentType.Electrodynamic);
	if (tickable.getTicks() % 20 == 0) {
	    hasWater = world.getBlockState(pos.offset(facing.getOpposite())).getFluidState()
		    .getFluid() == Fluids.FLOWING_WATER;
	}
	if (hasWater) {
	    ElectricityUtilities.receivePower(output.get(), facing.getOpposite(),
		    TransferPack.ampsVoltage(Constants.THERMOELECTRICGENERATOR_AMPERAGE, electro.getVoltage()), false);
	}
    }
}
