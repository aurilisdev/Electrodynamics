package physica.core.common.blockprefab;

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
import physica.core.common.blockprefab.property.PropertySingle;
import physica.core.common.blockprefab.state.IBlockStateInfo;
import physica.core.common.item.ItemBlockStateHolder;

public abstract class BlockStateHolder<T extends Enum<T> & IBlockStateInfo> extends BlockBase {
	protected PropertySingle<T> stateProperty;

	public BlockStateHolder(Material material, String name) {
		super(material, name);
		setDefaultState(blockState.getBaseState().withProperty(stateProperty, getDefaultStateEnum()));
	}

	public abstract Class<T> getStateEnumClass();

	public abstract T getDefaultStateEnum();

	public abstract T[] getEnumValuesByMeta();

	@Override
	public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos) {
		return blockState.getValue(stateProperty).getHardness();
	}

	@Override
	public float getExplosionResistance(World world, BlockPos pos, Entity exploder, Explosion explosion) {
		return world.getBlockState(pos).getValue(stateProperty).getResistance() / 5.0f;
	}

	@Override
	public String getHarvestTool(IBlockState state) {
		return state.getValue(stateProperty).getHarvestTool();
	}

	@Override
	public int getHarvestLevel(IBlockState state) {
		return state.getValue(stateProperty).getHarvestLevel();
	}

	@Override
	public int damageDropped(IBlockState state) {
		return state.getValue(stateProperty).ordinal();
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
		return getDefaultState().withProperty(stateProperty, getEnumValuesByMeta()[meta]);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(stateProperty).ordinal();
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this,
				new IProperty[] { stateProperty = PropertySingle.<T>createProperty("state", getStateEnumClass()) });
	}

	@Override
	public void registerItemModel(Item itemBlock) {
		for (T t : getEnumValuesByMeta()) {
			Physica.proxy.registerBlockItemRenderer(itemBlock, t.ordinal(), name, "state=" + t.getName());
		}
	}

	public PropertySingle<T> getStateProperty() {
		return stateProperty;
	}

	@Override
	public BlockStateHolder<T> setCreativeTab(CreativeTabs tab) {
		return (BlockStateHolder<T>) super.setCreativeTab(tab);
	}
}
