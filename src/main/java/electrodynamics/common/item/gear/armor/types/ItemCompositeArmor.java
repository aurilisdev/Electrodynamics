package electrodynamics.common.item.gear.armor.types;

import java.util.List;

import electrodynamics.api.References;
import electrodynamics.client.render.model.armor.types.RenderCompositeArmor;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import electrodynamics.prefab.utilities.NBTUtils;
import electrodynamics.registers.ElectrodynamicsItems;
import electrodynamics.registers.ElectrodynamicsSounds;
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
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ItemCompositeArmor extends ArmorItem {

	public static final String ARMOR_TEXTURE_LOCATION = References.ID + ":textures/model/armor/compositearmor.png";

	public ItemCompositeArmor(EquipmentSlotType slot) {
		super(CompositeArmor.COMPOSITE_ARMOR, slot, new Item.Properties().stacksTo(1).tab(References.CORETAB).fireResistant().setNoRepair());
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A defaultM) {

		if (itemStack != ItemStack.EMPTY && itemStack.getItem() instanceof ArmorItem) {

			RenderCompositeArmor model = armorSlot == EquipmentSlotType.LEGS ? new RenderCompositeArmor(0.5f) : new RenderCompositeArmor(1f);

			model.HEAD.visible = armorSlot == EquipmentSlotType.HEAD;

			model.CHEST.visible = armorSlot == EquipmentSlotType.CHEST;
			model.RIGHT_ARM.visible = armorSlot == EquipmentSlotType.CHEST;
			model.LEFT_ARM.visible = armorSlot == EquipmentSlotType.CHEST;

			model.RIGHT_LEG.visible = armorSlot == EquipmentSlotType.LEGS;
			model.LEFT_LEG.visible = armorSlot == EquipmentSlotType.LEGS;

			model.RIGHT_SHOE.visible = armorSlot == EquipmentSlotType.FEET;
			model.LEFT_SHOE.visible = armorSlot == EquipmentSlotType.FEET;

			model.young = defaultM.young;
			model.riding = defaultM.riding;
			model.crouching = defaultM.crouching;

			model.rightArmPose = defaultM.rightArmPose;
			model.leftArmPose = defaultM.leftArmPose;

			return (A) model;

		}
		return null;
	}

	@Override
	public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) {
		if (!allowdedIn(group)) {
			return;
		}
		ItemStack filled = new ItemStack(this);
		if (getSlot() == EquipmentSlotType.CHEST) {
			CompoundNBT tag = filled.getOrCreateTag();
			tag.putInt(NBTUtils.PLATES, 2);
			items.add(filled);
		}
		ItemStack empty = new ItemStack(this);
		items.add(empty);
	}

	@Override
	public boolean canBeDepleted() {
		return false;
	}

	@Override
	public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
		return false;
	}

	@Override
	public boolean isEnchantable(ItemStack stack) {
		return false;
	}

	@Override
	public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		if (((ArmorItem) stack.getItem()).getSlot() == EquipmentSlotType.CHEST) {
			staticAppendHoverText(stack, worldIn, tooltip, flagIn);
		}
	}

	protected static void staticAppendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		int plates = stack.hasTag() ? stack.getTag().getInt(NBTUtils.PLATES) : 0;
		tooltip.add(ElectroTextUtils.tooltip("ceramicplatecount", plates).withStyle(TextFormatting.AQUA));
	}

	@Override
	public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
		super.onArmorTick(stack, world, player);
		player.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 20));
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
		return ARMOR_TEXTURE_LOCATION;
	}

	public enum CompositeArmor implements IArmorMaterial {
		COMPOSITE_ARMOR(References.ID + ":composite", new int[] { 3, 6, 8, 3 }, 2.0f);

		private final String name;
		private final int[] damageReductionAmountArray;
		private final float toughness;

		// Constructor
		CompositeArmor(String name, int[] damageReductionAmountArray, float toughness) {
			this.name = name;
			this.damageReductionAmountArray = damageReductionAmountArray;
			this.toughness = toughness;
		}

		@Override
		public int getDurabilityForSlot(EquipmentSlotType slotIn) {
			return 2000;
		}

		@Override
		public int getDefenseForSlot(EquipmentSlotType slotIn) {
			return damageReductionAmountArray[slotIn.getIndex()];
		}

		@Override
		public SoundEvent getEquipSound() {
			return ElectrodynamicsSounds.SOUND_EQUIPHEAVYARMOR.get();
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public float getToughness() {
			return toughness;
		}

		@Override
		public float getKnockbackResistance() {
			return 4;
		}

		@Override
		public int getEnchantmentValue() {
			return 0;
		}

		@Override
		public Ingredient getRepairIngredient() {
			return Ingredient.of(ElectrodynamicsItems.ITEM_RAWCOMPOSITEPLATING.get());
		}

	}
}
