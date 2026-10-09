package electrodynamics.common.tile.machines;

import java.util.List;

import javax.annotation.Nullable;

import com.mojang.datafixers.util.Pair;

import electrodynamics.Electrodynamics;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerElectrolosisChamber;
import electrodynamics.common.recipe.categories.fluid2fluid.specificmachines.ElectrolosisChamberRecipe;
import electrodynamics.common.settings.ElectrodynamicsConfig;
import electrodynamics.registers.ElectrodynamicsRecipes;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.IItemHandler;
import voltaic.api.IWrenchItem;
import voltaic.api.electricity.ICapabilityElectrodynamic;
import voltaic.api.multiblock.assemblybased.Multiblock;
import voltaic.api.multiblock.assemblybased.TileMultiblockController;
import voltaic.api.multiblock.assemblybased.TileMultiblockSlave;
import voltaic.common.network.utils.FluidUtilities;
import voltaic.common.recipe.VoltaicRecipe;
import voltaic.prefab.properties.types.PropertyTypes;
import voltaic.prefab.properties.variant.SingleProperty;
import voltaic.prefab.tile.components.CapabilityInputType;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentContainerProvider;
import voltaic.prefab.tile.components.type.ComponentElectrodynamic;
import voltaic.prefab.tile.components.type.ComponentFluidHandlerMulti;
import voltaic.prefab.tile.components.type.ComponentInventory;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.tile.components.utils.IComponentFluidHandler;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.registers.VoltaicCapabilities;

public class TileElectrolosisChamber extends TileMultiblockController {

    public static final ResourceLocation ID = Electrodynamics.rl("electrolosischamber");
    public static final ResourceKey<Multiblock> RESOURCE_KEY = Multiblock.makeKey(ID);

    public static final int MAX_INPUT_TANK_CAPACITY = 5000;
    public static final int MAX_OUTPUT_TANK_CAPACITY = 5000;

    private static final int FLUID_OUT_SLAVE_INDEX = 39;

    public final SingleProperty<Integer> processAmount = property(
	    new SingleProperty<>(getPropertyManager(), PropertyTypes.INTEGER, "processamount", 0));
    public final SingleProperty<Double> operatingTicks = property(
	    new SingleProperty<>(getPropertyManager(), PropertyTypes.DOUBLE, "operatingticks", 0.0));
    public final SingleProperty<Double> neededTicks = property(
	    new SingleProperty<>(getPropertyManager(), PropertyTypes.DOUBLE, "neededticks", 0.0));
    public final SingleProperty<Boolean> isActive = property(
	    new SingleProperty<>(getPropertyManager(), PropertyTypes.BOOLEAN, "isactive", false));
    private @Nullable ElectrolosisChamberRecipe currRecipe;

    public TileElectrolosisChamber(BlockPos worldPos, BlockState blockState) {
	super(ElectrodynamicsTiles.TILE_ELECTROLOSISCHAMBER.get(), worldPos, blockState);
	addComponent(new ComponentElectrodynamic(this, false, true)
		.setInputDirections(BlockEntityUtils.MachineDirection.BACK)
		.voltage(VoltaicCapabilities.DEFAULT_VOLTAGE * 16)
		.maxJoules(ElectrodynamicsConfig.INSTANCE.ELECTROLOSIS_CHAMBER_TARGET_JOULES.get() * 20 * 100));
	addComponent(new ComponentFluidHandlerMulti(this).setInputDirections(BlockEntityUtils.MachineDirection.RIGHT)
		.setInputTanks(1, arr(MAX_INPUT_TANK_CAPACITY))
		.setOutputDirections(BlockEntityUtils.MachineDirection.LEFT).setOutputTanks(1, MAX_OUTPUT_TANK_CAPACITY)
		.setRecipeType(ElectrodynamicsRecipes.ELECTROLOSIS_CHAMBER_TYPE.get()));
	addComponent(new ComponentContainerProvider(SubtypeMachine.electrolosischamber.tag(), this)
		.createMenu((id, player) -> new ContainerElectrolosisChamber(id, player,
			requireComponent(IComponentType.Inventory), getCoordsArray())));
	addComponent(new ComponentInventory(this,
		ComponentInventory.InventoryBuilder.newInv().bucketInputs(1).bucketOutputs(1))
		.valid(machineValidator()));
    }

    @Override
    public void tickServer(Level level, ComponentTickable tickable) {
	super.tickServer(level, tickable);
	ComponentFluidHandlerMulti fluidHandler = requireComponent(IComponentType.FluidHandler);
	ComponentElectrodynamic electro = requireComponent(IComponentType.Electrodynamic);
	FluidUtilities.drainItem(this, fluidHandler.getInputTanks());
	FluidUtilities.fillItem(this, fluidHandler.getOutputTanks());
	outputToPipe();
	ElectrolosisChamberRecipe recipe = currRecipe;
	if (recipe == null) {
	    for (RecipeHolder<ElectrolosisChamberRecipe> holder : level.getRecipeManager()
		    .getAllRecipesFor(ElectrodynamicsRecipes.ELECTROLOSIS_CHAMBER_TYPE.get())) {
		ElectrolosisChamberRecipe candidate = holder.value();
		if (testRecipe(candidate, fluidHandler.getInputTanks())) {
		    recipe = candidate;
		    currRecipe = candidate;
		    break;
		}
	    }
	} else if (!testRecipe(recipe, fluidHandler.getInputTanks())) {
	    recipe = null;
	    currRecipe = null;
	}
	if (recipe == null) {
	    resetProcessing();
	    return;
	}
	FluidTank outputTank = fluidHandler.getOutputTanks()[0];
	if (electro.getJoulesStored() <= 0
		|| !outputTank.isEmpty() && !outputTank.getFluid().is(recipe.getFluidRecipeOutput().getFluid())) {
	    resetProcessing();
	    return;
	}
	double energySatisfaction = electro.getJoulesStored()
		/ ElectrodynamicsConfig.INSTANCE.ELECTROLOSIS_CHAMBER_TARGET_JOULES.get();
	if (energySatisfaction < 1) {
	    neededTicks.setValue(1.0 / energySatisfaction);
	    processAmount.setValue(1);
	} else {
	    neededTicks.setValue(0.0);
	    operatingTicks.setValue(0.0);
	    processAmount.setValue((int) energySatisfaction);
	}
	int room = outputTank.getCapacity() - outputTank.getFluidAmount();
	if (room <= 0) {
	    isActive.setValue(false);
	    return;
	}
	FluidTank inputTank = fluidHandler.getInputTanks()[0];
	int amountToProcess = Math.min(room, processAmount.getValue());
	amountToProcess = Math.min(amountToProcess, inputTank.getFluidAmount());
	if (amountToProcess <= 0) {
	    isActive.setValue(false);
	    return;
	}
	electro.setJoulesStored(0);
	isActive.setValue(true);
	if (neededTicks.getValue() > 0 && operatingTicks.getValue() < neededTicks.getValue()) {
	    operatingTicks.setValue(operatingTicks.getValue() + 1.0);
	    return;
	}
	operatingTicks.setValue(0.0);
	inputTank.drain(amountToProcess, IFluidHandler.FluidAction.EXECUTE);
	outputTank.fill(new FluidStack(recipe.getFluidRecipeOutput().getFluidHolder(), amountToProcess),
		IFluidHandler.FluidAction.EXECUTE);
    }

    private void resetProcessing() {
	operatingTicks.setValue(0.0);
	isActive.setValue(false);
	processAmount.setValue(0);
	neededTicks.setValue(0.0);
    }

    private static boolean testRecipe(ElectrolosisChamberRecipe recipe, FluidTank[] inputTanks) {
	Pair<List<Integer>, Boolean> pair = VoltaicRecipe.areFluidsValid(recipe.getFluidIngredients(), inputTanks);
	if (pair.getSecond()) {
	    recipe.setFluidArrangement(pair.getFirst());
	    return true;
	}
	return false;
    }

    private void outputToPipe() {
	Level level = getLevel();
	if (level == null || level.isClientSide() || !isFormed.getValue())
	    return;

	List<BlockPos> positions = slavePositions.getValue();
	if (positions.size() <= FLUID_OUT_SLAVE_INDEX)
	    return;

	BlockPos portPos = positions.get(FLUID_OUT_SLAVE_INDEX);
	if (portPos == null)
	    return;

	ComponentFluidHandlerMulti component = requireComponent(IComponentType.FluidHandler);
	Direction facing = getFacing();
	for (Direction relative : component.outputDirections) {
	    Direction direction = BlockEntityUtils.getRelativeSide(facing, relative);
	    BlockPos targetPos = portPos.relative(direction);
	    BlockEntity faceTile = level.getBlockEntity(targetPos);
	    if (faceTile == null)
		continue;

	    IFluidHandler handler = level.getCapability(Capabilities.FluidHandler.BLOCK, targetPos,
		    faceTile.getBlockState(), faceTile, direction.getOpposite());
	    if (handler == null)
		continue;

	    for (FluidTank tank : component.getOutputTanks()) {
		FluidStack fluid = tank.getFluid();
		if (fluid.isEmpty())
		    continue;

		int acceptedAmount = handler.fill(fluid.copy(), IFluidHandler.FluidAction.EXECUTE);
		if (acceptedAmount > 0) {
		    tank.drain(acceptedAmount, IFluidHandler.FluidAction.EXECUTE);
		}
	    }
	}
    }

    @Override
    public @Nullable IFluidHandler getFluidHandlerCapability(@Nullable Direction side) {
	return null;
    }

    @Override
    public @Nullable IFluidHandler getSlaveFluidHandlerCapability(TileMultiblockSlave slave, @Nullable Direction side) {
	if (slave.index.getValue() != 35 && slave.index.getValue() != 39)
	    return null;
	return this.<IComponentFluidHandler>requireComponent(IComponentType.FluidHandler).getCapability(side,
		CapabilityInputType.NONE);
    }

    @Override
    public @Nullable ICapabilityElectrodynamic getElectrodynamicCapability(@Nullable Direction side) {
	return null;
    }

    @Override
    public @Nullable ICapabilityElectrodynamic getSlaveCapabilityElectrodynamic(TileMultiblockSlave slave,
	    @Nullable Direction side) {
	if (slave.index.getValue() != 7)
	    return null;
	return this.<ComponentElectrodynamic>requireComponent(IComponentType.Electrodynamic).getCapability(side,
		CapabilityInputType.NONE);
    }

    @Override
    public @Nullable IItemHandler getItemHandlerCapability(@Nullable Direction side) {
	return null;
    }

    @Override
    public ItemInteractionResult useWithItem(Level level, ItemStack used, Player player, InteractionHand hand,
	    BlockHitResult hit) {
	if (level.isClientSide)
	    return super.useWithItem(level, used, player, hand, hit);

	if (hit.getBlockPos().equals(getBlockPos()) && used.getItem() instanceof IWrenchItem) {
	    checkFormed();
	    if (isFormed.getValue()) {
		formMultiblock(level);
	    } else {
		destroyMultiblock(level);
	    }
	    return ItemInteractionResult.CONSUME;
	}
	return super.useWithItem(level, used, player, hand, hit);

    }

    @Override
    public InteractionResult useWithoutItem(Level level, Player player, BlockHitResult hit) {
	if (!isFormed.getValue())
	    return InteractionResult.FAIL;
	return super.useWithoutItem(level, player, hit);
    }

    @Override
    public ResourceLocation getMultiblockId() {
	return ID;
    }

    @Override
    public ResourceKey<Multiblock> getResourceKey() {
	return RESOURCE_KEY;
    }
}
