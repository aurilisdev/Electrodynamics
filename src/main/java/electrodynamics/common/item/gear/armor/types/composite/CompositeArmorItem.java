package electrodynamics.common.item.gear.armor.types.composite;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.References;
import electrodynamics.api.capability.compositearmor.CapabilityCeramicPlate;
import electrodynamics.api.capability.compositearmor.CapabilityCeramicPlateHolderProvider;
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
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class CompositeArmorItem extends ArmorItem{
	
	private static final String ARMOR_TEXTURE_LOCATION = References.ID + ":textures/model/armor/compositearmor.png";
	
	public CompositeArmorItem(IArmorMaterial materialIn, EquipmentSlotType slot) {
		super(materialIn, slot, new Item.Properties().maxStackSize(1).group(References.CORETAB).isImmuneToFire().setNoRepair());
	}
	
	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
		CompositeArmorItem item = (CompositeArmorItem)stack.getItem();
		if(item.getEquipmentSlot().equals(EquipmentSlotType.CHEST)) {
			return new CapabilityCeramicPlateHolderProvider();
		}
		return null;
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
				ITextComponent tip = new TranslationTextComponent("tooltip.electrodynamics.ceramicplatecount")
					.mergeStyle(TextFormatting.AQUA).append(new StringTextComponent(h.getPlateCount() + ""));
				tooltip.add(tip);
			});
		}
	}
	
	@Override
	public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
		super.onArmorTick(stack, world, player);
		ItemStack[] ARMOR_PIECES = new ItemStack[] {
				new ItemStack(DeferredRegisters.COMPOSITE_HELMET.get()),
				new ItemStack(DeferredRegisters.COMPOSITE_CHESTPLATE.get()),	
				new ItemStack(DeferredRegisters.COMPOSITE_LEGGINGS.get()),
				new ItemStack(DeferredRegisters.COMPOSITE_BOOTS.get())
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
	
	
	@SuppressWarnings("unchecked")
	@Override
	public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default) {
		
		if(itemStack != ItemStack.EMPTY && itemStack.getItem() instanceof ArmorItem) {	
			
			CompositeArmorModel model = (armorSlot == EquipmentSlotType.LEGS ? new CompositeArmorModel(0.5f) : new CompositeArmorModel(1f));
			
			model.HEAD.showModel = armorSlot == EquipmentSlotType.HEAD;
			
			model.CHEST.showModel = armorSlot == EquipmentSlotType.CHEST;
			model.RIGHT_ARM.showModel = armorSlot == EquipmentSlotType.CHEST;
			model.LEFT_ARM.showModel = armorSlot == EquipmentSlotType.CHEST;
			
			model.RIGHT_LEG.showModel = armorSlot == EquipmentSlotType.LEGS;
			model.LEFT_LEG.showModel = armorSlot == EquipmentSlotType.LEGS;
			
			model.RIGHT_SHOE.showModel = armorSlot == EquipmentSlotType.FEET;
			model.LEFT_SHOE.showModel = armorSlot == EquipmentSlotType.FEET;
			
			return (A) model;

		}
		return null;
    }
	
	
    @Override 
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) { 
    	return ARMOR_TEXTURE_LOCATION; 
    }
}
