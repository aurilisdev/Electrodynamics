package electrodynamics.common.tile.electricitygrid.generators;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerCreativePowerSource;
import electrodynamics.prefab.utilities.ElectricityUtils;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import voltaic.prefab.properties.types.PropertyTypes;
import voltaic.prefab.properties.variant.SingleProperty;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.type.ComponentContainerProvider;
import voltaic.prefab.tile.components.type.ComponentElectrodynamic;
import voltaic.prefab.tile.components.type.ComponentPacketHandler;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.prefab.utilities.object.CachedTileOutput;
import voltaic.prefab.utilities.object.TransferPack;

public class TileCreativePowerSource extends GenericTile {

	private static final int POWER_MULTIPLIER = 1000000;

	public final SingleProperty<Integer> voltage = property(new SingleProperty<>(PropertyTypes.INTEGER, "setvoltage", 0));
	public final SingleProperty<Double> power = property(new SingleProperty<>(PropertyTypes.DOUBLE, "setpower", 0.0));
	private final SingleProperty<Boolean> hasRedstoneSignal = property(new SingleProperty<>(PropertyTypes.BOOLEAN, "redstonesignal", false));

	protected List<CachedTileOutput> outputs;

	public TileCreativePowerSource() {
		super(ElectrodynamicsTiles.TILE_CREATIVEPOWERSOURCE.get());
		addComponent(new ComponentTickable(this).tickServer(this::tickServer));
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentElectrodynamic(this, true, false).setOutputDirections(BlockEntityUtils.MachineDirection.values()).voltage(-1));
		addComponent(new ComponentContainerProvider(SubtypeMachine.creativepowersource.tag(), this).createMenu((id, player) -> new ContainerCreativePowerSource(id, player, getCoordsArray())));
	}

	private void tickServer(ComponentTickable tick) {
		if (hasRedstoneSignal.getValue()) {
			return;
		}
		// ComponentElectrodynamic electro = getComponent(ComponentType.Electrodynamic);
		if (outputs == null) {
			outputs = new ArrayList<>();
			for (Direction dir : Direction.values()) {
				outputs.add(new CachedTileOutput(level, worldPosition.relative(dir)));
			}
		}
		if (tick.getTicks() % 40 == 0) {
			for (int i = 0; i < Direction.values().length; i++) {
				CachedTileOutput cache = outputs.get(i);
				cache.update(worldPosition.relative(Direction.values()[i]));
			}
		}

		if (voltage.getValue() <= 0) {
			return;
		}

		// electro.voltage(power.get());
		TransferPack output = TransferPack.joulesVoltage(power.getValue() * POWER_MULTIPLIER / 20.0, voltage.getValue());
		for (int i = 0; i < outputs.size(); i++) {
			CachedTileOutput cache = outputs.get(i);
			Direction dir = Direction.values()[i];
			if (cache.valid()) {
				ElectricityUtils.receivePower(cache.getSafe(), dir.getOpposite(), output, false);
			}
		}

	}

	@Override
	public int getComparatorSignal() {
		return power.getValue() > 0 ? 15 : 0;
	}

	@Override
	public void onNeightborChanged(BlockPos neighbor, boolean blockStateTrigger) {
		if (level.isClientSide) {
			return;
		}
		hasRedstoneSignal.setValue(level.hasNeighborSignal(getBlockPos()));
	}
}
