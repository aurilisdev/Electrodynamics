package electrodynamics.common.eventbus;

import java.util.HashMap;
import java.util.HashSet;

import electrodynamics.common.block.connect.BlockWire;
import electrodynamics.common.block.subtype.SubtypeWire;
import net.neoforged.bus.api.Event;
import net.neoforged.fml.event.IModBusEvent;

public class RegisterWiresEvent extends Event implements IModBusEvent {

    private final HashSet<BlockWire> wires = new HashSet<>();

    public void registerWire(BlockWire wire) {
	wires.add(wire);
    }

    public void process() {
	for (BlockWire wire : wires) {
	    SubtypeWire.WIRE_MATERIALS.add(wire.wire.getWireMaterial());
	    SubtypeWire.WireColor.WIRE_COLORS.add(wire.wire.getWireColor());
	    var materialmap = SubtypeWire.WIRES.getOrDefault(wire.wire.getWireMaterial(), new HashMap<>());
	    var insulationmap = materialmap.getOrDefault(wire.wire.getInsulation(), new HashMap<>());
	    var classmap = insulationmap.getOrDefault(wire.wire.getWireClass(), new HashMap<>());
	    classmap.put(wire.wire.getWireColor(), wire);
	    insulationmap.put(wire.wire.getWireClass(), classmap);
	    materialmap.put(wire.wire.getInsulation(), insulationmap);
	    SubtypeWire.WIRES.put(wire.wire.getWireMaterial(), materialmap);
	}
    }

}
