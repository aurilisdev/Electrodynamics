package electrodynamics.prefab.item;

import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.item.Item.Properties;

public class ElectricItemProperties extends Properties {
    public double capacity;
    public TransferPack receive = TransferPack.EMPTY;
    public TransferPack extract = TransferPack.EMPTY;

    public ElectricItemProperties capacity(double capacity) {
	this.capacity = (int) capacity;
	return this;
    }

    public ElectricItemProperties receive(TransferPack receive) {
	this.receive = receive;
	return this;
    }

    public ElectricItemProperties extract(TransferPack extract) {
	this.extract = extract;
	return this;
    }

}