package electrodynamics.common.item.gear.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import electrodynamics.api.capability.types.fluid.RestrictedFluidHandlerItemStack;
import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.inventory.InventoryTickConsumer;
import electrodynamics.common.item.ItemElectrodynamics;
import electrodynamics.prefab.utilities.CapabilityUtils;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemCanister extends ItemElectrodynamics {

	public static final int MAX_FLUID_CAPACITY = 5000;

	public static final List<InventoryTickConsumer> INVENTORY_TICK_CONSUMERS = new ArrayList<>();

	public ItemCanister(Item.Properties properties, Supplier<CreativeModeTab> creativeTab) {
		super(properties, creativeTab);
	}

	@Override
	public void addCreativeModeItems(CreativeModeTab group, List<ItemStack> items) {

		items.add(new ItemStack(this));
		if (!CapabilityUtils.isFluidItemNull()) {
			for (Fluid liq : ForgeRegistries.FLUIDS.getValues()) {
				if (liq.isSame(Fluids.EMPTY)) {
					continue;
				}
				ItemStack temp = new ItemStack(this);
				temp.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).ifPresent(h -> ((RestrictedFluidHandlerItemStack) h).setFluid(new FluidStack(liq, MAX_FLUID_CAPACITY)));
				items.add(temp);

			}
		}

	}

	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean isSelected) {
		super.inventoryTick(stack, level, entity, slot, isSelected);
		INVENTORY_TICK_CONSUMERS.forEach(consumer -> consumer.apply(stack, level, entity, slot, isSelected));
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
		return new RestrictedFluidHandlerItemStack.SwapEmpty(stack, stack, MAX_FLUID_CAPACITY);
	}

	@Override
	public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		if (!CapabilityUtils.isFluidItemNull()) {
			stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).ifPresent(h -> {
				if (!((RestrictedFluidHandlerItemStack) h).getFluid().isEmpty()) {
					RestrictedFluidHandlerItemStack cap = (RestrictedFluidHandlerItemStack) h;
					tooltip.add(ElectroTextUtils.ratio(ChatFormatter.formatFluidMilibuckets(cap.getFluidInTank(0).getAmount()), ChatFormatter.formatFluidMilibuckets(MAX_FLUID_CAPACITY)).withStyle(ChatFormatting.GRAY));
					tooltip.add(cap.getFluid().getDisplayName().copy().withStyle(ChatFormatting.DARK_GRAY));
				}
			});
		}
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
	}

	@Override
	public int getBarWidth(ItemStack stack) {
		return (int) Math.round(stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).map(h -> {
			RestrictedFluidHandlerItemStack cap = (RestrictedFluidHandlerItemStack) h;
			return 13.0 * cap.getFluidInTank(0).getAmount() / cap.getTankCapacity(0);
		}).orElse(13.0));
	}

	@Override
	public boolean isBarVisible(ItemStack stack) {
		return stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).map(h -> !((RestrictedFluidHandlerItemStack) h).getFluid().isEmpty()).orElse(false);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
		useCanister(worldIn, playerIn, handIn);
		return InteractionResultHolder.pass(playerIn.getItemInHand(handIn));
	}

	public void useCanister(Level world, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		HitResult trace = getPlayerPOVHitResult(world, player, net.minecraft.world.level.ClipContext.Fluid.ANY);
		if (!world.isClientSide && trace.getType() != Type.MISS && trace.getType() != Type.ENTITY) {
			BlockHitResult blockTrace = (BlockHitResult) trace;
			BlockPos pos = blockTrace.getBlockPos();
			BlockState state = world.getBlockState(pos);
			if (state.getFluidState().isSource() && !state.getFluidState().getType().isSame(Fluids.EMPTY)) {
				FluidStack sourceFluid = new FluidStack(state.getFluidState().getType(), 1000);
				int accepted = CapabilityUtils.fillFluidItem(stack, sourceFluid, FluidAction.SIMULATE);
				if (accepted >= 1000) {
					CapabilityUtils.fillFluidItem(stack, sourceFluid, FluidAction.EXECUTE);
					world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
					world.playSound(null, player.blockPosition(), SoundEvents.BUCKET_FILL, SoundSource.PLAYERS, 1, 1);
				}
			}
		}
	}

}
