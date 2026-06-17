package electrodynamics.client.render.itemdecorators;

import electrodynamics.common.item.gear.armor.types.ItemCombatArmor;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.IItemDecorator;
import voltaic.api.gas.GasStack;
import voltaic.api.screen.ITexture;
import voltaic.prefab.utilities.RenderingUtils;
import voltaic.registers.VoltaicDataComponentTypes;

public class ItemDecoratorCombatHelmet implements IItemDecorator {
    @Override
    public boolean render(GuiGraphics guiGraphics, Font font, ItemStack stack, int x, int y) {

	GasStack gas = stack.getOrDefault(VoltaicDataComponentTypes.GAS_STACK.get(), GasStack.EMPTY);

	if (gas.isEmpty() || gas.getAmount() == ItemCombatArmor.HELMET_CAPACITY) {
	    return false;
	}

	int blackBoxHeight = 1;

	if (!stack.isBarVisible()) {
	    y += 1;
	    blackBoxHeight = 2;
	}

	guiGraphics.setColor(0, 0, 0, 255);
	guiGraphics.blit(ITexture.Textures.WHITE.getLocation(), x + 2, y + 12, 199, 0, 0, 13, blackBoxHeight, 16, 16);
	guiGraphics.setColor(0, 255, 0, 255);

	int width = (int) (13 * ((double) gas.getAmount() / (double) ItemCombatArmor.HELMET_CAPACITY));
	guiGraphics.blit(ITexture.Textures.WHITE.getLocation(), x + 2, y + 12, 199, 0, 0, width, 1, 16, 16);
	RenderingUtils.resetShaderColor();

	return false;
    }
}
