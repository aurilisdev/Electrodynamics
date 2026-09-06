package electrodynamics.common.tile.machines.quarry;

import java.util.ArrayList;
import java.util.Optional;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerSeismicRelay;
import electrodynamics.registers.ElectrodynamicsItems;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import voltaic.prefab.properties.types.PropertyTypes;
import voltaic.prefab.properties.variant.ListProperty;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentContainerProvider;
import voltaic.prefab.tile.components.type.ComponentInventory;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.utilities.ItemUtils;

public class TileSeismicRelay extends GenericTile {

    public ListProperty<BlockPos> markerLocs = property(
	    new ListProperty<>(getPropertyManager(), PropertyTypes.BLOCK_POS_LIST, "markerlocs", new ArrayList<>()));

    public boolean cornerOnRight = false;

    public TileSeismicRelay(BlockPos worldPosition, BlockState blockState) {
	super(ElectrodynamicsTiles.TILE_SEISMICRELAY.get(), worldPosition, blockState);

	addComponent(new ComponentTickable(this).tickServer(this::tickServer));
	addComponent(new ComponentInventory(this, ComponentInventory.InventoryBuilder.newInv().outputs(1)).valid((slot,
		stack, i) -> ItemUtils.testItems(stack.getItem(), ElectrodynamicsItems.ITEM_SEISMICMARKER.get())));
	addComponent(new ComponentContainerProvider(SubtypeMachine.seismicrelay.tag(), this)
		.createMenu((id, player) -> new ContainerSeismicRelay(id, player,
			requireComponent(IComponentType.Inventory), getCoordsArray())));
    }

    private void tickServer(Level level, ComponentTickable tickable) {
	if (markerLocs.getValue().size() < 4) {
	    Direction facing = getFacing().getOpposite();
	    BlockEntity tile = level.getBlockEntity(getBlockPos().relative(facing));
	    if (tile != null && tile instanceof TileSeismicMarker marker) {
		getMarkers(level, marker, facing);
	    }
	}

    }

    private void getMarkers(Level level, TileSeismicMarker marker, Direction facing) {
	markerLocs.wipeList();
	cornerOnRight = false;
	Optional<BlockPos> frontMarker = getMarker(facing, marker.getBlockPos(), level);
	Optional<BlockPos> sideMarker = Optional.empty();
	Optional<BlockPos> cornerMarker = Optional.empty();
	if (frontMarker.isPresent()) {
	    sideMarker = getMarker(facing.getClockWise(), marker.getBlockPos(), level);
	    if (sideMarker.isPresent()) {
		cornerOnRight = true;
		cornerMarker = getMarker(facing, sideMarker.orElseThrow(), level);
	    } else {
		sideMarker = getMarker(facing.getCounterClockWise(), marker.getBlockPos(), level);
		if (sideMarker.isPresent())
		    cornerMarker = getMarker(facing, sideMarker.orElseThrow(), level);
	    }
	}
	markerLocs.addValue(marker.getBlockPos());
	frontMarker.ifPresent(markerLocs::addValue);
	sideMarker.ifPresent(markerLocs::addValue);
	cornerMarker.ifPresent(markerLocs::addValue);
	if (markerLocs.getValue().size() > 3) {
	    collectMarkers(level);
	    level.playSound(null, getBlockPos(), SoundEvents.ANVIL_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
	}
    }

    private static Optional<BlockPos> getMarker(Direction facing, BlockPos blockPos, Level level) {
	for (int i = 0; i <= TileSeismicMarker.MAX_RADIUS; i++) {
	    blockPos = blockPos.relative(facing);
	    BlockEntity marker = level.getBlockEntity(blockPos);
	    if (marker instanceof TileSeismicMarker && i > 0)
		return Optional.of(blockPos);
	}
	return Optional.empty();
    }

    private void collectMarkers(Level level) {
	ComponentInventory inv = requireComponent(IComponentType.Inventory);
	ItemStack input = inv.getOutputContents().get(0);
	if (input.isEmpty()) {
	    inv.setItem(0,
		    new ItemStack(ElectrodynamicsItems.ITEM_SEISMICMARKER.get(), markerLocs.getValue().size()).copy());
	    for (BlockPos pos : markerLocs.getValue()) {
		level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
	    }
	} else if (ItemUtils.testItems(input.getItem(), ElectrodynamicsItems.ITEM_SEISMICMARKER.get())) {
	    int room = input.getMaxStackSize() - input.getCount();
	    int accepted = Math.min(room, markerLocs.getValue().size());
	    input.grow(accepted);
	    for (BlockPos pos : markerLocs.getValue()) {
		level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
	    }
	}
    }

    public boolean hasMarkers() {
	return markerLocs.getValue().size() > 3;
    }

    @Override
    protected void saveAdditional(CompoundTag compound, HolderLookup.Provider registries) {
	super.saveAdditional(compound, registries);
	compound.putBoolean("onRight", cornerOnRight);
    }

    @Override
    protected void loadAdditional(CompoundTag compound, HolderLookup.Provider registries) {
	super.loadAdditional(compound, registries);
	cornerOnRight = compound.getBoolean("onRight");
    }

}
