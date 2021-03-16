package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.tile.GenericTileTicking;
import electrodynamics.api.tile.components.ComponentType;
import electrodynamics.api.tile.components.type.ComponentDirection;
import electrodynamics.api.tile.components.type.ComponentElectrodynamic;
import electrodynamics.api.tile.components.type.ComponentPacketHandler;
import electrodynamics.api.tile.components.type.ComponentTickable;
import electrodynamics.api.utilities.object.CachedTileOutput;
import electrodynamics.api.utilities.object.TransferPack;
import electrodynamics.common.network.ElectricityUtilities;
import electrodynamics.common.settings.Constants;
import net.minecraft.fluid.Fluids;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;

public class TileHydroelectricGenerator extends GenericTileTicking {
    protected CachedTileOutput output;
    public boolean hasWater = false;

    public TileHydroelectricGenerator() {
	super(DeferredRegisters.TILE_HYDROELECTRICGENERATOR.get());
	addComponent(new ComponentDirection());
	addComponent(new ComponentTickable().addTickServer(this::tickServer));
	addComponent(new ComponentPacketHandler().addGuiPacketReader(this::readNBT).addGuiPacketWriter(this::writeNBT));
	addComponent(new ComponentElectrodynamic(this).addRelativeOutputDirection(Direction.NORTH));
    }

    protected void tickServer(ComponentTickable tickable) {
	ComponentDirection direction = getComponent(ComponentType.Direction);
	Direction facing = direction.getDirection();
	if (output == null) {
	    output = new CachedTileOutput(world, pos.offset(facing.getOpposite()));
	}
	ComponentElectrodynamic electro = getComponent(ComponentType.Electrodynamic);
	if (tickable.getTicks() % 20 == 0) {
	    hasWater = world.getBlockState(pos.offset(facing)).getFluidState().getFluid() == Fluids.FLOWING_WATER;
	    this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendGuiPacketToTracking();
	}
	if (hasWater) {
	    ElectricityUtilities.receivePower(output.get(), facing,
		    TransferPack.ampsVoltage(Constants.HYDROELECTRICGENERATOR_AMPERAGE, electro.getVoltage()), false);
	}
    }

    protected void writeNBT(CompoundNBT nbt) {
	nbt.putBoolean("hasWater", hasWater);
    }

    protected void readNBT(CompoundNBT nbt) {
	hasWater = nbt.getBoolean("hasWater");
    }
}
