package electrodynamics.prefab.screen;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.text.ITextComponent;

public abstract class GenericCustomScreenUpgradeable<T extends Container> extends GenericCustomScreen<T> {
    protected GenericCustomScreenUpgradeable(T screenContainer, PlayerInventory inv, ITextComponent titleIn) {
	super(screenContainer, inv, titleIn);
	xSize = 212;
    }
}
