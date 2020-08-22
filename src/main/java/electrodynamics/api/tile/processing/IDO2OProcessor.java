package electrodynamics.api.tile.processing;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public interface IDO2OProcessor extends IElectricProcessor {
	default TileEntityType<?> getType() {
		return ((TileEntity) this).getType();
	}

	ItemStack getInput1();

	ItemStack getInput2();

	ItemStack getOutput();

	void setOutput(ItemStack stack);
}
