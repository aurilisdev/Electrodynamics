package physica.content.client;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import physica.References;
import physica.api.base.IProxyContent;
import physica.api.lib.client.render.ItemRenderObjModel;
import physica.api.lib.client.render.TileRenderObjModel;
import physica.content.client.render.entity.RenderParticle;
import physica.content.client.render.item.ItemRenderCentrifuge;
import physica.content.client.render.tile.TileRenderAssembler;
import physica.content.client.render.tile.TileRenderCentrifuge;
import physica.content.common.BlockRegister;
import physica.content.common.FluidRegister;
import physica.content.common.entity.EntityParticle;
import physica.content.common.tile.TileAssembler;
import physica.content.common.tile.TileCentrifuge;
import physica.content.common.tile.TileChemicalBoiler;

@SideOnly(Side.CLIENT)
public class ClientRegister implements IProxyContent {
	@Override
	public void preInit() {
		MinecraftForge.EVENT_BUS.register(this);
		ClientRegistry.bindTileEntitySpecialRenderer(TileAssembler.class, new TileRenderAssembler("assembler.obj", "assembler.png"));
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockRegister.blockAssembler),
				new ItemRenderObjModel("assembler.obj", "assembler.png", References.DOMAIN, References.MODEL_DIRECTORY, References.MODEL_TEXTURE_DIRECTORY));

		ClientRegistry.bindTileEntitySpecialRenderer(TileCentrifuge.class, new TileRenderCentrifuge("centrifuge.obj", "centrifuge.png"));
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockRegister.blockCentrifuge), new ItemRenderCentrifuge("centrifuge.obj", "centrifuge.png"));

		ClientRegistry.bindTileEntitySpecialRenderer(TileChemicalBoiler.class,
				new TileRenderObjModel<TileChemicalBoiler>("chemicalBoiler.obj", "chemicalBoiler.png", References.DOMAIN, References.MODEL_DIRECTORY, References.MODEL_TEXTURE_DIRECTORY));
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockRegister.blockChemicalBoiler),
				new ItemRenderObjModel("chemicalBoiler.obj", "chemicalBoiler.png", References.DOMAIN, References.MODEL_DIRECTORY, References.MODEL_TEXTURE_DIRECTORY));
	}

	@Override
	public void postInit() {
		RenderingRegistry.registerEntityRenderingHandler(EntityParticle.class, new RenderParticle());
	}

	@SubscribeEvent
	public void textureStitchEventPre(TextureStitchEvent.Pre event) {
		if (event.map.getTextureType() == 0)
		{
			FluidRegister.textureStitchEventPre(event);
		}
	}

	@SubscribeEvent
	public void textureStitchEventPost(TextureStitchEvent.Post event) {
		if (event.map.getTextureType() == 0)
		{
			FluidRegister.textureStitchEventPost(event);
		}
	}
}
