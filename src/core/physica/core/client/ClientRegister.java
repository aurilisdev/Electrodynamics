package physica.core.client;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import physica.CoreReferences;
import physica.api.core.load.IContent;
import physica.api.core.load.LoadPhase;
import physica.core.client.render.tile.TileRenderBlastFurnace;
import physica.core.client.render.tile.TileRenderEnergyCable;
import physica.core.common.CoreBlockRegister;
import physica.core.common.tile.TileBlastFurnace;
import physica.core.common.tile.TileEnergyCable;
import physica.core.common.tile.TileInfiniteEnergy;
import physica.library.client.render.ItemRenderObjModel;
import physica.library.client.render.TileRenderObjModel;

@SideOnly(Side.CLIENT)
public class ClientRegister implements IContent {
	@Override
	public void register(LoadPhase phase) {
		if (phase == LoadPhase.ClientRegister) {
			ClientRegistry.bindTileEntitySpecialRenderer(TileInfiniteEnergy.class, new TileRenderObjModel<TileInfiniteEnergy>("infenergy.obj", "infenergy.png", CoreReferences.DOMAIN, CoreReferences.MODEL_DIRECTORY, CoreReferences.MODEL_TEXTURE_DIRECTORY));
			MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(CoreBlockRegister.blockInfEnergy), new ItemRenderObjModel("infenergy.obj", "infenergy.png", CoreReferences.DOMAIN, CoreReferences.MODEL_DIRECTORY, CoreReferences.MODEL_TEXTURE_DIRECTORY));

			ClientRegistry.bindTileEntitySpecialRenderer(TileBlastFurnace.class, new TileRenderBlastFurnace("blastfurnace.obj", "blastfurnace.png"));
			MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(CoreBlockRegister.blockBlastFurnace), new ItemRenderObjModel("blastfurnace.obj", "blastfurnace.png", CoreReferences.DOMAIN, CoreReferences.MODEL_DIRECTORY, CoreReferences.MODEL_TEXTURE_DIRECTORY));

			ClientRegistry.bindTileEntitySpecialRenderer(TileEnergyCable.class, new TileRenderEnergyCable());
		}
	}
}
