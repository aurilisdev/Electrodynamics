package electrodynamics.common.item.gear.armor.types;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.mojang.datafixers.util.Pair;

import electrodynamics.DeferredRegisters;
import electrodynamics.SoundRegister;
import electrodynamics.api.References;
import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.api.capability.multicapability.JetpackCapability;
import electrodynamics.api.capability.types.boolstorage.CapabilityBooleanStorage;
import electrodynamics.api.capability.types.intstorage.CapabilityIntStorage;
import electrodynamics.api.fluid.RestrictedFluidHandlerItemStack;
import electrodynamics.api.item.ItemUtils;
import electrodynamics.client.ClientRegister;
import electrodynamics.client.KeyBinds;
import electrodynamics.client.render.model.armor.types.ModelJetpack;
import electrodynamics.common.item.gear.armor.ICustomArmor;
import electrodynamics.common.packet.NetworkHandler;
import electrodynamics.common.packet.types.PacketJetpackFlightServer;
import electrodynamics.common.tags.ElectrodynamicsTags;
import electrodynamics.prefab.utilities.CapabilityUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.IItemRenderProperties;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

public class ItemJetpack extends ArmorItem {

	public static final Fluid EMPTY_FLUID = Fluids.EMPTY;
	public static final int MAX_CAPACITY = 30000;

	public static final int USAGE_PER_TICK = 1;
	public static final double VERT_SPEED_INCREASE = 0.5;
	public static final double TERMINAL_VELOCITY = 1;

	private static final String ARMOR_TEXTURE_LOCATION = References.ID + ":textures/model/armor/jetpack.png";

	public ItemJetpack() {
		super(Jetpack.JETPACK, EquipmentSlot.CHEST, new Item.Properties().tab(References.CORETAB).stacksTo(1));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void initializeClient(Consumer<IItemRenderProperties> consumer) {
		consumer.accept(new IItemRenderProperties() {
			@Override
			public <A extends HumanoidModel<?>> A getArmorModel(LivingEntity entity, ItemStack stack, EquipmentSlot slot, A properties) {

				ModelJetpack<LivingEntity> model = new ModelJetpack<>(ClientRegister.JETPACK.bakeRoot());

				model.crouching = properties.crouching;
				model.riding = properties.riding;
				model.young = properties.young;

				return (A) model;
			}
		});
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
		CapabilityIntStorage number = new CapabilityIntStorage(2);
		number.setInt(0, 0);
		CapabilityBooleanStorage bool = new CapabilityBooleanStorage(2);
		bool.setBoolean(0, false);
		return new JetpackCapability(new RestrictedFluidHandlerItemStack(stack, stack, MAX_CAPACITY, getWhitelistedFluids()), number, bool);
	}

	@Override
	public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> items) {
		if (allowdedIn(tab)) {
			items.add(new ItemStack(this));
			if (!CapabilityUtils.isFluidItemNull()) {
				ItemStack full = new ItemStack(this);
				Fluid fluid = getWhitelistedFluids().getSecond().get(0);
				full.getCapability(CapabilityUtils.getFluidItemCap())
						.ifPresent(h -> ((RestrictedFluidHandlerItemStack) h).fillInit(new FluidStack(fluid, MAX_CAPACITY)));
				full.getCapability(CapabilityUtils.getFluidItemCap()).ifPresent(h -> ((RestrictedFluidHandlerItemStack) h).hasInitHappened(true));
				items.add(full);

			}
		}
	}

	@Override
	public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flagIn) {
		if (!CapabilityUtils.isFluidItemNull()) {
			stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent(h -> {
				tooltip.add(new TextComponent(h.getFluidInTank(0).getAmount() + " / " + MAX_CAPACITY + " mB").withStyle(ChatFormatting.GRAY));
			});
		}
		// TODO this breaks on the server for some reason
		stack.getCapability(ElectrodynamicsCapabilities.INTEGER_STORAGE_CAPABILITY).ifPresent(h -> {
			int mode = h.getInt(0);
			Component modeTip = switch (mode) {
			case 0 -> new TranslatableComponent("tooltip.jetpack.mode").withStyle(ChatFormatting.GRAY)
					.append(new TranslatableComponent("tooltip.jetpack.moderegular").withStyle(ChatFormatting.GREEN));
			case 1 -> new TranslatableComponent("tooltip.jetpack.mode").withStyle(ChatFormatting.GRAY)
					.append(new TranslatableComponent("tooltip.jetpack.modehover").withStyle(ChatFormatting.AQUA));
			case 2 -> new TranslatableComponent("tooltip.jetpack.mode").withStyle(ChatFormatting.GRAY)
					.append(new TranslatableComponent("tooltip.jetpack.modeoff").withStyle(ChatFormatting.RED));
			default -> new TextComponent("");
			};

			tooltip.add(modeTip);
		});

		super.appendHoverText(stack, world, tooltip, flagIn);
	}

	@Override
	public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean isSelected) {
		super.inventoryTick(stack, world, entity, slot, isSelected);
		stack.getCapability(ElectrodynamicsCapabilities.INTEGER_STORAGE_CAPABILITY).ifPresent(h -> h.setInt(1, h.getInt(1) + 1));
		if (entity instanceof Player player) {
			if (world.isClientSide) {
				boolean isDown = KeyBinds.jetpackAscend.isDown();
				// has to be send every tick. 1 Hour of debugging confirms this
				if (isDown) {
					NetworkHandler.CHANNEL.sendToServer(new PacketJetpackFlightServer(player.getUUID(), true));
				} else {
					NetworkHandler.CHANNEL.sendToServer(new PacketJetpackFlightServer(player.getUUID(), false));
				}

				boolean isRunning = stack.getCapability(ElectrodynamicsCapabilities.BOOLEAN_STORAGE_CAPABILITY).map(m -> m.getBoolean(1))
						.orElse(false);
				if (isRunning) {
					// TODO doesnt work on server
					renderParticles(world, entity);
				}

			} else // slot check catches ~99% of issues; still bugs if on hot bar slot #2
			if (slot == 2 && ItemUtils.testItems(player.getItemBySlot(EquipmentSlot.CHEST).getItem(), stack.getItem())) {
				int mode = stack.getCapability(ElectrodynamicsCapabilities.INTEGER_STORAGE_CAPABILITY).map(m -> m.getInt(0)).orElse(2);
				boolean enoughFuel = stack.getCapability(CapabilityUtils.getFluidItemCap())
						.map(m -> m.getFluidInTank(0).getAmount() >= ItemJetpack.USAGE_PER_TICK).orElse(false);
				boolean isDown = stack.getCapability(ElectrodynamicsCapabilities.BOOLEAN_STORAGE_CAPABILITY).map(m -> m.getBoolean(0)).orElse(false);
				int ticks = stack.getCapability(ElectrodynamicsCapabilities.INTEGER_STORAGE_CAPABILITY).map(m -> m.getInt(1)).orElse(1);
				if (enoughFuel) {
					if (mode == 0 && isDown) {
						handleSound(ticks, player);
						ascendWithJetpack(ItemJetpack.VERT_SPEED_INCREASE, ItemJetpack.TERMINAL_VELOCITY, player);
						handleRunning(world, entity, stack);
					} else if (mode == 1 && isDown) {
						handleSound(ticks, player);
						ascendWithJetpack(ItemJetpack.VERT_SPEED_INCREASE / 2, ItemJetpack.TERMINAL_VELOCITY / 2, player);
						handleRunning(world, entity, stack);
					} else if (mode == 1 && player.fallDistance > 0) {
						handleSound(ticks, player);
						hoverWithJetpack(player);
						handleRunning(world, entity, stack);
					} else {
						stack.getCapability(ElectrodynamicsCapabilities.BOOLEAN_STORAGE_CAPABILITY).ifPresent(h -> h.setBoolean(1, false));
					}
				} else {
					stack.getCapability(ElectrodynamicsCapabilities.BOOLEAN_STORAGE_CAPABILITY).ifPresent(h -> h.setBoolean(1, false));
				}
			}
		}
		stack.getCapability(ElectrodynamicsCapabilities.INTEGER_STORAGE_CAPABILITY).ifPresent(h -> {
			if (h.getInt(1) > 100) {
				h.setInt(1, 0);
			}
		});
	}

	@Override
	public boolean canBeDepleted() {
		return false;
	}

	@Override
	public boolean isEnchantable(ItemStack stack) {
		return false;
	}

	@Override
	public boolean isValidRepairItem(ItemStack stack1, ItemStack stack2) {
		return false;
	}

	@Override
	public boolean isBarVisible(ItemStack stack) {
		return stack.getCapability(CapabilityUtils.getFluidItemCap()).map(m -> {
			RestrictedFluidHandlerItemStack cap = (RestrictedFluidHandlerItemStack) m;
			return 13.0 * cap.getFluidInTank(0).getAmount() / cap.getTankCapacity(0) < 13.0;
		}).orElse(false);
	}

	@Override
	public int getBarWidth(ItemStack stack) {
		return (int) Math.round(stack.getCapability(CapabilityUtils.getFluidItemCap()).map(h -> {
			RestrictedFluidHandlerItemStack cap = (RestrictedFluidHandlerItemStack) h;
			return 13.0 * cap.getFluidInTank(0).getAmount() / cap.getTankCapacity(0);
		}).orElse(13.0));
	}

	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		return slotChanged;
	}

	public Pair<List<ResourceLocation>, List<Fluid>> getWhitelistedFluids() {
		List<ResourceLocation> tags = new ArrayList<>();
		List<Fluid> fluids = new ArrayList<>();
		tags.add(ElectrodynamicsTags.Fluids.HYDROGEN.getName());
		fluids.add(DeferredRegisters.fluidHydrogen);
		return Pair.of(tags, fluids);
	}

	private static void ascendWithJetpack(double speed, double termVelocity, Player player) {
		Vec3 currMovement = player.getDeltaMovement();
		double y = currMovement.y + speed >= termVelocity ? termVelocity : currMovement.y + speed;
		currMovement = new Vec3(currMovement.x, y, currMovement.z);
		player.setDeltaMovement(currMovement);
		player.resetFallDistance();
		player.hurtMarked = true;
	}

	private static void hoverWithJetpack(Player player) {
		// TODO doesnt work on server
		if (player.isShiftKeyDown()) {
			Vec3 currMovement = player.getDeltaMovement();
			currMovement = new Vec3(currMovement.x, -0.3, currMovement.z);
			player.resetFallDistance();
			player.setDeltaMovement(currMovement);
		} else {
			Vec3 currMovement = player.getDeltaMovement();
			currMovement = new Vec3(currMovement.x, 0, currMovement.z);
			player.setDeltaMovement(currMovement);
			player.resetFallDistance();
		}
		player.hurtMarked = true;
	}

	private static void handleRunning(Level world, Entity entity, ItemStack stack) {
		stack.getCapability(CapabilityUtils.getFluidItemCap()).ifPresent(h -> h.drain(ItemJetpack.USAGE_PER_TICK, FluidAction.EXECUTE));
		stack.getCapability(ElectrodynamicsCapabilities.BOOLEAN_STORAGE_CAPABILITY).ifPresent(h -> h.setBoolean(1, true));

	}

	private static void handleSound(int ticks, Player player) {
		if (ticks % 10 == 0) {
			player.playNotifySound(SoundRegister.SOUND_JETPACK.get(), SoundSource.PLAYERS, 1, 1);
		}
	}

	private static void renderParticles(Level world, Entity entity) {
		Vec3 worldPosition = entity.position();
		for (int i = 0; i < 5; i++) {
			double x = worldPosition.x - world.random.nextFloat() + 0.5;
			double z = worldPosition.z - world.random.nextFloat() + 0.5;
			world.addParticle(ParticleTypes.FLAME, x, worldPosition.y + 0.8, z, 0.0D, -2D, 0.0D);
		}
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
		return ARMOR_TEXTURE_LOCATION;
	}

	public enum Jetpack implements ICustomArmor {
		JETPACK;

		@Override
		public int getDurabilityForSlot(EquipmentSlot slotIn) {
			return 100;
		}

		@Override
		public int getDefenseForSlot(EquipmentSlot slotIn) {
			return 1;
		}

		@Override
		public SoundEvent getEquipSound() {
			return SoundEvents.ARMOR_EQUIP_IRON;
		}

		@Override
		public String getName() {
			return References.ID + ":jetpack";
		}

		@Override
		public float getToughness() {
			return 0.0F;
		}

		@Override
		public float getKnockbackResistance() {
			return 0.0F;
		}

	}

}
