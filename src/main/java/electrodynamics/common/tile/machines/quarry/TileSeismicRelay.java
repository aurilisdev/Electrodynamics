package electrodynamics.common.tile.machines.quarry;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerSeismicRelay;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.tile.components.type.ComponentInventory.InventoryBuilder;
import electrodynamics.prefab.utilities.ItemUtils;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import electrodynamics.registers.ElectrodynamicsItems;
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
import org.jetbrains.annotations.NotNull;

//TODO: Make this use the property system...
public class TileSeismicRelay extends GenericTile {

	public List<BlockPos> markerLocs = new ArrayList<>();

	public List<BlockPos> clientLocs = new ArrayList<>();

	public boolean cornerOnRight = false;

	public TileSeismicRelay(BlockPos worldPosition, BlockState blockState) {
		super(ElectrodynamicsBlockTypes.TILE_SEISMICRELAY.get(), worldPosition, blockState);
		addComponent(new ComponentPacketHandler(this).addCustomPacketWriter(this::createPacket).addGuiPacketWriter(this::createPacket).addCustomPacketReader(this::readPacket).addGuiPacketReader(this::readPacket));
		addComponent(new ComponentTickable(this).tickServer(this::tickServer));
		addComponent(new ComponentInventory(this, InventoryBuilder.newInv().outputs(1)).valid((slot, stack, i) -> ItemUtils.testItems(stack.getItem(), ElectrodynamicsItems.ITEM_SEISMICMARKER.get())));
		addComponent(new ComponentContainerProvider(SubtypeMachine.seismicrelay, this).createMenu((id, player) -> new ContainerSeismicRelay(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));
	}

	private void tickServer(ComponentTickable tickable) {
		if (tickable.getTicks() % 10 == 0) {
			this.<ComponentPacketHandler>getComponent(IComponentType.PacketHandler).sendGuiPacketToTracking();
		}
		if (markerLocs.size() < 4) {
			Direction facing = getFacing().getOpposite();
			Level world = getLevel();
			BlockEntity tile = world.getBlockEntity(getBlockPos().relative(facing));
			if (tile != null && tile instanceof TileSeismicMarker marker) {
				getMarkers(marker, facing);
			}
		}

	}

	private void getMarkers(TileSeismicMarker marker, Direction facing) {
		markerLocs.clear();
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
		markerLocs.add(marker.getBlockPos());
		if (frontMarker != null) {
			markerLocs.add(frontMarker);
		}
		if (sideMarker != null) {
			markerLocs.add(sideMarker);
		}
		if (cornerMarker != null) {
			markerLocs.add(cornerMarker);
		}

		if (markerLocs.size() > 3) {
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
			inv.setItem(0, new ItemStack(ElectrodynamicsItems.ITEM_SEISMICMARKER.get(), markerLocs.size()).copy());
			for (BlockPos pos : markerLocs) {
				getLevel().setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
			}
		} else if (ItemUtils.testItems(input.getItem(), ElectrodynamicsItems.ITEM_SEISMICMARKER.get())) {
			int room = input.getMaxStackSize() - input.getCount();
			int accepted = room > markerLocs.size() ? markerLocs.size() : room;
			input.grow(accepted);
			for (BlockPos pos : markerLocs) {
				getLevel().setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
			}
		}
	}

	public boolean hasMarkers() {
		return markerLocs.size() > 3;
	}

	@Override
	public void saveAdditional(@NotNull CompoundTag compound) {
		super.saveAdditional(compound);
		compound.putInt("size", markerLocs.size());
		for (int i = 0; i < markerLocs.size(); i++) {
			BlockPos pos = markerLocs.get(i);
			compound.putInt("x" + i, pos.getX());
			compound.putInt("y" + i, pos.getY());
			compound.putInt("z" + i, pos.getZ());
		}
		compound.putBoolean("onRight", cornerOnRight);
	}

	@Override
	public void load(@NotNull CompoundTag compound) {
		super.load(compound);
		int size = compound.getInt("size");
		for (int i = 0; i < size; i++) {
			markerLocs.add(new BlockPos(compound.getInt("x" + i), compound.getInt("y" + i), compound.getInt("z" + i)));
		}
		cornerOnRight = compound.getBoolean("onRight");
	}

	private void createPacket(CompoundTag nbt) {
		nbt.putInt("size", markerLocs.size());
		for (int i = 0; i < markerLocs.size(); i++) {
			BlockPos pos = markerLocs.get(i);
			nbt.putInt("x" + i, pos.getX());
			nbt.putInt("y" + i, pos.getY());
			nbt.putInt("z" + i, pos.getZ());
		}
	}

	private void readPacket(CompoundTag nbt) {
		clientLocs.clear();
		int size = nbt.getInt("size");
		for (int i = 0; i < size; i++) {
			clientLocs.add(new BlockPos(nbt.getInt("x" + i), nbt.getInt("y" + i), nbt.getInt("z" + i)));
		}
	}

}
