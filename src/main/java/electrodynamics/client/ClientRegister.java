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
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fmlclient.registry.ClientRegistry;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = References.ID, bus = Bus.MOD, value = { Dist.CLIENT })
public class ClientRegister {

    private static final String BLOCK_LOC = References.ID + ":block/";

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
		ModelLoader.addSpecialModel(MODEL_CHEMICALMIXERSULFURICACID);
		ModelLoader.addSpecialModel(MODEL_RODSTEEL);
		ModelLoader.addSpecialModel(MODEL_RODSTAINLESSSTEEL);
		ModelLoader.addSpecialModel(MODEL_RODHSLASTEEL);
		ModelLoader.addSpecialModel(MODEL_LATHE);
		ModelLoader.addSpecialModel(MODEL_LATHESHAFT);
    }

    public static final ResourceLocation MODEL_ADVSOLARTOP = new ResourceLocation(BLOCK_LOC + "advancedsolarpaneltop");
    public static final ResourceLocation MODEL_ADVSOLARBASE = new ResourceLocation(BLOCK_LOC + "advancedsolarpanelbase");
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
    public static final ResourceLocation MODEL_MINERALCRUSHERBASE = new ResourceLocation(BLOCK_LOC + "mineralcrusherbase");
    public static final ResourceLocation MODEL_MINERALCRUSHERHANDLE = new ResourceLocation(BLOCK_LOC + "mineralcrusherhandle");
    public static final ResourceLocation MODEL_MINERALCRUSHERDOUBLEBASE = new ResourceLocation(BLOCK_LOC + "mineralcrusherdoublebase");
    public static final ResourceLocation MODEL_MINERALCRUSHERDOUBLEHANDLE = new ResourceLocation(BLOCK_LOC + "mineralcrusherdoublehandle");
    public static final ResourceLocation MODEL_MINERALCRUSHERTRIPLEBASE = new ResourceLocation(BLOCK_LOC + "mineralcrushertriplebase");
    public static final ResourceLocation MODEL_MINERALCRUSHERTRIPLEHANDLE = new ResourceLocation(BLOCK_LOC + "mineralcrushertriplehandle");
    public static final ResourceLocation MODEL_MINERALGRINDERBASE = new ResourceLocation(BLOCK_LOC + "mineralgrinderbase");
    public static final ResourceLocation MODEL_MINERALGRINDERDOUBLEBASE = new ResourceLocation(BLOCK_LOC + "mineralgrinderdoublebase");
    public static final ResourceLocation MODEL_MINERALGRINDERTRIPLEBASE = new ResourceLocation(BLOCK_LOC + "mineralgrindertriplebase");
    public static final ResourceLocation MODEL_MINERALGRINDERWHEEL = new ResourceLocation(BLOCK_LOC + "mineralgrinderwheel");
    public static final ResourceLocation MODEL_FERMENTATIONPLANTWATER = new ResourceLocation(BLOCK_LOC + "fermentationplantwater");
    public static final ResourceLocation MODEL_FERMENTATIONPLANTETHANOL = new ResourceLocation(BLOCK_LOC + "fermentationplantethanol");
    public static final ResourceLocation MODEL_COMBUSTIONCHAMBERETHANOL = new ResourceLocation(BLOCK_LOC + "combustionchamberethanol");
    public static final ResourceLocation MODEL_CHEMICALMIXERBASE = new ResourceLocation(BLOCK_LOC + "chemicalmixerbase");
    public static final ResourceLocation MODEL_CHEMICALMIXERBLADES = new ResourceLocation(BLOCK_LOC + "chemicalmixerblades");
    public static final ResourceLocation MODEL_CHEMICALMIXERWATER = new ResourceLocation(BLOCK_LOC + "chemicalmixerwater");
    public static final ResourceLocation MODEL_CHEMICALMIXERSULFURICACID = new ResourceLocation(BLOCK_LOC + "chemicalmixersulfuricacid");
    public static final ResourceLocation MODEL_LATHE = new ResourceLocation(BLOCK_LOC + "lathe");
    public static final ResourceLocation MODEL_LATHESHAFT = new ResourceLocation(BLOCK_LOC + "latheshaft");
    public static final ResourceLocation MODEL_RODSTEEL = new ResourceLocation(References.ID + ":entity/rodsteel");
    public static final ResourceLocation MODEL_RODSTAINLESSSTEEL = new ResourceLocation(References.ID + ":entity/rodstainlesssteel");
    public static final ResourceLocation MODEL_RODHSLASTEEL = new ResourceLocation(References.ID + ":entity/rodhslasteel");

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
	
		MenuScreens.register(DeferredRegisters.CONTAINER_COALGENERATOR.get(), ScreenCoalGenerator::new);
		MenuScreens.register(DeferredRegisters.CONTAINER_ELECTRICFURNACE.get(), ScreenElectricFurnace::new);
		MenuScreens.register(DeferredRegisters.CONTAINER_ELECTRICFURNACEDOUBLE.get(), ScreenElectricFurnaceDouble::new);
		MenuScreens.register(DeferredRegisters.CONTAINER_ELECTRICFURNACETRIPLE.get(), ScreenElectricFurnaceTriple::new);
		MenuScreens.register(DeferredRegisters.CONTAINER_O2OPROCESSOR.get(), ScreenO2OProcessor::new);
		MenuScreens.register(DeferredRegisters.CONTAINER_O2OPROCESSORDOUBLE.get(), ScreenO2OProcessorDouble::new);
		MenuScreens.register(DeferredRegisters.CONTAINER_O2OPROCESSORTRIPLE.get(), ScreenO2OProcessorTriple::new);
		MenuScreens.register(DeferredRegisters.CONTAINER_DO2OPROCESSOR.get(), ScreenDO2OProcessor::new);
		MenuScreens.register(DeferredRegisters.CONTAINER_BATTERYBOX.get(), ScreenBatteryBox::new);
		MenuScreens.register(DeferredRegisters.CONTAINER_LITHIUMBATTERYBOX.get(), ScreenLithiumBatteryBox::new);
		MenuScreens.register(DeferredRegisters.CONTAINER_FERMENTATIONPLANT.get(), ScreenFermentationPlant::new);
		MenuScreens.register(DeferredRegisters.CONTAINER_MINERALWASHER.get(), ScreenMineralWasher::new);
		MenuScreens.register(DeferredRegisters.CONTAINER_CHEMICALMIXER.get(), ScreenChemicalMixer::new);
		MenuScreens.register(DeferredRegisters.CONTAINER_CHEMICALCRYSTALLIZER.get(), ScreenChemicalCrystallizer::new);
		MenuScreens.register(DeferredRegisters.CONTAINER_CHARGER.get(), ScreenChargerGeneric::new);
		MenuScreens.register(DeferredRegisters.CONTAINER_TANK.get(), ScreenTankGeneric::new);
	
		ItemBlockRenderTypes.setRenderLayer(DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.coalgeneratorrunning), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.wiremill), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.fermentationplant),
			ClientRegister::shouldMultilayerRender);
		ItemBlockRenderTypes.setRenderLayer(DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.combustionchamber),
			ClientRegister::shouldMultilayerRender);
		ItemBlockRenderTypes.setRenderLayer(DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.mineralwasher),
			ClientRegister::shouldMultilayerRender);
		ItemBlockRenderTypes.setRenderLayer(DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.chemicalcrystallizer),
			ClientRegister::shouldMultilayerRender);
		ItemBlockRenderTypes.setRenderLayer(DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.circuitbreaker),
			ClientRegister::shouldMultilayerRender);
		ItemBlockRenderTypes.setRenderLayer(DeferredRegisters.multi, RenderType.cutout());
		ItemProperties
			.register(DeferredRegisters.ITEM_ELECTRICDRILL.get(), new ResourceLocation("on"),
				(stack, world, entity, call) -> entity != null && (entity.getMainHandItem() == stack || entity.getOffhandItem() == stack)
					&& ((ItemElectricDrill) stack.getItem())
						.getJoulesStored(stack) > ((ItemElectricDrill) stack.getItem()).getElectricProperties().extract.getJoules()
							? 1
							: 0);
		ItemProperties
			.register(DeferredRegisters.ITEM_ELECTRICCHAINSAW.get(), new ResourceLocation("on"),
				(stack, world, entity,
					call) -> entity != null && (entity.getMainHandItem() == stack || entity.getOffhandItem() == stack)
						&& ((ItemElectricChainsaw) stack.getItem()).getJoulesStored(
							stack) > ((ItemElectricChainsaw) stack.getItem()).getElectricProperties().extract.getJoules() ? 1
								: 0);
    }
    
    //TODO the block entities get handled through here as well; not quite sure how tho
    //     was looking into the BlockEntityRendererProvider class
    
    //idk how you want to handle this so I just put this here 
    @SubscribeEvent
    public static void registerEntities(EntityRenderersEvent.RegisterRenderers event) {
    	event.registerEntityRenderer(DeferredRegisters.ENTITY_ENERGYBLAST.get(), RenderEnergyBlast::new);
    	event.registerEntityRenderer(DeferredRegisters.ENTITY_METALROD.get(), RenderMetalRod::new);
    	
    	//event.registerBlockEntityRenderer(null, null);
    	
    }

    public static boolean shouldMultilayerRender(RenderType type) {
    	return type == RenderType.translucent() || type == RenderType.solid();
    }

}
