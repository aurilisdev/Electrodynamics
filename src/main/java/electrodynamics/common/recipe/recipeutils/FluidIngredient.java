package electrodynamics.common.recipe.recipeutils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.gson.JsonObject;

import electrodynamics.Electrodynamics;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags.IOptionalNamedTag;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Extension of Ingredient that adds Fluid compatibility
 * 
 * @author skip999
 *
 */
public class FluidIngredient extends Ingredient {

	private static final List<FluidStack> EMPTY_FLUID_LIST = Lists.newArrayList(new FluidStack(Fluids.EMPTY, 1000));

	@Nonnull
	private List<FluidStack> fluidStacks;

	@Nullable
	public IOptionalNamedTag<Fluid> tag;
	private int amount;

	public FluidIngredient(FluidStack fluidStack) {
		super(Stream.empty());
		fluidStacks = new ArrayList<>();
		fluidStacks.add(fluidStack);
		amount = fluidStack.getAmount();
	}

	public FluidIngredient(List<FluidStack> fluidStack) {
		super(Stream.empty());
		fluidStacks = fluidStack;
		amount = fluidStack.get(0).getAmount();
	}

	/**
	 * Call this one if you're trying to get a Tag once loaded on the server or are trying to get a specific fluid
	 * 
	 * DO NOT call this one if trying to load a fluid from a JSON file!
	 * 
	 * @param resourceLocation
	 * @param amount
	 * @param isTag
	 */
	public FluidIngredient(ResourceLocation resourceLocation, int amount, boolean isTag) {
		super(Stream.empty());
		if (isTag) {
			// Don't know ifi can use FluidTags.create(resourceLocation) all the time but we shall see.
			fluidStacks = new ArrayList<>();
			ForgeRegistries.FLUIDS.forEach(fluid -> {
				if (fluid.is(tag)) {
					fluidStacks.add(new FluidStack(fluid, amount));
				}
			});
		} else {
			List<FluidStack> fluids = new ArrayList<>();
			fluids.add(new FluidStack(ForgeRegistries.FLUIDS.getValue(resourceLocation), amount));
			fluidStacks = fluids;
		}
		if (fluidStacks.isEmpty()) {
			throw new UnsupportedOperationException("No fluids returned from tag " + resourceLocation);
		}
	}

	/**
	 * Constructor is designed to defer loading tag value until ingredient is referenced
	 * 
	 * @param resource
	 * @param amount
	 */
	public FluidIngredient(ResourceLocation resource, int amount) {
		this(FluidTags.createOptional(resource), amount);
	}

	public FluidIngredient(IOptionalNamedTag<Fluid> tag, int amount) {
		super(Stream.empty());
		this.tag = tag;
		this.amount = amount;
		fluidStacks = new ArrayList<>();

	}

	public static FluidIngredient deserialize(JsonObject jsonObject) {
		Preconditions.checkArgument(jsonObject != null, "FluidStack can only be deserialized from a JsonObject");
		try {
			if (JSONUtils.isValidNode(jsonObject, "fluid")) {
				ResourceLocation resourceLocation = new ResourceLocation(JSONUtils.getAsString(jsonObject, "fluid"));
				int amount = JSONUtils.getAsInt(jsonObject, "amount");
				return new FluidIngredient(resourceLocation, amount, false);
			} else if (JSONUtils.isValidNode(jsonObject, "tag")) {
				ResourceLocation resourceLocation = new ResourceLocation(JSONUtils.getAsString(jsonObject, "tag"));
				int amount = JSONUtils.getAsInt(jsonObject, "amount");
				// special constructor call for JSONs
				return new FluidIngredient(resourceLocation, amount);
			}
		} catch (Exception e) {
			Electrodynamics.LOGGER.info("Invalid Fluid Type or Fluid amount entered in JSON file");
		}

		return null;
	}

	public int getAmount() {
		return amount;
	}

	public boolean testFluid(@Nullable FluidStack t) {
		if (t != null) {
			for (FluidStack stack : getMatchingFluids()) {
				if (t.getAmount() >= stack.getAmount()) {
					if (t.getFluid().isSame(stack.getFluid())) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public static FluidIngredient read(PacketBuffer input) {

		if (input.readBoolean()) {

			return new FluidIngredient(input.readResourceLocation(), input.readInt());

		} else {

			List<FluidStack> stacks = new ArrayList<>();
			int count = input.readInt();
			for (int i = 0; i < count; i++) {
				stacks.add(input.readFluidStack());
			}
			return new FluidIngredient(stacks);

		}

	}

	public static FluidIngredient[] readList(PacketBuffer buffer) {
		int length = buffer.readInt();
		FluidIngredient[] ings = new FluidIngredient[length];
		for (int i = 0; i < length; i++) {
			ings[i] = read(buffer);
		}
		return ings;
	}

	public void write(PacketBuffer output) {

		output.writeBoolean(tag != null);

		if (tag != null) {

			output.writeResourceLocation(tag.getName());
			output.writeInt(amount);

		} else {

			fluidStacks = getMatchingFluids();
			output.writeInt(fluidStacks.size());
			for (FluidStack stack : fluidStacks) {
				output.writeFluidStack(stack);
			}

		}

	}

	public static void writeList(PacketBuffer buffer, List<FluidIngredient> list) {
		buffer.writeInt(list.size());
		for (FluidIngredient ing : list) {
			ing.write(buffer);
		}
	}

	public List<FluidStack> getMatchingFluids() {
		if (fluidStacks.isEmpty() && tag != null) {
			ForgeRegistries.FLUIDS.forEach(fluid -> {
				if (fluid.is(tag)) {
					fluidStacks.add(new FluidStack(fluid, amount));
				}
			});

			// ForgeRegistries.FLUIDS.tags().getTag(tag).forEach(h -> {
			// fluidStacks.add(new FluidStack(h, amount));
			// });
		}
		return fluidStacks.isEmpty() ? EMPTY_FLUID_LIST : fluidStacks;
	}

	public FluidStack getFluidStack() {
		return getMatchingFluids().isEmpty() ? EMPTY_FLUID_LIST.get(0) : getMatchingFluids().get(0);
	}

}
