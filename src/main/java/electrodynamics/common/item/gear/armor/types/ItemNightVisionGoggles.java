package electrodynamics.common.item.gear.armor.types;

import java.util.List;
import java.util.function.Consumer;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.References;
import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.api.capability.types.boolstorage.CapabilityBooleanStorage;
import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.api.item.IItemElectric;
import electrodynamics.api.item.ItemUtils;
import electrodynamics.client.ClientRegister;
import electrodynamics.client.render.model.armor.types.ModelNightVisionGoggles;
import electrodynamics.common.item.gear.armor.ICustomArmor;
import electrodynamics.prefab.item.ElectricItemProperties;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.IItemRenderProperties;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class ItemNightVisionGoggles extends ArmorItem implements IItemElectric {

	private final ElectricItemProperties properties;

	public static final int JOULES_PER_TICK = 5;
	public static final int DURATION_SECONDS = 12;

	private static final String ARMOR_TEXTURE_OFF = References.ID + ":textures/model/armor/nightvisiongogglesoff.png";
	private static final String ARMOR_TEXTURE_ON = References.ID + ":textures/model/armor/nightvisiongoggleson.png";

	private static final String BOOLEAN_NBT_KEY = "status";

	public ItemNightVisionGoggles(ElectricItemProperties properties) {
		super(NightVisionGoggles.NVGS, EquipmentSlot.HEAD, properties);
		this.properties = properties;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void initializeClient(Consumer<IItemRenderProperties> consumer) {
		consumer.accept(new IItemRenderProperties() {
			@Override
			public <A extends HumanoidModel<?>> A getArmorModel(LivingEntity entity, ItemStack stack, EquipmentSlot slot, A properties) {

				ModelNightVisionGoggles<LivingEntity> model = new ModelNightVisionGoggles<>(ClientRegister.NIGHT_VISION_GOGGLES.bakeRoot());

				model.crouching = properties.crouching;
				model.riding = properties.riding;
				model.young = properties.young;

				return (A) model;
			}
		});
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
		CapabilityBooleanStorage storage = new CapabilityBooleanStorage(1);
		storage.setBoolean(0, false);
		return storage;
	}

	@Override
	public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean isSelected) {
		ItemNightVisionGoggles nvgs = (ItemNightVisionGoggles) stack.getItem();
		if (entity instanceof Player player && !world.isClientSide) {
			boolean status = stack.getCapability(ElectrodynamicsCapabilities.BOOLEAN_STORAGE_CAPABILITY).map(m -> m.getBoolean(0)).orElse(false);
			if (!stack.hasTag()) {
				stack.setTag(new CompoundTag());
			}
			stack.getTag().putBoolean(BOOLEAN_NBT_KEY, status);
			if (status && ItemUtils.testItems(player.getItemBySlot(EquipmentSlot.HEAD).getItem(), DeferredRegisters.ITEM_NIGHTVISIONGOGGLES.get())
					&& nvgs.getJoulesStored(stack) >= JOULES_PER_TICK) {
				nvgs.extractPower(stack, JOULES_PER_TICK, false);
				player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, DURATION_SECONDS * 20, 0, false, false, false));

			}
		}
		super.inventoryTick(stack, world, entity, slot, isSelected);
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
		tooltip.add(new TranslatableComponent("tooltip.item.electric.info").withStyle(ChatFormatting.GRAY)
				.append(new TextComponent(ChatFormatter.getChatDisplayShort(getJoulesStored(stack), DisplayUnit.JOULES))));
		tooltip.add(
				new TranslatableComponent("tooltip.item.electric.voltage",
						ChatFormatter.getChatDisplayShort(properties.receive.getVoltage(), DisplayUnit.VOLTAGE) + " / "
								+ ChatFormatter.getChatDisplayShort(properties.extract.getVoltage(), DisplayUnit.VOLTAGE))
										.withStyle(ChatFormatting.RED));
		if (stack.hasTag() && stack.getTag().getBoolean(BOOLEAN_NBT_KEY)) {
			tooltip.add(new TranslatableComponent("tooltip.nightvisiongoggles.status").withStyle(ChatFormatting.GRAY)
					.append(new TranslatableComponent("tooltip.nightvisiongoggles.on").withStyle(ChatFormatting.GREEN)));
		} else {
			tooltip.add(new TranslatableComponent("tooltip.nightvisiongoggles.status").withStyle(ChatFormatting.GRAY)
					.append(new TranslatableComponent("tooltip.nightvisiongoggles.off").withStyle(ChatFormatting.RED)));
		}
	}

	@Override
	public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
		if (allowdedIn(group)) {
			ItemStack charged = new ItemStack(this);
			IItemElectric.setEnergyStored(charged, properties.capacity);
			items.add(charged);

			ItemStack empty = new ItemStack(this);
			IItemElectric.setEnergyStored(empty, 0);
			items.add(empty);
		}
	}

	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		return slotChanged;
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
		boolean isOn = stack.getCapability(ElectrodynamicsCapabilities.BOOLEAN_STORAGE_CAPABILITY).map(m -> m.getBoolean(0)).orElse(false);
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

}
