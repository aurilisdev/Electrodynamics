package electrodynamics.common.tile.processor;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.tile.processing.IO2OProcessor;
import electrodynamics.api.utilities.TileUtilities;
import electrodynamics.common.block.BlockGenericMachine;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.ContainerElectricFurnace;
import electrodynamics.common.settings.Constants;
import electrodynamics.common.tile.generic.GenericTileProcessor;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class TileElectricFurnace extends GenericTileProcessor implements IO2OProcessor {

	public static final int[] SLOTS_INPUT = new int[] { 0 };
	public static final int[] SLOTS_OUTPUT = new int[] { 1 };

	public TileElectricFurnace() {
		super(DeferredRegisters.TILE_ELECTRICFURNACE.get());
		addUpgradeSlots(2, 3, 4);
	}

	@Override
	public double getJoulesPerTick() {
		return Constants.ELECTRICFURNACE_USAGE_PER_TICK * currentSpeedMultiplier;
	}

	@Override
	public int getRequiredTicks() {
		return Constants.ELECTRICFURNACE_REQUIRED_TICKS;
	}

	protected IRecipe<?> cachedRecipe = null;

	@Override
	public boolean canProcess() {
		if (joules >= getJoulesPerTick()) {
			if (getBlockState().getBlock() == DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.electricfurnace)) {
				world.setBlockState(pos,
						DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.electricfurnacerunning).getDefaultState().with(BlockGenericMachine.FACING, getBlockState().get(BlockGenericMachine.FACING)), 3);
			}
			if (!getInput().isEmpty()) {
				boolean hasRecipe = cachedRecipe != null;
				if (!hasRecipe) {
					for (IRecipe<?> recipe : world.getRecipeManager().getRecipes()) {
						if (recipe.getType() == IRecipeType.SMELTING) {
							if (recipe.getIngredients().get(0).test(getInput())) {
								hasRecipe = true;
								cachedRecipe = recipe;
							}
						}
					}
				}
				if (hasRecipe && cachedRecipe.getIngredients().get(0).test(getInput())) {
					ItemStack output = getOutput();
					ItemStack result = cachedRecipe.getRecipeOutput();
					return (output.isEmpty() || ItemStack.areItemsEqual(output, result)) && output.getCount() + result.getCount() <= output.getMaxStackSize();
				}
			}
		} else if (getBlockState().getBlock() == DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.electricfurnacerunning)) {
			world.setBlockState(pos, DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.electricfurnace).getDefaultState().with(BlockGenericMachine.FACING, getBlockState().get(BlockGenericMachine.FACING)), 3);
		}
		return false;
	}

	@Override
	protected void failedOperation() {
		super.failedOperation();
		cachedRecipe = null;
	}

	@Override
	public void process() {
		ItemStack output = getStackInSlot(1);
		ItemStack result = cachedRecipe.getRecipeOutput();
		if (!output.isEmpty()) {
			output.setCount(output.getCount() + result.getCount());
		} else {
			setInventorySlotContents(1, result.copy());
		}
		decrStackSize(0, 1);
	}

	@Override
	public int getSizeInventory() {
		return 5;
	}

	@Override
	public int[] getSlotsForFace(Direction side) {
		return side == Direction.UP || side == TileUtilities.getRelativeSide(getFacing(), Direction.WEST) ? SLOTS_INPUT
				: side == Direction.DOWN || side == TileUtilities.getRelativeSide(getFacing(), Direction.EAST) ? SLOTS_OUTPUT : SLOTS_EMPTY;
	}

	@Override
	protected Container createMenu(int id, PlayerInventory player) {
		return new ContainerElectricFurnace(id, player, this, inventorydata);
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TranslationTextComponent("container.electricfurnace");
	}

	@Override
	public ItemStack getInput() {
		return getStackInSlot(0);
	}

	@Override
	public ItemStack getOutput() {
		return getStackInSlot(1);
	}

	@Override
	public void setOutput(ItemStack stack) {
		setInventorySlotContents(1, stack);
	}

}
