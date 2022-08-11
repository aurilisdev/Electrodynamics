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
		setTextureName(texturePrefix + name.toLowerCase());
		lineList.addAll(Arrays.asList(customLines));
		setCreativeTab(CoreTabRegister.coreTab);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List lines, boolean par4) {
		lines.addAll(lineList);
	}
}
