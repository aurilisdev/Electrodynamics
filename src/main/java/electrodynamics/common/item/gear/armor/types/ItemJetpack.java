package electrodynamics.common.item.gear.armor.types;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.mojang.datafixers.util.Pair;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.References;
import electrodynamics.api.fluid.RestrictedFluidHandlerItemStack;
import electrodynamics.client.ClientRegister;
import electrodynamics.client.KeyBinds;
import electrodynamics.client.render.model.armor.types.ModelJetpack;
import electrodynamics.common.item.gear.armor.ICustomArmor;
import electrodynamics.common.packet.NetworkHandler;
import electrodynamics.common.packet.types.PacketJetpackFlightServer;
import electrodynamics.common.tags.ElectrodynamicsTags;
import electrodynamics.prefab.utilities.CapabilityUtils;
import electrodynamics.prefab.utilities.NBTUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
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
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
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
import net.minecraftforge.network.PacketDistributor;

public class ItemJetpack extends ArmorItem {

	public static final Fluid EMPTY_FLUID = Fluids.EMPTY;
	public static final int MAX_CAPACITY = 30000;

	public static final int USAGE_PER_TICK = 1;
	public static final double VERT_SPEED_INCREASE = 0.5;
	public static final double TERMINAL_VERTICAL_VELOCITY = 1;
	public static final double TERMINAL_X_VELOCITY = 1;
	public static final double TERMINAL_Z_VELOCITY = 1;

	private static final String ARMOR_TEXTURE_LOCATION = References.ID + ":textures/model/armor/jetpack.png";
	
	public ItemJetpack() {
		super(Jetpack.JETPACK, EquipmentSlot.CHEST, new Item.Properties().tab(References.CORETAB).stacksTo(1));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void initializeClient(Consumer<IItemRenderProperties> consumer) {
		consumer.accept(new IItemRenderProperties() {
			@Override
			public HumanoidModel<?> getArmorModel(LivingEntity entity, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel<?> properties) {

				ModelJetpack<LivingEntity> model = new ModelJetpack<>(ClientRegister.JETPACK.bakeRoot());

				model.crouching = properties.crouching;
				model.riding = properties.riding;
				model.young = properties.young;

				return model;
			}
		});
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
		return new RestrictedFluidHandlerItemStack(stack, stack, MAX_CAPACITY, getWhitelistedFluids());
	}

	@Override
	public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> items) {
		if (allowdedIn(tab)) {
			items.add(new ItemStack(this));
			if (!CapabilityUtils.isFluidItemNull()) {
				ItemStack full = new ItemStack(this);
				Fluid fluid = getWhitelistedFluids().getSecond().get(0);
				full.getCapability(CapabilityUtils.getFluidItemCap()).ifPresent(h -> ((RestrictedFluidHandlerItemStack) h).fillInit(new FluidStack(fluid, MAX_CAPACITY)));
				full.getCapability(CapabilityUtils.getFluidItemCap()).ifPresent(h -> ((RestrictedFluidHandlerItemStack) h).hasInitHappened(true));
				items.add(full);

			}
		}
	}

	@Override
	public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flagIn) {
		staticAppendHoverText(stack, world, tooltip, flagIn);
		super.appendHoverText(stack, world, tooltip, flagIn);
	}

	protected static void staticAppendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flagIn) {
		if (!CapabilityUtils.isFluidItemNull()) {
			stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent(h -> tooltip.add(new TextComponent(h.getFluidInTank(0).getAmount() + " / " + MAX_CAPACITY + " mB").withStyle(ChatFormatting.GRAY)));
		}
		// cheesing sync issues one line of code at a time
		if (stack.hasTag()) {
			int mode = stack.getTag().getInt(NBTUtils.MODE);
			Component modeTip = switch (mode) {
			case 0 -> new TranslatableComponent("tooltip.jetpack.mode").withStyle(ChatFormatting.GRAY).append(new TranslatableComponent("tooltip.jetpack.moderegular").withStyle(ChatFormatting.GREEN));
			case 1 -> new TranslatableComponent("tooltip.jetpack.mode").withStyle(ChatFormatting.GRAY).append(new TranslatableComponent("tooltip.jetpack.modehover").withStyle(ChatFormatting.AQUA));
			case 2 -> new TranslatableComponent("tooltip.jetpack.mode").withStyle(ChatFormatting.GRAY).append(new TranslatableComponent("tooltip.jetpack.modeoff").withStyle(ChatFormatting.RED));
			default -> new TextComponent("");
			};
			tooltip.add(modeTip);
		} else {
			tooltip.add(new TranslatableComponent("tooltip.jetpack.mode").withStyle(ChatFormatting.GRAY).append(new TranslatableComponent("tooltip.jetpack.moderegular").withStyle(ChatFormatting.GREEN)));
		}
	}

	@Override
	public void onArmorTick(ItemStack stack, Level world, Player player) {
		super.onArmorTick(stack, world, player);
		armorTick(stack, world, player);
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
		InteractionResultHolder<ItemStack> superResult = super.use(pLevel, pPlayer, pHand);
		if(!pLevel.isClientSide && superResult.getResult() == InteractionResult.SUCCESS) {
			PacketDistributor.TRACKING_ENTITY_AND_SELF.with(null);
		}
		return superResult; 
	}

	protected static void armorTick(ItemStack stack, Level world, Player player) {
		if (world.isClientSide) {
			ArmorItem item = (ArmorItem) stack.getItem();
			if (item.getSlot() == EquipmentSlot.CHEST && stack.hasTag()) {
				boolean isDown = KeyBinds.jetpackAscend.isDown();
				int mode = stack.hasTag() ? stack.getTag().getInt(NBTUtils.MODE) : 0;
				boolean enoughFuel = stack.getCapability(CapabilityUtils.getFluidItemCap()).map(m -> m.getFluidInTank(0).getAmount() >= ItemJetpack.USAGE_PER_TICK).orElse(false);
				if (enoughFuel) {
					if (mode == 0 && isDown) {
						moveWithJetpack(ItemJetpack.VERT_SPEED_INCREASE, ItemJetpack.TERMINAL_VERTICAL_VELOCITY, player);
						renderParticles(world, player);
						sendPacket(player, true);
					} else if (mode == 1 && isDown) {
						moveWithJetpack(ItemJetpack.VERT_SPEED_INCREASE / 2, ItemJetpack.TERMINAL_VERTICAL_VELOCITY / 2, player);
						renderParticles(world, player);
						sendPacket(player, true);
					} else if (mode == 1 && player.fallDistance > 0) {
						hoverWithJetpack(player);
						renderParticles(world, player);
						sendPacket(player, true);
					} else {
						sendPacket(player, false);
					}
				} else {
					sendPacket(player, false);
				}
			} else {
				sendPacket(player, false);
			}
		} else {
			CompoundTag tag = stack.getOrCreateTag();
			boolean hasRan = tag.getBoolean(NBTUtils.USED);
			tag.putBoolean(NBTUtils.PLAYING_SOUND, tag.getBoolean(NBTUtils.PLAYING_SOUND));
			if (hasRan) {
				drainHydrogen(stack);
				player.resetFallDistance();
			} 
		}
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
		return staticIsBarVisible(stack);
	}

	protected static boolean staticIsBarVisible(ItemStack stack) {
		return stack.getCapability(CapabilityUtils.getFluidItemCap()).map(m -> {
			RestrictedFluidHandlerItemStack cap = (RestrictedFluidHandlerItemStack) m;
			return 13.0 * cap.getFluidInTank(0).getAmount() / cap.getTankCapacity(0) < 13.0;
		}).orElse(false);
	}

	@Override
	public int getBarWidth(ItemStack stack) {
		return staticGetBarWidth(stack);
	}

	protected static int staticGetBarWidth(ItemStack stack) {
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
		return staticGetWhitelistedFluids();
	}

	protected static Pair<List<ResourceLocation>, List<Fluid>> staticGetWhitelistedFluids() {
		List<ResourceLocation> tags = new ArrayList<>();
		List<Fluid> fluids = new ArrayList<>();
		tags.add(ElectrodynamicsTags.Fluids.HYDROGEN.location());
		fluids.add(DeferredRegisters.fluidHydrogen);
		return Pair.of(tags, fluids);
	}

	protected static void moveWithJetpack(double speed, double termVelocity, Player player) {
		Vec3 lookVector = player.getLookAngle();

		int signX = (int) Math.ceil(Math.signum(lookVector.x));
		double newX = signX * Math.min(Math.abs(lookVector.x * 2), TERMINAL_X_VELOCITY);
		
		int signZ = (int) Math.ceil(Math.signum(lookVector.z));
		double newZ = signZ * Math.min(Math.abs(lookVector.z * 2), TERMINAL_Z_VELOCITY);
		
		double ySum = player.getDeltaMovement().y + speed;
		double absY = Math.min(Math.abs(ySum), termVelocity);
		double newY = Math.signum(ySum) * absY;
		Vec3 currMovement = new Vec3(newX, newY, newZ);
		

		player.setDeltaMovement(currMovement);
		player.resetFallDistance();
	}

	protected static void hoverWithJetpack(Player player) {
		Vec3 currMovement = player.getDeltaMovement();
		Vec3 lookVector = player.getLookAngle();

		boolean isMoving = Minecraft.getInstance().options.keyUp.isDown();
		
		int signX = (int) Math.ceil(Math.signum(lookVector.x));
		double newX = signX * Math.min(Math.abs(lookVector.x * 2), TERMINAL_X_VELOCITY);
		
		int signZ = (int) Math.ceil(Math.signum(lookVector.z));
		double newZ = signZ * Math.min(Math.abs(lookVector.z * 2), TERMINAL_Z_VELOCITY);
		if (player.isShiftKeyDown()) {
			currMovement = new Vec3(isMoving ? newX : currMovement.x, -0.3, isMoving ? newZ : currMovement.z);
		} else {
			currMovement = new Vec3(isMoving ? newX : currMovement.x, 0, isMoving ? newZ : currMovement.z);
		}
		player.setDeltaMovement(currMovement);
		player.resetFallDistance();
	}

	protected static void renderParticles(Level world, Entity entity) {
		Vec3 worldPosition = entity.position();
		for (int i = 0; i < 5; i++) {
			double x = worldPosition.x - world.random.nextFloat() + 0.5;
			double z = worldPosition.z - world.random.nextFloat() + 0.5;
			world.addParticle(ParticleTypes.FLAME, x, worldPosition.y + 0.8, z, 0.0D, -2D, 0.0D);
		}
	}

	protected static void drainHydrogen(ItemStack stack) {
		stack.getCapability(CapabilityUtils.getFluidItemCap()).ifPresent(h -> h.drain(ItemJetpack.USAGE_PER_TICK, FluidAction.EXECUTE));
	}

	protected static void sendPacket(Player player, boolean state) {
		NetworkHandler.CHANNEL.sendToServer(new PacketJetpackFlightServer(player.getUUID(), state));
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
