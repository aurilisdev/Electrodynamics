package electrodynamics.common.item.gear.tools.electric;

import electrodynamics.common.entity.projectile.EntityCustomProjectile;
import electrodynamics.common.entity.projectile.types.EntityEnergyBlast;
import electrodynamics.common.item.gear.tools.electric.utils.ItemRailgun;
import electrodynamics.prefab.item.ElectricItemProperties;
import electrodynamics.prefab.utilities.object.TransferPack;
import electrodynamics.registers.ElectrodynamicsSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemRailgunPlasma extends ItemRailgun {

	public static final double JOULES_PER_SHOT = 250000.0;
	private static final int OVERHEAT_TEMPERATURE = 1250;
	private static final int TEMPERATURE_PER_SHOT = 125;
	private static final double TEMPERATURE_REDUCED_PER_TICK = 2.5;
	private static final double OVERHEAT_WARNING_THRESHOLD = 0.5;

	public ItemRailgunPlasma(ElectricItemProperties properties) {
		super(properties, OVERHEAT_TEMPERATURE, OVERHEAT_WARNING_THRESHOLD, TEMPERATURE_REDUCED_PER_TICK);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
		ItemStack gunStack;

		if (handIn == InteractionHand.MAIN_HAND) {
			gunStack = playerIn.getMainHandItem();
		} else {
			gunStack = playerIn.getOffhandItem();
		}

		if (!worldIn.isClientSide) {
			ItemRailgunPlasma railgun = (ItemRailgunPlasma) gunStack.getItem();

			if (railgun.getJoulesStored(gunStack) >= JOULES_PER_SHOT && railgun.getTemperatureStored(gunStack) <= OVERHEAT_TEMPERATURE - TEMPERATURE_PER_SHOT) {

				EntityCustomProjectile projectile = new EntityEnergyBlast(playerIn, worldIn);
				projectile.setNoGravity(true);
				projectile.setOwner(playerIn);
				projectile.shootFromRotation(playerIn, playerIn.getXRot(), playerIn.getYRot(), 0F, 5f, 1.0F);
				worldIn.addFreshEntity(projectile);

				railgun.extractPower(gunStack, JOULES_PER_SHOT, false);
				worldIn.playSound(null, playerIn.blockPosition(), ElectrodynamicsSounds.SOUND_RAILGUNPLASMA.get(), SoundSource.PLAYERS, 1, 1);
				railgun.recieveHeat(gunStack, TransferPack.temperature(TEMPERATURE_PER_SHOT), false);
			} else {
				worldIn.playSound(null, playerIn.blockPosition(), ElectrodynamicsSounds.SOUND_RAILGUNKINETIC_NOAMMO.get(), SoundSource.PLAYERS, 1, 1);
			}
		}
		return InteractionResultHolder.pass(gunStack);
	}

}
