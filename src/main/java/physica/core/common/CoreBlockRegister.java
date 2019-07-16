package physica.core.common;

import java.util.HashMap;

import physica.CoreReferences;
import physica.api.core.abstraction.AbstractionLayer;
import physica.api.core.cable.EnumConductorType;
import physica.api.core.load.IContent;
import physica.api.core.load.LoadPhase;
import physica.core.common.block.BlockBlastFurnace;
import physica.core.common.block.BlockEnergyCable;
import physica.core.common.block.BlockFulmination;
import physica.core.common.block.BlockInfiniteEnergy;
import physica.core.common.block.BlockLead;
import physica.core.common.block.BlockOre;
import physica.core.common.configuration.ConfigCore;
import physica.core.common.tile.TileBlastFurnace;
import physica.core.common.tile.TileFulmination;
import physica.core.common.tile.TileInfiniteEnergy;
import physica.core.common.tile.cable.TileEnergyCable;
import physica.library.energy.ElectricityDisplay;
import physica.library.energy.ElectricityUtilities;
import physica.library.energy.base.Unit;
import physica.library.item.ItemBlockMetadata;

public class CoreBlockRegister implements IContent {

	public static BlockInfiniteEnergy	blockInfEnergy;
	public static BlockFulmination		blockFulmination;
	public static BlockBlastFurnace		blockBlastFurnace;
	public static BlockEnergyCable		blockCable;
	public static BlockLead				blockLead;
	public static BlockOre				blockTinOre;
	public static BlockOre				blockCopperOre;
	public static BlockOre				blockLeadOre;
	public static BlockOre				blockSilverOre;

	@Override
	public void register(LoadPhase phase)
	{
		if (phase == LoadPhase.RegisterObjects)
		{
			AbstractionLayer.Registering.registerBlock(blockInfEnergy = new BlockInfiniteEnergy(), "infEnergy");
			AbstractionLayer.Registering.registerTileEntity(TileInfiniteEnergy.class, CoreReferences.PREFIX + "infEnergy");
			AbstractionLayer.Registering.registerBlock(blockFulmination = new BlockFulmination(), "fulmination");
			AbstractionLayer.Registering.registerTileEntity(TileFulmination.class, CoreReferences.PREFIX + "fulmination");
			AbstractionLayer.Registering.registerBlock(blockBlastFurnace = new BlockBlastFurnace(), "blastFurnace");
			AbstractionLayer.Registering.registerTileEntity(TileBlastFurnace.class, CoreReferences.PREFIX + "blastFurnace");

			HashMap<Integer, String[]> instanceMap = new HashMap<>();
			for (EnumConductorType en : EnumConductorType.values())
			{
				if (en == EnumConductorType.superConductor)
				{
					instanceMap.put(en.ordinal(), new String[] { "Max Power: " + ElectricityDisplay.getDisplay(ElectricityUtilities.convertEnergy(en.getTransferRate(), Unit.RF, Unit.WATT), Unit.WATT), "Max Voltage: infinite" });
				} else
				{
					instanceMap.put(en.ordinal(), new String[] { "Max Power: " + ElectricityDisplay.getDisplay(ElectricityUtilities.convertEnergy(en.getTransferRate(), Unit.RF, Unit.WATT), Unit.WATT), "Max Voltage: " + en.getVoltage() });
				}
			}
			ItemBlockMetadata.descriptionMap.put(blockCable = new BlockEnergyCable(), instanceMap);
			AbstractionLayer.Registering.registerBlock(blockCable, ItemBlockMetadata.class, "energyCable");
			AbstractionLayer.Registering.registerTileEntity(TileEnergyCable.class, CoreReferences.PREFIX + "energyCable");
			AbstractionLayer.Registering.registerBlock(blockTinOre = new BlockOre("tinOre", ConfigCore.TIN_ORE_HARVEST_LEVEL), "tinOre");
			AbstractionLayer.Registering.registerBlock(blockCopperOre = new BlockOre("copperOre", ConfigCore.COPPER_ORE_HARVEST_LEVEL), "copperOre");
			AbstractionLayer.Registering.registerBlock(blockLeadOre = new BlockOre("leadOre", ConfigCore.LEAD_ORE_HARVEST_LEVEL), "leadOre");
			AbstractionLayer.Registering.registerBlock(blockSilverOre = new BlockOre("silverOre", ConfigCore.SILVER_ORE_HARVEST_LEVEL), "silverOre");
			AbstractionLayer.Registering.registerBlock(blockLead = new BlockLead(), "blockLead");
			AbstractionLayer.Registering.registerOre("oreLead", blockLeadOre);
			AbstractionLayer.Registering.registerOre("oreTin", blockTinOre);
			AbstractionLayer.Registering.registerOre("oreCopper", blockCopperOre);
			AbstractionLayer.Registering.registerOre("oreSilver", blockCopperOre);
		}
	}
}
