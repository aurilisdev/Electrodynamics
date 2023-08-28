package electrodynamics.common.blockitem;

import java.util.List;
import java.util.function.Supplier;

import electrodynamics.api.creativetab.CreativeTabSupplier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

public class BlockItemElectrodynamics extends BlockItem implements CreativeTabSupplier {

	private final Supplier<CreativeModeTab> creativeTab;

	public BlockItemElectrodynamics(Block block, Properties properties, Supplier<CreativeModeTab> creativeTab) {
		super(block, properties);
		this.creativeTab = creativeTab;
	}

	@Override
	public void addCreativeModeItems(CreativeModeTab tab, List<ItemStack> items) {
		items.add(new ItemStack(this));
	}

	@Override
	public boolean isAllowedInCreativeTab(CreativeModeTab tab) {
		return creativeTab.get() == tab;
	}

	@Override
	public boolean hasCreativeTab() {
		return creativeTab != null;
	}

}
