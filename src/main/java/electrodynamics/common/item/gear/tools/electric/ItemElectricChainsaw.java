package electrodynamics.common.item.gear.tools.electric;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.api.item.IItemElectric;
import electrodynamics.common.item.gear.tools.electric.utils.ElectricItemTier;
import electrodynamics.prefab.item.ElectricItemProperties;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class ItemElectricChainsaw extends ToolItem implements IItemElectric {

	private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(Blocks.LADDER, Blocks.SCAFFOLDING, Blocks.OAK_BUTTON, Blocks.SPRUCE_BUTTON, Blocks.BIRCH_BUTTON, Blocks.JUNGLE_BUTTON, Blocks.DARK_OAK_BUTTON, Blocks.ACACIA_BUTTON, Blocks.CRIMSON_BUTTON, Blocks.WARPED_BUTTON);
	private static final Set<Material> EFFECTIVE_ON_MATERIALS = Sets.newHashSet(Material.WOOD, Material.NETHER_WOOD, Material.PLANT, Material.REPLACEABLE_PLANT, Material.BAMBOO, Material.VEGETABLE);
	private final ElectricItemProperties properties;

	public ItemElectricChainsaw(ElectricItemProperties properties) {
		super(4, -2.4f, ElectricItemTier.DRILL, EFFECTIVE_ON, properties.addToolType(ToolType.AXE, ElectricItemTier.DRILL.getLevel()));
		this.properties = properties;
	}

	@Override
	public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
		return true;
	}

	@Override
	public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) {

		if (!allowdedIn(group)) {
			return;
		}

		ItemStack charged = new ItemStack(this);
		IItemElectric.setEnergyStored(charged, properties.capacity);
		items.add(charged);

		ItemStack empty = new ItemStack(this);
		IItemElectric.setEnergyStored(empty, 0);
		items.add(empty);
	}

	@Override
	public float getDestroySpeed(ItemStack stack, BlockState state) {
		return getJoulesStored(stack) > properties.extract.getJoules() ? super.getDestroySpeed(stack, state) : 0;
	}

	@Override
	public boolean canBeDepleted() {
		return false;
	}

	@Override
	public boolean mineBlock(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
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
	public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		tooltip.add(ElectroTextUtils.tooltip("item.electric.info", ChatFormatter.getChatDisplayShort(getJoulesStored(stack), DisplayUnit.JOULES)).withStyle(TextFormatting.GRAY));
		tooltip.add(ElectroTextUtils.tooltip("item.electric.voltage", ElectroTextUtils.ratio(ChatFormatter.getChatDisplayShort(properties.receive.getVoltage(), DisplayUnit.VOLTAGE), ChatFormatter.getChatDisplayShort(properties.extract.getVoltage(), DisplayUnit.VOLTAGE))).withStyle(TextFormatting.RED));
	}

	@Override
	public ElectricItemProperties getElectricProperties() {
		return properties;
	}

}
