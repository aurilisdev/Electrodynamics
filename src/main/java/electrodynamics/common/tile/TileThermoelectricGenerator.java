package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.tile.GenericTileTicking;
import electrodynamics.api.tile.components.ComponentType;
import electrodynamics.api.tile.components.type.ComponentDirection;
import electrodynamics.api.tile.components.type.ComponentElectrodynamic;
import electrodynamics.api.tile.components.type.ComponentTickable;
import electrodynamics.api.utilities.object.CachedTileOutput;
import electrodynamics.api.utilities.object.TransferPack;
import electrodynamics.common.network.ElectricityUtilities;
import electrodynamics.common.settings.Constants;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.Direction;

public class TileThermoelectricGenerator extends GenericTileTicking {
    protected CachedTileOutput output;
    protected boolean hasHeat = false;

    public TileThermoelectricGenerator() {
	super(DeferredRegisters.TILE_THERMOELECTRICGENERATOR.get());
	addComponent(new ComponentDirection());
	addComponent(new ComponentTickable().addTickServer(this::tickServer));
	addComponent(new ComponentElectrodynamic(this).addRelativeOutputDirection(Direction.UP));
    }

    protected void tickServer(ComponentTickable tickable) {
	if (output == null) {
	    output = new CachedTileOutput(world, pos.offset(Direction.UP));
	}
	ComponentDirection direction = getComponent(ComponentType.Direction);
	ComponentElectrodynamic electro = getComponent(ComponentType.Electrodynamic);
	if (tickable.getTicks() % 20 == 0) {
	    hasHeat = world.getBlockState(pos.offset(direction.getDirection().getOpposite())).getFluidState().getFluid() == Fluids.LAVA;
	}
	if (hasHeat) {
	    ElectricityUtilities.receivePower(output.get(), Direction.UP,
		    TransferPack.ampsVoltage(Constants.THERMOELECTRICGENERATOR_AMPERAGE, electro.getVoltage()), false);
	}
    }
}
