package electrodynamics.common.item.gear.tools.electric;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import electrodynamics.common.entity.projectile.EntityCustomProjectile;
import electrodynamics.common.entity.projectile.types.EntityMetalRod;
import electrodynamics.common.item.gear.tools.electric.utils.ItemRailgun;
import electrodynamics.registers.ElectrodynamicsSounds;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import voltaic.api.item.IItemTemperate;
import voltaic.common.tags.VoltaicTags;
import voltaic.prefab.item.ElectricItemProperties;

public class ItemRailgunKinetic extends ItemRailgun {

	public static final double JOULES_PER_SHOT = 100000.0;
	public static final int OVERHEAT_TEMPERATURE = 400;
	public static final int TEMPERATURE_PER_SHOT = 300;
	public static final double TEMPERATURE_REDUCED_PER_TICK = 2.0;
	public static final double OVERHEAT_WARNING_THRESHOLD = 0.75;

	public ItemRailgunKinetic(ElectricItemProperties properties, Supplier<ItemGroup> creativeTab) {
		super(properties, creativeTab, OVERHEAT_TEMPERATURE, OVERHEAT_WARNING_THRESHOLD, TEMPERATURE_REDUCED_PER_TICK);
	}

	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		ItemStack gunStack;
		ItemStack ammoStack;

		if (hand == Hand.MAIN_HAND) {
			gunStack = player.getMainHandItem();
			ammoStack = player.getOffhandItem();
		} else {
			gunStack = player.getOffhandItem();
			ammoStack = player.getMainHandItem();
		}

		if (world.isClientSide) {
			return ActionResult.pass(gunStack);
		}

		ItemRailgunKinetic railgun = (ItemRailgunKinetic) gunStack.getItem();

		if (railgun.getJoulesStored(gunStack) < JOULES_PER_SHOT || ammoStack.isEmpty() || IItemTemperate.getTemperature(gunStack) > OVERHEAT_TEMPERATURE - TEMPERATURE_PER_SHOT) {

			world.playSound(null, player.blockPosition(), ElectrodynamicsSounds.SOUND_RAILGUNKINETIC_NOAMMO.get(), SoundCategory.PLAYERS, 1, 1);
			return ActionResult.pass(gunStack);

		}

		EntityCustomProjectile projectile = null;
		int i = 0;

		for (Ingredient ammo : getRailgunAmmo()) {
			if (ammo.test(ammoStack)) {
				projectile = new EntityMetalRod(player, world, i);
				break;
			}
			i++;
		}

		if (projectile == null) {
			world.playSound(null, player.blockPosition(), ElectrodynamicsSounds.SOUND_RAILGUNKINETIC_NOAMMO.get(), SoundCategory.PLAYERS, 1, 1);
			return ActionResult.pass(gunStack);
		}

		railgun.extractPower(gunStack, JOULES_PER_SHOT, false);
		world.playSound(null, player.blockPosition(), ElectrodynamicsSounds.SOUND_RAILGUNKINETIC.get(), SoundCategory.PLAYERS, 1, 1);
		projectile.setItem(ammoStack);
		projectile.setNoGravity(true);
		projectile.setOwner(player);

		Vector3d vec31 = player.getUpVector(1.0F);

		Quaternion quaternion = new Quaternion(new Vector3f(vec31), 0, true);

		Vector3d playerViewVector = player.getViewVector(1.0F);

		Vector3f viewVector = new Vector3f(playerViewVector);

		viewVector.transform(quaternion);

		projectile.shoot(viewVector.x(), viewVector.y(), viewVector.z(), 20.0F, 0.0F);

		world.addFreshEntity(projectile);
		railgun.recieveHeat(gunStack, TEMPERATURE_PER_SHOT, false);

		if (!player.isCreative()) {
			ammoStack.shrink(1);
		}

		return ActionResult.pass(gunStack);
	}

	/*
	 * Allows easy addition of ammo types in the future Uses the Ingredient of the item to allow cross-mod compatibility
	 */
	public List<Ingredient> getRailgunAmmo() {
		List<Ingredient> railgunAmmo = new ArrayList<>();
		railgunAmmo.add(Ingredient.of(VoltaicTags.Items.ROD_STEEL));
		railgunAmmo.add(Ingredient.of(VoltaicTags.Items.ROD_STAINLESSSTEEL));
		railgunAmmo.add(Ingredient.of(VoltaicTags.Items.ROD_HSLASTEEL));
		return railgunAmmo;
	}
}
