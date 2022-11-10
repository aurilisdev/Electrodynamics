package electrodynamics.common.inventory.container.tile;

import electrodynamics.common.packet.NetworkHandler;
import electrodynamics.common.packet.types.PacketPowerSetting;
import electrodynamics.common.tile.TileCreativePowerSource;
import electrodynamics.prefab.inventory.container.GenericContainerBlockEntity;
import electrodynamics.registers.ElectrodynamicsMenuTypes;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ContainerCreativePowerSource extends GenericContainerBlockEntity<TileCreativePowerSource> {

	public ContainerCreativePowerSource(int id, Inventory playerinv) {
		this(id, playerinv, new SimpleContainer(0), new SimpleContainerData(3));
	}

	public ContainerCreativePowerSource(int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
		super(ElectrodynamicsMenuTypes.CONTAINER_CREATIVEPOWERSOURCE.get(), id, playerinv, new SimpleContainer(), inventorydata);
	}

	public ContainerCreativePowerSource(MenuType<?> type, int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
		super(type, id, playerinv, inventory, inventorydata);
	}

	@Override
	public void addInventorySlots(Container inv, Inventory playerinv) {
	}

	@OnlyIn(Dist.CLIENT)
	public void setValues(String voltageString, String powerString) {
		Integer voltage = 0;
		Integer power = 0;
		try {
			voltage = Integer.parseInt(voltageString);
		} catch (Exception e) {

		}
		try {
			power = Integer.parseInt(powerString);
		} catch (Exception e) {

		}
		if (getHostFromIntArray() != null) {
			NetworkHandler.CHANNEL.sendToServer(new PacketPowerSetting(voltage, power, getHostFromIntArray().getBlockPos()));
		}

	}

}
