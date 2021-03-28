package electrodynamics.common.tile;

import java.util.HashSet;

import electrodynamics.DeferredRegisters;
import electrodynamics.SoundRegister;
import electrodynamics.api.sound.SoundAPI;
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
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;

public class TileWindmill extends GenericTileTicking implements IMultiblockTileNode {
    protected CachedTileOutput output;
    public boolean isGenerating = false;
    public boolean directionFlag = false;
    public double savedTickRotation;
    public double generating;

    public TileWindmill() {
	super(DeferredRegisters.TILE_WINDMILL.get());
	addComponent(new ComponentDirection());
	addComponent(new ComponentTickable().tickServer(this::tickServer).tickCommon(this::tickCommon).tickClient(this::tickClient));
	addComponent(new ComponentPacketHandler().guiPacketReader(this::readNBT).guiPacketWriter(this::writeNBT));
	addComponent(new ComponentElectrodynamic(this).output(Direction.DOWN));
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
	return super.getRenderBoundingBox().expand(0, 1.5, 0);
    }

    protected void tickServer(ComponentTickable tickable) {
	ComponentDirection direction = getComponent(ComponentType.Direction);
	Direction facing = direction.getDirection();
	if (output == null) {
	    output = new CachedTileOutput(world, pos.offset(Direction.DOWN));
	}
	ComponentElectrodynamic electro = getComponent(ComponentType.Electrodynamic);
	if (tickable.getTicks() % 40 == 0) {
	    output.update();
	    isGenerating = world.isAirBlock(pos.offset(facing).offset(Direction.UP));
	    generating = Constants.WINDMILL_MAX_AMPERAGE * (0.6 + Math.sin((pos.getY() - 60) / 50.0) * 0.4);
	    this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendGuiPacketToTracking();
	}
	if (isGenerating && output.valid()) {
	    ElectricityUtilities.receivePower(output.getSafe(), Direction.UP, TransferPack.ampsVoltage(generating, electro.getVoltage()), false);
	}
    }

    protected void tickCommon(ComponentTickable tickable) {
	if (isGenerating) {
	    savedTickRotation += directionFlag ? 1 : -1;
	}
    }

    protected void tickClient(ComponentTickable tickable) {
	if (isGenerating && tickable.getTicks() % 180 == 0) {
	    SoundAPI.playSound(SoundRegister.SOUND_WINDMILL.get(), SoundCategory.BLOCKS, 1, 1, pos);
	}
    }

    protected void writeNBT(CompoundNBT nbt) {
	nbt.putBoolean("isGenerating", isGenerating);
	nbt.putBoolean("directionFlag", directionFlag);
	nbt.putDouble("generating", generating);
    }

    protected void readNBT(CompoundNBT nbt) {
	isGenerating = nbt.getBoolean("isGenerating");
	directionFlag = nbt.getBoolean("directionFlag");
	generating = nbt.getDouble("generating");
    }

    @Override
    public HashSet<Subnode> getSubNodes() {
	return BlockMachine.windmillsubnodes;
    }
}
