package defense.api;

import net.minecraft.nbt.NBTTagCompound;

/**
 * An object that contains a reference to IExplosive. Carried by explosives, grenades and missile entities etc.
 *
 * @author Calclavia
 */
public interface IExplosiveContainer {

	NBTTagCompound getTagCompound();

	IExplosive getExplosiveType();
}
