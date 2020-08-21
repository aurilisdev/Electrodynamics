package electrodynamics;

import electrodynamics.api.References;
import electrodynamics.client.screen.ScreenBatteryBox;
import electrodynamics.client.screen.ScreenCoalGenerator;
import electrodynamics.client.screen.ScreenElectricFurnace;
import electrodynamics.client.screen.ScreenO2OProcessor;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.block.subtype.SubtypeOre;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;

@Mod(References.ID)
@EventBusSubscriber(modid = References.ID, bus = Bus.MOD)
public class Electrodynamics {

	public Electrodynamics() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		DeferredRegisters.BLOCKS.register(bus);
		DeferredRegisters.ITEMS.register(bus);
		DeferredRegisters.TILES.register(bus);
		DeferredRegisters.CONTAINERS.register(bus);
	}

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public static void onClientSetup(FMLClientSetupEvent event) {
		ScreenManager.registerFactory(DeferredRegisters.CONTAINER_COALGENERATOR.get(), ScreenCoalGenerator::new);
		ScreenManager.registerFactory(DeferredRegisters.CONTAINER_ELECTRICFURNACE.get(), ScreenElectricFurnace::new);
		ScreenManager.registerFactory(DeferredRegisters.CONTAINER_O2OPROCESSOR.get(), ScreenO2OProcessor::new);
		ScreenManager.registerFactory(DeferredRegisters.CONTAINER_BATTERYBOX.get(), ScreenBatteryBox::new);

		RenderTypeLookup.setRenderLayer(DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.coalgeneratorrunning), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(SubtypeMachine.wiremill), RenderType.getCutout());
	}

	@SubscribeEvent
	public static void onLoadEvent(FMLLoadCompleteEvent event) {
		for (Biome biome : ForgeRegistries.BIOMES) {
			if (biome.getCategory() != Biome.Category.NETHER && biome.getCategory() != Biome.Category.THEEND) {
				for (SubtypeOre ore : SubtypeOre.values()) {
					CountRangeConfig range = new CountRangeConfig(ore.veinsPerChunk, ore.minY, 0, ore.maxY);
					OreFeatureConfig feature = new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, DeferredRegisters.SUBTYPEBLOCK_MAPPINGS.get(ore).getDefaultState(), ore.veinSize);
					biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(feature).withPlacement(Placement.COUNT_RANGE.configure(range)));
				}
			}
		}
	}
}
