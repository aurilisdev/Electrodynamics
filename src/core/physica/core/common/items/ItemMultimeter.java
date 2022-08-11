package physica.core.common.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import physica.CoreReferences;
import physica.api.core.abstraction.AbstractionLayer;
import physica.api.core.abstraction.Face;
import physica.api.core.conductor.IConductor;
import physica.core.common.CoreTabRegister;
import physica.library.energy.ElectricityDisplay;
import physica.library.energy.ElectricityUtilities;
import physica.library.energy.base.Unit;
import physica.library.util.ChatUtilities;

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

		TileEntity tile = world.getTileEntity(x, y, z);
		Face dir = Face.getOrientation(side);
		if (tile instanceof IConductor)
		{
			if (!world.isRemote)
			{
				IConductor conductor = (IConductor) tile;
				player.addChatMessage(new ChatComponentText("Network Stats"));
				player.addChatMessage(new ChatComponentText(" - Conductors: " + conductor.getNetwork().conductorSet.size()));
				player.addChatMessage(new ChatComponentText(" - Acceptors: " + conductor.getNetwork().acceptorInputMap.size()));
				int voltage = conductor.getNetwork().getSafeVoltageLevel();
				player.addChatMessage(new ChatComponentText(" - Safe voltage level: " + (voltage < 0 ? "infinite voltage" : voltage + " V")));
				player.addChatMessage(new ChatComponentText(" - Power Transfer: " + ElectricityDisplay.getDisplay(ElectricityUtilities.convertEnergy(conductor.getNetwork().getEnergyTransmittedLastTick(), Unit.RF, Unit.WATT), Unit.WATT)
						+ " / " + ElectricityDisplay.getDisplay(ElectricityUtilities.convertEnergy(conductor.getNetwork().getMaxPowerTransfer(), Unit.RF, Unit.WATT), Unit.WATT)));
			}
		} else if (AbstractionLayer.Electricity.isElectricProvider(tile))
		{
			ChatUtilities.addSpamlessMessages(Integer.MAX_VALUE - 210, "Power Provider Stats",
					" - Energy Stored: " + ElectricityDisplay.getDisplay(ElectricityUtilities.convertEnergy(AbstractionLayer.Electricity.getElectricityStored(tile, dir), Unit.RF, Unit.WATTHOUR), Unit.WATTHOUR) + " / "
							+ ElectricityDisplay.getDisplay(ElectricityUtilities.convertEnergy(AbstractionLayer.Electricity.getElectricCapacity(tile, dir), Unit.RF, Unit.WATT), Unit.WATT),
					" - Side Output: " + ElectricityDisplay.getDisplay(ElectricityUtilities.convertEnergy(AbstractionLayer.Electricity.extractElectricity(tile, dir, Integer.MAX_VALUE, true), Unit.RF, Unit.WATT), Unit.WATT));
			return true;
		} else if (AbstractionLayer.Electricity.isElectricReceiver(tile))
		{
			ChatUtilities.addSpamlessMessages(Integer.MAX_VALUE - 200, "Power Receiver Stats",
					" - Energy Stored: " + ElectricityDisplay.getDisplay(ElectricityUtilities.convertEnergy(AbstractionLayer.Electricity.getElectricityStored(tile, dir), Unit.RF, Unit.WATTHOUR), Unit.WATTHOUR));
			return true;
		}
		return super.onItemUseFirst(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
	}
}
