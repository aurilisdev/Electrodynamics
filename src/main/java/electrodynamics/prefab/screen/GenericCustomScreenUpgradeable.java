package electrodynamics.prefab.screen;

import electrodynamics.prefab.inventory.container.GenericContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public abstract class GenericCustomScreenUpgradeable<T extends GenericContainer> extends GenericCustomScreen<T> {
	protected GenericCustomScreenUpgradeable(T screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
		imageWidth = 212;
	}
}