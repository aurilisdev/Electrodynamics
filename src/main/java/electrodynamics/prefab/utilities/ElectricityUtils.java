package electrodynamics.prefab.utilities;

import electrodynamics.common.tile.electricitygrid.GenericTileWire;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.energy.IEnergyStorage;
import voltaic.api.electricity.ICapabilityElectrodynamic;
import voltaic.common.tags.VoltaicTags;
import voltaic.prefab.utilities.CapabilityUtils;
import voltaic.prefab.utilities.ItemUtils;
import voltaic.prefab.utilities.object.TransferPack;
import voltaic.registers.VoltaicCapabilities;
import voltaic.registers.VoltaicDamageTypes;

public class ElectricityUtils {

	public static void electrecuteEntity(Entity entity, TransferPack transfer) {
		if (transfer.getVoltage() <= 960.0) {
			Ingredient insulatingItems = Ingredient.of(VoltaicTags.Items.INSULATES_PLAYER_FEET);
			for (ItemStack armor : entity.getArmorSlots()) {
				if (ItemUtils.isIngredientMember(insulatingItems, armor.getItem())) {
					float damage = (float) transfer.getAmps() / 10.0f;
					if (Math.random() < damage) {
						int integerDamage = (int) Math.max(1, damage);
						if (armor.getDamageValue() > armor.getMaxDamage() || armor.hurt(integerDamage, entity.level().random, null)) {
							armor.setCount(0);
						}
					}
					return;
				}
			}
		}
		entity.hurt(entity.damageSources().source(VoltaicDamageTypes.ELECTRICITY), (float) Math.min(9999, Math.max(0, transfer.getAmps())));
	}

	public static boolean isElectricReceiver(BlockEntity tile, Direction dir) {
		if (tile != null) {
			if (tile.getCapability(VoltaicCapabilities.CAPABILITY_ELECTRODYNAMIC_BLOCK, dir).orElse(CapabilityUtils.EMPTY_ELECTRO) != CapabilityUtils.EMPTY_ELECTRO || tile.getCapability(ForgeCapabilities.ENERGY, dir).orElse(CapabilityUtils.EMPTY_FE) != CapabilityUtils.EMPTY_FE) {
				return true;
			}
		}
		return false;
	}

	public static boolean isConductor(BlockEntity acceptor, GenericTileWire requesterWire) {
        if (acceptor instanceof GenericTileWire conductor) {
            return conductor.getCableType().isDefaultColor() || requesterWire.getCableType().isDefaultColor() || conductor.getWireColor() == requesterWire.getWireColor();
        }
        return false;
    }

	public static TransferPack receivePower(BlockEntity tile, Direction direction, TransferPack transfer, boolean debug) {
		if (tile == null) {
            return TransferPack.EMPTY;
        }

        ICapabilityElectrodynamic electro = tile.getCapability(VoltaicCapabilities.CAPABILITY_ELECTRODYNAMIC_BLOCK, direction).orElse(CapabilityUtils.EMPTY_ELECTRO);

        if (electro != CapabilityUtils.EMPTY_ELECTRO) {

            return electro.receivePower(transfer, debug);

        }

        IEnergyStorage fe = tile.getCapability(ForgeCapabilities.ENERGY, direction).orElse(CapabilityUtils.EMPTY_FE);

        if (fe != CapabilityUtils.EMPTY_FE) {
            TransferPack returner = TransferPack.joulesVoltage(fe.receiveEnergy((int) Math.min(Integer.MAX_VALUE, transfer.getJoules()), debug), transfer.getVoltage());
            if (transfer.getVoltage() > VoltaicCapabilities.DEFAULT_VOLTAGE) {
                Level world = tile.getLevel();
                BlockPos pos = tile.getBlockPos();
                world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                world.explode(null, pos.getX(), pos.getY(), pos.getZ(), (float) Math.log10(10 + transfer.getVoltage() / VoltaicCapabilities.DEFAULT_VOLTAGE), ExplosionInteraction.BLOCK);
            }
            return returner;
        }

        return TransferPack.EMPTY;
	}

}
