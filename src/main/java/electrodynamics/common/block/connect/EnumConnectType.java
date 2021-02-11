package electrodynamics.common.block.connect;

import net.minecraft.util.IStringSerializable;

public enum EnumConnectType implements IStringSerializable {
	NONE, WIRE, INVENTORY;
	@Override
	public String getString() {
		return name().toLowerCase();
	}
}
