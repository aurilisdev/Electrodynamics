package electrodynamics.common.tile.machines.quarry;

import java.util.ArrayList;

import javax.annotation.Nonnull;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerSeismicRelay;
import electrodynamics.registers.ElectrodynamicsItems;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
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

	public ListProperty<BlockPos> markerLocs = property(new ListProperty<>(PropertyTypes.BLOCK_POS_LIST, "markerlocs", new ArrayList<>()));

	public boolean cornerOnRight = false;

	public TileSeismicRelay() {
		super(ElectrodynamicsTiles.TILE_SEISMICRELAY.get());
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentTickable(this).tickServer(this::tickServer));
		addComponent(new ComponentInventory(this, ComponentInventory.InventoryBuilder.newInv().outputs(1)).valid((slot, stack, i) -> ItemUtils.testItems(stack.getItem(), ElectrodynamicsItems.ITEM_SEISMICMARKER.get())));
		addComponent(new ComponentContainerProvider(SubtypeMachine.seismicrelay.tag(), this).createMenu((id, player) -> new ContainerSeismicRelay(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));
	}

	private void tickServer(ComponentTickable tickable) {
		if (markerLocs.getValue().size() < 4) {
			Direction facing = getFacing().getOpposite();
			World world = getLevel();
			TileEntity tile = world.getBlockEntity(getBlockPos().relative(facing));
			if (tile instanceof TileSeismicMarker) {
				getMarkers((TileSeismicMarker) tile, facing);
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

		//markerLocs.forceDirty();

		if (markerLocs.getValue().size() > 3) {
			collectMarkers();
			getLevel().playSound(null, getBlockPos(), SoundEvents.ANVIL_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
		}
	}

	private static BlockPos getMarker(Direction facing, BlockPos blockPos, World level) {
		for (int i = 0; i <= TileSeismicMarker.MAX_RADIUS; i++) {
			blockPos = blockPos.relative(facing);
			TileEntity marker = level.getBlockEntity(blockPos);
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
			inv.setItem(0, new ItemStack(ElectrodynamicsItems.ITEM_SEISMICMARKER.get(), markerLocs.getValue().size()).copy());
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
	public CompoundNBT save(@Nonnull CompoundNBT compound) {
		compound.putBoolean("onRight", cornerOnRight);
		return super.save(compound);
	}

	@Override
	public void load(BlockState state, @Nonnull CompoundNBT compound) {
		super.load(state, compound);
		cornerOnRight = compound.getBoolean("onRight");
	}

}
