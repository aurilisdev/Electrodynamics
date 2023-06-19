package electrodynamics.prefab.screen.component;

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
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

/**
 * An extension of the ScreenComponentFluid class that allows for draining
 * PropertyFluidTanks by clicking on them.
 * 
 * @author skip999
 *
 */
public class ScreenComponentFluidGaugeInput extends ScreenComponentFluidGauge {

	public ScreenComponentFluidGaugeInput(FluidTankSupplier fluidInfoHandler, int x, int y) {
		super(fluidInfoHandler, x, y);
	}

	@Override
	public void onMouseClick(double mouseX, double mouseY) {
		
		PropertyFluidTank tank = (PropertyFluidTank) fluidInfoHandler.getTank();

		if (tank == null) {
			return;
		}

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
		
		FluidStack tankFluid = tank.getFluid();
		
		int amtTaken = CapabilityUtils.fillFluidItem(stack, tankFluid, FluidAction.SIMULATE);
		
		FluidStack taken = new FluidStack(tankFluid.getFluid(), amtTaken);
		
		if (isBucket && amtTaken == 1000 && (tankFluid.getFluid().isSame(Fluids.WATER) || tankFluid.getFluid().isSame(Fluids.LAVA))) {
			
			stack = new ItemStack(taken.getFluid().getBucket(), 1);
			tank.drain(taken, FluidAction.EXECUTE);
			Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.BUCKET_FILL, 1.0F));
			tank.updateServer();
		
		} else if (amtTaken > 0 && !isBucket) {
			
			CapabilityUtils.fillFluidItem(stack, tankFluid, FluidAction.EXECUTE);
			tank.drain(taken, FluidAction.EXECUTE);
			Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.BUCKET_FILL, 1.0F));
			tank.updateServer();
			
		}

		NetworkHandler.CHANNEL.sendToServer(new PacketUpdateCarriedItemServer(stack.copy(), ((GenericContainerBlockEntity<?>) screen.getMenu()).getHostFromIntArray().getBlockPos(), Minecraft.getInstance().player.getUUID()));
		
	}

}
