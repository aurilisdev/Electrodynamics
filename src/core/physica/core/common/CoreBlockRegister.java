package physica.core.common;

import net.minecraft.util.EnumChatFormatting;
import physica.CoreReferences;
import physica.api.core.abstraction.AbstractionLayer;
import physica.api.core.conductor.EnumConductorType;
import physica.api.core.load.IContent;
import physica.api.core.load.LoadPhase;
import physica.core.common.block.BlockBatteryBox;
import physica.core.common.block.BlockBatteryBox.EnumBatteryBox;
import physica.core.common.block.BlockBlastFurnace;
import physica.core.common.block.BlockCircuitPress;
import physica.core.common.block.BlockCoalGenerator;
import physica.core.common.block.BlockElectricFurnace;
import physica.core.common.block.BlockEnergyCable;
import physica.core.common.block.BlockFulmination;
import physica.core.common.block.BlockInfiniteEnergy;
import physica.core.common.block.BlockLead;
import physica.core.common.block.BlockOre;
import physica.core.common.configuration.ConfigCore;
import physica.core.common.tile.TileBatteryBox;
import physica.core.common.tile.TileBlastFurnace;
import physica.core.common.tile.TileCircuitPress;
import physica.core.common.tile.TileCoalGenerator;
import physica.core.common.tile.TileElectricFurnace;
import physica.core.common.tile.TileEnergyCable;
import physica.core.common.tile.TileFulmination;
import physica.core.common.tile.TileInfiniteEnergy;
import physica.library.energy.ElectricityDisplay;
import physica.library.energy.ElectricityUtilities;
import physica.library.energy.base.Unit;
import physica.library.item.ItemBlockDescriptable;
import physica.library.item.ItemBlockMetadata;

public class CoreBlockRegister implements IContent {

	public static BlockInfiniteEnergy	blockInfEnergy;
	public static BlockFulmination		blockFulmination;
	public static BlockBlastFurnace		blockBlastFurnace;
	public static BlockCoalGenerator	blockCoalGenerator;
	public static BlockElectricFurnace	blockElectricFurnace;
	public static BlockCircuitPress		blockCircuitPress;
	public static BlockBatteryBox		blockBatteryBox;
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
			AbstractionLayer.Registering.registerBlock(blockCoalGenerator = new BlockCoalGenerator(), ItemBlockDescriptable.class, "coalGenerator");
			AbstractionLayer.Registering.registerTileEntity(TileCoalGenerator.class, CoreReferences.PREFIX + "coalGenerator");
			AbstractionLayer.Registering.registerBlock(blockBatteryBox = new BlockBatteryBox(), ItemBlockMetadata.class, "batteryBox");
			AbstractionLayer.Registering.registerTileEntity(TileBatteryBox.class, CoreReferences.PREFIX + "batteryBox");
			AbstractionLayer.Registering.registerBlock(blockElectricFurnace = new BlockElectricFurnace(), ItemBlockMetadata.class, "electricFurnace");
			AbstractionLayer.Registering.registerTileEntity(TileElectricFurnace.class, CoreReferences.PREFIX + "electricFurnace");
			AbstractionLayer.Registering.registerBlock(blockCircuitPress = new BlockCircuitPress(), ItemBlockDescriptable.class, "circuitPress");
			AbstractionLayer.Registering.registerTileEntity(TileCircuitPress.class, CoreReferences.PREFIX + "circuitPress");
			AbstractionLayer.Registering.registerBlock(blockBlastFurnace = new BlockBlastFurnace(), ItemBlockDescriptable.class, "blastFurnace");
			AbstractionLayer.Registering.registerTileEntity(TileBlastFurnace.class, CoreReferences.PREFIX + "blastFurnace");
			AbstractionLayer.Registering.registerBlock(blockFulmination = new BlockFulmination(), ItemBlockDescriptable.class, "fulmination");
			AbstractionLayer.Registering.registerTileEntity(TileFulmination.class, CoreReferences.PREFIX + "fulmination");
			AbstractionLayer.Registering.registerBlock(blockInfEnergy = new BlockInfiniteEnergy(), ItemBlockDescriptable.class, "infEnergy");
			AbstractionLayer.Registering.registerTileEntity(TileInfiniteEnergy.class, CoreReferences.PREFIX + "infEnergy");
			AbstractionLayer.Registering.registerBlock(blockCable = new BlockEnergyCable(), ItemBlockMetadata.class, "energyCable");
			AbstractionLayer.Registering.registerTileEntity(TileEnergyCable.class, CoreReferences.PREFIX + "energyCable");
			AbstractionLayer.Registering.registerBlock(blockLead = new BlockLead(), "blockLead");
			AbstractionLayer.Registering.registerBlock(blockTinOre = new BlockOre("tinOre", ConfigCore.TIN_ORE_HARVEST_LEVEL), "tinOre");
			AbstractionLayer.Registering.registerBlock(blockCopperOre = new BlockOre("copperOre", ConfigCore.COPPER_ORE_HARVEST_LEVEL), "copperOre");
			AbstractionLayer.Registering.registerBlock(blockLeadOre = new BlockOre("leadOre", ConfigCore.LEAD_ORE_HARVEST_LEVEL), "leadOre");
			AbstractionLayer.Registering.registerBlock(blockSilverOre = new BlockOre("silverOre", ConfigCore.SILVER_ORE_HARVEST_LEVEL), "silverOre");
			AbstractionLayer.Registering.registerOre("oreLead", blockLeadOre);
			AbstractionLayer.Registering.registerOre("oreTin", blockTinOre);
			AbstractionLayer.Registering.registerOre("oreCopper", blockCopperOre);
			AbstractionLayer.Registering.registerOre("oreSilver", blockSilverOre);

			for (EnumConductorType en : EnumConductorType.values())
			{
				if (en == EnumConductorType.superconductor)
				{
					ItemBlockDescriptable.addDescription(blockCable, en.ordinal(),
							EnumChatFormatting.GREEN + "Max Power: " + EnumChatFormatting.GRAY + ElectricityDisplay.getDisplay(ElectricityUtilities.convertEnergy((double) en.getTransferRate(), Unit.RF, Unit.WATT), Unit.WATT),
							EnumChatFormatting.GREEN + "Max Voltage:" + EnumChatFormatting.GRAY + " infinite");
				} else
				{
					ItemBlockDescriptable.addDescription(blockCable, en.ordinal(),
							EnumChatFormatting.GREEN + "Max Power: " + EnumChatFormatting.GRAY + ElectricityDisplay.getDisplay(ElectricityUtilities.convertEnergy((double) en.getTransferRate(), Unit.RF, Unit.WATT), Unit.WATT),
							EnumChatFormatting.GREEN + "Max Voltage: " + EnumChatFormatting.GRAY + en.getVoltage() + " V");
				}
				ItemBlockDescriptable.addDescriptionShifted(blockCable, en.ordinal(), "Transfers energy from a source to", "different receivers in an energy", "network.");
			}
			ItemBlockDescriptable.addDescription(blockBatteryBox, 0,
					EnumChatFormatting.GREEN + "Capacity: " + EnumChatFormatting.GRAY
							+ ElectricityDisplay.getDisplayShort(ElectricityUtilities.convertEnergy((double) EnumBatteryBox.BASIC.getCapacity(), Unit.WATT, Unit.WATTHOUR), Unit.WATTHOUR),
					EnumChatFormatting.GREEN + "Power Transfer: " + EnumChatFormatting.GRAY + ElectricityDisplay.getDisplay(ElectricityUtilities.convertEnergy(EnumBatteryBox.BASIC.getCapacity() / 500, Unit.WATT, Unit.WATT), Unit.WATT));
			ItemBlockDescriptable.addDescription(blockBatteryBox, 1,
					EnumChatFormatting.GREEN + "Capacity: " + EnumChatFormatting.GRAY
							+ ElectricityDisplay.getDisplayShort(ElectricityUtilities.convertEnergy((double) EnumBatteryBox.ADVANCED.getCapacity(), Unit.WATT, Unit.WATTHOUR), Unit.WATTHOUR),
					EnumChatFormatting.GREEN + "Power Transfer: " + EnumChatFormatting.GRAY + ElectricityDisplay.getDisplay(ElectricityUtilities.convertEnergy(EnumBatteryBox.ADVANCED.getCapacity() / 500, Unit.WATT, Unit.WATT), Unit.WATT));
			ItemBlockDescriptable.addDescription(blockBatteryBox, 2,
					EnumChatFormatting.GREEN + "Capacity: " + EnumChatFormatting.GRAY
							+ ElectricityDisplay.getDisplayShort(ElectricityUtilities.convertEnergy((double) EnumBatteryBox.ELITE.getCapacity(), Unit.WATT, Unit.WATTHOUR), Unit.WATTHOUR),
					EnumChatFormatting.GREEN + "Power Transfer: " + EnumChatFormatting.GRAY + ElectricityDisplay.getDisplay(ElectricityUtilities.convertEnergy(EnumBatteryBox.ELITE.getCapacity() / 500, Unit.WATT, Unit.WATT), Unit.WATT));
			ItemBlockDescriptable.addDescription(blockElectricFurnace, 0,
					EnumChatFormatting.GREEN + "Power Usage: " + EnumChatFormatting.GRAY + ElectricityDisplay.getDisplay(ElectricityUtilities.convertEnergy(TileElectricFurnace.POWER_USAGE, Unit.RF, Unit.WATT), Unit.WATT));
			ItemBlockDescriptable.addDescription(blockElectricFurnace, 1,
					EnumChatFormatting.GREEN + "Power Usage: " + EnumChatFormatting.GRAY + ElectricityDisplay.getDisplay(ElectricityUtilities.convertEnergy(TileElectricFurnace.POWER_USAGE * 3, Unit.RF, Unit.WATT), Unit.WATT));
			ItemBlockDescriptable.addDescriptionShifted(blockBatteryBox, 0, "A block that can be used to store electricity.");
			ItemBlockDescriptable.addDescriptionShifted(blockBatteryBox, 1, "A block that can be used to store electricity.");
			ItemBlockDescriptable.addDescriptionShifted(blockBatteryBox, 2, "A block that can be used to store electricity.");
			ItemBlockDescriptable.addDescriptionShifted(blockInfEnergy, 0, "Emits infinite energy into nearby receivers.");
			ItemBlockDescriptable.addDescriptionShifted(blockFulmination, 0, "Harvests energy from explosions.");
			ItemBlockDescriptable.addDescriptionShifted(blockBlastFurnace, 0, "Smelts iron and combines it with carbon", "to produce steel.");
			ItemBlockDescriptable.addDescriptionShifted(blockCoalGenerator, 0, "Generates electricity burning coal.");
			ItemBlockDescriptable.addDescriptionShifted(blockElectricFurnace, 0, "This block is a faster version of the furnace", "that runs on electricity.");
			ItemBlockDescriptable.addDescriptionShifted(blockElectricFurnace, 1, "This block is a faster version of both the furnace and", "the electric furnace that runs on electricity that also", "doubles the ore output when smelted.");
			ItemBlockDescriptable.addDescriptionShifted(blockCircuitPress, 0, "This block presses different ingredients into circuits");

		}
	}
}
