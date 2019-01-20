package physica.content.common.items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import physica.Physica;
import physica.References;
import physica.api.lib.item.ItemUpdateable;

public class ItemInformation extends ItemUpdateable {
	public ArrayList<String> lineList = new ArrayList<>();

	public ItemInformation(String name, String... customLines) {
		setCreativeTab(Physica.creativeTab);
		setUnlocalizedName(name);
		setTextureName(References.PREFIX + name);
		lineList.addAll(Arrays.asList(customLines));
		setMaxStackSize(1);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List lines, boolean par4) {
		lines.addAll(lineList);
	}
}
