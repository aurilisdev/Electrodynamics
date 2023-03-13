package electrodynamics.api.capability.types.fluid;

import java.util.ArrayList;
import java.util.List;

import com.mojang.datafixers.util.Pair;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

public class RestrictedFluidHandlerItemStack extends FluidHandlerItemStack.SwapEmpty {

	private final List<ResourceLocation> tags;
	private final List<Fluid> fluids;

	public RestrictedFluidHandlerItemStack(ItemStack container, ItemStack emptyContainer, int capacity, Pair<List<ResourceLocation>, List<Fluid>> whitelistedFluids) {
		super(container, emptyContainer, capacity);
		tags = new ArrayList<>();
		tags.addAll(whitelistedFluids.getFirst());

		fluids = new ArrayList<>();
		fluids.addAll(whitelistedFluids.getSecond());

	}

	@Override
	public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
		// check tags first
		for (ResourceLocation loc : tags) {
			for (Fluid fluid : ForgeRegistries.FLUIDS.tags().getTag(FluidTags.create(loc)).stream().toList()) {
				// filter out flowing fluids
				if (fluid.builtInRegistryHolder().key().location().toString().toLowerCase().contains("flow")) {
					return false;
				}
				if (fluid.isSame(stack.getFluid())) {
					return true;
				}
			}
		}
		// next check specific fluids
		for (Fluid fluid : fluids) {
			if (fluid.isSame(stack.getFluid())) {
				return true;
			}
		}

		// next check specific fluids
		for (Fluid fluid : fluids) {
			if (fluid.isSame(stack.getFluid())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean canFillFluidType(FluidStack fluid) {
		return isFluidValid(0, fluid);
	}

	@Override
	public int fill(FluidStack resource, FluidAction doFill) {
		if (canFillFluidType(resource)) {
			return super.fill(resource, doFill);
		}
		return 0;
	}

	public void fillInit(FluidStack resource) {
		setFluid(resource);
	}

	public ArrayList<Fluid> getWhitelistedFluids() {
		ArrayList<Fluid> valid = new ArrayList<>();
		ArrayList<Fluid> unique = new ArrayList<>();
		for (ResourceLocation loc : tags) {
			List<Fluid> fluids = ForgeRegistries.FLUIDS.tags().getTag(FluidTags.create(loc)).stream().toList();
			for (Fluid fluid : fluids) {
				if (!fluid.builtInRegistryHolder().key().location().toString().toLowerCase().contains("flow")) {
					valid.add(fluid);
				}
			}
		}
		for (Fluid fluid : fluids) {
			if (!valid.contains(fluid)) {
				unique.add(fluid);
			}
		}
		valid.addAll(unique);

		return valid;
	}

}
