package electrodynamics.prefab.tile.types;

import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyType;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class GenericConnectTile extends GenericTile {

	private Property<Block> camoflaugedBlock = property(new Property<>(PropertyType.Block, "camoflaugedblock", Blocks.AIR));
	
	public GenericConnectTile(BlockEntityType<?> tile, BlockPos pos, BlockState state) {
		super(tile, pos, state);
		addComponent(new ComponentPacketHandler(this));
	}
	
	public void setBlock(Block block) {
		camoflaugedBlock.set(block);
		setChanged();
	}
	
	public Block getBlock() {
		return camoflaugedBlock.get();
	}
	
	public boolean isAir() {
		return Blocks.AIR.defaultBlockState().is(getBlock());
	}

}
