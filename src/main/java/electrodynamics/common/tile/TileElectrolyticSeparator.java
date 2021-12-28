package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.SoundRegister;
import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.api.sound.SoundAPI;
import electrodynamics.common.inventory.container.tile.ContainerElectrolyticSeparator;
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
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.state.BlockState;

public class TileElectrolyticSeparator extends GenericTile {

	public static final int MAX_TANK_CAPACITY = 5000;
	public long clientTicks = 0;
	
	
	public TileElectrolyticSeparator(BlockPos worldPos, BlockState blockState) {
		super(DeferredRegisters.TILE_ELECTROLYTICSEPARATOR.get(), worldPos, blockState);
		addComponent(new ComponentTickable().tickClient(this::tickClient));
		addComponent(new ComponentDirection());
		addComponent(new ComponentPacketHandler());
		addComponent(new ComponentElectrodynamic(this).relativeInput(Direction.NORTH).voltage(ElectrodynamicsCapabilities.DEFAULT_VOLTAGE * 2)
				.maxJoules(Constants.ELECTROLYTICSEPARATOR_USAGE_PER_TICK * 10));
		addComponent(((ComponentFluidHandlerMulti) new ComponentFluidHandlerMulti(this).relativeOutput(Direction.EAST, Direction.WEST)
				.relativeInput(Direction.SOUTH))
				.setAddFluidsValues(ElectrodynamicsRecipeInit.ELECTROLYTIC_SEPERATOR_TYPE, MAX_TANK_CAPACITY, true, true));
		addComponent(new ComponentInventory(this).size(6).bucketInputs(1).bucketOutputs(2).upgrades(3).valid(machineValidator()));
		addComponent(new ComponentProcessor(this).setProcessorNumber(0)
				.canProcess(component -> component.outputToPipe(component).consumeBucket().dispenseBucket().canProcessFluid2FluidRecipe(component,
						ElectrodynamicsRecipeInit.ELECTROLYTIC_SEPERATOR_TYPE))
				.process(component -> component.processFluid2FluidRecipe(component)).usage(Constants.ELECTROLYTICSEPARATOR_USAGE_PER_TICK)
				.requiredTicks(Constants.ELECTROLYTICSEPARATOR_REQUIRED_TICKS));
		addComponent(new ComponentContainerProvider("container.electrolyticseparator")
				.createMenu((id, player) -> new ContainerElectrolyticSeparator(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
	}
	
	protected void tickClient(ComponentTickable tickable) {
		boolean running = this.<ComponentProcessor>getComponent(ComponentType.Processor).operatingTicks > 0;
		if (running) {
			if(clientTicks >= 40 ) {
				clientTicks = 0;
				SoundAPI.playSound(SoundRegister.SOUND_ELECTROLYTICSEPARATOR.get(), SoundSource.BLOCKS, 5, .75f, worldPosition);
			}
			if (level.random.nextDouble() < 0.15) {
				level.addParticle(ParticleTypes.SMOKE, worldPosition.getX() + level.random.nextDouble(),
						worldPosition.getY() + level.random.nextDouble() * 0.4 + 0.5, worldPosition.getZ() + level.random.nextDouble(), 0.0D, 0.0D,
						0.0D);
			}
			clientTicks++;
		}
	}

}
