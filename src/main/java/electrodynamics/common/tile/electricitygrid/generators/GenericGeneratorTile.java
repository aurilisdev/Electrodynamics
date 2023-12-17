package electrodynamics.common.tile.electricitygrid.generators;

import javax.annotation.Nullable;

import org.apache.commons.lang3.ArrayUtils;

import electrodynamics.api.electricity.generator.IElectricGenerator;
import electrodynamics.common.item.ItemUpgrade;
import electrodynamics.common.item.subtype.SubtypeItemUpgrade;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityType;

public abstract class GenericGeneratorTile extends GenericTile implements IElectricGenerator {

	public final double upgradeMultiplier;
	@Nullable
	public final SubtypeItemUpgrade[] validMultipliers;

	protected GenericGeneratorTile(TileEntityType<?> tileEntityTypeIn, double multiplier, SubtypeItemUpgrade... itemUpgrades) {
		super(tileEntityTypeIn);
		upgradeMultiplier = multiplier;
		validMultipliers = itemUpgrades;
	}

	@Override
	public void onInventoryChange(ComponentInventory inv, int slot) {
		super.onInventoryChange(inv, slot);

		if (!inv.getUpgradeContents().isEmpty() && (slot >= inv.getUpgradeSlotStartIndex() || slot == -1) && validMultipliers != null) {
			setMultiplier(1);
			for (ItemStack stack : inv.getUpgradeContents()) {
				if (!stack.isEmpty() && stack.getItem() instanceof ItemUpgrade) {
					ItemUpgrade upgrade = (ItemUpgrade) stack.getItem();
					if (upgrade.subtype.isEmpty) {
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

}