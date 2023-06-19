package electrodynamics.prefab.screen;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.api.References;
import electrodynamics.api.screen.IScreenWrapper;
import electrodynamics.api.screen.component.ISlotTexture;
import electrodynamics.prefab.inventory.container.GenericContainer;
import electrodynamics.prefab.screen.component.ScreenComponentSlot;
import electrodynamics.prefab.screen.component.ScreenComponentSlot.IconType;
import electrodynamics.prefab.screen.component.ScreenComponentSlot.SlotType;
import electrodynamics.prefab.screen.component.editbox.ScreenComponentEditBox;
import electrodynamics.prefab.screen.component.utils.AbstractScreenComponent;
import electrodynamics.prefab.screen.component.utils.SlotTextureProvider;
import electrodynamics.prefab.utilities.RenderingUtils;
import net.minecraft.client.Minecraft;
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
	private Set<AbstractScreenComponent> components = new HashSet<>();
	private Set<ScreenComponentEditBox> editBoxes = new HashSet<>();
	protected int playerInvOffset = 0;

	public GenericScreen(T screenContainer, Inventory inv, Component titleIn) {
		super(screenContainer, inv, titleIn);
		initializeComponents();
	}

	protected void initializeComponents() {
		for (Slot slot : menu.slots) {
			addComponent(createScreenSlot(slot));
		}
	}

	protected ScreenComponentSlot createScreenSlot(Slot slot) {
		if (slot instanceof SlotTextureProvider provider) {
			ISlotTexture texture = provider.getSlotType();
			return new ScreenComponentSlot(slot, texture, provider.getIconType(), slot.x + texture.xOffset(), slot.y + texture.yOffset());
		}
		return new ScreenComponentSlot(slot, SlotType.NORMAL, IconType.NONE, slot.x + SlotType.NORMAL.xOffset(), slot.y + SlotType.NORMAL.yOffset());
	}
	
	@Override
	protected void containerTick() {
		super.containerTick();
		for(ScreenComponentEditBox box : editBoxes) {
			box.tick();
		}
	}
	
	@Override
	protected void init() {
		super.init();
		for(AbstractScreenComponent component : components) {
			addRenderableWidget(component);
		}
		if(editBoxes.size() > 0) {
			minecraft.keyboardHandler.setSendRepeatsToGui(true);
		}
	}

	@Override
	public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		renderTooltip(matrixStack, mouseX, mouseY);
	}

	@Override
	protected void renderLabels(PoseStack stack, int mouseX, int mouseY) {
		super.renderLabels(stack, mouseX, mouseY);
		int guiWidth = (int) getGuiWidth();
		int guiHeight = (int) getGuiHeight();
		int xAxis = mouseX - guiWidth;
		int yAxis = mouseY - guiHeight;
		for (AbstractScreenComponent component : components) {
			component.renderForeground(stack, xAxis, yAxis, guiWidth, guiHeight);
		}
	}

	@Override
	protected void renderBg(PoseStack stack, float partialTick, int mouseX, int mouseY) {
		RenderingUtils.bindTexture(defaultResource);
		int guiWidth = (int) getGuiWidth();
		int guiHeight = (int) getGuiHeight();
		blit(stack, guiWidth, guiHeight, 0, 248, imageWidth, 4);
		blit(stack, guiWidth, guiHeight + 4, 0, 0, imageWidth, imageHeight - 8);
		blit(stack, guiWidth, guiHeight + imageHeight - 4, 0, 252, imageWidth, 4);
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {

		for (AbstractScreenComponent component : components) {
			component.preOnMouseClick(mouseX, mouseY, button);
		}

		return super.mouseClicked(mouseX, mouseY, button);
	}

	@Override
	public void drawTexturedRect(PoseStack stack, int x, int y, int u, int v, int w, int h, int imgW, int imgH) {
		blit(stack, x, y, u, v, w, h, imgW, imgH);
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
		return Minecraft.getInstance().font;
	}
	
	@Override
	public void removed() {
		super.removed();
		if(editBoxes.size() > 0) {
			minecraft.keyboardHandler.setSendRepeatsToGui(false);
		}
	}
	
	@Override
	public void resize(Minecraft pMinecraft, int pWidth, int pHeight) {
		List<String> strings = new ArrayList<>();
		List<ScreenComponentEditBox> boxes = new ArrayList<>(editBoxes);
		for(ScreenComponentEditBox box : boxes) {
			strings.add(box.getValue());
		}
		super.resize(pMinecraft, pWidth, pHeight);
		for(int i = 0; i < boxes.size(); i++) {
			boxes.get(i).setValue(strings.get(i));
		}
	}

	public double getGuiWidth() {
		return (width - imageWidth) / 2.0;
	}

	public double getGuiHeight() {
		return (height - imageHeight) / 2.0;
	}

	public double getXAxis(double mouseX) {
		return mouseX - getGuiWidth();
	}

	public double getYAxis(double mouseY) {
		return mouseY - getGuiHeight();
	}

	public void addComponent(AbstractScreenComponent component) {
		components.add(component);
		component.setScreen(this);
	}

	public Set<AbstractScreenComponent> getComponents() {
		return components;
	}
	
	public void addEditBox(ScreenComponentEditBox box) {
		editBoxes.add(box);
		addComponent(box);
	}

}