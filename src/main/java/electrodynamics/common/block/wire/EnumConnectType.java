package electrodynamics.common.block.wire;

import net.minecraft.util.IStringSerializable;

public enum EnumConnectType implements IStringSerializable {
	NONE, WIRE, INVENTORY;

	@Override
	public String func_176610_l() {
		return name().toLowerCase();
	}
}
