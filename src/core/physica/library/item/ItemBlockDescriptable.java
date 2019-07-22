package physica.library.item;

import java.util.HashMap;
import java.util.List;

import org.lwjgl.input.Keyboard;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemBlockDescriptable extends ItemBlock {

	public HashMap<Integer, String[]>	instanceMap			= new HashMap<>();
	public HashMap<Integer, String[]>	instanceMapShifted	= new HashMap<>();

	public ItemBlockDescriptable(Block block) {
		super(block);
	}

	public static void addDescription(Block block, int data, String... description)
	{
		((ItemBlockDescriptable) Item.getItemFromBlock(block)).instanceMap.put(data, description);
	}

	public static void addDescriptionShifted(Block block, int data, String... description)
	{
		((ItemBlockDescriptable) Item.getItemFromBlock(block)).instanceMapShifted.put(data, description);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, @SuppressWarnings("rawtypes") List list, boolean par4)
	{
		boolean useShiftedInformation = (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) && !instanceMapShifted.isEmpty();
		if (useShiftedInformation && instanceMapShifted.containsKey(stack.getItemDamage()))
		{
			for (String str : instanceMapShifted.get(stack.getItemDamage()))
			{
				list.add(str);
			}
		} else if (instanceMap.containsKey(stack.getItemDamage()))
		{
			for (String str : instanceMap.get(stack.getItemDamage()))
			{
				list.add(str);
			}
		}
		if (!instanceMapShifted.isEmpty() && !useShiftedInformation)
		{
			list.add("Hold " + EnumChatFormatting.GREEN + "SHIFT" + EnumChatFormatting.GRAY + " for a description.");
		}
	}
}
