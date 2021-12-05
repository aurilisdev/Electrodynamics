package electrodynamics.common.item.gear.tools.electric;

import java.util.List;


public class ItemElectricBaton extends SwordItem implements IItemElectric {

    private final ElectricItemProperties properties;

    public ItemElectricBaton(ElectricItemProperties properties) {
	super(ElectricItemTier.DRILL, 12, -2.4f, properties.durability(0));
	this.properties = properties;
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
	return false;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
	return getJoulesStored(stack) > properties.extract.getJoules() ? super.getAttributeModifiers(slot, stack) : ImmutableMultimap.of();
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
	return (int) Math.round(13.0f * getJoulesStored(stack) / properties.capacity);
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
	return getJoulesStored(stack) < properties.capacity;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
	super.appendHoverText(stack, worldIn, tooltip, flagIn);
	tooltip.add(new TranslatableComponent("tooltip.item.electric.info").withStyle(ChatFormatting.GRAY)
		.append(new TextComponent(ChatFormatter.getElectricDisplayShort(getJoulesStored(stack), ElectricUnit.JOULES))));
	tooltip.add(new TranslatableComponent("tooltip.item.electric.voltage",
		ChatFormatter.getElectricDisplayShort(properties.receive.getVoltage(), ElectricUnit.VOLTAGE) + " / "
			+ ChatFormatter.getElectricDisplayShort(properties.extract.getVoltage(), ElectricUnit.VOLTAGE))
				.withStyle(ChatFormatting.RED));
    }

    @Override
    public ElectricItemProperties getElectricProperties() {
	return properties;
    }

}
