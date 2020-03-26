package physica.core.common.block;

import net.minecraft.block.material.Material;
import physica.core.common.block.state.EnumOreState;

public class BlockOre extends BlockStateHolder<EnumOreState> {

	public BlockOre(Material material, String name) {
		super(material, name);
	}

	public BlockOre(String name) {
		this(Material.ROCK, name);
	}

	@Override
	public Class<EnumOreState> getStateEnumClass() {
		return EnumOreState.class;
	}

	@Override
	public EnumOreState getDefaultStateEnum() {
		return EnumOreState.copper;
	}

	@Override
	public EnumOreState[] getEnumValuesByMeta() {
		return EnumOreState.values();
	}

}
