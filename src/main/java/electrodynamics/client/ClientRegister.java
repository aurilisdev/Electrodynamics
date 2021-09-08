package electrodynamics.client;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.References;
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
import electrodynamics.client.render.tile.RenderTankGeneric;
import electrodynamics.client.render.tile.RenderWindmill;
import electrodynamics.client.screen.ScreenBatteryBox;
import electrodynamics.client.screen.ScreenChargerGeneric;
import electrodynamics.client.screen.ScreenChemicalCrystallizer;
import electrodynamics.client.screen.ScreenChemicalMixer;
import electrodynamics.client.screen.ScreenCoalGenerator;
import electrodynamics.client.screen.ScreenDO2OProcessor;
import electrodynamics.client.screen.ScreenElectricFurnace;
import electrodynamics.client.screen.ScreenElectricFurnaceDouble;
import electrodynamics.client.screen.ScreenElectricFurnaceTriple;
import electrodynamics.client.screen.ScreenFermentationPlant;
import electrodynamics.client.screen.ScreenLithiumBatteryBox;
import electrodynamics.client.screen.ScreenMineralWasher;
import electrodynamics.client.screen.ScreenO2OProcessor;
import electrodynamics.client.screen.ScreenO2OProcessorDouble;
import electrodynamics.client.screen.ScreenO2OProcessorTriple;
import electrodynamics.client.screen.ScreenTankGeneric;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.item.gear.tools.electric.ItemElectricChainsaw;
import electrodynamics.common.item.gear.tools.electric.ItemElectricDrill;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = References.ID, bus = Bus.MOD, value = { Dist.CLIENT })
public class ClientRegister {
    @SubscribeEvent
    public static void onModelEvent(ModelRegistryEvent event) {
	ModelLoader.addSpecialModel(MODEL_ADVSOLARTOP);
	ModelLoader.addSpecialModel(MODEL_ADVSOLARBASE);
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
	ModelLoader.addSpecialModel(MODEL_MINERALCRUSHERBASE);
	ModelLoader.addSpecialModel(MODEL_MINERALCRUSHERHANDLE);
	ModelLoader.addSpecialModel(MODEL_MINERALCRUSHERDOUBLEBASE);
	ModelLoader.addSpecialModel(MODEL_MINERALCRUSHERDOUBLEHANDLE);
	ModelLoader.addSpecialModel(MODEL_MINERALCRUSHERTRIPLEBASE);
	ModelLoader.addSpecialModel(MODEL_MINERALCRUSHERTRIPLEHANDLE);
	ModelLoader.addSpecialModel(MODEL_MINERALGRINDERBASE);
	ModelLoader.addSpecialModel(MODEL_MINERALGRINDERWHEEL);
	ModelLoader.addSpecialModel(MODEL_MINERALGRINDERDOUBLEBASE);
	ModelLoader.addSpecialModel(MODEL_MINERALGRINDERTRIPLEBASE);
	ModelLoader.addSpecialModel(MODEL_FERMENTATIONPLANTWATER);
	ModelLoader.addSpecialModel(MODEL_FERMENTATIONPLANTETHANOL);
	ModelLoader.addSpecialModel(MODEL_COMBUSTIONCHAMBERETHANOL);
	ModelLoader.addSpecialModel(MODEL_CHEMICALMIXERBASE);
	ModelLoader.addSpecialModel(MODEL_CHEMICALMIXERBLADES);
	ModelLoader.addSpecialModel(MODEL_CHEMICALMIXERWATER);
	ModelLoader.addSpecialModel(MODEL_RODSTEEL);
	ModelLoader.addSpecialModel(MODEL_RODSTAINLESSSTEEL);
	ModelLoader.addSpecialModel(MODEL_RODHSLASTEEL);
	ModelLoader.addSpecialModel(MODEL_LATHE);
	ModelLoader.addSpecialModel(MODEL_LATHESHAFT);
	ModelLoader.addSpecialModel(MODEL_EMPTYCANISTER);
    }

    public static final ResourceLocation MODEL_ADVSOLARTOP = new ResourceLocation(References.ID + ":block/advancedsolarpaneltop");
    public static final ResourceLocation MODEL_ADVSOLARBASE = new ResourceLocation(References.ID + ":block/advancedsolarpanelbase");
    public static final ResourceLocation MODEL_BATTERYBOX = new ResourceLocation(References.ID + ":block/batterybox");
    public static final ResourceLocation MODEL_BATTERYBOX2 = new ResourceLocation(References.ID + ":block/batterybox2");
    public static final ResourceLocation MODEL_BATTERYBOX3 = new ResourceLocation(References.ID + ":block/batterybox3");
    public static final ResourceLocation MODEL_BATTERYBOX4 = new ResourceLocation(References.ID + ":block/batterybox4");
    public static final ResourceLocation MODEL_BATTERYBOX5 = new ResourceLocation(References.ID + ":block/batterybox5");
    public static final ResourceLocation MODEL_BATTERYBOX6 = new ResourceLocation(References.ID + ":block/batterybox6");
    public static final ResourceLocation MODEL_BATTERYBOX7 = new ResourceLocation(References.ID + ":block/batterybox7");
    public static final ResourceLocation MODEL_LITHIUMBATTERYBOX = new ResourceLocation(References.ID + ":block/lithiumbatterybox");
    public static final ResourceLocation MODEL_LITHIUMBATTERYBOX2 = new ResourceLocation(References.ID + ":block/lithiumbatterybox2");
    public static final ResourceLocation MODEL_LITHIUMBATTERYBOX3 = new ResourceLocation(References.ID + ":block/lithiumbatterybox3");
    public static final ResourceLocation MODEL_LITHIUMBATTERYBOX4 = new ResourceLocation(References.ID + ":block/lithiumbatterybox4");
    public static final ResourceLocation MODEL_LITHIUMBATTERYBOX5 = new ResourceLocation(References.ID + ":block/lithiumbatterybox5");
    public static final ResourceLocation MODEL_LITHIUMBATTERYBOX6 = new ResourceLocation(References.ID + ":block/lithiumbatterybox6");
    public static final ResourceLocation MODEL_LITHIUMBATTERYBOX7 = new ResourceLocation(References.ID + ":block/lithiumbatterybox7");
    public static final ResourceLocation MODEL_HYDROELECTRICGENERATORBLADES = new ResourceLocation(
	    References.ID + ":block/hydroelectricgeneratorblades");
    public static final ResourceLocation MODEL_WINDMILLBLADES = new ResourceLocation(References.ID + ":block/windmillblades");
    public static final ResourceLocation MODEL_MINERALCRUSHERBASE = new ResourceLocation(References.ID + ":block/mineralcrusherbase");
    public static final ResourceLocation MODEL_MINERALCRUSHERHANDLE = new ResourceLocation(References.ID + ":block/mineralcrusherhandle");
    public static final ResourceLocation MODEL_MINERALCRUSHERDOUBLEBASE = new ResourceLocation(References.ID + ":block/mineralcrusherdoublebase");
    public static final ResourceLocation MODEL_MINERALCRUSHERDOUBLEHANDLE = new ResourceLocation(References.ID + ":block/mineralcrusherdoublehandle");
    public static final ResourceLocation MODEL_MINERALCRUSHERTRIPLEBASE = new ResourceLocation(References.ID + ":block/mineralcrushertriplebase");
    public static final ResourceLocation MODEL_MINERALCRUSHERTRIPLEHANDLE = new ResourceLocation(References.ID + ":block/mineralcrushertriplehandle");
    public static final ResourceLocation MODEL_MINERALGRINDERBASE = new ResourceLocation(References.ID + ":block/mineralgrinderbase");
    public static final ResourceLocation MODEL_MINERALGRINDERDOUBLEBASE = new ResourceLocation(References.ID + ":block/mineralgrinderdoublebase");
    public static final ResourceLocation MODEL_MINERALGRINDERTRIPLEBASE = new ResourceLocation(References.ID + ":block/mineralgrindertriplebase");
    public static final ResourceLocation MODEL_MINERALGRINDERWHEEL = new ResourceLocation(References.ID + ":block/mineralgrinderwheel");
    public static final ResourceLocation MODEL_FERMENTATIONPLANTWATER = new ResourceLocation(References.ID + ":block/fermentationplantwater");
    public static final ResourceLocation MODEL_FERMENTATIONPLANTETHANOL = new ResourceLocation(References.ID + ":block/fermentationplantethanol");
    public static final ResourceLocation MODEL_COMBUSTIONCHAMBERETHANOL = new ResourceLocation(References.ID + ":block/combustionchamberethanol");
    public static final ResourceLocation MODEL_CHEMICALMIXERBASE = new ResourceLocation(References.ID + ":block/chemicalmixerbase");
    public static final ResourceLocation MODEL_CHEMICALMIXERBLADES = new ResourceLocation(References.ID + ":block/chemicalmixerblades");
    public static final ResourceLocation MODEL_CHEMICALMIXERWATER = new ResourceLocation(References.ID + ":block/chemicalmixerwater");
    public static final ResourceLocation MODEL_LATHE = new ResourceLocation(References.ID + ":block/lathe");
    public static final ResourceLocation MODEL_LATHESHAFT = new ResourceLocation(References.ID + ":block/latheshaft");
    public static final ResourceLocation MODEL_RODSTEEL = new ResourceLocation(References.ID + ":entity/rodsteel");
    public static final ResourceLocation MODEL_RODSTAINLESSSTEEL = new ResourceLocation(References.ID + ":entity/rodstainlesssteel");
    public static final ResourceLocation MODEL_RODHSLASTEEL = new ResourceLocation(References.ID + ":entity/rodhslasteel");
    public static final ResourceLocation MODEL_EMPTYCANISTER = new ResourceLocation(References.ID + ":item/canisterreinforced");
    public static final ResourceLocation MODEL_FLUIDLEVEL = new ResourceLocation(References.ID + "block/fluidlevel");
    
    
    public static final ResourceLocation TEXTURE_RODSTEEL = new ResourceLocation(References.ID + ":textures/entity/projectile/rodsteel.png");
    public static final ResourceLocation TEXTURE_RODSTAINLESSSTEEL = new ResourceLocation(
	    References.ID + ":textures/entity/projectile/rodstainlesssteel.png");
    public static final ResourceLocation TEXTURE_RODHSLASTEEL = new ResourceLocation(References.ID + ":textures/entity/projectile/rodhslasteel.png");

    public static void setup() {
	ClientRegistry.bindTileEntityRenderer(DeferredRegisters.TILE_ADVANCEDSOLARPANEL.get(), RenderAdvancedSolarPanel::new);
	ClientRegistry.bindTileEntityRenderer(DeferredRegisters.TILE_BATTERYBOX.get(), RenderBatteryBox::new);
	ClientRegistry.bindTileEntityRenderer(DeferredRegisters.TILE_LITHIUMBATTERYBOX.get(), RenderLithiumBatteryBox::new);
	ClientRegistry.bindTileEntityRenderer(DeferredRegisters.TILE_HYDROELECTRICGENERATOR.get(), RenderHydroelectricGenerator::new);
	ClientRegistry.bindTileEntityRenderer(DeferredRegisters.TILE_WINDMILL.get(), RenderWindmill::new);
	ClientRegistry.bindTileEntityRenderer(DeferredRegisters.TILE_MINERALCRUSHER.get(), RenderMineralCrusher::new);
	ClientRegistry.bindTileEntityRenderer(DeferredRegisters.TILE_MINERALCRUSHERDOUBLE.get(), RenderMineralCrusherDouble::new);
	ClientRegistry.bindTileEntityRenderer(DeferredRegisters.TILE_MINERALCRUSHERTRIPLE.get(), RenderMineralCrusherTriple::new);
	ClientRegistry.bindTileEntityRenderer(DeferredRegisters.TILE_MINERALGRINDER.get(), RenderMineralGrinder::new);
	ClientRegistry.bindTileEntityRenderer(DeferredRegisters.TILE_MINERALGRINDERDOUBLE.get(), RenderMineralGrinderDouble::new);
	ClientRegistry.bindTileEntityRenderer(DeferredRegisters.TILE_MINERALGRINDERTRIPLE.get(), RenderMineralGrinderTriple::new);
	ClientRegistry.bindTileEntityRenderer(DeferredRegisters.TILE_FERMENTATIONPLANT.get(), RenderFermentationPlant::new);
	ClientRegistry.bindTileEntityRenderer(DeferredRegisters.TILE_COMBUSTIONCHAMBER.get(), RenderCombustionChamber::new);
	ClientRegistry.bindTileEntityRenderer(DeferredRegisters.TILE_MINERALWASHER.get(), RenderMineralWasher::new);
	ClientRegistry.bindTileEntityRenderer(DeferredRegisters.TILE_CHEMICALMIXER.get(), RenderChemicalMixer::new);
	ClientRegistry.bindTileEntityRenderer(DeferredRegisters.TILE_MULTIMETERBLOCK.get(), RenderMultimeterBlock::new);
	ClientRegistry.bindTileEntityRenderer(DeferredRegisters.TILE_LATHE.get(), RenderLathe::new);
	ClientRegistry.bindTileEntityRenderer(DeferredRegisters.TILE_CHARGERLV.get(), RenderChargerGeneric::new);
	ClientRegistry.bindTileEntityRenderer(DeferredRegisters.TILE_CHARGERMV.get(), RenderChargerGeneric::new);
	ClientRegistry.bindTileEntityRenderer(DeferredRegisters.TILE_CHARGERHV.get(), RenderChargerGeneric::new);
	ClientRegistry.bindTileEntityRenderer(DeferredRegisters.TILE_TANKSTEEL.get(), RenderTankGeneric::new);
	
	RenderingRegistry.registerEntityRenderingHandler(DeferredRegisters.ENTITY_ENERGYBLAST.get(), RenderEnergyBlast::new);
	RenderingRegistry.registerEntityRenderingHandler(DeferredRegisters.ENTITY_METALROD.get(), RenderMetalRod::new);

	ScreenManager.registerFactory(DeferredRegisters.CONTAINER_COALGENERATOR.get(), ScreenCoalGenerator::new);
	ScreenManager.registerFactory(DeferredRegisters.CONTAINER_ELECTRICFURNACE.get(), ScreenElectricFurnace::new);
	ScreenManager.registerFactory(DeferredRegisters.CONTAINER_ELECTRICFURNACEDOUBLE.get(), ScreenElectricFurnaceDouble::new);
	ScreenManager.registerFactory(DeferredRegisters.CONTAINER_ELECTRICFURNACETRIPLE.get(), ScreenElectricFurnaceTriple::new);
	ScreenManager.registerFactory(DeferredRegisters.CONTAINER_O2OPROCESSOR.get(), ScreenO2OProcessor::new);
	ScreenManager.registerFactory(DeferredRegisters.CONTAINER_O2OPROCESSORDOUBLE.get(), ScreenO2OProcessorDouble::new);
	ScreenManager.registerFactory(DeferredRegisters.CONTAINER_O2OPROCESSORTRIPLE.get(), ScreenO2OProcessorTriple::new);
	ScreenManager.registerFactory(DeferredRegisters.CONTAINER_DO2OPROCESSOR.get(), ScreenDO2OProcessor::new);
	ScreenManager.registerFactory(DeferredRegisters.CONTAINER_BATTERYBOX.get(), ScreenBatteryBox::new);
	ScreenManager.registerFactory(DeferredRegisters.CONTAINER_LITHIUMBATTERYBOX.get(), ScreenLithiumBatteryBox::new);
	ScreenManager.registerFactory(DeferredRegisters.CONTAINER_FERMENTATIONPLANT.get(), ScreenFermentationPlant::new);
	ScreenManager.registerFactory(DeferredRegisters.CONTAINER_MINERALWASHER.get(), ScreenMineralWasher::new);
	ScreenManager.registerFactory(DeferredRegisters.CONTAINER_CHEMICALMIXER.get(), ScreenChemicalMixer::new);
	ScreenManager.registerFactory(DeferredRegisters.CONTAINER_CHEMICALCRYSTALLIZER.get(), ScreenChemicalCrystallizer::new);
	ScreenManager.registerFactory(DeferredRegisters.CONTAINER_CHARGER.get(), ScreenChargerGeneric::new);
	ScreenManager.registerFactory(DeferredRegisters.CONTAINER_TANK.get(), ScreenTankGeneric::new);
	
	
	RenderTypeLookup.setRenderLayer(DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.coalgeneratorrunning), RenderType.getCutout());
	RenderTypeLookup.setRenderLayer(DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.wiremill), RenderType.getCutout());
	RenderTypeLookup.setRenderLayer(DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.fermentationplant),
		ClientRegister::shouldMultilayerRender);
	RenderTypeLookup.setRenderLayer(DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.combustionchamber),
		ClientRegister::shouldMultilayerRender);
	RenderTypeLookup.setRenderLayer(DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.mineralwasher),
		ClientRegister::shouldMultilayerRender);
	RenderTypeLookup.setRenderLayer(DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.chemicalcrystallizer),
		ClientRegister::shouldMultilayerRender);
	RenderTypeLookup.setRenderLayer(DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.circuitbreaker),
		ClientRegister::shouldMultilayerRender);
	RenderTypeLookup.setRenderLayer(DeferredRegisters.multi, RenderType.getCutout());
	ItemModelsProperties
		.registerProperty(DeferredRegisters.ITEM_ELECTRICDRILL.get(), new ResourceLocation("on"),
			(stack, world, entity) -> entity != null && (entity.getHeldItemMainhand() == stack || entity.getHeldItemOffhand() == stack)
				&& ((ItemElectricDrill) stack.getItem())
					.getJoulesStored(stack) > ((ItemElectricDrill) stack.getItem()).getElectricProperties().extract.getJoules()
						? 1
						: 0);
	ItemModelsProperties
		.registerProperty(DeferredRegisters.ITEM_ELECTRICCHAINSAW.get(), new ResourceLocation("on"),
			(stack, world,
				entity) -> entity != null && (entity.getHeldItemMainhand() == stack || entity.getHeldItemOffhand() == stack)
					&& ((ItemElectricChainsaw) stack.getItem()).getJoulesStored(
						stack) > ((ItemElectricChainsaw) stack.getItem()).getElectricProperties().extract.getJoules() ? 1
							: 0);

    }

    public static boolean shouldMultilayerRender(RenderType type) {
	return type == RenderType.getTranslucent() || type == RenderType.getSolid();
    }
}
