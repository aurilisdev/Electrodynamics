package electrodynamics.client;

import java.util.HashMap;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.References;
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
import electrodynamics.client.render.tile.RenderCoolantResavoir;
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
import electrodynamics.client.render.tile.RenderMotorComplex;
import electrodynamics.client.render.tile.RenderMultimeterBlock;
import electrodynamics.client.render.tile.RenderSeismicRelay;
import electrodynamics.client.render.tile.RenderTankGeneric;
import electrodynamics.client.render.tile.RenderWindmill;
import electrodynamics.client.screen.item.ScreenSeismicScanner;
import electrodynamics.client.screen.tile.ScreenBatteryBox;
import electrodynamics.client.screen.tile.ScreenCarbyneBatteryBox;
import electrodynamics.client.screen.tile.ScreenChargerGeneric;
import electrodynamics.client.screen.tile.ScreenChemicalCrystallizer;
import electrodynamics.client.screen.tile.ScreenChemicalMixer;
import electrodynamics.client.screen.tile.ScreenCoalGenerator;
import electrodynamics.client.screen.tile.ScreenCobblestoneGenerator;
import electrodynamics.client.screen.tile.ScreenCombustionChamber;
import electrodynamics.client.screen.tile.ScreenCoolantResavoir;
import electrodynamics.client.screen.tile.ScreenCreativeFluidSource;
import electrodynamics.client.screen.tile.ScreenCreativePowerSource;
import electrodynamics.client.screen.tile.ScreenDO2OProcessor;
import electrodynamics.client.screen.tile.ScreenElectricArcFurnace;
import electrodynamics.client.screen.tile.ScreenElectricArcFurnaceDouble;
import electrodynamics.client.screen.tile.ScreenElectricArcFurnaceTriple;
import electrodynamics.client.screen.tile.ScreenElectricFurnace;
import electrodynamics.client.screen.tile.ScreenElectricFurnaceDouble;
import electrodynamics.client.screen.tile.ScreenElectricFurnaceTriple;
import electrodynamics.client.screen.tile.ScreenElectrolyticSeparator;
import electrodynamics.client.screen.tile.ScreenFermentationPlant;
import electrodynamics.client.screen.tile.ScreenFluidVoid;
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
import electrodynamics.client.screen.tile.ScreenTankGeneric;
import electrodynamics.client.screen.tile.ScreenWindmill;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.item.gear.tools.electric.ItemElectricChainsaw;
import electrodynamics.common.item.gear.tools.electric.ItemElectricDrill;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ForgeModelBakery;
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
	
	@SubscribeEvent
	public static void onModelEvent(ModelRegistryEvent event) {
		ForgeModelBakery.addSpecialModel(MODEL_ADVSOLARTOP);
		ForgeModelBakery.addSpecialModel(MODEL_ADVSOLARBASE);
		ForgeModelBakery.addSpecialModel(MODEL_BATTERYBOX);
		ForgeModelBakery.addSpecialModel(MODEL_BATTERYBOX2);
		ForgeModelBakery.addSpecialModel(MODEL_BATTERYBOX3);
		ForgeModelBakery.addSpecialModel(MODEL_BATTERYBOX4);
		ForgeModelBakery.addSpecialModel(MODEL_BATTERYBOX5);
		ForgeModelBakery.addSpecialModel(MODEL_BATTERYBOX6);
		ForgeModelBakery.addSpecialModel(MODEL_BATTERYBOX7);
		ForgeModelBakery.addSpecialModel(MODEL_LITHIUMBATTERYBOX);
		ForgeModelBakery.addSpecialModel(MODEL_LITHIUMBATTERYBOX2);
		ForgeModelBakery.addSpecialModel(MODEL_LITHIUMBATTERYBOX3);
		ForgeModelBakery.addSpecialModel(MODEL_LITHIUMBATTERYBOX4);
		ForgeModelBakery.addSpecialModel(MODEL_LITHIUMBATTERYBOX5);
		ForgeModelBakery.addSpecialModel(MODEL_LITHIUMBATTERYBOX6);
		ForgeModelBakery.addSpecialModel(MODEL_LITHIUMBATTERYBOX7);
		ForgeModelBakery.addSpecialModel(MODEL_CARBYNEBATTERYBOX);
		ForgeModelBakery.addSpecialModel(MODEL_CARBYNEBATTERYBOX2);
		ForgeModelBakery.addSpecialModel(MODEL_CARBYNEBATTERYBOX3);
		ForgeModelBakery.addSpecialModel(MODEL_CARBYNEBATTERYBOX4);
		ForgeModelBakery.addSpecialModel(MODEL_CARBYNEBATTERYBOX5);
		ForgeModelBakery.addSpecialModel(MODEL_CARBYNEBATTERYBOX6);
		ForgeModelBakery.addSpecialModel(MODEL_CARBYNEBATTERYBOX7);
		ForgeModelBakery.addSpecialModel(MODEL_HYDROELECTRICGENERATORBLADES);
		ForgeModelBakery.addSpecialModel(MODEL_WINDMILLBLADES);
		ForgeModelBakery.addSpecialModel(MODEL_MINERALCRUSHERBASE);
		ForgeModelBakery.addSpecialModel(MODEL_MINERALCRUSHERHANDLE);
		ForgeModelBakery.addSpecialModel(MODEL_MINERALCRUSHERDOUBLEBASE);
		ForgeModelBakery.addSpecialModel(MODEL_MINERALCRUSHERDOUBLEHANDLE);
		ForgeModelBakery.addSpecialModel(MODEL_MINERALCRUSHERTRIPLEBASE);
		ForgeModelBakery.addSpecialModel(MODEL_MINERALCRUSHERTRIPLEHANDLE);
		ForgeModelBakery.addSpecialModel(MODEL_MINERALGRINDERBASE);
		ForgeModelBakery.addSpecialModel(MODEL_MINERALGRINDERWHEEL);
		ForgeModelBakery.addSpecialModel(MODEL_MINERALGRINDERDOUBLEBASE);
		ForgeModelBakery.addSpecialModel(MODEL_MINERALGRINDERTRIPLEBASE);
		ForgeModelBakery.addSpecialModel(MODEL_FERMENTATIONPLANTWATER);
		ForgeModelBakery.addSpecialModel(MODEL_FERMENTATIONPLANTETHANOL);
		ForgeModelBakery.addSpecialModel(MODEL_COMBUSTIONCHAMBERETHANOL);
		ForgeModelBakery.addSpecialModel(MODEL_COMBUSTIONCHAMBERHYDROGEN);
		ForgeModelBakery.addSpecialModel(MODEL_CHEMICALMIXERBASE);
		ForgeModelBakery.addSpecialModel(MODEL_CHEMICALMIXERBLADES);
		ForgeModelBakery.addSpecialModel(MODEL_CHEMICALMIXERWATER);
		ForgeModelBakery.addSpecialModel(MODEL_CHEMICALMIXERSULFURICACID);
		ForgeModelBakery.addSpecialModel(MODEL_RODSTEEL);
		ForgeModelBakery.addSpecialModel(MODEL_RODSTAINLESSSTEEL);
		ForgeModelBakery.addSpecialModel(MODEL_RODHSLASTEEL);
		ForgeModelBakery.addSpecialModel(MODEL_LATHE);
		ForgeModelBakery.addSpecialModel(MODEL_LATHESHAFT);
		ForgeModelBakery.addSpecialModel(MODEL_MOTORCOMPLEXROTOR);
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
	public static final ResourceLocation MODEL_CARBYNEBATTERYBOX = new ResourceLocation(BLOCK_LOC + "carbynebatterybox");
	public static final ResourceLocation MODEL_CARBYNEBATTERYBOX2 = new ResourceLocation(BLOCK_LOC + "carbynebatterybox2");
	public static final ResourceLocation MODEL_CARBYNEBATTERYBOX3 = new ResourceLocation(BLOCK_LOC + "carbynebatterybox3");
	public static final ResourceLocation MODEL_CARBYNEBATTERYBOX4 = new ResourceLocation(BLOCK_LOC + "carbynebatterybox4");
	public static final ResourceLocation MODEL_CARBYNEBATTERYBOX5 = new ResourceLocation(BLOCK_LOC + "carbynebatterybox5");
	public static final ResourceLocation MODEL_CARBYNEBATTERYBOX6 = new ResourceLocation(BLOCK_LOC + "carbynebatterybox6");
	public static final ResourceLocation MODEL_CARBYNEBATTERYBOX7 = new ResourceLocation(BLOCK_LOC + "carbynebatterybox7");
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
	public static final ResourceLocation MODEL_COMBUSTIONCHAMBERHYDROGEN = new ResourceLocation(BLOCK_LOC + "combustionchamberhydrogen");
	public static final ResourceLocation MODEL_CHEMICALMIXERBASE = new ResourceLocation(BLOCK_LOC + "chemicalmixerbase");
	public static final ResourceLocation MODEL_CHEMICALMIXERBLADES = new ResourceLocation(BLOCK_LOC + "chemicalmixerblades");
	public static final ResourceLocation MODEL_CHEMICALMIXERWATER = new ResourceLocation(BLOCK_LOC + "chemicalmixerwater");
	public static final ResourceLocation MODEL_CHEMICALMIXERSULFURICACID = new ResourceLocation(BLOCK_LOC + "chemicalmixersulfuricacid");
	public static final ResourceLocation MODEL_LATHE = new ResourceLocation(BLOCK_LOC + "lathe");
	public static final ResourceLocation MODEL_LATHESHAFT = new ResourceLocation(BLOCK_LOC + "latheshaft");
	public static final ResourceLocation MODEL_MOTORCOMPLEXROTOR = new ResourceLocation(BLOCK_LOC + "motorcomplexrotor");

	public static final ResourceLocation MODEL_RODSTEEL = new ResourceLocation(References.ID + ":entity/rodsteel");
	public static final ResourceLocation MODEL_RODSTAINLESSSTEEL = new ResourceLocation(References.ID + ":entity/rodstainlesssteel");
	public static final ResourceLocation MODEL_RODHSLASTEEL = new ResourceLocation(References.ID + ":entity/rodhslasteel");

	public static final ResourceLocation TEXTURE_RODSTEEL = new ResourceLocation(References.ID + ":textures/entity/projectile/rodsteel.png");
	public static final ResourceLocation TEXTURE_RODSTAINLESSSTEEL = new ResourceLocation(References.ID + ":textures/entity/projectile/rodstainlesssteel.png");
	public static final ResourceLocation TEXTURE_RODHSLASTEEL = new ResourceLocation(References.ID + ":textures/entity/projectile/rodhslasteel.png");

	//Custom Textures
	public static final ResourceLocation TEXTURE_QUARRYARM = new ResourceLocation(CUSTOM_LOC + "quarryarm");
	
	
	
	public static void setup() {
		MenuScreens.register(DeferredRegisters.CONTAINER_COALGENERATOR.get(), ScreenCoalGenerator::new);
		MenuScreens.register(DeferredRegisters.CONTAINER_ELECTRICFURNACE.get(), ScreenElectricFurnace::new);
		MenuScreens.register(DeferredRegisters.CONTAINER_ELECTRICFURNACEDOUBLE.get(), ScreenElectricFurnaceDouble::new);
		MenuScreens.register(DeferredRegisters.CONTAINER_ELECTRICFURNACETRIPLE.get(), ScreenElectricFurnaceTriple::new);
		MenuScreens.register(DeferredRegisters.CONTAINER_ELECTRICARCFURNACE.get(), ScreenElectricArcFurnace::new);
		MenuScreens.register(DeferredRegisters.CONTAINER_ELECTRICARCFURNACEDOUBLE.get(), ScreenElectricArcFurnaceDouble::new);
		MenuScreens.register(DeferredRegisters.CONTAINER_ELECTRICARCFURNACETRIPLE.get(), ScreenElectricArcFurnaceTriple::new);
		MenuScreens.register(DeferredRegisters.CONTAINER_O2OPROCESSOR.get(), ScreenO2OProcessor::new);
		MenuScreens.register(DeferredRegisters.CONTAINER_O2OPROCESSORDOUBLE.get(), ScreenO2OProcessorDouble::new);
		MenuScreens.register(DeferredRegisters.CONTAINER_O2OPROCESSORTRIPLE.get(), ScreenO2OProcessorTriple::new);
		MenuScreens.register(DeferredRegisters.CONTAINER_DO2OPROCESSOR.get(), ScreenDO2OProcessor::new);
		MenuScreens.register(DeferredRegisters.CONTAINER_BATTERYBOX.get(), ScreenBatteryBox::new);
		MenuScreens.register(DeferredRegisters.CONTAINER_LITHIUMBATTERYBOX.get(), ScreenLithiumBatteryBox::new);
		MenuScreens.register(DeferredRegisters.CONTAINER_CARBYNEBATTERYBOX.get(), ScreenCarbyneBatteryBox::new);
		MenuScreens.register(DeferredRegisters.CONTAINER_FERMENTATIONPLANT.get(), ScreenFermentationPlant::new);
		MenuScreens.register(DeferredRegisters.CONTAINER_MINERALWASHER.get(), ScreenMineralWasher::new);
		MenuScreens.register(DeferredRegisters.CONTAINER_CHEMICALMIXER.get(), ScreenChemicalMixer::new);
		MenuScreens.register(DeferredRegisters.CONTAINER_CHEMICALCRYSTALLIZER.get(), ScreenChemicalCrystallizer::new);
		MenuScreens.register(DeferredRegisters.CONTAINER_CHARGER.get(), ScreenChargerGeneric::new);
		MenuScreens.register(DeferredRegisters.CONTAINER_TANK.get(), ScreenTankGeneric::new);
		MenuScreens.register(DeferredRegisters.CONTAINER_COMBUSTION_CHAMBER.get(), ScreenCombustionChamber::new);
		MenuScreens.register(DeferredRegisters.CONTAINER_SOLARPANEL.get(), ScreenSolarPanel::new);
		MenuScreens.register(DeferredRegisters.CONTAINER_WINDMILL.get(), ScreenWindmill::new);
		MenuScreens.register(DeferredRegisters.CONTAINER_HYDROELECTRICGENERATOR.get(), ScreenHydroelectricGenerator::new);
		MenuScreens.register(DeferredRegisters.CONTAINER_COBBLESTONEGENERATOR.get(), ScreenCobblestoneGenerator::new);
		MenuScreens.register(DeferredRegisters.CONTAINER_CREATIVEPOWERSOURCE.get(), ScreenCreativePowerSource::new);
		MenuScreens.register(DeferredRegisters.CONTAINER_CREATIVEFLUIDSOURCE.get(), ScreenCreativeFluidSource::new);
		MenuScreens.register(DeferredRegisters.CONTAINER_FLUIDVOID.get(), ScreenFluidVoid::new);
		MenuScreens.register(DeferredRegisters.CONTAINER_SEISMICSCANNER.get(), ScreenSeismicScanner::new);
		MenuScreens.register(DeferredRegisters.CONTAINER_ELECTROLYTICSEPARATOR.get(), ScreenElectrolyticSeparator::new);
		MenuScreens.register(DeferredRegisters.CONTAINER_SEISMICRELAY.get(), ScreenSeismicRelay::new);
		MenuScreens.register(DeferredRegisters.CONTAINER_COOLANTRESAVOIR.get(), ScreenCoolantResavoir::new);
		MenuScreens.register(DeferredRegisters.CONTAINER_MOTORCOMPLEX.get(), ScreenMotorComplex::new);
		MenuScreens.register(DeferredRegisters.CONTAINER_QUARRY.get(), ScreenQuarry::new);

		ItemBlockRenderTypes.setRenderLayer(DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.coalgeneratorrunning), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.wiremill), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.fermentationplant), ClientRegister::shouldMultilayerRender);
		ItemBlockRenderTypes.setRenderLayer(DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.combustionchamber), ClientRegister::shouldMultilayerRender);
		ItemBlockRenderTypes.setRenderLayer(DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.mineralwasher), ClientRegister::shouldMultilayerRender);
		ItemBlockRenderTypes.setRenderLayer(DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.chemicalcrystallizer), ClientRegister::shouldMultilayerRender);
		ItemBlockRenderTypes.setRenderLayer(DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.circuitbreaker), ClientRegister::shouldMultilayerRender);
		ItemBlockRenderTypes.setRenderLayer(DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.combustionchamber), ClientRegister::shouldMultilayerRender);
		ItemBlockRenderTypes.setRenderLayer(DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.coolantresavoir), ClientRegister::shouldMultilayerRender);
		ItemBlockRenderTypes.setRenderLayer(DeferredRegisters.multi, RenderType.cutout());
		ItemProperties.register(DeferredRegisters.ITEM_ELECTRICDRILL.get(), new ResourceLocation("on"), (stack, world, entity, call) -> entity != null && (entity.getMainHandItem() == stack || entity.getOffhandItem() == stack) && ((ItemElectricDrill) stack.getItem()).getJoulesStored(stack) > ((ItemElectricDrill) stack.getItem()).getElectricProperties().extract.getJoules() ? 1 : 0);
		ItemProperties.register(DeferredRegisters.ITEM_ELECTRICCHAINSAW.get(), new ResourceLocation("on"), (stack, world, entity, call) -> entity != null && (entity.getMainHandItem() == stack || entity.getOffhandItem() == stack) && ((ItemElectricChainsaw) stack.getItem()).getJoulesStored(stack) > ((ItemElectricChainsaw) stack.getItem()).getElectricProperties().extract.getJoules() ? 1 : 0);
		KeyBinds.registerKeys();
	}

	@SubscribeEvent
	public static void registerEntities(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(DeferredRegisters.ENTITY_ENERGYBLAST.get(), RenderEnergyBlast::new);
		event.registerEntityRenderer(DeferredRegisters.ENTITY_METALROD.get(), RenderMetalRod::new);
		event.registerBlockEntityRenderer(DeferredRegisters.TILE_ADVANCEDSOLARPANEL.get(), RenderAdvancedSolarPanel::new);
		event.registerBlockEntityRenderer(DeferredRegisters.TILE_BATTERYBOX.get(), RenderBatteryBox::new);
		event.registerBlockEntityRenderer(DeferredRegisters.TILE_LITHIUMBATTERYBOX.get(), RenderLithiumBatteryBox::new);
		event.registerBlockEntityRenderer(DeferredRegisters.TILE_CARBYNEBATTERYBOX.get(), RenderCarbyneBatteryBox::new);
		event.registerBlockEntityRenderer(DeferredRegisters.TILE_HYDROELECTRICGENERATOR.get(), RenderHydroelectricGenerator::new);
		event.registerBlockEntityRenderer(DeferredRegisters.TILE_WINDMILL.get(), RenderWindmill::new);
		event.registerBlockEntityRenderer(DeferredRegisters.TILE_MINERALCRUSHER.get(), RenderMineralCrusher::new);
		event.registerBlockEntityRenderer(DeferredRegisters.TILE_MINERALCRUSHERDOUBLE.get(), RenderMineralCrusherDouble::new);
		event.registerBlockEntityRenderer(DeferredRegisters.TILE_MINERALCRUSHERTRIPLE.get(), RenderMineralCrusherTriple::new);
		event.registerBlockEntityRenderer(DeferredRegisters.TILE_MINERALGRINDER.get(), RenderMineralGrinder::new);
		event.registerBlockEntityRenderer(DeferredRegisters.TILE_MINERALGRINDERDOUBLE.get(), RenderMineralGrinderDouble::new);
		event.registerBlockEntityRenderer(DeferredRegisters.TILE_MINERALGRINDERTRIPLE.get(), RenderMineralGrinderTriple::new);
		event.registerBlockEntityRenderer(DeferredRegisters.TILE_FERMENTATIONPLANT.get(), RenderFermentationPlant::new);
		event.registerBlockEntityRenderer(DeferredRegisters.TILE_COMBUSTIONCHAMBER.get(), RenderCombustionChamber::new);
		event.registerBlockEntityRenderer(DeferredRegisters.TILE_MINERALWASHER.get(), RenderMineralWasher::new);
		event.registerBlockEntityRenderer(DeferredRegisters.TILE_CHEMICALMIXER.get(), RenderChemicalMixer::new);
		event.registerBlockEntityRenderer(DeferredRegisters.TILE_MULTIMETERBLOCK.get(), RenderMultimeterBlock::new);
		event.registerBlockEntityRenderer(DeferredRegisters.TILE_LATHE.get(), RenderLathe::new);
		event.registerBlockEntityRenderer(DeferredRegisters.TILE_CHARGERLV.get(), RenderChargerGeneric::new);
		event.registerBlockEntityRenderer(DeferredRegisters.TILE_CHARGERMV.get(), RenderChargerGeneric::new);
		event.registerBlockEntityRenderer(DeferredRegisters.TILE_CHARGERHV.get(), RenderChargerGeneric::new);
		event.registerBlockEntityRenderer(DeferredRegisters.TILE_SEISMICRELAY.get(), RenderSeismicRelay::new);
		event.registerBlockEntityRenderer(DeferredRegisters.TILE_COOLANTRESAVOIR.get(), RenderCoolantResavoir::new);
		event.registerBlockEntityRenderer(DeferredRegisters.TILE_TANKHSLA.get(), RenderTankGeneric::new);
		event.registerBlockEntityRenderer(DeferredRegisters.TILE_TANKREINFORCED.get(), RenderTankGeneric::new);
		event.registerBlockEntityRenderer(DeferredRegisters.TILE_TANKSTEEL.get(), RenderTankGeneric::new);
		event.registerBlockEntityRenderer(DeferredRegisters.TILE_MOTORCOMPLEX.get(), RenderMotorComplex::new);
	}

	public static boolean shouldMultilayerRender(RenderType type) {
		return type == RenderType.translucent() || type == RenderType.solid();
	}

}
