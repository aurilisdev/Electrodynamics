package electrodynamics.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import electrodynamics.api.References;
import electrodynamics.client.guidebook.ModuleElectrodynamics;
import electrodynamics.client.guidebook.ScreenGuidebook;
import electrodynamics.client.render.entity.RenderEnergyBlast;
import electrodynamics.client.render.entity.RenderMetalRod;
import electrodynamics.client.render.tile.RenderAdvancedSolarPanel;
import electrodynamics.client.render.tile.RenderBatteryBox;
import electrodynamics.client.render.tile.RenderChargerGeneric;
import electrodynamics.client.render.tile.RenderChemicalMixer;
import electrodynamics.client.render.tile.RenderCombustionChamber;
import electrodynamics.client.render.tile.RenderFermentationPlant;
import electrodynamics.client.render.tile.RenderHydroelectricGenerator;
import electrodynamics.client.render.tile.RenderLathe;
import electrodynamics.client.render.tile.RenderLithiumBatteryBox;
import electrodynamics.client.render.tile.RenderMineralCrusher;
import electrodynamics.client.render.tile.RenderMineralCrusherDouble;
import electrodynamics.client.render.tile.RenderMineralCrusherTriple;
import electrodynamics.client.render.tile.RenderMineralGrinder;
import electrodynamics.client.render.tile.RenderMineralGrinderDouble;
import electrodynamics.client.render.tile.RenderMineralGrinderTriple;
import electrodynamics.client.render.tile.RenderMineralWasher;
import electrodynamics.client.render.tile.RenderMultimeterBlock;
import electrodynamics.client.render.tile.RenderWindmill;
import electrodynamics.client.screen.tile.ScreenBatteryBox;
import electrodynamics.client.screen.tile.ScreenChargerGeneric;
import electrodynamics.client.screen.tile.ScreenChemicalCrystallizer;
import electrodynamics.client.screen.tile.ScreenChemicalMixer;
import electrodynamics.client.screen.tile.ScreenCoalGenerator;
import electrodynamics.client.screen.tile.ScreenCombustionChamber;
import electrodynamics.client.screen.tile.ScreenCreativeFluidSource;
import electrodynamics.client.screen.tile.ScreenCreativePowerSource;
import electrodynamics.client.screen.tile.ScreenDO2OProcessor;
import electrodynamics.client.screen.tile.ScreenElectricFurnace;
import electrodynamics.client.screen.tile.ScreenElectricFurnaceDouble;
import electrodynamics.client.screen.tile.ScreenElectricFurnaceTriple;
import electrodynamics.client.screen.tile.ScreenFermentationPlant;
import electrodynamics.client.screen.tile.ScreenFluidTankGeneric;
import electrodynamics.client.screen.tile.ScreenHydroelectricGenerator;
import electrodynamics.client.screen.tile.ScreenMineralWasher;
import electrodynamics.client.screen.tile.ScreenO2OProcessor;
import electrodynamics.client.screen.tile.ScreenO2OProcessorDouble;
import electrodynamics.client.screen.tile.ScreenO2OProcessorTriple;
import electrodynamics.client.screen.tile.ScreenSolarPanel;
import electrodynamics.client.screen.tile.ScreenWindmill;
import electrodynamics.common.block.subtype.SubtypeGlass;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.item.gear.tools.electric.ItemElectricChainsaw;
import electrodynamics.common.item.gear.tools.electric.ItemElectricDrill;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import electrodynamics.registers.ElectrodynamicsBlocks;
import electrodynamics.registers.ElectrodynamicsEntities;
import electrodynamics.registers.ElectrodynamicsItems;
import electrodynamics.registers.ElectrodynamicsMenuTypes;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = References.ID, bus = Bus.MOD, value = { Dist.CLIENT })
public class ClientRegister {

	private static final String BLOCK_LOC = References.ID + ":block/";
	private static final String CUSTOM_LOC = References.ID + ":custom/";

	public static HashMap<ResourceLocation, TextureAtlasSprite> CACHED_TEXTUREATLASSPRITES = new HashMap<>();
	// for registration purposes only!
	private static List<ResourceLocation> customTextures = new ArrayList<>();

	public static final ResourceLocation ON = new ResourceLocation("on");

	@SubscribeEvent
	public static void onModelEvent(ModelRegistryEvent event) {
		ModelLoader.addSpecialModel(MODEL_ADVSOLARTOP);
		ModelLoader.addSpecialModel(MODEL_BATTERYBOX);
		ModelLoader.addSpecialModel(MODEL_BATTERYBOX2);
		ModelLoader.addSpecialModel(MODEL_BATTERYBOX3);
		ModelLoader.addSpecialModel(MODEL_BATTERYBOX4);
		ModelLoader.addSpecialModel(MODEL_BATTERYBOX5);
		ModelLoader.addSpecialModel(MODEL_BATTERYBOX6);
		ModelLoader.addSpecialModel(MODEL_BATTERYBOX7);
		ModelLoader.addSpecialModel(MODEL_LITHIUMBATTERYBOX);
		ModelLoader.addSpecialModel(MODEL_LITHIUMBATTERYBOX2);
		ModelLoader.addSpecialModel(MODEL_LITHIUMBATTERYBOX3);
		ModelLoader.addSpecialModel(MODEL_LITHIUMBATTERYBOX4);
		ModelLoader.addSpecialModel(MODEL_LITHIUMBATTERYBOX5);
		ModelLoader.addSpecialModel(MODEL_LITHIUMBATTERYBOX6);
		ModelLoader.addSpecialModel(MODEL_LITHIUMBATTERYBOX7);
		ModelLoader.addSpecialModel(MODEL_HYDROELECTRICGENERATORBLADES);
		ModelLoader.addSpecialModel(MODEL_WINDMILLBLADES);
		ModelLoader.addSpecialModel(MODEL_MINERALCRUSHERHANDLE);
		ModelLoader.addSpecialModel(MODEL_MINERALCRUSHERDOUBLEHANDLE);
		ModelLoader.addSpecialModel(MODEL_MINERALCRUSHERTRIPLEHANDLE);
		ModelLoader.addSpecialModel(MODEL_MINERALGRINDERWHEEL);
		ModelLoader.addSpecialModel(MODEL_CHEMICALMIXERBASE);
		ModelLoader.addSpecialModel(MODEL_CHEMICALMIXERBLADES);
		ModelLoader.addSpecialModel(MODEL_RODSTEEL);
		ModelLoader.addSpecialModel(MODEL_RODSTAINLESSSTEEL);
		ModelLoader.addSpecialModel(MODEL_RODHSLASTEEL);
		ModelLoader.addSpecialModel(MODEL_LATHESHAFT);
	}

	public static final ResourceLocation MODEL_ADVSOLARTOP = new ResourceLocation(BLOCK_LOC + "advancedsolarpaneltop");
	public static final ResourceLocation MODEL_BATTERYBOX = new ResourceLocation(BLOCK_LOC + "batterybox");
	public static final ResourceLocation MODEL_BATTERYBOX2 = new ResourceLocation(BLOCK_LOC + "batterybox2");
	public static final ResourceLocation MODEL_BATTERYBOX3 = new ResourceLocation(BLOCK_LOC + "batterybox3");
	public static final ResourceLocation MODEL_BATTERYBOX4 = new ResourceLocation(BLOCK_LOC + "batterybox4");
	public static final ResourceLocation MODEL_BATTERYBOX5 = new ResourceLocation(BLOCK_LOC + "batterybox5");
	public static final ResourceLocation MODEL_BATTERYBOX6 = new ResourceLocation(BLOCK_LOC + "batterybox6");
	public static final ResourceLocation MODEL_BATTERYBOX7 = new ResourceLocation(BLOCK_LOC + "batterybox7");
	public static final ResourceLocation MODEL_LITHIUMBATTERYBOX = new ResourceLocation(BLOCK_LOC + "lithiumbatterybox");
	public static final ResourceLocation MODEL_LITHIUMBATTERYBOX2 = new ResourceLocation(BLOCK_LOC + "lithiumbatterybox2");
	public static final ResourceLocation MODEL_LITHIUMBATTERYBOX3 = new ResourceLocation(BLOCK_LOC + "lithiumbatterybox3");
	public static final ResourceLocation MODEL_LITHIUMBATTERYBOX4 = new ResourceLocation(BLOCK_LOC + "lithiumbatterybox4");
	public static final ResourceLocation MODEL_LITHIUMBATTERYBOX5 = new ResourceLocation(BLOCK_LOC + "lithiumbatterybox5");
	public static final ResourceLocation MODEL_LITHIUMBATTERYBOX6 = new ResourceLocation(BLOCK_LOC + "lithiumbatterybox6");
	public static final ResourceLocation MODEL_LITHIUMBATTERYBOX7 = new ResourceLocation(BLOCK_LOC + "lithiumbatterybox7");

	public static final ResourceLocation MODEL_HYDROELECTRICGENERATORBLADES = new ResourceLocation(BLOCK_LOC + "hydroelectricgeneratorblades");
	public static final ResourceLocation MODEL_WINDMILLBLADES = new ResourceLocation(BLOCK_LOC + "windmillblades");
	public static final ResourceLocation MODEL_MINERALCRUSHERHANDLE = new ResourceLocation(BLOCK_LOC + "mineralcrusherhandle");
	public static final ResourceLocation MODEL_MINERALCRUSHERDOUBLEHANDLE = new ResourceLocation(BLOCK_LOC + "mineralcrusherdoublehandle");
	public static final ResourceLocation MODEL_MINERALCRUSHERTRIPLEHANDLE = new ResourceLocation(BLOCK_LOC + "mineralcrushertriplehandle");
	public static final ResourceLocation MODEL_MINERALGRINDERWHEEL = new ResourceLocation(BLOCK_LOC + "mineralgrinderwheel");
	public static final ResourceLocation MODEL_CHEMICALMIXERBASE = new ResourceLocation(BLOCK_LOC + "chemicalmixerbase");
	public static final ResourceLocation MODEL_CHEMICALMIXERBLADES = new ResourceLocation(BLOCK_LOC + "chemicalmixerblades");
	public static final ResourceLocation MODEL_LATHESHAFT = new ResourceLocation(BLOCK_LOC + "latheshaft");

	public static final ResourceLocation MODEL_RODSTEEL = new ResourceLocation(References.ID + ":entity/rodsteel");
	public static final ResourceLocation MODEL_RODSTAINLESSSTEEL = new ResourceLocation(References.ID + ":entity/rodstainlesssteel");
	public static final ResourceLocation MODEL_RODHSLASTEEL = new ResourceLocation(References.ID + ":entity/rodhslasteel");

	public static final ResourceLocation TEXTURE_RODSTEEL = new ResourceLocation(References.ID + ":textures/entity/projectile/rodsteel.png");
	public static final ResourceLocation TEXTURE_RODSTAINLESSSTEEL = new ResourceLocation(References.ID + ":textures/entity/projectile/rodstainlesssteel.png");
	public static final ResourceLocation TEXTURE_RODHSLASTEEL = new ResourceLocation(References.ID + ":textures/entity/projectile/rodhslasteel.png");

	public static void setup() {

		ClientEvents.init();

		RenderingRegistry.registerEntityRenderingHandler(ElectrodynamicsEntities.ENTITY_ENERGYBLAST.get(), RenderEnergyBlast::new);
		RenderingRegistry.registerEntityRenderingHandler(ElectrodynamicsEntities.ENTITY_METALROD.get(), RenderMetalRod::new);

		ClientRegistry.bindTileEntityRenderer(ElectrodynamicsBlockTypes.TILE_ADVANCEDSOLARPANEL.get(), RenderAdvancedSolarPanel::new);
		ClientRegistry.bindTileEntityRenderer(ElectrodynamicsBlockTypes.TILE_BATTERYBOX.get(), RenderBatteryBox::new);
		ClientRegistry.bindTileEntityRenderer(ElectrodynamicsBlockTypes.TILE_LITHIUMBATTERYBOX.get(), RenderLithiumBatteryBox::new);
		ClientRegistry.bindTileEntityRenderer(ElectrodynamicsBlockTypes.TILE_HYDROELECTRICGENERATOR.get(), RenderHydroelectricGenerator::new);
		ClientRegistry.bindTileEntityRenderer(ElectrodynamicsBlockTypes.TILE_WINDMILL.get(), RenderWindmill::new);
		ClientRegistry.bindTileEntityRenderer(ElectrodynamicsBlockTypes.TILE_MINERALCRUSHER.get(), RenderMineralCrusher::new);
		ClientRegistry.bindTileEntityRenderer(ElectrodynamicsBlockTypes.TILE_MINERALCRUSHERDOUBLE.get(), RenderMineralCrusherDouble::new);
		ClientRegistry.bindTileEntityRenderer(ElectrodynamicsBlockTypes.TILE_MINERALCRUSHERTRIPLE.get(), RenderMineralCrusherTriple::new);
		ClientRegistry.bindTileEntityRenderer(ElectrodynamicsBlockTypes.TILE_MINERALGRINDER.get(), RenderMineralGrinder::new);
		ClientRegistry.bindTileEntityRenderer(ElectrodynamicsBlockTypes.TILE_MINERALGRINDERDOUBLE.get(), RenderMineralGrinderDouble::new);
		ClientRegistry.bindTileEntityRenderer(ElectrodynamicsBlockTypes.TILE_MINERALGRINDERTRIPLE.get(), RenderMineralGrinderTriple::new);
		ClientRegistry.bindTileEntityRenderer(ElectrodynamicsBlockTypes.TILE_FERMENTATIONPLANT.get(), RenderFermentationPlant::new);
		ClientRegistry.bindTileEntityRenderer(ElectrodynamicsBlockTypes.TILE_COMBUSTIONCHAMBER.get(), RenderCombustionChamber::new);
		ClientRegistry.bindTileEntityRenderer(ElectrodynamicsBlockTypes.TILE_MINERALWASHER.get(), RenderMineralWasher::new);
		ClientRegistry.bindTileEntityRenderer(ElectrodynamicsBlockTypes.TILE_CHEMICALMIXER.get(), RenderChemicalMixer::new);
		ClientRegistry.bindTileEntityRenderer(ElectrodynamicsBlockTypes.TILE_MULTIMETERBLOCK.get(), RenderMultimeterBlock::new);
		ClientRegistry.bindTileEntityRenderer(ElectrodynamicsBlockTypes.TILE_LATHE.get(), RenderLathe::new);
		ClientRegistry.bindTileEntityRenderer(ElectrodynamicsBlockTypes.TILE_CHARGERLV.get(), RenderChargerGeneric::new);
		ClientRegistry.bindTileEntityRenderer(ElectrodynamicsBlockTypes.TILE_CHARGERMV.get(), RenderChargerGeneric::new);
		ClientRegistry.bindTileEntityRenderer(ElectrodynamicsBlockTypes.TILE_CHARGERHV.get(), RenderChargerGeneric::new);

		ScreenManager.register(ElectrodynamicsMenuTypes.CONTAINER_COALGENERATOR.get(), ScreenCoalGenerator::new);
		ScreenManager.register(ElectrodynamicsMenuTypes.CONTAINER_ELECTRICFURNACE.get(), ScreenElectricFurnace::new);
		ScreenManager.register(ElectrodynamicsMenuTypes.CONTAINER_ELECTRICFURNACEDOUBLE.get(), ScreenElectricFurnaceDouble::new);
		ScreenManager.register(ElectrodynamicsMenuTypes.CONTAINER_ELECTRICFURNACETRIPLE.get(), ScreenElectricFurnaceTriple::new);
		ScreenManager.register(ElectrodynamicsMenuTypes.CONTAINER_O2OPROCESSOR.get(), ScreenO2OProcessor::new);
		ScreenManager.register(ElectrodynamicsMenuTypes.CONTAINER_O2OPROCESSORDOUBLE.get(), ScreenO2OProcessorDouble::new);
		ScreenManager.register(ElectrodynamicsMenuTypes.CONTAINER_O2OPROCESSORTRIPLE.get(), ScreenO2OProcessorTriple::new);
		ScreenManager.register(ElectrodynamicsMenuTypes.CONTAINER_DO2OPROCESSOR.get(), ScreenDO2OProcessor::new);
		ScreenManager.register(ElectrodynamicsMenuTypes.CONTAINER_BATTERYBOX.get(), ScreenBatteryBox::new);
		ScreenManager.register(ElectrodynamicsMenuTypes.CONTAINER_FERMENTATIONPLANT.get(), ScreenFermentationPlant::new);
		ScreenManager.register(ElectrodynamicsMenuTypes.CONTAINER_MINERALWASHER.get(), ScreenMineralWasher::new);
		ScreenManager.register(ElectrodynamicsMenuTypes.CONTAINER_CHEMICALMIXER.get(), ScreenChemicalMixer::new);
		ScreenManager.register(ElectrodynamicsMenuTypes.CONTAINER_CHEMICALCRYSTALLIZER.get(), ScreenChemicalCrystallizer::new);
		ScreenManager.register(ElectrodynamicsMenuTypes.CONTAINER_CHARGER.get(), ScreenChargerGeneric::new);
		ScreenManager.register(ElectrodynamicsMenuTypes.CONTAINER_TANK.get(), ScreenFluidTankGeneric::new);
		ScreenManager.register(ElectrodynamicsMenuTypes.CONTAINER_COMBUSTION_CHAMBER.get(), ScreenCombustionChamber::new);
		ScreenManager.register(ElectrodynamicsMenuTypes.CONTAINER_SOLARPANEL.get(), ScreenSolarPanel::new);
		ScreenManager.register(ElectrodynamicsMenuTypes.CONTAINER_WINDMILL.get(), ScreenWindmill::new);
		ScreenManager.register(ElectrodynamicsMenuTypes.CONTAINER_HYDROELECTRICGENERATOR.get(), ScreenHydroelectricGenerator::new);
		ScreenManager.register(ElectrodynamicsMenuTypes.CONTAINER_CREATIVEPOWERSOURCE.get(), ScreenCreativePowerSource::new);
		ScreenManager.register(ElectrodynamicsMenuTypes.CONTAINER_CREATIVEFLUIDSOURCE.get(), ScreenCreativeFluidSource::new);
		ScreenManager.register(ElectrodynamicsMenuTypes.CONTAINER_GUIDEBOOK.get(), ScreenGuidebook::new);

		for (SubtypeGlass glass : SubtypeGlass.values()) {

			RenderTypeLookup.setRenderLayer(ElectrodynamicsBlocks.getBlock(glass), RenderType.cutout());

		}
		RenderTypeLookup.setRenderLayer(ElectrodynamicsBlocks.getBlock(SubtypeMachine.circuitbreaker), ClientRegister::shouldMultilayerRender);
		RenderTypeLookup.setRenderLayer(ElectrodynamicsBlocks.getBlock(SubtypeMachine.chemicalcrystallizer), ClientRegister::shouldMultilayerRender);
		RenderTypeLookup.setRenderLayer(ElectrodynamicsBlocks.getBlock(SubtypeMachine.coalgeneratorrunning), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ElectrodynamicsBlocks.getBlock(SubtypeMachine.combustionchamber), ClientRegister::shouldMultilayerRender);
		RenderTypeLookup.setRenderLayer(ElectrodynamicsBlocks.getBlock(SubtypeMachine.fermentationplant), ClientRegister::shouldMultilayerRender);
		RenderTypeLookup.setRenderLayer(ElectrodynamicsBlocks.getBlock(SubtypeMachine.mineralwasher), ClientRegister::shouldMultilayerRender);
		RenderTypeLookup.setRenderLayer(ElectrodynamicsBlocks.getBlock(SubtypeMachine.wiremill), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ElectrodynamicsBlocks.getBlock(SubtypeMachine.tanksteel), ClientRegister::shouldMultilayerRender);
		RenderTypeLookup.setRenderLayer(ElectrodynamicsBlocks.getBlock(SubtypeMachine.tankhsla), ClientRegister::shouldMultilayerRender);
		RenderTypeLookup.setRenderLayer(ElectrodynamicsBlocks.getBlock(SubtypeMachine.tankreinforced), ClientRegister::shouldMultilayerRender);

		RenderTypeLookup.setRenderLayer(ElectrodynamicsBlocks.multi, RenderType.cutout());

		ItemModelsProperties.register(ElectrodynamicsItems.ITEM_ELECTRICDRILL.get(), ON, (stack, world, entity) -> entity != null && (entity.getMainHandItem() == stack || entity.getOffhandItem() == stack) && ((ItemElectricDrill) stack.getItem()).getJoulesStored(stack) > ((ItemElectricDrill) stack.getItem()).getElectricProperties().extract.getJoules() ? 1 : 0);
		ItemModelsProperties.register(ElectrodynamicsItems.ITEM_ELECTRICCHAINSAW.get(), ON, (stack, world, entity) -> entity != null && (entity.getMainHandItem() == stack || entity.getOffhandItem() == stack) && ((ItemElectricChainsaw) stack.getItem()).getJoulesStored(stack) > ((ItemElectricChainsaw) stack.getItem()).getElectricProperties().extract.getJoules() ? 1 : 0);

		ScreenGuidebook.addGuidebookModule(new ModuleElectrodynamics());
	}

	public static boolean shouldMultilayerRender(RenderType type) {
		return type == RenderType.translucent() || type == RenderType.solid();
	}

}
