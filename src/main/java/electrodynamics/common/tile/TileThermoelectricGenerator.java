package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.network.ElectricityUtilities;
import electrodynamics.common.settings.Constants;
import electrodynamics.prefab.tile.GenericTileTicking;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.object.CachedTileOutput;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.Direction;

public class TileThermoelectricGenerator extends GenericTileTicking {
    protected CachedTileOutput output;
    protected boolean hasHeat = false;

    public TileThermoelectricGenerator() {
	super(DeferredRegisters.TILE_THERMOELECTRICGENERATOR.get());
	addComponent(new ComponentDirection());
	addComponent(new ComponentTickable().tickServer(this::tickServer));
	addComponent(new ComponentElectrodynamic(this).relativeOutput(Direction.UP));
    }

    protected void tickServer(ComponentTickable tickable) {
	if (output == null) {
	    output = new CachedTileOutput(world, pos.offset(Direction.UP));
	}
	ComponentDirection direction = getComponent(ComponentType.Direction);
	ComponentElectrodynamic electro = getComponent(ComponentType.Electrodynamic);
	if (tickable.getTicks() % 60 == 0) {
	    hasHeat = world.getFluidState(pos.offset(direction.getDirection().getOpposite())).getFluid() == Fluids.LAVA;
	    output.update();
	}
	if (hasHeat && output.valid()) {
	    ElectricityUtilities.receivePower(output.getSafe(), Direction.UP,
		    TransferPack.ampsVoltage(Constants.THERMOELECTRICGENERATOR_AMPERAGE, electro.getVoltage()), false);
	}
    }
}
