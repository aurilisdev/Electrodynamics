package electrodynamics.common.item.gear.tools.electric;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;

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
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

public class ItemElectricChainsaw extends DiggerItem implements IItemElectric {
	
	//private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(Blocks.LADDER, Blocks.SCAFFOLDING, Blocks.OAK_BUTTON, Blocks.SPRUCE_BUTTON,
	//    Blocks.BIRCH_BUTTON, Blocks.JUNGLE_BUTTON, Blocks.DARK_OAK_BUTTON, Blocks.ACACIA_BUTTON, Blocks.CRIMSON_BUTTON, Blocks.WARPED_BUTTON);
    private static final Set<Material> EFFECTIVE_ON_MATERIALS = Sets.newHashSet(Material.WOOD, Material.NETHER_WOOD, Material.PLANT,
	    Material.REPLACEABLE_PLANT, Material.BAMBOO, Material.VEGETABLE);
    private final ElectricItemProperties properties;
    
    
    public ItemElectricChainsaw(ElectricItemProperties properties) {
    	//super(4, -2.4f, ElectricItemTier.DRILL, EFFECTIVE_ON, properties.durability(0).addToolType(ToolType.AXE, ElectricItemTier.DRILL.getLevel()));
    	super(4, -2.4f, ElectricItemTier.DRILL, BlockTags.MINEABLE_WITH_AXE, properties.durability(0));
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
    public float getDestroySpeed(ItemStack stack, BlockState state) {
	Material material = state.getMaterial();
	//TODO rework this to work with the BlockTags class?
	return getJoulesStored(stack) > properties.extract.getJoules()
		? EFFECTIVE_ON_MATERIALS.contains(material) ? speed : super.getDestroySpeed(stack, state)
		: 0;
    }

    @Override
    public boolean canBeDepleted() {
	return false;
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
	extractPower(stack, properties.extract.getJoules(), false);
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
