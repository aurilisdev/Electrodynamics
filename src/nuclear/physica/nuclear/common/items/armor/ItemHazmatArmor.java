package physica.nuclear.common.items.armor;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import physica.CoreReferences;
import physica.nuclear.common.NuclearTabRegister;

public class ItemHazmatArmor extends ItemArmor {

	public static final ArmorMaterial	armorMaterial		= EnumHelper.addArmorMaterial("HAZMAT", 0, new int[] { 2, 4, 2, 1 }, 0);
	private float						platingProtection	= 1;

	public ItemHazmatArmor(String type, int id, int type2) {
		super(armorMaterial, id, type2);
		setCreativeTab(NuclearTabRegister.nuclearPhysicsTab);
		setMaxDamage(37500);
		canRepair = false;
		setUnlocalizedName(type);
		setTextureName(CoreReferences.PREFIX + "hazmat/" + type);
		if (type.contains("Reinforced"))
		{
			setPlatingProtection(5);
		}
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
	{
		return CoreReferences.PREFIX + CoreReferences.MODEL_DIRECTORY + (getUnlocalizedName().contains("Reinforced") ? "reinforcedHazmatArmor" : "hazmatArmor") + ".png";
	}

	public ItemHazmatArmor setPlatingProtection(float protection)
	{
		platingProtection = protection;
		return this;

	}

	public float getPlatingProtection()
	{
		return platingProtection;
	}

}
