package electrodynamics.client.screen.item;

import electrodynamics.common.inventory.container.item.ContainerPortableChest;
import electrodynamics.prefab.screen.GenericScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ScreenPortableChest extends GenericScreen<ContainerPortableChest> {

	public ScreenPortableChest(ContainerPortableChest container, Inventory inv, Component titleIn) {
		super(container, inv, titleIn);
	}

}
