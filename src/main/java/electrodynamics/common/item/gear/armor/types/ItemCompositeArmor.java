package electrodynamics.common.item.gear.armor.types;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import electrodynamics.api.References;
import electrodynamics.client.ClientRegister;
import electrodynamics.client.render.model.armor.types.ModelCompositeArmor;
import electrodynamics.common.item.gear.armor.ICustomArmor;
import electrodynamics.prefab.utilities.NBTUtils;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import electrodynamics.registers.ElectrodynamicsItems;
import electrodynamics.registers.ElectrodynamicsSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class ItemCompositeArmor extends ArmorItem {

	public static final String ARMOR_TEXTURE_LOCATION = References.ID + ":textures/model/armor/compositearmor.png";

	public ItemCompositeArmor(EquipmentSlot slot) {
		super(CompositeArmor.COMPOSITE_ARMOR, slot, new Item.Properties().stacksTo(1).tab(References.CORETAB).fireResistant().setNoRepair());
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void initializeClient(Consumer<IClientItemExtensions> consumer) {
		consumer.accept(new IClientItemExtensions() {
			@Override
			public HumanoidModel<?> getHumanoidArmorModel(LivingEntity entity, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel<?> properties) {

				ItemStack[] armorPiecesArray = new ItemStack[] { new ItemStack(ElectrodynamicsItems.ITEM_COMPOSITEHELMET.get()), new ItemStack(ElectrodynamicsItems.ITEM_COMPOSITECHESTPLATE.get()), new ItemStack(ElectrodynamicsItems.ITEM_COMPOSITELEGGINGS.get()), new ItemStack(ElectrodynamicsItems.ITEM_COMPOSITEBOOTS.get()) };

				List<ItemStack> armorPieces = new ArrayList<>();
				entity.getArmorSlots().forEach(armorPieces::add);

				boolean isBoth = ItemStack.isSameIgnoreDurability(armorPieces.get(0), armorPiecesArray[3]) && ItemStack.isSameIgnoreDurability(armorPieces.get(1), armorPiecesArray[2]);

				boolean hasChest = ItemStack.isSameIgnoreDurability(armorPieces.get(2), armorPiecesArray[1]);

				ModelCompositeArmor<LivingEntity> model;

				if (isBoth) {
					if (hasChest) {
						model = new ModelCompositeArmor<>(ClientRegister.COMPOSITE_ARMOR_LAYER_COMB_CHEST.bakeRoot(), slot);
					} else {
						model = new ModelCompositeArmor<>(ClientRegister.COMPOSITE_ARMOR_LAYER_COMB_NOCHEST.bakeRoot(), slot);
					}
				} else if (slot == EquipmentSlot.FEET) {
					model = new ModelCompositeArmor<>(ClientRegister.COMPOSITE_ARMOR_LAYER_BOOTS.bakeRoot(), slot);
				} else if (hasChest) {
					model = new ModelCompositeArmor<>(ClientRegister.COMPOSITE_ARMOR_LAYER_LEG_CHEST.bakeRoot(), slot);
				} else {
					model = new ModelCompositeArmor<>(ClientRegister.COMPOSITE_ARMOR_LAYER_LEG_NOCHEST.bakeRoot(), slot);
				}

				model.crouching = properties.crouching;
				model.riding = properties.riding;
				model.young = properties.young;

				return model;
			}
		});
	}

	@Override
	public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
		if (allowedIn(group)) {
			ItemStack filled = new ItemStack(this);
			if (getSlot() == EquipmentSlot.CHEST) {
				CompoundTag tag = filled.getOrCreateTag();
				tag.putInt(NBTUtils.PLATES, 2);
				items.add(filled);
			}
			ItemStack empty = new ItemStack(this);
			items.add(empty);
		}
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
	public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		if (((ArmorItem) stack.getItem()).getSlot() == EquipmentSlot.CHEST) {
			staticAppendHoverText(stack, worldIn, tooltip, flagIn);
		}
	}

	protected static void staticAppendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		int plates = stack.hasTag() ? stack.getTag().getInt(NBTUtils.PLATES) : 0;
		tooltip.add(ElectroTextUtils.tooltip("ceramicplatecount", Component.translatable(plates + "")).withStyle(ChatFormatting.AQUA));
	}

	@Override
	public void onArmorTick(ItemStack stack, Level world, Player player) {
		super.onArmorTick(stack, world, player);
		player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20));
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
		return ARMOR_TEXTURE_LOCATION;
	}

	public enum CompositeArmor implements ICustomArmor {
		COMPOSITE_ARMOR(References.ID + ":composite", new int[] { 6, 12, 16, 6 }, 2.0f);

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
		public int getDurabilityForSlot(EquipmentSlot slotIn) {
			return 2000;
		}

		@Override
		public int getDefenseForSlot(EquipmentSlot slotIn) {
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

	}
}
