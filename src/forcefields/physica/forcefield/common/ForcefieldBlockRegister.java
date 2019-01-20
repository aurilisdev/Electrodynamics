package physica.forcefield.common;

import cpw.mods.fml.common.registry.GameRegistry;
import physica.api.core.IContent;
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

	public static BlockCoercionDriver blockCoercionDriver;
	public static BlockFortronFieldConstructor blockFortronConstructor;
	public static BlockFortronCapacitor blockFortronCapacitor;
	public static BlockFortronField blockFortronField;
	public static BlockInterdictionMatrix blockInterdictionMatrix;
	public static BlockBiometricIdentifier blockBiometricIdentifier;

	@Override
	public void preInit() {
		GameRegistry.registerBlock(blockCoercionDriver = new BlockCoercionDriver(), "coercionDriver");
		GameRegistry.registerTileEntity(TileCoercionDriver.class, ForcefieldReferences.PREFIX + "coercionDriver");
		GameRegistry.registerBlock(blockFortronConstructor = new BlockFortronFieldConstructor(), "fortronConstructor");
		GameRegistry.registerTileEntity(TileFortronFieldConstructor.class, ForcefieldReferences.PREFIX + "fortronConstructor");
		GameRegistry.registerBlock(blockFortronCapacitor = new BlockFortronCapacitor(), "fortronCapacitor");
		GameRegistry.registerTileEntity(TileFortronCapacitor.class, ForcefieldReferences.PREFIX + "fortronCapacitor");
		GameRegistry.registerBlock(blockInterdictionMatrix = new BlockInterdictionMatrix(), "interdictionMatrix");
		GameRegistry.registerTileEntity(TileInterdictionMatrix.class, ForcefieldReferences.PREFIX + "interdictionMatrix");
		GameRegistry.registerBlock(blockBiometricIdentifier = new BlockBiometricIdentifier(), "biometricIdentifier");
		GameRegistry.registerTileEntity(TileBiometricIdentifier.class, ForcefieldReferences.PREFIX + "biometricIdentifier");
		GameRegistry.registerBlock(blockFortronField = new BlockFortronField(), "fortronField");
		GameRegistry.registerTileEntity(TileFortronField.class, ForcefieldReferences.PREFIX + "fortronField");
	}
}
