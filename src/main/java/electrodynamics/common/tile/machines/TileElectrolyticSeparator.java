package electrodynamics.common.tile.machines;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerElectrolyticSeparator;
import electrodynamics.common.network.FluidUtilities;
import electrodynamics.common.recipe.ElectrodynamicsRecipeInit;
import electrodynamics.prefab.sound.SoundBarrierMethods;
import electrodynamics.prefab.sound.utils.ITickableSound;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerMulti;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentInventory.InventoryBuilder;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.tile.types.GenericFluidTile;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import electrodynamics.registers.ElectrodynamicsSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class TileElectrolyticSeparator extends GenericFluidTile implements ITickableSound {

	public static final int MAX_TANK_CAPACITY = 5000;
	public long clientTicks = 0;

	private static final Direction OXYGEN_DIRECTION = Direction.EAST;
	private static final Direction HYDROGEN_DIRECTION = Direction.WEST;

	private boolean isSoundPlaying = false;

	public TileElectrolyticSeparator(BlockPos worldPos, BlockState blockState) {
		super(ElectrodynamicsBlockTypes.TILE_ELECTROLYTICSEPARATOR.get(), worldPos, blockState);
		addComponent(new ComponentTickable(this).tickClient(this::tickClient).tickServer(this::tickServer));
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentElectrodynamic(this, false, true).setInputDirections(Direction.SOUTH).voltage(ElectrodynamicsCapabilities.DEFAULT_VOLTAGE * 2));
		addComponent(new ComponentFluidHandlerMulti(this).setOutputDirections(OXYGEN_DIRECTION, HYDROGEN_DIRECTION).setInputDirections(Direction.NORTH).setTanks(1, 2, new int[] { MAX_TANK_CAPACITY }, new int[] { MAX_TANK_CAPACITY, MAX_TANK_CAPACITY }).setRecipeType(ElectrodynamicsRecipeInit.ELECTROLYTIC_SEPERATOR_TYPE.get()));
		addComponent(new ComponentInventory(this, InventoryBuilder.newInv().bucketInputs(1).gasOutputs(2).upgrades(3)).validUpgrades(ContainerElectrolyticSeparator.VALID_UPGRADES).valid(machineValidator()));
		addComponent(new ComponentProcessor(this).canProcess(component -> component.consumeBucket().dispenseBucket().canProcessFluid2FluidRecipe(component, ElectrodynamicsRecipeInit.ELECTROLYTIC_SEPERATOR_TYPE.get())).process(component -> component.processFluid2FluidRecipe(component)));
		addComponent(new ComponentContainerProvider(SubtypeMachine.electrolyticseparator, this).createMenu((id, player) -> new ContainerElectrolyticSeparator(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));
	}

	public void tickServer(ComponentTickable tickable) {
		// ensures only one fluid per output
		ComponentFluidHandlerMulti handler = getComponent(IComponentType.FluidHandler);
		FluidUtilities.outputToPipe(this, new FluidTank[] { handler.getOutputTanks()[0] }, OXYGEN_DIRECTION);
		FluidUtilities.outputToPipe(this, new FluidTank[] { handler.getOutputTanks()[1] }, HYDROGEN_DIRECTION);
	}

	protected void tickClient(ComponentTickable tickable) {
		if (!shouldPlaySound()) {
			return;
		}
		if (level.random.nextDouble() < 0.15) {
			level.addParticle(ParticleTypes.SMOKE, worldPosition.getX() + level.random.nextDouble(), worldPosition.getY() + level.random.nextDouble() * 0.4 + 0.5, worldPosition.getZ() + level.random.nextDouble(), 0.0D, 0.0D, 0.0D);
		}

		if (!isSoundPlaying) {
			isSoundPlaying = true;
			SoundBarrierMethods.playTileSound(ElectrodynamicsSounds.SOUND_ELECTROLYTICSEPARATOR.get(), this, true);
		}
	}

	@Override
	public void setNotPlaying() {
		isSoundPlaying = false;
	}

	@Override
	public boolean shouldPlaySound() {
		return this.<ComponentProcessor>getComponent(IComponentType.Processor).isActive();
	}

	@Override
	public int getComparatorSignal() {
		return this.<ComponentProcessor>getComponent(IComponentType.Processor).isActive() ? 15 : 0;
	}

}
