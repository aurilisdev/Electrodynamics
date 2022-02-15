package electrodynamics.common.event;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.DeferredRegisters;
import electrodynamics.SoundRegister;
import electrodynamics.api.References;
import electrodynamics.api.item.ItemUtils;
import electrodynamics.common.packet.NetworkHandler;
import electrodynamics.common.packet.types.PacketPlayerInformation;
import electrodynamics.prefab.utilities.CapabilityUtils;
import electrodynamics.prefab.utilities.NBTUtils;
import net.minecraft.nbt.CompoundTag;
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
		ItemStack[] compositeArmor = new ItemStack[] { new ItemStack(DeferredRegisters.ITEM_COMPOSITEHELMET.get()), new ItemStack(DeferredRegisters.ITEM_COMPOSITECHESTPLATE.get()), new ItemStack(DeferredRegisters.ITEM_COMPOSITELEGGINGS.get()), new ItemStack(DeferredRegisters.ITEM_COMPOSITEBOOTS.get()) };
		ItemStack[] combatArmor = new ItemStack[] { new ItemStack(DeferredRegisters.ITEM_COMBATHELMET.get()), new ItemStack(DeferredRegisters.ITEM_COMBATCHESTPLATE.get()), new ItemStack(DeferredRegisters.ITEM_COMBATLEGGINGS.get()), new ItemStack(DeferredRegisters.ITEM_COMBATBOOTS.get()) };

		List<ItemStack> armorPieces = new ArrayList<>();
		event.getEntityLiving().getArmorSlots().forEach(armorPieces::add);

		if (compareArmor(armorPieces, compositeArmor) || compareArmor(armorPieces, combatArmor)) {
			ItemStack stack = armorPieces.get(2);
			CompoundTag tag = stack.getOrCreateTag();
			int stored = tag.getInt(NBTUtils.PLATES);
			if (event.getAmount() >= LETHAL_DAMAGE_AMOUNT && stored > 0) {
				event.setAmount((float) Math.sqrt(event.getAmount()));
				tag.putInt(NBTUtils.PLATES, stored - 1);
				event.getEntityLiving().getCommandSenderWorld().playSound(null, event.getEntityLiving().blockPosition(), SoundRegister.SOUND_CERAMICPLATEBREAKING.get(), SoundSource.PLAYERS, 1, 1);
			}
		}
	}

	private static boolean compareArmor(List<ItemStack> set1, ItemStack[] set2) {
		return ItemStack.isSameIgnoreDurability(set1.get(0), set2[3]) && ItemStack.isSameIgnoreDurability(set1.get(1), set2[2]) && ItemStack.isSameIgnoreDurability(set1.get(2), set2[1]) && ItemStack.isSameIgnoreDurability(set1.get(3), set2[0]);
	}

	@SubscribeEvent
	public static void hydraulicFallDamage(LivingHurtEvent event) {
		DamageSource source = event.getSource();
		if (source.isFall()) {
			ItemStack hydraulicBoots = new ItemStack(DeferredRegisters.ITEM_HYDRAULICBOOTS.get());
			ItemStack combatBoots = new ItemStack(DeferredRegisters.ITEM_COMBATBOOTS.get());
			ItemStack playerBoots = event.getEntityLiving().getItemBySlot(EquipmentSlot.FEET);
			if (ItemUtils.testItems(hydraulicBoots.getItem(), playerBoots.getItem()) || ItemUtils.testItems(combatBoots.getItem(), playerBoots.getItem())) {
				int fluidRequired = (int) Math.log10(event.getAmount());
				if (playerBoots.getCapability(CapabilityUtils.getFluidItemCap()).map(m -> m.getFluidInTank(0).getAmount() - fluidRequired >= 0).orElse(false)) {
					event.setCanceled(true);
					playerBoots.getCapability(CapabilityUtils.getFluidItemCap()).ifPresent(h -> h.drain(fluidRequired, FluidAction.EXECUTE));
					event.getEntityLiving().getCommandSenderWorld().playSound(null, event.getEntityLiving().blockPosition(), SoundRegister.SOUND_HYDRAULICBOOTS.get(), SoundSource.PLAYERS, 1, 1);
				}
			}
		}
	}
}
