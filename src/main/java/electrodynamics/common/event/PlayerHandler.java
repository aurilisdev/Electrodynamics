package electrodynamics.common.event;

import electrodynamics.DeferredRegisters;
import electrodynamics.SoundRegister;
import electrodynamics.api.References;
import electrodynamics.api.item.ItemUtils;
import electrodynamics.common.packet.NetworkHandler;
import electrodynamics.common.packet.PacketPlayerInformation;
import electrodynamics.prefab.utilities.CapabilityUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.ScreenEvent.KeyboardKeyPressedEvent;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ForgeRegistries;

@EventBusSubscriber(modid = References.ID, bus = Bus.FORGE)
public class PlayerHandler {

	@SubscribeEvent
	public static void tick(PlayerTickEvent event) {
		if (event.side == LogicalSide.CLIENT && event.player.level.getLevelData().getDayTime() % 50 == 10) {
			NetworkHandler.CHANNEL.sendToServer(new PacketPlayerInformation());
		}
	}
	
	@SubscribeEvent
	public static void hydraulicFallDamage(LivingHurtEvent event) {
		DamageSource source = event.getSource();
		if(source.isFall()) {
			ItemStack hydraulicBoots = new ItemStack(DeferredRegisters.ITEM_HYDRAULICBOOTS.get());
			ItemStack playerBoots = event.getEntityLiving().getItemBySlot(EquipmentSlot.FEET);
			if(ItemUtils.testItems(hydraulicBoots.getItem(), playerBoots.getItem()) && event.getAmount() >= 2) {
				int fluidRequired = (int) Math.log10(event.getAmount());
				if(playerBoots.getCapability(CapabilityUtils.getFluidItemCap()).map(m -> m.getFluidInTank(0).getAmount() - fluidRequired >= 0).orElse(false)) {
					event.setAmount((float) Math.sqrt(event.getAmount()));
					playerBoots.getCapability(CapabilityUtils.getFluidItemCap()).ifPresent(h -> h.drain(fluidRequired, FluidAction.EXECUTE));
					event.getEntityLiving().getCommandSenderWorld().playSound(null, event.getEntityLiving().blockPosition(),
							SoundRegister.SOUND_HYDRAULICBOOTS.get(), SoundSource.PLAYERS, 1, 1);
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void ascendWithJetpack(KeyboardKeyPressedEvent.Post event) {
		/* TODO
		 * 1. Create custom key binding for jetpack fly key
		 * 2. Create keyboard pressed and released events to detect key press
		 * 3. Create packet to send to server for key press and release
		 * 
		 */
	}
}
