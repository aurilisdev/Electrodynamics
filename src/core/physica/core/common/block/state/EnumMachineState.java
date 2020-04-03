package physica.core.common.block.state;

import net.minecraft.tileentity.TileEntity;
import physica.core.common.blockprefab.state.IBlockStateInfo;
import physica.core.common.tile.TileElectricFurnace;

public enum EnumMachineState implements IBlockStateInfo {
	electricfurnace(TileElectricFurnace.class, "pickaxe", 1, 3f, 5f),
	electricfurnacerunning(TileElectricFurnace.class, "pickaxe", 1, 3f, 5f),
	coalgenerator(TileElectricFurnace.class, "pickaxe", 1, 3f, 5f),
	coalgeneratorrunning(TileElectricFurnace.class, "pickaxe", 1, 3f, 5f);
	private Class<? extends TileEntity> classType;
	private String harvestTool;
	private int harvestLevel;
	private float hardness;
	private float resistance;

	private EnumMachineState(Class<? extends TileEntity> classType, String harvestTool, int harvestLevel,
			float hardness, float resistance) {
		this.classType = classType;
		this.harvestTool = harvestTool;
		this.harvestLevel = harvestLevel;
		this.hardness = hardness;
		this.resistance = resistance;
	}

	@Override
	public String getName() {
		return name();
	}

	@Override
	public int getHarvestLevel() {
		return harvestLevel;
	}

	@Override
	public String getHarvestTool() {
		return harvestTool;
	}

	@Override
	public float getHardness() {
		return hardness;
	}

	@Override
	public float getResistance() {
		return resistance;
	}

	public TileEntity createTileInstance() {
		try {
			return classType.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

}
