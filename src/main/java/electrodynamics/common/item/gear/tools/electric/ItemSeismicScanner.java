package electrodynamics.common.item.gear.tools.electric;

import java.util.List;
import java.util.function.Supplier;

import electrodynamics.common.inventory.container.item.ContainerSeismicScanner;
import electrodynamics.common.packet.NetworkHandler;
import electrodynamics.common.packet.types.client.PacketAddClientRenderInfo;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import electrodynamics.registers.ElectrodynamicsSounds;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import voltaic.api.item.CapabilityItemStackHandler;
import voltaic.prefab.inventory.container.types.GenericContainerItem;
import voltaic.prefab.item.ElectricItemProperties;
import voltaic.prefab.item.ItemElectric;
import voltaic.prefab.utilities.CapabilityUtils;
import voltaic.prefab.utilities.NBTUtils;
import voltaic.prefab.utilities.WorldUtils;
import voltaic.prefab.utilities.object.Location;

public class ItemSeismicScanner extends ItemElectric {

	private static final ITextComponent CONTAINER_TITLE = new TranslationTextComponent("container.seismicscanner");

	public static final int SLOT_COUNT = 1;
	public static final int RADUIS_BLOCKS = 16;
	public static final int COOLDOWN_SECONDS = 10;
	public static final int JOULES_PER_SCAN = 1000;

	public static final String PLAY_LOC = "player";
	public static final String BLOCK_LOC = "block";

	public ItemSeismicScanner(ElectricItemProperties properties, Supplier<ItemGroup> creativeTab) {
		super(properties, creativeTab);
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
		return new CapabilityItemStackHandler(1, stack);
	}

	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> tooltips, ITooltipFlag flag) {
		super.appendHoverText(stack, world, tooltips, flag);
		tooltips.add(ElectroTextUtils.tooltip("seismicscanner.use"));
		tooltips.add(ElectroTextUtils.tooltip("seismicscanner.opengui").withStyle(TextFormatting.GRAY));
		boolean onCooldown = stack.hasTag() && stack.getTag().getInt(NBTUtils.TIMER) > 0;
		if (onCooldown) {
			tooltips.add(ElectroTextUtils.tooltip("seismicscanner.oncooldown").withStyle(TextFormatting.BOLD, TextFormatting.RED));
		} else {
			tooltips.add(ElectroTextUtils.tooltip("seismicscanner.showuse").withStyle(TextFormatting.GRAY));
		}

	}

	@Override
	public boolean canBeDepleted() {
		return false;
	}

	@Override
	public ActionResult<ItemStack> use(final World world, PlayerEntity player, Hand hand) {
		if (!world.isClientSide) {
			ItemStack scanner = player.getItemInHand(hand);
			ItemSeismicScanner seismic = (ItemSeismicScanner) scanner.getItem();
			CompoundNBT tag = scanner.getOrCreateTag();
			boolean isTimerUp = tag.getInt(NBTUtils.TIMER) <= 0;
			boolean isPowered = seismic.getJoulesStored(scanner) >= JOULES_PER_SCAN;
			ItemStack ore = scanner.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).map(m -> m.getStackInSlot(0)).orElse(ItemStack.EMPTY);
			if (player.isShiftKeyDown() && isTimerUp && isPowered && !ore.isEmpty()) {
				extractPower(scanner, getElectricProperties().extract.getJoules(), false);
				tag.putInt(NBTUtils.TIMER, COOLDOWN_SECONDS * 20);
				world.playSound(null, player.blockPosition(), ElectrodynamicsSounds.SOUND_SEISMICSCANNER.get(), SoundCategory.PLAYERS, 1, 1);
				if (ore.getItem() instanceof BlockItem) {
					BlockItem oreBlockItem = (BlockItem) ore.getItem();
					Location playerPos = new Location(player.blockPosition());
					Location blockPos = new Location(WorldUtils.getClosestBlockToCenter(world, playerPos.toBlockPos(), RADUIS_BLOCKS, oreBlockItem.getBlock()));
					playerPos.writeToNBT(tag, NBTUtils.LOCATION + PLAY_LOC);
					blockPos.writeToNBT(tag, NBTUtils.LOCATION + BLOCK_LOC);
					NetworkHandler.CHANNEL.sendTo(new PacketAddClientRenderInfo(player.getUUID(), blockPos.toBlockPos()), ((ServerPlayerEntity) player).connection.connection, NetworkDirection.PLAY_TO_CLIENT);
				}
			} else {
				player.openMenu(getMenuProvider(world, player, scanner, hand));
			}
		}
		return super.use(world, player, hand);
	}

	public INamedContainerProvider getMenuProvider(World world, PlayerEntity player, ItemStack stack, Hand hand) {
		return new SimpleNamedContainerProvider((id, inv, play) -> {
			IItemHandler capability = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(CapabilityUtils.EMPTY_ITEM_HANDLER);
			CapabilityItemStackHandler handler = new CapabilityItemStackHandler(SLOT_COUNT, stack);
			if (capability != CapabilityUtils.EMPTY_ITEM_HANDLER) {
				handler = (CapabilityItemStackHandler) capability;
			}
			return new ContainerSeismicScanner(id, player.inventory, handler, GenericContainerItem.makeData(hand));
		}, CONTAINER_TITLE);
	}

	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
		if (!world.isClientSide) {
			CompoundNBT tag = stack.getOrCreateTag();
			int time = tag.getInt(NBTUtils.TIMER);
			if (time > 0) {
				tag.putInt(NBTUtils.TIMER, time - 1);
			}
		}
		super.inventoryTick(stack, world, entity, itemSlot, isSelected);
	}

	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		return oldStack.getItem() != newStack.getItem();
	}

}
