package electrodynamics.api.tile.processing;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityType;

public interface IO2OProcessor extends IElectricProcessor {
	TileEntityType<?> getType();

	ItemStack getInput();

	ItemStack getOutput();

	void setOutput(ItemStack stack);
}
