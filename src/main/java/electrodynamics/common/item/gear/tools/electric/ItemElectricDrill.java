package electrodynamics.common.item.gear.tools.electric;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.Electrodynamics;
import electrodynamics.common.inventory.container.item.ContainerElectricDrill;
import electrodynamics.common.item.ItemDrillHead;
import electrodynamics.common.item.gear.tools.electric.utils.ElectricItemTier;
import electrodynamics.common.item.subtype.SubtypeDrillHead;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import electrodynamics.registers.ElectrodynamicsItems;
import electrodynamics.registers.ElectrodynamicsSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import voltaic.api.creativetab.CreativeTabSupplier;
import voltaic.api.electricity.formatting.ChatFormatter;
import voltaic.api.electricity.formatting.DisplayUnits;
import voltaic.api.item.CapabilityItemStackHandler;
import voltaic.api.item.IItemElectric;
import voltaic.common.tags.VoltaicTags;
import voltaic.prefab.inventory.container.types.GenericContainerItem;
import voltaic.prefab.item.ElectricItemProperties;
import voltaic.prefab.utilities.VoltaicTextUtils;
import voltaic.prefab.utilities.math.Color;
import voltaic.registers.VoltaicDataComponentTypes;

public class ItemElectricDrill extends DiggerItem implements IItemElectric, CreativeTabSupplier {

    private final Holder<CreativeModeTab> creativeTab;

    private static final List<ItemElectricDrill> DRILLS = new ArrayList<>();

    private static final Component CONTAINER_TITLE = Component.translatable("container.electricdrill");

    public static final int SLOT_COUNT = 3;

    public static final double POWER_USAGE = 1666666.66667 / (120.0 * 20.0);

    private final ElectricItemProperties properties;

    public ItemElectricDrill(ElectricItemProperties properties, Holder<CreativeModeTab> creativeTab) {
	super(ElectricItemTier.ELECTRIC_DRILL, VoltaicTags.Blocks.ELECTRIC_DRILL_BLOCKS,
		properties.durability(0).attributes(createAttributes(ElectricItemTier.ELECTRIC_DRILL, 4, -2.4F)));
	this.properties = properties;
	this.creativeTab = creativeTab;
	DRILLS.add(this);
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
	return true;
    }

    @Override
    public void setDamage(ItemStack stack, int damage) {

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
    public float getDestroySpeed(ItemStack stack, BlockState state) {
	if (getJoulesStored(stack) < properties.extract.getJoules()) {
	    return 0;
	}

	float normalized = (float) Math.max(1, getHead(stack).speedBoost * getSpeedBoost(stack));

	return super.getDestroySpeed(stack, state) * normalized;

    }

    @Override
    public boolean mineBlock(ItemStack stack, Level worldIn, BlockState state, BlockPos pos,
	    LivingEntity entityLiving) {

	IItemElectric.setEnergyStored(stack,
		getJoulesStored(stack) - stack.getOrDefault(VoltaicDataComponentTypes.POWER_USAGE, POWER_USAGE));

	return super.mineBlock(stack, worldIn, state, pos, entityLiving);
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
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
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

	IItemElectric.addBatteryTooltip(stack, context, tooltip);
	tooltip.add(ElectroTextUtils.tooltip("electricdrill.miningspeed",
		ChatFormatter.getChatDisplayShort(getHead(stack).speedBoost * 100, DisplayUnits.PERCENTAGE)
			.withStyle(ChatFormatting.GRAY))
		.withStyle(ChatFormatting.DARK_GRAY));
	tooltip.add(ElectroTextUtils
		.tooltip("electricdrill.usage",
			ChatFormatter.getChatDisplayShort(
				stack.getOrDefault(VoltaicDataComponentTypes.POWER_USAGE, POWER_USAGE),
				DisplayUnits.JOULES).withStyle(ChatFormatting.GRAY))
		.withStyle(ChatFormatting.DARK_GRAY));

	tooltip.add(ElectroTextUtils.tooltip("electricdrill.overclock",
		ChatFormatter.getChatDisplayShort(getSpeedBoost(stack) * 100, DisplayUnits.PERCENTAGE)
			.withStyle(ChatFormatting.GRAY))
		.withStyle(ChatFormatting.DARK_GRAY));
	super.appendHoverText(stack, context, tooltip, flagIn);
    }

    @Override
    public ElectricItemProperties getElectricProperties() {
	return properties;
    }

    @Override
    public Item getDefaultStorageBattery() {
	return ElectrodynamicsItems.ITEM_BATTERY.get();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {

	if (!level.isClientSide) {

	    player.openMenu(getMenuProvider(level, player, player.getItemInHand(hand), hand));

	}

	return super.use(level, player, hand);
    }

    public MenuProvider getMenuProvider(Level world, Player player, ItemStack stack, InteractionHand hand) {
	return new SimpleMenuProvider((id, inv, play) -> {
	    CapabilityItemStackHandler handler = (CapabilityItemStackHandler) stack
		    .getCapability(Capabilities.ItemHandler.ITEM);
	    if (handler == null) {
		handler = new CapabilityItemStackHandler(SLOT_COUNT, stack);
	    }
	    return new ContainerElectricDrill(id, player.getInventory(), handler, GenericContainerItem.makeData(hand));
	}, CONTAINER_TITLE);
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack other, Slot slot, ClickAction action,
	    Player player, SlotAccess access) {

	if (!other.isEmpty() && other.getItem() instanceof ItemDrillHead head) {

	    ItemStack oldHead = new ItemStack(ElectrodynamicsItems.ITEMS_DRILLHEAD.getValue(getHead(stack)));

	    saveHead(stack, head.head);

	    access.set(oldHead);

	    player.level().playLocalSound(player.getX(), player.getY(), player.getZ(),
		    ElectrodynamicsSounds.SOUND_BATTERY_SWAP.get(), SoundSource.PLAYERS, 0.25F, 1.0F, false);

	    return true;

	}

	if (!IItemElectric.overrideOtherStackedOnMe(stack, other, slot, action, player, access)) {
	    return super.overrideOtherStackedOnMe(stack, other, slot, action, player, access);
	}

	return true;

    }

    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {

    }

    public static double getSpeedBoost(ItemStack stack) {
	return stack.getOrDefault(VoltaicDataComponentTypes.SPEED, 0.0);
    }

    public static SubtypeDrillHead getHead(ItemStack stack) {
	return SubtypeDrillHead.values()[stack.getOrDefault(VoltaicDataComponentTypes.ENUM, 0)];
    }

    public static void saveHead(ItemStack stack, SubtypeDrillHead head) {
	stack.set(VoltaicDataComponentTypes.ENUM, head.ordinal());
    }

    @EventBusSubscriber(value = Dist.CLIENT, modid = Electrodynamics.ID, bus = EventBusSubscriber.Bus.MOD)
    private static class ColorHandler {

	@SubscribeEvent
	public static void registerColoredBlocks(RegisterColorHandlersEvent.Item event) {
	    DRILLS.forEach(item -> event.register((stack, index) -> {
		if (index == 1) {
		    return getHead(stack).color.color();
		}
		return Color.WHITE.color();
	    }, item));
	}

    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
	return !oldStack.is(newStack.getItem());
    }

    @Override
    public boolean isAllowedInCreativeTab(CreativeModeTab tab) {
	return creativeTab.value() == tab;
    }

    @Override
    public boolean hasCreativeTab() {
	return creativeTab != null;
    }

}
