package electrodynamics.common.event.types.living.hurt;

import electrodynamics.prefab.utilities.ItemUtils;
import electrodynamics.registers.ElectrodynamicsItems;
import electrodynamics.registers.ElectrodynamicsSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

public class HandlerHydraulicBoots extends AbstractLivingHurtHandler {

	@Override
	public void handle(LivingHurtEvent event) {
		DamageSource source = event.getSource();
		if (!source.isFall()) {
			return;
		}
		LivingEntity entity = event.getEntityLiving();
		ItemStack playerBoots = entity.getItemBySlot(EquipmentSlot.FEET);

		if (!ItemUtils.testItems(playerBoots.getItem(), ElectrodynamicsItems.ITEM_HYDRAULICBOOTS.get(), ElectrodynamicsItems.ITEM_COMBATBOOTS.get())) {
			return;
		}

		int fluidRequired = (int) Math.log10(event.getAmount());

		if (playerBoots.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).map(m -> m.getFluidInTank(0).getAmount() - fluidRequired >= 0).orElse(false)) {
			event.setCanceled(true);
			playerBoots.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent(h -> h.drain(fluidRequired, FluidAction.EXECUTE));
			entity.getCommandSenderWorld().playSound(null, event.getEntity().blockPosition(), ElectrodynamicsSounds.SOUND_HYDRAULICBOOTS.get(), SoundSource.PLAYERS, 1, 1);
		}

	}

}
