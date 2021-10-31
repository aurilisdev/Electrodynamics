package electrodynamics.common.item.gear.tools.electric;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.ElectricUnit;
import electrodynamics.api.item.IItemElectric;
import electrodynamics.common.item.gear.tools.electric.utils.ElectricItemTier;
import electrodynamics.prefab.item.ElectricItemProperties;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.tags.Tag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

public class ItemElectricDrill extends DiggerItem implements IItemElectric {
    /*
     * private static final Set<Block> EFFECTIVE_ON = Set.of(Blocks.ACTIVATOR_RAIL,
     * Blocks.COAL_ORE, Blocks.COBBLESTONE, Blocks.DETECTOR_RAIL,
     * Blocks.DIAMOND_BLOCK, Blocks.DIAMOND_ORE, Blocks.POWERED_RAIL,
     * Blocks.GOLD_BLOCK, Blocks.GOLD_ORE, Blocks.NETHER_GOLD_ORE, Blocks.ICE,
     * Blocks.IRON_BLOCK, Blocks.IRON_ORE, Blocks.LAPIS_BLOCK, Blocks.LAPIS_ORE,
     * Blocks.MOSSY_COBBLESTONE, Blocks.NETHERRACK, Blocks.PACKED_ICE,
     * Blocks.BLUE_ICE, Blocks.RAIL, Blocks.REDSTONE_ORE, Blocks.SANDSTONE,
     * Blocks.CHISELED_SANDSTONE, Blocks.CUT_SANDSTONE,
     * Blocks.CHISELED_RED_SANDSTONE, Blocks.CUT_RED_SANDSTONE,
     * Blocks.RED_SANDSTONE, Blocks.STONE, Blocks.GRANITE, Blocks.POLISHED_GRANITE,
     * Blocks.DIORITE, Blocks.POLISHED_DIORITE, Blocks.ANDESITE,
     * Blocks.POLISHED_ANDESITE, Blocks.STONE_SLAB, Blocks.SMOOTH_STONE_SLAB,
     * Blocks.SANDSTONE_SLAB, Blocks.PETRIFIED_OAK_SLAB, Blocks.COBBLESTONE_SLAB,
     * Blocks.BRICK_SLAB, Blocks.STONE_BRICK_SLAB, Blocks.NETHER_BRICK_SLAB,
     * Blocks.QUARTZ_SLAB, Blocks.RED_SANDSTONE_SLAB, Blocks.PURPUR_SLAB,
     * Blocks.SMOOTH_QUARTZ, Blocks.SMOOTH_RED_SANDSTONE, Blocks.SMOOTH_SANDSTONE,
     * Blocks.SMOOTH_STONE, Blocks.STONE_BUTTON, Blocks.STONE_PRESSURE_PLATE,
     * Blocks.POLISHED_GRANITE_SLAB, Blocks.SMOOTH_RED_SANDSTONE_SLAB,
     * Blocks.MOSSY_STONE_BRICK_SLAB, Blocks.POLISHED_DIORITE_SLAB,
     * Blocks.MOSSY_COBBLESTONE_SLAB, Blocks.END_STONE_BRICK_SLAB,
     * Blocks.SMOOTH_SANDSTONE_SLAB, Blocks.SMOOTH_QUARTZ_SLAB, Blocks.GRANITE_SLAB,
     * Blocks.ANDESITE_SLAB, Blocks.RED_NETHER_BRICK_SLAB,
     * Blocks.POLISHED_ANDESITE_SLAB, Blocks.DIORITE_SLAB, Blocks.SHULKER_BOX,
     * Blocks.BLACK_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX,
     * Blocks.CYAN_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX,
     * Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.LIGHT_GRAY_SHULKER_BOX,
     * Blocks.LIME_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX,
     * Blocks.ORANGE_SHULKER_BOX, Blocks.PINK_SHULKER_BOX,
     * Blocks.PURPLE_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.WHITE_SHULKER_BOX,
     * Blocks.YELLOW_SHULKER_BOX, Blocks.PISTON, Blocks.STICKY_PISTON,
     * Blocks.PISTON_HEAD, Blocks.CLAY, Blocks.DIRT, Blocks.COARSE_DIRT,
     * Blocks.PODZOL, Blocks.FARMLAND, Blocks.GRASS_BLOCK, Blocks.GRAVEL,
     * Blocks.MYCELIUM, Blocks.SAND, Blocks.RED_SAND, Blocks.SNOW_BLOCK,
     * Blocks.SNOW, Blocks.SOUL_SAND, Blocks.DIRT_PATH,
     * Blocks.WHITE_CONCRETE_POWDER, Blocks.ORANGE_CONCRETE_POWDER,
     * Blocks.MAGENTA_CONCRETE_POWDER, Blocks.LIGHT_BLUE_CONCRETE_POWDER,
     * Blocks.YELLOW_CONCRETE_POWDER, Blocks.LIME_CONCRETE_POWDER,
     * Blocks.PINK_CONCRETE_POWDER, Blocks.GRAY_CONCRETE_POWDER,
     * Blocks.LIGHT_GRAY_CONCRETE_POWDER, Blocks.CYAN_CONCRETE_POWDER,
     * Blocks.PURPLE_CONCRETE_POWDER, Blocks.BLUE_CONCRETE_POWDER,
     * Blocks.BROWN_CONCRETE_POWDER, Blocks.GREEN_CONCRETE_POWDER,
     * Blocks.RED_CONCRETE_POWDER, Blocks.BLACK_CONCRETE_POWDER, Blocks.SOUL_SOIL);
     * private static final Set<Block> PICK_EFFECTIVE_ON =
     * Set.of(Blocks.ACTIVATOR_RAIL, Blocks.COAL_ORE, Blocks.COBBLESTONE,
     * Blocks.DETECTOR_RAIL, Blocks.DIAMOND_BLOCK, Blocks.DIAMOND_ORE,
     * Blocks.POWERED_RAIL, Blocks.GOLD_BLOCK, Blocks.GOLD_ORE,
     * Blocks.NETHER_GOLD_ORE, Blocks.ICE, Blocks.IRON_BLOCK, Blocks.IRON_ORE,
     * Blocks.LAPIS_BLOCK, Blocks.LAPIS_ORE, Blocks.MOSSY_COBBLESTONE,
     * Blocks.NETHERRACK, Blocks.PACKED_ICE, Blocks.BLUE_ICE, Blocks.RAIL,
     * Blocks.REDSTONE_ORE, Blocks.SANDSTONE, Blocks.CHISELED_SANDSTONE,
     * Blocks.CUT_SANDSTONE, Blocks.CHISELED_RED_SANDSTONE,
     * Blocks.CUT_RED_SANDSTONE, Blocks.RED_SANDSTONE, Blocks.STONE, Blocks.GRANITE,
     * Blocks.POLISHED_GRANITE, Blocks.DIORITE, Blocks.POLISHED_DIORITE,
     * Blocks.ANDESITE, Blocks.POLISHED_ANDESITE, Blocks.STONE_SLAB,
     * Blocks.SMOOTH_STONE_SLAB, Blocks.SANDSTONE_SLAB, Blocks.PETRIFIED_OAK_SLAB,
     * Blocks.COBBLESTONE_SLAB, Blocks.BRICK_SLAB, Blocks.STONE_BRICK_SLAB,
     * Blocks.NETHER_BRICK_SLAB, Blocks.QUARTZ_SLAB, Blocks.RED_SANDSTONE_SLAB,
     * Blocks.PURPUR_SLAB, Blocks.SMOOTH_QUARTZ, Blocks.SMOOTH_RED_SANDSTONE,
     * Blocks.SMOOTH_SANDSTONE, Blocks.SMOOTH_STONE, Blocks.STONE_BUTTON,
     * Blocks.STONE_PRESSURE_PLATE, Blocks.POLISHED_GRANITE_SLAB,
     * Blocks.SMOOTH_RED_SANDSTONE_SLAB, Blocks.MOSSY_STONE_BRICK_SLAB,
     * Blocks.POLISHED_DIORITE_SLAB, Blocks.MOSSY_COBBLESTONE_SLAB,
     * Blocks.END_STONE_BRICK_SLAB, Blocks.SMOOTH_SANDSTONE_SLAB,
     * Blocks.SMOOTH_QUARTZ_SLAB, Blocks.GRANITE_SLAB, Blocks.ANDESITE_SLAB,
     * Blocks.RED_NETHER_BRICK_SLAB, Blocks.POLISHED_ANDESITE_SLAB,
     * Blocks.DIORITE_SLAB, Blocks.SHULKER_BOX, Blocks.BLACK_SHULKER_BOX,
     * Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX,
     * Blocks.GRAY_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX,
     * Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.LIGHT_GRAY_SHULKER_BOX,
     * Blocks.LIME_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX,
     * Blocks.ORANGE_SHULKER_BOX, Blocks.PINK_SHULKER_BOX,
     * Blocks.PURPLE_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.WHITE_SHULKER_BOX,
     * Blocks.YELLOW_SHULKER_BOX, Blocks.PISTON, Blocks.STICKY_PISTON,
     * Blocks.PISTON_HEAD);
     */
    private final ElectricItemProperties properties;

    private static Set<Block> blocks;

    public static Collection<?> add(Collection a, Collection b) {
	a.addAll(b);
	return a;
    }

    public ItemElectricDrill(ElectricItemProperties properties) {
	// super(4, -2.4f, ElectricItemTier.DRILL, EFFECTIVE_ON,
	// properties.durability(0)
	// .addToolType(ToolType.PICKAXE,
	// ElectricItemTier.DRILL.getLevel()).addToolType(ToolType.SHOVEL,
	// ElectricItemTier.DRILL.getLevel()));
	// TODO: DONT USE EMPTY HASHSET
	super(4, -2.4f, ElectricItemTier.DRILL, Tag.<Block>fromSet(new HashSet<>()), properties.durability(0));
	if (blocks == null) {
	    blocks = new HashSet<>();
	    // blocks.addAll(BlockTags.MINEABLE_WITH_SHOVEL.getValues());
	}
	this.properties = properties;
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
	return true;
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

    /*
     * @Override public boolean isCorrectToolForDrops(BlockState blockIn) { int i =
     * getTier().getLevel(); if
     * (BlockTags.MINEABLE_WITH_PICKAXE.contains(blockIn.getBlock())) { return i >=
     * blockIn.getHarvestLevel(); } Material material = blockIn.getMaterial();
     * return material == Material.STONE || material == Material.METAL || material
     * == Material.HEAVY_METAL || blockIn.is(Blocks.SNOW) ||
     * blockIn.is(Blocks.SNOW_BLOCK); }
     */

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
	Material material = state.getMaterial();
	return getJoulesStored(stack) > properties.extract.getJoules()
		? material != Material.METAL && material != Material.HEAVY_METAL && material != Material.STONE ? super.getDestroySpeed(stack, state)
			: speed
		: 0;
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
	extractPower(stack, properties.extract.getJoules() * (blocks.contains(state.getBlock()) ? 1.5 : 1), false);
	return super.mineBlock(stack, worldIn, state, pos, entityLiving);
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
	return 1.0 - getJoulesStored(stack) / properties.capacity;
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
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
