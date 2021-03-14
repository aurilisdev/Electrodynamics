package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.common.block.BlockGenericMachine;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.ContainerElectricFurnace;
import electrodynamics.common.settings.Constants;
import electrodynamics.common.tile.generic.GenericTileTicking;
import electrodynamics.common.tile.generic.component.ComponentType;
import electrodynamics.common.tile.generic.component.type.ComponentContainerProvider;
import electrodynamics.common.tile.generic.component.type.ComponentDirection;
import electrodynamics.common.tile.generic.component.type.ComponentElectrodynamic;
import electrodynamics.common.tile.generic.component.type.ComponentInventory;
import electrodynamics.common.tile.generic.component.type.ComponentPacketHandler;
import electrodynamics.common.tile.generic.component.type.ComponentProcessor;
import electrodynamics.common.tile.generic.component.type.ComponentProcessorType;
import electrodynamics.common.tile.generic.component.type.ComponentTickable;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.Direction;

public class TileElectricFurnace extends GenericTileTicking {

    protected IRecipe<?> cachedRecipe = null;
    protected long timeSinceChange = 0;

    public TileElectricFurnace() {
	super(DeferredRegisters.TILE_ELECTRICFURNACE.get());
	addComponent(new ComponentDirection());
	addComponent(new ComponentPacketHandler());
	addComponent(new ComponentTickable());
	addComponent(new ComponentElectrodynamic(this).addRelativeInputDirection(Direction.NORTH));
	addComponent(new ComponentInventory().setInventorySize(5).addSlotOnFace(Direction.UP, 0)
		.addSlotOnFace(Direction.DOWN, 1));
	addComponent(new ComponentContainerProvider("container.electricfurnace")
		.setCreateMenuFunction((id, player) -> new ContainerElectricFurnace(id, player,
			getComponent(ComponentType.Inventory), getCoordsArray())));
	addComponent(new ComponentProcessor(this).addUpgradeSlots(2, 3, 4).setCanProcess(this::canProcess)
		.setFailed(component -> cachedRecipe = null).setProcess(this::process)
		.setRequiredTicks(Constants.ELECTRICFURNACE_REQUIRED_TICKS)
		.setJoulesPerTick(Constants.ELECTRICFURNACE_USAGE_PER_TICK)
		.setType(ComponentProcessorType.ObjectToObject));
    }

    protected void process(ComponentProcessor component) {
	ComponentInventory inv = getComponent(ComponentType.Inventory);
	ItemStack output = component.getOutput();
	ItemStack result = cachedRecipe.getRecipeOutput();
	if (!output.isEmpty()) {
	    output.setCount(output.getCount() + result.getCount());
	} else {
	    component.setOutput(result.copy());
	}
	ItemStack input = component.getInput();
	input.shrink(1);
	if (input.getCount() == 0) {
	    inv.setInventorySlotContents(0, ItemStack.EMPTY);
	}
    }

    protected boolean canProcess(ComponentProcessor component) {
	timeSinceChange++;
	if (this.<ComponentElectrodynamic>getComponent(ComponentType.Electrodynamic)
		.getJoulesStored() >= component.getJoulesPerTick() * component.operatingSpeed) {
	    if (timeSinceChange > 40 && getBlockState().getBlock() == DeferredRegisters.SUBTYPEBLOCK_MAPPINGS
		    .get(SubtypeMachine.electricfurnace)) {
		world.setBlockState(pos,
			DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.electricfurnacerunning)
				.getDefaultState().with(BlockGenericMachine.FACING,
					getBlockState().get(BlockGenericMachine.FACING)),
			2 | 16 | 32);
		timeSinceChange = 0;
	    }
	    if (!component.getInput().isEmpty()) {
		if (cachedRecipe != null && !cachedRecipe.getIngredients().get(0).test(component.getInput())) {
		    cachedRecipe = null;
		}
		boolean hasRecipe = cachedRecipe != null;
		if (!hasRecipe) {
		    for (IRecipe<?> recipe : world.getRecipeManager().getRecipes()) {
			if (recipe.getType() == IRecipeType.SMELTING
				&& recipe.getIngredients().get(0).test(component.getInput())) {
			    hasRecipe = true;
			    cachedRecipe = recipe;
			}
		    }
		}
		if (hasRecipe && cachedRecipe.getIngredients().get(0).test(component.getInput())) {
		    ItemStack output = component.getOutput();
		    ItemStack result = cachedRecipe.getRecipeOutput();
		    return (output.isEmpty() || ItemStack.areItemsEqual(output, result))
			    && output.getCount() + result.getCount() <= output.getMaxStackSize();
		}
	    }
	} else if (timeSinceChange > 40 && getBlockState().getBlock() == DeferredRegisters.SUBTYPEBLOCK_MAPPINGS
		.get(SubtypeMachine.electricfurnacerunning)) {
	    timeSinceChange = 0;
	    world.setBlockState(
		    pos, DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.electricfurnace).getDefaultState()
			    .with(BlockGenericMachine.FACING, getBlockState().get(BlockGenericMachine.FACING)),
		    2 | 16 | 32);
	}
	return false;
    }
}
