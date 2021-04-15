package electrodynamics.client;

import javax.annotation.Nullable;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.References;
import electrodynamics.client.render.tile.RenderAdvancedSolarPanel;
import electrodynamics.client.render.tile.RenderBatteryBox;
import electrodynamics.client.render.tile.RenderChemicalMixer;
import electrodynamics.client.render.tile.RenderCombustionChamber;
import electrodynamics.client.render.tile.RenderFermentationPlant;
import electrodynamics.client.render.tile.RenderHydroelectricGenerator;
import electrodynamics.client.render.tile.RenderMineralCrusher;
import electrodynamics.client.render.tile.RenderMineralGrinder;
import electrodynamics.client.render.tile.RenderMineralWasher;
import electrodynamics.client.render.tile.RenderTeleporter;
import electrodynamics.client.render.tile.RenderWindmill;
import electrodynamics.client.screen.ScreenBatteryBox;
import electrodynamics.client.screen.ScreenChemicalCrystallizer;
import electrodynamics.client.screen.ScreenChemicalMixer;
import electrodynamics.client.screen.ScreenCoalGenerator;
import electrodynamics.client.screen.ScreenDO2OProcessor;
import electrodynamics.client.screen.ScreenElectricFurnace;
import electrodynamics.client.screen.ScreenFermentationPlant;
import electrodynamics.client.screen.ScreenMineralWasher;
import electrodynamics.client.screen.ScreenO2OProcessor;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.tile.network.TileWire;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
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
	ModelLoader.addSpecialModel(MODEL_HYDROELECTRICGENERATORBLADES);
	ModelLoader.addSpecialModel(MODEL_WINDMILLBLADES);
	ModelLoader.addSpecialModel(MODEL_MINERALCRUSHERBASE);
	ModelLoader.addSpecialModel(MODEL_MINERALCRUSHERHANDLE);
	ModelLoader.addSpecialModel(MODEL_MINERALGRINDERBASE);
	ModelLoader.addSpecialModel(MODEL_MINERALGRINDERWHEEL);
	ModelLoader.addSpecialModel(MODEL_FERMENTATIONPLANTWATER);
	ModelLoader.addSpecialModel(MODEL_FERMENTATIONPLANTETHANOL);
	ModelLoader.addSpecialModel(MODEL_COMBUSTIONCHAMBERETHANOL);
	ModelLoader.addSpecialModel(MODEL_CHEMICALMIXERBASE);
	ModelLoader.addSpecialModel(MODEL_CHEMICALMIXERBLADES);
	ModelLoader.addSpecialModel(MODEL_CHEMICALMIXERWATER);
	ModelLoader.addSpecialModel(MODEL_TELEPORTERON);
	ModelLoader.addSpecialModel(MODEL_TELEPORTER);
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
    public static final ResourceLocation MODEL_HYDROELECTRICGENERATORBLADES = new ResourceLocation(
	    References.ID + ":block/hydroelectricgeneratorblades");
    public static final ResourceLocation MODEL_WINDMILLBLADES = new ResourceLocation(References.ID + ":block/windmillblades");
    public static final ResourceLocation MODEL_MINERALCRUSHERBASE = new ResourceLocation(References.ID + ":block/mineralcrusherbase");
    public static final ResourceLocation MODEL_MINERALCRUSHERHANDLE = new ResourceLocation(References.ID + ":block/mineralcrusherhandle");
    public static final ResourceLocation MODEL_MINERALGRINDERBASE = new ResourceLocation(References.ID + ":block/mineralgrinderbase");
    public static final ResourceLocation MODEL_MINERALGRINDERWHEEL = new ResourceLocation(References.ID + ":block/mineralgrinderwheel");
    public static final ResourceLocation MODEL_FERMENTATIONPLANTWATER = new ResourceLocation(References.ID + ":block/fermentationplantwater");
    public static final ResourceLocation MODEL_FERMENTATIONPLANTETHANOL = new ResourceLocation(References.ID + ":block/fermentationplantethanol");
    public static final ResourceLocation MODEL_COMBUSTIONCHAMBERETHANOL = new ResourceLocation(References.ID + ":block/combustionchamberethanol");
    public static final ResourceLocation MODEL_CHEMICALMIXERBASE = new ResourceLocation(References.ID + ":block/chemicalmixerbase");
    public static final ResourceLocation MODEL_CHEMICALMIXERBLADES = new ResourceLocation(References.ID + ":block/chemicalmixerblades");
    public static final ResourceLocation MODEL_CHEMICALMIXERWATER = new ResourceLocation(References.ID + ":block/chemicalmixerwater");
    public static final ResourceLocation MODEL_TELEPORTERON = new ResourceLocation(References.ID + ":block/teleporteron");
    public static final ResourceLocation MODEL_TELEPORTER = new ResourceLocation(References.ID + ":block/teleporter");

    public static void setup() {
	ClientRegistry.bindTileEntityRenderer(DeferredRegisters.TILE_ADVANCEDSOLARPANEL.get(), RenderAdvancedSolarPanel::new);
	ClientRegistry.bindTileEntityRenderer(DeferredRegisters.TILE_BATTERYBOX.get(), RenderBatteryBox::new);
	ClientRegistry.bindTileEntityRenderer(DeferredRegisters.TILE_HYDROELECTRICGENERATOR.get(), RenderHydroelectricGenerator::new);
	ClientRegistry.bindTileEntityRenderer(DeferredRegisters.TILE_WINDMILL.get(), RenderWindmill::new);
	ClientRegistry.bindTileEntityRenderer(DeferredRegisters.TILE_MINERALCRUSHER.get(), RenderMineralCrusher::new);
	ClientRegistry.bindTileEntityRenderer(DeferredRegisters.TILE_MINERALGRINDER.get(), RenderMineralGrinder::new);
	ClientRegistry.bindTileEntityRenderer(DeferredRegisters.TILE_FERMENTATIONPLANT.get(), RenderFermentationPlant::new);
	ClientRegistry.bindTileEntityRenderer(DeferredRegisters.TILE_COMBUSTIONCHAMBER.get(), RenderCombustionChamber::new);
	ClientRegistry.bindTileEntityRenderer(DeferredRegisters.TILE_MINERALWASHER.get(), RenderMineralWasher::new);
	ClientRegistry.bindTileEntityRenderer(DeferredRegisters.TILE_CHEMICALMIXER.get(), RenderChemicalMixer::new);
	ClientRegistry.bindTileEntityRenderer(DeferredRegisters.TILE_TELEPORTER.get(), RenderTeleporter::new);

	ScreenManager.registerFactory(DeferredRegisters.CONTAINER_COALGENERATOR.get(), ScreenCoalGenerator::new);
	ScreenManager.registerFactory(DeferredRegisters.CONTAINER_ELECTRICFURNACE.get(), ScreenElectricFurnace::new);
	ScreenManager.registerFactory(DeferredRegisters.CONTAINER_O2OPROCESSOR.get(), ScreenO2OProcessor::new);
	ScreenManager.registerFactory(DeferredRegisters.CONTAINER_DO2OPROCESSOR.get(), ScreenDO2OProcessor::new);
	ScreenManager.registerFactory(DeferredRegisters.CONTAINER_BATTERYBOX.get(), ScreenBatteryBox::new);
	ScreenManager.registerFactory(DeferredRegisters.CONTAINER_FERMENTATIONPLANT.get(), ScreenFermentationPlant::new);
	ScreenManager.registerFactory(DeferredRegisters.CONTAINER_MINERALWASHER.get(), ScreenMineralWasher::new);
	ScreenManager.registerFactory(DeferredRegisters.CONTAINER_CHEMICALMIXER.get(), ScreenChemicalMixer::new);
	ScreenManager.registerFactory(DeferredRegisters.CONTAINER_CHEMICALCRYSTALLIZER.get(), ScreenChemicalCrystallizer::new);

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
	ItemModelsProperties.registerProperty(DeferredRegisters.ITEM_MULTIMETER.get(), new ResourceLocation("number"), new IItemPropertyGetter() {
	    private double num = 0.1;
	    private long lastCheck = 0;

	    @Override
	    public float call(ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity entity) {
		boolean goesUp = false;
		if (entity instanceof PlayerEntity) {
		    RayTraceResult res = Minecraft.getInstance().objectMouseOver;
		    if (res.getType() == Type.BLOCK) {
			BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult) res;
			TileEntity tile = entity.world.getTileEntity(blockraytraceresult.getPos());
			if (tile instanceof TileWire) {
			    TileWire wire = (TileWire) tile;
			    if (wire.transmit > 0) {
				goesUp = true;
			    }
			}
		    }
		}
		if (world != null && lastCheck != world.getGameTime()) {
		    lastCheck = world.getGameTime();
		    num = (float) Math.min(0.9, Math.max(0.1, num + (goesUp ? 0.1 : -0.1)));
		}
		return (float) num;
	    }
	});
    }

    public static boolean shouldMultilayerRender(RenderType type) {
	return type == RenderType.getTranslucent() || type == RenderType.getSolid();
    }
}
