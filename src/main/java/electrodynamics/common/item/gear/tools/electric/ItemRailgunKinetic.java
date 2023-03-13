package electrodynamics.common.item.gear.tools.electric;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.common.entity.projectile.EntityCustomProjectile;
import electrodynamics.common.entity.projectile.types.EntityMetalRod;
import electrodynamics.common.item.gear.tools.electric.utils.ItemRailgun;
import electrodynamics.common.tags.ElectrodynamicsTags;
import electrodynamics.prefab.item.ElectricItemProperties;
import electrodynamics.prefab.utilities.object.TransferPack;
import electrodynamics.registers.ElectrodynamicsSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

public class ItemRailgunKinetic extends ItemRailgun {

	public static final double JOULES_PER_SHOT = 100000.0;
	private static final int OVERHEAT_TEMPERATURE = 400;
	private static final int TEMPERATURE_PER_SHOT = 300;
	private static final double TEMPERATURE_REDUCED_PER_TICK = 2.0;
	private static final double OVERHEAT_WARNING_THRESHOLD = 0.75;

	public ItemRailgunKinetic(ElectricItemProperties properties) {
		super(properties, OVERHEAT_TEMPERATURE, OVERHEAT_WARNING_THRESHOLD, TEMPERATURE_REDUCED_PER_TICK);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
		ItemStack gunStack;
		ItemStack ammoStack;

		if (handIn == InteractionHand.MAIN_HAND) {
			gunStack = playerIn.getMainHandItem();
			ammoStack = playerIn.getOffhandItem();
		} else {
			gunStack = playerIn.getOffhandItem();
			ammoStack = playerIn.getMainHandItem();
		}

		if (!worldIn.isClientSide) {
			ItemRailgunKinetic railgun = (ItemRailgunKinetic) gunStack.getItem();

			if (railgun.getJoulesStored(gunStack) >= JOULES_PER_SHOT && !ammoStack.isEmpty() && railgun.getTemperatureStored(gunStack) <= OVERHEAT_TEMPERATURE - TEMPERATURE_PER_SHOT) {

				EntityCustomProjectile projectile = null;
				int i = 0;

				for (Ingredient ammo : getRailgunAmmo()) {
					if (ammo.test(ammoStack)) {
						projectile = new EntityMetalRod(playerIn, worldIn, i);
						break;
					}
					i++;
				}

				if (projectile == null) {
					worldIn.playSound(null, playerIn.blockPosition(), ElectrodynamicsSounds.SOUND_RAILGUNKINETIC_NOAMMO.get(), SoundSource.PLAYERS, 1, 1);
				} else {
					railgun.extractPower(gunStack, JOULES_PER_SHOT, false);
					worldIn.playSound(null, playerIn.blockPosition(), ElectrodynamicsSounds.SOUND_RAILGUNKINETIC.get(), SoundSource.PLAYERS, 1, 1);
					projectile.setItem(ammoStack);
					projectile.setNoGravity(true);
					projectile.setOwner(playerIn);
					projectile.shootFromRotation(playerIn, playerIn.getXRot(), playerIn.getYRot(), 0f, 20f, 1.0F);
					if (!worldIn.isClientSide) {
						worldIn.addFreshEntity(projectile);
					}
					railgun.recieveHeat(gunStack, TransferPack.temperature(TEMPERATURE_PER_SHOT), false);
					ammoStack.shrink(1);
				}
			} else {
				worldIn.playSound(null, playerIn.blockPosition(), ElectrodynamicsSounds.SOUND_RAILGUNKINETIC_NOAMMO.get(), SoundSource.PLAYERS, 1, 1);
			}
		}
		return InteractionResultHolder.pass(gunStack);
	}

	/*
	 * Allows easy addition of ammo types in the future Uses the Ingredient of the item to allow cross-mod compatibility
	 */
	public List<Ingredient> getRailgunAmmo() {
		List<Ingredient> railgunAmmo = new ArrayList<>();
		railgunAmmo.add(Ingredient.of(ElectrodynamicsTags.Items.ROD_STEEL));
		railgunAmmo.add(Ingredient.of(ElectrodynamicsTags.Items.ROD_STAINLESSSTEEL));
		railgunAmmo.add(Ingredient.of(ElectrodynamicsTags.Items.ROD_HSLASTEEL));
		return railgunAmmo;
	}
}
