package physica.forcefield.common;

import physica.api.core.abstraction.AbstractionLayer;
import physica.api.core.load.IContent;
import physica.api.core.load.LoadPhase;
import physica.forcefield.ForcefieldReferences;
import physica.forcefield.common.block.BlockBiometricIdentifier;
import physica.forcefield.common.block.BlockCoercionDriver;
import physica.forcefield.common.block.BlockFortronCapacitor;
import physica.forcefield.common.block.BlockFortronField;
import physica.forcefield.common.block.BlockFortronFieldConstructor;
import physica.forcefield.common.block.BlockInterdictionMatrix;
import physica.forcefield.common.tile.TileBiometricIdentifier;
import physica.forcefield.common.tile.TileCoercionDriver;
import physica.forcefield.common.tile.TileFortronCapacitor;
import physica.forcefield.common.tile.TileFortronField;
import physica.forcefield.common.tile.TileFortronFieldConstructor;
import physica.forcefield.common.tile.TileInterdictionMatrix;
import physica.library.item.ItemBlockDescriptable;

public class ForcefieldBlockRegister implements IContent {

	public static BlockCoercionDriver			blockCoercionDriver;
	public static BlockFortronFieldConstructor	blockFortronConstructor;
	public static BlockFortronCapacitor			blockFortronCapacitor;
	public static BlockFortronField				blockFortronField;
	public static BlockInterdictionMatrix		blockInterdictionMatrix;
	public static BlockBiometricIdentifier		blockBiometricIdentifier;

	@Override
	public void register(LoadPhase phase)
	{
		if (phase == LoadPhase.RegisterObjects)
		{
			AbstractionLayer.Registering.registerBlock(blockCoercionDriver = new BlockCoercionDriver(), ItemBlockDescriptable.class, "coercionDriver");
			AbstractionLayer.Registering.registerTileEntity(TileCoercionDriver.class, ForcefieldReferences.PREFIX + "coercionDriver");
			AbstractionLayer.Registering.registerBlock(blockFortronCapacitor = new BlockFortronCapacitor(), ItemBlockDescriptable.class, "fortronCapacitor");
			AbstractionLayer.Registering.registerTileEntity(TileFortronCapacitor.class, ForcefieldReferences.PREFIX + "fortronCapacitor");
			AbstractionLayer.Registering.registerBlock(blockFortronConstructor = new BlockFortronFieldConstructor(), ItemBlockDescriptable.class, "fortronConstructor");
			AbstractionLayer.Registering.registerTileEntity(TileFortronFieldConstructor.class, ForcefieldReferences.PREFIX + "fortronConstructor");
			AbstractionLayer.Registering.registerBlock(blockInterdictionMatrix = new BlockInterdictionMatrix(), ItemBlockDescriptable.class, "interdictionMatrix");
			AbstractionLayer.Registering.registerTileEntity(TileInterdictionMatrix.class, ForcefieldReferences.PREFIX + "interdictionMatrix");
			AbstractionLayer.Registering.registerBlock(blockBiometricIdentifier = new BlockBiometricIdentifier(), ItemBlockDescriptable.class, "biometricIdentifier");
			AbstractionLayer.Registering.registerTileEntity(TileBiometricIdentifier.class, ForcefieldReferences.PREFIX + "biometricIdentifier");
			AbstractionLayer.Registering.registerBlock(blockFortronField = new BlockFortronField(), "fortronField");
			AbstractionLayer.Registering.registerTileEntity(TileFortronField.class, ForcefieldReferences.PREFIX + "fortronField");

			ItemBlockDescriptable.addDescriptionShifted(blockCoercionDriver, 0, "Produces fortron using electricity.");
			ItemBlockDescriptable.addDescriptionShifted(blockFortronConstructor, 0, "Produces a fortron field with customizable", "options.");
			ItemBlockDescriptable.addDescriptionShifted(blockFortronCapacitor, 0, "Transports fortron from a coercion driver or", "another capacitor to the field", "constructor.");
			ItemBlockDescriptable.addDescriptionShifted(blockInterdictionMatrix, 0, "Imposes restrictions on players or entities", "present in the space around it.");
			ItemBlockDescriptable.addDescriptionShifted(blockBiometricIdentifier, 0, "Uses the bio-signatures given off by", "players to govern what permissions they have ", "when interacting with force fields and ", "MFFS machines.");
		}
	}
}
