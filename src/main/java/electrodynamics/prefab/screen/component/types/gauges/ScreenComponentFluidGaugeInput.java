package electrodynamics.prefab.screen.component.types.gauges;

import electrodynamics.api.fluid.PropertyFluidTank;
import electrodynamics.api.screen.component.FluidTankSupplier;
import electrodynamics.common.packet.NetworkHandler;
import electrodynamics.common.packet.types.server.PacketUpdateCarriedItemServer;
import electrodynamics.prefab.inventory.container.GenericContainerBlockEntity;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.utilities.CapabilityUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;

/**
 * An extension of the ScreenComponentFluid class that allows for draining PropertyFluidTanks by clicking on them.
 * 
 * @author skip999
 *
 */
public class ScreenComponentFluidGaugeInput extends ScreenComponentFluidGauge {

	public ScreenComponentFluidGaugeInput(FluidTankSupplier fluidInfoHandler, int x, int y) {
		super(fluidInfoHandler, x, y);
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

		PropertyFluidTank tank = (PropertyFluidTank) fluidInfoHandler.getTank();

		if (tank == null) {
			return;
		}

		GenericScreen<?> screen = (GenericScreen<?>) gui;

		GenericTile owner = (GenericTile) ((GenericContainerBlockEntity<?>) screen.getMenu()).getHostFromIntArray();

		if (owner == null) {
			return;
		}

		ItemStack stack = screen.getMenu().getCarried();

		if (!CapabilityUtils.hasFluidItemCap(stack)) {
			return;
		}

		IFluidHandlerItem handler = CapabilityUtils.getFluidHandlerItem(stack);

		FluidStack tankFluid = tank.getFluid().copy();
		
		int taken = handler.fill(tankFluid, FluidAction.EXECUTE);
		
		if(taken <= 0) {
			return;
		}
		
		tank.drain(taken, FluidAction.EXECUTE);
		
		Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.BUCKET_FILL, 1.0F));
		
		stack = handler.getContainer();
		
		tank.updateServer();

		NetworkHandler.CHANNEL.sendToServer(new PacketUpdateCarriedItemServer(stack.copy(), ((GenericContainerBlockEntity<?>) screen.getMenu()).getHostFromIntArray().getBlockPos(), Minecraft.getInstance().player.getUUID()));

	}

}
