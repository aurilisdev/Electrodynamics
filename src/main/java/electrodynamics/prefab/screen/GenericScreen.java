package electrodynamics.prefab.screen;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.api.References;
import electrodynamics.api.screen.IScreenWrapper;
import electrodynamics.api.screen.component.ISlotTexture;
import electrodynamics.prefab.inventory.container.GenericContainer;
import electrodynamics.prefab.screen.component.AbstractScreenComponent;
import electrodynamics.prefab.screen.component.editbox.ScreenComponentEditBox;
import electrodynamics.prefab.screen.component.types.ScreenComponentSimpleLabel;
import electrodynamics.prefab.screen.component.types.ScreenComponentSlot;
import electrodynamics.prefab.screen.component.types.ScreenComponentSlot.IconType;
import electrodynamics.prefab.screen.component.types.ScreenComponentSlot.SlotType;
import electrodynamics.prefab.screen.component.utils.SlotTextureProvider;
import electrodynamics.prefab.utilities.RenderingUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GenericScreen<T extends GenericContainer> extends ContainerScreen<T> implements IScreenWrapper {

	protected ResourceLocation defaultResource = new ResourceLocation(References.ID + ":textures/screen/component/base.png");
	private Set<AbstractScreenComponent> components = new HashSet<>();
	public List<ScreenComponentSlot> slots = new ArrayList<>();
	private Set<ScreenComponentEditBox> editBoxes = new HashSet<>();
	protected int playerInvOffset = 0;

	// Ability to manipulate labels
	public ScreenComponentSimpleLabel guiTitle;
	public ScreenComponentSimpleLabel playerInvLabel;

	public GenericScreen(T container, PlayerInventory inv, ITextComponent title) {
		super(container, inv, title);
		initializeComponents();
	}

	protected void initializeComponents() {
		for (Slot slot : menu.slots) {
			ScreenComponentSlot component = createScreenSlot(slot);
			addComponent(component);
			slots.add(component);
		}
		addComponent(guiTitle = new ScreenComponentSimpleLabel(this.titleLabelX, this.titleLabelY, 10, 4210752, this.title));
		addComponent(playerInvLabel = new ScreenComponentSimpleLabel(this.inventoryLabelX, this.inventoryLabelY, 10, 4210752, this.inventory.getDisplayName()));
	}

	protected ScreenComponentSlot createScreenSlot(Slot slot) {
		if (slot instanceof SlotTextureProvider) {
			SlotTextureProvider provider = (SlotTextureProvider) slot;
			ISlotTexture texture = provider.getSlotType();
			return new ScreenComponentSlot(slot, texture, provider.getIconType(), slot.x + texture.xOffset(), slot.y + texture.yOffset());
		}
		return new ScreenComponentSlot(slot, SlotType.NORMAL, IconType.NONE, slot.x + SlotType.NORMAL.xOffset(), slot.y + SlotType.NORMAL.yOffset());
	}

	@Override
	public void tick() {
		super.tick();
		for (ScreenComponentEditBox box : editBoxes) {
			box.tick();
		}
	}

	@Override
	protected void init() {
		super.init();
		guiTitle.x = titleLabelX;
		guiTitle.y = titleLabelY;
		playerInvLabel.x = inventoryLabelX;
		playerInvLabel.y = inventoryLabelY;
		for (AbstractScreenComponent component : components) {
			addButton(component);
		}
	}

	@Override
	public void render(MatrixStack stack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(stack);
		super.render(stack, mouseX, mouseY, partialTicks);
		renderTooltip(stack, mouseX, mouseY);
	}

	@Override
	protected void renderLabels(MatrixStack stack, int mouseX, int mouseY) {
		// super.renderLabels(graphics, mouseX, mouseY);
		int guiWidth = (int) getGuiWidth();
		int guiHeight = (int) getGuiHeight();
		int xAxis = mouseX - guiWidth;
		int yAxis = mouseY - guiHeight;
		for (AbstractScreenComponent component : components) {
			component.renderForeground(stack, xAxis, yAxis, guiWidth, guiHeight);
		}
	}

	@Override
	protected void renderBg(MatrixStack stack, float partialTick, int mouseX, int mouseY) {
		// RenderingUtils.bindTexture(defaultResource);
		int guiWidth = (int) getGuiWidth();
		int guiHeight = (int) getGuiHeight();
		RenderingUtils.bindTexture(defaultResource);
		blit(stack, guiWidth, guiHeight, 0, 248, imageWidth, 4);
		blit(stack, guiWidth, guiHeight + 4, 0, 0, imageWidth, imageHeight - 8);
		blit(stack, guiWidth, guiHeight + imageHeight - 4, 0, 252, imageWidth, 4);
		RenderingUtils.resetShaderColor();
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {

		for (AbstractScreenComponent component : components) {
			component.preOnMouseClick(mouseX, mouseY, button);
		}

		return super.mouseClicked(mouseX, mouseY, button);
	}
	
	@Override
	public FontRenderer getFontRenderer() {
		return Minecraft.getInstance().font;
	}

	@Override
	public void resize(Minecraft pMinecraft, int pWidth, int pHeight) {
		List<String> strings = new ArrayList<>();
		List<ScreenComponentEditBox> boxes = new ArrayList<>(editBoxes);
		for (ScreenComponentEditBox box : boxes) {
			strings.add(box.getValue());
		}
		super.resize(pMinecraft, pWidth, pHeight);
		for (int i = 0; i < boxes.size(); i++) {
			boxes.get(i).setValue(strings.get(i));
		}
	}

	@Override
	public double getGuiWidth() {
		return (width - imageWidth) / 2.0;
	}

	@Override
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

	@Override
	public void displayTooltips(MatrixStack stack, List<? extends IReorderingProcessor> tooltips, int mouseX, int mouseY) {
		renderTooltip(stack, tooltips, mouseX, mouseY);
	}

	@Override
	public void displayTooltips(MatrixStack stack, List<? extends IReorderingProcessor> lines, int x, int y, FontRenderer font) {
		renderTooltip(stack, lines, x, y);
	}

}