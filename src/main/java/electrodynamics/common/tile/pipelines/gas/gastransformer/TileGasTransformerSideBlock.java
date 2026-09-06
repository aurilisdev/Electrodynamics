package electrodynamics.common.tile.pipelines.gas.gastransformer;

import java.util.Optional;

import javax.annotation.Nullable;

import electrodynamics.common.settings.ElectrodynamicsConfig;
import electrodynamics.registers.ElectrodynamicsBlocks;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import voltaic.api.gas.IGasHandler;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.CapabilityInputType;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.utils.IComponentFluidHandler;
import voltaic.prefab.utilities.BlockEntityUtils;

public class TileGasTransformerSideBlock extends GenericTile implements IAddonTankManager {

    private BlockPos ownerPos = BlockEntityUtils.OUT_OF_REACH;
    private boolean isLeft = false;

    public TileGasTransformerSideBlock(BlockPos worldPos, BlockState blockState) {
	super(ElectrodynamicsTiles.TILE_COMPRESSOR_SIDE.get(), worldPos, blockState);
    }

    public void setOwnerPos(BlockPos ownerPos) {
	this.ownerPos = ownerPos;
    }

    public void setIsLeft() {
	isLeft = true;
    }

    public boolean isLeft() {
	return isLeft;
    }

    @Override
    public void onPlace(Level level, BlockState oldState, boolean isMoving) {
	super.onPlace(level, oldState, isMoving);
	if (level.isClientSide)
	    return;

	updateTankCount(level);
    }

    @Override
    public void updateTankCount(Level level) {
	BlockPos abovePos = getBlockPos().above();
	BlockState aboveState = level.getBlockState(abovePos);
	BlockEntity aboveTile;
	int tankCount = 0;
	for (int i = 0; i < ElectrodynamicsConfig.INSTANCE.GAS_TRANSFORMER_ADDON_TANK_LIMIT.get(); i++) {
	    if (!aboveState.is(ElectrodynamicsBlocks.BLOCK_COMPRESSOR_ADDONTANK)) {
		break;
	    }
	    aboveTile = level.getBlockEntity(abovePos);
	    if (aboveTile == null || !(aboveTile instanceof TileGasTransformerAddonTank tank)) {
		break;
	    }
	    abovePos = abovePos.above();
	    aboveState = level.getBlockState(abovePos);
	    tank.setOwnerPos(getBlockPos());
	    tankCount++;
	}
	BlockEntity owner = level.getBlockEntity(ownerPos);
	if (owner != null && owner instanceof IMultiblockGasTransformer compressor) {
	    compressor.updateAddonTanks(tankCount, isLeft);
	}
    }

    @Override
    public void onBlockDestroyed(Level level) {
	if (level.isClientSide)
	    return;
	if (level.getBlockEntity(ownerPos) instanceof IMultiblockGasTransformer compressor) {
	    level.destroyBlock(ownerPos, !compressor.hasBeenDestroyed());
	}
    }

    @Override
    protected void saveAdditional(CompoundTag compound, HolderLookup.Provider registries) {
	super.saveAdditional(compound, registries);
	compound.put("owner", NbtUtils.writeBlockPos(ownerPos));
	compound.putBoolean("isleft", isLeft);
    }

    @Override
    protected void loadAdditional(CompoundTag compound, HolderLookup.Provider registries) {
	super.loadAdditional(compound, registries);
	Optional<BlockPos> optional = NbtUtils.readBlockPos(compound, "owner");
	ownerPos = optional.isPresent() ? optional.get() : BlockEntityUtils.OUT_OF_REACH;
	isLeft = compound.getBoolean("isleft");
    }

    @Override
    @Nullable
    public IFluidHandler getFluidHandlerCapability(@Nullable Direction side) {
	Level level = this.level;
	if (level == null || ownerPos.equals(BlockEntityUtils.OUT_OF_REACH))
	    return null;

	if (level.getBlockEntity(ownerPos) instanceof GenericTileGasTransformer compressor
		&& compressor.hasComponent(IComponentType.FluidHandler)) {

	    if (isLeft)
		return compressor.<IComponentFluidHandler>requireComponent(IComponentType.FluidHandler)
			.getCapability(side, CapabilityInputType.INPUT);
	    return compressor.<IComponentFluidHandler>requireComponent(IComponentType.FluidHandler).getCapability(side,
		    CapabilityInputType.OUTPUT);

	}
	return null;
    }

    @Override
    public @Nullable IGasHandler getGasHandlerCapability(@Nullable Direction side) {
	Level level = this.level;
	if (level == null || ownerPos.equals(BlockEntityUtils.OUT_OF_REACH))
	    return null;

	if (level.getBlockEntity(ownerPos) instanceof GenericTileGasTransformer compressor)
	    return compressor.getGasHandlerCapability(side);

	return null;
    }

    @Override
    public ItemInteractionResult useWithItem(Level level, ItemStack used, Player player, InteractionHand hand,
	    BlockHitResult hit) {
	if (level.getBlockEntity(ownerPos) instanceof GenericTileGasTransformer compressor)
	    return compressor.useWithItem(level, used, player, hand, hit);
	return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    public InteractionResult useWithoutItem(Level level, Player player, BlockHitResult hit) {
	if (level.getBlockEntity(ownerPos) instanceof GenericTileGasTransformer compressor)
	    return compressor.useWithoutItem(level, player, hit);
	return InteractionResult.FAIL;
    }

}
