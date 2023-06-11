package electrodynamics.common.item.gear.armor.types;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import electrodynamics.api.References;
import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.api.capability.types.fluid.RestrictedFluidHandlerItemStack;
import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.api.gas.Gas;
import electrodynamics.api.gas.GasAction;
import electrodynamics.api.gas.GasHandlerItemStack;
import electrodynamics.api.gas.GasStack;
import electrodynamics.api.item.IItemElectric;
import electrodynamics.client.ClientRegister;
import electrodynamics.client.render.model.armor.types.ModelCombatArmor;
import electrodynamics.prefab.item.ElectricItemProperties;
import electrodynamics.prefab.utilities.CapabilityUtils;
import electrodynamics.prefab.utilities.NBTUtils;
import electrodynamics.prefab.utilities.TextUtils;
import electrodynamics.registers.ElectrodynamicsFluids;
import electrodynamics.registers.ElectrodynamicsGases;
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

public class ItemCombatArmor extends ArmorItem implements IItemElectric {

	public static final String ARMOR_TEXTURE_LOCATION = References.ID + ":textures/model/armor/combatarmor.png";

	private final ElectricItemProperties properties;

	public static final float OFFSET = 0.2F;

	public ItemCombatArmor(Properties properties, EquipmentSlot slot) {
		super(ItemCompositeArmor.CompositeArmor.COMPOSITE_ARMOR, slot, properties);
		switch (slot) {
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

				boolean isBoth = ItemStack.isSameIgnoreDurability(armorPieces.get(0), armorPiecesArray[3]) && ItemStack.isSameIgnoreDurability(armorPieces.get(1), armorPiecesArray[2]);

				boolean hasChest = ItemStack.isSameIgnoreDurability(armorPieces.get(2), armorPiecesArray[1]);

				ModelCombatArmor<LivingEntity> model;

				if (isBoth) {
					if (hasChest) {
						model = new ModelCombatArmor<>(ClientRegister.COMBAT_ARMOR_LAYER_COMB_CHEST.bakeRoot(), slot);
					} else {
						model = new ModelCombatArmor<>(ClientRegister.COMBAT_ARMOR_LAYER_COMB_NOCHEST.bakeRoot(), slot);
					}
				} else if (slot == EquipmentSlot.FEET) {
					model = new ModelCombatArmor<>(ClientRegister.COMBAT_ARMOR_LAYER_BOOTS.bakeRoot(), slot);
				} else if (hasChest) {
					model = new ModelCombatArmor<>(ClientRegister.COMBAT_ARMOR_LAYER_LEG_CHEST.bakeRoot(), slot);
				} else {
					model = new ModelCombatArmor<>(ClientRegister.COMBAT_ARMOR_LAYER_LEG_NOCHEST.bakeRoot(), slot);
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
			return new GasHandlerItemStack(stack, ItemJetpack.MAX_CAPACITY, ItemJetpack.MAX_TEMPERATURE, ItemJetpack.MAX_PRESSURE).setPredicate(ItemJetpack.getGasValidator());
		case FEET:
			return new RestrictedFluidHandlerItemStack(stack, ItemHydraulicBoots.MAX_CAPACITY).setValidator(ItemHydraulicBoots.getPredicate());
		default:
			return super.initCapabilities(stack, nbt);
		}
	}

	@Override
	public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> items) {
		if (tab == References.CORETAB) {
			switch (getSlot()) {
			case HEAD, LEGS:
				ItemStack empty = new ItemStack(this);
				IItemElectric.setEnergyStored(empty, 0);
				items.add(empty);

				ItemStack charged = new ItemStack(this);
				IItemElectric.setEnergyStored(charged, properties.capacity);
				items.add(charged);
				break;
			case CHEST:
				items.add(new ItemStack(this));
				if (!CapabilityUtils.isGasItemNull()) {
					ItemStack full = new ItemStack(this);
					
					GasStack gas = new GasStack(ElectrodynamicsGases.HYDROGEN.get(), ItemJetpack.MAX_CAPACITY, Gas.ROOM_TEMPERATURE, Gas.PRESSURE_AT_SEA_LEVEL);
					
					full.getCapability(ElectrodynamicsCapabilities.GAS_HANDLER_ITEM).ifPresent(cap -> cap.fillTank(0, gas, GasAction.EXECUTE));

					CompoundTag tag = full.getOrCreateTag();
					tag.putInt(NBTUtils.PLATES, 2);
					
					items.add(full);

				}
				break;
			case FEET:
				items.add(new ItemStack(this));
				if (!CapabilityUtils.isFluidItemNull()) {
					ItemStack full = new ItemStack(this);
					full.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).ifPresent(h -> ((RestrictedFluidHandlerItemStack) h).setFluid(new FluidStack(ElectrodynamicsFluids.fluidHydraulic, ItemHydraulicBoots.MAX_CAPACITY)));
					items.add(full);
				}
				break;
			default:
				break;
			}
		}
	}

	@Override
	public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flagin) {
		super.appendHoverText(stack, level, tooltip, flagin);
		switch (((ArmorItem) stack.getItem()).getSlot()) {
		case HEAD:
			tooltip.add(TextUtils.tooltip("item.electric.info").withStyle(ChatFormatting.GRAY).append(Component.literal(ChatFormatter.getChatDisplayShort(getJoulesStored(stack), DisplayUnit.JOULES))));
			tooltip.add(TextUtils.tooltip("item.electric.voltage", ChatFormatter.getChatDisplayShort(properties.receive.getVoltage(), DisplayUnit.VOLTAGE) + " / " + ChatFormatter.getChatDisplayShort(properties.extract.getVoltage(), DisplayUnit.VOLTAGE)).withStyle(ChatFormatting.RED));
			if (stack.hasTag() && stack.getTag().getBoolean(NBTUtils.ON)) {
				tooltip.add(TextUtils.tooltip("nightvisiongoggles.status").withStyle(ChatFormatting.GRAY).append(TextUtils.tooltip("nightvisiongoggles.on").withStyle(ChatFormatting.GREEN)));
			} else {
				tooltip.add(TextUtils.tooltip("nightvisiongoggles.status").withStyle(ChatFormatting.GRAY).append(TextUtils.tooltip("nightvisiongoggles.off").withStyle(ChatFormatting.RED)));
			}
			IItemElectric.addBatteryTooltip(stack, level, tooltip);
			break;
		case CHEST:
			ItemJetpack.staticAppendHoverText(stack, level, tooltip, flagin);
			ItemCompositeArmor.staticAppendHoverText(stack, level, tooltip, flagin);
			break;
		case LEGS:
			tooltip.add(TextUtils.tooltip("item.electric.info").withStyle(ChatFormatting.GRAY).append(Component.literal(ChatFormatter.getChatDisplayShort(getJoulesStored(stack), DisplayUnit.JOULES))));
			tooltip.add(TextUtils.tooltip("item.electric.voltage", ChatFormatter.getChatDisplayShort(properties.receive.getVoltage(), DisplayUnit.VOLTAGE) + " / " + ChatFormatter.getChatDisplayShort(properties.extract.getVoltage(), DisplayUnit.VOLTAGE)).withStyle(ChatFormatting.RED));
			ItemServoLeggings.staticAppendTooltips(stack, level, tooltip, flagin);
			//IItemElectric.addBatteryTooltip(stack, level, tooltip);
			break;
		case FEET:
			if (!CapabilityUtils.isFluidItemNull()) {
				stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).ifPresent(h -> tooltip.add(Component.literal(h.getFluidInTank(0).getAmount() + " / " + ItemHydraulicBoots.MAX_CAPACITY + " mB").withStyle(ChatFormatting.GRAY)));
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
			return getJoulesStored(stack) < properties.capacity;
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
			return (int) Math.round(13.0f * getJoulesStored(stack) / properties.capacity);
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
		return switch(getSlot()) {
		case HEAD, LEGS -> ElectrodynamicsItems.ITEM_BATTERY.get();
		default -> Items.AIR;
		};
	}
	
	@Override
	public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack other, Slot slot, ClickAction action, Player player, SlotAccess access) {
		
		if(getSlot() == EquipmentSlot.CHEST || getSlot() == EquipmentSlot.FEET) {
			return super.overrideOtherStackedOnMe(stack, other, slot, action, player, access);
		}
		
		if(!IItemElectric.overrideOtherStackedOnMe(stack, other, slot, action, player, access)) {
			return super.overrideOtherStackedOnMe(stack, other, slot, action, player, access);
		}
		
		return true;
		
	}

}
