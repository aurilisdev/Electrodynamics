package electrodynamics.common.tile;

import com.mojang.math.Vector3f;

import electrodynamics.DeferredRegisters;
import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.common.inventory.container.tile.ContainerMineralWasher;
import electrodynamics.common.recipe.ElectrodynamicsRecipeInit;
import electrodynamics.common.settings.Constants;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerMulti;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class TileMineralWasher extends GenericTile {
	public static final int MAX_TANK_CAPACITY = 5000;

	public TileMineralWasher(BlockPos worldPosition, BlockState blockState) {
		super(DeferredRegisters.TILE_MINERALWASHER.get(), worldPosition, blockState);
		addComponent(new ComponentTickable().tickClient(this::tickClient));
		addComponent(new ComponentDirection());
		addComponent(new ComponentPacketHandler());
		addComponent(new ComponentElectrodynamic(this).relativeInput(Direction.NORTH).voltage(ElectrodynamicsCapabilities.DEFAULT_VOLTAGE * 4)
				.maxJoules(Constants.MINERALWASHER_USAGE_PER_TICK * 10));
		addComponent(((ComponentFluidHandlerMulti) new ComponentFluidHandlerMulti(this).relativeInput(Direction.values()))
				.setAddFluidsValues(ElectrodynamicsRecipeInit.MINERAL_WASHER_TYPE, MAX_TANK_CAPACITY, true, true));
		addComponent(new ComponentInventory(this).size(6).relativeSlotFaces(0, Direction.values()).inputs(1).bucketInputs(1).bucketOutputs(1)
				.upgrades(3).processors(1).processorInputs(1).valid(machineValidator()).shouldSendInfo());
		addComponent(new ComponentProcessor(this).setProcessorNumber(0).usage(Constants.MINERALWASHER_USAGE_PER_TICK)
				.canProcess(component -> component.outputToPipe().consumeBucket().dispenseBucket().canProcessFluidItem2FluidRecipe(component,
						ElectrodynamicsRecipeInit.MINERAL_WASHER_TYPE))
				.process(component -> component.processFluidItem2FluidRecipe(component)).requiredTicks(Constants.MINERALWASHER_REQUIRED_TICKS));
		addComponent(new ComponentContainerProvider("container.mineralwasher")
				.createMenu((id, player) -> new ContainerMineralWasher(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
	}

	@Override
	public AABB getRenderBoundingBox() {
		return super.getRenderBoundingBox().inflate(1);
	}

	protected void tickClient(ComponentTickable tickable) {
		if (this.<ComponentProcessor>getComponent(ComponentType.Processor).operatingTicks > 0) {
			if (level.random.nextDouble() < 0.15) {
				level.addParticle(ParticleTypes.SMOKE, worldPosition.getX() + level.random.nextDouble(),
						worldPosition.getY() + level.random.nextDouble() * 0.4 + 0.5, worldPosition.getZ() + level.random.nextDouble(), 0.0D, 0.0D,
						0.0D);
			}
			for (int i = 0; i < 2; i++) {
				double x = 0.5 + level.random.nextDouble() * 0.4 - 0.2;
				double y = 0.5 + level.random.nextDouble() * 0.3 - 0.15;
				double z = 0.5 + level.random.nextDouble() * 0.4 - 0.2;
				level.addParticle(new DustParticleOptions(new Vector3f(1f, 1f, 0), 1), worldPosition.getX() + x, worldPosition.getY() + y,
						worldPosition.getZ() + z, level.random.nextDouble() * 0.2 - 0.1, level.random.nextDouble() * 0.2 - 0.1,
						level.random.nextDouble() * 0.2 - 0.1);
			}
		}
	}

}
