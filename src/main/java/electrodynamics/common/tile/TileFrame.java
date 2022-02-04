package electrodynamics.common.tile;

import org.apache.commons.lang3.tuple.Triple;

import electrodynamics.DeferredRegisters;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TileFrame extends GenericTile {

	private BlockPos quarryPos = null;
	private boolean isCorner = false;
	private boolean shouldNotify = true;
	
	//DO NOT MAKE THIS TICK IF AT ALL POSSIBLE!!!!!
	public TileFrame(BlockPos pos, BlockState state) {
		super(DeferredRegisters.TILE_FRAME.get(), pos, state);
		addComponent(new ComponentDirection());
	}
	
	public void setQuarryPos(BlockPos pos) {
		this.quarryPos = pos;
	}
	
	public BlockPos getQuarryPos() {
		return this.quarryPos;
	}
	
	public void setCorner(boolean bool) {
		this.isCorner = bool;
	}
	
	public boolean isCorner() {
		return this.isCorner;
	}
	
	public void setNoNotify() {
		shouldNotify = false;
	}
	
	@Override
	public void setRemoved() {
		Level world = getLevel();
		if(quarryPos != null && !world.isClientSide && shouldNotify) {
			BlockEntity tile = world.getBlockEntity(quarryPos);
			if(tile != null && tile instanceof TileQuarry quarry) {
				BlockPos pos = getBlockPos();
				Direction dir = ((ComponentDirection)getComponent(ComponentType.Direction)).getDirection();
				quarry.addBroken(Triple.of(new BlockPos(pos.getX(), pos.getY(), pos.getZ()), dir, isCorner));
			}
		}
		super.setRemoved();
	}
	
	@Override
	public void saveAdditional(CompoundTag compound) {
		super.saveAdditional(compound);
		//sanity check
		if(quarryPos != null) {
			compound.putInt("xPos", quarryPos.getX());
			compound.putInt("yPos", quarryPos.getY());
			compound.putInt("zPos", quarryPos.getZ());
		}
		compound.putBoolean("isCorner", isCorner);
		compound.putBoolean("shouldNotify", shouldNotify);
	}
	
	@Override
	public void load(CompoundTag compound) {
		super.load(compound);
		quarryPos = new BlockPos(compound.getInt("xPos"), compound.getInt("yPos"), compound.getInt("zPos"));
		isCorner = compound.getBoolean("isCorner");
		shouldNotify = compound.getBoolean("shouldNotify");
	}

}
