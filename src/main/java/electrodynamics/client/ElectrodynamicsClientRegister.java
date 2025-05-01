package electrodynamics.client;

import java.util.HashMap;
import java.util.List;

import electrodynamics.Electrodynamics;
import electrodynamics.client.guidebook.ModuleElectrodynamics;
import electrodynamics.client.model.armor.ModelCombatArmor;
import electrodynamics.client.model.armor.ModelCompositeArmor;
import electrodynamics.client.model.armor.ModelHydraulicBoots;
import electrodynamics.client.model.armor.ModelJetpack;
import electrodynamics.client.model.armor.ModelNightVisionGoggles;
import electrodynamics.client.model.armor.ModelServoLeggings;
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
import electrodynamics.client.render.tile.RenderGasPipePump;
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
import electrodynamics.client.screen.tile.ScreenCombustionChamber;
import electrodynamics.client.screen.tile.ScreenCompressor;
import electrodynamics.client.screen.tile.ScreenCoolantResavoir;
import electrodynamics.client.screen.tile.ScreenCreativeFluidSource;
import electrodynamics.client.screen.tile.ScreenCreativePowerSource;
import electrodynamics.client.screen.tile.ScreenDecompressor;
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
import electrodynamics.client.screen.tile.ScreenGasPipeFilter;
import electrodynamics.client.screen.tile.ScreenGasPipePump;
import electrodynamics.client.screen.tile.ScreenGasTankGeneric;
import electrodynamics.client.screen.tile.ScreenGasVent;
import electrodynamics.client.screen.tile.ScreenHydroelectricGenerator;
import electrodynamics.client.screen.tile.ScreenMineralWasher;
import electrodynamics.client.screen.tile.ScreenMotorComplex;
import electrodynamics.client.screen.tile.ScreenPotentiometer;
import electrodynamics.client.screen.tile.ScreenQuarry;
import electrodynamics.client.screen.tile.ScreenSeismicRelay;
import electrodynamics.client.screen.tile.ScreenSolarPanel;
import electrodynamics.client.screen.tile.ScreenThermoelectricManipulator;
import electrodynamics.client.screen.tile.ScreenWindmill;
import electrodynamics.common.item.gear.tools.electric.ItemElectricBaton;
import electrodynamics.common.item.gear.tools.electric.ItemElectricChainsaw;
import electrodynamics.common.item.gear.tools.electric.ItemElectricDrill;
import electrodynamics.registers.ElectrodynamicsTiles;
import electrodynamics.registers.ElectrodynamicsEntities;
import electrodynamics.registers.ElectrodynamicsItems;
import electrodynamics.registers.ElectrodynamicsMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelEvent.RegisterAdditional;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import voltaic.client.VoltaicClientRegister;
import voltaic.client.guidebook.ScreenGuidebook;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = Electrodynamics.ID, bus = Bus.MOD, value = { Dist.CLIENT })
public class ElectrodynamicsClientRegister {

	// sometimes I fucking hate this game
    public static final LayerDefinition COMPOSITE_ARMOR_LAYER_LEG_NOCHEST = ModelCompositeArmor.createBodyLayer(1, true);
    public static final LayerDefinition COMPOSITE_ARMOR_LAYER_BOOTS = ModelCompositeArmor.createBodyLayer(2, false);
    public static final LayerDefinition COMPOSITE_ARMOR_LAYER_COMB_NOCHEST = ModelCompositeArmor.createBodyLayer(3, true);
    public static final LayerDefinition COMPOSITE_ARMOR_LAYER_LEG_CHEST = ModelCompositeArmor.createBodyLayer(1, false);
    public static final LayerDefinition COMPOSITE_ARMOR_LAYER_COMB_CHEST = ModelCompositeArmor.createBodyLayer(3, false);

    public static final LayerDefinition NIGHT_VISION_GOGGLES = ModelNightVisionGoggles.createBodyLayer();

    public static final LayerDefinition HYDRAULIC_BOOTS = ModelHydraulicBoots.createBodyLayer();

    public static final LayerDefinition JETPACK = ModelJetpack.createBodyLayer();

    public static final LayerDefinition SERVO_LEGGINGS = ModelServoLeggings.createBodyLayer();

    public static final LayerDefinition COMBAT_ARMOR_LAYER_LEG_NOCHEST = ModelCombatArmor.createBodyLayer(1, true);
    public static final LayerDefinition COMBAT_ARMOR_LAYER_BOOTS = ModelCombatArmor.createBodyLayer(2, false);
    public static final LayerDefinition COMBAT_ARMOR_LAYER_COMB_NOCHEST = ModelCombatArmor.createBodyLayer(3, true);
    public static final LayerDefinition COMBAT_ARMOR_LAYER_LEG_CHEST = ModelCombatArmor.createBodyLayer(1, false);
    public static final LayerDefinition COMBAT_ARMOR_LAYER_COMB_CHEST = ModelCombatArmor.createBodyLayer(3, false);

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

    private static final HashMap<ResourceLocation, TextureAtlasSprite> CACHED_TEXTUREATLASSPRITES = new HashMap<>();
    // for registration purposes only!
    private static final List<ResourceLocation> CUSTOM_TEXTURES = List.of(ElectrodynamicsClientRegister.TEXTURE_QUARRYARM, ElectrodynamicsClientRegister.TEXTURE_QUARRYARM_DARK);

	public static void setup() {
		ElectrodynamicsClientEvents.init();

		MenuScreens.register(ElectrodynamicsMenuTypes.CONTAINER_COALGENERATOR.get(), ScreenCoalGenerator::new);
		MenuScreens.register(ElectrodynamicsMenuTypes.CONTAINER_ELECTRICFURNACE.get(), ScreenElectricFurnace::new);
		MenuScreens.register(ElectrodynamicsMenuTypes.CONTAINER_ELECTRICFURNACEDOUBLE.get(), ScreenElectricFurnaceDouble::new);
		MenuScreens.register(ElectrodynamicsMenuTypes.CONTAINER_ELECTRICFURNACETRIPLE.get(), ScreenElectricFurnaceTriple::new);
		MenuScreens.register(ElectrodynamicsMenuTypes.CONTAINER_ELECTRICARCFURNACE.get(), ScreenElectricArcFurnace::new);
		MenuScreens.register(ElectrodynamicsMenuTypes.CONTAINER_ELECTRICARCFURNACEDOUBLE.get(), ScreenElectricArcFurnaceDouble::new);
		MenuScreens.register(ElectrodynamicsMenuTypes.CONTAINER_ELECTRICARCFURNACETRIPLE.get(), ScreenElectricArcFurnaceTriple::new);
		MenuScreens.register(ElectrodynamicsMenuTypes.CONTAINER_BATTERYBOX.get(), ScreenBatteryBox::new);
		MenuScreens.register(ElectrodynamicsMenuTypes.CONTAINER_FERMENTATIONPLANT.get(), ScreenFermentationPlant::new);
		MenuScreens.register(ElectrodynamicsMenuTypes.CONTAINER_MINERALWASHER.get(), ScreenMineralWasher::new);
		MenuScreens.register(ElectrodynamicsMenuTypes.CONTAINER_CHEMICALMIXER.get(), ScreenChemicalMixer::new);
		MenuScreens.register(ElectrodynamicsMenuTypes.CONTAINER_CHEMICALCRYSTALLIZER.get(), ScreenChemicalCrystallizer::new);
		MenuScreens.register(ElectrodynamicsMenuTypes.CONTAINER_CHARGER.get(), ScreenChargerGeneric::new);
		MenuScreens.register(ElectrodynamicsMenuTypes.CONTAINER_TANK.get(), ScreenFluidTankGeneric::new);
		MenuScreens.register(ElectrodynamicsMenuTypes.CONTAINER_COMBUSTION_CHAMBER.get(), ScreenCombustionChamber::new);
		MenuScreens.register(ElectrodynamicsMenuTypes.CONTAINER_SOLARPANEL.get(), ScreenSolarPanel::new);
		MenuScreens.register(ElectrodynamicsMenuTypes.CONTAINER_WINDMILL.get(), ScreenWindmill::new);
		MenuScreens.register(ElectrodynamicsMenuTypes.CONTAINER_HYDROELECTRICGENERATOR.get(), ScreenHydroelectricGenerator::new);
		MenuScreens.register(ElectrodynamicsMenuTypes.CONTAINER_CREATIVEPOWERSOURCE.get(), ScreenCreativePowerSource::new);
		MenuScreens.register(ElectrodynamicsMenuTypes.CONTAINER_CREATIVEFLUIDSOURCE.get(), ScreenCreativeFluidSource::new);
		MenuScreens.register(ElectrodynamicsMenuTypes.CONTAINER_FLUIDVOID.get(), ScreenFluidVoid::new);
		MenuScreens.register(ElectrodynamicsMenuTypes.CONTAINER_SEISMICSCANNER.get(), ScreenSeismicScanner::new);
		MenuScreens.register(ElectrodynamicsMenuTypes.CONTAINER_ELECTROLYTICSEPARATOR.get(), ScreenElectrolyticSeparator::new);
		MenuScreens.register(ElectrodynamicsMenuTypes.CONTAINER_SEISMICRELAY.get(), ScreenSeismicRelay::new);
		MenuScreens.register(ElectrodynamicsMenuTypes.CONTAINER_COOLANTRESAVOIR.get(), ScreenCoolantResavoir::new);
		MenuScreens.register(ElectrodynamicsMenuTypes.CONTAINER_MOTORCOMPLEX.get(), ScreenMotorComplex::new);
		MenuScreens.register(ElectrodynamicsMenuTypes.CONTAINER_QUARRY.get(), ScreenQuarry::new);
		MenuScreens.register(ElectrodynamicsMenuTypes.CONTAINER_GASTANK.get(), ScreenGasTankGeneric::new);
		MenuScreens.register(ElectrodynamicsMenuTypes.CONTAINER_COMPRESSOR.get(), ScreenCompressor::new);
		MenuScreens.register(ElectrodynamicsMenuTypes.CONTAINER_DECOMPRESSOR.get(), ScreenDecompressor::new);
		MenuScreens.register(ElectrodynamicsMenuTypes.CONTAINER_GASVENT.get(), ScreenGasVent::new);
		MenuScreens.register(ElectrodynamicsMenuTypes.CONTAINER_THERMOELECTRICMANIPULATOR.get(), ScreenThermoelectricManipulator::new);
		MenuScreens.register(ElectrodynamicsMenuTypes.CONTAINER_GASPIPEPUMP.get(), ScreenGasPipePump::new);
		MenuScreens.register(ElectrodynamicsMenuTypes.CONTAINER_FLUIDPIPEPUMP.get(), ScreenFluidPipePump::new);
		MenuScreens.register(ElectrodynamicsMenuTypes.CONTAINER_GASPIPEFILTER.get(), ScreenGasPipeFilter::new);
		MenuScreens.register(ElectrodynamicsMenuTypes.CONTAINER_FLUIDPIPEFILTER.get(), ScreenFluidPipeFilter::new);
		MenuScreens.register(ElectrodynamicsMenuTypes.CONTAINER_ELECTRICDRILL.get(), ScreenElectricDrill::new);
		MenuScreens.register(ElectrodynamicsMenuTypes.CONTAINER_POTENTIOMETER.get(), ScreenPotentiometer::new);
		MenuScreens.register(ElectrodynamicsMenuTypes.CONTAINER_ADVANCEDDOWNGRADETRANSFORMER.get(), ScreenAdvancedDowngradeTransformer::new);
		MenuScreens.register(ElectrodynamicsMenuTypes.CONTAINER_ADVANCEDUPGRADETRANSFORMER.get(), ScreenAdvancedUpgradeTransformer::new);
		MenuScreens.register(ElectrodynamicsMenuTypes.CONTAINER_CIRCUITMONITOR.get(), ScreenCircuitMonitor::new);

		ItemProperties.register(ElectrodynamicsItems.ITEM_ELECTRICBATON.get(), VoltaicClientRegister.ON, (stack, world, entity, call) -> entity != null && (entity.getMainHandItem() == stack || entity.getOffhandItem() == stack) && ((ItemElectricBaton) stack.getItem()).getJoulesStored(stack) > ((ItemElectricBaton) stack.getItem()).getElectricProperties().extract.getJoules() ? 1 : 0);
		ItemProperties.register(ElectrodynamicsItems.ITEM_ELECTRICDRILL.get(), VoltaicClientRegister.ON, (stack, world, entity, call) -> entity != null && (entity.getMainHandItem() == stack || entity.getOffhandItem() == stack) && ((ItemElectricDrill) stack.getItem()).getJoulesStored(stack) > ((ItemElectricDrill) stack.getItem()).getElectricProperties().extract.getJoules() ? 1 : 0);
		ItemProperties.register(ElectrodynamicsItems.ITEM_ELECTRICCHAINSAW.get(), VoltaicClientRegister.ON, (stack, world, entity, call) -> entity != null && (entity.getMainHandItem() == stack || entity.getOffhandItem() == stack) && ((ItemElectricChainsaw) stack.getItem()).getJoulesStored(stack) > ((ItemElectricChainsaw) stack.getItem()).getElectricProperties().extract.getJoules() ? 1 : 0);

		ScreenGuidebook.addGuidebookModule(new ModuleElectrodynamics());
		
	}
	
	@SubscribeEvent
    public static void onModelEvent(RegisterAdditional event) {
        event.register(MODEL_ADVSOLARTOP);
        event.register(MODEL_BATTERYBOX);
        event.register(MODEL_BATTERYBOX2);
        event.register(MODEL_BATTERYBOX3);
        event.register(MODEL_BATTERYBOX4);
        event.register(MODEL_BATTERYBOX5);
        event.register(MODEL_BATTERYBOX6);
        event.register(MODEL_BATTERYBOX7);
        event.register(MODEL_LITHIUMBATTERYBOX);
        event.register(MODEL_LITHIUMBATTERYBOX2);
        event.register(MODEL_LITHIUMBATTERYBOX3);
        event.register(MODEL_LITHIUMBATTERYBOX4);
        event.register(MODEL_LITHIUMBATTERYBOX5);
        event.register(MODEL_LITHIUMBATTERYBOX6);
        event.register(MODEL_LITHIUMBATTERYBOX7);
        event.register(MODEL_CHEMICALMIXERBASE);
        event.register(MODEL_CARBYNEBATTERYBOX);
        event.register(MODEL_CARBYNEBATTERYBOX2);
        event.register(MODEL_CARBYNEBATTERYBOX3);
        event.register(MODEL_CARBYNEBATTERYBOX4);
        event.register(MODEL_CARBYNEBATTERYBOX5);
        event.register(MODEL_CARBYNEBATTERYBOX6);
        event.register(MODEL_CARBYNEBATTERYBOX7);
        event.register(MODEL_HYDROELECTRICGENERATORBLADES);
        event.register(MODEL_WINDMILLBLADES);
        event.register(MODEL_MINERALCRUSHERHANDLE);
        event.register(MODEL_MINERALCRUSHERDOUBLEHANDLE);
        event.register(MODEL_MINERALCRUSHERTRIPLEHANDLE);
        event.register(MODEL_MINERALGRINDERWHEEL);
        event.register(MODEL_CHEMICALMIXERBLADES);
        event.register(MODEL_RODSTEEL);
        event.register(MODEL_RODSTAINLESSSTEEL);
        event.register(MODEL_RODHSLASTEEL);
        event.register(MODEL_LATHESHAFT);
        event.register(MODEL_MOTORCOMPLEXROTOR);
        event.register(MODEL_QUARRYWHEEL_STILL);
        event.register(MODEL_QUARRYWHEEL_ROT);
    }

	@SubscribeEvent
	public static void registerEntities(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(ElectrodynamicsEntities.ENTITY_ENERGYBLAST.get(), RenderEnergyBlast::new);
		event.registerEntityRenderer(ElectrodynamicsEntities.ENTITY_METALROD.get(), RenderMetalRod::new);

		event.registerBlockEntityRenderer(ElectrodynamicsTiles.TILE_ADVANCEDSOLARPANEL.get(), RenderAdvancedSolarPanel::new);
        event.registerBlockEntityRenderer(ElectrodynamicsTiles.TILE_BATTERYBOX.get(), RenderBatteryBox::new);
        event.registerBlockEntityRenderer(ElectrodynamicsTiles.TILE_LITHIUMBATTERYBOX.get(), RenderLithiumBatteryBox::new);
        event.registerBlockEntityRenderer(ElectrodynamicsTiles.TILE_CARBYNEBATTERYBOX.get(), RenderCarbyneBatteryBox::new);
        event.registerBlockEntityRenderer(ElectrodynamicsTiles.TILE_HYDROELECTRICGENERATOR.get(), RenderHydroelectricGenerator::new);
        event.registerBlockEntityRenderer(ElectrodynamicsTiles.TILE_WINDMILL.get(), RenderWindmill::new);
        event.registerBlockEntityRenderer(ElectrodynamicsTiles.TILE_MINERALCRUSHER.get(), RenderMineralCrusher::new);
        event.registerBlockEntityRenderer(ElectrodynamicsTiles.TILE_MINERALCRUSHERDOUBLE.get(), RenderMineralCrusherDouble::new);
        event.registerBlockEntityRenderer(ElectrodynamicsTiles.TILE_MINERALCRUSHERTRIPLE.get(), RenderMineralCrusherTriple::new);
        event.registerBlockEntityRenderer(ElectrodynamicsTiles.TILE_MINERALGRINDER.get(), RenderMineralGrinder::new);
        event.registerBlockEntityRenderer(ElectrodynamicsTiles.TILE_MINERALGRINDERDOUBLE.get(), RenderMineralGrinderDouble::new);
        event.registerBlockEntityRenderer(ElectrodynamicsTiles.TILE_MINERALGRINDERTRIPLE.get(), RenderMineralGrinderTriple::new);
        event.registerBlockEntityRenderer(ElectrodynamicsTiles.TILE_FERMENTATIONPLANT.get(), RenderFermentationPlant::new);
        event.registerBlockEntityRenderer(ElectrodynamicsTiles.TILE_COMBUSTIONCHAMBER.get(), RenderCombustionChamber::new);
        event.registerBlockEntityRenderer(ElectrodynamicsTiles.TILE_MINERALWASHER.get(), RenderMineralWasher::new);
        event.registerBlockEntityRenderer(ElectrodynamicsTiles.TILE_CHEMICALMIXER.get(), RenderChemicalMixer::new);
        event.registerBlockEntityRenderer(ElectrodynamicsTiles.TILE_MULTIMETERBLOCK.get(), RenderMultimeterBlock::new);
        event.registerBlockEntityRenderer(ElectrodynamicsTiles.TILE_LATHE.get(), RenderLathe::new);
        event.registerBlockEntityRenderer(ElectrodynamicsTiles.TILE_CHARGERLV.get(), RenderChargerGeneric::new);
        event.registerBlockEntityRenderer(ElectrodynamicsTiles.TILE_CHARGERMV.get(), RenderChargerGeneric::new);
        event.registerBlockEntityRenderer(ElectrodynamicsTiles.TILE_CHARGERHV.get(), RenderChargerGeneric::new);
        event.registerBlockEntityRenderer(ElectrodynamicsTiles.TILE_SEISMICRELAY.get(), RenderSeismicRelay::new);
        event.registerBlockEntityRenderer(ElectrodynamicsTiles.TILE_COOLANTRESAVOIR.get(), RenderCoolantResavoir::new);
        event.registerBlockEntityRenderer(ElectrodynamicsTiles.TILE_TANKHSLA.get(), RenderTankGeneric::new);
        event.registerBlockEntityRenderer(ElectrodynamicsTiles.TILE_TANKREINFORCED.get(), RenderTankGeneric::new);
        event.registerBlockEntityRenderer(ElectrodynamicsTiles.TILE_TANKSTEEL.get(), RenderTankGeneric::new);
        event.registerBlockEntityRenderer(ElectrodynamicsTiles.TILE_MOTORCOMPLEX.get(), RenderMotorComplex::new);
        event.registerBlockEntityRenderer(ElectrodynamicsTiles.TILE_ELECTROLYTICSEPARATOR.get(), RenderElectrolyticSeparator::new);
        event.registerBlockEntityRenderer(ElectrodynamicsTiles.TILE_GASPIPEPUMP.get(), RenderGasPipePump::new);
        event.registerBlockEntityRenderer(ElectrodynamicsTiles.TILE_FLUIDPIPEPUMP.get(), RenderFluidPipePump::new);
        event.registerBlockEntityRenderer(ElectrodynamicsTiles.TILE_LOGISTICALWIRE.get(), RenderLogisticalWire::new);

        event.registerBlockEntityRenderer(ElectrodynamicsTiles.TILE_WIRE.get(), RenderConnectBlock::new);
        event.registerBlockEntityRenderer(ElectrodynamicsTiles.TILE_LOGISTICALWIRE.get(), RenderConnectBlock::new);
        event.registerBlockEntityRenderer(ElectrodynamicsTiles.TILE_PIPE.get(), RenderConnectBlock::new);
        event.registerBlockEntityRenderer(ElectrodynamicsTiles.TILE_GAS_PIPE.get(), RenderConnectBlock::new);

        event.registerBlockEntityRenderer(ElectrodynamicsTiles.TILE_ADVANCEDUPGRADETRANSFORMER.get(), RenderAdvancedTransformer.RenderAdvancedUpgradeTransformer::new);
        event.registerBlockEntityRenderer(ElectrodynamicsTiles.TILE_ADVANCEDDOWNGRADETRANSFORMER.get(), RenderAdvancedTransformer.RenderAdvancedDowngradeTransformer::new);
	}

	public static boolean shouldMultilayerRender(RenderType type) {
		return type == RenderType.translucent() || type == RenderType.solid();
	}

	@SubscribeEvent
	public static void cacheCustomTextureAtlases(TextureStitchEvent.Post event) {
		if (event.getAtlas().location().equals(TextureAtlas.LOCATION_BLOCKS)) {
			for (ResourceLocation loc : CUSTOM_TEXTURES) {
				ElectrodynamicsClientRegister.CACHED_TEXTUREATLASSPRITES.put(loc, event.getAtlas().getSprite(loc));
			}
		}
	}
	
	public static TextureAtlasSprite getSprite(ResourceLocation sprite) {
        return CACHED_TEXTUREATLASSPRITES.getOrDefault(sprite, VoltaicClientRegister.whiteSprite());
    }

}
