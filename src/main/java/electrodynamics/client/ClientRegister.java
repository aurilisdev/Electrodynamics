package electrodynamics.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import electrodynamics.api.References;
import electrodynamics.client.guidebook.ModuleElectrodynamics;
import electrodynamics.client.guidebook.ScreenGuidebook;
import electrodynamics.client.particle.plasmaball.ParticlePlasmaBall;
import electrodynamics.client.render.entity.RenderEnergyBlast;
import electrodynamics.client.render.entity.RenderMetalRod;
import electrodynamics.client.render.model.armor.types.ModelCombatArmor;
import electrodynamics.client.render.model.armor.types.ModelCompositeArmor;
import electrodynamics.client.render.model.armor.types.ModelHydraulicBoots;
import electrodynamics.client.render.model.armor.types.ModelJetpack;
import electrodynamics.client.render.model.armor.types.ModelNightVisionGoggles;
import electrodynamics.client.render.model.armor.types.ModelServoLeggings;
import electrodynamics.client.render.tile.RenderAdvancedSolarPanel;
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
import electrodynamics.client.screen.tile.ScreenBatteryBox;
import electrodynamics.client.screen.tile.ScreenCarbyneBatteryBox;
import electrodynamics.client.screen.tile.ScreenChargerGeneric;
import electrodynamics.client.screen.tile.ScreenChemicalCrystallizer;
import electrodynamics.client.screen.tile.ScreenChemicalMixer;
import electrodynamics.client.screen.tile.ScreenCoalGenerator;
import electrodynamics.client.screen.tile.ScreenCombustionChamber;
import electrodynamics.client.screen.tile.ScreenCoolantResavoir;
import electrodynamics.client.screen.tile.ScreenCreativeFluidSource;
import electrodynamics.client.screen.tile.ScreenCreativePowerSource;
import electrodynamics.client.screen.tile.ScreenDO2OProcessor;
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
import electrodynamics.client.screen.tile.ScreenFluidVoid;
import electrodynamics.client.screen.tile.ScreenGasPipeFilter;
import electrodynamics.client.screen.tile.ScreenGasPipePump;
import electrodynamics.client.screen.tile.ScreenGasTankGeneric;
import electrodynamics.client.screen.tile.ScreenGasVent;
import electrodynamics.client.screen.tile.ScreenCompressor;
import electrodynamics.client.screen.tile.ScreenHydroelectricGenerator;
import electrodynamics.client.screen.tile.ScreenLithiumBatteryBox;
import electrodynamics.client.screen.tile.ScreenMineralWasher;
import electrodynamics.client.screen.tile.ScreenMotorComplex;
import electrodynamics.client.screen.tile.ScreenO2OProcessor;
import electrodynamics.client.screen.tile.ScreenO2OProcessorDouble;
import electrodynamics.client.screen.tile.ScreenO2OProcessorTriple;
import electrodynamics.client.screen.tile.ScreenQuarry;
import electrodynamics.client.screen.tile.ScreenSeismicRelay;
import electrodynamics.client.screen.tile.ScreenSolarPanel;
import electrodynamics.client.screen.tile.ScreenThermoelectricManipulator;
import electrodynamics.client.screen.tile.ScreenFluidTankGeneric;
import electrodynamics.client.screen.tile.ScreenWindmill;
import electrodynamics.common.item.gear.tools.electric.ItemElectricBaton;
import electrodynamics.common.item.gear.tools.electric.ItemElectricChainsaw;
import electrodynamics.common.item.gear.tools.electric.ItemElectricDrill;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import electrodynamics.registers.ElectrodynamicsEntities;
import electrodynamics.registers.ElectrodynamicsItems;
import electrodynamics.registers.ElectrodynamicsMenuTypes;
import electrodynamics.registers.ElectrodynamicsParticles;
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
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = References.ID, bus = Bus.MOD, value = { Dist.CLIENT })
public class ClientRegister {

	private static final String BLOCK_LOC = References.ID + ":block/";
	private static final String CUSTOM_LOC = References.ID + ":custom/";

	// sometimes I fucking hate this game
	public static LayerDefinition COMPOSITE_ARMOR_LAYER_LEG_NOCHEST = ModelCompositeArmor.createBodyLayer(1, true);
	public static LayerDefinition COMPOSITE_ARMOR_LAYER_BOOTS = ModelCompositeArmor.createBodyLayer(2, false);
	public static LayerDefinition COMPOSITE_ARMOR_LAYER_COMB_NOCHEST = ModelCompositeArmor.createBodyLayer(3, true);
	public static LayerDefinition COMPOSITE_ARMOR_LAYER_LEG_CHEST = ModelCompositeArmor.createBodyLayer(1, false);
	public static LayerDefinition COMPOSITE_ARMOR_LAYER_COMB_CHEST = ModelCompositeArmor.createBodyLayer(3, false);

	public static LayerDefinition NIGHT_VISION_GOGGLES = ModelNightVisionGoggles.createBodyLayer();

	public static LayerDefinition HYDRAULIC_BOOTS = ModelHydraulicBoots.createBodyLayer();

	public static LayerDefinition JETPACK = ModelJetpack.createBodyLayer();

	public static LayerDefinition SERVO_LEGGINGS = ModelServoLeggings.createBodyLayer();

	public static LayerDefinition COMBAT_ARMOR_LAYER_LEG_NOCHEST = ModelCombatArmor.createBodyLayer(1, true);
	public static LayerDefinition COMBAT_ARMOR_LAYER_BOOTS = ModelCombatArmor.createBodyLayer(2, false);
	public static LayerDefinition COMBAT_ARMOR_LAYER_COMB_NOCHEST = ModelCombatArmor.createBodyLayer(3, true);
	public static LayerDefinition COMBAT_ARMOR_LAYER_LEG_CHEST = ModelCombatArmor.createBodyLayer(1, false);
	public static LayerDefinition COMBAT_ARMOR_LAYER_COMB_CHEST = ModelCombatArmor.createBodyLayer(3, false);

	public static HashMap<ResourceLocation, TextureAtlasSprite> CACHED_TEXTUREATLASSPRITES = new HashMap<>();
	// for registration purposes only!
	private static final List<ResourceLocation> CUSTOM_TEXTURES = new ArrayList<>();

	public static final ResourceLocation ON = new ResourceLocation("on");

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
	public static final ResourceLocation MODEL_CARBYNEBATTERYBOX = new ResourceLocation(BLOCK_LOC + "carbynebatterybox");
	public static final ResourceLocation MODEL_CARBYNEBATTERYBOX2 = new ResourceLocation(BLOCK_LOC + "carbynebatterybox2");
	public static final ResourceLocation MODEL_CARBYNEBATTERYBOX3 = new ResourceLocation(BLOCK_LOC + "carbynebatterybox3");
	public static final ResourceLocation MODEL_CARBYNEBATTERYBOX4 = new ResourceLocation(BLOCK_LOC + "carbynebatterybox4");
	public static final ResourceLocation MODEL_CARBYNEBATTERYBOX5 = new ResourceLocation(BLOCK_LOC + "carbynebatterybox5");
	public static final ResourceLocation MODEL_CARBYNEBATTERYBOX6 = new ResourceLocation(BLOCK_LOC + "carbynebatterybox6");
	public static final ResourceLocation MODEL_CARBYNEBATTERYBOX7 = new ResourceLocation(BLOCK_LOC + "carbynebatterybox7");
	public static final ResourceLocation MODEL_CHEMICALMIXERBASE = new ResourceLocation(BLOCK_LOC + "chemicalmixerbase");
	public static final ResourceLocation MODEL_HYDROELECTRICGENERATORBLADES = new ResourceLocation(BLOCK_LOC + "hydroelectricgeneratorblades");
	public static final ResourceLocation MODEL_WINDMILLBLADES = new ResourceLocation(BLOCK_LOC + "windmillblades");
	public static final ResourceLocation MODEL_MINERALCRUSHERHANDLE = new ResourceLocation(BLOCK_LOC + "mineralcrusherhandle");
	public static final ResourceLocation MODEL_MINERALCRUSHERDOUBLEHANDLE = new ResourceLocation(BLOCK_LOC + "mineralcrusherdoublehandle");
	public static final ResourceLocation MODEL_MINERALCRUSHERTRIPLEHANDLE = new ResourceLocation(BLOCK_LOC + "mineralcrushertriplehandle");
	public static final ResourceLocation MODEL_MINERALGRINDERWHEEL = new ResourceLocation(BLOCK_LOC + "mineralgrinderwheel");
	public static final ResourceLocation MODEL_CHEMICALMIXERBLADES = new ResourceLocation(BLOCK_LOC + "chemicalmixerblades");
	public static final ResourceLocation MODEL_LATHESHAFT = new ResourceLocation(BLOCK_LOC + "latheshaft");
	public static final ResourceLocation MODEL_MOTORCOMPLEXROTOR = new ResourceLocation(BLOCK_LOC + "motorcomplexrotor");

	public static final ResourceLocation MODEL_RODSTEEL = new ResourceLocation(References.ID + ":entity/rodsteel");
	public static final ResourceLocation MODEL_RODSTAINLESSSTEEL = new ResourceLocation(References.ID + ":entity/rodstainlesssteel");
	public static final ResourceLocation MODEL_RODHSLASTEEL = new ResourceLocation(References.ID + ":entity/rodhslasteel");

	public static final ResourceLocation TEXTURE_RODSTEEL = new ResourceLocation(References.ID + ":textures/entity/projectile/rodsteel.png");
	public static final ResourceLocation TEXTURE_RODSTAINLESSSTEEL = new ResourceLocation(References.ID + ":textures/entity/projectile/rodstainlesssteel.png");
	public static final ResourceLocation TEXTURE_RODHSLASTEEL = new ResourceLocation(References.ID + ":textures/entity/projectile/rodhslasteel.png");

	public static final ResourceLocation MODEL_QUARRYWHEEL_STILL = new ResourceLocation(BLOCK_LOC + "quarrywheelstill");
	public static final ResourceLocation MODEL_QUARRYWHEEL_ROT = new ResourceLocation(BLOCK_LOC + "quarrywheelrot");

	// Custom Textures
	public static final ResourceLocation TEXTURE_QUARRYARM = new ResourceLocation(CUSTOM_LOC + "quarryarm");
	public static final ResourceLocation TEXTURE_QUARRYARM_DARK = new ResourceLocation(CUSTOM_LOC + "quarrydark");
	public static final ResourceLocation TEXTURE_WHITE = new ResourceLocation("forge", "white");

	public static final ResourceLocation TEXTURE_PLASMA_BALL = new ResourceLocation(CUSTOM_LOC + "plasmaorb");
	
	public static final ResourceLocation TEXTURE_MERCURY = new ResourceLocation(CUSTOM_LOC + "mercury");
	
	public static final ResourceLocation TEXTURE_GAS = new ResourceLocation(CUSTOM_LOC + "gastexture"); // use this texture when needing to render a visual representation of a gas that is not a barometer

	public static void setup() {
		ClientEvents.init();

		MenuScreens.register(ElectrodynamicsMenuTypes.CONTAINER_COALGENERATOR.get(), ScreenCoalGenerator::new);
		MenuScreens.register(ElectrodynamicsMenuTypes.CONTAINER_ELECTRICFURNACE.get(), ScreenElectricFurnace::new);
		MenuScreens.register(ElectrodynamicsMenuTypes.CONTAINER_ELECTRICFURNACEDOUBLE.get(), ScreenElectricFurnaceDouble::new);
		MenuScreens.register(ElectrodynamicsMenuTypes.CONTAINER_ELECTRICFURNACETRIPLE.get(), ScreenElectricFurnaceTriple::new);
		MenuScreens.register(ElectrodynamicsMenuTypes.CONTAINER_ELECTRICARCFURNACE.get(), ScreenElectricArcFurnace::new);
		MenuScreens.register(ElectrodynamicsMenuTypes.CONTAINER_ELECTRICARCFURNACEDOUBLE.get(), ScreenElectricArcFurnaceDouble::new);
		MenuScreens.register(ElectrodynamicsMenuTypes.CONTAINER_ELECTRICARCFURNACETRIPLE.get(), ScreenElectricArcFurnaceTriple::new);
		MenuScreens.register(ElectrodynamicsMenuTypes.CONTAINER_O2OPROCESSOR.get(), ScreenO2OProcessor::new);
		MenuScreens.register(ElectrodynamicsMenuTypes.CONTAINER_O2OPROCESSORDOUBLE.get(), ScreenO2OProcessorDouble::new);
		MenuScreens.register(ElectrodynamicsMenuTypes.CONTAINER_O2OPROCESSORTRIPLE.get(), ScreenO2OProcessorTriple::new);
		MenuScreens.register(ElectrodynamicsMenuTypes.CONTAINER_DO2OPROCESSOR.get(), ScreenDO2OProcessor::new);
		MenuScreens.register(ElectrodynamicsMenuTypes.CONTAINER_BATTERYBOX.get(), ScreenBatteryBox::new);
		MenuScreens.register(ElectrodynamicsMenuTypes.CONTAINER_LITHIUMBATTERYBOX.get(), ScreenLithiumBatteryBox::new);
		MenuScreens.register(ElectrodynamicsMenuTypes.CONTAINER_CARBYNEBATTERYBOX.get(), ScreenCarbyneBatteryBox::new);
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
		MenuScreens.register(ElectrodynamicsMenuTypes.CONTAINER_GUIDEBOOK.get(), ScreenGuidebook::new);
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

		ItemProperties.register(ElectrodynamicsItems.ITEM_ELECTRICBATON.get(), ON, (stack, world, entity, call) -> entity != null && (entity.getMainHandItem() == stack || entity.getOffhandItem() == stack) && ((ItemElectricBaton) stack.getItem()).getJoulesStored(stack) > ((ItemElectricBaton) stack.getItem()).getElectricProperties().extract.getJoules() ? 1 : 0);
		ItemProperties.register(ElectrodynamicsItems.ITEM_ELECTRICDRILL.get(), ON, (stack, world, entity, call) -> entity != null && (entity.getMainHandItem() == stack || entity.getOffhandItem() == stack) && ((ItemElectricDrill) stack.getItem()).getJoulesStored(stack) > ((ItemElectricDrill) stack.getItem()).getElectricProperties().extract.getJoules() ? 1 : 0);
		ItemProperties.register(ElectrodynamicsItems.ITEM_ELECTRICCHAINSAW.get(), ON, (stack, world, entity, call) -> entity != null && (entity.getMainHandItem() == stack || entity.getOffhandItem() == stack) && ((ItemElectricChainsaw) stack.getItem()).getJoulesStored(stack) > ((ItemElectricChainsaw) stack.getItem()).getElectricProperties().extract.getJoules() ? 1 : 0);

		ScreenGuidebook.addGuidebookModule(new ModuleElectrodynamics());
	}

	@SubscribeEvent
	public static void registerEntities(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(ElectrodynamicsEntities.ENTITY_ENERGYBLAST.get(), RenderEnergyBlast::new);
		event.registerEntityRenderer(ElectrodynamicsEntities.ENTITY_METALROD.get(), RenderMetalRod::new);

		event.registerBlockEntityRenderer(ElectrodynamicsBlockTypes.TILE_ADVANCEDSOLARPANEL.get(), RenderAdvancedSolarPanel::new);
		event.registerBlockEntityRenderer(ElectrodynamicsBlockTypes.TILE_BATTERYBOX.get(), RenderBatteryBox::new);
		event.registerBlockEntityRenderer(ElectrodynamicsBlockTypes.TILE_LITHIUMBATTERYBOX.get(), RenderLithiumBatteryBox::new);
		event.registerBlockEntityRenderer(ElectrodynamicsBlockTypes.TILE_CARBYNEBATTERYBOX.get(), RenderCarbyneBatteryBox::new);
		event.registerBlockEntityRenderer(ElectrodynamicsBlockTypes.TILE_HYDROELECTRICGENERATOR.get(), RenderHydroelectricGenerator::new);
		event.registerBlockEntityRenderer(ElectrodynamicsBlockTypes.TILE_WINDMILL.get(), RenderWindmill::new);
		event.registerBlockEntityRenderer(ElectrodynamicsBlockTypes.TILE_MINERALCRUSHER.get(), RenderMineralCrusher::new);
		event.registerBlockEntityRenderer(ElectrodynamicsBlockTypes.TILE_MINERALCRUSHERDOUBLE.get(), RenderMineralCrusherDouble::new);
		event.registerBlockEntityRenderer(ElectrodynamicsBlockTypes.TILE_MINERALCRUSHERTRIPLE.get(), RenderMineralCrusherTriple::new);
		event.registerBlockEntityRenderer(ElectrodynamicsBlockTypes.TILE_MINERALGRINDER.get(), RenderMineralGrinder::new);
		event.registerBlockEntityRenderer(ElectrodynamicsBlockTypes.TILE_MINERALGRINDERDOUBLE.get(), RenderMineralGrinderDouble::new);
		event.registerBlockEntityRenderer(ElectrodynamicsBlockTypes.TILE_MINERALGRINDERTRIPLE.get(), RenderMineralGrinderTriple::new);
		event.registerBlockEntityRenderer(ElectrodynamicsBlockTypes.TILE_FERMENTATIONPLANT.get(), RenderFermentationPlant::new);
		event.registerBlockEntityRenderer(ElectrodynamicsBlockTypes.TILE_COMBUSTIONCHAMBER.get(), RenderCombustionChamber::new);
		event.registerBlockEntityRenderer(ElectrodynamicsBlockTypes.TILE_MINERALWASHER.get(), RenderMineralWasher::new);
		event.registerBlockEntityRenderer(ElectrodynamicsBlockTypes.TILE_CHEMICALMIXER.get(), RenderChemicalMixer::new);
		event.registerBlockEntityRenderer(ElectrodynamicsBlockTypes.TILE_MULTIMETERBLOCK.get(), RenderMultimeterBlock::new);
		event.registerBlockEntityRenderer(ElectrodynamicsBlockTypes.TILE_LATHE.get(), RenderLathe::new);
		event.registerBlockEntityRenderer(ElectrodynamicsBlockTypes.TILE_CHARGERLV.get(), RenderChargerGeneric::new);
		event.registerBlockEntityRenderer(ElectrodynamicsBlockTypes.TILE_CHARGERMV.get(), RenderChargerGeneric::new);
		event.registerBlockEntityRenderer(ElectrodynamicsBlockTypes.TILE_CHARGERHV.get(), RenderChargerGeneric::new);
		event.registerBlockEntityRenderer(ElectrodynamicsBlockTypes.TILE_SEISMICRELAY.get(), RenderSeismicRelay::new);
		event.registerBlockEntityRenderer(ElectrodynamicsBlockTypes.TILE_COOLANTRESAVOIR.get(), RenderCoolantResavoir::new);
		event.registerBlockEntityRenderer(ElectrodynamicsBlockTypes.TILE_TANKHSLA.get(), RenderTankGeneric::new);
		event.registerBlockEntityRenderer(ElectrodynamicsBlockTypes.TILE_TANKREINFORCED.get(), RenderTankGeneric::new);
		event.registerBlockEntityRenderer(ElectrodynamicsBlockTypes.TILE_TANKSTEEL.get(), RenderTankGeneric::new);
		event.registerBlockEntityRenderer(ElectrodynamicsBlockTypes.TILE_MOTORCOMPLEX.get(), RenderMotorComplex::new);
		event.registerBlockEntityRenderer(ElectrodynamicsBlockTypes.TILE_ELECTROLYTICSEPARATOR.get(), RenderElectrolyticSeparator::new);
		event.registerBlockEntityRenderer(ElectrodynamicsBlockTypes.TILE_GASPIPEPUMP.get(), RenderGasPipePump::new);
		event.registerBlockEntityRenderer(ElectrodynamicsBlockTypes.TILE_FLUIDPIPEPUMP.get(), RenderFluidPipePump::new);
		event.registerBlockEntityRenderer(ElectrodynamicsBlockTypes.TILE_LOGISTICALWIRE.get(), RenderLogisticalWire::new);
		
		event.registerBlockEntityRenderer(ElectrodynamicsBlockTypes.TILE_WIRE.get(), RenderConnectBlock::new);
		event.registerBlockEntityRenderer(ElectrodynamicsBlockTypes.TILE_LOGISTICALWIRE.get(), RenderConnectBlock::new);
		event.registerBlockEntityRenderer(ElectrodynamicsBlockTypes.TILE_PIPE.get(), RenderConnectBlock::new);
		event.registerBlockEntityRenderer(ElectrodynamicsBlockTypes.TILE_GAS_PIPE.get(), RenderConnectBlock::new);
	}

	public static boolean shouldMultilayerRender(RenderType type) {
		return type == RenderType.translucent() || type == RenderType.solid();
	}

	static {
		CUSTOM_TEXTURES.add(ClientRegister.TEXTURE_QUARRYARM);
		CUSTOM_TEXTURES.add(ClientRegister.TEXTURE_QUARRYARM_DARK);
		CUSTOM_TEXTURES.add(ClientRegister.TEXTURE_WHITE);
		CUSTOM_TEXTURES.add(ClientRegister.TEXTURE_PLASMA_BALL);
		CUSTOM_TEXTURES.add(ClientRegister.TEXTURE_MERCURY);
		CUSTOM_TEXTURES.add(ClientRegister.TEXTURE_GAS);
	}

	@SubscribeEvent
	public static void addCustomTextureAtlases(TextureStitchEvent.Pre event) {
		if (event.getAtlas().location().equals(TextureAtlas.LOCATION_BLOCKS)) {
			CUSTOM_TEXTURES.forEach(event::addSprite);
		}
	}

	@SubscribeEvent
	public static void cacheCustomTextureAtlases(TextureStitchEvent.Post event) {
		if (event.getAtlas().location().equals(TextureAtlas.LOCATION_BLOCKS)) {
			for (ResourceLocation loc : CUSTOM_TEXTURES) {
				ClientRegister.CACHED_TEXTUREATLASSPRITES.put(loc, event.getAtlas().getSprite(loc));
			}
		}
	}

	@SubscribeEvent
	public static void registerParticles(RegisterParticleProvidersEvent event) {
		event.register(ElectrodynamicsParticles.PARTICLE_PLASMA_BALL.get(), ParticlePlasmaBall.Factory::new);
	}

}
