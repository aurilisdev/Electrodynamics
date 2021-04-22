package electrodynamics.prefab.utilities;

import electrodynamics.common.damage.DamageSources;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.entity.Entity;

public class UtilitiesElectricity {
    public static void electrecuteEntity(Entity entityIn, TransferPack transfer) {
	entityIn.attackEntityFrom(DamageSources.ELECTRICITY, (float) Math.min(9999, Math.max(0, transfer.getAmps())));
    }
}
