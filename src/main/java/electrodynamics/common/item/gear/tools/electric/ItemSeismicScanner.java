package electrodynamics.common.item.gear.tools.electric;

import electrodynamics.api.capability.itemhandler.CapabilityItemStackHandler;
import electrodynamics.api.item.IItemElectric;
import electrodynamics.common.inventory.container.item.ContainerSeismicScanner;
import electrodynamics.prefab.item.ElectricItemProperties;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class ItemSeismicScanner extends Item implements IItemElectric {

	private static final Component CONTAINER_TITLE = new TranslatableComponent("container.seismicscanner");
	private final ElectricItemProperties properties;

	public static final int SLOT_COUNT = 1;

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
		if (!world.isClientSide) {
			player.openMenu(getMenuProvider(world, player, player.getItemInHand(hand)));
		}
		return super.use(world, player, hand);
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
		return new CapabilityItemStackHandler(SLOT_COUNT, ItemSeismicScanner.class);
	}

	public MenuProvider getMenuProvider(Level world, Player player, ItemStack stack) {
		return new SimpleMenuProvider((id, inv, play) -> {
			LazyOptional<IItemHandler> capability = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);
			IItemHandler handler = new ItemStackHandler();
			if (capability.isPresent()) {
				handler = capability.resolve().get();
			}
			return new ContainerSeismicScanner(id, player.getInventory(), handler);
		}, CONTAINER_TITLE);
	}

}
