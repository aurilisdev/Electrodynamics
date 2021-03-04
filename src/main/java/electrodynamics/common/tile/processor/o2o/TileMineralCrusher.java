package electrodynamics.common.tile.processor.o2o;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.tile.processing.IO2OProcessor;
import electrodynamics.api.utilities.TileUtilities;
import electrodynamics.common.inventory.container.ContainerO2OProcessor;
import electrodynamics.common.settings.Constants;
import electrodynamics.common.tile.generic.GenericTileProcessor;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class TileMineralCrusher extends GenericTileProcessor implements IO2OProcessor {
    public static final double REQUIRED_JOULES_PER_TICK = 110;
    public static final int REQUIRED_TICKS = 200;

    public static final int[] SLOTS_INPUT = new int[] { 0 };
    public static final int[] SLOTS_OUTPUT = new int[] { 1 };

    public TileMineralCrusher() {
	super(DeferredRegisters.TILE_MINERALCRUSHER.get());
	addUpgradeSlots(2, 3, 4);
    }

    @Override
    public double getJoulesPerTick() {
	return Constants.MINERALCRUSHER_USAGE_PER_TICK * currentSpeedMultiplier;
    }

    @Override
    public double getVoltage() {
	return DEFAULT_BASIC_MACHINE_VOLTAGE * 2;
    }

    @Override
    public int getRequiredTicks() {
	return Constants.MINERALCRUSHER_REQUIRED_TICKS;
    }

    @Override
    public int getSizeInventory() {
	return 5;
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
	return side == Direction.UP || side == TileUtilities.getRelativeSide(getFacing(), Direction.WEST) ? SLOTS_INPUT
		: side == Direction.DOWN || side == TileUtilities.getRelativeSide(getFacing(), Direction.EAST)
			? SLOTS_OUTPUT
			: SLOTS_EMPTY;
    }

    @Override
    protected Container createMenu(int id, PlayerInventory player) {
	return new ContainerO2OProcessor(id, player, this, getInventoryData());
    }

    @Override
    public ITextComponent getDisplayName() {
	return new TranslationTextComponent("container.mineralcrusher");
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
