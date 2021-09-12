package electrodynamics.common.item.gear.armor.types.composite;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.References;
import electrodynamics.api.capability.ceramicplate.CapabilityCeramicPlate;
import electrodynamics.api.capability.ceramicplate.CapabilityCeramicPlateHolderProvider;
import electrodynamics.client.render.model.armor.types.RenderCompositeArmor;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class CompositeArmorItem extends ArmorItem {

    private static final String ARMOR_TEXTURE_LOCATION = References.ID + ":textures/model/armor/compositearmor.png";

    public CompositeArmorItem(IArmorMaterial materialIn, EquipmentSlotType slot) {
	super(materialIn, slot, new Item.Properties().maxStackSize(1).group(References.CORETAB).isImmuneToFire().setNoRepair());
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
	CompositeArmorItem item = (CompositeArmorItem) stack.getItem();
	if (item.getEquipmentSlot().equals(EquipmentSlotType.CHEST)) {
	    return new CapabilityCeramicPlateHolderProvider();
	}
	return null;
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
	if (isInGroup(group)) {
	    ItemStack filled = new ItemStack(this);
	    if (ItemStack.areItemsEqualIgnoreDurability(filled, new ItemStack(DeferredRegisters.COMPOSITE_CHESTPLATE.get()))) {
		filled.getCapability(CapabilityCeramicPlate.CERAMIC_PLATE_HOLDER_CAPABILITY).ifPresent(h -> h.increasePlateCount(2));
		items.add(filled);
	    }
	    ItemStack empty = new ItemStack(this);
	    items.add(empty);
	}
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
	return false;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
	return false;
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
	super.addInformation(stack, worldIn, tooltip, flagIn);
	if (getEquipmentSlot().equals(EquipmentSlotType.CHEST)) {
	    stack.getCapability(CapabilityCeramicPlate.CERAMIC_PLATE_HOLDER_CAPABILITY).ifPresent(h -> {
		ITextComponent tip = new TranslationTextComponent("tooltip.electrodynamics.ceramicplatecount",
			new StringTextComponent(h.getPlateCount() + "")).mergeStyle(TextFormatting.AQUA);
		tooltip.add(tip);
	    });
	}
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
	super.onArmorTick(stack, world, player);
	ItemStack[] pieces = new ItemStack[] { new ItemStack(DeferredRegisters.COMPOSITE_HELMET.get()),
		new ItemStack(DeferredRegisters.COMPOSITE_CHESTPLATE.get()), new ItemStack(DeferredRegisters.COMPOSITE_LEGGINGS.get()),
		new ItemStack(DeferredRegisters.COMPOSITE_BOOTS.get()) };

	List<ItemStack> armorPieces = new ArrayList<>();
	player.getArmorInventoryList().forEach(armorPieces::add);

	if (ItemStack.areItemsEqual(armorPieces.get(0), pieces[3]) || ItemStack.areItemsEqual(armorPieces.get(1), pieces[2])
		|| ItemStack.areItemsEqual(armorPieces.get(2), pieces[1]) || ItemStack.areItemsEqual(armorPieces.get(3), pieces[0])) {
	    player.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 20));
	}
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A defaultM) {

	if (itemStack != ItemStack.EMPTY && itemStack.getItem() instanceof ArmorItem) {

	    RenderCompositeArmor model = armorSlot == EquipmentSlotType.LEGS ? new RenderCompositeArmor(0.5f) : new RenderCompositeArmor(1f);

	    model.HEAD.showModel = armorSlot == EquipmentSlotType.HEAD;

	    model.CHEST.showModel = armorSlot == EquipmentSlotType.CHEST;
	    model.RIGHT_ARM.showModel = armorSlot == EquipmentSlotType.CHEST;
	    model.LEFT_ARM.showModel = armorSlot == EquipmentSlotType.CHEST;

	    model.RIGHT_LEG.showModel = armorSlot == EquipmentSlotType.LEGS;
	    model.LEFT_LEG.showModel = armorSlot == EquipmentSlotType.LEGS;

	    model.RIGHT_SHOE.showModel = armorSlot == EquipmentSlotType.FEET;
	    model.LEFT_SHOE.showModel = armorSlot == EquipmentSlotType.FEET;

	    model.isChild = defaultM.isChild;
	    model.isSitting = defaultM.isSitting;
	    model.isSneak = defaultM.isSneak;

	    model.rightArmPose = defaultM.rightArmPose;
	    model.leftArmPose = defaultM.leftArmPose;

	    return (A) model;

	}
	return null;
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
	return ARMOR_TEXTURE_LOCATION;
    }
}
