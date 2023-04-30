package electrodynamics.common.tile.gastransformer;

import javax.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.common.tile.quarry.TileQuarry;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.CapabilityInputType;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import electrodynamics.registers.ElectrodynamicsBlocks;
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

public class TileGasTransformerSideBlock extends GenericTile {

	private BlockPos ownerPos = TileQuarry.OUT_OF_REACH;
	private boolean isLeft = false;
	
	
	public TileGasTransformerSideBlock(BlockPos worldPos, BlockState blockState) {
		super(ElectrodynamicsBlockTypes.TILE_COMPRESSOR_SIDE.get(), worldPos, blockState);
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
		updateTankCount();
	}
	
	public void updateTankCount() {
		BlockPos abovePos = getBlockPos().above();
		BlockState aboveState = getLevel().getBlockState(abovePos);
		BlockEntity aboveTile;
		int tankCount = 0;
		for(int i = 0; i < TileGasTransformerAddonTank.MAX_ADDON_TANKS; i++) {
			if(!aboveState.is(ElectrodynamicsBlocks.blockGasTransformerAddonTank)) {
				break;
			}
			aboveTile = getLevel().getBlockEntity(abovePos);
			if(aboveTile != null && aboveTile instanceof TileGasTransformerAddonTank tank) {
				abovePos = abovePos.above();
				aboveState = getLevel().getBlockState(abovePos);
				tank.setOwnerPos(getBlockPos());
				tankCount++;
			} else {
				break;
			}
		}
		BlockEntity owner = getLevel().getBlockEntity(ownerPos);
		if(owner != null && owner instanceof GenericTileGasTransformer compressor) {
			compressor.updateAddonTanks(tankCount, isLeft);
		}
	}

	@Override
	public void onBlockDestroyed() {
		BlockEntity owner = getLevel().getBlockEntity(ownerPos);
		if (owner != null && owner instanceof GenericTileGasTransformer compressor) {
			getLevel().destroyBlock(ownerPos, !compressor.hasBeenDestroyed);
		}
	}

	@Override
	public void saveAdditional(@NotNull CompoundTag compound) {
		super.saveAdditional(compound);
		compound.put("owner", NbtUtils.writeBlockPos(ownerPos));
		compound.putBoolean("isleft", isLeft);
	}

	@Override
	public void load(@NotNull CompoundTag compound) {
		super.load(compound);
		ownerPos = NbtUtils.readBlockPos(compound.getCompound("owner"));
		isLeft = compound.getBoolean("isleft");
	}

	@Override
	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
		if(ownerPos == null || ownerPos.equals(TileQuarry.OUT_OF_REACH)) {
			return LazyOptional.empty();
		}
		BlockEntity owner = getLevel().getBlockEntity(ownerPos);
		if(cap == ElectrodynamicsCapabilities.ELECTRODYNAMIC) {
			return LazyOptional.empty();
		}
		if (owner != null && owner instanceof GenericTileGasTransformer compressor) {
			
			if(cap == ForgeCapabilities.FLUID_HANDLER) {
				if(isLeft) {
					return compressor.getComponent(ComponentType.FluidHandler).getCapability(cap, side, CapabilityInputType.INPUT);
				} else {
					return compressor.getComponent(ComponentType.FluidHandler).getCapability(cap, side, CapabilityInputType.OUTPUT);
				}
			}
			return compressor.getCapability(cap, side);
		}
		return super.getCapability(cap);
	}

	@Override
	public InteractionResult use(Player player, InteractionHand handIn, BlockHitResult hit) {
		BlockEntity owner = getLevel().getBlockEntity(ownerPos);
		if (owner != null && owner instanceof GenericTileGasTransformer compressor) {
			return compressor.use(player, handIn, hit);
		}
		return InteractionResult.FAIL;
	}

}
