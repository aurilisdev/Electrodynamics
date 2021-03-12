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

public class TileThermoelectricGenerator extends GenericTileTicking {
    protected CachedTileOutput output;
    protected boolean hasHeat = false;

    public TileThermoelectricGenerator() {
	super(DeferredRegisters.TILE_THERMOELECTRICGENERATOR.get());
	addComponent(new ComponentDirection());
	addComponent(new ComponentTickable().addTickServer(this::tickServer));
	addComponent(new ComponentElectrodynamic().addRelativeOutputDirection(Direction.UP));
    }

    public void tickServer(ComponentTickable tickable) {
	if (output == null) {
	    output = new CachedTileOutput(world, pos.offset(Direction.UP));
	}
	ComponentDirection direction = getComponent(ComponentType.Direction);
	ComponentElectrodynamic electro = getComponent(ComponentType.Electrodynamic);
	if (tickable.getTicks() % 20 == 0) {
	    hasHeat = world.getBlockState(pos.offset(direction.getDirection().getOpposite())).getFluidState()
		    .getFluid() == Fluids.LAVA;
	}
	if (hasHeat) {
	    ElectricityUtilities.receivePower(output.get(), Direction.UP,
		    TransferPack.ampsVoltage(Constants.THERMOELECTRICGENERATOR_AMPERAGE, electro.getVoltage()), false);
	}
    }
}
