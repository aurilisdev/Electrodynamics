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
			AbstractionLayer.Registering.registerBlock(blockCoercionDriver = new BlockCoercionDriver(), "coercionDriver");
			AbstractionLayer.Registering.registerTileEntity(TileCoercionDriver.class, ForcefieldReferences.PREFIX + "coercionDriver");
			AbstractionLayer.Registering.registerBlock(blockFortronConstructor = new BlockFortronFieldConstructor(), "fortronConstructor");
			AbstractionLayer.Registering.registerTileEntity(TileFortronFieldConstructor.class, ForcefieldReferences.PREFIX + "fortronConstructor");
			AbstractionLayer.Registering.registerBlock(blockFortronCapacitor = new BlockFortronCapacitor(), "fortronCapacitor");
			AbstractionLayer.Registering.registerTileEntity(TileFortronCapacitor.class, ForcefieldReferences.PREFIX + "fortronCapacitor");
			AbstractionLayer.Registering.registerBlock(blockInterdictionMatrix = new BlockInterdictionMatrix(), "interdictionMatrix");
			AbstractionLayer.Registering.registerTileEntity(TileInterdictionMatrix.class, ForcefieldReferences.PREFIX + "interdictionMatrix");
			AbstractionLayer.Registering.registerBlock(blockBiometricIdentifier = new BlockBiometricIdentifier(), "biometricIdentifier");
			AbstractionLayer.Registering.registerTileEntity(TileBiometricIdentifier.class, ForcefieldReferences.PREFIX + "biometricIdentifier");
			AbstractionLayer.Registering.registerBlock(blockFortronField = new BlockFortronField(), "fortronField");
			AbstractionLayer.Registering.registerTileEntity(TileFortronField.class, ForcefieldReferences.PREFIX + "fortronField");
		}
	}
}
