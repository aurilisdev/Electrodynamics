package electrodynamics.common.tile;

import java.util.HashSet;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.tile.GenericTileTicking;
import electrodynamics.api.tile.components.ComponentType;
import electrodynamics.api.tile.components.type.ComponentDirection;
import electrodynamics.api.tile.components.type.ComponentElectrodynamic;
import electrodynamics.api.tile.components.type.ComponentPacketHandler;
import electrodynamics.api.tile.components.type.ComponentTickable;
import electrodynamics.api.utilities.object.CachedTileOutput;
import electrodynamics.api.utilities.object.TransferPack;
import electrodynamics.common.block.BlockMachine;
import electrodynamics.common.multiblock.IMultiblockTileNode;
import electrodynamics.common.multiblock.Subnode;
import electrodynamics.common.network.ElectricityUtilities;
import electrodynamics.common.settings.Constants;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;

public class TileWindmill extends GenericTileTicking implements IMultiblockTileNode {
    protected CachedTileOutput output;
    public boolean isGenerating = false;
    public boolean directionFlag = false;
    public double savedTickRotation;

    public TileWindmill() {
	super(DeferredRegisters.TILE_WINDMILL.get());
	addComponent(new ComponentDirection());
	addComponent(new ComponentTickable().addTickServer(this::tickServer).addTickCommon(this::tickCommon).addTickClient(this::tickClient));
	addComponent(new ComponentPacketHandler().addGuiPacketReader(this::readNBT).addGuiPacketWriter(this::writeNBT));
	addComponent(new ComponentElectrodynamic(this).addOutputDirection(Direction.DOWN));
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
	return super.getRenderBoundingBox().expand(0, 1.5, 0);
    }

    protected void tickServer(ComponentTickable tickable) {
	ComponentDirection direction = getComponent(ComponentType.Direction);
	Direction facing = direction.getDirection();
	if (output == null) {
	    output = new CachedTileOutput(world, pos.offset(facing.getOpposite()));
	}
	ComponentElectrodynamic electro = getComponent(ComponentType.Electrodynamic);
	if (tickable.getTicks() % 20 == 0) {
	    this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendGuiPacketToTracking();
	}
	if (isGenerating) {
	    ElectricityUtilities.receivePower(output.get(), facing, TransferPack.ampsVoltage(Constants.WINDMILL_MAX_AMPERAGE, electro.getVoltage()),
		    false);
	}
    }

    protected void tickCommon(ComponentTickable tickable) {
	if (isGenerating) {
	    savedTickRotation += directionFlag ? 1 : -1;
	}
    }

    protected void tickClient(ComponentTickable tickable) {
	if (isGenerating && world.rand.nextDouble() < 0.3) {
	//    Direction direction = this.<ComponentDirection>getComponent(ComponentType.Direction).getDirection();
	}
    }

    protected void writeNBT(CompoundNBT nbt) {
	nbt.putBoolean("isGenerating", isGenerating);
	nbt.putBoolean("directionFlag", directionFlag);
    }

    protected void readNBT(CompoundNBT nbt) {
	isGenerating = nbt.getBoolean("isGenerating");
	directionFlag = nbt.getBoolean("directionFlag");
    }

    @Override
    public HashSet<Subnode> getSubNodes() {
	return BlockMachine.windmillsubnodes;
    }
}
