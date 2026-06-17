package electrodynamics.common.tile.machines.quarry;

import java.util.ArrayList;

import org.jetbrains.annotations.NotNull;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerSeismicRelay;
import electrodynamics.registers.ElectrodynamicsItems;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import voltaic.prefab.tile.components.type.ComponentPacketHandler;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.utilities.ItemUtils;

public class TileSeismicRelay extends GenericTile {

    public ListProperty<BlockPos> markerLocs = property(
	    new ListProperty<>(PropertyTypes.BLOCK_POS_LIST, "markerlocs", new ArrayList<>()));

    public boolean cornerOnRight = false;

    public TileSeismicRelay(BlockPos worldPosition, BlockState blockState) {
	super(ElectrodynamicsTiles.TILE_SEISMICRELAY.get(), worldPosition, blockState);
	addComponent(new ComponentPacketHandler(this));
	addComponent(new ComponentTickable(this).tickServer(this::tickServer));
	addComponent(new ComponentInventory(this, ComponentInventory.InventoryBuilder.newInv().outputs(1)).valid((slot,
		stack, i) -> ItemUtils.testItems(stack.getItem(), ElectrodynamicsItems.ITEM_SEISMICMARKER.get())));
	addComponent(new ComponentContainerProvider(SubtypeMachine.seismicrelay.tag(), this)
		.createMenu((id, player) -> new ContainerSeismicRelay(id, player,
			getComponent(IComponentType.Inventory), getCoordsArray())));
    }

    private void tickServer(ComponentTickable tickable) {
	if (markerLocs.getValue().size() < 4) {
	    Direction facing = getFacing().getOpposite();
	    Level world = getLevel();
	    BlockEntity tile = world.getBlockEntity(getBlockPos().relative(facing));
	    if (tile != null && tile instanceof TileSeismicMarker marker) {
		getMarkers(marker, facing);
	    }
	}

    }

    private void getMarkers(TileSeismicMarker marker, Direction facing) {
	markerLocs.wipeList();
	BlockPos frontMarker = getMarker(facing, marker.getBlockPos(), marker.getLevel());
	BlockPos sideMarker = null;
	BlockPos cornerMarker = null;
	if (frontMarker != null) {
	    sideMarker = getMarker(facing.getClockWise(), marker.getBlockPos(), marker.getLevel());
	    if (sideMarker != null) {
		cornerOnRight = true;
		cornerMarker = getMarker(facing, sideMarker, marker.getLevel());
	    } else {
		sideMarker = getMarker(facing.getCounterClockWise(), marker.getBlockPos(), marker.getLevel());
		if (sideMarker != null) {
		    cornerMarker = getMarker(facing, sideMarker, marker.getLevel());
		}
	    }
	}
	markerLocs.addValue(marker.getBlockPos());
	if (frontMarker != null) {
	    markerLocs.addValue(frontMarker);
	}
	if (sideMarker != null) {
	    markerLocs.addValue(sideMarker);
	}
	if (cornerMarker != null) {
	    markerLocs.addValue(cornerMarker);
	}

	// markerLocs.forceDirty();

	if (markerLocs.getValue().size() > 3) {
	    collectMarkers();
	    getLevel().playSound(null, getBlockPos(), SoundEvents.ANVIL_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
	}
    }

    private static BlockPos getMarker(Direction facing, BlockPos blockPos, Level level) {
	for (int i = 0; i <= TileSeismicMarker.MAX_RADIUS; i++) {
	    blockPos = blockPos.relative(facing);
	    BlockEntity marker = level.getBlockEntity(blockPos);
	    if (marker instanceof TileSeismicMarker && i > 0) {
		return marker.getBlockPos();
	    }
	}
	return null;
    }

    private void collectMarkers() {
	ComponentInventory inv = getComponent(IComponentType.Inventory);
	ItemStack input = inv.getOutputContents().get(0);
	if (input.isEmpty()) {
	    inv.setItem(0,
		    new ItemStack(ElectrodynamicsItems.ITEM_SEISMICMARKER.get(), markerLocs.getValue().size()).copy());
	    for (BlockPos pos : markerLocs.getValue()) {
		getLevel().setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
	    }
	} else if (ItemUtils.testItems(input.getItem(), ElectrodynamicsItems.ITEM_SEISMICMARKER.get())) {
	    int room = input.getMaxStackSize() - input.getCount();
	    int accepted = Math.min(room, markerLocs.getValue().size());
	    input.grow(accepted);
	    for (BlockPos pos : markerLocs.getValue()) {
		getLevel().setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
	    }
	}
    }

    public boolean hasMarkers() {
	return markerLocs.getValue().size() > 3;
    }

    @Override
    public void saveAdditional(@NotNull CompoundTag compound) {
	super.saveAdditional(compound);
	compound.putBoolean("onRight", cornerOnRight);
    }

    @Override
    public void load(@NotNull CompoundTag compound) {
	super.load(compound);
	cornerOnRight = compound.getBoolean("onRight");
    }

}
