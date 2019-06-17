package physica.nuclear.client;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import physica.CoreReferences;
import physica.api.core.IContent;
import physica.library.client.render.ItemRenderObjModel;
import physica.library.client.render.TileRenderObjModel;
import physica.nuclear.client.render.entity.RenderParticle;
import physica.nuclear.client.render.item.ItemRenderCentrifuge;
import physica.nuclear.client.render.item.ItemRenderControlRod;
import physica.nuclear.client.render.item.ItemRenderTurbine;
import physica.nuclear.client.render.tile.TileRenderAssembler;
import physica.nuclear.client.render.tile.TileRenderCentrifuge;
import physica.nuclear.client.render.tile.TileRenderControlRod;
import physica.nuclear.client.render.tile.TileRenderFissionReactor;
import physica.nuclear.client.render.tile.TileRenderFusionReactor;
import physica.nuclear.client.render.tile.TileRenderNeutronCaptureChamber;
import physica.nuclear.client.render.tile.TileRenderTurbine;
import physica.nuclear.common.NuclearBlockRegister;
import physica.nuclear.common.NuclearFluidRegister;
import physica.nuclear.common.entity.EntityParticle;
import physica.nuclear.common.tile.TileCentrifuge;
import physica.nuclear.common.tile.TileChemicalBoiler;
import physica.nuclear.common.tile.TileChemicalExtractor;
import physica.nuclear.common.tile.TileFissionReactor;
import physica.nuclear.common.tile.TileFusionReactor;
import physica.nuclear.common.tile.TileInsertableControlRod;
import physica.nuclear.common.tile.TileMeltedReactor;
import physica.nuclear.common.tile.TileNeutronCaptureChamber;
import physica.nuclear.common.tile.TileQuantumAssembler;
import physica.nuclear.common.tile.TileTurbine;

@SideOnly(Side.CLIENT)
public class NuclearClientRegister implements IContent {

	@Override
	public void preInit() {
		MinecraftForge.EVENT_BUS.register(this);
		ClientRegistry.bindTileEntitySpecialRenderer(TileQuantumAssembler.class, new TileRenderAssembler("assembler.obj", "assembler.png"));
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(NuclearBlockRegister.blockQuantumAssembler),
				new ItemRenderObjModel("assembler.obj", "assembler.png", CoreReferences.DOMAIN, CoreReferences.MODEL_DIRECTORY, CoreReferences.MODEL_TEXTURE_DIRECTORY));

		ClientRegistry.bindTileEntitySpecialRenderer(TileCentrifuge.class, new TileRenderCentrifuge("centrifuge.obj", "centrifuge.png"));
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(NuclearBlockRegister.blockCentrifuge), new ItemRenderCentrifuge("centrifuge.obj", "centrifuge.png"));

		ClientRegistry.bindTileEntitySpecialRenderer(TileFissionReactor.class, new TileRenderFissionReactor("fissionReactor.obj", "fissionReactor.png"));
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(NuclearBlockRegister.blockFissionReactor),
				new ItemRenderObjModel("fissionReactor.obj", "fissionReactor.png", CoreReferences.DOMAIN, CoreReferences.MODEL_DIRECTORY, CoreReferences.MODEL_TEXTURE_DIRECTORY));

		ClientRegistry.bindTileEntitySpecialRenderer(TileNeutronCaptureChamber.class, new TileRenderNeutronCaptureChamber("neutronCapturer.obj", "neutronCapture.png"));
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(NuclearBlockRegister.blockNeutronCaptureChamber),
				new ItemRenderObjModel("neutronCapturer.obj", "neutronCaptureEmpty.png", CoreReferences.DOMAIN, CoreReferences.MODEL_DIRECTORY, CoreReferences.MODEL_TEXTURE_DIRECTORY));
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(NuclearBlockRegister.blockNeutronCaptureChamber),
				new ItemRenderObjModel("neutronCapturer.obj", "neutronCaptureEmpty.png", CoreReferences.DOMAIN, CoreReferences.MODEL_DIRECTORY, CoreReferences.MODEL_TEXTURE_DIRECTORY));

		ClientRegistry.bindTileEntitySpecialRenderer(TileChemicalBoiler.class,
				new TileRenderObjModel<TileChemicalBoiler>("chemicalBoiler.obj", "chemicalBoiler.png", CoreReferences.DOMAIN, CoreReferences.MODEL_DIRECTORY, CoreReferences.MODEL_TEXTURE_DIRECTORY));
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(NuclearBlockRegister.blockChemicalBoiler),
				new ItemRenderObjModel("chemicalBoiler.obj", "chemicalBoiler.png", CoreReferences.DOMAIN, CoreReferences.MODEL_DIRECTORY, CoreReferences.MODEL_TEXTURE_DIRECTORY));

		ClientRegistry.bindTileEntitySpecialRenderer(TileChemicalExtractor.class,
				new TileRenderObjModel<TileChemicalExtractor>("chemicalExtractor.obj", "chemicalExtractor.png", CoreReferences.DOMAIN, CoreReferences.MODEL_DIRECTORY,
						CoreReferences.MODEL_TEXTURE_DIRECTORY));
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(NuclearBlockRegister.blockChemicalExtractor),
				new ItemRenderObjModel("chemicalExtractor.obj", "chemicalExtractor.png", CoreReferences.DOMAIN, CoreReferences.MODEL_DIRECTORY, CoreReferences.MODEL_TEXTURE_DIRECTORY));

		ClientRegistry.bindTileEntitySpecialRenderer(TileTurbine.class, new TileRenderTurbine("turbine.obj", "turbine.png"));
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(NuclearBlockRegister.blockTurbine),
				new ItemRenderTurbine("turbine.obj", "turbine.png", CoreReferences.DOMAIN, CoreReferences.MODEL_DIRECTORY, CoreReferences.MODEL_TEXTURE_DIRECTORY));

		ClientRegistry.bindTileEntitySpecialRenderer(TileFusionReactor.class,
				new TileRenderFusionReactor("fusionReactor.obj", "fusionReactor.png", CoreReferences.DOMAIN, CoreReferences.MODEL_DIRECTORY, CoreReferences.MODEL_TEXTURE_DIRECTORY));
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(NuclearBlockRegister.blockFusionReactor),
				new ItemRenderObjModel("fusionReactor.obj", "fusionReactor.png", CoreReferences.DOMAIN, CoreReferences.MODEL_DIRECTORY, CoreReferences.MODEL_TEXTURE_DIRECTORY));

		ClientRegistry.bindTileEntitySpecialRenderer(TileInsertableControlRod.class,
				new TileRenderControlRod());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(NuclearBlockRegister.blockInsertableControlRod),
				new ItemRenderControlRod("controlRodStation.obj", "fissionReactor.png"));

		ClientRegistry.bindTileEntitySpecialRenderer(TileMeltedReactor.class,
				new TileRenderObjModel<TileMeltedReactor>("meltedReactor.obj", "meltedReactor.png", CoreReferences.DOMAIN, CoreReferences.MODEL_DIRECTORY, CoreReferences.MODEL_TEXTURE_DIRECTORY));
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(NuclearBlockRegister.blockMeltedReactor),
				new ItemRenderObjModel("meltedReactor.obj", "meltedReactor.png", CoreReferences.DOMAIN, CoreReferences.MODEL_DIRECTORY, CoreReferences.MODEL_TEXTURE_DIRECTORY));

	}

	@Override
	public void postInit() {
		RenderingRegistry.registerEntityRenderingHandler(EntityParticle.class, new RenderParticle());
	}

	@SubscribeEvent
	public void textureStitchEventPre(TextureStitchEvent.Pre event) {
		if (event.map.getTextureType() == 0) {
			NuclearFluidRegister.textureStitchEventPre(event);
		}
	}

	@SubscribeEvent
	public void textureStitchEventPost(TextureStitchEvent.Post event) {
		if (event.map.getTextureType() == 0) {
			NuclearFluidRegister.textureStitchEventPost(event);
		}
	}
}
