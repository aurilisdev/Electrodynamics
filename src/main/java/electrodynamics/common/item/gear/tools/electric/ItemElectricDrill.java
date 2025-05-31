package electrodynamics.common.item.gear.tools.electric;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import org.jetbrains.annotations.Nullable;

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
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import voltaic.api.electricity.formatting.ChatFormatter;
import voltaic.api.electricity.formatting.DisplayUnits;
import voltaic.api.item.CapabilityItemStackHandler;
import voltaic.api.item.IItemElectric;
import voltaic.common.item.ItemUpgrade;
import voltaic.prefab.inventory.container.types.GenericContainerItem;
import voltaic.prefab.item.ElectricItemProperties;
import voltaic.prefab.item.ItemMultiDigger;
import voltaic.prefab.utilities.CapabilityUtils;
import voltaic.prefab.utilities.ItemUtils;
import voltaic.prefab.utilities.NBTUtils;
import voltaic.prefab.utilities.VoltaicTextUtils;
import voltaic.prefab.utilities.math.Color;

public class ItemElectricDrill extends ItemMultiDigger implements IItemElectric {

	private final Supplier<CreativeModeTab> creativeTab;

	private static final List<ItemElectricDrill> DRILLS = new ArrayList<>();

	private static final Component CONTAINER_TITLE = new TranslatableComponent("container.electricdrill");

	public static final int SLOT_COUNT = 3;

	public static final double POWER_USAGE = 1666666.66667 / (120.0 * 20.0);

	private static final String SUBTYPE = "subtype";
	private final ElectricItemProperties properties;

	public ItemElectricDrill(ElectricItemProperties properties, Supplier<CreativeModeTab> creativeTab) {
		super(4, -2.4f, ElectricItemTier.DRILL, properties.durability(0), BlockTags.MINEABLE_WITH_SHOVEL, BlockTags.MINEABLE_WITH_PICKAXE);
		this.properties = properties;
		this.creativeTab = creativeTab;
		DRILLS.add(this);
	}
	
	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		return !oldStack.is(newStack.getItem());
	}

	@Override
	public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
		return new CapabilityItemStackHandler(SLOT_COUNT, stack).setOnChange((item, cap, slot) -> {

			int fortune = 0;
			boolean silkTouch = false;
			double speedBoost = 1;

			for (ItemStack content : cap.getItems()) {
				if (!content.isEmpty() && content.getItem() instanceof ItemUpgrade upgrade && upgrade.subtype.isEmpty) {
					for (int i = 0; i < content.getCount(); i++) {

						switch (upgrade.subtype) {

						case basicspeed:
							speedBoost = Math.min(speedBoost * 1.5, Math.pow(1.5, 3));
							break;
						case advancedspeed:
							speedBoost = Math.min(speedBoost * 2.25, Math.pow(2.25, 3));
							break;
						case fortune:

							if (!silkTouch) {
								fortune = Math.min(fortune + 1, 9);
							}
							break;
						case silktouch:
							if (fortune == 0) {
								silkTouch = true;
							}
							break;
						default:
							break;
						}
					}
				}
			}

			CompoundTag tag = stack.getOrCreateTag();

			ItemUtils.removeEnchantment(item, Enchantments.BLOCK_FORTUNE);

			if (fortune > 0) {
				stack.enchant(Enchantments.BLOCK_FORTUNE, fortune);
			}

			ItemUtils.removeEnchantment(item, Enchantments.SILK_TOUCH);

			if (silkTouch) {
				stack.enchant(Enchantments.SILK_TOUCH, 1);
			}

			tag.putDouble(NBTUtils.SPEED_ENCHANT, speedBoost);

		});
	}

	@Override
	public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
		return true;
	}
	
	@Override
	protected boolean allowdedIn(CreativeModeTab category) {
		return creativeTab != null && (category == creativeTab.get() || category == CreativeModeTab.TAB_SEARCH);
	}

	@Override
	public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
		
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
	public float getDestroySpeed(ItemStack stack, BlockState state) {
		if (getJoulesStored(stack) < properties.extract.getJoules()) {
			return 0;
		}

		float normalized = (float) Math.max(1, getHead(stack).speedBoost * getSpeedBoost(stack));

		return super.getDestroySpeed(stack, state) * normalized;

	}

	@Override
	public boolean mineBlock(ItemStack stack, Level worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {

		IItemElectric.setEnergyStored(stack, getJoulesStored(stack) - getPowerUsage(stack));

		// extractPower(stack, properties.extract.getJoules() * multiplier, false);
		return super.mineBlock(stack, worldIn, state, pos, entityLiving);
	}

	public double getPowerUsage(ItemStack stack) {
		double multiplier = Math.max(1, getSpeedBoost(stack));

		if (EnchantmentHelper.getEnchantments(stack).getOrDefault(Enchantments.SILK_TOUCH, 0) > 0) {
			multiplier += 3;
		}

		int fortune = EnchantmentHelper.getEnchantments(stack).getOrDefault(Enchantments.BLOCK_FORTUNE, 0);

		if (fortune > 0) {
			multiplier += fortune;
		}

		return POWER_USAGE * multiplier;
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
		tooltip.add(ElectroTextUtils.tooltip("item.electric.info", VoltaicTextUtils.ratio(ChatFormatter.getChatDisplayShort(getJoulesStored(stack), DisplayUnits.JOULES), ChatFormatter.getChatDisplayShort(getMaximumCapacity(stack), DisplayUnits.JOULES)).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY));
        tooltip.add(ElectroTextUtils.tooltip("item.electric.voltage", ChatFormatter.getChatDisplayShort(properties.receive.getVoltage(), DisplayUnits.VOLTAGE).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY));

        IItemElectric.addBatteryTooltip(stack, worldIn, tooltip);
        tooltip.add(ElectroTextUtils.tooltip("electricdrill.miningspeed", ChatFormatter.getChatDisplayShort(getHead(stack).speedBoost * 100, DisplayUnits.PERCENTAGE).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY));
        tooltip.add(ElectroTextUtils.tooltip("electricdrill.usage", ChatFormatter.getChatDisplayShort(getPowerUsage(stack), DisplayUnits.JOULES).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY));

        tooltip.add(ElectroTextUtils.tooltip("electricdrill.overclock", ChatFormatter.getChatDisplayShort(getSpeedBoost(stack) * 100, DisplayUnits.PERCENTAGE).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY));

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
			IItemHandler capability = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(CapabilityUtils.EMPTY_ITEM_HANDLER);
			CapabilityItemStackHandler handler = new CapabilityItemStackHandler(SLOT_COUNT, stack);
			if (capability != CapabilityUtils.EMPTY_ITEM_HANDLER) {
				handler = (CapabilityItemStackHandler) capability;
			}
			return new ContainerElectricDrill(id, player.getInventory(), handler, GenericContainerItem.makeData(hand));
		}, CONTAINER_TITLE);
	}

	@Override
	public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack other, Slot slot, ClickAction action, Player player, SlotAccess access) {

		if (!other.isEmpty() && other.getItem() instanceof ItemDrillHead head) {

			ItemStack oldHead = new ItemStack(ElectrodynamicsItems.ITEMS_DRILLHEAD.getValue(getHead(stack)));

			saveHead(stack, head.head);

			access.set(oldHead);

			player.level.playLocalSound(player.getX(), player.getY(), player.getZ(), ElectrodynamicsSounds.SOUND_BATTERY_SWAP.get(), SoundSource.PLAYERS, 0.25F, 1.0F, false);

			return true;

		}

		if (!IItemElectric.overrideOtherStackedOnMe(stack, other, slot, action, player, access)) {
			return super.overrideOtherStackedOnMe(stack, other, slot, action, player, access);
		}

		return true;

	}

	public static double getSpeedBoost(ItemStack stack) {
		return stack.getOrCreateTag().getDouble(NBTUtils.SPEED_ENCHANT);
	}

	public static SubtypeDrillHead getHead(ItemStack stack) {
		return SubtypeDrillHead.values()[stack.getOrCreateTag().getInt(SUBTYPE)];
	}

	public static void saveHead(ItemStack stack, SubtypeDrillHead head) {
		stack.getOrCreateTag().putInt(SUBTYPE, head.ordinal());
	}

	@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Electrodynamics.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
	private static class ColorHandler {

		@SubscribeEvent
		public static void registerColoredBlocks(ColorHandlerEvent.Item event) {
			DRILLS.forEach(item -> event.getItemColors().register((stack, index) -> {
				if (index == 1) {
					return getHead(stack).color.color();
				}
				return Color.WHITE.color();
			}, item));
		}

	}


}
