package electrodynamics.common.tile.pipelines.gas.gastransformer;

import electrodynamics.common.settings.ElectroConstants;
import electrodynamics.registers.ElectrodynamicsBlocks;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.utilities.BlockEntityUtils;

public class TileGasTransformerAddonTank extends GenericTile {

	private BlockPos ownerPos = BlockEntityUtils.OUT_OF_REACH;
	// public boolean isDestroyed = false;

	public TileGasTransformerAddonTank(BlockPos worldPos, BlockState blockState) {
		super(ElectrodynamicsTiles.TILE_COMPRESSOR_ADDONTANK.get(), worldPos, blockState);
	}

	public void setOwnerPos(BlockPos ownerPos) {
		this.ownerPos = ownerPos;
	}

	@Override
	public void onBlockDestroyed() {
		if (level.isClientSide) {
			return;
		}
		BlockPos above = getBlockPos().above();
		BlockEntity aboveTile = getLevel().getBlockEntity(above);
		for (int i = 0; i < ElectroConstants.GAS_TRANSFORMER_ADDON_TANK_LIMIT; i++) {
			if (aboveTile instanceof TileGasTransformerAddonTank tank) {
				tank.setOwnerPos(BlockEntityUtils.OUT_OF_REACH);
			}
			above = above.above();
			aboveTile = getLevel().getBlockEntity(above);
		}
		if (getLevel().getBlockEntity(ownerPos) instanceof IAddonTankManager manager) {
			// isDestroyed = true;
			manager.updateTankCount();
		}
	}

	@Override
	public void onPlace(BlockState oldState, boolean isMoving) {
		super.onPlace(oldState, isMoving);
		if (level.isClientSide) {
			return;
		}
		BlockPos belowPos = getBlockPos().below();
		BlockState below = getLevel().getBlockState(belowPos);
		for (int i = 0; i < ElectroConstants.GAS_TRANSFORMER_ADDON_TANK_LIMIT; i++) {
			if (getLevel().getBlockEntity(belowPos) instanceof IAddonTankManager manager) {
				manager.updateTankCount();
				break;
			}
			if (!below.is(ElectrodynamicsBlocks.BLOCK_COMPRESSOR_ADDONTANK.get())) {
				break;
			}
			belowPos = belowPos.below();
			below = getLevel().getBlockState(belowPos);
		}
	}
	
	@Override
	public InteractionResult use(Player player, InteractionHand hand, BlockHitResult hit) {
		if (getLevel().getBlockEntity(ownerPos) instanceof GenericTile compressor) {
			return compressor.use(player, hand, hit);
		}
		return super.use(player, hand, hit);
	}

	@Override
	protected void saveAdditional(CompoundTag compound) {
		super.saveAdditional(compound);
		compound.put("owner", NbtUtils.writeBlockPos(ownerPos));
	}

	@Override
	public void load(CompoundTag compound) {
		super.load(compound);
		BlockPos optional = NbtUtils.readBlockPos(compound.getCompound("owner"));
		ownerPos = optional;
	}

}
