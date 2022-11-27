package electrodynamics.common.fluid.types;

import java.util.function.Consumer;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;

public class SimpleWaterBasedFluidType extends FluidType {
	private String modId;
	private String fluidName;
	private int color;

	public SimpleWaterBasedFluidType(String modId, String fluidName, int color) {
		super(FluidType.Properties.create().descriptionId("fluid." + modId + "." + fluidName).fallDistanceModifier(0F).canExtinguish(true).canConvertToSource(true).supportsBoating(true).sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL).sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY).sound(SoundActions.FLUID_VAPORIZE, SoundEvents.FIRE_EXTINGUISH).canHydrate(true));
		this.modId = modId;
		this.fluidName = fluidName;
		this.color = color;
	}

	public SimpleWaterBasedFluidType(String modId, String fluidName) {
		this(modId, fluidName, 0xFF3F76E4);
	}

	@Override
	public @Nullable BlockPathTypes getBlockPathType(FluidState state, BlockGetter level, BlockPos pos, @Nullable Mob mob, boolean canFluidLog) {
		return canFluidLog ? super.getBlockPathType(state, level, pos, mob, true) : null;
	}

	@Override
	public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
		consumer.accept(new IClientFluidTypeExtensions() {

			@Override
			public ResourceLocation getStillTexture() {
				return new ResourceLocation(modId + ":fluid/" + fluidName.replaceFirst("fluid", ""));
			}

			@Override
			public ResourceLocation getFlowingTexture() {
				return new ResourceLocation(modId + ":fluid/" + fluidName.replaceFirst("fluid", ""));
			}

			@Override
			public int getTintColor() {
				return color;
			}

			@Override
			public int getTintColor(FluidState state, BlockAndTintGetter getter, BlockPos pos) {
				return getTintColor();
			}
		});
	}
}
