package electrodynamics.client.screen.generic;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.text.ITextComponent;

public abstract class GenericContainerScreenUpgradeable<T extends Container> extends GenericContainerScreen<T> {
    public GenericContainerScreenUpgradeable(T screenContainer, PlayerInventory inv, ITextComponent titleIn) {
	super(screenContainer, inv, titleIn);
	xSize = 212;
    }
}
