package physica.nuclear.common.items.armor;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import physica.CoreReferences;
import physica.nuclear.common.NuclearTabRegister;

public class ItemHazmatArmor extends ItemArmor {

	public static final ArmorMaterial armorMaterial = EnumHelper.addArmorMaterial("HAZMAT", 0, new int[] { 2, 4, 2, 1 }, 0);

	public ItemHazmatArmor(String type, int id, int type2) {
		super(armorMaterial, id, type2);
		setCreativeTab(NuclearTabRegister.nuclearPhysicsTab);
		setMaxDamage(40000);
		canRepair = false;
		setUnlocalizedName(type);
		setTextureName(CoreReferences.PREFIX + type);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		return CoreReferences.PREFIX + CoreReferences.MODEL_DIRECTORY + "hazmatArmor.png";
	}

}
