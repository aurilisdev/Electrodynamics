package electrodynamics.prefab.screen.component.types;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.api.gas.GasAction;
import electrodynamics.api.gas.GasStack;
import electrodynamics.common.tile.network.gas.TileGasPipeFilter;
import electrodynamics.prefab.inventory.container.GenericContainerBlockEntity;
import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.types.gauges.ScreenComponentGasGauge;
import electrodynamics.prefab.screen.component.types.gauges.ScreenComponentGasGauge.GasGaugeTextures;
import electrodynamics.prefab.utilities.CapabilityUtils;
import electrodynamics.prefab.utilities.RenderingUtils;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.ItemStack;

public class ScreenComponentGasFilter extends ScreenComponentGeneric {

	private final int index;

	public ScreenComponentGasFilter(int x, int y, int index) {
		super(GasGaugeTextures.BACKGROUND_DEFAULT, x, y);
		this.index = index;
	}

	@Override
	public void renderBackground(PoseStack stack, int xAxis, int yAxis, int guiWidth, int guiHeight) {
		super.renderBackground(stack, xAxis, yAxis, guiWidth, guiHeight);

		TileGasPipeFilter filter = (TileGasPipeFilter) ((GenericContainerBlockEntity<?>) ((GenericScreen<?>) gui).getMenu()).getHostFromIntArray();

		if (filter == null) {
			return;
		}

		Property<GasStack> property = filter.filteredGases[index];

		if (!property.get().isEmpty()) {

			ScreenComponentGasGauge.renderMercuryTexture(stack, guiWidth + xLocation + 1, guiHeight + yLocation + 1, 1);

		}

		GasGaugeTextures texture = GasGaugeTextures.LEVEL_DEFAULT;

		RenderingUtils.bindTexture(texture.getLocation());

		gui.drawTexturedRect(stack, guiWidth + xLocation, guiHeight + yLocation, texture.textureU(), texture.textureV(), texture.textureWidth(), texture.textureHeight(), texture.imageWidth(), texture.imageHeight());

	}

	@Override
	public void renderForeground(PoseStack stack, int xAxis, int yAxis, int guiWidth, int guiHeight) {
		if (!isPointInRegion(xLocation, yLocation, xAxis, yAxis, super.texture.textureWidth(), super.texture.textureHeight())) {
			return;
		}

		TileGasPipeFilter filter = (TileGasPipeFilter) ((GenericContainerBlockEntity<?>) ((GenericScreen<?>) gui).getMenu()).getHostFromIntArray();

		if (filter == null) {
			return;
		}

		List<FormattedCharSequence> tooltips = new ArrayList<>();

		Property<GasStack> property = filter.filteredGases[index];

		tooltips.add(property.get().getGas().getDescription().getVisualOrderText());

		gui.displayTooltips(stack, tooltips, xAxis, yAxis);
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if (isActiveAndVisible() && isValidClick(button) && isInClickRegion(mouseX, mouseY)) {

			onMouseClick(mouseX, mouseY);

			return true;
		}
		return false;
	}

	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		if (isValidClick(button)) {
			onMouseRelease(mouseX, mouseY);
			return true;
		}
		return false;
	}

	@Override
	public void onMouseClick(double mouseX, double mouseY) {

		GenericScreen<?> screen = (GenericScreen<?>) gui;

		TileGasPipeFilter filter = (TileGasPipeFilter) ((GenericContainerBlockEntity<?>) screen.getMenu()).getHostFromIntArray();

		if (filter == null) {
			return;
		}

		Property<GasStack> property = filter.filteredGases[index];

		ItemStack holding = screen.getMenu().getCarried();

		if (holding.isEmpty()) {

			if (!Screen.hasShiftDown()) {
				return;
			}
			property.set(GasStack.EMPTY);
			property.updateServer();

		}

		GasStack taken = CapabilityUtils.drainGasItem(holding, Double.MAX_VALUE, GasAction.SIMULATE);

		if (taken.isEmpty()) {
			return;
		}

		property.set(taken);
		property.updateServer();

	}

}
