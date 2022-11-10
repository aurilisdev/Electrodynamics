package electrodynamics.common.event;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.api.References;
import electrodynamics.api.item.ItemUtils;
import electrodynamics.common.packet.NetworkHandler;
import electrodynamics.common.packet.types.PacketJetpackEquipedSound;
import electrodynamics.common.packet.types.PacketPlayerInformation;
import electrodynamics.prefab.utilities.CapabilityUtils;
import electrodynamics.prefab.utilities.NBTUtils;
import electrodynamics.registers.ElectrodynamicsItems;
import electrodynamics.registers.ElectrodynamicsSounds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.PacketDistributor;

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
		ItemStack[] compositeArmor = new ItemStack[] { new ItemStack(ElectrodynamicsItems.ITEM_COMPOSITEHELMET.get()), new ItemStack(ElectrodynamicsItems.ITEM_COMPOSITECHESTPLATE.get()), new ItemStack(ElectrodynamicsItems.ITEM_COMPOSITELEGGINGS.get()), new ItemStack(ElectrodynamicsItems.ITEM_COMPOSITEBOOTS.get()) };
		ItemStack[] combatArmor = new ItemStack[] { new ItemStack(ElectrodynamicsItems.ITEM_COMBATHELMET.get()), new ItemStack(ElectrodynamicsItems.ITEM_COMBATCHESTPLATE.get()), new ItemStack(ElectrodynamicsItems.ITEM_COMBATLEGGINGS.get()), new ItemStack(ElectrodynamicsItems.ITEM_COMBATBOOTS.get()) };

		List<ItemStack> armorPieces = new ArrayList<>();
		event.getEntity().getArmorSlots().forEach(armorPieces::add);

		if (compareArmor(armorPieces, compositeArmor) || compareArmor(armorPieces, combatArmor)) {
			ItemStack stack = armorPieces.get(2);
			CompoundTag tag = stack.getOrCreateTag();
			int stored = tag.getInt(NBTUtils.PLATES);
			if (event.getAmount() >= LETHAL_DAMAGE_AMOUNT && stored > 0) {
				event.setAmount((float) Math.sqrt(event.getAmount()));
				tag.putInt(NBTUtils.PLATES, stored - 1);
				event.getEntity().getCommandSenderWorld().playSound(null, event.getEntity().blockPosition(), ElectrodynamicsSounds.SOUND_CERAMICPLATEBREAKING.get(), SoundSource.PLAYERS, 1, 1);
			}
		}
	}

	private static boolean compareArmor(List<ItemStack> set1, ItemStack[] set2) {
		if (set1.size() >= 3) {
			return ItemStack.isSameIgnoreDurability(set1.get(0), set2[3]) && ItemStack.isSameIgnoreDurability(set1.get(1), set2[2]) && ItemStack.isSameIgnoreDurability(set1.get(2), set2[1]) && ItemStack.isSameIgnoreDurability(set1.get(3), set2[0]);
		}
		return false;
	}

	@SubscribeEvent
	public static void hydraulicFallDamage(LivingHurtEvent event) {
		DamageSource source = event.getSource();
		if (source.isFall()) {
			ItemStack hydraulicBoots = new ItemStack(ElectrodynamicsItems.ITEM_HYDRAULICBOOTS.get());
			ItemStack combatBoots = new ItemStack(ElectrodynamicsItems.ITEM_COMBATBOOTS.get());
			ItemStack playerBoots = event.getEntity().getItemBySlot(EquipmentSlot.FEET);
			if (ItemUtils.testItems(hydraulicBoots.getItem(), playerBoots.getItem()) || ItemUtils.testItems(combatBoots.getItem(), playerBoots.getItem())) {
				int fluidRequired = (int) Math.log10(event.getAmount());
				if (playerBoots.getCapability(CapabilityUtils.getFluidItemCap()).map(m -> m.getFluidInTank(0).getAmount() - fluidRequired >= 0).orElse(false)) {
					event.setCanceled(true);
					playerBoots.getCapability(CapabilityUtils.getFluidItemCap()).ifPresent(h -> h.drain(fluidRequired, FluidAction.EXECUTE));
					event.getEntity().getCommandSenderWorld().playSound(null, event.getEntity().blockPosition(), ElectrodynamicsSounds.SOUND_HYDRAULICBOOTS.get(), SoundSource.PLAYERS, 1, 1);
				}
			}
		}
	}

	@SubscribeEvent
	public static void jetpackEquipedHandler(LivingEquipmentChangeEvent event) {
		Entity entity = event.getEntity();
		if (event.getSlot() == EquipmentSlot.CHEST && entity instanceof Player player) {
			ItemStack chest = event.getTo();
			if (event.getFrom().isEmpty() && (ItemUtils.testItems(chest.getItem(), ElectrodynamicsItems.ITEM_JETPACK.get()) || ItemUtils.testItems(chest.getItem(), ElectrodynamicsItems.ITEM_COMBATCHESTPLATE.get()))) {
				NetworkHandler.CHANNEL.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player), new PacketJetpackEquipedSound(player.getUUID()));
			}
		}
	}

	@SubscribeEvent
	public static void jetpackSoundHandler(PlayerEvent.StartTracking event) {
		Entity entity = event.getTarget();
		if (entity instanceof Player player) {
			ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);
			if (!chest.isEmpty() && (ItemUtils.testItems(chest.getItem(), ElectrodynamicsItems.ITEM_JETPACK.get()) || ItemUtils.testItems(chest.getItem(), ElectrodynamicsItems.ITEM_COMBATCHESTPLATE.get()))) {
				ServerPlayer server = (ServerPlayer) event.getEntity();
				NetworkHandler.CHANNEL.sendTo(new PacketJetpackEquipedSound(player.getUUID()), server.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);
			}
		}
	}

	// TODO: Why was this commented?
	/*
	 * @SubscribeEvent public static void jetpackOwnerSoundHandler(PlayerLoggedInEvent event) { Player player = event.getPlayer(); if(player instanceof ServerPlayer server) { ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST); if(!chest.isEmpty() && (ItemUtils.testItems(chest.getItem(), DeferredRegisters.ITEM_JETPACK.get()) || ItemUtils.testItems(chest.getItem(), DeferredRegisters.ITEM_COMBATCHESTPLATE.get()))) { NetworkHandler.CHANNEL.sendTo(new PacketJetpackEquipedSound(player.getUUID()), server.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT); } } }
	 */

}
