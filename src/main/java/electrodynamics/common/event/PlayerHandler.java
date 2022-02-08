package electrodynamics.common.event;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.DeferredRegisters;
import electrodynamics.SoundRegister;
import electrodynamics.api.References;
import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.api.item.ItemUtils;
import electrodynamics.common.packet.NetworkHandler;
import electrodynamics.common.packet.types.PacketPlayerInformation;
import electrodynamics.prefab.utilities.CapabilityUtils;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = References.ID, bus = Bus.FORGE)
public class PlayerHandler {

	private static final float LETHAL_DAMAGE_AMOUNT = 18.0f;

	@SubscribeEvent
	public static void tick(PlayerTickEvent event) {
		if (event.side == LogicalSide.CLIENT && event.player.level.getLevelData().getDayTime() % 50 == 10) {
			NetworkHandler.CHANNEL.sendToServer(new PacketPlayerInformation());
		}
	}

	@SubscribeEvent
	public static void takeDamageWithArmor(LivingHurtEvent event) {
		ItemStack[] armorPiecesArray = new ItemStack[] { new ItemStack(DeferredRegisters.COMPOSITE_HELMET.get()), new ItemStack(DeferredRegisters.COMPOSITE_CHESTPLATE.get()), new ItemStack(DeferredRegisters.COMPOSITE_LEGGINGS.get()), new ItemStack(DeferredRegisters.COMPOSITE_BOOTS.get()) };

		List<ItemStack> armorPieces = new ArrayList<>();
		event.getEntityLiving().getArmorSlots().forEach(armorPieces::add);

		if (ItemStack.isSameIgnoreDurability(armorPieces.get(0), armorPiecesArray[3]) && ItemStack.isSameIgnoreDurability(armorPieces.get(1), armorPiecesArray[2]) && ItemStack.isSameIgnoreDurability(armorPieces.get(2), armorPiecesArray[1]) && ItemStack.isSameIgnoreDurability(armorPieces.get(3), armorPiecesArray[0])) {
			ItemStack stack = armorPieces.get(2);
			stack.getCapability(ElectrodynamicsCapabilities.INTEGER_STORAGE_CAPABILITY).ifPresent(h -> {
				if (event.getAmount() >= LETHAL_DAMAGE_AMOUNT && h.getServerInt(0) > 0) {

					event.setAmount((float) Math.sqrt(event.getAmount()));
					h.setServerInt(0, h.getServerInt(0) - 1);
					event.getEntityLiving().getCommandSenderWorld().playSound(null, event.getEntityLiving().blockPosition(), SoundRegister.SOUND_CERAMICPLATEBREAKING.get(), SoundSource.PLAYERS, 1, 1);
				}
			});

		}
	}

	@SubscribeEvent
	public static void hydraulicFallDamage(LivingHurtEvent event) {
		DamageSource source = event.getSource();
		if (source.isFall()) {
			ItemStack hydraulicBoots = new ItemStack(DeferredRegisters.ITEM_HYDRAULICBOOTS.get());
			ItemStack playerBoots = event.getEntityLiving().getItemBySlot(EquipmentSlot.FEET);
			if (ItemUtils.testItems(hydraulicBoots.getItem(), playerBoots.getItem()) && event.getAmount() >= 2) {
				int fluidRequired = (int) Math.log10(event.getAmount());
				if (playerBoots.getCapability(CapabilityUtils.getFluidItemCap()).map(m -> m.getFluidInTank(0).getAmount() - fluidRequired >= 0).orElse(false)) {
					event.setAmount((float) Math.sqrt(event.getAmount()));
					playerBoots.getCapability(CapabilityUtils.getFluidItemCap()).ifPresent(h -> h.drain(fluidRequired, FluidAction.EXECUTE));
					event.getEntityLiving().getCommandSenderWorld().playSound(null, event.getEntityLiving().blockPosition(), SoundRegister.SOUND_HYDRAULICBOOTS.get(), SoundSource.PLAYERS, 1, 1);
				}
			}
		}
	}
}
