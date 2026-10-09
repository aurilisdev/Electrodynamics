package electrodynamics.common.tile.machines.chemicalreactor;

import javax.annotation.Nullable;

import electrodynamics.common.block.chemicalreactor.BlockChemicalReactorExtra;
import electrodynamics.common.inventory.container.tile.ContainerChemicalReactor;
import electrodynamics.common.recipe.categories.chemicalreactor.ChemicalReactorRecipe;
import electrodynamics.registers.ElectrodynamicsRecipes;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.IItemHandler;
import voltaic.prefab.properties.types.PropertyTypes;
import voltaic.prefab.properties.variant.SingleProperty;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentContainerProvider;
import voltaic.prefab.tile.components.type.ComponentElectrodynamic;
import voltaic.prefab.tile.components.type.ComponentFluidHandlerMulti;
import voltaic.prefab.tile.components.type.ComponentGasHandlerMulti;
import voltaic.prefab.tile.components.type.ComponentInventory;
import voltaic.prefab.tile.components.type.ComponentProcessor;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.tile.types.GenericGasTile;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.registers.VoltaicCapabilities;

public class TileChemicalReactor extends GenericGasTile {

    public static final int MAX_FLUID_TANK_CAPACITY = 5000;
    public static final int MAX_GAS_TANK_CAPACITY = 5000;

    public final SingleProperty<Boolean> hasItemInputs = property(
	    new SingleProperty<>(getPropertyManager(), PropertyTypes.BOOLEAN, "hasiteminputs", false));
    public final SingleProperty<Boolean> hasFluidInputs = property(
	    new SingleProperty<>(getPropertyManager(), PropertyTypes.BOOLEAN, "hasfluidinputs", false));
    public final SingleProperty<Boolean> hasGasInputs = property(
	    new SingleProperty<>(getPropertyManager(), PropertyTypes.BOOLEAN, "hasgasinputs", false));

    public TileChemicalReactor(BlockPos worldPos, BlockState blockState) {
	super(ElectrodynamicsTiles.TILE_CHEMICALREACTOR.get(), worldPos, blockState);
	addComponent(new ComponentTickable(this));

	addComponent(new ComponentContainerProvider("chemicalreactor", this)
		.createMenu((id, player) -> new ContainerChemicalReactor(id, player,
			requireComponent(IComponentType.Inventory), getCoordsArray())));
	addComponent(new ComponentElectrodynamic(this, false, true)
		.setInputDirections(BlockEntityUtils.MachineDirection.TOP, BlockEntityUtils.MachineDirection.BOTTOM)
		.voltage(VoltaicCapabilities.DEFAULT_VOLTAGE * 4));
	addComponent(new ComponentFluidHandlerMulti(this)
		.setTanks(2, 2, new int[] { MAX_FLUID_TANK_CAPACITY, MAX_FLUID_TANK_CAPACITY },
			new int[] { MAX_FLUID_TANK_CAPACITY, MAX_FLUID_TANK_CAPACITY })
		.setInputDirections(BlockEntityUtils.MachineDirection.FRONT, BlockEntityUtils.MachineDirection.RIGHT)
		//
		.setOutputDirections(BlockEntityUtils.MachineDirection.BACK, BlockEntityUtils.MachineDirection.LEFT)
		.setRecipeType(ElectrodynamicsRecipes.CHEMICAL_REACTOR_TYPE.get()));
	addComponent(new ComponentInventory(this,
		ComponentInventory.InventoryBuilder.newInv().processors(1, 2, 1, 3).bucketInputs(2).bucketOutputs(2)
			.gasInputs(2).gasOutputs(2).upgrades(3))
		.setDirectionsBySlot(0, BlockEntityUtils.MachineDirection.BACK)
		//
		.setDirectionsBySlot(1, BlockEntityUtils.MachineDirection.RIGHT)
		.setSlotsByDirection(BlockEntityUtils.MachineDirection.LEFT, 2)
		.setSlotsByDirection(BlockEntityUtils.MachineDirection.FRONT, 3, 4, 5)
		.validUpgrades(ContainerChemicalReactor.VALID_UPGRADES).valid(machineValidator()));
	addComponent(new ComponentGasHandlerMulti(this)
		.setInputDirections(BlockEntityUtils.MachineDirection.FRONT, BlockEntityUtils.MachineDirection.RIGHT)
		.setOutputDirections(BlockEntityUtils.MachineDirection.BACK, BlockEntityUtils.MachineDirection.LEFT)
		//
		.setInputTanks(2, arr(MAX_GAS_TANK_CAPACITY, MAX_GAS_TANK_CAPACITY), arr(1000, 1000), arr(1024, 1024))
		.setOutputTanks(2, arr(MAX_GAS_TANK_CAPACITY, MAX_GAS_TANK_CAPACITY), arr(1000, 1000), arr(1024, 1024))
		.setCondensedHandler(getCondensedHandler())
		//
		.setRecipeType(ElectrodynamicsRecipes.CHEMICAL_REACTOR_TYPE.get()));
	addComponent(new ComponentProcessor(this).canProcess(this::canProcess).process(this::process));
    }

    private boolean canProcess(ComponentProcessor processor, Level level, int procNumber) {
	processor.consumeBucket().consumeGasCylinder().dispenseGasCylinder().dispenseBucket().outputToGasPipe();
	outputToPipe(level);

	ChemicalReactorRecipe recipe = processor.prepareRecipe(procNumber,
		ElectrodynamicsRecipes.CHEMICAL_REACTOR_TYPE.get(), ChemicalReactorRecipe.class);

	if (recipe == null) {
	    hasItemInputs.setValue(false);
	    hasFluidInputs.setValue(false);
	    hasGasInputs.setValue(false);
	    return false;
	}

	hasItemInputs.setValue(recipe.hasItemInputs());
	hasFluidInputs.setValue(recipe.hasFluidInputs());
	hasGasInputs.setValue(recipe.hasGasInputs());

	return processor.canProcessMaterialRecipe(recipe, procNumber, 1, 1);

    }

    private void process(ComponentProcessor processor, Level level, int procNumber) {
	processor.processMaterialRecipe(procNumber, ChemicalReactorRecipe.class, 1, 1);
    }

    private void outputToPipe(Level level) {
	ComponentFluidHandlerMulti component = requireComponent(IComponentType.FluidHandler);
	Direction[] outputDirections = component.outputDirections;

	Direction facing = getFacing();

	for (Direction relative : outputDirections) {

	    Direction direction = BlockEntityUtils.getRelativeSide(facing, relative);

	    BlockEntity faceTile = level.getBlockEntity(getBlockPos().relative(direction).offset(0, 2, 0));

	    if (faceTile == null) {
		continue;
	    }

	    IFluidHandler handler = level.getCapability(Capabilities.FluidHandler.BLOCK, faceTile.getBlockPos(),
		    faceTile.getBlockState(), faceTile, direction.getOpposite());

	    if (handler == null) {
		continue;
	    }

	    for (FluidTank fluidTank : component.getOutputTanks()) {

		FluidStack tankFluid = fluidTank.getFluid();

		int amtAccepted = handler.fill(tankFluid, IFluidHandler.FluidAction.EXECUTE);

		FluidStack taken = new FluidStack(tankFluid.getFluid(), amtAccepted);

		fluidTank.drain(taken, IFluidHandler.FluidAction.EXECUTE);
	    }
	}
    }

    @Override
    public @Nullable IFluidHandler getFluidHandlerCapability(@Nullable Direction side) {
	return null;
    }

    @Override
    public @Nullable IItemHandler getItemHandlerCapability(@Nullable Direction side) {
	return null;
    }

    @Override
    public void onBlockDestroyed(Level level) {
	level.destroyBlock(getBlockPos().offset(BlockChemicalReactorExtra.Location.MIDDLE.offsetUpFromParent), false);
	level.destroyBlock(getBlockPos().offset(BlockChemicalReactorExtra.Location.TOP.offsetUpFromParent), false);
	super.onBlockDestroyed(level);
    }
}
