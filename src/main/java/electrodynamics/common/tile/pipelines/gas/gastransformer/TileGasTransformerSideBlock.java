package electrodynamics.common.tile.pipelines.gas.gastransformer;

import org.jetbrains.annotations.NotNull;

import electrodynamics.common.settings.ElectroConstants;
import electrodynamics.registers.ElectrodynamicsBlocks;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.CapabilityInputType;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.utils.IComponentFluidHandler;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.registers.VoltaicCapabilities;

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
    public void onPlace(BlockState oldState, boolean isMoving) {
        super.onPlace(oldState, isMoving);
        if (level.isClientSide) {
            return;
        }
        updateTankCount();
    }

    @Override
    public void updateTankCount() {
        BlockPos abovePos = getBlockPos().above();
        BlockState aboveState = getLevel().getBlockState(abovePos);
        BlockEntity aboveTile;
        int tankCount = 0;
        for (int i = 0; i < ElectroConstants.GAS_TRANSFORMER_ADDON_TANK_LIMIT; i++) {
            if (!aboveState.is(ElectrodynamicsBlocks.BLOCK_COMPRESSOR_ADDONTANK.get())) {
                break;
            }
            aboveTile = getLevel().getBlockEntity(abovePos);
            if ((aboveTile == null) || !(aboveTile instanceof TileGasTransformerAddonTank tank)) {
                break;
            }
            abovePos = abovePos.above();
            aboveState = getLevel().getBlockState(abovePos);
            tank.setOwnerPos(getBlockPos());
            tankCount++;
        }
        BlockEntity owner = getLevel().getBlockEntity(ownerPos);
        if (owner != null && owner instanceof IMultiblockGasTransformer compressor) {
            compressor.updateAddonTanks(tankCount, isLeft);
        }
    }

    @Override
    public void onBlockDestroyed() {
        if (level.isClientSide) {
            return;
        }
        if (getLevel().getBlockEntity(ownerPos) instanceof IMultiblockGasTransformer compressor) {
            getLevel().destroyBlock(ownerPos, !compressor.hasBeenDestroyed());
        }
    }

    @Override
    protected void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        compound.put("owner", NbtUtils.writeBlockPos(ownerPos));
        compound.putBoolean("isleft", isLeft);
    }

    @Override
	public void load(CompoundTag compound) {
        super.load(compound);
        BlockPos optional = NbtUtils.readBlockPos(compound.getCompound("owner"));
        ownerPos = optional;
        isLeft = compound.getBoolean("isleft");
    }
    
    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, Direction side) {
    	if(side == null || ownerPos == null) {
    		return LazyOptional.empty();
    	}
    	if(cap == ForgeCapabilities.FLUID_HANDLER && getLevel().getBlockEntity(ownerPos) instanceof GenericTileGasTransformer compressor && compressor.hasComponent(IComponentType.FluidHandler)) {
    		if (isLeft) {
                return compressor.<IComponentFluidHandler>getComponent(IComponentType.FluidHandler).getCapability(cap, side, CapabilityInputType.INPUT);
            }
            return compressor.<IComponentFluidHandler>getComponent(IComponentType.FluidHandler).getCapability(cap, side, CapabilityInputType.OUTPUT);
    	}
    	if(cap == VoltaicCapabilities.CAPABILITY_GASHANDLER_BLOCK && getLevel().getBlockEntity(ownerPos) instanceof GenericTileGasTransformer compressor) {
    		return compressor.getCapability(cap, side);
    	}
    	return LazyOptional.empty();
    }
    
    @Override
    public InteractionResult use(Player player, InteractionHand hand, BlockHitResult hit) {
    	if (getLevel().getBlockEntity(ownerPos) instanceof GenericTileGasTransformer compressor) {
    		return compressor.use(player, hand, hit);
    	}	
    	return super.use(player, hand, hit);
    }

}
