package electrodynamics.common.tile.pipelines.gas.gastransformer;

import java.util.Optional;

import electrodynamics.common.settings.ElectrodynamicsConfig;
import electrodynamics.registers.ElectrodynamicsBlocks;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
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
    public void onBlockDestroyed(Level level) {
	if (level.isClientSide)
	    return;
	BlockPos above = getBlockPos().above();
	BlockEntity aboveTile = level.getBlockEntity(above);
	for (int i = 0; i < ElectrodynamicsConfig.INSTANCE.GAS_TRANSFORMER_ADDON_TANK_LIMIT.get(); i++) {
	    if (aboveTile instanceof TileGasTransformerAddonTank tank) {
		tank.setOwnerPos(BlockEntityUtils.OUT_OF_REACH);
	    }
	    above = above.above();
	    aboveTile = level.getBlockEntity(above);
	}
	if (level.getBlockEntity(ownerPos) instanceof IAddonTankManager manager) {
	    // isDestroyed = true;
	    manager.updateTankCount(level);
	}
    }

    @Override
    public void onPlace(Level level, BlockState oldState, boolean isMoving) {
	super.onPlace(level, oldState, isMoving);
	if (level.isClientSide)
	    return;
	BlockPos belowPos = getBlockPos().below();
	BlockState below = level.getBlockState(belowPos);
	for (int i = 0; i < ElectrodynamicsConfig.INSTANCE.GAS_TRANSFORMER_ADDON_TANK_LIMIT.get(); i++) {
	    if (level.getBlockEntity(belowPos) instanceof IAddonTankManager manager) {
		manager.updateTankCount(level);
		break;
	    }
	    if (!below.is(ElectrodynamicsBlocks.BLOCK_COMPRESSOR_ADDONTANK)) {
		break;
	    }
	    belowPos = belowPos.below();
	    below = level.getBlockState(belowPos);
	}
    }

    @Override
    public ItemInteractionResult useWithItem(Level level, ItemStack used, Player player, InteractionHand hand,
	    BlockHitResult hit) {
	if (level.getBlockEntity(ownerPos) instanceof GenericTile compressor)
	    return compressor.useWithItem(level, used, player, hand, hit);
	return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    public InteractionResult useWithoutItem(Level level, Player player, BlockHitResult hit) {
	if (level.getBlockEntity(ownerPos) instanceof GenericTile compressor)
	    return compressor.useWithoutItem(level, player, hit);
	return InteractionResult.FAIL;
    }

    @Override
    protected void saveAdditional(CompoundTag compound, HolderLookup.Provider registries) {
	super.saveAdditional(compound, registries);
	compound.put("owner", NbtUtils.writeBlockPos(ownerPos));
    }

    @Override
    protected void loadAdditional(CompoundTag compound, HolderLookup.Provider registries) {
	super.loadAdditional(compound, registries);
	Optional<BlockPos> optional = NbtUtils.readBlockPos(compound, "owner");
	ownerPos = optional.isPresent() ? optional.get() : BlockEntityUtils.OUT_OF_REACH;
    }

}
