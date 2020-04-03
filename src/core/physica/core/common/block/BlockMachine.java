package physica.core.common.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import physica.core.common.block.state.EnumMachineState;
import physica.core.common.blockprefab.BlockRotatable;

public class BlockMachine extends BlockRotatable<EnumMachineState> {

	protected BlockMachine(String name, Material material) {
		super(name, material);
	}

	public BlockMachine(String name) {
		this(name, Material.IRON);
	}

	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		for (EnumMachineState ENUM : getEnumValuesByMeta()) {
			if (!ENUM.name().contains("running")) {
				items.add(new ItemStack(this, 1, ENUM.ordinal()));
			}
		}
	}

	@Override
	public Class<EnumMachineState> getStateEnumClass() {
		return EnumMachineState.class;
	}

	@Override
	public EnumMachineState getDefaultStateEnum() {
		return EnumMachineState.electricfurnace;
	}

	@Override
	public EnumMachineState[] getEnumValuesByMeta() {
		return EnumMachineState.values();
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		EnumMachineState machineState = state.getValue(getStateProperty());
		return machineState.createTileInstance();
	}

}
