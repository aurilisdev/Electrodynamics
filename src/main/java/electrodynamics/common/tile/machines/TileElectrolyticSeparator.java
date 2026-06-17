package electrodynamics.common.tile.machines;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerElectrolyticSeparator;
import electrodynamics.registers.ElectrodynamicsRecipies;
import electrodynamics.registers.ElectrodynamicsSounds;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.state.BlockState;
import voltaic.common.network.utils.GasUtilities;
import voltaic.prefab.sound.ITickableSound;
import voltaic.prefab.sound.SoundBarrierMethods;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentContainerProvider;
import voltaic.prefab.tile.components.type.ComponentElectrodynamic;
import voltaic.prefab.tile.components.type.ComponentFluidHandlerMulti;
import voltaic.prefab.tile.components.type.ComponentGasHandlerMulti;
import voltaic.prefab.tile.components.type.ComponentInventory;
import voltaic.prefab.tile.components.type.ComponentPacketHandler;
import voltaic.prefab.tile.components.type.ComponentProcessor;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.tile.types.GenericGasTile;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.registers.VoltaicCapabilities;

public class TileElectrolyticSeparator extends GenericGasTile implements ITickableSound {

    public static final int MAX_INPUT_TANK_CAPACITY = 5000;
    public static final int MAX_OUTPUT_TANK_CAPACITY = 5000;
    public long clientTicks = 0;

    private static final BlockEntityUtils.MachineDirection OXYGEN_DIRECTION = BlockEntityUtils.MachineDirection.RIGHT;
    private static final BlockEntityUtils.MachineDirection HYDROGEN_DIRECTION = BlockEntityUtils.MachineDirection.LEFT;

    private boolean isSoundPlaying = false;

    public TileElectrolyticSeparator(BlockPos worldPos, BlockState blockState) {
	super(ElectrodynamicsTiles.TILE_ELECTROLYTICSEPARATOR.get(), worldPos, blockState);
	addComponent(new ComponentTickable(this).tickClient(this::tickClient).tickServer(this::tickServer));
	addComponent(new ComponentPacketHandler(this));
	addComponent(new ComponentElectrodynamic(this, false, true)
		.setInputDirections(BlockEntityUtils.MachineDirection.FRONT)
		.voltage(VoltaicCapabilities.DEFAULT_VOLTAGE * 2));
	addComponent(new ComponentFluidHandlerMulti(this).setInputDirections(BlockEntityUtils.MachineDirection.BACK)
		.setInputTanks(1, arr(MAX_INPUT_TANK_CAPACITY))
		.setRecipeType(ElectrodynamicsRecipies.ELECTROLYTIC_SEPERATOR_TYPE.get()));
	addComponent(new ComponentGasHandlerMulti(this).setOutputDirections(OXYGEN_DIRECTION, HYDROGEN_DIRECTION)
		.setOutputTanks(2, arr(MAX_OUTPUT_TANK_CAPACITY, MAX_OUTPUT_TANK_CAPACITY), arr(1000, 1000),
			arr(1024, 1024))
		.setRecipeType(ElectrodynamicsRecipies.ELECTROLYTIC_SEPERATOR_TYPE.get())
		.setCondensedHandler(getCondensedHandler()));
	addComponent(new ComponentInventory(this,
		ComponentInventory.InventoryBuilder.newInv().bucketInputs(1).gasOutputs(2).upgrades(3))
		.validUpgrades(ContainerElectrolyticSeparator.VALID_UPGRADES).valid(machineValidator()));
	addComponent(new ComponentProcessor(this)
		.canProcess((component, procNumber) -> component.consumeBucket().dispenseGasCylinder()
			.canProcessFluid2GasRecipe(procNumber,
				ElectrodynamicsRecipies.ELECTROLYTIC_SEPERATOR_TYPE.get()))
		.process(ComponentProcessor::processFluid2GasRecipe));
	addComponent(new ComponentContainerProvider(SubtypeMachine.electrolyticseparator.tag(), this)
		.createMenu((id, player) -> new ContainerElectrolyticSeparator(id, player,
			getComponent(IComponentType.Inventory), getCoordsArray())));
    }

    public void tickServer(ComponentTickable tickable) {
	ComponentGasHandlerMulti handler = getComponent(IComponentType.GasHandler);
	GasUtilities.outputToPipe(this, handler.getOutputTanks()[0].asArray(), OXYGEN_DIRECTION.mappedDir);
	GasUtilities.outputToPipe(this, handler.getOutputTanks()[1].asArray(), HYDROGEN_DIRECTION.mappedDir);
    }

    protected void tickClient(ComponentTickable tickable) {
	if (!shouldPlaySound()) {
	    return;
	}
	if (level.random.nextDouble() < 0.15) {
	    level.addParticle(ParticleTypes.SMOKE, worldPosition.getX() + level.random.nextDouble(),
		    worldPosition.getY() + level.random.nextDouble() * 0.4 + 0.5,
		    worldPosition.getZ() + level.random.nextDouble(), 0.0D, 0.0D, 0.0D);
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
	return this.<ComponentProcessor>getComponent(IComponentType.Processor).isActive(0);
    }

    @Override
    public int getComparatorSignal() {
	return this.<ComponentProcessor>getComponent(IComponentType.Processor).isActive(0) ? 15 : 0;
    }

}
