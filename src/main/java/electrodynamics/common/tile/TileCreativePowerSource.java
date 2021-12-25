package electrodynamics.common.tile;

import java.util.ArrayList;
import java.util.List;

import com.mojang.datafixers.util.Pair;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.inventory.container.tile.ContainerCreativePowerSource;
import electrodynamics.common.network.ElectricityUtilities;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.object.CachedTileOutput;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;

public class TileCreativePowerSource extends GenericTile {

	public Pair<Integer, Integer> outputValue;

	protected List<CachedTileOutput> outputs;

	public TileCreativePowerSource(BlockPos worldPos, BlockState blockState) {
		super(DeferredRegisters.TILE_CREATIVEPOWERSOURCE.get(), worldPos, blockState);
		addComponent(new ComponentTickable().tickServer(this::tickServer));
		addComponent(new ComponentPacketHandler().customPacketWriter(this::writePacket).customPacketReader(this::readPacket)
				.guiPacketReader(this::readPacket).guiPacketWriter(this::writePacket));
		addComponent(new ComponentElectrodynamic(this).output(Direction.DOWN).output(Direction.UP).output(Direction.NORTH).output(Direction.SOUTH)
				.output(Direction.EAST).output(Direction.WEST));
		addComponent(new ComponentInventory(this));
		addComponent(new ComponentContainerProvider("container.creativepowersource")
				.createMenu((id, player) -> new ContainerCreativePowerSource(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
	}

	private void tickServer(ComponentTickable tick) {
		ComponentPacketHandler packet = getComponent(ComponentType.PacketHandler);
		ComponentElectrodynamic electro = getComponent(ComponentType.Electrodynamic);
		if (tick.getTicks() % 20 == 0) {
			packet.sendCustomPacket();
		}
		if (outputValue == null) {
			outputValue = Pair.of(0, 0);
			packet.sendCustomPacket();
		}
		if (outputs == null) {
			outputs = new ArrayList<>();
			for (Direction dir : Direction.values()) {
				outputs.add(new CachedTileOutput(level, worldPosition.relative(dir)));
			}
		}
		if (tick.getTicks() % 40 == 0) {
			for (CachedTileOutput cache : outputs) {
				cache.update();
			}
		}
		electro.voltage(outputValue.getSecond());
		TransferPack output = TransferPack.joulesVoltage(outputValue.getSecond(), outputValue.getFirst());
		for (int i = 0; i < outputs.size(); i++) {
			CachedTileOutput cache = outputs.get(i);
			Direction dir = Direction.values()[i];
			if (cache.valid()) {
				ElectricityUtilities.receivePower(cache.getSafe(), dir.getOpposite(), output, false);
			}
		}

	}

	private void writePacket(CompoundTag nbt) {
		if (outputValue != null) {
			nbt.putInt("voltage", outputValue.getFirst());
			nbt.putInt("power", outputValue.getSecond());
		}
	}

	private void readPacket(CompoundTag nbt) {
		outputValue = Pair.of(nbt.getInt("voltage"), nbt.getInt("power"));
	}

	@Override
	public void saveAdditional(CompoundTag compound) {
		super.saveAdditional(compound);
		writePacket(compound);
	}

	@Override
	public void load(CompoundTag compound) {
		super.load(compound);
		readPacket(compound);
	}

}
