package electrodynamics.common.item.gear.tools;

import electrodynamics.api.References;
import electrodynamics.api.capability.types.itemhandler.CapabilityItemStackHandler;
import electrodynamics.common.inventory.container.item.ContainerPortableChest;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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

public class ItemPortableChest extends Item {

	private static final Component CONTAINER_TITLE = new TranslatableComponent("container.portablechest");
	
	public static final int SLOT_COUNT = 27;
	
	public ItemPortableChest() {
		super(new Item.Properties().tab(References.CORETAB).stacksTo(1));
	}
	
	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
		return new CapabilityItemStackHandler(SLOT_COUNT);
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		if(!world.isClientSide) {
			ItemStack chest = player.getItemInHand(hand);
			world.playSound(null, player.blockPosition(), SoundEvents.CHEST_OPEN, SoundSource.PLAYERS, 1, 1);
			player.openMenu(getMenuProvider(world, player, chest));
		}
		return super.use(world, player, hand);
	}
	
	public MenuProvider getMenuProvider(Level world, Player player, ItemStack stack) {
		return new SimpleMenuProvider((id, inv, play) -> {
			LazyOptional<IItemHandler> capability = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);
			IItemHandler handler = new ItemStackHandler();
			if (capability.isPresent()) {
				handler = capability.resolve().get();
			}
			return new ContainerPortableChest(id, player.getInventory(), handler);
		}, CONTAINER_TITLE);
	}
	
	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		return slotChanged;
	}
	
	@Override
	public boolean isBarVisible(ItemStack stack) {
		return true;
	}
	
	@Override
	public int getBarWidth(ItemStack stack) {
		return stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).map(m -> {
			int filled = 0;
			for(int i = 0; i < m.getSlots(); i++) {
				ItemStack slotItem = m.getStackInSlot(i);
				if(!slotItem.isEmpty()) {
					filled++;
				}
			}
			return (int) ((double) filled / (double) m.getSlots() * 13.0);
		}).orElse(0);
	}

}
