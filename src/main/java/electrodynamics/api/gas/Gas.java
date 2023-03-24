package electrodynamics.api.gas;

import java.util.function.Supplier;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import electrodynamics.registers.ElectrodynamicsGases;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

/**
 * Basic implementation of a Gas mirroring certain aspects of fluids
 * 
 * Gases are not designed to be place hence how lightweight it is
 * 
 * @author skip999
 *
 */
public class Gas {
	
	private final Supplier<Item> container;
	private final TagKey<Gas> tag;
	private final Component description;
	private final double condensationTemp; // Degrees Kelvin
	
	public Gas(Supplier<Item> container, @Nullable TagKey<Gas> tag, Component description, double condensationTemp) {
		this.container = container;
		this.tag = tag;
		this.description = description;
		this.condensationTemp = condensationTemp;
	}
	
	public Component getDescription() {
		return description;
	}
	
	public Item getContainer() {
		return container.get();
	}
	
	public boolean is(@Nonnull TagKey<Gas> tag) {
		return this.tag == null ? false : this.tag.equals(tag);
	}
	
	public boolean isEmpty() {
		return this == empty();
	}
	
	public double getCondensationTemp() {
		return condensationTemp;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Gas other) {
			return other == this;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return description.getString() + ",\tcondensation temp : " + condensationTemp + " K";
	}
	
	public static Gas empty() {
		return ElectrodynamicsGases.EMPTY.get();
	}

}
