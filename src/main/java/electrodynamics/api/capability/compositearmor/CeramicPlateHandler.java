package electrodynamics.api.capability.compositearmor;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.References;
import electrodynamics.common.item.subtype.SubtypeCeramic;
import electrodynamics.common.player.armor.CompositeArmorItem;
import electrodynamics.common.recipe.ElectrodynamicsRecipe;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickItem;

public class CeramicPlateHandler {

	private static final float LETHAL_DAMAGE_AMOUNT = 15.0f;
	private static final float NO_DAMAGE_DEALT = 1.0f;
	
	public static void attachOnItemStackCreation(AttachCapabilitiesEvent<ItemStack> event) {
		if(event.getObject().getItem() instanceof CompositeArmorItem) {
			CompositeArmorItem item = (CompositeArmorItem)event.getObject().getItem();
			if(item.getEquipmentSlot().equals(EquipmentSlotType.CHEST)) {
				CeramicPlateHolderProvider provider = new CeramicPlateHolderProvider();
				event.addCapability(new ResourceLocation(References.ID,CapabilityCeramicPlate.PLATES_KEY), provider);
				event.addListener(provider::invalidate);
			}
		}
	}
	
	public static void takeDamageWithArmor(LivingHurtEvent event) {
		ItemStack[] ARMOR_PIECES = new ItemStack[] {
				new ItemStack(DeferredRegisters.COMPOSITE_ARMOR_PIECES.get(EquipmentSlotType.HEAD).get()),
				new ItemStack(DeferredRegisters.COMPOSITE_ARMOR_PIECES.get(EquipmentSlotType.CHEST).get()),	
				new ItemStack(DeferredRegisters.COMPOSITE_ARMOR_PIECES.get(EquipmentSlotType.LEGS).get()),
				new ItemStack(DeferredRegisters.COMPOSITE_ARMOR_PIECES.get(EquipmentSlotType.FEET).get())
		};
		
		List<ItemStack> armorPieces = new ArrayList<>();
		event.getEntityLiving().getArmorInventoryList().forEach(armorPieces::add);
		
		if(ItemStack.areItemsEqualIgnoreDurability(armorPieces.get(0), ARMOR_PIECES[3])
			&& ItemStack.areItemsEqualIgnoreDurability(armorPieces.get(1), ARMOR_PIECES[2])
			&& ItemStack.areItemsEqualIgnoreDurability(armorPieces.get(2), ARMOR_PIECES[1])
			&& ItemStack.areItemsEqualIgnoreDurability(armorPieces.get(3), ARMOR_PIECES[0])
		) {
			ElectrodynamicsRecipe.LOGGER.info("Valid");
			
			if(event.getAmount() <= NO_DAMAGE_DEALT) {
				if(!event.getSource().equals(DamageSource.OUT_OF_WORLD)) {
					event.setCanceled(true);
				}
			}
			
			ItemStack stack = armorPieces.get(2);
			stack.getCapability(CapabilityCeramicPlate.CERAMIC_PLATE_HOLDER_CAPABILITY).ifPresent(h ->{
				event.getEntityLiving().replaceItemInInventory(13, new ItemStack(Items.DIAMOND));
				if(event.getAmount() >= LETHAL_DAMAGE_AMOUNT && h.getPlateCount() > 0) {
			
					event.setAmount((float) Math.sqrt(event.getAmount()));
					
					h.setPlateCount(h.getPlateCount() - 1);
					
					event.getEntityLiving().replaceItemInInventory(14, new ItemStack(Items.REDSTONE));
				}
			});
		}
	}
	
	//
	//
	public static void addPlateToArmor(RightClickItem event) {
		if(ItemStack.areItemsEqualIgnoreDurability(event.getItemStack(), new ItemStack(DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeCeramic.plate)))){
			
			List<ItemStack> armorPieces = new ArrayList<>();
			event.getEntityLiving().getArmorInventoryList().forEach(armorPieces::add);
			
			ItemStack chestplate = armorPieces.get(2);
			if(ItemStack.areItemsEqualIgnoreDurability(chestplate, new ItemStack(DeferredRegisters.COMPOSITE_ARMOR_PIECES.get(EquipmentSlotType.CHEST).get()))) {
				chestplate.getCapability(CapabilityCeramicPlate.CERAMIC_PLATE_HOLDER_CAPABILITY).ifPresent(h -> {
					if(h.getPlateCount() < 2) {
						h.setPlateCount(h.getPlateCount() + 1);
						event.getItemStack().shrink(1);
					}
				});
			}
			
			
		}
	}
	
}
