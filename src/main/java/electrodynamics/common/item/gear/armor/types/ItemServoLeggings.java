package electrodynamics.common.item.gear.armor.types;

import java.util.List;
import java.util.function.Consumer;

import electrodynamics.api.References;
import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.api.item.IItemElectric;
import electrodynamics.client.ClientRegister;
import electrodynamics.client.render.model.armor.types.ModelServoLeggings;
import electrodynamics.common.item.gear.armor.ICustomArmor;
import electrodynamics.prefab.item.ElectricItemProperties;
import electrodynamics.prefab.utilities.NBTUtils;
import electrodynamics.prefab.utilities.TextUtils;
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
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class ItemServoLeggings extends ArmorItem implements IItemElectric {

	public static final int JOULES_PER_TICK = 5;
	public static final int DURATION_SECONDS = 1;

	public static final float DEFAULT_VANILLA_STEPUP = 0.6F;

	private static final String ARMOR_TEXTURE = References.ID + ":textures/model/armor/servoleggings.png";

	final ElectricItemProperties properties;

	public ItemServoLeggings(ElectricItemProperties pProperties) {
		super(ServoLeggings.SERVOLEGGINGS, EquipmentSlot.LEGS, pProperties);
		properties = pProperties;
	}

	@Override
	public void initializeClient(Consumer<IClientItemExtensions> consumer) {
		consumer.accept(new IClientItemExtensions() {
			@Override
			public HumanoidModel<?> getHumanoidArmorModel(LivingEntity entity, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel<?> properties) {

				ModelServoLeggings<LivingEntity> model = new ModelServoLeggings<>(ClientRegister.SERVO_LEGGINGS.bakeRoot());

				model.crouching = properties.crouching;
				model.riding = properties.riding;
				model.young = properties.young;

				return model;
			}
		});
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
		tooltip.add(TextUtils.tooltip("item.electric.info").withStyle(ChatFormatting.GRAY).append(Component.literal(ChatFormatter.getChatDisplayShort(getJoulesStored(stack), DisplayUnit.JOULES))));
		tooltip.add(TextUtils.tooltip("item.electric.voltage", ChatFormatter.getChatDisplayShort(properties.receive.getVoltage(), DisplayUnit.VOLTAGE) + " / " + ChatFormatter.getChatDisplayShort(properties.extract.getVoltage(), DisplayUnit.VOLTAGE)).withStyle(ChatFormatting.RED));
		staticAppendTooltips(stack, world, tooltip, flagIn);
	}

	protected static void staticAppendTooltips(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flagIn) {
		if (stack.hasTag()) {
			CompoundTag tag = stack.getTag();
			if (tag.getBoolean(NBTUtils.ON)) {
				tooltip.add(TextUtils.tooltip("nightvisiongoggles.status").withStyle(ChatFormatting.GRAY).append(TextUtils.tooltip("nightvisiongoggles.on").withStyle(ChatFormatting.GREEN)));
			} else {
				tooltip.add(TextUtils.tooltip("nightvisiongoggles.status").withStyle(ChatFormatting.GRAY).append(TextUtils.tooltip("nightvisiongoggles.off").withStyle(ChatFormatting.RED)));
			}
			Component modeTip = switch (tag.getInt(NBTUtils.MODE)) {
			case 0 -> TextUtils.tooltip("jetpack.mode").withStyle(ChatFormatting.GRAY).append(TextUtils.tooltip("servolegs.step").withStyle(ChatFormatting.GREEN));
			case 1 -> TextUtils.tooltip("jetpack.mode").withStyle(ChatFormatting.GRAY).append(TextUtils.tooltip("servolegs.both").withStyle(ChatFormatting.AQUA));
			case 2 -> TextUtils.tooltip("jetpack.mode").withStyle(ChatFormatting.GRAY).append(TextUtils.tooltip("servolegs.speed").withStyle(ChatFormatting.GREEN));
			case 3 -> TextUtils.tooltip("jetpack.mode").withStyle(ChatFormatting.GRAY).append(TextUtils.tooltip("servolegs.none").withStyle(ChatFormatting.RED));
			default -> Component.literal("");
			};
			tooltip.add(modeTip);
		} else {
			tooltip.add(TextUtils.tooltip("nightvisiongoggles.status").withStyle(ChatFormatting.GRAY).append(TextUtils.tooltip("nightvisiongoggles.off").withStyle(ChatFormatting.RED)));
			tooltip.add(TextUtils.tooltip("jetpack.mode").withStyle(ChatFormatting.GRAY).append(TextUtils.tooltip("servolegs.none").withStyle(ChatFormatting.RED)));
		}
		IItemElectric.addBatteryTooltip(stack, world, tooltip);
	}

	@Override
	public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
		if (allowedIn(group)) {
			ItemStack charged = new ItemStack(this);
			IItemElectric.setEnergyStored(charged, properties.capacity);
			items.add(charged);

			ItemStack empty = new ItemStack(this);
			IItemElectric.setEnergyStored(empty, 0);
			items.add(empty);
		}
	}

	@Override
	public void onArmorTick(ItemStack stack, Level world, Player player) {
		super.onArmorTick(stack, world, player);
		armorTick(stack, world, player);
	}

	protected static void armorTick(ItemStack stack, Level world, Player player) {
		if (!world.isClientSide) {
			IItemElectric legs = (IItemElectric) stack.getItem();
			CompoundTag tag = stack.getOrCreateTag();
			if (tag.getBoolean(NBTUtils.ON) && legs.getJoulesStored(stack) >= JOULES_PER_TICK) {
				switch (tag.getInt(NBTUtils.MODE)) {
				case 0:
					tag.putBoolean("reset", false);
					tag.putBoolean(NBTUtils.SUCESS, true);
					player.maxUpStep = 1.1F;
					legs.extractPower(stack, JOULES_PER_TICK, false);
					break;
				case 1:
					tag.putBoolean("reset", false);
					tag.putBoolean(NBTUtils.SUCESS, true);
					player.maxUpStep = 1.1F;
					player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, DURATION_SECONDS * 20, 0, false, false, false));
					legs.extractPower(stack, JOULES_PER_TICK, false);
					break;
				case 2:
					tag.putBoolean("reset", false);
					tag.putBoolean(NBTUtils.SUCESS, false);
					player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, DURATION_SECONDS * 20, 0, false, false, false));
					legs.extractPower(stack, JOULES_PER_TICK, false);
					break;
				case 3:
					tag.putBoolean(NBTUtils.SUCESS, false);
					if (!tag.getBoolean("reset")) {
						player.maxUpStep = DEFAULT_VANILLA_STEPUP;
					}
					break;
				default:
					break;
				}
			} else {
				tag.putBoolean(NBTUtils.SUCESS, false);
				if (!tag.getBoolean("reset")) {
					player.maxUpStep = DEFAULT_VANILLA_STEPUP;
				}
			}
		} else if (stack.hasTag()) {
			CompoundTag tag = stack.getTag();
			if (tag.getBoolean(NBTUtils.SUCESS)) {
				switch (tag.getInt(NBTUtils.MODE)) {
				case 0, 1:
					player.maxUpStep = 1.1F;
					break;
				case 2, 3:
					if (!tag.getBoolean("reset")) {
						player.maxUpStep = DEFAULT_VANILLA_STEPUP;
					}
					break;
				default:
					break;
				}
			} else if (!tag.getBoolean("reset")) {
				player.maxUpStep = DEFAULT_VANILLA_STEPUP;
			}
		}
	}

	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		return slotChanged;
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
		return ARMOR_TEXTURE;
	}

	public enum ServoLeggings implements ICustomArmor {
		SERVOLEGGINGS;

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
			return References.ID + ":servoleggings";
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
