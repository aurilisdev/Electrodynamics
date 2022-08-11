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
import physica.api.core.load.IContent;
import physica.api.core.load.LoadPhase;
import physica.library.client.render.ItemRenderObjModel;
import physica.library.client.render.TileRenderObjModel;
import physica.nuclear.NuclearReferences;
import physica.nuclear.client.render.entity.RenderParticle;
import physica.nuclear.client.render.item.ItemRenderCentrifuge;
import physica.nuclear.client.render.item.ItemRenderControlRod;
import physica.nuclear.client.render.item.ItemRenderNeutronCaptureChamber;
import physica.nuclear.client.render.item.ItemRenderTurbine;
import physica.nuclear.client.render.tile.TileRenderAssembler;
import physica.nuclear.client.render.tile.TileRenderCentrifuge;
import physica.nuclear.client.render.tile.TileRenderControlRod;
import physica.nuclear.client.render.tile.TileRenderFissionReactor;
import physica.nuclear.client.render.tile.TileRenderFusionReactor;
import physica.nuclear.client.render.tile.TileRenderNeutronCaptureChamber;
import physica.nuclear.client.render.tile.TileRenderThermometer;
import physica.nuclear.client.render.tile.TileRenderTurbine;
import physica.nuclear.common.NuclearBlockRegister;
import physica.nuclear.common.NuclearFluidRegister;
import physica.nuclear.common.entity.EntityParticle;
import physica.nuclear.common.tile.TileChemicalBoiler;
import physica.nuclear.common.tile.TileChemicalExtractor;
import physica.nuclear.common.tile.TileFissionReactor;
import physica.nuclear.common.tile.TileFusionReactor;
import physica.nuclear.common.tile.TileGasCentrifuge;
import physica.nuclear.common.tile.TileInsertableControlRod;
import physica.nuclear.common.tile.TileMeltedReactor;
import physica.nuclear.common.tile.TileNeutronCaptureChamber;
import physica.nuclear.common.tile.TileQuantumAssembler;
import physica.nuclear.common.tile.TileRadioisotopeGenerator;
import physica.nuclear.common.tile.TileThermometer;
import physica.nuclear.common.tile.TileTurbine;

@SideOnly(Side.CLIENT)
public class NuclearClientRegister implements IContent {

	@Override
	public void register(LoadPhase phase) {
		if (phase == LoadPhase.ClientRegister) {
			MinecraftForge.EVENT_BUS.register(this);
			ClientRegistry.bindTileEntitySpecialRenderer(TileQuantumAssembler.class, new TileRenderAssembler("assembler.obj", "assembler.png"));
			MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(NuclearBlockRegister.blockQuantumAssembler), new ItemRenderObjModel("assembler.obj", "assembler.png", NuclearReferences.DOMAIN, CoreReferences.MODEL_DIRECTORY, CoreReferences.MODEL_TEXTURE_DIRECTORY));

			ClientRegistry.bindTileEntitySpecialRenderer(TileGasCentrifuge.class, new TileRenderCentrifuge("centrifugestand.obj", "gascentrifuge.png"));
			MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(NuclearBlockRegister.blockCentrifuge), new ItemRenderCentrifuge("centrifugestand.obj", "gascentrifuge.png"));

			ClientRegistry.bindTileEntitySpecialRenderer(TileFissionReactor.class, new TileRenderFissionReactor("fissionreactor.obj", "fissionreactor.png"));
			MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(NuclearBlockRegister.blockFissionReactor), new ItemRenderObjModel("fissionreactor.obj", "fissionreactor.png", NuclearReferences.DOMAIN, CoreReferences.MODEL_DIRECTORY, CoreReferences.MODEL_TEXTURE_DIRECTORY));

			ClientRegistry.bindTileEntitySpecialRenderer(TileNeutronCaptureChamber.class, new TileRenderNeutronCaptureChamber("neutroncapturer.obj", "neutroncapture.png"));
			MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(NuclearBlockRegister.blockNeutronCaptureChamber), new ItemRenderObjModel("neutroncapturer.obj", "neutroncaptureempty.png", NuclearReferences.DOMAIN, CoreReferences.MODEL_DIRECTORY, CoreReferences.MODEL_TEXTURE_DIRECTORY));
			MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(NuclearBlockRegister.blockNeutronCaptureChamber), new ItemRenderNeutronCaptureChamber("neutroncapturer.obj", "neutroncaptureempty.png", NuclearReferences.DOMAIN, CoreReferences.MODEL_DIRECTORY, CoreReferences.MODEL_TEXTURE_DIRECTORY));

			ClientRegistry.bindTileEntitySpecialRenderer(TileChemicalBoiler.class, new TileRenderObjModel<TileChemicalBoiler>("chemicalboiler.obj", "chemicalboiler.png", NuclearReferences.DOMAIN, CoreReferences.MODEL_DIRECTORY, CoreReferences.MODEL_TEXTURE_DIRECTORY));
			MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(NuclearBlockRegister.blockChemicalBoiler), new ItemRenderObjModel("chemicalboiler.obj", "chemicalboiler.png", NuclearReferences.DOMAIN, CoreReferences.MODEL_DIRECTORY, CoreReferences.MODEL_TEXTURE_DIRECTORY));

			ClientRegistry.bindTileEntitySpecialRenderer(TileChemicalExtractor.class, new TileRenderObjModel<TileChemicalExtractor>("chemicalextractor.obj", "chemicalextractor.png", NuclearReferences.DOMAIN, CoreReferences.MODEL_DIRECTORY, CoreReferences.MODEL_TEXTURE_DIRECTORY));
			MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(NuclearBlockRegister.blockChemicalExtractor), new ItemRenderObjModel("chemicalextractor.obj", "chemicalextractor.png", NuclearReferences.DOMAIN, CoreReferences.MODEL_DIRECTORY, CoreReferences.MODEL_TEXTURE_DIRECTORY));

			ClientRegistry.bindTileEntitySpecialRenderer(TileTurbine.class, new TileRenderTurbine("turbine.obj", "turbine.png"));
			MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(NuclearBlockRegister.blockTurbine), new ItemRenderTurbine("turbine.obj", "turbine.png", NuclearReferences.DOMAIN, CoreReferences.MODEL_DIRECTORY, CoreReferences.MODEL_TEXTURE_DIRECTORY));

			ClientRegistry.bindTileEntitySpecialRenderer(TileFusionReactor.class, new TileRenderFusionReactor("fusionreactor.obj", "fusionreactor.png", NuclearReferences.DOMAIN, CoreReferences.MODEL_DIRECTORY, CoreReferences.MODEL_TEXTURE_DIRECTORY));
			MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(NuclearBlockRegister.blockFusionReactor), new ItemRenderObjModel("fusionreactor.obj", "fusionreactor.png", NuclearReferences.DOMAIN, CoreReferences.MODEL_DIRECTORY, CoreReferences.MODEL_TEXTURE_DIRECTORY));
			ClientRegistry.bindTileEntitySpecialRenderer(TileRadioisotopeGenerator.class, new TileRenderObjModel<TileRadioisotopeGenerator>("radioisotopegenerator.obj", "radioisotopegenerator.png", NuclearReferences.DOMAIN, CoreReferences.MODEL_DIRECTORY, CoreReferences.MODEL_TEXTURE_DIRECTORY));
			MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(NuclearBlockRegister.blockRadioisotopeGenerator), new ItemRenderObjModel("radioisotopegenerator.obj", "radioisotopegenerator.png", NuclearReferences.DOMAIN, CoreReferences.MODEL_DIRECTORY, CoreReferences.MODEL_TEXTURE_DIRECTORY));

			ClientRegistry.bindTileEntitySpecialRenderer(TileInsertableControlRod.class, new TileRenderControlRod());
			MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(NuclearBlockRegister.blockInsertableControlRod), new ItemRenderControlRod("controlrodstation.obj", "fissionreactor.png"));

			ClientRegistry.bindTileEntitySpecialRenderer(TileMeltedReactor.class, new TileRenderObjModel<TileMeltedReactor>("meltedreactor.obj", "meltedreactor.png", NuclearReferences.DOMAIN, CoreReferences.MODEL_DIRECTORY, CoreReferences.MODEL_TEXTURE_DIRECTORY));
			MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(NuclearBlockRegister.blockMeltedReactor), new ItemRenderObjModel("meltedreactor.obj", "meltedreactor.png", NuclearReferences.DOMAIN, CoreReferences.MODEL_DIRECTORY, CoreReferences.MODEL_TEXTURE_DIRECTORY));

			ClientRegistry.bindTileEntitySpecialRenderer(TileThermometer.class, new TileRenderThermometer());
		} else if (phase == LoadPhase.PostInitialize) {
			RenderingRegistry.registerEntityRenderingHandler(EntityParticle.class, new RenderParticle());
		}
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
