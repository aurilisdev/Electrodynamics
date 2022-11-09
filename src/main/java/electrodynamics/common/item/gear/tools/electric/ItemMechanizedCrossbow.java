package electrodynamics.common.item.gear.tools.electric;

import java.util.List;
import java.util.function.Predicate;

import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;

import electrodynamics.SoundRegister;
import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.api.item.IItemElectric;
import electrodynamics.prefab.item.ElectricItemProperties;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ItemMechanizedCrossbow extends ProjectileWeaponItem implements IItemElectric {

	private final ElectricItemProperties properties;

	public static final int JOULES_PER_SHOT = 5000;
	public static final int NUMBER_OF_SHOTS = 200;

	public static final int PROJECTILE_RANGE = 20;
	public static final int PROJECTILE_SPEED = 3;

	public ItemMechanizedCrossbow(ElectricItemProperties properties) {
		super(properties);
		this.properties = properties;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		ItemStack crossbow = player.getItemInHand(hand);
		if (!world.isClientSide) {
			ItemMechanizedCrossbow mechanized = (ItemMechanizedCrossbow) crossbow.getItem();
			if (mechanized.getJoulesStored(crossbow) >= JOULES_PER_SHOT) {
				ItemStack arrow = getAmmo(player);
				Projectile projectile = getArrow(world, player, crossbow, arrow);
				if (!arrow.isEmpty()) {
					mechanized.extractPower(crossbow, JOULES_PER_SHOT, false);
					arrow.shrink(1);
					Vec3 playerUp = player.getUpVector(1.0F);
					Quaternion quaternion = new Quaternion(new Vector3f(playerUp), 0, true);
					Vec3 playerView = player.getViewVector(1.0F);
					Vector3f viewVector = new Vector3f(playerView);
					viewVector.transform(quaternion);
					projectile.shoot(viewVector.x(), viewVector.y(), viewVector.z(), PROJECTILE_SPEED, 1);
					world.addFreshEntity(projectile);
					world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.CROSSBOW_SHOOT, SoundSource.PLAYERS, 1.0F, 1);
				} else {
					world.playSound(null, player.blockPosition(), SoundRegister.SOUND_RAILGUNKINETIC_NOAMMO.get(), SoundSource.PLAYERS, 1, 1);
				}
			} else {
				world.playSound(null, player.blockPosition(), SoundRegister.SOUND_RAILGUNKINETIC_NOAMMO.get(), SoundSource.PLAYERS, 1, 1);
			}
		}
		return InteractionResultHolder.pass(crossbow);
	}

	@Override
	public boolean canBeDepleted() {
		return false;
	}

	@Override
	public boolean isEnchantable(ItemStack stack) {
		return false;
	}

	private static AbstractArrow getArrow(Level world, LivingEntity entity, ItemStack crossbow, ItemStack ammo) {
		ArrowItem arrowitem = (ArrowItem) (ammo.getItem() instanceof ArrowItem ? ammo.getItem() : Items.ARROW);
		AbstractArrow abstractarrow = arrowitem.createArrow(world, ammo, entity);
		if (entity instanceof Player) {
			abstractarrow.setCritArrow(true);
		}

		abstractarrow.setSoundEvent(SoundEvents.CROSSBOW_HIT);
		abstractarrow.setShotFromCrossbow(true);
		int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PIERCING, crossbow);
		if (i > 0) {
			abstractarrow.setPierceLevel((byte) i);
		}

		return abstractarrow;
	}

	private ItemStack getAmmo(Player player) {
		Inventory playerInv = player.getInventory();
		for (ItemStack stack : playerInv.items) {
			if (getAllSupportedProjectiles().test(stack)) {
				return stack;
			}
		}
		return ItemStack.EMPTY;
	}

	@Override
	public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
		if (allowedIn(group)) {
			ItemStack charged = new ItemStack(this);
			IItemElectric.setEnergyStored(charged, properties.capacity);
			items.add(charged);
			ItemStack empty = new ItemStack(this);
			IItemElectric.setEnergyStored(empty, 0);
			items.add(empty);
		}
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
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		return slotChanged;
	}

	@Override
	public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		tooltip.add(Component.translatable("tooltip.item.electric.info").withStyle(ChatFormatting.GRAY).append(Component.literal(ChatFormatter.getChatDisplayShort(getJoulesStored(stack), DisplayUnit.JOULES))));
		tooltip.add(Component.translatable("tooltip.item.electric.voltage", ChatFormatter.getChatDisplayShort(properties.receive.getVoltage(), DisplayUnit.VOLTAGE) + " / " + ChatFormatter.getChatDisplayShort(properties.extract.getVoltage(), DisplayUnit.VOLTAGE)).withStyle(ChatFormatting.RED));
	}

	@Override
	public ElectricItemProperties getElectricProperties() {
		return properties;
	}

	@Override
	public Predicate<ItemStack> getAllSupportedProjectiles() {
		return ARROW_ONLY;
	}

	@Override
	public int getDefaultProjectileRange() {
		return PROJECTILE_RANGE;
	}

}
