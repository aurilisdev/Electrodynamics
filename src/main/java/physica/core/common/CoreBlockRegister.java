package physica.core.common;

import java.util.HashMap;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import physica.CoreReferences;
import physica.api.core.IContent;
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

	public static BlockInfiniteEnergy blockInfEnergy;
	public static BlockFulmination blockFulmination;
	public static BlockBlastFurnace blockBlastFurnace;
	public static BlockEnergyCable blockCable;
	public static BlockLead blockLead;
	public static BlockOre blockTinOre;
	public static BlockOre blockCopperOre;
	public static BlockOre blockLeadOre;
	public static BlockOre blockSilverOre;

	@Override
	public void preInit()
	{
		GameRegistry.registerBlock(blockInfEnergy = new BlockInfiniteEnergy(), "infEnergy");
		GameRegistry.registerTileEntity(TileInfiniteEnergy.class, CoreReferences.PREFIX + "infEnergy");
		GameRegistry.registerBlock(blockFulmination = new BlockFulmination(), "fulmination");
		GameRegistry.registerTileEntity(TileFulmination.class, CoreReferences.PREFIX + "fulmination");
		GameRegistry.registerBlock(blockBlastFurnace = new BlockBlastFurnace(), "blastFurnace");
		GameRegistry.registerTileEntity(TileBlastFurnace.class, CoreReferences.PREFIX + "blastFurnace");

		HashMap<Integer, String[]> instanceMap = new HashMap<>();
		instanceMap.put(0,
				new String[] {
						"Max Transfer: "
								+ ElectricityDisplay.getDisplay(ElectricityUtilities.convertEnergy(TileEnergyCable.MAX_ENERGY_STORED_BASE * Math.pow(1, 2) * 1.5, Unit.RF, Unit.WATT), Unit.WATT),
						"Max Voltage: " + 120 * 1 });
		instanceMap.put(1,
				new String[] {
						"Max Transfer: "
								+ ElectricityDisplay.getDisplay(ElectricityUtilities.convertEnergy(TileEnergyCable.MAX_ENERGY_STORED_BASE * Math.pow(2, 2) * 1.5, Unit.RF, Unit.WATT), Unit.WATT),
						"Max Voltage: " + 120 * 2 });
		instanceMap.put(2,
				new String[] {
						"Max Transfer: "
								+ ElectricityDisplay.getDisplay(ElectricityUtilities.convertEnergy(TileEnergyCable.MAX_ENERGY_STORED_BASE * Math.pow(3, 2) * 1.5, Unit.RF, Unit.WATT), Unit.WATT),
						"Max Voltage: " + 120 * 3 });
		instanceMap.put(3,
				new String[] {
						"Max Transfer: " + ElectricityDisplay.getDisplay(ElectricityUtilities.convertEnergy(TileEnergyCable.MAX_ENERGY_STORED_BASE * 20 * 1.5, Unit.RF, Unit.WATT), Unit.WATT),
						"Max Voltage: infinite" });
		ItemBlockMetadata.descriptionMap.put(blockCable = new BlockEnergyCable(), instanceMap);
		GameRegistry.registerBlock(blockCable, ItemBlockMetadata.class, "energyCable");
		GameRegistry.registerTileEntity(TileEnergyCable.class, CoreReferences.PREFIX + "energyCable");
		GameRegistry.registerBlock(blockTinOre = new BlockOre("tinOre", ConfigCore.TIN_ORE_HARVEST_LEVEL), "tinOre");
		GameRegistry.registerBlock(blockCopperOre = new BlockOre("copperOre", ConfigCore.COPPER_ORE_HARVEST_LEVEL), "copperOre");
		GameRegistry.registerBlock(blockLead = new BlockLead(), "blockLead");
		GameRegistry.registerBlock(blockLeadOre = new BlockOre("leadOre", ConfigCore.LEAD_ORE_HARVEST_LEVEL), "leadOre");
		GameRegistry.registerBlock(blockSilverOre = new BlockOre("silverOre", ConfigCore.SILVER_ORE_HARVEST_LEVEL), "silverOre");

		OreDictionary.registerOre("oreLead", blockLeadOre);
		OreDictionary.registerOre("oreTin", blockTinOre);
		OreDictionary.registerOre("oreCopper", blockCopperOre);
		OreDictionary.registerOre("oreSilver", blockCopperOre);
	}
}
