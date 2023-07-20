package electrodynamics.common.tile.network.electric;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerPotentiometer;
import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyType;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.utilities.object.TransferPack;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

public class TilePotentiometer extends GenericTile {

	public final Property<Double> powerConsumption = property(new Property<>(PropertyType.Double, "consumption", -1.0)).onChange((prop, oldval) -> {
		if (level.isClientSide) {
			return;
		}
		setChanged();
	});

	public TilePotentiometer(BlockPos pos, BlockState state) {
		super(ElectrodynamicsBlockTypes.TILE_POTENTIOMETER.get(), pos, state);
		addComponent(new ComponentInventory(this));
		addComponent(new ComponentContainerProvider(SubtypeMachine.potentiometer, this).createMenu((id, player) -> new ContainerPotentiometer(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
		addComponent(new ComponentElectrodynamic(this).receivePower(this::receivePower).input(Direction.NORTH).input(Direction.EAST).input(Direction.SOUTH).input(Direction.WEST).input(Direction.DOWN).voltage(-1));
	}

	private TransferPack receivePower(TransferPack pack, boolean debug) {
		if (powerConsumption.get() < 0) {
			return pack;
		}
		double accepted = Math.min(pack.getJoules(), powerConsumption.get());
		return TransferPack.joulesVoltage(accepted, pack.getVoltage());
	}

}
