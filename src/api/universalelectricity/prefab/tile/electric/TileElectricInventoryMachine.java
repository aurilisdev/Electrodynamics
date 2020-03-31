package universalelectricity.prefab.tile.electric;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import physica.core.common.utility.InventoryUtility;

public abstract class TileElectricInventoryMachine extends TileElectricMachine {
	private static String NBT_SLOTS = "slots";

	protected IItemHandlerModifiable inventory;

	public TileElectricInventoryMachine() {
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);

		InventoryUtility.readFromNBT(tag, inventory);
		CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.readNBT(inventory, null, tag.getTag(NBT_SLOTS));
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);

		tag.setTag(NBT_SLOTS, CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.writeNBT(inventory, null));

		return tag;
	}

	@Override
	public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}

	@Override
	@Nonnull
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return (T) inventory;
		}

		return super.getCapability(capability, facing);
	}

	public IItemHandlerModifiable getInventory() {
		return inventory;
	}
}