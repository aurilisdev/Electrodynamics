package physica.library.item;

import java.util.HashMap;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockMetadata extends ItemBlock {

	public static HashMap<Block, HashMap<Integer, String[]>>	descriptionMap	= new HashMap<>();
	public HashMap<Integer, String[]>							instanceMap		= new HashMap<>();

	public ItemBlockMetadata(Block block) {
		super(block);
		setMaxDamage(0);
		setHasSubtypes(true);
		if (descriptionMap.containsKey(block))
		{
			instanceMap = descriptionMap.get(block);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, @SuppressWarnings("rawtypes") List list, boolean par4)
	{
		if (instanceMap.containsKey(stack.getItemDamage()))
		{
			for (String str : instanceMap.get(stack.getItemDamage()))
			{
				list.add(str);
			}
		}
	}

	@Override
	public String getUnlocalizedName(final ItemStack itemStack)
	{
		return super.getUnlocalizedName(itemStack) + "." + itemStack.getItemDamage();
	}

	@Override
	public int getMetadata(int metadata)
	{
		return metadata;
	}
}
