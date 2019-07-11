package physica.core.common.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import physica.CoreReferences;
import physica.api.core.cable.IConductor;
import physica.core.common.CoreTabRegister;
import physica.library.energy.ElectricityDisplay;
import physica.library.energy.ElectricityUtilities;
import physica.library.energy.base.Unit;

public class ItemMultimeter extends Item {

	public ItemMultimeter() {
		setUnlocalizedName("multimeter");
		setTextureName(CoreReferences.PREFIX + "multimeter");
		setMaxStackSize(1);
		setCreativeTab(CoreTabRegister.coreTab);
	}

	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote)
		{
			TileEntity tile = world.getTileEntity(x, y, z);
			if (tile instanceof IConductor)
			{
				IConductor conductor = (IConductor) tile;
				player.addChatMessage(new ChatComponentText("Network Stats"));
				player.addChatMessage(new ChatComponentText(" - Conductors: " + conductor.getNetwork().conductorSet.size()));
				player.addChatMessage(new ChatComponentText(" - Acceptors: " + conductor.getNetwork().acceptorInputMap.size()));
				int voltage = conductor.getNetwork().getSafeVoltageLevel();
				player.addChatMessage(new ChatComponentText(" - Safe voltage level: " + (voltage < 0 ? "infinite voltage" : voltage)));
				player.addChatMessage(new ChatComponentText(" - Power Transfer: " + ElectricityDisplay.getDisplay(ElectricityUtilities.convertEnergy(conductor.getNetwork().getEnergyTransmittedLastTick(), Unit.RF, Unit.WATT), Unit.WATT)
						+ " / " + ElectricityDisplay.getDisplay(ElectricityUtilities.convertEnergy(conductor.getNetwork().getMaxPowerTransfer(), Unit.RF, Unit.WATT), Unit.WATT)));

			}
		}
		return super.onItemUseFirst(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
	}
}
