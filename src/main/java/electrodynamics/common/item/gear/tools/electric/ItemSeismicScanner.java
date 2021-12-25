package electrodynamics.common.item.gear.tools.electric;

import electrodynamics.api.capability.itemhandler.CapabilityItemStackHandler;
import electrodynamics.api.item.IItemElectric;
import electrodynamics.common.inventory.container.item.ContainerSeismicScanner;
import electrodynamics.prefab.item.ElectricItemProperties;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.network.NetworkHooks;

public class ItemSeismicScanner extends Item implements IItemElectric {

	private static final Component CONTAINER_TITLE = new TranslatableComponent("container.seismicscanner");
	private final ElectricItemProperties properties;
	
	public ItemSeismicScanner(ElectricItemProperties properties) {
		super(properties);
		this.properties = properties;
	}

	@Override
	public ElectricItemProperties getElectricProperties() {
		return properties;
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		if(!world.isClientSide) {
			NetworkHooks.openGui((ServerPlayer)player, getMenuProvider(world, player, getDefaultInstance()));
		}
		return super.use(world, player, hand);
	}
	
	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
		return new CapabilityItemStackHandler(ContainerSeismicScanner.SLOT_COUNT, ItemSeismicScanner.class);
	}
	
	public MenuProvider getMenuProvider(Level world, Player player, ItemStack stack) {
		Container container = new SimpleContainer(ContainerSeismicScanner.SLOT_COUNT);
		LazyOptional<IItemHandler> capability = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);
		if(capability.isPresent()) {
			IItemHandler handler = capability.resolve().get();
			container.setItem(0, handler.getStackInSlot(0));
		}
		return new SimpleMenuProvider((id, inv, play) -> {
			return new ContainerSeismicScanner(id, player.getInventory(), container, stack);
		}, CONTAINER_TITLE);
	}

}
