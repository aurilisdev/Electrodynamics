package electrodynamics.client.screen.item;

import electrodynamics.common.inventory.container.item.ContainerElectricDrill;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import voltaic.prefab.screen.GenericScreen;

public class ScreenElectricDrill extends GenericScreen<ContainerElectricDrill> {

	public ScreenElectricDrill(ContainerElectricDrill screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
	}

}
