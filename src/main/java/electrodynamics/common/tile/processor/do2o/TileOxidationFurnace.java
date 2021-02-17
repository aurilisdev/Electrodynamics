package electrodynamics.common.tile.processor.do2o;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.tile.processing.IDO2OProcessor;
import electrodynamics.api.utilities.TileUtilities;
import electrodynamics.common.block.BlockGenericMachine;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.ContainerDO2OProcessor;
import electrodynamics.common.tile.generic.GenericTileProcessor;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class TileOxidationFurnace extends GenericTileProcessor implements IDO2OProcessor {
	public static final double REQUIRED_JOULES_PER_TICK = 90;
	public static final int REQUIRED_TICKS = 250;

	public static final int[] SLOTS_INPUT1 = new int[] { 0 };
	public static final int[] SLOTS_INPUT2 = new int[] { 1 };
	public static final int[] SLOTS_OUTPUT = new int[] { 2 };

	public TileOxidationFurnace() {
		super(DeferredRegisters.TILE_OXIDATIONFURNACE.get());
		addUpgradeSlots(3, 4, 5);
	}

	@Override
	public boolean canProcess() {
		if (super.canProcess()) {
			if (getBlockState().getBlock() == DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.oxidationfurnace)) {
				world.setBlockState(pos,
						DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.oxidationfurnacerunning).getDefaultState().with(BlockGenericMachine.FACING, getBlockState().get(BlockGenericMachine.FACING)), 3);
			}
			return true;
		} else if (getBlockState().getBlock() == DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.oxidationfurnacerunning)) {
			world.setBlockState(pos, DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.oxidationfurnace).getDefaultState().with(BlockGenericMachine.FACING, getBlockState().get(BlockGenericMachine.FACING)), 3);
		}
		return false;
	}

	@Override
	public double getJoulesPerTick() {
		return REQUIRED_JOULES_PER_TICK * currentSpeedMultiplier;
	}

	@Override
	public int getRequiredTicks() {
		return REQUIRED_TICKS;
	}

	@Override
	public double getVoltage() {
		return DEFAULT_BASIC_MACHINE_VOLTAGE * 2;
	}

	@Override
	public int getSizeInventory() {
		return 6;
	}

	@Override
	public int[] getSlotsForFace(Direction side) {
		return side == Direction.UP ? SLOTS_INPUT1
				: side == TileUtilities.getRelativeSide(getFacing(), Direction.WEST) ? SLOTS_INPUT2 : side == TileUtilities.getRelativeSide(getFacing(), Direction.EAST) ? SLOTS_OUTPUT : SLOTS_EMPTY;
	}

	@Override
	protected Container createMenu(int id, PlayerInventory player) {
		return new ContainerDO2OProcessor(id, player, this, inventorydata);
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TranslationTextComponent("container.oxidationfurnace");
	}

	@Override
	public ItemStack getInput1() {
		return getStackInSlot(0);
	}

	@Override
	public ItemStack getInput2() {
		return getStackInSlot(1);
	}

	@Override
	public ItemStack getOutput() {
		return getStackInSlot(2);
	}

	@Override
	public void setOutput(ItemStack stack) {
		setInventorySlotContents(2, stack);
	}

}
