package electrodynamics.common.item.gear.armor.types;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import electrodynamics.Electrodynamics;
import electrodynamics.client.ElectrodynamicsClientRegister;
import electrodynamics.client.model.armor.ModelCombatArmor;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import electrodynamics.registers.ElectrodynamicsFluids;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
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
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import voltaic.api.electricity.formatting.ChatFormatter;
import voltaic.api.electricity.formatting.DisplayUnits;
import voltaic.api.fluid.RestrictedFluidHandlerItemStack;
import voltaic.api.item.IItemElectric;
import voltaic.common.item.gear.ItemVoltaicArmor;
import voltaic.prefab.item.ElectricItemProperties;
import voltaic.prefab.utilities.NBTUtils;
import voltaic.prefab.utilities.VoltaicTextUtils;

public class ItemCombatArmor extends ItemVoltaicArmor implements IItemElectric {

	public static final String ARMOR_TEXTURE_LOCATION = Electrodynamics.ID + ":textures/model/armor/combatarmor.png";

	private final ElectricItemProperties properties;

	public static final float OFFSET = 0.2F;

	public ItemCombatArmor(Properties properties, EquipmentSlot type, Supplier<CreativeModeTab> creativeTab) {
		super(ItemCompositeArmor.CompositeArmor.COMPOSITE_ARMOR, type, properties, creativeTab);
		switch (type) {
		case HEAD, LEGS:
			this.properties = (ElectricItemProperties) properties;
			break;
		default:
			this.properties = new ElectricItemProperties();
			break;
		}
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void initializeClient(Consumer<IClientItemExtensions> consumer) {
		consumer.accept(new IClientItemExtensions() {
			@Override
			public HumanoidModel<?> getHumanoidArmorModel(LivingEntity entity, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel<?> properties) {
				ItemStack[] armorPiecesArray = new ItemStack[] { new ItemStack(ElectrodynamicsItems.ITEM_COMBATHELMET.get()), new ItemStack(ElectrodynamicsItems.ITEM_COMBATCHESTPLATE.get()), new ItemStack(ElectrodynamicsItems.ITEM_COMBATLEGGINGS.get()), new ItemStack(ElectrodynamicsItems.ITEM_COMBATBOOTS.get()) };

				List<ItemStack> armorPieces = new ArrayList<>();
				entity.getArmorSlots().forEach(armorPieces::add);

				boolean isBoth = armorPieces.get(0).getItem() == armorPiecesArray[3].getItem() && armorPieces.get(1).getItem() == armorPiecesArray[2].getItem();

				boolean hasChest = armorPieces.get(2).getItem() == armorPiecesArray[1].getItem();

				ModelCombatArmor<LivingEntity> model;

				if (isBoth) {
					if (hasChest) {
						model = new ModelCombatArmor<>(ElectrodynamicsClientRegister.COMBAT_ARMOR_LAYER_COMB_CHEST.bakeRoot(), slot);
					} else {
						model = new ModelCombatArmor<>(ElectrodynamicsClientRegister.COMBAT_ARMOR_LAYER_COMB_NOCHEST.bakeRoot(), slot);
					}
				} else if (slot == EquipmentSlot.FEET) {
					model = new ModelCombatArmor<>(ElectrodynamicsClientRegister.COMBAT_ARMOR_LAYER_BOOTS.bakeRoot(), slot);
				} else if (hasChest) {
					model = new ModelCombatArmor<>(ElectrodynamicsClientRegister.COMBAT_ARMOR_LAYER_LEG_CHEST.bakeRoot(), slot);
				} else {
					model = new ModelCombatArmor<>(ElectrodynamicsClientRegister.COMBAT_ARMOR_LAYER_LEG_NOCHEST.bakeRoot(), slot);
				}

				model.crouching = properties.crouching;
				model.riding = properties.riding;
				model.young = properties.young;

				return model;
			}
		});
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
		ArmorItem armor = (ArmorItem) stack.getItem();
		switch (armor.getSlot()) {
		case CHEST:
			return new RestrictedFluidHandlerItemStack(stack, ItemJetpack.MAX_CAPACITY).setValidator(ItemJetpack.getValidator());
		case FEET:
			return new RestrictedFluidHandlerItemStack(stack, ItemHydraulicBoots.MAX_CAPACITY).setValidator(ItemHydraulicBoots.getPredicate());
		default:
			return super.initCapabilities(stack, nbt);
		}
	}

	@Override
	public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> items) {
		
		if(!allowedIn(tab)) {
			return;
		}

		switch (getSlot()) {
		case HEAD, LEGS:
			ItemStack empty = new ItemStack(this);
			IItemElectric.setEnergyStored(empty, 0);
			items.add(empty);

			ItemStack charged = new ItemStack(this);
			IItemElectric.setEnergyStored(charged, getMaximumCapacity(charged));
			items.add(charged);
			break;
		case CHEST:
			items.add(new ItemStack(this));
			if (ForgeCapabilities.FLUID_HANDLER_ITEM != null) {
				ItemStack full = new ItemStack(this);

				full.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).ifPresent(h -> ((RestrictedFluidHandlerItemStack) h).fill(new FluidStack(ElectrodynamicsFluids.FLUID_HYDROGEN.get(), ItemJetpack.MAX_CAPACITY), FluidAction.EXECUTE));

				CompoundTag tag = full.getOrCreateTag();
				tag.putInt(NBTUtils.PLATES, 2);

				items.add(full);

			}
			break;
		case FEET:
			items.add(new ItemStack(this));
			if (ForgeCapabilities.FLUID_HANDLER_ITEM != null) {
				ItemStack full = new ItemStack(this);
				full.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).ifPresent(h -> ((RestrictedFluidHandlerItemStack) h).setFluid(new FluidStack(ElectrodynamicsFluids.FLUID_HYDRAULIC.get(), ItemHydraulicBoots.MAX_CAPACITY)));
				items.add(full);
			}
			break;
		default:
			break;
		}

	}

	@Override
	public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flagin) {
		super.appendHoverText(stack, level, tooltip, flagin);
		switch (((ArmorItem) stack.getItem()).getSlot()) {
		case HEAD:
			tooltip.add(ElectroTextUtils.tooltip("item.electric.info", VoltaicTextUtils.ratio(ChatFormatter.getChatDisplayShort(getJoulesStored(stack), DisplayUnits.JOULES), ChatFormatter.getChatDisplayShort(getMaximumCapacity(stack), DisplayUnits.JOULES)).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY));
            tooltip.add(ElectroTextUtils.tooltip("item.electric.voltage", ChatFormatter.getChatDisplayShort(properties.receive.getVoltage(), DisplayUnits.VOLTAGE).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY));
            IItemElectric.addBatteryTooltip(stack, level, tooltip);
			if (stack.hasTag() && stack.getTag().getBoolean(NBTUtils.ON)) {
				tooltip.add(ElectroTextUtils.tooltip("nightvisiongoggles.status").withStyle(ChatFormatting.GRAY).append(ElectroTextUtils.tooltip("nightvisiongoggles.on").withStyle(ChatFormatting.GREEN)));
			} else {
				tooltip.add(ElectroTextUtils.tooltip("nightvisiongoggles.status").withStyle(ChatFormatting.GRAY).append(ElectroTextUtils.tooltip("nightvisiongoggles.off").withStyle(ChatFormatting.RED)));
			}
			break;
		case CHEST:
			ItemJetpack.staticAppendHoverText(stack, level, tooltip, flagin);
			ItemCompositeArmor.staticAppendHoverText(stack, level, tooltip, flagin);
			break;
		case LEGS:
			tooltip.add(ElectroTextUtils.tooltip("item.electric.info", VoltaicTextUtils.ratio(ChatFormatter.getChatDisplayShort(getJoulesStored(stack), DisplayUnits.JOULES), ChatFormatter.getChatDisplayShort(getMaximumCapacity(stack), DisplayUnits.JOULES)).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY));
            tooltip.add(ElectroTextUtils.tooltip("item.electric.voltage", ChatFormatter.getChatDisplayShort(properties.receive.getVoltage(), DisplayUnits.VOLTAGE).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY));
            ItemServoLeggings.staticAppendTooltips(stack, level, tooltip, flagin);
			break;
		case FEET:
			if (ForgeCapabilities.FLUID_HANDLER_ITEM != null) {
				stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).ifPresent(handler -> tooltip.add(VoltaicTextUtils.ratio(ChatFormatter.formatFluidMilibuckets(handler.getFluidInTank(0).getAmount()), ChatFormatter.formatFluidMilibuckets(ItemHydraulicBoots.MAX_CAPACITY)).withStyle(ChatFormatting.GRAY)));
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void onArmorTick(ItemStack stack, Level world, Player player) {
		super.onArmorTick(stack, world, player);
		ItemCombatArmor combat = (ItemCombatArmor) stack.getItem();
		switch (combat.getSlot()) {
		case HEAD:
			ItemNightVisionGoggles.armorTick(stack, world, player);
			break;
		case CHEST:
			ItemJetpack.armorTick(stack, world, player, OFFSET, true);
			break;
		case LEGS:
			ItemServoLeggings.armorTick(stack, world, player);
			break;
		default:
			break;
		}
	}

	@Override
	public boolean isBarVisible(ItemStack stack) {
		ItemCombatArmor combat = (ItemCombatArmor) stack.getItem();
		switch (combat.getSlot()) {
		case HEAD, LEGS:
			return getJoulesStored(stack) < getMaximumCapacity(stack);
		case CHEST:
			return ItemJetpack.staticIsBarVisible(stack);
		case FEET:
			return ItemHydraulicBoots.staticIsBarVisible(stack);
		default:
			return false;
		}
	}

	@Override
	public int getBarWidth(ItemStack stack) {
		ItemCombatArmor combat = (ItemCombatArmor) stack.getItem();
		switch (combat.getSlot()) {
		case HEAD, LEGS:
			return (int) Math.round(13.0f * getJoulesStored(stack) / getMaximumCapacity(stack));
		case CHEST:
			return ItemJetpack.staticGetBarWidth(stack);
		case FEET:
			return ItemHydraulicBoots.staticGetBarWidth(stack);
		default:
			return 0;
		}
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
		return ARMOR_TEXTURE_LOCATION;
	}

	@Override
	public boolean canBeDepleted() {
		return false;
	}

	@Override
	public boolean isEnchantable(ItemStack stack) {
		return false;
	}

	@Override
	public boolean isValidRepairItem(ItemStack stack1, ItemStack stack2) {
		return false;
	}

	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		return slotChanged;
	}

	@Override
	public ElectricItemProperties getElectricProperties() {
		return properties;
	}

	@Override
	public boolean canElytraFly(ItemStack stack, LivingEntity entity) {
		return ItemJetpack.staticCanElytraFly(stack, entity);
	}

	@Override
	public boolean elytraFlightTick(ItemStack stack, LivingEntity entity, int flightTicks) {
		return ItemJetpack.staticElytraFlightTick(stack, entity, flightTicks);
	}

	@Override
	public Item getDefaultStorageBattery() {
		return switch (getSlot()) {
		case HEAD, LEGS -> ElectrodynamicsItems.ITEM_BATTERY.get();
		default -> Items.AIR;
		};
	}

	@Override
	public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack other, Slot slot, ClickAction action, Player player, SlotAccess access) {

		if (getSlot() == EquipmentSlot.CHEST || getSlot() == EquipmentSlot.FEET) {
			return super.overrideOtherStackedOnMe(stack, other, slot, action, player, access);
		}

		if (!IItemElectric.overrideOtherStackedOnMe(stack, other, slot, action, player, access)) {
			return super.overrideOtherStackedOnMe(stack, other, slot, action, player, access);
		}

		return true;

	}

}
