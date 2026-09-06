package electrodynamics.common.tile.machines.chemicalreactor;

import javax.annotation.Nullable;

import electrodynamics.common.block.chemicalreactor.BlockChemicalReactorExtra;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.items.IItemHandler;
import voltaic.api.electricity.ICapabilityElectrodynamic;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.CapabilityInputType;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentElectrodynamic;
import voltaic.prefab.tile.components.type.ComponentFluidHandlerMulti;
import voltaic.prefab.tile.components.type.ComponentInventory;

public class TileChemicalReactorDummy extends GenericTile {

    private boolean destroyed = false;

    public TileChemicalReactorDummy(BlockPos worldPos, BlockState blockState) {
	super(ElectrodynamicsTiles.TILE_CHEMICALREACTOR_DUMMY.get(), worldPos, blockState);
    }

    @Override
    @Nullable
    public ICapabilityElectrodynamic getElectrodynamicCapability(@Nullable Direction side) {
	Level level = this.level;
	if (level == null)
	    return null;

	if (level.getBlockEntity(
		getBlockPos().offset(getLocation().offsetDownToParent)) instanceof TileChemicalReactor reactor
		&& getLocation() == BlockChemicalReactorExtra.Location.TOP)
	    return reactor.<ComponentElectrodynamic>requireComponent(IComponentType.Electrodynamic).getCapability(side,
		    CapabilityInputType.NONE);

	return null;
    }

    @Override
    @Nullable
    public IFluidHandler getFluidHandlerCapability(@Nullable Direction side) {
	Level level = this.level;
	if (level == null)
	    return null;

	if (level.getBlockEntity(
		getBlockPos().offset(getLocation().offsetDownToParent)) instanceof TileChemicalReactor reactor
		&& getLocation() == BlockChemicalReactorExtra.Location.TOP)
	    return reactor.<ComponentFluidHandlerMulti>requireComponent(IComponentType.FluidHandler).getCapability(side,
		    CapabilityInputType.NONE);
	return null;
    }

    @Override
    @Nullable
    public IItemHandler getItemHandlerCapability(@Nullable Direction side) {
	Level level = this.level;
	if (level == null)
	    return null;

	if (level.getBlockEntity(
		getBlockPos().offset(getLocation().offsetDownToParent)) instanceof TileChemicalReactor reactor
		&& getLocation() == BlockChemicalReactorExtra.Location.MIDDLE)
	    return reactor.<ComponentInventory>requireComponent(IComponentType.Inventory).getCapability(side,
		    CapabilityInputType.NONE);
	return null;
    }

    @Override
    public void onBlockDestroyed(Level level) {
	if (!destroyed && level.getBlockEntity(
		getBlockPos().offset(getLocation().offsetDownToParent)) instanceof TileChemicalReactor) {
	    level.destroyBlock(getBlockPos().offset(getLocation().offsetDownToParent), true);
	    destroyed = true;
	}
	super.onBlockDestroyed(level);
    }

    @Override
    public ItemInteractionResult useWithItem(Level level, ItemStack used, Player player, InteractionHand hand,
	    BlockHitResult hit) {
	if (level.getBlockEntity(
		getBlockPos().offset(getLocation().offsetDownToParent)) instanceof TileChemicalReactor reactor)
	    return reactor.useWithItem(level, used, player, hand, hit);
	return super.useWithItem(level, used, player, hand, hit);
    }

    @Override
    public InteractionResult useWithoutItem(Level level, Player player, BlockHitResult hit) {
	if (level.getBlockEntity(
		getBlockPos().offset(getLocation().offsetDownToParent)) instanceof TileChemicalReactor reactor)
	    return reactor.useWithoutItem(level, player, hit);
	return super.useWithoutItem(level, player, hit);
    }

    @Override
    public int getComparatorSignal(Level level) {
	if (level.getBlockEntity(
		getBlockPos().offset(getLocation().offsetDownToParent)) instanceof TileChemicalReactor reactor)
	    return reactor.getComparatorSignal(level);
	return super.getComparatorSignal(level);
    }

    public BlockChemicalReactorExtra.Location getLocation() {
	return ((BlockChemicalReactorExtra) getBlockState().getBlock()).loc;
    }
}
