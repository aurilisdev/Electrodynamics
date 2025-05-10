package electrodynamics.common.item.gear.tools.electric;

import java.util.List;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

import electrodynamics.common.item.gear.tools.electric.utils.ElectricItemTier;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import voltaic.api.electricity.formatting.ChatFormatter;
import voltaic.api.electricity.formatting.DisplayUnits;
import voltaic.api.item.IItemElectric;
import voltaic.prefab.item.ElectricItemProperties;
import voltaic.prefab.utilities.VoltaicTextUtils;

public class ItemElectricBaton extends SwordItem implements IItemElectric {

	private final ElectricItemProperties properties;
	private final Supplier<ItemGroup> creativeTab;

	public ItemElectricBaton(ElectricItemProperties properties, Supplier<ItemGroup> creativeTab) {
		super(ElectricItemTier.DRILL, 12, -2.4f, properties.durability(0));
		this.properties = properties;
		this.creativeTab = creativeTab;
	}

	@Override
	public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
		return false;
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
		return getJoulesStored(stack) > properties.extract.getJoules() ? super.getAttributeModifiers(slot, stack) : ImmutableMultimap.of();
	}
	
	@Override
	protected boolean allowdedIn(ItemGroup category) {
		return creativeTab != null && (category == creativeTab.get() || category == ItemGroup.TAB_SEARCH);
	}

	@Override
	public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) {
		
		if(!allowdedIn(group)) {
			return;
		}

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
	public boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity) {
		extractPower(stack, properties.extract.getJoules(), false);
		return super.onLeftClickEntity(stack, player, entity);
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		return 1.0D - (getJoulesStored(stack) / getMaximumCapacity(stack));
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return getJoulesStored(stack) < getMaximumCapacity(stack);
	}

	@Override
	public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		tooltip.add(ElectroTextUtils.tooltip("item.electric.info", VoltaicTextUtils.ratio(ChatFormatter.getChatDisplayShort(getJoulesStored(stack), DisplayUnits.JOULES), ChatFormatter.getChatDisplayShort(getMaximumCapacity(stack), DisplayUnits.JOULES)).withStyle(TextFormatting.GRAY)).withStyle(TextFormatting.DARK_GRAY));
		tooltip.add(ElectroTextUtils.tooltip("item.electric.voltage", ChatFormatter.getChatDisplayShort(properties.receive.getVoltage(), DisplayUnits.VOLTAGE).withStyle(TextFormatting.GRAY)).withStyle(TextFormatting.DARK_GRAY));
	}

	@Override
	public ElectricItemProperties getElectricProperties() {
		return properties;
	}

}
