package electrodynamics.common.item.subtype;

import java.util.function.Consumer;

import electrodynamics.api.ISubtype;
import electrodynamics.common.tile.TileBatteryBox;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;

public enum SubtypeProcessorUpgrade implements ISubtype {
    basiccapacity(holder -> {
	if (holder instanceof TileBatteryBox box) {
	    box.currentCapacityMultiplier *= 1.5;
	    box.currentVoltageMultiplier = Math.max(box.currentVoltageMultiplier, 2);
	}
    }),
    basicspeed(processor -> {
	if (processor.getComponent(ComponentType.Processor)instanceof ComponentProcessor component) {
	    component.operatingSpeed *= 1.5;
	}
    }),
    advancedcapacity(holder -> {
	if (holder instanceof TileBatteryBox box) {
	    box.currentCapacityMultiplier *= 2.25;
	    box.currentVoltageMultiplier = Math.max(box.currentVoltageMultiplier, 4);
	}
    }),
    advancedspeed(processor -> {
	if (processor.getComponent(ComponentType.Processor)instanceof ComponentProcessor component) {
	    component.operatingSpeed *= 2.25;
	}
    });

    public final Consumer<GenericTile> applyUpgrade;

    SubtypeProcessorUpgrade(Consumer<GenericTile> applyUpgrade) {
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
