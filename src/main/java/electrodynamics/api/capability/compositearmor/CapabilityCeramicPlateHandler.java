package electrodynamics.api.capability.compositearmor;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.DeferredRegisters;
import electrodynamics.SoundRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class CapabilityCeramicPlateHandler {

	private static final float LETHAL_DAMAGE_AMOUNT = 18.0f;
	
	@SubscribeEvent
	public static void takeDamageWithArmor(LivingHurtEvent event) {
		
		ItemStack[] ARMOR_PIECES = new ItemStack[] {
				new ItemStack(DeferredRegisters.COMPOSITE_HELMET.get()),
				new ItemStack(DeferredRegisters.COMPOSITE_CHESTPLATE.get()),	
				new ItemStack(DeferredRegisters.COMPOSITE_LEGGINGS.get()),
				new ItemStack(DeferredRegisters.COMPOSITE_BOOTS.get())
		};
		
		List<ItemStack> armorPieces = new ArrayList<>();
		event.getEntityLiving().getArmorInventoryList().forEach(armorPieces::add);
		
		if(ItemStack.areItemsEqualIgnoreDurability(armorPieces.get(0), ARMOR_PIECES[3])
			&& ItemStack.areItemsEqualIgnoreDurability(armorPieces.get(1), ARMOR_PIECES[2])
			&& ItemStack.areItemsEqualIgnoreDurability(armorPieces.get(2), ARMOR_PIECES[1])
			&& ItemStack.areItemsEqualIgnoreDurability(armorPieces.get(3), ARMOR_PIECES[0])
		) {
			ItemStack stack = armorPieces.get(2);
			stack.getCapability(CapabilityCeramicPlate.CERAMIC_PLATE_HOLDER_CAPABILITY).ifPresent(h ->{
				if(event.getAmount() >= LETHAL_DAMAGE_AMOUNT && h.getPlateCount() > 0) {
					
					event.setAmount((float) Math.sqrt(event.getAmount()));
					h.decreasePlateCount(1);
					event.getEntityLiving().getEntityWorld().playSound(
							null,
							event.getEntityLiving().getPosition(), 
							SoundRegister.SOUND_CERAMICPLATEBREAKING.get(), 
							SoundCategory.PLAYERS, 1, 1);
				}
			});
			
		}
	}
}
