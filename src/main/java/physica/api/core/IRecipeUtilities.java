package physica.api.core;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public interface IRecipeUtilities {

	default void addShapeless(Item output, Object... params)
	{
		addShapeless(new ItemStack(output), params);
	}

	default void addShapeless(Block output, Object... params)
	{
		addShapeless(new ItemStack(output), params);
	}

	default void addShapeless(ItemStack output, Object... params)
	{
		for (Object obj : params)
		{
			if (obj == null)
			{
				return;
			}
		}
		GameRegistry.addRecipe(new ShapelessOreRecipe(output, params));
	}

	default void addRecipe(Item output, Object... params)
	{
		addRecipe(new ItemStack(output), params);
	}

	default void addRecipe(Block output, Object... params)
	{
		addRecipe(new ItemStack(output), params);
	}

	default void addRecipe(ItemStack output, Object... params)
	{
		if (output.getItem() == null)
		{
			return;
		}
		GameRegistry.addRecipe(new ShapedOreRecipe(output, params));
	}
}
