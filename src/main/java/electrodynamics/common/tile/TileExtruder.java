package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.electricity.CapabilityElectrodynamic;
import electrodynamics.common.inventory.container.ContainerO2OProcessor;
import electrodynamics.common.item.ItemProcessorUpgrade;
import electrodynamics.common.recipe.ElectrodynamicsRecipeInit;
import electrodynamics.common.recipe.categories.o2o.specificmachines.ExtruderRecipe;
import electrodynamics.common.settings.Constants;
import electrodynamics.prefab.tile.GenericTileTicking;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import electrodynamics.prefab.tile.components.type.ComponentProcessorType;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import net.minecraft.util.Direction;

public class TileExtruder extends GenericTileTicking{

	public TileExtruder() {
		this(0);
	}
	
	public TileExtruder(int extra) {
		super(DeferredRegisters.TILE_EXTRUDER.get());
		addComponent(new ComponentDirection());
		addComponent(new ComponentPacketHandler());
		addComponent(new ComponentTickable().tickClient(this::tickClient));
		addComponent(new ComponentElectrodynamic(this).relativeInput(Direction.NORTH)
			.voltage(CapabilityElectrodynamic.DEFAULT_VOLTAGE * 2)
			.maxJoules(Constants.EXTRUDER_USAGE_PER_TICK * 20 * (extra + 1)));
		addComponent(new ComponentInventory(this).size(5 + extra * 2)
			.valid((slot, stack) -> slot == 0 || slot == extra * 2 || extra == 2 && slot == 2
				|| slot != extra && slot != extra * 3 && slot != extra * 5 && stack.getItem() instanceof ItemProcessorUpgrade)
			.setMachineSlots(extra).shouldSendInfo());
		addComponent(new ComponentContainerProvider("container.extruder").createMenu((id, player) -> 
			(new ContainerO2OProcessor(id, player, getComponent(ComponentType.Inventory), getCoordsArray()))));
		addProcessor(new ComponentProcessor(this).upgradeSlots(2, 3, 4)
			.canProcess(component -> component.canProcessO2ORecipe(component, ExtruderRecipe.class,ElectrodynamicsRecipeInit.EXTRUDER_TYPE))
			.process(component -> component.processO2ORecipe(component, ExtruderRecipe.class))
			.requiredTicks(Constants.EXTRUDER_REQUIRED_TICKS).usage(Constants.EXTRUDER_USAGE_PER_TICK)
			.type(ComponentProcessorType.ObjectToObject));
	}
	
	protected void tickClient(ComponentTickable tickable) {
		
	}

}
