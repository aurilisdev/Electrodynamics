package physica.forcefield.client;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import physica.CoreReferences;
import physica.api.core.load.IContent;
import physica.api.core.load.LoadPhase;
import physica.forcefield.client.render.item.ItemRenderConstructor;
import physica.forcefield.client.render.tile.TileRenderFortronBlock;
import physica.forcefield.common.ForcefieldBlockRegister;
import physica.forcefield.common.ForcefieldFluidRegister;
import physica.forcefield.common.tile.TileBiometricIdentifier;
import physica.forcefield.common.tile.TileCoercionDriver;
import physica.forcefield.common.tile.TileFortronCapacitor;
import physica.forcefield.common.tile.TileFortronFieldConstructor;
import physica.forcefield.common.tile.TileInterdictionMatrix;
import physica.library.client.render.ItemRenderObjModel;

@SideOnly(Side.CLIENT)
public class ForcefieldClientRegister implements IContent {

	@Override
	public void register(LoadPhase phase)
	{
		if (phase == LoadPhase.ClientRegister)
		{
			MinecraftForge.EVENT_BUS.register(this);
			MinecraftForge.EVENT_BUS.register(new ForcefieldRenderHandler());
			ClientRegistry.bindTileEntitySpecialRenderer(TileCoercionDriver.class, new TileRenderFortronBlock<TileCoercionDriver>("coercionDriver.obj"));
			MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ForcefieldBlockRegister.blockCoercionDriver),
					new ItemRenderObjModel("coercionDriver.obj", "fortronMachineBase.png", CoreReferences.DOMAIN, CoreReferences.MODEL_DIRECTORY, CoreReferences.MODEL_TEXTURE_DIRECTORY));

			ClientRegistry.bindTileEntitySpecialRenderer(TileFortronFieldConstructor.class, new TileRenderFortronBlock<TileFortronFieldConstructor>("fortronFieldConstructor.obj"));
			MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ForcefieldBlockRegister.blockFortronConstructor),
					new ItemRenderConstructor("fortronFieldConstructor.obj", "fortronMachineBase.png", CoreReferences.DOMAIN, CoreReferences.MODEL_DIRECTORY, CoreReferences.MODEL_TEXTURE_DIRECTORY));

			ClientRegistry.bindTileEntitySpecialRenderer(TileFortronCapacitor.class, new TileRenderFortronBlock<TileFortronCapacitor>("fortronCapacitor.obj"));
			MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ForcefieldBlockRegister.blockFortronCapacitor),
					new ItemRenderObjModel("fortronCapacitor.obj", "fortronMachineBase.png", CoreReferences.DOMAIN, CoreReferences.MODEL_DIRECTORY, CoreReferences.MODEL_TEXTURE_DIRECTORY));

			ClientRegistry.bindTileEntitySpecialRenderer(TileInterdictionMatrix.class, new TileRenderFortronBlock<TileInterdictionMatrix>("matrixModel.obj"));
			MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ForcefieldBlockRegister.blockInterdictionMatrix),
					new ItemRenderObjModel("matrixModel.obj", "fortronMachineBase.png", CoreReferences.DOMAIN, CoreReferences.MODEL_DIRECTORY, CoreReferences.MODEL_TEXTURE_DIRECTORY));

			ClientRegistry.bindTileEntitySpecialRenderer(TileBiometricIdentifier.class, new TileRenderFortronBlock<TileBiometricIdentifier>("biometricIdentifier.obj"));
			MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ForcefieldBlockRegister.blockBiometricIdentifier),
					new ItemRenderObjModel("biometricIdentifier.obj", "fortronMachineBase.png", CoreReferences.DOMAIN, CoreReferences.MODEL_DIRECTORY, CoreReferences.MODEL_TEXTURE_DIRECTORY));
		}
	}

	@SubscribeEvent
	public void textureStitchEventPre(TextureStitchEvent.Pre event)
	{
		if (event.map.getTextureType() == 0)
		{
			ForcefieldFluidRegister.textureStitchEventPre(event);
		}
	}

	@SubscribeEvent
	public void textureStitchEventPost(TextureStitchEvent.Post event)
	{
		if (event.map.getTextureType() == 0)
		{
			ForcefieldFluidRegister.textureStitchEventPost(event);
		}
	}
}
