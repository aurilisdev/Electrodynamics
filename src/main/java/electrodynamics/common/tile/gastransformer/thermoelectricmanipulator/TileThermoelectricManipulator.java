package electrodynamics.common.tile.gastransformer.thermoelectricmanipulator;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.common.inventory.container.tile.ContainerThermoelectricManipulator;
import electrodynamics.common.tile.gastransformer.GenericTileGasTransformer;
import electrodynamics.common.tile.gastransformer.TileGasTransformerAddonTank;
import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyType;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerMulti;
import electrodynamics.prefab.tile.components.type.ComponentGasHandlerMulti;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.tile.components.type.ComponentInventory.InventoryBuilder;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

public class TileThermoelectricManipulator extends GenericTileGasTransformer {
	
	public final Property<Double> targetTemperature = property(new Property<>(PropertyType.Double, "targettemperature", 0.0));
	
	public TileThermoelectricManipulator(BlockPos worldPos, BlockState blockState) {
		super(ElectrodynamicsBlockTypes.TILE_THERMOELECTRIC_MANIPULATOR.get(), worldPos, blockState);
		addComponent(new ComponentElectrodynamic(this).input(Direction.DOWN).voltage(ElectrodynamicsCapabilities.DEFAULT_VOLTAGE).maxJoules(BASE_INPUT_CAPACITY * 10));
		addComponent(new ComponentFluidHandlerMulti(this).setInputDirections(Direction.DOWN).setInputTanks(1, arr((int) BASE_INPUT_CAPACITY)).setOutputDirections(Direction.DOWN).setOutputTanks(1, arr((int) BASE_OUTPUT_CAPACITY)));
	}

	@Override
	public boolean canProcess(ComponentProcessor processor) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private boolean checkFluidConditions() {
		
		return false;
	}
	
	private boolean checkGasConditions() {
		
		return false;
	}

	@Override
	public void process(ComponentProcessor processor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tickClient(ComponentTickable tickable) {
		// TODO Auto-generated method stub
		
	}
	
	public void updateAddonTanks(int count, boolean isLeft) {
		ComponentGasHandlerMulti handler = getComponent(ComponentType.GasHandler);
		ComponentFluidHandlerMulti multi = getComponent(ComponentType.FluidHandler);
		if (isLeft) {
			multi.getInputTanks()[0].setCapacity((int) (BASE_INPUT_CAPACITY + TileGasTransformerAddonTank.ADDITIONAL_CAPACITY * (double) count));
			handler.getInputTanks()[0].setCapacity(BASE_INPUT_CAPACITY + TileGasTransformerAddonTank.ADDITIONAL_CAPACITY * (double) count); 
		} else {
			multi.getOutputTanks()[0].setCapacity((int) (BASE_INPUT_CAPACITY + TileGasTransformerAddonTank.ADDITIONAL_CAPACITY * (double) count));
			handler.getOutputTanks()[0].setCapacity(BASE_INPUT_CAPACITY + TileGasTransformerAddonTank.ADDITIONAL_CAPACITY * (double) count); 
		}
	}

	@Override
	public ComponentContainerProvider getContainerProvider() {
		return new ComponentContainerProvider("container.thermoelectricmanipulator").createMenu((id, inv) -> new ContainerThermoelectricManipulator(id, inv, getComponent(ComponentType.Inventory), getCoordsArray()));
	}

	@Override
	public ComponentInventory getInventory() {
		return new ComponentInventory(this, InventoryBuilder.newInv().bucketInputs(1).gasInputs(1).bucketOutputs(1).gasOutputs(1).upgrades(3)).valid(machineValidator()).validUpgrades(ContainerThermoelectricManipulator.VALID_UPGRADES);
	}

}
