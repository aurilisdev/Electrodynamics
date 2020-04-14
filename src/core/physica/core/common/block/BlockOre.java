package physica.core.common.block;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import physica.core.common.block.state.EnumOreState;
import physica.core.common.blockprefab.BlockStateHolder;
import physica.core.common.component.ComponentItems;
import physica.core.common.item.subtypes.EnumBlend;

public class BlockOre extends BlockStateHolder<EnumOreState> {

	public BlockOre(Material material, String name) {
		super(material, name);
	}

	public BlockOre(String name) {
		this(Material.ROCK, name);
	}

	@Override
	public Class<EnumOreState> getStateEnumClass() {
		return EnumOreState.class;
	}

	@Override
	public EnumOreState getDefaultStateEnum() {
		return EnumOreState.copper;
	}

	@Override
	public EnumOreState[] getEnumValuesByMeta() {
		return EnumOreState.values();
	}

	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state,
			int fortune) {
		Random rand = world instanceof World ? ((World) world).rand : RANDOM;

		int count = state.getValue(stateProperty) == EnumOreState.sulfur ? 2 + rand.nextInt(fortune + 2) : 1;
		for (int i = 0; i < count; i++) {
			Item item = this.getItemDropped(state, rand, fortune);
			if (item != Items.AIR) {
				if (state.getValue(stateProperty) == EnumOreState.sulfur) {
					drops.add(new ItemStack(ComponentItems.blendBase, 1, EnumBlend.sulfur.ordinal()));
				} else {
					drops.add(new ItemStack(item, 1, this.damageDropped(state)));
				}
			}
		}
	}

}
