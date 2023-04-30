package electrodynamics.prefab.screen.component;

import java.util.function.Supplier;

import electrodynamics.api.gas.GasAction;
import electrodynamics.api.gas.GasStack;
import electrodynamics.api.gas.GasTank;
import electrodynamics.api.gas.PropertyGasTank;
import electrodynamics.api.screen.IScreenWrapper;
import electrodynamics.common.packet.NetworkHandler;
import electrodynamics.common.packet.types.PacketUpdateCarriedItemServer;
import electrodynamics.prefab.inventory.container.GenericContainerBlockEntity;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.utilities.CapabilityUtils;
import electrodynamics.registers.ElectrodynamicsSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.world.item.ItemStack;

public class ScreenComponentGasInput extends ScreenComponentGasGauge {

	public ScreenComponentGasInput(Supplier<GasTank> gasStack, IScreenWrapper gui, int x, int y) {
		super(gasStack, gui, x, y);
	}
	
	@Override
	public void mouseClicked(double xAxis, double yAxis, int button) {
		super.mouseClicked(xAxis, yAxis, button);
		
		if(!isPointInRegion(xLocation, yLocation, xAxis, yAxis, texture.textureWidth(), texture.textureHeight())) {
			return;
		}
		
		
		PropertyGasTank tank = (PropertyGasTank) gasTank.get();

		if (tank == null) {
			return;
		}

		GenericScreen<?> screen = (GenericScreen<?>) gui;
		
		GenericTile owner = (GenericTile) ((GenericContainerBlockEntity<?>) screen.getMenu()).getHostFromIntArray();
		
		if(owner == null) {
			return;
		}
		
		ItemStack stack = screen.getMenu().getCarried();

		if (!CapabilityUtils.hasGasItemCap(stack)) {
			return;
		}
		
		GasStack tankGas = tank.getGas();
		
		double amtTaken = CapabilityUtils.fillGasItem(stack, tankGas, GasAction.SIMULATE);
		
		GasStack taken = new GasStack(tankGas.getGas(), amtTaken, tankGas.getTemperature(), tankGas.getPressure());
		if (amtTaken > 0) {
			
			CapabilityUtils.fillGasItem(stack, tankGas, GasAction.EXECUTE);
			tank.drain(taken, GasAction.EXECUTE);
			Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(ElectrodynamicsSounds.SOUND_PRESSURERELEASE.get(), 1.0F));
			tank.updateServer();
			
		}

		NetworkHandler.CHANNEL.sendToServer(new PacketUpdateCarriedItemServer(stack.copy(), ((GenericContainerBlockEntity<?>) screen.getMenu()).getHostFromIntArray().getBlockPos(), Minecraft.getInstance().player.getUUID()));

	}

}
