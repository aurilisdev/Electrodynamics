package electrodynamics.common.item;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.plexus.util.StringUtils;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.api.capability.multicapability.EjectorCapability;
import electrodynamics.api.capability.types.boolstorage.CapabilityBooleanStorage;
import electrodynamics.api.capability.types.dirstorage.CapabilityDirectionalStorage;
import electrodynamics.api.capability.types.dirstorage.IDirectionalStorage;
import electrodynamics.api.capability.types.intstorage.CapabilityIntStorage;
import electrodynamics.common.item.subtype.SubtypeItemUpgrade;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class ItemUpgrade extends Item {
	public final SubtypeItemUpgrade subtype;

	public ItemUpgrade(Properties properties, SubtypeItemUpgrade subtype) {
		super(properties.stacksTo(subtype.maxSize));
		this.subtype = subtype;
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
		SubtypeItemUpgrade type = ((ItemUpgrade) stack.getItem()).subtype;
		if (type == SubtypeItemUpgrade.itemoutput || type == SubtypeItemUpgrade.iteminput) {
			return new EjectorCapability(new CapabilityBooleanStorage(), new CapabilityIntStorage(1), new CapabilityDirectionalStorage());
		}
		return super.initCapabilities(stack, nbt);
	}

	@Override
	public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		if (subtype == SubtypeItemUpgrade.advancedcapacity || subtype == SubtypeItemUpgrade.basiccapacity) {
			double capacityMultiplier = subtype == SubtypeItemUpgrade.advancedcapacity ? 2.25 : 1.5;
			tooltip.add(new TranslatableComponent("tooltip.info.capacityupgrade", capacityMultiplier).withStyle(ChatFormatting.GRAY));
			tooltip.add(new TranslatableComponent("tooltip.info.capacityupgradevoltage", (capacityMultiplier == 2.25 ? 4 : 2) + "x")
					.withStyle(ChatFormatting.RED));
		}
		if (subtype == SubtypeItemUpgrade.advancedspeed || subtype == SubtypeItemUpgrade.basicspeed) {
			double speedMultiplier = subtype == SubtypeItemUpgrade.advancedspeed ? 2.25 : 1.5;
			tooltip.add(new TranslatableComponent("tooltip.info.speedupgrade", speedMultiplier).withStyle(ChatFormatting.GRAY));
		}
		if (subtype == SubtypeItemUpgrade.itemoutput || subtype == SubtypeItemUpgrade.iteminput) {

			if (subtype == SubtypeItemUpgrade.itemoutput) {
				tooltip.add(new TranslatableComponent("tooltip.info.itemoutputupgrade").withStyle(ChatFormatting.GRAY));
			} else {
				tooltip.add(new TranslatableComponent("tooltip.info.iteminputupgrade").withStyle(ChatFormatting.GRAY));
			}
			if (stack.getCapability(ElectrodynamicsCapabilities.BOOLEAN_STORAGE_CAPABILITY).map(m -> m.getBoolean(0)).orElse(false)) {
				tooltip.add(new TranslatableComponent("tooltip.info.insmartmode").withStyle(ChatFormatting.LIGHT_PURPLE));
			}
			List<Direction> dirs = stack.getCapability(ElectrodynamicsCapabilities.DIR_STORAGE_CAPABILITY).map(IDirectionalStorage::getDirections)
					.orElse(new ArrayList<>());
			if (!dirs.isEmpty()) {
				tooltip.add(new TranslatableComponent("tooltip.info.dirlist").withStyle(ChatFormatting.BLUE));
				for (int i = 0; i < dirs.size(); i++) {
					Direction dir = dirs.get(i);
					tooltip.add(new TextComponent(i + 1 + ". " + StringUtils.capitalise(dir.getName())).withStyle(ChatFormatting.BLUE));
				}
				tooltip.add(new TranslatableComponent("tooltip.info.cleardirs").withStyle(ChatFormatting.GRAY));
			} else {
				tooltip.add(new TranslatableComponent("tooltip.info.nodirs").withStyle(ChatFormatting.GRAY));
			}
			tooltip.add(new TranslatableComponent("tooltip.info.togglesmart").withStyle(ChatFormatting.GRAY));
		}
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		if (!world.isClientSide) {
			if (ElectrodynamicsCapabilities.DIR_STORAGE_CAPABILITY != null) {
				ItemStack handStack = player.getItemInHand(hand);
				if (player.isShiftKeyDown()) {
					Vec3 look = player.getLookAngle();
					Direction lookingDir = Direction.getNearest(look.x, look.y, look.z);
					handStack.getCapability(ElectrodynamicsCapabilities.DIR_STORAGE_CAPABILITY).ifPresent(k -> k.addDirection(lookingDir));
					return InteractionResultHolder.pass(player.getItemInHand(hand));
				}
				handStack.getCapability(ElectrodynamicsCapabilities.BOOLEAN_STORAGE_CAPABILITY).ifPresent(m -> m.setBoolean(0, !m.getBoolean(0)));
			}
		}
		return super.use(world, player, hand);
	}

	@Override
	public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
		if (!entity.level.isClientSide && entity.isShiftKeyDown()) {
			if (!entity.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()) {
				entity.getItemInHand(InteractionHand.MAIN_HAND).getCapability(ElectrodynamicsCapabilities.DIR_STORAGE_CAPABILITY)
						.ifPresent(IDirectionalStorage::removeAllDirs);
			} else if (!entity.getItemInHand(InteractionHand.OFF_HAND).isEmpty()) {
				entity.getItemInHand(InteractionHand.OFF_HAND).getCapability(ElectrodynamicsCapabilities.DIR_STORAGE_CAPABILITY)
						.ifPresent(IDirectionalStorage::removeAllDirs);
			}
		}
		return super.onEntitySwing(stack, entity);
	}

	@Override
	public boolean isFoil(ItemStack stack) {
		return stack.getCapability(ElectrodynamicsCapabilities.BOOLEAN_STORAGE_CAPABILITY).map(m -> m.getBoolean(0)).orElse(false);
	}
}
