package electrodynamics.common.tile;

import java.util.HashSet;

import electrodynamics.DeferredRegisters;
import electrodynamics.SoundRegister;
import electrodynamics.api.sound.SoundAPI;
import electrodynamics.common.block.BlockMachine;
import electrodynamics.common.multiblock.IMultiblockTileNode;
import electrodynamics.common.multiblock.Subnode;
import electrodynamics.common.network.ElectricityUtilities;
import electrodynamics.common.settings.Constants;
import electrodynamics.prefab.tile.GenericTileTicking;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.object.CachedTileOutput;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.AABB;

public class TileWindmill extends GenericTileTicking implements IMultiblockTileNode {
    protected CachedTileOutput output;
    public boolean isGenerating = false;
    public boolean directionFlag = false;
    public double savedTickRotation;
    public double generating;
    public double rotationSpeed;

    public TileWindmill() {
	super(DeferredRegisters.TILE_WINDMILL.get());
	addComponent(new ComponentDirection());
	addComponent(new ComponentTickable().tickServer(this::tickServer).tickCommon(this::tickCommon).tickClient(this::tickClient));
	addComponent(new ComponentPacketHandler().guiPacketReader(this::readNBT).guiPacketWriter(this::writeNBT));
	addComponent(new ComponentElectrodynamic(this).output(Direction.DOWN));
    }

    @Override
    public AABB getRenderBoundingBox() {
	return super.getRenderBoundingBox().expandTowards(0, 1.5, 0);
    }

    protected void tickServer(ComponentTickable tickable) {
	ComponentDirection direction = getComponent(ComponentType.Direction);
	Direction facing = direction.getDirection();
	if (output == null) {
	    output = new CachedTileOutput(level, worldPosition.relative(Direction.DOWN));
	}
	ComponentElectrodynamic electro = getComponent(ComponentType.Electrodynamic);
	if (tickable.getTicks() % 40 == 0) {
	    output.update();
	    isGenerating = level.isEmptyBlock(worldPosition.relative(facing).relative(Direction.UP));
	    generating = Constants.WINDMILL_MAX_AMPERAGE * (0.6 + Math.sin((worldPosition.getY() - 60) / 50.0) * 0.4);
	    this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendGuiPacketToTracking();
	}
	if (isGenerating && output.valid()) {
	    ElectricityUtilities.receivePower(output.getSafe(), Direction.UP, TransferPack.ampsVoltage(generating, electro.getVoltage()), false);
	}
    }

    protected void tickCommon(ComponentTickable tickable) {
	savedTickRotation += (directionFlag ? 1 : -1) * rotationSpeed;
	rotationSpeed = Mth.clamp(rotationSpeed + 0.05 * (isGenerating ? 1 : -1), 0.0, 1.0);
    }

    protected void tickClient(ComponentTickable tickable) {
	if (isGenerating && tickable.getTicks() % 180 == 0) {
	    SoundAPI.playSound(SoundRegister.SOUND_WINDMILL.get(), SoundSource.BLOCKS, 1, 1, worldPosition);
	}
    }

    protected void writeNBT(CompoundTag nbt) {
	nbt.putBoolean("isGenerating", isGenerating);
	nbt.putBoolean("directionFlag", directionFlag);
	nbt.putDouble("generating", generating);
    }

    protected void readNBT(CompoundTag nbt) {
	isGenerating = nbt.getBoolean("isGenerating");
	directionFlag = nbt.getBoolean("directionFlag");
	generating = nbt.getDouble("generating");
    }

    @Override
    public HashSet<Subnode> getSubNodes() {
	return BlockMachine.windmillsubnodes;
    }
}
