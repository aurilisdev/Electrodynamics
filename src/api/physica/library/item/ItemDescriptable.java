package physica.library.item;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import physica.core.common.CoreTabRegister;

public class ItemDescriptable extends ItemUpdateable {

	public Set<String> lineList = new HashSet<>();

	public ItemDescriptable(String texturePrefix, String name, String... customLines) {
		setUnlocalizedName(name);
		setTextureName(texturePrefix + name);
		lineList.addAll(Arrays.asList(customLines));
		setCreativeTab(CoreTabRegister.coreTab);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, @SuppressWarnings("rawtypes") List lines, boolean par4)
	{
		lines.addAll(lineList);
	}
}
