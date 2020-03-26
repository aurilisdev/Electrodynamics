package physica;

import org.apache.logging.log4j.Logger;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import physica.component.ComponentBlocks;
import physica.component.ComponentDictionary;
import physica.component.ComponentItems;
import physica.component.ComponentRecipes;
import physica.network.PacketHandler;
import physica.proxy.CommonProxy;
import physica.world.ComponentWorldGen;

@Mod(modid = References.DOMAIN, name = References.NAME, version = References.VERSION)
public class Physica {
	@SidedProxy(serverSide = "physica.proxy.CommonProxy", clientSide = "physica.proxy.ClientProxy")
	public static CommonProxy proxy;

	public static Logger logger;
	public static PacketHandler packetsystem = new PacketHandler();

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();
		GameRegistry.registerWorldGenerator(new ComponentWorldGen(), 1);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		ComponentDictionary.initOreDictionary();
		ComponentRecipes.init();
	}

	@EventBusSubscriber
	public static class RegistrationHandler {
		@SubscribeEvent
		public static void registerItems(RegistryEvent.Register<Item> event) {
			ComponentItems.register(event.getRegistry());
			ComponentBlocks.registerItemBlocks(event.getRegistry());
		}

		@SubscribeEvent
		public static void registerItems(ModelRegistryEvent event) {
			ComponentItems.registerModels();
			ComponentBlocks.registerModels();
		}

		@SubscribeEvent
		public static void registerBlocks(RegistryEvent.Register<Block> event) {
			ComponentBlocks.register(event.getRegistry());
		}

	}

}
