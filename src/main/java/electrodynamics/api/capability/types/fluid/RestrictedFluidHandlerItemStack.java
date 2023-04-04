package electrodynamics.api.capability.types.fluid;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.Nullable;

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

	@Nullable
	private final List<ResourceLocation> tags;
	@Nullable
	private final List<Fluid> fluids;

	public RestrictedFluidHandlerItemStack(ItemStack container, ItemStack emptyContainer, int capacity, @Nullable Pair<List<ResourceLocation>, List<Fluid>> whitelistedFluids) {
		super(container, emptyContainer, capacity);
		if(whitelistedFluids != null) {
			tags = new ArrayList<>();
			tags.addAll(whitelistedFluids.getFirst());

			fluids = new ArrayList<>();
			fluids.addAll(whitelistedFluids.getSecond());

		} else {
			tags = null;
			fluids = null;
		}
	}

	@Override
	public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
		if(tags == null && fluids == null) {
			return super.isFluidValid(tank, stack);
		}
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
				if (!fluid.builtInRegistryHolder().key().location().toString().toLowerCase(Locale.ROOT).contains("flow")) {
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
