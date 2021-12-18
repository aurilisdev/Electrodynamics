package electrodynamics.prefab.screen;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

public abstract class GenericCustomScreenUpgradeable<T extends AbstractContainerMenu> extends GenericCustomScreen<T> {
	protected GenericCustomScreenUpgradeable(T screenContainer, Inventory inv, Component titleIn) {
		super(screenContainer, inv, titleIn);
		imageWidth = 212;
	}
}
