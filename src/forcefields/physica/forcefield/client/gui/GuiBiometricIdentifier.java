package physica.forcefield.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;
import physica.api.core.IBaseUtilities;
import physica.forcefield.ForcefieldReferences;
import physica.forcefield.common.inventory.ContainerBiometricIndentifier;
import physica.forcefield.common.item.Permission;
import physica.forcefield.common.tile.TileBiometricIdentifier;
import physica.library.client.gui.GuiContainerBase;

@SideOnly(Side.CLIENT)
public class GuiBiometricIdentifier extends GuiContainerBase<TileBiometricIdentifier> implements IBaseUtilities {

	public GuiBiometricIdentifier(EntityPlayer player, TileBiometricIdentifier host) {
		super(new ContainerBiometricIndentifier(player, host), host);
		ySize += 81;
	}

	@Override
	public void initGui()
	{
		super.initGui();
		addToolTip(8, 64, (int) ("Rights".length() * 4.8D), 12, "Assign rights to the card under this text");
		addButton(new GuiButton(Permission.BLOCK_ACCESS.id + 1, width / 2 - 40, height / 2 - 100, "Access".length() * 8, 20, "Access"));
		addButton(new GuiButton(Permission.BLOCK_ALTER.id + 1, width / 2 - 40 + "Access".length() * 8 + 10, height / 2 - 100, "Alter".length() * 8, 20, "Alter"));
		addButton(new GuiButton(Permission.BYPASS_CONFISCATION.id + 1, width / 2 - 40, height / 2 - 100 + 25, "Bypass Confiscation".length() * 6, 20, "Bypass Confiscation"));
		addButton(new GuiButton(Permission.BYPASS_INTERDICTION_MATRIX.id + 1, width / 2 - 40, height / 2 - 100 + 50, "Bypass Matrix".length() * 6, 20, "Bypass Matrix"));
		addButton(new GuiButton(Permission.SECURITY_CENTER_CONFIGURE.id + 1, width / 2 - 40, height / 2 - 100 + 50 + 25, "Configure Identifier".length() * 6, 20, "Configure Identifier"));
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		drawString("Rights", 8, 64);
		drawStringCentered(StatCollector.translateToLocal("tile." + ForcefieldReferences.PREFIX + "biometricIdentifier.gui"), xSize / 2, 5);
	}

	@Override
	protected void actionPerformed(GuiButton button)
	{
		super.actionPerformed(button);
		host.actionPerformed(Permission.getPermission(button.id - 1), Side.CLIENT);
	}
}
