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
import net.minecraftforge.client.IItemRenderProperties;

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
	public void initializeClient(Consumer<IItemRenderProperties> consumer) {
		consumer.accept(new IItemRenderProperties() {
			@Override
			public <A extends HumanoidModel<?>> A getArmorModel(LivingEntity entity, ItemStack stack, EquipmentSlot slot, A properties) {

				ModelServoLeggings<LivingEntity> model = new ModelServoLeggings<>(ClientRegister.SERVO_LEGGINGS.bakeRoot());

				model.crouching = properties.crouching;
				model.riding = properties.riding;
				model.young = properties.young;

				return (A) model;
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
		tooltip.add(new TranslatableComponent("tooltip.item.electric.info").withStyle(ChatFormatting.GRAY).append(new TextComponent(ChatFormatter.getChatDisplayShort(getJoulesStored(stack), DisplayUnit.JOULES))));
		tooltip.add(new TranslatableComponent("tooltip.item.electric.voltage", ChatFormatter.getChatDisplayShort(properties.receive.getVoltage(), DisplayUnit.VOLTAGE) + " / " + ChatFormatter.getChatDisplayShort(properties.extract.getVoltage(), DisplayUnit.VOLTAGE)).withStyle(ChatFormatting.RED));
		staticAppendTooltips(stack, world, tooltip, flagIn);
	}

	protected static void staticAppendTooltips(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flagIn) {
		if (stack.hasTag()) {
			CompoundTag tag = stack.getTag();
			if (tag.getBoolean(NBTUtils.ON)) {
				tooltip.add(new TranslatableComponent("tooltip.nightvisiongoggles.status").withStyle(ChatFormatting.GRAY).append(new TranslatableComponent("tooltip.nightvisiongoggles.on").withStyle(ChatFormatting.GREEN)));
			} else {
				tooltip.add(new TranslatableComponent("tooltip.nightvisiongoggles.status").withStyle(ChatFormatting.GRAY).append(new TranslatableComponent("tooltip.nightvisiongoggles.off").withStyle(ChatFormatting.RED)));
			}
			Component modeTip = switch (tag.getInt(NBTUtils.MODE)) {
			case 0 -> new TranslatableComponent("tooltip.jetpack.mode").withStyle(ChatFormatting.GRAY).append(new TranslatableComponent("tooltip.servolegs.step").withStyle(ChatFormatting.GREEN));
			case 1 -> new TranslatableComponent("tooltip.jetpack.mode").withStyle(ChatFormatting.GRAY).append(new TranslatableComponent("tooltip.servolegs.both").withStyle(ChatFormatting.AQUA));
			case 2 -> new TranslatableComponent("tooltip.jetpack.mode").withStyle(ChatFormatting.GRAY).append(new TranslatableComponent("tooltip.servolegs.speed").withStyle(ChatFormatting.GREEN));
			case 3 -> new TranslatableComponent("tooltip.jetpack.mode").withStyle(ChatFormatting.GRAY).append(new TranslatableComponent("tooltip.servolegs.none").withStyle(ChatFormatting.RED));
			default -> new TextComponent("");
			};
			tooltip.add(modeTip);
		} else {
			tooltip.add(new TranslatableComponent("tooltip.nightvisiongoggles.status").withStyle(ChatFormatting.GRAY).append(new TranslatableComponent("tooltip.nightvisiongoggles.off").withStyle(ChatFormatting.RED)));
			tooltip.add(new TranslatableComponent("tooltip.jetpack.mode").withStyle(ChatFormatting.GRAY).append(new TranslatableComponent("tooltip.servolegs.none").withStyle(ChatFormatting.RED)));
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
			} else {
				if (!tag.getBoolean("reset")) {
					player.maxUpStep = DEFAULT_VANILLA_STEPUP;
				}
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

}
