package electrodynamics.common.tile.gastransformer;

import org.jetbrains.annotations.NotNull;

import electrodynamics.common.tile.quarry.TileQuarry;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import electrodynamics.registers.ElectrodynamicsBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class TileGasTransformerAddonTank extends GenericTile {

	public static final int MAX_ADDON_TANKS = 5;
	public static final double ADDITIONAL_CAPACITY = 5000;
	
	private BlockPos ownerPos = TileQuarry.OUT_OF_REACH;
	//public boolean isDestroyed = false;
	
	public TileGasTransformerAddonTank(BlockPos worldPos, BlockState blockState) {
		super(ElectrodynamicsBlockTypes.TILE_COMPRESSOR_ADDONTANK.get(), worldPos, blockState);
	}
	
	public void setOwnerPos(BlockPos ownerPos) {
		this.ownerPos = ownerPos;
	}
	
	@Override
	public void onBlockDestroyed() {
		BlockPos above = getBlockPos().above();
		BlockEntity aboveTile = getLevel().getBlockEntity(above);
		for(int i = 0; i < MAX_ADDON_TANKS; i++) {
			if(aboveTile != null && aboveTile instanceof TileGasTransformerAddonTank tank) {
				tank.setOwnerPos(TileQuarry.OUT_OF_REACH);
			}
			above = above.above();
			aboveTile = getLevel().getBlockEntity(above);
		}
		BlockEntity tile = getLevel().getBlockEntity(ownerPos);
		if(tile != null && tile instanceof TileGasTransformerSideBlock side) {
			//isDestroyed = true;
			side.updateTankCount();
		}
	}
	
	@Override
	public void onPlace(BlockState oldState, boolean isMoving) {
		BlockPos belowPos = getBlockPos().below();
		BlockState below = getLevel().getBlockState(belowPos);
		BlockEntity tile;
		for(int i = 0; i < MAX_ADDON_TANKS; i++) {
			if(below.is(ElectrodynamicsBlocks.blockGasTransformerSide)) {
				tile = getLevel().getBlockEntity(belowPos);
				if(tile != null && tile instanceof TileGasTransformerSideBlock side) {
					side.updateTankCount();
				}
				break;
			} else if (below.is(ElectrodynamicsBlocks.blockGasTransformerAddonTank)) {
				belowPos = belowPos.below();
				below = getLevel().getBlockState(belowPos);
			} else {
				break;
			}
		}
	}
	
	@Override
	public InteractionResult use(Player player, InteractionHand handIn, BlockHitResult hit) {
		BlockEntity owner = getLevel().getBlockEntity(ownerPos);
		if (owner != null && owner instanceof TileGasTransformerSideBlock compressor) {
			return compressor.use(player, handIn, hit);
		}
		return InteractionResult.FAIL;
	}
	
	@Override
	public void saveAdditional(@NotNull CompoundTag compound) {
		super.saveAdditional(compound);
		compound.put("owner", NbtUtils.writeBlockPos(ownerPos));
	}

	@Override
	public void load(@NotNull CompoundTag compound) {
		super.load(compound);
		ownerPos = NbtUtils.readBlockPos(compound.getCompound("owner"));
	}

}
