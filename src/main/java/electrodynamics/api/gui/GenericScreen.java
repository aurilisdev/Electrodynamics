package electrodynamics.api.gui;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.api.References;
import electrodynamics.api.gui.component.GuiComponentSlot;
import electrodynamics.api.gui.component.IGuiComponent;
import electrodynamics.api.utilities.UtilitiesRendering;
import electrodynamics.prefab.inventory.container.GenericContainer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextProperties;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GenericScreen<T extends GenericContainer<? extends TileEntity>> extends ContainerScreen<T> implements IGuiWrapper {

    protected ResourceLocation defaultResource = new ResourceLocation(References.ID + ":textures/gui/component/base.png");
    protected Set<IGuiComponent> components = new HashSet<>();

    public GenericScreen(T screenContainer, PlayerInventory inv, ITextComponent titleIn) {
	super(screenContainer, inv, titleIn);
	initializeComponents();
    }

    protected void initializeComponents() {
	for (Slot slot : container.inventorySlots) {
	    components.add(createGuiSlot(slot));
	}
    }

    protected GuiComponentSlot createGuiSlot(Slot slot) {
	return new GuiComponentSlot(this, slot.xPos - 1, slot.yPos - 1);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
	this.renderBackground(matrixStack);
	super.render(matrixStack, mouseX, mouseY, partialTicks);
	renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack stack, int x, int y) {
	super.drawGuiContainerForegroundLayer(stack, x, y);
	int xAxis = x - (width - xSize) / 2;
	int yAxis = y - (height - ySize) / 2;
	for (IGuiComponent component : components) {
	    component.renderForeground(stack, xAxis, yAxis);
	}
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack stack, float partialTick, int x, int y) {
	UtilitiesRendering.bindTexture(defaultResource);
	int guiWidth = (width - xSize) / 2;
	int guiHeight = (height - ySize) / 2;
	blit(stack, guiWidth, guiHeight, 0, 248, xSize, 4);
	blit(stack, guiWidth, guiHeight + 4, 0, 0, xSize, ySize - 8);
	blit(stack, guiWidth, guiHeight + ySize - 4, 0, 252, xSize, 4);
	int xAxis = x - guiWidth;
	int yAxis = y - guiHeight;
	for (IGuiComponent component : components) {
	    component.renderBackground(stack, xAxis, yAxis, guiWidth, guiHeight);
	}
    }

    @Override
    public boolean mouseClicked(double x, double y, int button) {
	double xAxis = x - (width - xSize) / 2.0;
	double yAxis = y - (height - ySize) / 2.0;

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
	double xAxis = mouseX - (width - xSize) / 2.0;
	double yAxis = mouseY - (height - ySize) / 2.0;

	for (IGuiComponent component : components) {
	    component.mouseClicked(xAxis, yAxis, button);
	}
	return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
	boolean ret = super.mouseReleased(mouseX, mouseY, button);

	double xAxis = mouseX - (width - xSize) / 2.0;
	double yAxis = mouseY - (height - ySize) / 2.0;

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
	return (width - xSize) / 2;
    }

    public int getYPos() {
	return (height - ySize) / 2;
    }

    @Override
    public void drawTexturedRect(MatrixStack stack, int x, int y, int u, int v, int w, int h) {
	blit(stack, x, y, u, v, w, h);
    }

    @Override
    public void drawTexturedRectFromIcon(MatrixStack stack, int x, int y, TextureAtlasSprite icon, int w, int h) {
	blit(stack, x, y, (int) (icon.getMinU() * icon.getWidth()), (int) (icon.getMinV() * icon.getHeight()), w, h);
    }

    @Override
    public void displayTooltip(MatrixStack stack, ITextComponent text, int xAxis, int yAxis) {
	this.renderTooltip(stack, text, xAxis, yAxis);
    }

    @Override
    public void displayTooltips(MatrixStack stack, List<? extends ITextProperties> tooltips, int xAxis, int yAxis) {
	super.renderWrappedToolTip(stack, tooltips, xAxis, yAxis, font);
    }

    @Override
    public FontRenderer getFontRenderer() {
	return font;
    }

}