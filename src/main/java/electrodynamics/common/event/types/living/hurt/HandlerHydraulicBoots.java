package electrodynamics.common.event.types.living.hurt;

import electrodynamics.prefab.utilities.CapabilityUtils;
import electrodynamics.prefab.utilities.ItemUtils;
import electrodynamics.registers.ElectrodynamicsItems;
import electrodynamics.registers.ElectrodynamicsSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

public class HandlerHydraulicBoots extends AbstractLivingHurtHandler {

	private static final ItemStack HYDRAULIC_BOOTS = new ItemStack(ElectrodynamicsItems.ITEM_HYDRAULICBOOTS.get());
	private static final ItemStack COMBAT_BOOTS = new ItemStack(ElectrodynamicsItems.ITEM_COMBATBOOTS.get());
	
	@Override
	public void handle(LivingHurtEvent event) {
		DamageSource source = event.getSource();
		if(!source.isFall()) {
			return;
		}
		ItemStack playerBoots = event.getEntity().getItemBySlot(EquipmentSlot.FEET);
		if (ItemUtils.testItems(HYDRAULIC_BOOTS.getItem(), playerBoots.getItem()) || ItemUtils.testItems(COMBAT_BOOTS.getItem(), playerBoots.getItem())) {
			int fluidRequired = (int) Math.log10(event.getAmount());
			if (playerBoots.getCapability(CapabilityUtils.getFluidItemCap()).map(m -> m.getFluidInTank(0).getAmount() - fluidRequired >= 0).orElse(false)) {
				event.setCanceled(true);
				playerBoots.getCapability(CapabilityUtils.getFluidItemCap()).ifPresent(h -> h.drain(fluidRequired, FluidAction.EXECUTE));
				event.getEntity().getCommandSenderWorld().playSound(null, event.getEntity().blockPosition(), ElectrodynamicsSounds.SOUND_HYDRAULICBOOTS.get(), SoundSource.PLAYERS, 1, 1);
			}
		}
		
	}

}
