package electrodynamics.prefab.screen.component;

import java.util.function.Supplier;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.common.packet.NetworkHandler;
import electrodynamics.common.packet.types.server.PacketSendUpdatePropertiesServer;
import electrodynamics.common.packet.types.server.PacketUpdateCarriedItemServer;
import electrodynamics.prefab.inventory.container.GenericContainerBlockEntity;
import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.ScreenComponentSlot.IconType;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.utilities.CapabilityUtils;
import electrodynamics.prefab.utilities.RenderingUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

public class ScreenComponentCondensedFluid extends ScreenComponentGeneric {

	private final Supplier<Property<FluidStack>> fluidPropertySupplier;

	public ScreenComponentCondensedFluid(GenericScreen<?> gui, Supplier<Property<FluidStack>> fluidStackSupplier, int x, int y) {
		super(IconType.FLUID_DARK, gui, x, y);
		this.fluidPropertySupplier = fluidStackSupplier;
	}

	@Override
	public void renderBackground(PoseStack stack, int xAxis, int yAxis, int guiWidth, int guiHeight) {

		super.renderBackground(stack, xAxis, yAxis, guiWidth, guiHeight);

		Property<FluidStack> fluidProperty = fluidPropertySupplier.get();

		if (fluidProperty == null || fluidProperty.get().isEmpty()) {
			return;
		}

		IconType fluidFull = IconType.FLUID_BLUE;

		RenderingUtils.bindTexture(fluidFull.getLocation());

		gui.drawTexturedRect(stack, guiWidth + xLocation + 1, guiHeight + yLocation + 1, fluidFull.textureU(), fluidFull.textureV(), fluidFull.textureWidth(), fluidFull.textureHeight(), fluidFull.imageWidth(), fluidFull.imageHeight());
	}

	@Override
	public void mouseClicked(double xAxis, double yAxis, int button) {
		super.mouseClicked(xAxis, yAxis, button);

		if (!isPointInRegion(xLocation, yLocation, xAxis, yAxis, texture.textureWidth(), texture.textureHeight())) {
			return;
		}

		Property<FluidStack> fluidProperty = fluidPropertySupplier.get();

		if (fluidProperty == null || fluidProperty.get().isEmpty()) {
			return;
		}

		FluidStack fluidStack = fluidProperty.get();
		
		GenericScreen<?> screen = (GenericScreen<?>) gui;
		
		GenericTile owner = (GenericTile) ((GenericContainerBlockEntity<?>) screen.getMenu()).getHostFromIntArray();
		
		if(owner == null) {
			return;
		}
		
		ItemStack stack = screen.getMenu().getCarried();

		if (!CapabilityUtils.hasFluidItemCap(stack)) {
			return;
		}

		boolean isBucket = stack.getItem() instanceof BucketItem;

		int amtTaken = CapabilityUtils.fillFluidItem(stack, fluidStack, FluidAction.SIMULATE);

		FluidStack taken = new FluidStack(fluidStack.getFluid(), amtTaken);

		if (isBucket && amtTaken == 1000 && (fluidStack.getFluid().isSame(Fluids.WATER) || fluidStack.getFluid().isSame(Fluids.LAVA))) {

			stack = new ItemStack(taken.getFluid().getBucket(), 1);
			fluidStack.shrink(amtTaken);
			Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.BUCKET_FILL, 1.0F));
			NetworkHandler.CHANNEL.sendToServer(new PacketSendUpdatePropertiesServer(fluidProperty.getPropertyManager().getProperties().indexOf(fluidProperty), fluidProperty, owner.getBlockPos()));

		} else if (amtTaken > 0 && !isBucket) {

			CapabilityUtils.fillFluidItem(stack, fluidStack, FluidAction.EXECUTE);
			fluidStack.shrink(amtTaken);
			Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.BUCKET_FILL, 1.0F));
			NetworkHandler.CHANNEL.sendToServer(new PacketSendUpdatePropertiesServer(fluidProperty.getPropertyManager().getProperties().indexOf(fluidProperty), fluidProperty, owner.getBlockPos()));

		}

		NetworkHandler.CHANNEL.sendToServer(new PacketUpdateCarriedItemServer(stack.copy(), owner.getBlockPos(), Minecraft.getInstance().player.getUUID()));

	}

}
