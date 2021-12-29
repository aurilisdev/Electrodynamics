package electrodynamics.common.tile;

import electrodynamics.DeferredRegisters;
import electrodynamics.SoundRegister;
import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.api.sound.SoundAPI;
import electrodynamics.common.inventory.container.tile.ContainerElectrolyticSeparator;
import electrodynamics.common.network.FluidUtilities;
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
import electrodynamics.prefab.tile.components.utils.AbstractFluidHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class TileElectrolyticSeparator extends GenericTile {

	public static final int MAX_TANK_CAPACITY = 5000;
	public long clientTicks = 0;
	
	private static final Direction OXYGEN_DIRECTION = Direction.EAST;
	private static final Direction HYDROGEN_DIRECTION = Direction.WEST;
	
	public TileElectrolyticSeparator(BlockPos worldPos, BlockState blockState) {
		super(DeferredRegisters.TILE_ELECTROLYTICSEPARATOR.get(), worldPos, blockState);
		addComponent(new ComponentTickable().tickClient(this::tickClient).tickServer(this::tickServer));
		addComponent(new ComponentDirection());
		addComponent(new ComponentPacketHandler());
		addComponent(new ComponentElectrodynamic(this).relativeInput(Direction.NORTH).voltage(ElectrodynamicsCapabilities.DEFAULT_VOLTAGE * 2)
				.maxJoules(Constants.ELECTROLYTICSEPARATOR_USAGE_PER_TICK * 10));
		addComponent(((ComponentFluidHandlerMulti) new ComponentFluidHandlerMulti(this).relativeOutput(OXYGEN_DIRECTION, HYDROGEN_DIRECTION)
				.relativeInput(Direction.SOUTH))
				.setAddFluidsValues(ElectrodynamicsRecipeInit.ELECTROLYTIC_SEPERATOR_TYPE, MAX_TANK_CAPACITY, true, true));
		addComponent(new ComponentInventory(this).size(6).bucketInputs(1).bucketOutputs(2).upgrades(3).valid(machineValidator()));
		addComponent(new ComponentProcessor(this).setProcessorNumber(0)
				.canProcess(component -> component.consumeBucket().dispenseBucket().canProcessFluid2FluidRecipe(component,
						ElectrodynamicsRecipeInit.ELECTROLYTIC_SEPERATOR_TYPE))
				.process(component -> component.processFluid2FluidRecipe(component)).usage(Constants.ELECTROLYTICSEPARATOR_USAGE_PER_TICK)
				.requiredTicks(Constants.ELECTROLYTICSEPARATOR_REQUIRED_TICKS));
		addComponent(new ComponentContainerProvider("container.electrolyticseparator")
				.createMenu((id, player) -> new ContainerElectrolyticSeparator(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
	}
	
	public void tickServer(ComponentTickable tickable) {
		//ensures only one fluid per output
		AbstractFluidHandler<?> handler = getComponent(ComponentType.FluidHandler);
		FluidTank oxygen = handler.getOutputTanks()[0];
		FluidTank hydrogen = handler.getOutputTanks()[1];
		FluidUtilities.outputToPipe(this, new FluidTank[] {oxygen}, OXYGEN_DIRECTION);
		FluidUtilities.outputToPipe(this, new FluidTank[] {hydrogen}, HYDROGEN_DIRECTION);
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
