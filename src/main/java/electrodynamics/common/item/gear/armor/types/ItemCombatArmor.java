package electrodynamics.common.item.gear.armor.types;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.References;
import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.api.fluid.RestrictedFluidHandlerItemStack;
import electrodynamics.api.item.IItemElectric;
import electrodynamics.client.ClientRegister;
import electrodynamics.client.render.model.armor.types.ModelCombatArmor;
import electrodynamics.prefab.item.ElectricItemProperties;
import electrodynamics.prefab.utilities.CapabilityUtils;
import electrodynamics.prefab.utilities.NBTUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.IItemRenderProperties;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

public class ItemCombatArmor extends ArmorItem implements IItemElectric {

	public static final String ARMOR_TEXTURE_LOCATION = References.ID + ":textures/model/armor/combatarmor.png";

	private final ElectricItemProperties properties;

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
	public void initializeClient(Consumer<IItemRenderProperties> consumer) {
		consumer.accept(new IItemRenderProperties() {
			@Override
			public HumanoidModel<?> getArmorModel(LivingEntity entity, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel<?> properties) {
				ItemStack[] ARMOR_PIECES = new ItemStack[] { new ItemStack(DeferredRegisters.ITEM_COMBATHELMET.get()), new ItemStack(DeferredRegisters.ITEM_COMBATCHESTPLATE.get()), new ItemStack(DeferredRegisters.ITEM_COMBATLEGGINGS.get()), new ItemStack(DeferredRegisters.ITEM_COMBATBOOTS.get()) };

				List<ItemStack> armorPieces = new ArrayList<>();
				entity.getArmorSlots().forEach(armorPieces::add);

				boolean isBoth = ItemStack.isSameIgnoreDurability(armorPieces.get(0), ARMOR_PIECES[3]) && ItemStack.isSameIgnoreDurability(armorPieces.get(1), ARMOR_PIECES[2]);

				boolean hasChest = ItemStack.isSameIgnoreDurability(armorPieces.get(2), ARMOR_PIECES[1]);

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
			return new RestrictedFluidHandlerItemStack(stack, stack, ItemJetpack.MAX_CAPACITY, ItemJetpack.staticGetWhitelistedFluids());
		case FEET:
			return new RestrictedFluidHandlerItemStack(stack, stack, ItemHydraulicBoots.MAX_CAPACITY, ItemHydraulicBoots.staticGetWhitelistedFluids());
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
				if (!CapabilityUtils.isFluidItemNull()) {
					ItemStack full = new ItemStack(this);
					Fluid fluid = ItemJetpack.staticGetWhitelistedFluids().getSecond().get(0);
					full.getCapability(CapabilityUtils.getFluidItemCap()).ifPresent(h -> ((RestrictedFluidHandlerItemStack) h).fillInit(new FluidStack(fluid, ItemJetpack.MAX_CAPACITY)));
					full.getCapability(CapabilityUtils.getFluidItemCap()).ifPresent(h -> ((RestrictedFluidHandlerItemStack) h).hasInitHappened(true));
					CompoundTag tag = full.getOrCreateTag();
					tag.putInt(NBTUtils.PLATES, 2);
					items.add(full);
				}
				break;
			case FEET:
				items.add(new ItemStack(this));
				if (!CapabilityUtils.isFluidItemNull()) {
					ItemStack full = new ItemStack(this);
					Fluid fluid = ItemHydraulicBoots.staticGetWhitelistedFluids().getSecond().get(0);
					full.getCapability(CapabilityUtils.getFluidItemCap()).ifPresent(h -> ((RestrictedFluidHandlerItemStack) h).fillInit(new FluidStack(fluid, ItemHydraulicBoots.MAX_CAPACITY)));
					full.getCapability(CapabilityUtils.getFluidItemCap()).ifPresent(h -> ((RestrictedFluidHandlerItemStack) h).hasInitHappened(true));
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
			tooltip.add(new TranslatableComponent("tooltip.item.electric.info").withStyle(ChatFormatting.GRAY).append(new TextComponent(ChatFormatter.getChatDisplayShort(getJoulesStored(stack), DisplayUnit.JOULES))));
			tooltip.add(new TranslatableComponent("tooltip.item.electric.voltage", ChatFormatter.getChatDisplayShort(properties.receive.getVoltage(), DisplayUnit.VOLTAGE) + " / " + ChatFormatter.getChatDisplayShort(properties.extract.getVoltage(), DisplayUnit.VOLTAGE)).withStyle(ChatFormatting.RED));
			if (stack.hasTag() && stack.getTag().getBoolean(NBTUtils.ON)) {
				tooltip.add(new TranslatableComponent("tooltip.nightvisiongoggles.status").withStyle(ChatFormatting.GRAY).append(new TranslatableComponent("tooltip.nightvisiongoggles.on").withStyle(ChatFormatting.GREEN)));
			} else {
				tooltip.add(new TranslatableComponent("tooltip.nightvisiongoggles.status").withStyle(ChatFormatting.GRAY).append(new TranslatableComponent("tooltip.nightvisiongoggles.off").withStyle(ChatFormatting.RED)));
			}
			break;
		case CHEST:
			ItemJetpack.staticAppendHoverText(stack, level, tooltip, flagin);
			ItemCompositeArmor.staticAppendHoverText(stack, level, tooltip, flagin);
			break;
		case LEGS:
			tooltip.add(new TranslatableComponent("tooltip.item.electric.info").withStyle(ChatFormatting.GRAY).append(new TextComponent(ChatFormatter.getChatDisplayShort(getJoulesStored(stack), DisplayUnit.JOULES))));
			tooltip.add(new TranslatableComponent("tooltip.item.electric.voltage", ChatFormatter.getChatDisplayShort(properties.receive.getVoltage(), DisplayUnit.VOLTAGE) + " / " + ChatFormatter.getChatDisplayShort(properties.extract.getVoltage(), DisplayUnit.VOLTAGE)).withStyle(ChatFormatting.RED));
			ItemServoLeggings.staticAppendTooltips(stack, level, tooltip, flagin);
			break;
		case FEET:
			if (!CapabilityUtils.isFluidItemNull()) {
				stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent(h -> {
					tooltip.add(new TextComponent(h.getFluidInTank(0).getAmount() + " / " + ItemHydraulicBoots.MAX_CAPACITY + " mB").withStyle(ChatFormatting.GRAY));
				});
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
			ItemJetpack.armorTick(stack, world, player);
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

}
