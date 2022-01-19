package electrodynamics.prefab.screen;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.api.References;
import electrodynamics.api.screen.IScreenWrapper;
import electrodynamics.api.screen.component.IGuiComponent;
import electrodynamics.prefab.inventory.container.GenericContainer;
import electrodynamics.prefab.inventory.container.slot.item.SlotGeneric;
import electrodynamics.prefab.screen.component.ScreenComponentSlot;
import electrodynamics.prefab.screen.component.ScreenComponentSlot.EnumSlotType;
import electrodynamics.prefab.utilities.RenderingUtils;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GenericScreen<T extends GenericContainer> extends AbstractContainerScreen<T> implements IScreenWrapper {

	protected ResourceLocation defaultResource = new ResourceLocation(References.ID + ":textures/screen/component/base.png");
	protected Set<IGuiComponent> components = new HashSet<>();
	protected int playerInvOffset = 0;

	public GenericScreen(T screenContainer, Inventory inv, Component titleIn) {
		super(screenContainer, inv, titleIn);
		initializeComponents();
	}

	protected void initializeComponents() {
		for (Slot slot : menu.slots) {
			components.add(createScreenSlot(slot));
		}
	}

	protected ScreenComponentSlot createScreenSlot(Slot slot) {
		// this is a lot cleaner IMO and more straight-forward
		if (slot instanceof SlotGeneric generic) {
			return new ScreenComponentSlot(generic.getSlotType(), this, slot.x - 1, slot.y - 1);
		}
		return new ScreenComponentSlot(EnumSlotType.NORMAL, this, slot.x - 1, slot.y - 1);
	}

	@Override
	public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		renderTooltip(matrixStack, mouseX, mouseY);
	}

	@Override
	protected void renderLabels(PoseStack stack, int x, int y) {
		super.renderLabels(stack, x, y);
		int xAxis = x - (width - imageWidth) / 2;
		int yAxis = y - (height - imageHeight) / 2;
		for (IGuiComponent component : components) {
			component.renderForeground(stack, xAxis, yAxis);
		}
	}

	@Override
	protected void renderBg(PoseStack stack, float partialTick, int x, int y) {
		RenderingUtils.bindTexture(defaultResource);
		int guiWidth = (width - imageWidth) / 2;
		int guiHeight = (height - imageHeight) / 2;
		blit(stack, guiWidth, guiHeight, 0, 248, imageWidth, 4);
		blit(stack, guiWidth, guiHeight + 4, 0, 0, imageWidth, imageHeight - 8);
		blit(stack, guiWidth, guiHeight + imageHeight - 4, 0, 252, imageWidth, 4);
		int xAxis = x - guiWidth;
		int yAxis = y - guiHeight;
		for (IGuiComponent component : components) {
			component.renderBackground(stack, xAxis, yAxis, guiWidth, guiHeight);
		}
	}

	@Override
	public boolean mouseClicked(double x, double y, int button) {
		double xAxis = x - (width - imageWidth) / 2.0;
		double yAxis = y - (height - imageHeight) / 2.0;

		for (IGuiComponent component : components) {
			component.preMouseClicked(xAxis, yAxis, button);
		}

		boolean ret = super.mouseClicked(x, y, button);

		for (IGuiComponent component : components) {
			component.mouseClicked(xAxis, yAxis, button);
		}
		return ret;
	}

	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
		double xAxis = mouseX - (width - imageWidth) / 2.0;
		double yAxis = mouseY - (height - imageHeight) / 2.0;

		for (IGuiComponent component : components) {
			component.mouseClicked(xAxis, yAxis, button);
		}
		return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
	}

	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		boolean ret = super.mouseReleased(mouseX, mouseY, button);

		double xAxis = mouseX - (width - imageWidth) / 2.0;
		double yAxis = mouseY - (height - imageHeight) / 2.0;

		for (IGuiComponent component : components) {
			component.mouseReleased(xAxis, yAxis, button);
		}
		return ret;
	}

	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
		for (IGuiComponent component : components) {
			component.mouseWheel(mouseX, mouseY, delta);
		}
		return super.mouseScrolled(mouseX, mouseY, delta);
	}

	public int getXPos() {
		return (width - imageWidth) / 2;
	}

	public int getYPos() {
		return (height - imageHeight) / 2;
	}

	@Override
	public void drawTexturedRect(PoseStack stack, int x, int y, int u, int v, int w, int h) {
		blit(stack, x, y, u, v, w, h);
	}

	@Override
	public void drawTexturedRectFromIcon(PoseStack stack, int x, int y, TextureAtlasSprite icon, int w, int h) {
		blit(stack, x, y, (int) (icon.getU0() * icon.getWidth()), (int) (icon.getV0() * icon.getHeight()), w, h);
	}

	@Override
	public void displayTooltip(PoseStack stack, Component text, int xAxis, int yAxis) {
		this.renderTooltip(stack, text, xAxis, yAxis);
	}

	@Override
	public void displayTooltips(PoseStack stack, List<? extends FormattedCharSequence> tooltips, int xAxis, int yAxis) {
		super.renderTooltip(stack, tooltips, xAxis, yAxis, font);
	}

	@Override
	public Font getFontRenderer() {
		return font;
	}

}