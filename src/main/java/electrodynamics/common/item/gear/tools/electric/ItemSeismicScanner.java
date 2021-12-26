package electrodynamics.common.item.gear.tools.electric;

import java.util.List;

import electrodynamics.Electrodynamics;
import electrodynamics.SoundRegister;
import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.api.capability.intstorage.CapabilityIntStorage;
import electrodynamics.api.capability.itemhandler.CapabilityItemStackHandler;
import electrodynamics.api.capability.multicapability.seismicscanner.SeismicScannerCapability;
import electrodynamics.api.item.IItemElectric;
import electrodynamics.common.inventory.container.item.ContainerSeismicScanner;
import electrodynamics.prefab.item.ElectricItemProperties;
import electrodynamics.prefab.utilities.WorldUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
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
	public static final int RADUIS_BLOCKS = 16;
	public static final int COOLDOWN_SECONDS = 10;

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
			if (m.getInt() > 0) {
				return true;
			}
			return false;
		}).orElse(false);
		if (onCooldown) {
			tooltips.add(new TranslatableComponent("tooltip.seismicscanner.oncooldown").withStyle(ChatFormatting.BOLD, ChatFormatting.RED));
		} else {
			tooltips.add(new TranslatableComponent("tooltip.seismicscanner.showuse").withStyle(ChatFormatting.GRAY));
		}
		stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
			ItemStack invStack = h.getStackInSlot(0);
			Component component;
			if (invStack.isEmpty()) {
				component = new TranslatableComponent("tooltip.seismicscanner.empty");
			} else {
				component = invStack.getDisplayName();
			}
			tooltips.add(new TranslatableComponent("tooltip.seismicscanner.currentore", component));
		});
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		if (!world.isClientSide) {
			ItemStack scanner = player.getItemInHand(hand);
			boolean isTimerUp = scanner.getCapability(ElectrodynamicsCapabilities.INTEGER_STORAGE_CAPABILITY).map(m -> {
				if (m.getInt() <= 0) {
					return true;
				}
				return false;
			}).orElse(false);
			if (player.isShiftKeyDown() && isTimerUp) {
				scanner.getCapability(ElectrodynamicsCapabilities.INTEGER_STORAGE_CAPABILITY).ifPresent(h -> h.setInt(COOLDOWN_SECONDS * 20));
				world.playSound(null, player.blockPosition(), SoundRegister.SOUND_SEISMICSCANNER.get(), SoundSource.PLAYERS, 1, 1);
				ItemStack ore = scanner.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).map(m -> m.getStackInSlot(0))
						.orElse(ItemStack.EMPTY);
				if (ore.getItem() instanceof BlockItem oreBlockItem) {
					BlockPos pos = WorldUtils.getClosestBlockToCenter(world, player.getOnPos(), RADUIS_BLOCKS, oreBlockItem.getBlock());
					Electrodynamics.LOGGER.info(pos.toString());
				}
			} else {
				player.openMenu(getMenuProvider(world, player, scanner));
			}
		}
		return super.use(world, player, hand);
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
		return new SeismicScannerCapability(new CapabilityIntStorage(), new CapabilityItemStackHandler(SLOT_COUNT, ItemSeismicScanner.class));
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
			if (h.getInt() > 0) {
				h.setInt(h.getInt() - 1);
			}
		});
		super.inventoryTick(stack, world, entity, itemSlot, isSelected);
	}

}
