package physica.core.common.items;

import java.util.List;

import cofh.api.energy.ItemEnergyContainer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import physica.CoreReferences;
import physica.core.common.CoreTabRegister;
import physica.library.energy.ElectricityDisplay;
import physica.library.energy.ElectricityUtilities;
import physica.library.energy.base.Unit;

public class ItemBattery extends ItemEnergyContainer {

	public ItemBattery(String name) {
		super((int) (250000 / 0.4D));
		setUnlocalizedName(name);
		setTextureName(CoreReferences.PREFIX + name);
		setCreativeTab(CoreTabRegister.coreTab);
		setMaxStackSize(1);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void getSubItems(Item item, CreativeTabs tabs, @SuppressWarnings("rawtypes") List list) {
		super.getSubItems(item, tabs, list);
		ItemStack charged = new ItemStack(this);
		receiveEnergy(charged, capacity, false);
		list.add(charged);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, @SuppressWarnings("rawtypes") List info, boolean par4) {
		super.addInformation(stack, player, info, par4);
		info.add(EnumChatFormatting.AQUA + "Energy Stored: " + EnumChatFormatting.GRAY
				+ ElectricityDisplay.getDisplay(ElectricityUtilities.convertEnergy(getEnergyStored(stack), Unit.RF, Unit.WATT), Unit.WATT));
	}
}
