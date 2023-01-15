package electrodynamics.prefab.screen.component;

import java.awt.Rectangle;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.api.References;
import electrodynamics.api.screen.IScreenWrapper;
import electrodynamics.api.screen.ITexture;
import electrodynamics.api.screen.component.ISlotTexture;
import electrodynamics.api.screen.component.TextSupplier;
import electrodynamics.prefab.utilities.RenderingUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.Slot;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScreenComponentSlot extends ScreenComponentGeneric {
	
	public static final ResourceLocation TEXTURE_SLOT = new ResourceLocation(References.ID + ":textures/screen/component/slot.png");
	public static final ResourceLocation TEXTURE_ICON = new ResourceLocation(References.ID + ":textures/screen/component/icon.png");
	
	private final ISlotTexture slotType;
	private final ITexture iconType;
	private TextSupplier tooltip;
	
	private final Slot slot;

	public ScreenComponentSlot(Slot slot, final ISlotTexture type, final ITexture iconType, final IScreenWrapper gui, final int x, final int y) {
		super(type, gui, x, y);
		this.slotType = type;
		this.iconType = iconType;
		this.slot = slot;
	}

	public ScreenComponentSlot(Slot slot, final IScreenWrapper gui, final int x, final int y) {
		this(slot, SlotType.NORMAL, IconType.NONE, gui, x, y);
	}

	public ScreenComponentSlot(Slot slot, final ISlotTexture type, final ITexture iconType, final IScreenWrapper gui, final int x, final int y, final TextSupplier tooltip) {
		this(slot, type, iconType, gui, x, y);
		this.tooltip = tooltip;
	}

	public void tooltip(TextSupplier tooltip) {
		this.tooltip = tooltip;
	}
	
	public ScreenComponentSlot setHoverText(Slot slot) {
		tooltip = () -> slot.getItem().isEmpty() ? Component.literal("") : slot.getItem().getHoverName();
		return this;
	}

	@Override
	public Rectangle getBounds(final int guiWidth, final int guiHeight) {
		return new Rectangle(guiWidth + xLocation, guiHeight + yLocation, slotType.textureWidth(), slotType.textureHeight());
	}

	@Override
	public void renderBackground(PoseStack stack, final int xAxis, final int yAxis, final int guiWidth, final int guiHeight) {
		if(!isVisible()) {
			return;
		}
		super.renderBackground(stack, xAxis, yAxis, guiWidth, guiHeight);
		if(iconType == IconType.NONE) {
			return;
		}
		RenderingUtils.bindTexture(iconType.getLocation());
		int slotXOffset = (slotType.imageWidth() - iconType.imageWidth()) / 2;
		int slotYOffset = (slotType.imageHeight() - iconType.imageHeight()) / 2;
		gui.drawTexturedRect(stack, guiWidth + xLocation + slotXOffset, guiHeight + yLocation + slotYOffset, iconType.textureU(), iconType.textureV(), iconType.textureWidth(), iconType.textureHeight(), iconType.imageWidth(), iconType.imageHeight());
	}

	@Override
	public void renderForeground(PoseStack stack, final int xAxis, final int yAxis) {
		if(!slot.isActive()) {
			return;
		}
		if (isPointInRegion(xLocation, yLocation, xAxis, yAxis, slotType.textureWidth(), slotType.textureHeight()) && tooltip != null && !tooltip.getText().getString().isEmpty()) {
			gui.displayTooltip(stack, tooltip.getText(), xAxis, yAxis);
		}
	}
	
	@Override
	public boolean isVisible() {
		return slot.isActive();
	}

	public static enum SlotType implements ISlotTexture {
		
		NORMAL(18, 18, 0, 0);
		
		private final int textureWidth;
		private final int textureHeight;
		private final int textureU;
		private final int textureV;
		private final int imageWidth;
		private final int imageHeight;
		private final ResourceLocation loc;
		
		private final int xOffset;
		private final int yOffset;
		
		private SlotType(int textureWidth, int textureHeight, int textureU, int textureV) {
			this(textureWidth, textureHeight, textureU, textureV, -1, -1);
		}
		
		private SlotType(int textureWidth, int textureHeight, int textureU, int textureV, int xOffset, int yOffset) {
			this.textureWidth = textureWidth;
			this.textureHeight = textureHeight;
			this.textureU = textureU;
			this.textureV = textureV;
			this.imageWidth = 256;
			this.imageHeight = 256;;
			this.loc = TEXTURE_SLOT;
			
			this.xOffset = xOffset;
			this.yOffset = yOffset;
		}

		@Override
		public ResourceLocation getLocation() {
			return loc;
		}

		@Override
		public int imageHeight() {
			return imageHeight;
		}

		@Override
		public int imageWidth() {
			return imageWidth;
		}

		@Override
		public int textureHeight() {
			return textureHeight;
		}

		@Override
		public int textureU() {
			return textureU;
		}

		@Override
		public int textureV() {
			return textureV;
		}

		@Override
		public int textureWidth() {
			return textureWidth;
		}
		
		public int xOffset() {
			return xOffset;
		}
		
		public int yOffset() {
			return yOffset;
		}
		
	
	}
	
	public static enum IconType implements ITexture {
		
		NONE(0, 0, 0, 0, 0 ,0, null),
		ENERGY(18, 18, 0, 0, 256, 256, TEXTURE_ICON),
		FLUID(18, 18, 18, 0, 256, 256, TEXTURE_ICON),
		GAS(18, 18, 36, 0, 256, 256, TEXTURE_ICON),
		UPGRADE(18, 18, 54, 0, 256, 256, TEXTURE_ICON),
		DRILL_HEAD(18, 18, 72, 0, 256, 256, TEXTURE_ICON),
		TRASH_CAN(18, 18, 90, 0, 256, 256, TEXTURE_ICON);
		
		private final int textureWidth;
		private final int textureHeight;
		private final int textureU;
		private final int textureV;
		private final int imageWidth;
		private final int imageHeight;
		private final ResourceLocation loc;
		
		private IconType(int textureWidth, int textureHeight, int textureU, int textureV, int imageWidth, int imageHeight, ResourceLocation loc) {
			this.textureWidth = textureWidth;
			this.textureHeight = textureHeight;
			this.textureU = textureU;
			this.textureV = textureV;
			this.imageWidth = imageWidth;
			this.imageHeight = imageHeight;
			this.loc = loc;
		}

		@Override
		public ResourceLocation getLocation() {
			return loc;
		}

		@Override
		public int imageHeight() {
			return imageHeight;
		}

		@Override
		public int imageWidth() {
			return imageWidth;
		}

		@Override
		public int textureHeight() {
			return textureHeight;
		}

		@Override
		public int textureU() {
			return textureU;
		}

		@Override
		public int textureV() {
			return textureV;
		}

		@Override
		public int textureWidth() {
			return textureWidth;
		}
		
	}

}