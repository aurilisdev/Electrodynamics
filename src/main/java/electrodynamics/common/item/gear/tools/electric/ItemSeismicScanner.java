package electrodynamics.common.item.gear.tools.electric;

import java.util.List;

import electrodynamics.api.capability.types.itemhandler.CapabilityItemStackHandler;
import electrodynamics.api.item.IItemElectric;
import electrodynamics.common.inventory.container.item.ContainerSeismicScanner;
import electrodynamics.common.packet.NetworkHandler;
import electrodynamics.common.packet.types.client.PacketAddClientRenderInfo;
import electrodynamics.prefab.item.ElectricItemProperties;
import electrodynamics.prefab.item.ItemElectric;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import electrodynamics.prefab.utilities.NBTUtils;
import electrodynamics.prefab.utilities.WorldUtils;
import electrodynamics.prefab.utilities.object.Location;
import electrodynamics.registers.ElectrodynamicsItems;
import electrodynamics.registers.ElectrodynamicsSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkDirection;

public class ItemSeismicScanner extends ItemElectric {

	private static final Component CONTAINER_TITLE = Component.translatable("container.seismicscanner");
	private final ElectricItemProperties properties;

	public static final int SLOT_COUNT = 1;
	public static final int RADUIS_BLOCKS = 16;
	public static final int COOLDOWN_SECONDS = 10;
	public static final int JOULES_PER_SCAN = 1000;

	public static final String PLAY_LOC = "player";
	public static final String BLOCK_LOC = "block";

	public ItemSeismicScanner(ElectricItemProperties properties) {
		super(properties, item -> ElectrodynamicsItems.ITEM_BATTERY.get());
		this.properties = properties;
	}

	@Override
	public ElectricItemProperties getElectricProperties() {
		return properties;
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
		return new CapabilityItemStackHandler(1, stack);
	}

	@Override
	public void appendHoverText(ItemStack stack, Level world, List<Component> tooltips, TooltipFlag flag) {
		super.appendHoverText(stack, world, tooltips, flag);
		tooltips.add(ElectroTextUtils.tooltip("seismicscanner.use"));
		tooltips.add(ElectroTextUtils.tooltip("seismicscanner.opengui").withStyle(ChatFormatting.GRAY));
		boolean onCooldown = stack.hasTag() && stack.getTag().getInt(NBTUtils.TIMER) > 0;
		if (onCooldown) {
			tooltips.add(ElectroTextUtils.tooltip("seismicscanner.oncooldown").withStyle(ChatFormatting.BOLD, ChatFormatting.RED));
		} else {
			tooltips.add(ElectroTextUtils.tooltip("seismicscanner.showuse").withStyle(ChatFormatting.GRAY));
		}

	}

	@Override
	public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
		if (allowedIn(group)) {

			ItemStack empty = new ItemStack(this);
			IItemElectric.setEnergyStored(empty, 0);
			items.add(empty);

			ItemStack charged = new ItemStack(this);
			IItemElectric.setEnergyStored(charged, properties.capacity);
			items.add(charged);
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
		if (!world.isClientSide) {
			ItemStack scanner = player.getItemInHand(hand);
			ItemSeismicScanner seismic = (ItemSeismicScanner) scanner.getItem();
			CompoundTag tag = scanner.getOrCreateTag();
			boolean isTimerUp = tag.getInt(NBTUtils.TIMER) <= 0;
			boolean isPowered = seismic.getJoulesStored(scanner) >= JOULES_PER_SCAN;
			ItemStack ore = scanner.getCapability(ForgeCapabilities.ITEM_HANDLER).map(m -> m.getStackInSlot(0)).orElse(ItemStack.EMPTY);
			if (player.isShiftKeyDown() && isTimerUp && isPowered && !ore.isEmpty()) {
				extractPower(scanner, properties.extract.getJoules(), false);
				tag.putInt(NBTUtils.TIMER, COOLDOWN_SECONDS * 20);
				world.playSound(null, player.blockPosition(), ElectrodynamicsSounds.SOUND_SEISMICSCANNER.get(), SoundSource.PLAYERS, 1, 1);
				if (ore.getItem() instanceof BlockItem oreBlockItem) {
					Location playerPos = new Location(player.getOnPos());
					Location blockPos = new Location(WorldUtils.getClosestBlockToCenter(world, playerPos.toBlockPos(), RADUIS_BLOCKS, oreBlockItem.getBlock()));
					playerPos.writeToNBT(tag, NBTUtils.LOCATION + PLAY_LOC);
					blockPos.writeToNBT(tag, NBTUtils.LOCATION + BLOCK_LOC);
					NetworkHandler.CHANNEL.sendTo(new PacketAddClientRenderInfo(player.getUUID(), blockPos.toBlockPos()), ((ServerPlayer) player).connection.connection, NetworkDirection.PLAY_TO_CLIENT);
				}
			} else {
				player.openMenu(getMenuProvider(world, player, scanner));
			}
		}
		return super.use(world, player, hand);
	}

	public MenuProvider getMenuProvider(Level world, Player player, ItemStack stack) {
		return new SimpleMenuProvider((id, inv, play) -> {
			LazyOptional<IItemHandler> capability = stack.getCapability(ForgeCapabilities.ITEM_HANDLER);
			IItemHandler handler = new ItemStackHandler();
			if (capability.isPresent()) {
				handler = capability.resolve().get();
			}
			return new ContainerSeismicScanner(id, player.getInventory(), handler);
		}, CONTAINER_TITLE);
	}

	@Override
	public void inventoryTick(ItemStack stack, Level world, Entity entity, int itemSlot, boolean isSelected) {
		if (!world.isClientSide) {
			CompoundTag tag = stack.getOrCreateTag();
			int time = tag.getInt(NBTUtils.TIMER);
			if (time > 0) {
				tag.putInt(NBTUtils.TIMER, time - 1);
			}
		}
		super.inventoryTick(stack, world, entity, itemSlot, isSelected);
	}

	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		return slotChanged;
	}

	@Override
	public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack other, Slot slot, ClickAction action, Player player, SlotAccess access) {

		if (!IItemElectric.overrideOtherStackedOnMe(stack, other, slot, action, player, access)) {
			return super.overrideOtherStackedOnMe(stack, other, slot, action, player, access);
		}

		return true;

	}

}
