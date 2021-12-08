package electrodynamics.common.item.subtype;

import java.util.function.BiConsumer;

import electrodynamics.api.ISubtype;
import electrodynamics.common.tile.TileBatteryBox;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;

public enum SubtypeProcessorUpgrade implements ISubtype {
    basiccapacity((holder, processor) -> {
	if (holder instanceof TileBatteryBox box) {
	    box.currentCapacityMultiplier *= 1.5;
	    box.currentVoltageMultiplier = Math.max(box.currentVoltageMultiplier, 2);
	}
    }),
    basicspeed((holder, processor) -> {
	if (processor != null) {
	    processor.operatingSpeed *= 1.5;
	}
    }),
    advancedcapacity((holder, processor) -> {
	if (holder instanceof TileBatteryBox box) {
	    box.currentCapacityMultiplier *= 2.25;
	    box.currentVoltageMultiplier = Math.max(box.currentVoltageMultiplier, 4);
	}
    }),
    advancedspeed((holder, processor) -> {
	if (processor != null) {
	    processor.operatingSpeed *= 2.25;
	}
    });

    public final BiConsumer<GenericTile, ComponentProcessor> applyUpgrade;

    SubtypeProcessorUpgrade(BiConsumer<GenericTile, ComponentProcessor> applyUpgrade) {
	this.applyUpgrade = applyUpgrade;
    }

    @Override
    public String tag() {
	return "processorupgrade" + name();
    }

    @Override
    public String forgeTag() {
	return "processorupgrade/" + name();
    }

    @Override
    public boolean isItem() {
	return true;
    }
}
