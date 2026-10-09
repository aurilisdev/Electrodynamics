package electrodynamics.common.tile.machines;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerChemicalMixer;
import electrodynamics.registers.ElectrodynamicsRecipes;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentContainerProvider;
import voltaic.prefab.tile.components.type.ComponentElectrodynamic;
import voltaic.prefab.tile.components.type.ComponentFluidHandlerMulti;
import voltaic.prefab.tile.components.type.ComponentInventory;
import voltaic.prefab.tile.components.type.ComponentProcessor;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.tile.types.GenericMaterialTile;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.registers.VoltaicCapabilities;

public class TileChemicalMixer extends GenericMaterialTile {

    public static final int MAX_TANK_CAPACITY = 5000;

    public TileChemicalMixer(BlockPos worldPosition, BlockState blockState) {
	super(ElectrodynamicsTiles.TILE_CHEMICALMIXER.get(), worldPosition, blockState);
	addComponent(new ComponentTickable(this).tickClient(this::tickClient));

	addComponent(new ComponentElectrodynamic(this, false, true)
		.setInputDirections(BlockEntityUtils.MachineDirection.BACK)
		.voltage(VoltaicCapabilities.DEFAULT_VOLTAGE * 2));
	addComponent(new ComponentFluidHandlerMulti(this)
		.setTanks(1, 1, new int[] { MAX_TANK_CAPACITY }, new int[] { MAX_TANK_CAPACITY })
		.setInputDirections(BlockEntityUtils.MachineDirection.RIGHT)
		.setOutputDirections(BlockEntityUtils.MachineDirection.LEFT)
		.setRecipeType(ElectrodynamicsRecipes.CHEMICAL_MIXER_TYPE.get()));
	addComponent(new ComponentInventory(this,
		ComponentInventory.InventoryBuilder.newInv().processors(1, 1, 0, 0).bucketInputs(1).bucketOutputs(1)
			.upgrades(3))
		//
		.setDirectionsBySlot(0, BlockEntityUtils.MachineDirection.FRONT, BlockEntityUtils.MachineDirection.TOP,
			BlockEntityUtils.MachineDirection.BOTTOM)
		.validUpgrades(ContainerChemicalMixer.VALID_UPGRADES).valid(machineValidator()));
	addComponent(new ComponentProcessor(this)
		.canProcess((component, level, procNumber) -> component.outputToFluidPipe().consumeBucket()
			.dispenseBucket().canProcessFluidItem2FluidRecipe(level, procNumber,
				ElectrodynamicsRecipes.CHEMICAL_MIXER_TYPE.get()))
		.process(ComponentProcessor::processFluidItem2FluidRecipe));
	addComponent(new ComponentContainerProvider(SubtypeMachine.chemicalmixer.tag(), this)
		.createMenu((id, player) -> new ContainerChemicalMixer(id, player,
			requireComponent(IComponentType.Inventory), getCoordsArray())));

    }

    protected void tickClient(Level level, ComponentTickable tickable) {
	if (!this.<ComponentProcessor>requireComponent(IComponentType.Processor).isActive(0))
	    return;

	if (level.random.nextDouble() < 0.15) {
	    level.addParticle(ParticleTypes.SMOKE, worldPosition.getX() + level.random.nextDouble(),
		    worldPosition.getY() + level.random.nextDouble() * 0.4 + 0.5,
		    worldPosition.getZ() + level.random.nextDouble(), 0.0D, 0.0D, 0.0D);
	}

    }

    @Override
    public int getComparatorSignal(Level level) {
	return this.<ComponentProcessor>requireComponent(IComponentType.Processor).isActive(0) ? 15 : 0;
    }

}
