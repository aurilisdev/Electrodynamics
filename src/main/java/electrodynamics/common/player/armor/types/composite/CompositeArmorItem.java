package electrodynamics.common.player.armor.types.composite;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.References;
import electrodynamics.api.capability.compositearmor.CapabilityCeramicPlate;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class CompositeArmorItem extends ArmorItem{
	
	//private static final String ARMOR_TEXTURE_LOCATION = References.ID + ":textures/model/armor/compositearmor.png";
	
	
	public CompositeArmorItem(IArmorMaterial materialIn, EquipmentSlotType slot) {
		super(materialIn, slot, new Item.Properties().maxStackSize(1).group(References.CORETAB).isImmuneToFire().setNoRepair());
	}
	
	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		return false;
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn){
		super.addInformation(stack, worldIn, tooltip, flagIn);
		if(getEquipmentSlot().equals(EquipmentSlotType.CHEST)) {
			stack.getCapability(CapabilityCeramicPlate.CERAMIC_PLATE_HOLDER_CAPABILITY).ifPresent(h -> {
				tooltip.add(new StringTextComponent("Plates Remaining: " + h.getPlateCount()).mergeStyle(TextFormatting.AQUA));
			});
			
		}
	}
	
	@Override
	public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
		super.onArmorTick(stack, world, player);
		ItemStack[] ARMOR_PIECES = new ItemStack[] {
				new ItemStack(DeferredRegisters.COMPOSITE_ARMOR_PIECES.get(EquipmentSlotType.HEAD).get()),
				new ItemStack(DeferredRegisters.COMPOSITE_ARMOR_PIECES.get(EquipmentSlotType.CHEST).get()),	
				new ItemStack(DeferredRegisters.COMPOSITE_ARMOR_PIECES.get(EquipmentSlotType.LEGS).get()),
				new ItemStack(DeferredRegisters.COMPOSITE_ARMOR_PIECES.get(EquipmentSlotType.FEET).get())
		};
		
		List<ItemStack> armorPieces = new ArrayList<>();
		player.getArmorInventoryList().forEach(armorPieces::add);
		
		if(ItemStack.areItemsEqual(armorPieces.get(0), ARMOR_PIECES[3])
				|| ItemStack.areItemsEqual(armorPieces.get(1), ARMOR_PIECES[2])
				|| ItemStack.areItemsEqual(armorPieces.get(2), ARMOR_PIECES[1])
				|| ItemStack.areItemsEqual(armorPieces.get(3), ARMOR_PIECES[0])
		) {
			player.addPotionEffect(new EffectInstance(Effects.SLOWNESS,20));
		}
	}
	
	/*
	
	@SuppressWarnings("unchecked")
	@Override
	public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default) {
		
		
		if(itemStack != ItemStack.EMPTY && itemStack.getItem() instanceof ArmorItem) {	
			
			CompositeArmorModel model = (armorSlot == EquipmentSlotType.LEGS ? new CompositeArmorModel(0.5f) : new CompositeArmorModel(1f));

			model.bipedHeadwear.showModel = false;
			model.bipedHead.showModel = armorSlot == EquipmentSlotType.HEAD;
			
			model.bipedBody.showModel = armorSlot == EquipmentSlotType.CHEST;
			model.bipedLeftArm.showModel = armorSlot == EquipmentSlotType.CHEST;
			model.bipedRightArm.showModel = armorSlot == EquipmentSlotType.CHEST;
			
			model.bipedLeftLeg.showModel = armorSlot == EquipmentSlotType.LEGS;
			model.bipedRightLeg.showModel = armorSlot == EquipmentSlotType.HEAD;
			
			model.bipedLeftLeg.showModel = armorSlot == EquipmentSlotType.FEET;
			model.bipedLeftLeg.showModel = armorSlot == EquipmentSlotType.FEET;
			
			
			model.isChild = _default.isChild;
			model.isSitting = _default.isSitting;
			model.isSneak = _default.isSneak;
			
			return (A) model;
			
		}
		return null;
	}
	
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
		return ARMOR_TEXTURE_LOCATION;
	}
	*/
}
