package electrodynamics.common.item.gear.tools.electric;

import java.util.List;

import electrodynamics.SoundRegister;
import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.api.capability.multicapability.SeismicScannerCapability;
import electrodynamics.api.capability.types.intstorage.CapabilityIntStorage;
import electrodynamics.api.capability.types.itemhandler.CapabilityItemStackHandler;
import electrodynamics.api.item.IItemElectric;
import electrodynamics.common.inventory.container.item.ContainerSeismicScanner;
import electrodynamics.common.packet.NetworkHandler;
import electrodynamics.common.packet.types.PacketAddClientRenderInfo;
import electrodynamics.prefab.item.ElectricItemProperties;
import electrodynamics.prefab.item.ItemElectric;
import electrodynamics.prefab.utilities.WorldUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkDirection;

public class ItemSeismicScanner extends ItemElectric {

	private static final Component CONTAINER_TITLE = new TranslatableComponent("container.seismicscanner");
	private final ElectricItemProperties properties;

	public static final int SLOT_COUNT = 1;
	public static final int RADUIS_BLOCKS = 16;
	public static final int COOLDOWN_SECONDS = 10;
	public static final int JOULES_PER_SCAN = 1000;

	public ItemSeismicScanner(ElectricItemProperties properties) {
		super(properties);
		this.properties = properties;
	}

	@Override
	public ElectricItemProperties getElectricProperties() {
		return properties;
	}

	@Override
	public void appendHoverText(ItemStack stack, Level world, List<Component> tooltips, TooltipFlag flag) {
		super.appendHoverText(stack, world, tooltips, flag);
		tooltips.add(new TranslatableComponent("tooltip.seismicscanner.use"));
		tooltips.add(new TranslatableComponent("tooltip.seismicscanner.opengui").withStyle(ChatFormatting.GRAY));
		boolean onCooldown = stack.getCapability(ElectrodynamicsCapabilities.INTEGER_STORAGE_CAPABILITY).map(m -> {
			if (m.getInt(0) > 0) {
				return true;
			}
			return false;
		}).orElse(false);
		if (onCooldown) {
			tooltips.add(new TranslatableComponent("tooltip.seismicscanner.oncooldown").withStyle(ChatFormatting.BOLD, ChatFormatting.RED));
		} else {
			tooltips.add(new TranslatableComponent("tooltip.seismicscanner.showuse").withStyle(ChatFormatting.GRAY));
		}

	}

	@Override
	public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
		if (allowdedIn(group)) {
			ItemStack charged = new ItemStack(this);
			IItemElectric.setEnergyStored(charged, properties.capacity);
			items.add(charged);

			ItemStack empty = new ItemStack(this);
			IItemElectric.setEnergyStored(empty, 0);
			items.add(empty);
		}
	}

	@Override
	public boolean canBeDepleted() {
		return false;
	}

	@Override
	public int getBarWidth(ItemStack stack) {
		return (int) Math.round(13.0f * getJoulesStored(stack) / properties.capacity);
	}

	@Override
	public boolean isBarVisible(ItemStack stack) {
		return getJoulesStored(stack) < properties.capacity;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(final Level world, Player player, InteractionHand hand) {
		if(!world.isClientSide) {
			ItemStack scanner = player.getItemInHand(hand);
			ItemSeismicScanner seismic = (ItemSeismicScanner) scanner.getItem();
			boolean isTimerUp = scanner.getCapability(ElectrodynamicsCapabilities.INTEGER_STORAGE_CAPABILITY).map(m -> {
				if (m.getInt(0) <= 0) {
					return true;
				}
				return false;
			}).orElse(false);
			boolean isPowered = seismic.getJoulesStored(scanner) >= JOULES_PER_SCAN;
			ItemStack ore = scanner.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).map(m -> m.getStackInSlot(0)).orElse(ItemStack.EMPTY);
			if (player.isShiftKeyDown() && isTimerUp && isPowered && !ore.isEmpty()) {
				extractPower(scanner, properties.extract.getJoules(), false);
				scanner.getCapability(ElectrodynamicsCapabilities.INTEGER_STORAGE_CAPABILITY).ifPresent(h -> h.setInt(0, COOLDOWN_SECONDS * 20));
				world.playSound(null, player.blockPosition(), SoundRegister.SOUND_SEISMICSCANNER.get(), SoundSource.PLAYERS, 1, 1);
				if (ore.getItem() instanceof BlockItem oreBlockItem) {
					BlockPos playerPos = player.getOnPos();
					BlockPos blockPos = WorldUtils.getClosestBlockToCenter(world, playerPos, RADUIS_BLOCKS, oreBlockItem.getBlock());
					CompoundTag scanLoc = scanner.getOrCreateTagElement("scanloc");
					CompoundTag onLoc = scanner.getOrCreateTagElement("onloc");
					writePosToTag(scanLoc, blockPos);
					writePosToTag(onLoc, playerPos);
					NetworkHandler.CHANNEL.sendTo(new PacketAddClientRenderInfo(player.getUUID(), blockPos), ((ServerPlayer)player).connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);
				}
			} else {
				player.openMenu(getMenuProvider(world, player, scanner));
			}
		}
		return super.use(world, player, hand);
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
		return new SeismicScannerCapability(new CapabilityIntStorage(1), new CapabilityItemStackHandler(SLOT_COUNT, ItemSeismicScanner.class));
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

	@Override
	public void inventoryTick(ItemStack stack, Level world, Entity entity, int itemSlot, boolean isSelected) {
		stack.getCapability(ElectrodynamicsCapabilities.INTEGER_STORAGE_CAPABILITY).ifPresent(h -> {
			if (h.getInt(0) > 0) {
				h.setInt(0, h.getInt(0) - 1);
			}
		});
		super.inventoryTick(stack, world, entity, itemSlot, isSelected);
	}

	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		return slotChanged;
	}
	
	private void writePosToTag(CompoundTag tag, BlockPos pos) {
		tag.remove("x");
		tag.remove("y");
		tag.remove("z");
		tag.putInt("x", pos.getX());
		tag.putInt("y", pos.getY());
		tag.putInt("z", pos.getZ());
	}

}
