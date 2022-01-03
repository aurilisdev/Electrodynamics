package electrodynamics.client.screen.tile;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.ElectricUnit;
import electrodynamics.api.electricity.generator.IElectricGenerator;
import electrodynamics.common.inventory.container.tile.ContainerHydroelectricGenerator;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.ScreenComponentElectricInfo;
import electrodynamics.prefab.screen.component.ScreenComponentInfo;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScreenHydroelectricGenerator extends GenericScreen<ContainerHydroelectricGenerator> {

	public ScreenHydroelectricGenerator(ContainerHydroelectricGenerator container, Inventory playerInventory, Component title) {
		super(container, playerInventory, title);
		components.add(new ScreenComponentElectricInfo(this, -ScreenComponentInfo.SIZE + 1, 2));
	}

	@Override
	protected void renderLabels(PoseStack matrixStack, int mouseX, int mouseY) {
		super.renderLabels(matrixStack, mouseX, mouseY);
		if (menu.getUnsafeHost() instanceof IElectricGenerator gen) {
			TransferPack transfer = gen.getProduced();
			font.draw(matrixStack,
					new TranslatableComponent("gui.machine.current",
							ChatFormatter.getElectricDisplayShort(transfer.getAmps(), ElectricUnit.AMPERE)),
					(float) inventoryLabelX + 60, (float) inventoryLabelY - 48, 4210752);
			font.draw(matrixStack,
					new TranslatableComponent("gui.machine.output",
							ChatFormatter.getElectricDisplayShort(transfer.getWatts(), ElectricUnit.WATT)),
					(float) inventoryLabelX + 60, (float) inventoryLabelY - 35, 4210752);
			font.draw(matrixStack,
					new TranslatableComponent("gui.machine.voltage",
							ChatFormatter.getElectricDisplayShort(transfer.getVoltage(), ElectricUnit.VOLTAGE)),
					(float) inventoryLabelX + 60, (float) inventoryLabelY - 22, 4210752);
		}
	}
}