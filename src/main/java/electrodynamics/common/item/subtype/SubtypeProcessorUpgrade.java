package electrodynamics.common.item.subtype;

import java.util.function.BiConsumer;

import electrodynamics.api.ISubtype;
import electrodynamics.common.tile.TileBatteryBox;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;

public enum SubtypeProcessorUpgrade implements ISubtype {
    basiccapacity((holder, processor) -> {
	if (holder instanceof TileBatteryBox box) {
	    box.currentCapacityMultiplier = Math.min(box.currentCapacityMultiplier * 1.5, Math.pow(2.25, 3));
	    box.currentVoltageMultiplier = Math.max(box.currentVoltageMultiplier, 2);
	}
    }, 3),
    basicspeed((holder, processor) -> {
	if (processor != null) {
	    processor.operatingSpeed = Math.min(processor.operatingSpeed * 1.5, Math.pow(2.25, 3));
	}
    }, 3),
    advancedcapacity((holder, processor) -> {
	if (holder instanceof TileBatteryBox box) {
	    box.currentCapacityMultiplier = Math.min(box.currentCapacityMultiplier * 2.25, Math.pow(2.25, 3));
	    box.currentVoltageMultiplier = Math.max(box.currentVoltageMultiplier, 4);
	}
    }, 3),
    advancedspeed((holder, processor) -> {
	if (processor != null) {
	    processor.operatingSpeed = Math.min(processor.operatingSpeed * 2.25, Math.pow(2.25, 3));
	}
    }, 3);

    public final BiConsumer<GenericTile, ComponentProcessor> applyUpgrade;
    public final int maxSize;

    SubtypeProcessorUpgrade(BiConsumer<GenericTile, ComponentProcessor> applyUpgrade, int maxSize) {
	this.applyUpgrade = applyUpgrade;
	this.maxSize = maxSize;
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
