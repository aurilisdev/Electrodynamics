package electrodynamics.prefab.tile.types;

import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyType;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class GenericConnectTile extends GenericTile {

	public final Property<BlockState> camoflaugedBlock = property(new Property<>(PropertyType.Blockstate, "camoflaugedblock", Blocks.AIR.defaultBlockState())).onChange((property, block) -> {
		level.getChunkSource().getLightEngine().checkBlock(worldPosition);
	});

	public final Property<BlockState> scaffoldBlock = property(new Property<>(PropertyType.Blockstate, "scaffoldblock", Blocks.AIR.defaultBlockState())).onChange((property, block) -> {
		level.getChunkSource().getLightEngine().checkBlock(worldPosition);
	});

	public GenericConnectTile(BlockEntityType<?> tile, BlockPos pos, BlockState state) {
		super(tile, pos, state);
		addComponent(new ComponentPacketHandler(this));
	}

	public void setCamoBlock(BlockState block) {
		camoflaugedBlock.set(block);
		setChanged();
	}

	public BlockState getCamoBlock() {
		return camoflaugedBlock.get();
	}

	public boolean isCamoAir() {
		return getCamoBlock().isAir();
	}

	public void setScaffoldBlock(BlockState scaffold) {
		scaffoldBlock.set(scaffold);
		setChanged();
	}

	public BlockState getScaffoldBlock() {
		return scaffoldBlock.get();
	}

	public boolean isScaffoldAir() {
		return getScaffoldBlock().isAir();
	}

	@Override
	public void onPlace(BlockState oldState, boolean isMoving) {
		super.onPlace(oldState, isMoving);
		if (!level.isClientSide) {
			this.<ComponentPacketHandler>getComponent(IComponentType.PacketHandler).sendProperties();
		}
	}

}
