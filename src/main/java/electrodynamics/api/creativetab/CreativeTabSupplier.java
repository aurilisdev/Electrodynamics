package electrodynamics.api.creativetab;

import java.util.List;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

/**
 * When mojank removes it do it yourself
 * 
 * @author skip999
 *
 */
public interface CreativeTabSupplier {

	/**
	 * If {@code isAllowedInCreativeTab} returns true, this will add the items this Item will display in the creative mode tab
	 * 
	 * @param tab   The creative mode tab being referenced
	 * @param items The list of existing creative mode items for that tab
	 */
	public void addCreativeModeItems(CreativeModeTab tab, List<ItemStack> items);

	/**
	 * This will be checked before {@code addCreativeModeItems} is called
	 * 
	 * @param tab The creative mode tab being compared
	 * @return whether or not this item belongs to the tab
	 */
	public boolean isAllowedInCreativeTab(CreativeModeTab tab);

	/**
	 * This method will check if a block has a creative tab. This method should filter for things like a null creative tab or similar
	 * 
	 * @return
	 */
	public boolean hasCreativeTab();

}
