package electrodynamics.common.tile.machines.wiremill;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.settings.ElectroConstants;
import electrodynamics.registers.ElectrodynamicsRecipies;
import electrodynamics.registers.ElectrodynamicsSounds;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import voltaic.common.inventory.container.ContainerO2OProcessor;
import voltaic.prefab.sound.ITickableSound;
import voltaic.prefab.sound.SoundBarrierMethods;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentContainerProvider;
import voltaic.prefab.tile.components.type.ComponentElectrodynamic;
import voltaic.prefab.tile.components.type.ComponentInventory;
import voltaic.prefab.tile.components.type.ComponentPacketHandler;
import voltaic.prefab.tile.components.type.ComponentProcessor;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.registers.VoltaicCapabilities;

public class TileWireMill extends GenericTile implements ITickableSound {

    private boolean isSoundPlaying = false;

    public TileWireMill(BlockPos worldPosition, BlockState blockState) {
	this(ElectrodynamicsTiles.TILE_WIREMILL.get(), 1, worldPosition, blockState);

	addComponent(new ComponentContainerProvider(SubtypeMachine.wiremill.tag(), this)
		.createMenu((id, player) -> new ContainerO2OProcessor(id, player,
			getComponent(IComponentType.Inventory), getCoordsArray())));
    }

    public TileWireMill(BlockEntityType<?> type, int procCount, BlockPos worldPosition, BlockState blockState) {
	super(type, worldPosition, blockState);

	int inputsPerProc = 1;
	int outputPerProc = 1;
	int biprodsPerProc = 1;

	addComponent(new ComponentPacketHandler(this));
	addComponent(new ComponentTickable(this).tickClient(this::tickClient));
	addComponent(new ComponentElectrodynamic(this, false, true)
		.setInputDirections(BlockEntityUtils.MachineDirection.BACK)
		.voltage(VoltaicCapabilities.DEFAULT_VOLTAGE * Math.pow(2, procCount - 1))
		.joules(ElectroConstants.WIREMILL_USAGE_PER_TICK * 20 * procCount));
	addComponent(new ComponentInventory(this,
		ComponentInventory.InventoryBuilder.newInv()
			.processors(procCount, inputsPerProc, outputPerProc, biprodsPerProc).upgrades(3))
		.validUpgrades(ContainerO2OProcessor.VALID_UPGRADES).valid(machineValidator())
		.implementMachineInputsAndOutputs());
	addComponent(new ComponentProcessor(this, procCount)
		.canProcess((component, procNumber) -> component.canProcessItem2ItemRecipe(procNumber,
			ElectrodynamicsRecipies.WIRE_MILL_TYPE.get()))
		.process(ComponentProcessor::processItem2ItemRecipe));

    }

    protected void tickClient(ComponentTickable tickable) {
	if (!this.<ComponentProcessor>getComponent(IComponentType.Processor).isAnyActive()) {
	    return;
	}

	if (level.random.nextDouble() < 0.15) {
	    level.addParticle(ParticleTypes.SMOKE, worldPosition.getX() + level.random.nextDouble(),
		    worldPosition.getY() + level.random.nextDouble() * 0.5 + 0.5,
		    worldPosition.getZ() + level.random.nextDouble(), 0.0D, 0.0D, 0.0D);
	}

	if (!isSoundPlaying) {
	    isSoundPlaying = true;
	    SoundBarrierMethods.playTileSound(ElectrodynamicsSounds.SOUND_HUM.get(), this, true);
	}
    }

    @Override
    public void setNotPlaying() {
	isSoundPlaying = false;
    }

    @Override
    public boolean shouldPlaySound() {
	return this.<ComponentProcessor>getComponent(IComponentType.Processor).isAnyActive();
    }

    @Override
    public int getComparatorSignal() {
	return (int) ((double) this.<ComponentProcessor>getComponent(IComponentType.Processor).getTotalActive()
		/ (double) Math.max(1,
			this.<ComponentProcessor>getComponent(IComponentType.Processor).getProcessorCount())
		* 15.0);
    }

}
