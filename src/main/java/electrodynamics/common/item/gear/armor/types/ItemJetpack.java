package electrodynamics.common.item.gear.armor.types;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import electrodynamics.api.References;
import electrodynamics.api.capability.types.fluid.RestrictedFluidHandlerItemStack;
import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.client.ClientRegister;
import electrodynamics.client.keys.KeyBinds;
import electrodynamics.client.render.model.armor.types.ModelJetpack;
import electrodynamics.common.item.gear.armor.ICustomArmor;
import electrodynamics.common.packet.NetworkHandler;
import electrodynamics.common.packet.types.client.PacketRenderJetpackParticles;
import electrodynamics.common.packet.types.server.PacketJetpackFlightServer;
import electrodynamics.common.tags.ElectrodynamicsTags;
import electrodynamics.prefab.utilities.CapabilityUtils;
import electrodynamics.prefab.utilities.NBTUtils;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import electrodynamics.registers.ElectrodynamicsFluids;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
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
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.network.PacketDistributor;

public class ItemJetpack extends ArmorItem {

	public static final int MAX_CAPACITY = 30000;

	public static final int USAGE_PER_TICK = 1;
	public static final double VERT_SPEED_INCREASE = 0.5;
	public static final double TERMINAL_VERTICAL_VELOCITY = 1;

	private static final String ARMOR_TEXTURE_LOCATION = References.ID + ":textures/model/armor/jetpack.png";

	public static final float OFFSET = 0.1F;

	public static final String DELTA_Y_KEY = "prevdeltay";
	public static final String WAS_HURT_KEY = "washurt";

	public ItemJetpack() {
		super(Jetpack.JETPACK, EquipmentSlot.CHEST, new Item.Properties().tab(References.CORETAB).stacksTo(1));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void initializeClient(Consumer<IClientItemExtensions> consumer) {
		consumer.accept(new IClientItemExtensions() {
			@Override
			public HumanoidModel<?> getHumanoidArmorModel(LivingEntity entity, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel<?> properties) {

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
		return new RestrictedFluidHandlerItemStack(stack, stack, MAX_CAPACITY).setValidator(getFuelPredicate());
	}

	@Override
	public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> items) {

		if (!allowedIn(tab)) {
			return;
		}

		items.add(new ItemStack(this));
		if (!CapabilityUtils.isFluidItemNull()) {
			ItemStack full = new ItemStack(this);
			full.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).ifPresent(h -> ((RestrictedFluidHandlerItemStack) h).fill(new FluidStack(ElectrodynamicsFluids.fluidHydrogen, MAX_CAPACITY), FluidAction.EXECUTE));
			items.add(full);

		}
	}

	@Override
	public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flagIn) {
		staticAppendHoverText(stack, world, tooltip, flagIn);
		super.appendHoverText(stack, world, tooltip, flagIn);
	}

	public static void staticAppendHoverText(ItemStack stack, Level world, List<Component> tooltips, TooltipFlag flagIn) {
		if (!CapabilityUtils.isFluidItemNull()) {

			stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).ifPresent(cap -> {
				FluidStack fluid = cap.getFluidInTank(0);
				// tooltips.add(gas.getGas().getDescription());
				if (fluid.isEmpty()) {
					tooltips.add(ElectroTextUtils.ratio(Component.literal("0"), ChatFormatter.formatFluidMilibuckets(MAX_CAPACITY)));
				} else {
					tooltips.add(ElectroTextUtils.ratio(ChatFormatter.formatFluidMilibuckets(fluid.getAmount()), ChatFormatter.formatFluidMilibuckets(MAX_CAPACITY)));
				}

			});
		}
		// cheesing sync issues one line of code at a time
		if (stack.hasTag()) {
			tooltips.add(getModeText(stack.getTag().getInt(NBTUtils.MODE)));
		} else {
			tooltips.add(ElectroTextUtils.tooltip("jetpack.mode").withStyle(ChatFormatting.GRAY).append(ElectroTextUtils.tooltip("jetpack.moderegular").withStyle(ChatFormatting.GREEN)));
		}
	}

	public static Component getModeText(int mode) {
		return switch (mode) {
		case 0 -> ElectroTextUtils.tooltip("jetpack.mode").withStyle(ChatFormatting.GRAY).append(ElectroTextUtils.tooltip("jetpack.moderegular").withStyle(ChatFormatting.GREEN));
		case 1 -> ElectroTextUtils.tooltip("jetpack.mode").withStyle(ChatFormatting.GRAY).append(ElectroTextUtils.tooltip("jetpack.modehover").withStyle(ChatFormatting.AQUA));
		case 2 -> ElectroTextUtils.tooltip("jetpack.mode").withStyle(ChatFormatting.GRAY).append(ElectroTextUtils.tooltip("jetpack.modeelytra").withStyle(ChatFormatting.YELLOW));
		case 3 -> ElectroTextUtils.tooltip("jetpack.mode").withStyle(ChatFormatting.GRAY).append(ElectroTextUtils.tooltip("jetpack.modeoff").withStyle(ChatFormatting.RED));
		default -> Component.empty();
		};
	}

	@Override
	public void onArmorTick(ItemStack stack, Level world, Player player) {
		super.onArmorTick(stack, world, player);
		armorTick(stack, world, player, OFFSET, false);
	}

	public static void armorTick(ItemStack stack, Level world, Player player, float particleZ, boolean isCombat) {
		if (world.isClientSide) {

			ArmorItem item = (ArmorItem) stack.getItem();
			if (item.getSlot() == EquipmentSlot.CHEST && stack.hasTag()) {
				boolean isDown = KeyBinds.jetpackAscend.isDown();
				int mode = stack.hasTag() ? stack.getTag().getInt(NBTUtils.MODE) : 0;
				boolean enoughFuel = stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).map(cap -> cap.getFluidInTank(0).getAmount() >= ItemJetpack.USAGE_PER_TICK).orElse(false);
				if (enoughFuel) {
					IFluidHandlerItem handler = (IFluidHandlerItem) stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).cast().resolve().get();
					if (mode == 0 && isDown) {
						double deltaY = moveWithJetpack(ItemJetpack.VERT_SPEED_INCREASE, ItemJetpack.TERMINAL_VERTICAL_VELOCITY, player, stack);
						renderClientParticles(world, player, particleZ);
						sendPacket(player, true, deltaY);
					} else if (mode == 1 && isDown) {
						double deltaY = moveWithJetpack(ItemJetpack.VERT_SPEED_INCREASE / 2.0, ItemJetpack.TERMINAL_VERTICAL_VELOCITY / 2.0, player, stack);
						renderClientParticles(world, player, particleZ);
						sendPacket(player, true, deltaY);
					} else if (mode == 1 && player.getFeetBlockState().isAir()) {
						double deltaY = hoverWithJetpack(player, stack);
						renderClientParticles(world, player, particleZ);
						sendPacket(player, true, deltaY);
					} else if (mode == 2 && isDown) {
						double deltaY = moveWithJetpack(ItemJetpack.VERT_SPEED_INCREASE / 4.0, ItemJetpack.TERMINAL_VERTICAL_VELOCITY / 4.0, player, stack);
						sendPacket(player, true, deltaY);
						// TODO elytra fuel particles?
					} else {
						sendPacket(player, false, player.getDeltaMovement().y);
					}
				} else {
					sendPacket(player, false, player.getDeltaMovement().y);
				}
			} else {
				sendPacket(player, false, player.getDeltaMovement().y);
			}
		} else {
			CompoundTag tag = stack.getOrCreateTag();
			boolean hasRan = tag.getBoolean(NBTUtils.USED);
			tag.putBoolean(NBTUtils.PLAYING_SOUND, tag.getBoolean(NBTUtils.PLAYING_SOUND));
			if (hasRan) {
				drainHydrogen(stack);
				NetworkHandler.CHANNEL.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player), new PacketRenderJetpackParticles(player.getUUID(), isCombat));
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

	public static boolean staticIsBarVisible(ItemStack stack) {
		return stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).map(cap -> (13.0 * cap.getFluidInTank(0).getAmount() / cap.getTankCapacity(0) < 13.0)).orElse(false);
	}

	@Override
	public int getBarWidth(ItemStack stack) {
		return staticGetBarWidth(stack);
	}

	public static int staticGetBarWidth(ItemStack stack) {
		return (int) Math.round(stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).map(cap -> (13.0 * cap.getFluidInTank(0).getAmount() / cap.getTankCapacity(0))).orElse(13.0));
	}

	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		return slotChanged;
	}

	@Override
	public boolean canElytraFly(ItemStack stack, LivingEntity entity) {
		return staticCanElytraFly(stack, entity);
	}

	public static boolean staticCanElytraFly(ItemStack stack, LivingEntity entity) {
		int mode = stack.hasTag() ? stack.getTag().getInt(NBTUtils.MODE) : 0;
		return mode == 2 && stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).map(cap -> cap.getFluidInTank(0).getAmount() >= ItemJetpack.USAGE_PER_TICK).orElse(false);
	}

	@Override
	public boolean elytraFlightTick(ItemStack stack, LivingEntity entity, int flightTicks) {
		return staticElytraFlightTick(stack, entity, flightTicks);
	}

	public static boolean staticElytraFlightTick(ItemStack stack, LivingEntity entity, int flightTicks) {
		if (entity.getLevel().isClientSide) {
			return true;
		}
		int nextFlightTick = flightTicks + 1;
		if (nextFlightTick % 10 == 0) {
			if (nextFlightTick % 20 == 0) {

				drainHydrogen(stack);

			}
			entity.gameEvent(net.minecraft.world.level.gameevent.GameEvent.ELYTRA_GLIDE);
		}
		return true;
	}

	protected static double moveWithJetpack(double speed, double termVelocity, Player player, ItemStack jetpack) {

		Vec3 movement = player.getDeltaMovement();

		// if (player.hasImpulse && wasEntityHurt(jetpack)) {
		// movement = new Vec3(movement.x, getPrevDeltaY(jetpack), movement.z);
		// }

		double newY = Math.min(movement.y + speed, termVelocity);
		Vec3 currMovement = new Vec3(movement.x, newY, movement.z);

		player.setDeltaMovement(currMovement);
		player.resetFallDistance();
		// we keep track of the previous delta y for impulses
		return currMovement.y;
	}

	protected static double hoverWithJetpack(Player player, ItemStack jetpack) {

		Vec3 movement = player.getDeltaMovement();

		if (player.hasImpulse && wasEntityHurt(jetpack)) {
			movement = new Vec3(movement.x, getPrevDeltaY(jetpack), movement.z);
		}

		if (player.isShiftKeyDown()) {
			movement = new Vec3(movement.x, -0.3, movement.z);
		} else {
			movement = new Vec3(movement.x, 0, movement.z);
		}
		player.setDeltaMovement(movement);
		player.resetFallDistance();
		// we keep track of the previous delta y for impulses
		return movement.y;
	}

	public static void renderClientParticles(Level world, Player player, float particleZ) {
		Vec3 worldPosition = player.position();
		float rad = (float) (processDeg(player.yBodyRot) / 180.0F * Math.PI);
		double cosY = Mth.cos(rad);
		double sinY = Mth.sin(rad);
		double xOffCos = cosY * 0.2;
		double xOffSin = sinY * 0.2;
		double zOffCos = cosY * particleZ;
		double zOffSin = sinY * particleZ;
		double xRight = worldPosition.x - xOffCos + zOffSin;
		double xLeft = worldPosition.x + xOffCos + zOffSin;
		double zRight = worldPosition.z - zOffCos - xOffSin;
		double zLeft = worldPosition.z - zOffCos + xOffSin;
		double y = worldPosition.y + (player.isShiftKeyDown() ? 0.5 : 0.8);
		for (int i = 0; i < 10; i++) {
			world.addParticle(ParticleTypes.FLAME, xRight, y, zRight, 0, -2D, 0);
			// world.sendParticles(ParticleTypes.FLAME, xRight, y , zRight, 0, 0.0D, -2D,
			// 0.0D, 2D);
		}
		for (int i = 0; i < 10; i++) {
			world.addParticle(ParticleTypes.FLAME, xLeft, y, zLeft, 0, -2D, 0);
			// world.sendParticles(ParticleTypes.FLAME, xLeft, y, zLeft, 0, 0.0D, -2D, 0.0D,
			// 2D);
		}
	}

	protected static void drainHydrogen(ItemStack stack) {
		stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).ifPresent(cap -> cap.drain(ItemJetpack.USAGE_PER_TICK, FluidAction.EXECUTE));
	}

	protected static void sendPacket(Player player, boolean state, double prevDeltaMove) {
		NetworkHandler.CHANNEL.sendToServer(new PacketJetpackFlightServer(player.getUUID(), state, prevDeltaMove));
	}

	protected static double getPrevDeltaY(ItemStack stack) {
		return stack.getOrCreateTag().getDouble(DELTA_Y_KEY);
	}

	protected static boolean wasEntityHurt(ItemStack stack) {
		return stack.getOrCreateTag().getBoolean(WAS_HURT_KEY);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
		return ARMOR_TEXTURE_LOCATION;
	}

	// we need to do this based upon some testing I did
	private static float processDeg(float deg) {
		if (deg > 180) {
			return deg - 360;
		}
		if (deg < 180) {
			return deg + 360;
		}
		return deg;
	}

	public static Predicate<FluidStack> getFuelPredicate() {
		return fluid -> fluid.getFluid().is(ElectrodynamicsTags.Fluids.HYDROGEN);
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
