package physica.core.common.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import physica.core.Physica;
import physica.core.common.block.property.PropertySingle;
import physica.core.common.block.state.IBlockStateInfo;
import physica.core.common.item.ItemBlockStateHolder;

public abstract class BlockStateHolder<T extends Enum<T> & IBlockStateInfo> extends BlockBase {
	private PropertySingle<T> STATES;

	public BlockStateHolder(Material material, String name) {
		super(material, name);
		setDefaultState(blockState.getBaseState().withProperty(STATES, getDefaultStateEnum()));
	}

	public abstract Class<T> getStateEnumClass();

	public abstract T getDefaultStateEnum();

	public abstract T[] getEnumValuesByMeta();

	@Override
	public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos) {
		return blockState.getValue(STATES).getHardness();
	}

	@Override
	public float getExplosionResistance(World world, BlockPos pos, Entity exploder, Explosion explosion) {
		return world.getBlockState(pos).getValue(STATES).getResistance() / 5.0f;
	}

	@Override
	public String getHarvestTool(IBlockState state) {
		return state.getValue(STATES).getHarvestTool();
	}

	@Override
	public int getHarvestLevel(IBlockState state) {
		return state.getValue(STATES).getHarvestLevel();
	}

	@Override
	public int damageDropped(IBlockState state) {
		return state.getValue(STATES).ordinal();
	}

	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		for (T ENUM : getEnumValuesByMeta()) {
			items.add(new ItemStack(this, 1, ENUM.ordinal()));
		}
	}

	@Override
	public ItemBlockStateHolder createItemBlock() {
		return (ItemBlockStateHolder) new ItemBlockStateHolder(this).setRegistryName(getRegistryName());
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(STATES, getEnumValuesByMeta()[meta]);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(STATES).ordinal();
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this,
				new IProperty[] { STATES = PropertySingle.<T>createProperty("state", getStateEnumClass()) });
	}

	@Override
	public void registerItemModel(Item itemBlock) {
		for (T t : getEnumValuesByMeta()) {
			Physica.proxy.registerBlockItemRenderer(itemBlock, t.ordinal(), name, t.getName());
		}
	}

	@Override
	public BlockStateHolder<T> setCreativeTab(CreativeTabs tab) {
		return (BlockStateHolder<T>) super.setCreativeTab(tab);
	}
}
