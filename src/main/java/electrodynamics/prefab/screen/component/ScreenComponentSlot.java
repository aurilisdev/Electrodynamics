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
		
		NORMAL(18, 18, 0, 0, 18, 18, "slot_regular");
		
		private final int textureWidth;
		private final int textureHeight;
		private final int textureU;
		private final int textureV;
		private final int imageWidth;
		private final int imageHeight;
		private final ResourceLocation loc;
		
		private final int xOffset;
		private final int yOffset;
		
		private SlotType(int textureWidth, int textureHeight, int textureU, int textureV, int imageWidth, int imageHeight, String name) {
			this(textureWidth, textureHeight, textureU, textureV, imageWidth, imageHeight, name, -1, -1);
		}
		
		private SlotType(int textureWidth, int textureHeight, int textureU, int textureV, int imageWidth, int imageHeight, String name, int xOffset, int yOffset) {
			this.textureWidth = textureWidth;
			this.textureHeight = textureHeight;
			this.textureU = textureU;
			this.textureV = textureV;
			this.imageWidth = imageWidth;
			this.imageHeight = imageHeight;
			this.loc = new ResourceLocation(References.ID + ":textures/screen/component/slot/" + name + ".png");
			
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
		ENERGY_DARK(10, 10, 0, 0, 10, 10, "electricity_dark"),
		FLUID_DARK(14, 14, 0, 0, 14, 14, "fluid_dark"),
		GAS_DARK(16, 16, 0, 0, 16, 16, "gas_dark"),
		UPGRADE_DARK(12, 12, 0, 0, 12, 12, "upgrade_dark"),
		DRILL_HEAD_DARK(12, 12, 0, 0, 12, 12, "drill_head_dark"),
		TRASH_CAN_DARK(10, 10, 0, 0, 10, 10, "trash_can_dark"),
		
		ENERGY_GREEN(14, 14, 0, 0, 14, 14, "electricity_green"),
		ENCHANTMENT(16, 16, 0, 0, 16, 16, "enchantment"),
		FLUID_BLUE(16, 16, 0, 0, 16, 16, "fluid_blue"),
		MINING_LOCATION(18, 18, 0, 0, 18, 18, "mining_location"), 
		QUARRY_COMPONENTS(18, 18, 0, 0, 18, 18, "quarry_components"),
		TEMPERATURE(14, 14, 0, 0, 14, 14, "temperature");
		
		private final int textureWidth;
		private final int textureHeight;
		private final int textureU;
		private final int textureV;
		private final int imageWidth;
		private final int imageHeight;
		private final ResourceLocation loc;
		
		private IconType(int textureWidth, int textureHeight, int textureU, int textureV, int imageWidth, int imageHeight, String name) {
			this.textureWidth = textureWidth;
			this.textureHeight = textureHeight;
			this.textureU = textureU;
			this.textureV = textureV;
			this.imageWidth = imageWidth;
			this.imageHeight = imageHeight;
			this.loc = new ResourceLocation(References.ID + ":textures/screen/component/icon/" + name + ".png");
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