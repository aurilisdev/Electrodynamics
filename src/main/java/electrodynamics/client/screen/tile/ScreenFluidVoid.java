package electrodynamics.client.screen.tile;

import electrodynamics.common.inventory.container.tile.ContainerFluidVoid;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import voltaic.prefab.screen.GenericScreen;

public class ScreenFluidVoid extends GenericScreen<ContainerFluidVoid> {

	public ScreenFluidVoid(ContainerFluidVoid container, PlayerInventory inv, ITextComponent titleIn) {
		super(container, inv, titleIn);
	}

}
