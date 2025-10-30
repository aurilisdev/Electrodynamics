package electrodynamics.common.tile.pipelines.gas.tank;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerGasTankGeneric;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import voltaic.api.gas.Gas;
import voltaic.api.gas.GasAction;
import voltaic.api.gas.GasStack;
import voltaic.common.network.utils.GasUtilities;
import voltaic.prefab.properties.types.PropertyTypes;
import voltaic.prefab.properties.variant.SingleProperty;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentContainerProvider;
import voltaic.prefab.tile.components.type.ComponentGasHandlerSimple;
import voltaic.prefab.tile.components.type.ComponentInventory;
import voltaic.prefab.tile.components.type.ComponentPacketHandler;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.tile.types.GenericGasTile;
import voltaic.prefab.utilities.BlockEntityUtils;

public class GenericTileGasTank extends GenericGasTile {

	public static final double INSULATION_EFFECTIVENESS = 1.05;

	public static final double HEAT_LOSS = 0.0025; // .05 / 20
	
	public final SingleProperty<Double> insulationBonus = property(new SingleProperty<>(PropertyTypes.DOUBLE, "insulationbonus", 1.0));
	

	public GenericTileGasTank(BlockEntityType<?> type, BlockPos pos, BlockState state, SubtypeMachine machine, int capacity, int maxPressure, int maxTemperature) {
		super(type, pos, state);
		addComponent(new ComponentTickable(this).tickServer(this::tickServer));
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentGasHandlerSimple(this, "", capacity, maxTemperature, maxPressure).setInputDirections(BlockEntityUtils.MachineDirection.TOP).setOutputDirections(BlockEntityUtils.MachineDirection.BOTTOM).setOnGasCondensed(getCondensedHandler()));
		addComponent(new ComponentInventory(this, ComponentInventory.InventoryBuilder.newInv().inputs(6).gasInputs(1).gasOutputs(1)).valid(machineValidator()));
		addComponent(new ComponentContainerProvider(machine.tag(), this).createMenu((id, player) -> new ContainerGasTankGeneric(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));
	}
	
	private double heatRemainder = 0.0;

	public void tickServer(ComponentTickable tick) {
		ComponentGasHandlerSimple handler = getComponent(IComponentType.GasHandler);
		GasUtilities.drainItem(this, handler.asArray());
		GasUtilities.fillItem(this, handler.asArray());
		GasUtilities.outputToPipe(this, handler.asArray(), handler.outputDirections);

		GasStack gasIn = handler.getGas();

		if (!gasIn.isEmpty()) {
		    int temp = gasIn.getTemperature();
		    int diff = Gas.ROOM_TEMPERATURE - temp;   // positive means we need to heat up
		    if (diff != 0) {
			int sign =  (int) Math.signum(diff);

		        double step = HEAT_LOSS / insulationBonus.getValue(); // HEAT_LOSS = 0.025
		        // accumulate toward an integer change, clamp so we never overshoot room temperature
		        heatRemainder += step;
		        int whole = (int) Math.floor(heatRemainder);
		        if (whole > 0) {
		            int allowed = Math.min(Math.abs(diff), whole);
		            heatRemainder -= allowed;
		            int delta = allowed * sign;
		            handler.heat(0, delta, GasAction.EXECUTE);
		        }
		    }
		}


		if (level.getBlockEntity(getBlockPos().below()) instanceof GenericTileGasTank tankBelow) {
			ComponentGasHandlerSimple belowHandler = tankBelow.getComponent(IComponentType.GasHandler);

			handler.drain(belowHandler.fill(handler.getGas(), GasAction.EXECUTE), GasAction.EXECUTE);
		}
	}

	@Override
	public int getComparatorSignal() {
		ComponentGasHandlerSimple handler = getComponent(IComponentType.GasHandler);
		return (int) (handler.getGasAmount() / Math.max(1, handler.getCapacity()) * 15.0);
	}

	@Override
	public void onInventoryChange(ComponentInventory inv, int slot) {
		super.onInventoryChange(inv, slot);
		if (slot > 5) {
			return;
		}

		double insulationBonus = 1.0;

		for (ItemStack item : inv.getInputContents()) {

			if (item.getItem() == ElectrodynamicsItems.ITEM_FIBERGLASSSHEET.get()) {

				insulationBonus *= INSULATION_EFFECTIVENESS;

			}

		}

		this.insulationBonus.setValue(insulationBonus);

	}

}
