package electrodynamics.common.recipe.recipeutils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;

import electrodynamics.Electrodynamics;
import electrodynamics.api.gas.Gas;
import electrodynamics.api.gas.GasStack;
import electrodynamics.common.tags.ElectrodynamicsTags;
import electrodynamics.registers.ElectrodynamicsRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.Ingredient;

/**
 * An extension of the Ingredient class for Gases
 * 
 * @author skip999
 *
 */
public class GasIngredient extends Ingredient {

	@Nonnull
	private List<GasStack> gasStacks;

	@Nullable
	private TagKey<Gas> tag;
	private double amount;
	private double temperature;
	private int pressure;

	public GasIngredient(GasStack stack) {
		super(Stream.empty());
		gasStacks = List.of(stack);
	}

	public GasIngredient(List<GasStack> gases) {
		super(Stream.empty());
		gasStacks = gases;
	}

	/**
	 * Constructor to be used if trying to load fluids from a ResourceLocation after the server has loaded
	 * 
	 * Do NOT call this method if you are trying to read from a JSON!
	 * 
	 * @param gasLoc
	 * @param amount
	 * @param pressure
	 * @param temperature
	 */
	public GasIngredient(ResourceLocation gasLoc, boolean isTag, double amount, double temperature, int pressure) {
		super(Stream.empty());
		if (isTag) {
			List<Gas> gases = ElectrodynamicsRegistries.gasRegistry().tags().getTag(ElectrodynamicsTags.Gases.create(gasLoc)).stream().toList();
			gasStacks = new ArrayList<>();
			for (Gas gas : gases) {
				gasStacks.add(new GasStack(gas, amount, temperature, pressure));
			}
		} else {
			gasStacks = List.of(new GasStack(ElectrodynamicsRegistries.gasRegistry().getValue(gasLoc), amount, temperature, pressure));
		}
		if (gasStacks.isEmpty()) {
			throw new UnsupportedOperationException("No gases returned from " + gasLoc);
		}

	}

	public GasIngredient(TagKey<Gas> tag, double amount, double temperature, int pressure) {
		super(Stream.empty());
		this.tag = tag;
		this.amount = amount;
		this.temperature = temperature;
		this.pressure = pressure;
		gasStacks = new ArrayList<>();
	}

	public GasIngredient(ResourceLocation tag, double amount, double temperature, int pressure) {
		this(ElectrodynamicsTags.Gases.create(tag), amount, temperature, pressure);
	}

	public static GasIngredient deserialize(JsonObject jsonObject) {
		Preconditions.checkArgument(jsonObject != null, "GasStack can only be deserialized from a JsonObject");
		try {
			if (GsonHelper.isValidNode(jsonObject, "gas")) {
				ResourceLocation resourceLocation = new ResourceLocation(GsonHelper.getAsString(jsonObject, "gas"));
				double amount = GsonHelper.getAsDouble(jsonObject, "amount");
				double temperature = GsonHelper.getAsDouble(jsonObject, "temp");
				int pressure = GsonHelper.getAsInt(jsonObject, "pressure");
				return new GasIngredient(resourceLocation, false, amount, temperature, pressure);
			}
			if (GsonHelper.isValidNode(jsonObject, "tag")) {
				ResourceLocation resourceLocation = new ResourceLocation(GsonHelper.getAsString(jsonObject, "tag"));
				double amount = GsonHelper.getAsDouble(jsonObject, "amount");
				double temperature = GsonHelper.getAsDouble(jsonObject, "temp");
				int pressure = GsonHelper.getAsInt(jsonObject, "pressure");
				return new GasIngredient(resourceLocation, amount, temperature, pressure);
			}
		} catch (Exception e) {
			Electrodynamics.LOGGER.info("Invalid Gas Type or Fluid amount entered in JSON file");
		}

		return null;
	}

	public boolean testGas(@Nullable GasStack gas, boolean checkTemperature, boolean checkPressure) {
		if (gas == null) {
			return false;
		}
		for (GasStack g : getMatchingGases()) {
			if (g.getAmount() >= gas.getAmount()) {
				if (g.isSameGas(gas)) {
					if (!checkTemperature && !checkPressure) {
						return true;
					}
					boolean sameTemp = g.isSameTemperature(gas);
					boolean samePres = g.isSamePressure(gas);
					if (!checkTemperature) {
						return samePres;
					}
					if (checkPressure) {
						return sameTemp && samePres;
					}
					return sameTemp;
				}
			}
		}
		return false;

	}

	public List<GasStack> getMatchingGases() {

		if (gasStacks.isEmpty() && tag != null) {
			ElectrodynamicsRegistries.gasRegistry().tags().getTag(tag).forEach(h -> {
				gasStacks.add(new GasStack(h, amount, temperature, pressure));
			});
		}

		return gasStacks;

	}

	public static GasIngredient read(FriendlyByteBuf input) {
		List<GasStack> stacks = new ArrayList<>();
		int count = input.readInt();
		for (int i = 0; i < count; i++) {
			stacks.add(GasStack.readFromBuffer(input));
		}
		return new GasIngredient(stacks);
	}

	public static GasIngredient[] readList(FriendlyByteBuf buffer) {
		int length = buffer.readInt();
		GasIngredient[] ings = new GasIngredient[length];
		for (int i = 0; i < length; i++) {
			ings[i] = read(buffer);
		}
		return ings;
	}

	public void write(FriendlyByteBuf output) {
		gasStacks = getMatchingGases();
		output.writeInt(gasStacks.size());
		for (GasStack stack : gasStacks) {
			stack.writeToBuffer(output);
		}
	}

	public static void writeList(FriendlyByteBuf buffer, List<GasIngredient> list) {
		buffer.writeInt(list.size());
		for (GasIngredient ing : list) {
			ing.write(buffer);
		}
	}

	public GasStack getGasStack() {
		return getMatchingGases().get(0);
	}

}
