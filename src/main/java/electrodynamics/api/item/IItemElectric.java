package electrodynamics.api.item;

import electrodynamics.prefab.item.ElectricItemProperties;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.item.ItemStack;

public interface IItemElectric {

    TransferPack extractPower(ItemStack stack, double amount, boolean debug);

    TransferPack receivePower(ItemStack stack, TransferPack amount, boolean debug);

    double getJoulesStored(ItemStack stack);

    void overVoltage(TransferPack attempt);
    
    ElectricItemProperties getProperties();

}