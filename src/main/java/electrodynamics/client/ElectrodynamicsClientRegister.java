package electrodynamics.client;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import electrodynamics.Electrodynamics;
import electrodynamics.client.guidebook.ModuleElectrodynamics;
import electrodynamics.client.render.entity.RenderEnergyBlast;
import electrodynamics.client.render.entity.RenderMetalRod;
import electrodynamics.client.render.tile.RenderAdvancedSolarPanel;
import electrodynamics.client.render.tile.RenderAdvancedTransformer;
import electrodynamics.client.render.tile.RenderBatteryBox;
import electrodynamics.client.render.tile.RenderCarbyneBatteryBox;
import electrodynamics.client.render.tile.RenderChargerGeneric;
import electrodynamics.client.render.tile.RenderChemicalMixer;
import electrodynamics.client.render.tile.RenderCombustionChamber;
import electrodynamics.client.render.tile.RenderConnectBlock;
import electrodynamics.client.render.tile.RenderCoolantResavoir;
import electrodynamics.client.render.tile.RenderElectrolyticSeparator;
import electrodynamics.client.render.tile.RenderFermentationPlant;
import electrodynamics.client.render.tile.RenderFluidPipePump;
import electrodynamics.client.render.tile.RenderHydroelectricGenerator;
import electrodynamics.client.render.tile.RenderLathe;
import electrodynamics.client.render.tile.RenderLithiumBatteryBox;
import electrodynamics.client.render.tile.RenderLogisticalWire;
import electrodynamics.client.render.tile.RenderMineralCrusher;
import electrodynamics.client.render.tile.RenderMineralCrusherDouble;
import electrodynamics.client.render.tile.RenderMineralCrusherTriple;
import electrodynamics.client.render.tile.RenderMineralGrinder;
import electrodynamics.client.render.tile.RenderMineralGrinderDouble;
import electrodynamics.client.render.tile.RenderMineralGrinderTriple;
import electrodynamics.client.render.tile.RenderMineralWasher;
import electrodynamics.client.render.tile.RenderMotorComplex;
import electrodynamics.client.render.tile.RenderMultimeterBlock;
import electrodynamics.client.render.tile.RenderSeismicRelay;
import electrodynamics.client.render.tile.RenderTankGeneric;
import electrodynamics.client.render.tile.RenderWindmill;
import electrodynamics.client.screen.item.ScreenElectricDrill;
import electrodynamics.client.screen.item.ScreenSeismicScanner;
import electrodynamics.client.screen.tile.ScreenAdvancedDowngradeTransformer;
import electrodynamics.client.screen.tile.ScreenAdvancedUpgradeTransformer;
import electrodynamics.client.screen.tile.ScreenBatteryBox;
import electrodynamics.client.screen.tile.ScreenChargerGeneric;
import electrodynamics.client.screen.tile.ScreenChemicalCrystallizer;
import electrodynamics.client.screen.tile.ScreenChemicalMixer;
import electrodynamics.client.screen.tile.ScreenCircuitMonitor;
import electrodynamics.client.screen.tile.ScreenCoalGenerator;
import electrodynamics.client.screen.tile.ScreenCobblestoneGenerator;
import electrodynamics.client.screen.tile.ScreenCombustionChamber;
import electrodynamics.client.screen.tile.ScreenCoolantResavoir;
import electrodynamics.client.screen.tile.ScreenCreativeFluidSource;
import electrodynamics.client.screen.tile.ScreenCreativePowerSource;
import electrodynamics.client.screen.tile.ScreenElectricArcFurnace;
import electrodynamics.client.screen.tile.ScreenElectricArcFurnaceDouble;
import electrodynamics.client.screen.tile.ScreenElectricArcFurnaceTriple;
import electrodynamics.client.screen.tile.ScreenElectricFurnace;
import electrodynamics.client.screen.tile.ScreenElectricFurnaceDouble;
import electrodynamics.client.screen.tile.ScreenElectricFurnaceTriple;
import electrodynamics.client.screen.tile.ScreenElectrolyticSeparator;
import electrodynamics.client.screen.tile.ScreenFermentationPlant;
import electrodynamics.client.screen.tile.ScreenFluidPipeFilter;
import electrodynamics.client.screen.tile.ScreenFluidPipePump;
import electrodynamics.client.screen.tile.ScreenFluidTankGeneric;
import electrodynamics.client.screen.tile.ScreenFluidVoid;
import electrodynamics.client.screen.tile.ScreenHydroelectricGenerator;
import electrodynamics.client.screen.tile.ScreenMineralWasher;
import electrodynamics.client.screen.tile.ScreenMotorComplex;
import electrodynamics.client.screen.tile.ScreenPotentiometer;
import electrodynamics.client.screen.tile.ScreenQuarry;
import electrodynamics.client.screen.tile.ScreenSeismicRelay;
import electrodynamics.client.screen.tile.ScreenSolarPanel;
import electrodynamics.client.screen.tile.ScreenWindmill;
import electrodynamics.common.block.connect.BlockWire;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.item.gear.tools.electric.ItemElectricBaton;
import electrodynamics.common.item.gear.tools.electric.ItemElectricChainsaw;
import electrodynamics.common.item.gear.tools.electric.ItemElectricDrill;
import electrodynamics.registers.ElectrodynamicsBlocks;
import electrodynamics.registers.ElectrodynamicsEntities;
import electrodynamics.registers.ElectrodynamicsItems;
import electrodynamics.registers.ElectrodynamicsMenuTypes;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.AtlasTexture;
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
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import voltaic.client.VoltaicClientRegister;
import voltaic.client.guidebook.ScreenGuidebook;
import voltaic.common.block.BlockCustomGlass;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = Electrodynamics.ID, bus = Bus.MOD, value = { Dist.CLIENT })
public class ElectrodynamicsClientRegister {

    public static final ResourceLocation MODEL_ADVSOLARTOP = Electrodynamics.rl("block/advancedsolarpaneltop");
    public static final ResourceLocation MODEL_BATTERYBOX = Electrodynamics.rl("block/batterybox");
    public static final ResourceLocation MODEL_BATTERYBOX2 = Electrodynamics.rl("block/batterybox2");
    public static final ResourceLocation MODEL_BATTERYBOX3 = Electrodynamics.rl("block/batterybox3");
    public static final ResourceLocation MODEL_BATTERYBOX4 = Electrodynamics.rl("block/batterybox4");
    public static final ResourceLocation MODEL_BATTERYBOX5 = Electrodynamics.rl("block/batterybox5");
    public static final ResourceLocation MODEL_BATTERYBOX6 = Electrodynamics.rl("block/batterybox6");
    public static final ResourceLocation MODEL_BATTERYBOX7 = Electrodynamics.rl("block/batterybox7");
    public static final ResourceLocation MODEL_LITHIUMBATTERYBOX = Electrodynamics.rl("block/lithiumbatterybox");
    public static final ResourceLocation MODEL_LITHIUMBATTERYBOX2 = Electrodynamics.rl("block/lithiumbatterybox2");
    public static final ResourceLocation MODEL_LITHIUMBATTERYBOX3 = Electrodynamics.rl("block/lithiumbatterybox3");
    public static final ResourceLocation MODEL_LITHIUMBATTERYBOX4 = Electrodynamics.rl("block/lithiumbatterybox4");
    public static final ResourceLocation MODEL_LITHIUMBATTERYBOX5 = Electrodynamics.rl("block/lithiumbatterybox5");
    public static final ResourceLocation MODEL_LITHIUMBATTERYBOX6 = Electrodynamics.rl("block/lithiumbatterybox6");
    public static final ResourceLocation MODEL_LITHIUMBATTERYBOX7 = Electrodynamics.rl("block/lithiumbatterybox7");
    public static final ResourceLocation MODEL_CARBYNEBATTERYBOX = Electrodynamics.rl("block/carbynebatterybox");
    public static final ResourceLocation MODEL_CARBYNEBATTERYBOX2 = Electrodynamics.rl("block/carbynebatterybox2");
    public static final ResourceLocation MODEL_CARBYNEBATTERYBOX3 = Electrodynamics.rl("block/carbynebatterybox3");
    public static final ResourceLocation MODEL_CARBYNEBATTERYBOX4 = Electrodynamics.rl("block/carbynebatterybox4");
    public static final ResourceLocation MODEL_CARBYNEBATTERYBOX5 = Electrodynamics.rl("block/carbynebatterybox5");
    public static final ResourceLocation MODEL_CARBYNEBATTERYBOX6 = Electrodynamics.rl("block/carbynebatterybox6");
    public static final ResourceLocation MODEL_CARBYNEBATTERYBOX7 = Electrodynamics.rl("block/carbynebatterybox7");
    public static final ResourceLocation MODEL_CHEMICALMIXERBASE = Electrodynamics.rl("block/chemicalmixerbase");
    public static final ResourceLocation MODEL_HYDROELECTRICGENERATORBLADES = Electrodynamics.rl("block/hydroelectricgeneratorblades");
    public static final ResourceLocation MODEL_WINDMILLBLADES = Electrodynamics.rl("block/windmillblades");
    public static final ResourceLocation MODEL_MINERALCRUSHERHANDLE = Electrodynamics.rl("block/mineralcrusherhandle");
    public static final ResourceLocation MODEL_MINERALCRUSHERDOUBLEHANDLE = Electrodynamics.rl("block/mineralcrusherdoublehandle");
    public static final ResourceLocation MODEL_MINERALCRUSHERTRIPLEHANDLE = Electrodynamics.rl("block/mineralcrushertriplehandle");
    public static final ResourceLocation MODEL_MINERALGRINDERWHEEL = Electrodynamics.rl("block/mineralgrinderwheel");
    public static final ResourceLocation MODEL_CHEMICALMIXERBLADES = Electrodynamics.rl("block/chemicalmixerblades");
    public static final ResourceLocation MODEL_LATHESHAFT = Electrodynamics.rl("block/latheshaft");
    public static final ResourceLocation MODEL_MOTORCOMPLEXROTOR = Electrodynamics.rl("block/motorcomplexrotor");

    public static final ResourceLocation MODEL_RODSTEEL = Electrodynamics.rl("entity/rodsteel");
    public static final ResourceLocation MODEL_RODSTAINLESSSTEEL = Electrodynamics.rl("entity/rodstainlesssteel");
    public static final ResourceLocation MODEL_RODHSLASTEEL = Electrodynamics.rl("entity/rodhslasteel");

    public static final ResourceLocation MODEL_QUARRYWHEEL_STILL = Electrodynamics.rl("block/quarrywheelstill");
    public static final ResourceLocation MODEL_QUARRYWHEEL_ROT = Electrodynamics.rl("block/quarrywheelrot");

    // Custom Textures
    public static final ResourceLocation TEXTURE_QUARRYARM = Electrodynamics.rl("block/custom/quarryarm");
    public static final ResourceLocation TEXTURE_QUARRYARM_DARK = Electrodynamics.rl("block/custom/quarrydark");
    
    public static final ResourceLocation BLOCK_ATLAS = new ResourceLocation("textures/atlas/blocks.png");

    private static final HashMap<ResourceLocation, TextureAtlasSprite> CACHED_TEXTUREATLASSPRITES = new HashMap<>();
    // for registration purposes only!
    private static final List<ResourceLocation> CUSTOM_TEXTURES = Arrays.asList(ElectrodynamicsClientRegister.TEXTURE_QUARRYARM, ElectrodynamicsClientRegister.TEXTURE_QUARRYARM_DARK);

	public static void setup() {
		ElectrodynamicsClientEvents.init();

		ScreenManager.register(ElectrodynamicsMenuTypes.CONTAINER_COALGENERATOR.get(), ScreenCoalGenerator::new);
		ScreenManager.register(ElectrodynamicsMenuTypes.CONTAINER_ELECTRICFURNACE.get(), ScreenElectricFurnace::new);
		ScreenManager.register(ElectrodynamicsMenuTypes.CONTAINER_ELECTRICFURNACEDOUBLE.get(), ScreenElectricFurnaceDouble::new);
		ScreenManager.register(ElectrodynamicsMenuTypes.CONTAINER_ELECTRICFURNACETRIPLE.get(), ScreenElectricFurnaceTriple::new);
		ScreenManager.register(ElectrodynamicsMenuTypes.CONTAINER_ELECTRICARCFURNACE.get(), ScreenElectricArcFurnace::new);
		ScreenManager.register(ElectrodynamicsMenuTypes.CONTAINER_ELECTRICARCFURNACEDOUBLE.get(), ScreenElectricArcFurnaceDouble::new);
		ScreenManager.register(ElectrodynamicsMenuTypes.CONTAINER_ELECTRICARCFURNACETRIPLE.get(), ScreenElectricArcFurnaceTriple::new);
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
		ScreenManager.register(ElectrodynamicsMenuTypes.CONTAINER_FLUIDVOID.get(), ScreenFluidVoid::new);
		ScreenManager.register(ElectrodynamicsMenuTypes.CONTAINER_SEISMICSCANNER.get(), ScreenSeismicScanner::new);
		ScreenManager.register(ElectrodynamicsMenuTypes.CONTAINER_ELECTROLYTICSEPARATOR.get(), ScreenElectrolyticSeparator::new);
		ScreenManager.register(ElectrodynamicsMenuTypes.CONTAINER_SEISMICRELAY.get(), ScreenSeismicRelay::new);
		ScreenManager.register(ElectrodynamicsMenuTypes.CONTAINER_COOLANTRESAVOIR.get(), ScreenCoolantResavoir::new);
		ScreenManager.register(ElectrodynamicsMenuTypes.CONTAINER_MOTORCOMPLEX.get(), ScreenMotorComplex::new);
		ScreenManager.register(ElectrodynamicsMenuTypes.CONTAINER_QUARRY.get(), ScreenQuarry::new);
		ScreenManager.register(ElectrodynamicsMenuTypes.CONTAINER_FLUIDPIPEPUMP.get(), ScreenFluidPipePump::new);
		ScreenManager.register(ElectrodynamicsMenuTypes.CONTAINER_FLUIDPIPEFILTER.get(), ScreenFluidPipeFilter::new);
		ScreenManager.register(ElectrodynamicsMenuTypes.CONTAINER_ELECTRICDRILL.get(), ScreenElectricDrill::new);
		ScreenManager.register(ElectrodynamicsMenuTypes.CONTAINER_POTENTIOMETER.get(), ScreenPotentiometer::new);
		ScreenManager.register(ElectrodynamicsMenuTypes.CONTAINER_ADVANCEDDOWNGRADETRANSFORMER.get(), ScreenAdvancedDowngradeTransformer::new);
		ScreenManager.register(ElectrodynamicsMenuTypes.CONTAINER_ADVANCEDUPGRADETRANSFORMER.get(), ScreenAdvancedUpgradeTransformer::new);
		ScreenManager.register(ElectrodynamicsMenuTypes.CONTAINER_CIRCUITMONITOR.get(), ScreenCircuitMonitor::new);
		ScreenManager.register(ElectrodynamicsMenuTypes.CONTAINER_COBBLESTONEGENERATOR.get(), ScreenCobblestoneGenerator::new);

		ItemModelsProperties.register(ElectrodynamicsItems.ITEM_ELECTRICBATON.get(), VoltaicClientRegister.ON, (stack, world, entity) -> entity != null && (entity.getMainHandItem() == stack || entity.getOffhandItem() == stack) && ((ItemElectricBaton) stack.getItem()).getJoulesStored(stack) > ((ItemElectricBaton) stack.getItem()).getElectricProperties().extract.getJoules() ? 1 : 0);
		ItemModelsProperties.register(ElectrodynamicsItems.ITEM_ELECTRICDRILL.get(), VoltaicClientRegister.ON, (stack, world, entity) -> entity != null && (entity.getMainHandItem() == stack || entity.getOffhandItem() == stack) && ((ItemElectricDrill) stack.getItem()).getJoulesStored(stack) > ((ItemElectricDrill) stack.getItem()).getElectricProperties().extract.getJoules() ? 1 : 0);
		ItemModelsProperties.register(ElectrodynamicsItems.ITEM_ELECTRICCHAINSAW.get(), VoltaicClientRegister.ON, (stack, world, entity) -> entity != null && (entity.getMainHandItem() == stack || entity.getOffhandItem() == stack) && ((ItemElectricChainsaw) stack.getItem()).getJoulesStored(stack) > ((ItemElectricChainsaw) stack.getItem()).getElectricProperties().extract.getJoules() ? 1 : 0);

		EntityRendererManager manager = Minecraft.getInstance().getEntityRenderDispatcher();
		manager.register(ElectrodynamicsEntities.ENTITY_ENERGYBLAST.get(), new RenderEnergyBlast(manager));
		manager.register(ElectrodynamicsEntities.ENTITY_METALROD.get(), new RenderMetalRod(manager));

		ClientRegistry.bindTileEntityRenderer(ElectrodynamicsTiles.TILE_ADVANCEDSOLARPANEL.get(), RenderAdvancedSolarPanel::new);
        ClientRegistry.bindTileEntityRenderer(ElectrodynamicsTiles.TILE_BATTERYBOX.get(), RenderBatteryBox::new);
        ClientRegistry.bindTileEntityRenderer(ElectrodynamicsTiles.TILE_LITHIUMBATTERYBOX.get(), RenderLithiumBatteryBox::new);
        ClientRegistry.bindTileEntityRenderer(ElectrodynamicsTiles.TILE_CARBYNEBATTERYBOX.get(), RenderCarbyneBatteryBox::new);
        ClientRegistry.bindTileEntityRenderer(ElectrodynamicsTiles.TILE_HYDROELECTRICGENERATOR.get(), RenderHydroelectricGenerator::new);
        ClientRegistry.bindTileEntityRenderer(ElectrodynamicsTiles.TILE_WINDMILL.get(), RenderWindmill::new);
        ClientRegistry.bindTileEntityRenderer(ElectrodynamicsTiles.TILE_MINERALCRUSHER.get(), RenderMineralCrusher::new);
        ClientRegistry.bindTileEntityRenderer(ElectrodynamicsTiles.TILE_MINERALCRUSHERDOUBLE.get(), RenderMineralCrusherDouble::new);
        ClientRegistry.bindTileEntityRenderer(ElectrodynamicsTiles.TILE_MINERALCRUSHERTRIPLE.get(), RenderMineralCrusherTriple::new);
        ClientRegistry.bindTileEntityRenderer(ElectrodynamicsTiles.TILE_MINERALGRINDER.get(), RenderMineralGrinder::new);
        ClientRegistry.bindTileEntityRenderer(ElectrodynamicsTiles.TILE_MINERALGRINDERDOUBLE.get(), RenderMineralGrinderDouble::new);
        ClientRegistry.bindTileEntityRenderer(ElectrodynamicsTiles.TILE_MINERALGRINDERTRIPLE.get(), RenderMineralGrinderTriple::new);
        ClientRegistry.bindTileEntityRenderer(ElectrodynamicsTiles.TILE_FERMENTATIONPLANT.get(), RenderFermentationPlant::new);
        ClientRegistry.bindTileEntityRenderer(ElectrodynamicsTiles.TILE_COMBUSTIONCHAMBER.get(), RenderCombustionChamber::new);
        ClientRegistry.bindTileEntityRenderer(ElectrodynamicsTiles.TILE_MINERALWASHER.get(), RenderMineralWasher::new);
        ClientRegistry.bindTileEntityRenderer(ElectrodynamicsTiles.TILE_CHEMICALMIXER.get(), RenderChemicalMixer::new);
        ClientRegistry.bindTileEntityRenderer(ElectrodynamicsTiles.TILE_MULTIMETERBLOCK.get(), RenderMultimeterBlock::new);
        ClientRegistry.bindTileEntityRenderer(ElectrodynamicsTiles.TILE_LATHE.get(), RenderLathe::new);
        ClientRegistry.bindTileEntityRenderer(ElectrodynamicsTiles.TILE_CHARGERLV.get(), RenderChargerGeneric::new);
        ClientRegistry.bindTileEntityRenderer(ElectrodynamicsTiles.TILE_CHARGERMV.get(), RenderChargerGeneric::new);
        ClientRegistry.bindTileEntityRenderer(ElectrodynamicsTiles.TILE_CHARGERHV.get(), RenderChargerGeneric::new);
        ClientRegistry.bindTileEntityRenderer(ElectrodynamicsTiles.TILE_SEISMICRELAY.get(), RenderSeismicRelay::new);
        ClientRegistry.bindTileEntityRenderer(ElectrodynamicsTiles.TILE_COOLANTRESAVOIR.get(), RenderCoolantResavoir::new);
        ClientRegistry.bindTileEntityRenderer(ElectrodynamicsTiles.TILE_TANKHSLA.get(), RenderTankGeneric::new);
        ClientRegistry.bindTileEntityRenderer(ElectrodynamicsTiles.TILE_TANKREINFORCED.get(), RenderTankGeneric::new);
        ClientRegistry.bindTileEntityRenderer(ElectrodynamicsTiles.TILE_TANKSTEEL.get(), RenderTankGeneric::new);
        ClientRegistry.bindTileEntityRenderer(ElectrodynamicsTiles.TILE_MOTORCOMPLEX.get(), RenderMotorComplex::new);
        ClientRegistry.bindTileEntityRenderer(ElectrodynamicsTiles.TILE_ELECTROLYTICSEPARATOR.get(), RenderElectrolyticSeparator::new);
        ClientRegistry.bindTileEntityRenderer(ElectrodynamicsTiles.TILE_FLUIDPIPEPUMP.get(), RenderFluidPipePump::new);
        ClientRegistry.bindTileEntityRenderer(ElectrodynamicsTiles.TILE_LOGISTICALWIRE.get(), RenderLogisticalWire::new);

        ClientRegistry.bindTileEntityRenderer(ElectrodynamicsTiles.TILE_WIRE.get(), RenderConnectBlock::new);
        ClientRegistry.bindTileEntityRenderer(ElectrodynamicsTiles.TILE_LOGISTICALWIRE.get(), RenderConnectBlock::new);
        ClientRegistry.bindTileEntityRenderer(ElectrodynamicsTiles.TILE_PIPE.get(), RenderConnectBlock::new);

        ClientRegistry.bindTileEntityRenderer(ElectrodynamicsTiles.TILE_ADVANCEDUPGRADETRANSFORMER.get(), RenderAdvancedTransformer.RenderAdvancedUpgradeTransformer::new);
        ClientRegistry.bindTileEntityRenderer(ElectrodynamicsTiles.TILE_ADVANCEDDOWNGRADETRANSFORMER.get(), RenderAdvancedTransformer.RenderAdvancedDowngradeTransformer::new);
		
		ScreenGuidebook.addGuidebookModule(new ModuleElectrodynamics());
		
		// I do not miss this

		for (BlockCustomGlass glass : ElectrodynamicsBlocks.BLOCKS_CUSTOMGLASS.getAllValues()) {
			RenderTypeLookup.setRenderLayer(glass, RenderType.cutout());
		}
		
		for(BlockWire wire : ElectrodynamicsBlocks.BLOCKS_WIRE.getAllValues()) {
			RenderTypeLookup.setRenderLayer(wire, RenderType.cutout());
		}
		
		RenderTypeLookup.setRenderLayer(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.circuitbreaker), ElectrodynamicsClientRegister::shouldMultilayerRender);
		RenderTypeLookup.setRenderLayer(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.chemicalcrystallizer), ElectrodynamicsClientRegister::shouldMultilayerRender);
		//RenderTypeLookup.setRenderLayer(ElectrodynamicsBlocks.getBlock(SubtypeMachine.coalgeneratorrunning), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.combustionchamber), ElectrodynamicsClientRegister::shouldMultilayerRender);
		RenderTypeLookup.setRenderLayer(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.coolantresavoir), ElectrodynamicsClientRegister::shouldMultilayerRender);
		RenderTypeLookup.setRenderLayer(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.fermentationplant), ElectrodynamicsClientRegister::shouldMultilayerRender);
		RenderTypeLookup.setRenderLayer(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.mineralwasher), ElectrodynamicsClientRegister::shouldMultilayerRender);
		//RenderTypeLookup.setRenderLayer(ElectrodynamicsBlocks.getBlock(SubtypeMachine.wiremill), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.tanksteel), ElectrodynamicsClientRegister::shouldMultilayerRender);
		RenderTypeLookup.setRenderLayer(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.tankhsla), ElectrodynamicsClientRegister::shouldMultilayerRender);
		RenderTypeLookup.setRenderLayer(ElectrodynamicsBlocks.BLOCKS_MACHINE.getValue(SubtypeMachine.tankreinforced), ElectrodynamicsClientRegister::shouldMultilayerRender);
		
		RenderTypeLookup.setRenderLayer(ElectrodynamicsBlocks.BLOCK_STEELSCAFFOLDING.get(), RenderType.cutout());

	}
	
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
        ModelLoader.addSpecialModel(MODEL_CHEMICALMIXERBASE);
        ModelLoader.addSpecialModel(MODEL_CARBYNEBATTERYBOX);
        ModelLoader.addSpecialModel(MODEL_CARBYNEBATTERYBOX2);
        ModelLoader.addSpecialModel(MODEL_CARBYNEBATTERYBOX3);
        ModelLoader.addSpecialModel(MODEL_CARBYNEBATTERYBOX4);
        ModelLoader.addSpecialModel(MODEL_CARBYNEBATTERYBOX5);
        ModelLoader.addSpecialModel(MODEL_CARBYNEBATTERYBOX6);
        ModelLoader.addSpecialModel(MODEL_CARBYNEBATTERYBOX7);
        ModelLoader.addSpecialModel(MODEL_HYDROELECTRICGENERATORBLADES);
        ModelLoader.addSpecialModel(MODEL_WINDMILLBLADES);
        ModelLoader.addSpecialModel(MODEL_MINERALCRUSHERHANDLE);
        ModelLoader.addSpecialModel(MODEL_MINERALCRUSHERDOUBLEHANDLE);
        ModelLoader.addSpecialModel(MODEL_MINERALCRUSHERTRIPLEHANDLE);
        ModelLoader.addSpecialModel(MODEL_MINERALGRINDERWHEEL);
        ModelLoader.addSpecialModel(MODEL_CHEMICALMIXERBLADES);
        ModelLoader.addSpecialModel(MODEL_RODSTEEL);
        ModelLoader.addSpecialModel(MODEL_RODSTAINLESSSTEEL);
        ModelLoader.addSpecialModel(MODEL_RODHSLASTEEL);
        ModelLoader.addSpecialModel(MODEL_LATHESHAFT);
        ModelLoader.addSpecialModel(MODEL_MOTORCOMPLEXROTOR);
        ModelLoader.addSpecialModel(MODEL_QUARRYWHEEL_STILL);
        ModelLoader.addSpecialModel(MODEL_QUARRYWHEEL_ROT);
    }

	public static boolean shouldMultilayerRender(RenderType type) {
		return type == RenderType.translucent() || type == RenderType.solid();
	}

	@SubscribeEvent
	public static void addCustomTextureAtlases(TextureStitchEvent.Pre event) {
		if (event.getMap().location().equals(AtlasTexture.LOCATION_BLOCKS)) {
			CUSTOM_TEXTURES.forEach(event::addSprite);
		}
	}

	@SubscribeEvent
	public static void cacheCustomTextureAtlases(TextureStitchEvent.Post event) {
		if (event.getMap().location().equals(AtlasTexture.LOCATION_BLOCKS)) {
			for (ResourceLocation loc : CUSTOM_TEXTURES) {
				ElectrodynamicsClientRegister.CACHED_TEXTUREATLASSPRITES.put(loc, event.getMap().getSprite(loc));
			}
		}
	}

	public static TextureAtlasSprite getSprite(ResourceLocation sprite) {
        return CACHED_TEXTUREATLASSPRITES.getOrDefault(sprite, VoltaicClientRegister.whiteSprite());
    }

}
