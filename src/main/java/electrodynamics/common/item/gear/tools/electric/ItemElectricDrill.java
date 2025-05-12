package electrodynamics.common.item.gear.tools.electric;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableSet;

import electrodynamics.Electrodynamics;
import electrodynamics.common.inventory.container.item.ContainerElectricDrill;
import electrodynamics.common.item.ItemDrillHead;
import electrodynamics.common.item.gear.tools.electric.utils.ElectricItemTier;
import electrodynamics.common.item.subtype.SubtypeDrillHead;
import electrodynamics.prefab.capability.CapabilityItemStackHandlerElectricDrill;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.common.ToolType;
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
import voltaic.prefab.utilities.CapabilityUtils;
import voltaic.prefab.utilities.ItemUtils;
import voltaic.prefab.utilities.NBTUtils;
import voltaic.prefab.utilities.VoltaicTextUtils;
import voltaic.prefab.utilities.math.Color;

public class ItemElectricDrill extends ToolItem implements IItemElectric {

	private static final List<ItemElectricDrill> DRILLS = new ArrayList<>();

	private static final ITextComponent CONTAINER_TITLE = new TranslationTextComponent("container.electricdrill");

	public static final int SLOT_COUNT = 3;

	public static final double POWER_USAGE = 1666666.66667 / (120.0 * 20.0);

	private static final String SUBTYPE = "subtype";

	private static final Set<Block> EFFECTIVE_ON = ImmutableSet.of(Blocks.ACTIVATOR_RAIL, Blocks.COAL_ORE, Blocks.COBBLESTONE, Blocks.DETECTOR_RAIL, Blocks.DIAMOND_BLOCK, Blocks.DIAMOND_ORE, Blocks.POWERED_RAIL, Blocks.GOLD_BLOCK, Blocks.GOLD_ORE, Blocks.NETHER_GOLD_ORE, Blocks.ICE, Blocks.IRON_BLOCK, Blocks.IRON_ORE, Blocks.LAPIS_BLOCK, Blocks.LAPIS_ORE, Blocks.MOSSY_COBBLESTONE,
			Blocks.NETHERRACK, Blocks.PACKED_ICE, Blocks.BLUE_ICE, Blocks.RAIL, Blocks.REDSTONE_ORE, Blocks.SANDSTONE, Blocks.CHISELED_SANDSTONE, Blocks.CUT_SANDSTONE, Blocks.CHISELED_RED_SANDSTONE, Blocks.CUT_RED_SANDSTONE, Blocks.RED_SANDSTONE, Blocks.STONE, Blocks.GRANITE, Blocks.POLISHED_GRANITE, Blocks.DIORITE, Blocks.POLISHED_DIORITE, Blocks.ANDESITE, Blocks.POLISHED_ANDESITE,
			Blocks.STONE_SLAB, Blocks.SMOOTH_STONE_SLAB, Blocks.SANDSTONE_SLAB, Blocks.PETRIFIED_OAK_SLAB, Blocks.COBBLESTONE_SLAB, Blocks.BRICK_SLAB, Blocks.STONE_BRICK_SLAB, Blocks.NETHER_BRICK_SLAB, Blocks.QUARTZ_SLAB, Blocks.RED_SANDSTONE_SLAB, Blocks.PURPUR_SLAB, Blocks.SMOOTH_QUARTZ, Blocks.SMOOTH_RED_SANDSTONE, Blocks.SMOOTH_SANDSTONE, Blocks.SMOOTH_STONE, Blocks.STONE_BUTTON,
			Blocks.STONE_PRESSURE_PLATE, Blocks.POLISHED_GRANITE_SLAB, Blocks.SMOOTH_RED_SANDSTONE_SLAB, Blocks.MOSSY_STONE_BRICK_SLAB, Blocks.POLISHED_DIORITE_SLAB, Blocks.MOSSY_COBBLESTONE_SLAB, Blocks.END_STONE_BRICK_SLAB, Blocks.SMOOTH_SANDSTONE_SLAB, Blocks.SMOOTH_QUARTZ_SLAB, Blocks.GRANITE_SLAB, Blocks.ANDESITE_SLAB, Blocks.RED_NETHER_BRICK_SLAB, Blocks.POLISHED_ANDESITE_SLAB,
			Blocks.DIORITE_SLAB, Blocks.SHULKER_BOX, Blocks.BLACK_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.LIGHT_GRAY_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX,
			Blocks.RED_SHULKER_BOX, Blocks.WHITE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX, Blocks.PISTON, Blocks.STICKY_PISTON, Blocks.PISTON_HEAD, Blocks.CLAY, Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.PODZOL, Blocks.FARMLAND, Blocks.GRASS_BLOCK, Blocks.GRAVEL, Blocks.MYCELIUM, Blocks.SAND, Blocks.RED_SAND, Blocks.SNOW_BLOCK, Blocks.SNOW, Blocks.SOUL_SAND, Blocks.GRASS_PATH,
			Blocks.WHITE_CONCRETE_POWDER, Blocks.ORANGE_CONCRETE_POWDER, Blocks.MAGENTA_CONCRETE_POWDER, Blocks.LIGHT_BLUE_CONCRETE_POWDER, Blocks.YELLOW_CONCRETE_POWDER, Blocks.LIME_CONCRETE_POWDER, Blocks.PINK_CONCRETE_POWDER, Blocks.GRAY_CONCRETE_POWDER, Blocks.LIGHT_GRAY_CONCRETE_POWDER, Blocks.CYAN_CONCRETE_POWDER, Blocks.PURPLE_CONCRETE_POWDER, Blocks.BLUE_CONCRETE_POWDER,
			Blocks.BROWN_CONCRETE_POWDER, Blocks.GREEN_CONCRETE_POWDER, Blocks.RED_CONCRETE_POWDER, Blocks.BLACK_CONCRETE_POWDER, Blocks.SOUL_SOIL);

	private final ElectricItemProperties properties;
	private final Supplier<ItemGroup> creativeTab;

	public ItemElectricDrill(ElectricItemProperties properties, Supplier<ItemGroup> creativeTab) {
		super(4, -2.4f, ElectricItemTier.DRILL, EFFECTIVE_ON, properties.addToolType(ToolType.PICKAXE, ElectricItemTier.DRILL.getLevel()).addToolType(ToolType.SHOVEL, ElectricItemTier.DRILL.getLevel()));
		this.properties = properties;
		this.creativeTab = creativeTab;
		DRILLS.add(this);
	}

	@Override
	public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
		return new CapabilityItemStackHandlerElectricDrill(SLOT_COUNT, stack).setOnChange((item, cap, slot) -> {
			
			
			int fortune = 0;
			boolean silkTouch = false;
			double speedBoost = 1;
			
			for (ItemStack content : cap.getItems().subList(1, SLOT_COUNT)) {
				if (!content.isEmpty() && content.getItem() instanceof ItemUpgrade) {
					ItemUpgrade upgrade = (ItemUpgrade) content.getItem();
					if (upgrade.subtype.isEmpty) {
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
			}

			CompoundNBT tag = stack.getOrCreateTag();

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
	protected boolean allowdedIn(ItemGroup category) {
		return creativeTab != null && (category == creativeTab.get() || category == ItemGroup.TAB_SEARCH);
	}

	@Override
	public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) {

		if (!allowdedIn(group)) {
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
	public boolean mineBlock(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
		IItemElectric.setEnergyStored(stack, getJoulesStored(stack) - getPowerUsage(stack));
		int i = getTier().getLevel();
		if (state.getHarvestTool() == ToolType.PICKAXE) {
			return i >= state.getHarvestLevel();
		}
		Material material = state.getMaterial();
		return material == Material.STONE || material == Material.METAL || material == Material.HEAVY_METAL || state.is(Blocks.SNOW) || state.is(Blocks.SNOW_BLOCK);
	}

	@Override
	public float getDestroySpeed(ItemStack stack, BlockState state) {

		float normalized = (float) Math.max(1, getHead(stack).speedBoost * getSpeedBoost(stack));

		Material material = state.getMaterial();

		return getJoulesStored(stack) > properties.extract.getJoules() ? material != Material.METAL && material != Material.HEAVY_METAL && material != Material.STONE ? super.getDestroySpeed(stack, state) : speed * normalized : 0;
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		return 1.0D - (getJoulesStored(stack) / properties.capacity);
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return getJoulesStored(stack) < properties.capacity;
	}

	@Override
	public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		tooltip.add(ElectroTextUtils.tooltip("item.electric.info", VoltaicTextUtils.ratio(ChatFormatter.getChatDisplayShort(getJoulesStored(stack), DisplayUnits.JOULES), ChatFormatter.getChatDisplayShort(getMaximumCapacity(stack), DisplayUnits.JOULES)).withStyle(TextFormatting.GRAY)).withStyle(TextFormatting.DARK_GRAY));
		tooltip.add(ElectroTextUtils.tooltip("item.electric.voltage", ChatFormatter.getChatDisplayShort(properties.receive.getVoltage(), DisplayUnits.VOLTAGE).withStyle(TextFormatting.GRAY)).withStyle(TextFormatting.DARK_GRAY));

		tooltip.add(ElectroTextUtils.tooltip("electricdrill.miningspeed", ChatFormatter.getChatDisplayShort(getHead(stack).speedBoost * 100, DisplayUnits.PERCENTAGE).withStyle(TextFormatting.GRAY)).withStyle(TextFormatting.DARK_GRAY));
		tooltip.add(ElectroTextUtils.tooltip("electricdrill.usage", ChatFormatter.getChatDisplayShort(getPowerUsage(stack), DisplayUnits.JOULES).withStyle(TextFormatting.GRAY)).withStyle(TextFormatting.DARK_GRAY));

		tooltip.add(ElectroTextUtils.tooltip("electricdrill.overclock", ChatFormatter.getChatDisplayShort(getSpeedBoost(stack) * 100, DisplayUnits.PERCENTAGE).withStyle(TextFormatting.GRAY)).withStyle(TextFormatting.DARK_GRAY));
	}

	@Override
	public ElectricItemProperties getElectricProperties() {
		return properties;
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
	public ActionResult<ItemStack> use(World level, PlayerEntity player, Hand hand) {

		if (!level.isClientSide) {

			player.openMenu(getMenuProvider(level, player, player.getItemInHand(hand), hand));

		}

		return super.use(level, player, hand);
	}

	public INamedContainerProvider getMenuProvider(World world, PlayerEntity player, ItemStack stack, Hand hand) {
		return new SimpleNamedContainerProvider((id, inv, play) -> {
			IItemHandler capability = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(CapabilityUtils.EMPTY_ITEM_HANDLER);
			CapabilityItemStackHandler handler = new CapabilityItemStackHandler(SLOT_COUNT, stack);
			if (capability != CapabilityUtils.EMPTY_ITEM_HANDLER) {
				handler = (CapabilityItemStackHandler) capability;
			}
			return new ContainerElectricDrill(id, player.inventory, handler, GenericContainerItem.makeData(hand));
		}, CONTAINER_TITLE);
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
