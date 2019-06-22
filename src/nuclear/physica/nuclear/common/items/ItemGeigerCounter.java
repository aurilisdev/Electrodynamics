package physica.nuclear.common.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import physica.CoreReferences;
import physica.library.item.ItemElectric;
import physica.nuclear.common.NuclearTabRegister;

public class ItemGeigerCounter extends ItemElectric {

	public ItemGeigerCounter(String name) {
		super((int) (250000 / 0.4D));
		setUnlocalizedName(name);
		setTextureName(CoreReferences.PREFIX + name);
		setCreativeTab(NuclearTabRegister.nuclearPhysicsTab);
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5) {
		if (entity instanceof EntityPlayer) {
			if (((EntityPlayer) entity).inventory.getCurrentItem() == stack) {
				extractEnergy(stack, getMaxEnergyStored(stack) / 250000, false);
			}
		}
	}
}
