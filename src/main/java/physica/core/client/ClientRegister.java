package physica.core.client;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import physica.CoreReferences;
import physica.api.core.IContent;
import physica.core.client.render.item.ItemRenderEnergyCable;
import physica.core.client.render.tile.TileRenderBlastFurnace;
import physica.core.client.render.tile.TileRenderCopperCable;
import physica.core.common.CoreBlockRegister;
import physica.core.common.tile.TileBlastFurnace;
import physica.core.common.tile.TileCopperCable;
import physica.core.common.tile.TileInfiniteEnergy;
import physica.library.client.render.ItemRenderObjModel;
import physica.library.client.render.TileRenderObjModel;

@SideOnly(Side.CLIENT)
public class ClientRegister implements IContent {

	@Override
	public void preInit() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileInfiniteEnergy.class,
				new TileRenderObjModel<TileInfiniteEnergy>("infEnergy.obj", "infEnergy.png", CoreReferences.DOMAIN, CoreReferences.MODEL_DIRECTORY, CoreReferences.MODEL_TEXTURE_DIRECTORY));
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(CoreBlockRegister.blockInfEnergy),
				new ItemRenderObjModel("infEnergy.obj", "infEnergy.png", CoreReferences.DOMAIN, CoreReferences.MODEL_DIRECTORY, CoreReferences.MODEL_TEXTURE_DIRECTORY));

		ClientRegistry.bindTileEntitySpecialRenderer(TileBlastFurnace.class,
				new TileRenderBlastFurnace("blastFurnace.obj", "blastFurnace.png"));
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(CoreBlockRegister.blockBlastFurnace),
				new ItemRenderObjModel("blastFurnace.obj", "blastFurnace.png", CoreReferences.DOMAIN, CoreReferences.MODEL_DIRECTORY, CoreReferences.MODEL_TEXTURE_DIRECTORY));

		ClientRegistry.bindTileEntitySpecialRenderer(TileCopperCable.class, new TileRenderCopperCable());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(CoreBlockRegister.blockCable), new ItemRenderEnergyCable());
	}
}
