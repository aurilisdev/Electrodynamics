package electrodynamics.common.item.gear.tools.electric;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

import electrodynamics.common.item.gear.tools.electric.utils.ElectricItemTier;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import voltaic.api.creativetab.CreativeTabSupplier;
import voltaic.api.electricity.formatting.ChatFormatter;
import voltaic.api.electricity.formatting.DisplayUnits;
import voltaic.api.item.IItemElectric;
import voltaic.prefab.item.ElectricItemProperties;
import voltaic.prefab.utilities.VoltaicTextUtils;

public class ItemElectricBaton extends SwordItem implements IItemElectric, CreativeTabSupplier {

    private final ElectricItemProperties properties;
    private final Supplier<CreativeModeTab> creativeTab;

    public ItemElectricBaton(ElectricItemProperties properties, Supplier<CreativeModeTab> creativeTab) {
	super(ElectricItemTier.DRILL, 12, -2.4f, properties);
	this.properties = properties;
	this.creativeTab = creativeTab;
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
	return false;
    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
	return 0;
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
	return !oldStack.is(newStack.getItem());
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
	return getJoulesStored(stack) > properties.extract.getJoules() ? super.getAttributeModifiers(slot, stack)
		: ImmutableMultimap.of();
    }

    @Override
    public void addCreativeModeItems(CreativeModeTab group, List<ItemStack> items) {

	ItemStack empty = new ItemStack(this);
	IItemElectric.setEnergyStored(empty, 0);
	items.add(empty);

	ItemStack charged = new ItemStack(this);
	IItemElectric.setEnergyStored(charged, getMaximumCapacity(charged));
	items.add(charged);

    }

    @Override
    public boolean canBeDepleted() {
	return false;
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
	extractPower(stack, properties.extract.getJoules(), false);
	return super.onLeftClickEntity(stack, player, entity);
    }

    @Override
    public int getBarWidth(ItemStack stack) {
	return (int) Math.round(13.0f * getJoulesStored(stack) / getMaximumCapacity(stack));
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
	return getJoulesStored(stack) < getMaximumCapacity(stack);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
	super.appendHoverText(stack, worldIn, tooltip, flagIn);
	tooltip.add(
		ElectroTextUtils
			.tooltip("item.electric.info",
				VoltaicTextUtils.ratio(
					ChatFormatter.getChatDisplayShort(getJoulesStored(stack), DisplayUnits.JOULES),
					ChatFormatter.getChatDisplayShort(getMaximumCapacity(stack),
						DisplayUnits.JOULES))
					.withStyle(ChatFormatting.GRAY))
			.withStyle(ChatFormatting.DARK_GRAY));
	tooltip.add(ElectroTextUtils.tooltip("item.electric.voltage",
		ChatFormatter.getChatDisplayShort(properties.receive.getVoltage(), DisplayUnits.VOLTAGE)
			.withStyle(ChatFormatting.GRAY))
		.withStyle(ChatFormatting.DARK_GRAY));
	IItemElectric.addBatteryTooltip(stack, worldIn, tooltip);
    }

    @Override
    public ElectricItemProperties getElectricProperties() {
	return properties;
    }

    @Override
    public Item getDefaultStorageBattery() {
	return ElectrodynamicsItems.ITEM_LITHIUMBATTERY.get();
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack other, Slot slot, ClickAction action,
	    Player player, SlotAccess access) {

	if (!IItemElectric.overrideOtherStackedOnMe(stack, other, slot, action, player, access)) {
	    return super.overrideOtherStackedOnMe(stack, other, slot, action, player, access);
	}

	return true;

    }

    @Override
    public boolean isAllowedInCreativeTab(CreativeModeTab tab) {
	return creativeTab.get() == tab;
    }

    @Override
    public boolean hasCreativeTab() {
	return creativeTab != null;
    }

}
