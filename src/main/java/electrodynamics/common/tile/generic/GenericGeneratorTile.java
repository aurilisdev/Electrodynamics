package electrodynamics.common.tile.generic;

import javax.annotation.Nullable;

import org.apache.commons.lang3.ArrayUtils;

import electrodynamics.api.electricity.generator.IElectricGenerator;
import electrodynamics.common.item.ItemUpgrade;
import electrodynamics.common.item.subtype.SubtypeItemUpgrade;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class GenericGeneratorTile extends GenericTile implements IElectricGenerator {

	public final double upgradeMultiplier;
	@Nullable
	public final SubtypeItemUpgrade[] validMultipliers;

	public GenericGeneratorTile(BlockEntityType<?> tileEntityTypeIn, BlockPos worldPos, BlockState blockState, double multiplier, SubtypeItemUpgrade... itemUpgrades) {
		super(tileEntityTypeIn, worldPos, blockState);
		upgradeMultiplier = multiplier;
		validMultipliers = itemUpgrades;
	}

	@Override
	public void onInventoryChange(ComponentInventory inv, int slot) {
		super.onInventoryChange(inv, slot);

		if (inv.getUpgradeContents().size() > 0 && (slot >= inv.getUpgradeSlotStartIndex() || slot == -1) && validMultipliers != null) {
			setMultiplier(1);
			for (ItemStack stack : inv.getUpgradeContents()) {
				if (!stack.isEmpty() && stack.getItem() instanceof ItemUpgrade upgrade && upgrade.subtype.isEmpty) {
					for (int i = 0; i < stack.getCount(); i++) {
						if (ArrayUtils.contains(validMultipliers, upgrade.subtype)) {
							setMultiplier(upgradeMultiplier);
						}
					}
				}
			}
		}

	}

}
