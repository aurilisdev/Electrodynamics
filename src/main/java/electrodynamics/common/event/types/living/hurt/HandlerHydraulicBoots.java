package electrodynamics.common.event.types.living.hurt;

import electrodynamics.prefab.utilities.ItemUtils;
import electrodynamics.registers.ElectrodynamicsItems;
import electrodynamics.registers.ElectrodynamicsSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.event.entity.living.LivingHurtEvent;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import net.neoforged.neoforge.fluids.capability.IFluidHandler.FluidAction;

public class HandlerHydraulicBoots extends AbstractLivingHurtHandler {

    @Override
    public void handle(LivingHurtEvent event) {
        LivingEntity entity = event.getEntity();
        if (!event.getSource().is(DamageTypes.FALL)) {
            return;
        }
        ItemStack playerBoots = entity.getItemBySlot(EquipmentSlot.FEET);

        if (!ItemUtils.testItems(playerBoots.getItem(), ElectrodynamicsItems.ITEM_HYDRAULICBOOTS.get(), ElectrodynamicsItems.ITEM_COMBATBOOTS.get())) {
            return;
        }

        int fluidRequired = (int) Math.log10(event.getAmount());

        IFluidHandlerItem handler = playerBoots.getCapability(Capabilities.FluidHandler.ITEM);

        if (handler == null || handler.getFluidInTank(0).getAmount() < fluidRequired) {
            return;
        }

        event.setCanceled(true);

        handler.drain(fluidRequired, FluidAction.EXECUTE);

        entity.getCommandSenderWorld().playSound(null, event.getEntity().blockPosition(), ElectrodynamicsSounds.SOUND_HYDRAULICBOOTS.get(), SoundSource.PLAYERS, 1, 1);

    }

}
