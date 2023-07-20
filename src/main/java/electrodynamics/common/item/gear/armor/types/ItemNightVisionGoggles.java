package electrodynamics.common.item.gear.armor.types;

import java.util.List;
import java.util.function.Consumer;

import electrodynamics.api.References;
import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.api.item.IItemElectric;
import electrodynamics.client.ClientRegister;
import electrodynamics.client.render.model.armor.types.ModelNightVisionGoggles;
import electrodynamics.common.item.gear.armor.ICustomArmor;
import electrodynamics.prefab.item.ElectricItemProperties;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import electrodynamics.prefab.utilities.NBTUtils;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class ItemNightVisionGoggles extends ArmorItem implements IItemElectric {

	private final ElectricItemProperties properties;

	public static final int JOULES_PER_TICK = 5;
	public static final int DURATION_SECONDS = 12;

	private static final String ARMOR_TEXTURE_OFF = References.ID + ":textures/model/armor/nightvisiongogglesoff.png";
	private static final String ARMOR_TEXTURE_ON = References.ID + ":textures/model/armor/nightvisiongoggleson.png";

	public ItemNightVisionGoggles(ElectricItemProperties properties) {
		super(NightVisionGoggles.NVGS, EquipmentSlot.HEAD, properties);
		this.properties = properties;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void initializeClient(Consumer<IClientItemExtensions> consumer) {
		consumer.accept(new IClientItemExtensions() {
			@Override
			public HumanoidModel<?> getHumanoidArmorModel(LivingEntity entity, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel<?> properties) {
				ModelNightVisionGoggles<LivingEntity> model = new ModelNightVisionGoggles<>(ClientRegister.NIGHT_VISION_GOGGLES.bakeRoot());

				model.crouching = properties.crouching;
				model.riding = properties.riding;
				model.young = properties.young;

				return model;
			}
		});
	}

	@Override
	public void onArmorTick(ItemStack stack, Level world, Player player) {
		super.onArmorTick(stack, world, player);
		armorTick(stack, world, player);
	}

	protected static void armorTick(ItemStack stack, Level world, Player player) {
		if (!world.isClientSide) {
			IItemElectric nvgs = (IItemElectric) stack.getItem();
			CompoundTag tag = stack.getOrCreateTag();
			if (tag.getBoolean(NBTUtils.ON) && nvgs.getJoulesStored(stack) >= JOULES_PER_TICK) {
				nvgs.extractPower(stack, JOULES_PER_TICK, false);
				player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, DURATION_SECONDS * 20, 0, false, false, false));
			}
		}
	}

	@Override
	public ElectricItemProperties getElectricProperties() {
		return properties;
	}

	@Override
	public boolean isEnchantable(ItemStack p_41456_) {
		return false;
	}

	@Override
	public boolean isRepairable(ItemStack stack) {
		return false;
	}

	@Override
	public boolean canBeDepleted() {
		return false;
	}

	@Override
	public int getBarWidth(ItemStack stack) {
		return (int) Math.round(13.0f * getJoulesStored(stack) / properties.capacity);
	}

	@Override
	public boolean isBarVisible(ItemStack stack) {
		return getJoulesStored(stack) < properties.capacity;
	}

	@Override
	public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flagIn) {
		super.appendHoverText(stack, world, tooltip, flagIn);
		tooltip.add(ElectroTextUtils.tooltip("item.electric.info").withStyle(ChatFormatting.GRAY).append(ChatFormatter.getChatDisplayShort(getJoulesStored(stack), DisplayUnit.JOULES)));
		tooltip.add(ElectroTextUtils.tooltip("item.electric.voltage", ElectroTextUtils.ratio(ChatFormatter.getChatDisplayShort(properties.receive.getVoltage(), DisplayUnit.VOLTAGE), ChatFormatter.getChatDisplayShort(properties.extract.getVoltage(), DisplayUnit.VOLTAGE))).withStyle(ChatFormatting.RED));
		if (stack.hasTag() && stack.getTag().getBoolean(NBTUtils.ON)) {
			tooltip.add(ElectroTextUtils.tooltip("nightvisiongoggles.status").withStyle(ChatFormatting.GRAY).append(ElectroTextUtils.tooltip("nightvisiongoggles.on").withStyle(ChatFormatting.GREEN)));
		} else {
			tooltip.add(ElectroTextUtils.tooltip("nightvisiongoggles.status").withStyle(ChatFormatting.GRAY).append(ElectroTextUtils.tooltip("nightvisiongoggles.off").withStyle(ChatFormatting.RED)));
		}
		IItemElectric.addBatteryTooltip(stack, world, tooltip);
	}

	@Override
	public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
		if (allowedIn(group)) {

			ItemStack empty = new ItemStack(this);
			IItemElectric.setEnergyStored(empty, 0);
			items.add(empty);

			ItemStack charged = new ItemStack(this);
			IItemElectric.setEnergyStored(charged, properties.capacity);
			items.add(charged);
		}
	}

	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		return slotChanged;
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
		boolean isOn = stack.hasTag() && stack.getTag().getBoolean(NBTUtils.ON);
		if (isOn) {
			return ARMOR_TEXTURE_ON;
		}
		return ARMOR_TEXTURE_OFF;
	}

	public enum NightVisionGoggles implements ICustomArmor {
		NVGS;

		@Override
		public int getDurabilityForSlot(EquipmentSlot slotIn) {
			return 100;
		}

		@Override
		public int getDefenseForSlot(EquipmentSlot slotIn) {
			return 1;
		}

		@Override
		public SoundEvent getEquipSound() {
			return SoundEvents.ARMOR_EQUIP_IRON;
		}

		@Override
		public String getName() {
			return References.ID + ":nvgs";
		}

		@Override
		public float getToughness() {
			return 0.0F;
		}

		@Override
		public float getKnockbackResistance() {
			return 0.0F;
		}

	}

	@Override
	public Item getDefaultStorageBattery() {
		return ElectrodynamicsItems.ITEM_BATTERY.get();
	}

	@Override
	public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack other, Slot slot, ClickAction action, Player player, SlotAccess access) {

		if (!IItemElectric.overrideOtherStackedOnMe(stack, other, slot, action, player, access)) {
			return super.overrideOtherStackedOnMe(stack, other, slot, action, player, access);
		}

		return true;

	}

}
