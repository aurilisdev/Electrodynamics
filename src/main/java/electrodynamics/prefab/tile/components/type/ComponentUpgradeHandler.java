package electrodynamics.prefab.tile.components.type;

import electrodynamics.common.item.ItemUpgrade;
import electrodynamics.common.item.subtype.SubtypeItemUpgrade;
import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyType;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.Component;
import electrodynamics.prefab.tile.components.ComponentType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class ComponentUpgradeHandler implements Component {

	public static final double BASIC_SPEED_BOOST = 1.5;
	public static final double BASIC_SPEED_POWER = 1.5;

	public static final double ADVANCED_SPEED_BOOST = 2.25;
	public static final double ADVANCED_SPEED_POWER = 2.25;

	public static final double SOLAR_CELL_MULT = 2.25;

	public static final double STATOR_MULT = 2.25;

	private GenericTile holder;

	private Property<Double> powerUsageMultiplier;

	private Property<Boolean> hasEjectorUpgrade;

	private Property<Boolean> hasInjectorUpgrade;

	private Property<Double> powerGenerationMultiplier;

	private Property<Integer> unbreakingLevel;

	private Property<Integer> fortuneLevel;

	private Property<Integer> silkTouchLevel;

	private Property<Boolean> hasExperienceUpgrade;

	private Property<Integer> rangeLevel;

	public ComponentUpgradeHandler(GenericTile holder) {
		this.holder = holder;

		powerUsageMultiplier = holder.property(new Property<>(PropertyType.Double, "powerusageupgradecomponent", 1.0));
		hasEjectorUpgrade = holder.property(new Property<>(PropertyType.Boolean, "hasejectorupgradecomponent", false));
		hasInjectorUpgrade = holder.property(new Property<>(PropertyType.Boolean, "hasinjectorupgradecomponent", false));
		powerGenerationMultiplier = holder.property(new Property<>(PropertyType.Double, "powergenupgradecomponent", 1.0));

		unbreakingLevel = holder.property(new Property<>(PropertyType.Integer, "unbreakinglevelupgradecomponent", 0));
		silkTouchLevel = holder.property(new Property<>(PropertyType.Integer, "silktouchlevelupgradecomponent", 0));
		fortuneLevel = holder.property(new Property<>(PropertyType.Integer, "fortunelevelupgradecomponent", 0));

		hasExperienceUpgrade = holder.property(new Property<>(PropertyType.Boolean, "experienceupgradecomponent", false));

		rangeLevel = holder.property(new Property<>(PropertyType.Integer, "rangelevelupgradecomponent", 1));

	}

	@Override
	public void holder(GenericTile holder) {
		this.holder = holder;
	}

	@Override
	public ComponentType getType() {
		return ComponentType.UpgradeHandler;
	}

	@Override
	public void loadFromNBT(CompoundTag nbt) {

	}

	@Override
	public void saveToNBT(CompoundTag nbt) {

	}

	public void serverTick(ComponentTickable tick) {

		if (!hasEjectorUpgrade.get() && !hasInjectorUpgrade.get()) {
			return;
		}

		ComponentInventory inv = holder.getComponent(ComponentType.Inventory);

		for (ItemStack stack : inv.getUpgradeContents()) {

			ItemUpgrade upgrade = (ItemUpgrade) stack.getItem();

			if (upgrade.subtype == SubtypeItemUpgrade.itemoutput && hasEjectorUpgrade.get()) {

			}

		}

	}

	public void onInventoryChange(int slot, ComponentInventory inv) {

	}

}
