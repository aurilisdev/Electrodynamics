package electrodynamics.common.tile.machines;

import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerFermentationPlant;
import electrodynamics.registers.ElectrodynamicsRecipies;
import electrodynamics.registers.ElectrodynamicsTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.state.BlockState;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentContainerProvider;
import voltaic.prefab.tile.components.type.ComponentElectrodynamic;
import voltaic.prefab.tile.components.type.ComponentFluidHandlerMulti;
import voltaic.prefab.tile.components.type.ComponentInventory;
import voltaic.prefab.tile.components.type.ComponentPacketHandler;
import voltaic.prefab.tile.components.type.ComponentProcessor;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.tile.types.GenericMaterialTile;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.registers.VoltaicCapabilities;

public class TileFermentationPlant extends GenericMaterialTile {

    public static final int MAX_TANK_CAPACITY = 5000;

    public TileFermentationPlant(BlockPos worldPosition, BlockState blockState) {
        super(ElectrodynamicsTiles.TILE_FERMENTATIONPLANT.get(), worldPosition, blockState);
        addComponent(new ComponentTickable(this).tickClient(this::tickClient));
        addComponent(new ComponentPacketHandler(this));
        addComponent(new ComponentElectrodynamic(this, false, true).setInputDirections(BlockEntityUtils.MachineDirection.BOTTOM).voltage(VoltaicCapabilities.DEFAULT_VOLTAGE));
        addComponent(new ComponentFluidHandlerMulti(this).setTanks(1, 1, new int[] { MAX_TANK_CAPACITY }, new int[] { MAX_TANK_CAPACITY }).setInputDirections(BlockEntityUtils.MachineDirection.LEFT).setOutputDirections(BlockEntityUtils.MachineDirection.RIGHT).setRecipeType(ElectrodynamicsRecipies.FERMENTATION_PLANT_TYPE.get()));
        addComponent(new ComponentInventory(this, ComponentInventory.InventoryBuilder.newInv().processors(1, 1, 0, 0).bucketInputs(1).bucketOutputs(1).upgrades(3))
                //
                .setDirectionsBySlot(0, BlockEntityUtils.MachineDirection.FRONT, BlockEntityUtils.MachineDirection.BACK, BlockEntityUtils.MachineDirection.TOP, BlockEntityUtils.MachineDirection.BOTTOM).validUpgrades(ContainerFermentationPlant.VALID_UPGRADES).valid(machineValidator()));
        addComponent(new ComponentProcessor(this).canProcess((processor, procNumber) -> processor.outputToFluidPipe().consumeBucket().dispenseBucket().canProcessFluidItem2FluidRecipe(procNumber, ElectrodynamicsRecipies.FERMENTATION_PLANT_TYPE.get())).process(ComponentProcessor::processFluidItem2FluidRecipe));
        addComponent(new ComponentContainerProvider(SubtypeMachine.fermentationplant.tag(), this).createMenu((id, player) -> new ContainerFermentationPlant(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));

    }

    protected void tickClient(ComponentTickable tickable) {
        if (this.<ComponentProcessor>getComponent(IComponentType.Processor).isActive(0)) {
            if (level.random.nextDouble() < 0.15) {
                level.addParticle(ParticleTypes.SMOKE, worldPosition.getX() + level.random.nextDouble(), worldPosition.getY() + level.random.nextDouble() * 0.4 + 0.5, worldPosition.getZ() + level.random.nextDouble(), 0.0D, 0.0D, 0.0D);
            }
            Direction dir = getFacing();
            double x = worldPosition.getX() + 0.55 - dir.getStepX() * 0.2;
            double z = worldPosition.getZ() + 0.55 - dir.getStepZ() * 0.2;
            level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, x, worldPosition.getY() + 0.4, z, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public int getComparatorSignal() {
        return this.<ComponentProcessor>getComponent(IComponentType.Processor).isActive(0) ? 15 : 0;
    }

}
