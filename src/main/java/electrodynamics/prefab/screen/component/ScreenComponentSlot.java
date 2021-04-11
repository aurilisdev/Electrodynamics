package electrodynamics.prefab.screen.component;

import java.awt.Rectangle;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.api.References;
import electrodynamics.api.screen.IGuiWrapper;
import electrodynamics.api.screen.component.TextSupplier;
import electrodynamics.prefab.utilities.UtilitiesRendering;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScreenComponentSlot extends ScreenComponent {
    private final EnumSlotType type;
    private TextSupplier tooltip;

    public ScreenComponentSlot(final EnumSlotType type, final IGuiWrapper gui, final int x, final int y) {
	super(new ResourceLocation(References.ID + ":textures/gui/component/slot.png"), gui, x, y);
	this.type = type;
    }

    public ScreenComponentSlot(final IGuiWrapper gui, final int x, final int y) {
	this(EnumSlotType.NORMAL, gui, x, y);
    }

    public ScreenComponentSlot(final IGuiWrapper gui, final int x, final int y, Slot slot) {
	this(EnumSlotType.NORMAL, gui, x, y, () -> slot.getStack().isEmpty() ? new StringTextComponent("") : slot.getStack().getDisplayName());
    }

    public ScreenComponentSlot(final EnumSlotType type, final IGuiWrapper gui, final int x, final int y, Slot slot) {
	this(type, gui, x, y, () -> slot.getStack().getDisplayName());
    }

    public ScreenComponentSlot(final EnumSlotType type, final IGuiWrapper gui, final int x, final int y, final TextSupplier tooltip) {
	this(type, gui, x, y);
	this.tooltip = tooltip;
    }

    @Override
    public Rectangle getBounds(final int guiWidth, final int guiHeight) {
	return new Rectangle(guiWidth + xLocation, guiHeight + yLocation, type.getWidth(), type.getHeight());
    }

    @Override
    public void renderBackground(MatrixStack stack, final int xAxis, final int yAxis, final int guiWidth, final int guiHeight) {
	UtilitiesRendering.bindTexture(resource);
	gui.drawTexturedRect(stack, guiWidth + xLocation, guiHeight + yLocation, type.getTextureX(), type.getTextureY(), type.getWidth(),
		type.getHeight());
    }

    @Override
    public void renderForeground(MatrixStack stack, final int xAxis, final int yAxis) {
	if (isPointInRegion(xLocation, yLocation, xAxis, yAxis, type.width, type.height) && tooltip != null
		&& !tooltip.getText().getString().isEmpty()) {
	    gui.displayTooltip(stack, tooltip.getText(), xAxis, yAxis);
	}
    }

    public enum EnumSlotType {
	NORMAL(18, 18, 0, 0), BATTERY(18, 18, 18, 0), LIQUID(18, 18, 36, 0), GAS(18, 18, 54, 0), SPEED(18, 18, 72, 0);

	private final int width;
	private final int height;
	private final int textureX;
	private final int textureY;

	EnumSlotType(int width, int height, int textureX, int textureY) {
	    this.width = width;
	    this.height = height;
	    this.textureX = textureX;
	    this.textureY = textureY;
	}

	public int getWidth() {
	    return width;
	}

	public int getHeight() {
	    return height;
	}

	public int getTextureX() {
	    return textureX;
	}

	public int getTextureY() {
	    return textureY;
	}
    }

}